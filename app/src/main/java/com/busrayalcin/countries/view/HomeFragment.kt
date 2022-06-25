package com.busrayalcin.countries.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.busrayalcin.countries.R
import com.busrayalcin.countries.adapters.HomeAdapter
import com.busrayalcin.countries.databinding.FragmentHomeBinding
import com.busrayalcin.countries.model.Country
import com.busrayalcin.countries.utils.ClickListener
import com.busrayalcin.countries.utils.Status
import com.busrayalcin.countries.viewmodel.HomeViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(), ClickListener {
    private lateinit var binding: FragmentHomeBinding
    private val homeViewModel: HomeViewModel by viewModels()

    lateinit var adapter: HomeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        refreshData()
        getDataFromAPI()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val view = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        view.visibility = View.VISIBLE
    }

    private fun refreshData() {
        binding.countriesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = HomeAdapter(this, homeViewModel.favouriteManager)
        binding.countriesRecyclerView.adapter = adapter
    }

    private fun getDataFromAPI() {
        homeViewModel.fetchCountries().observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    it.data?.let { countryData -> adapter.setData(countryData.data) }
                    binding.countriesRecyclerView.visibility = View.VISIBLE
                }
                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.countriesRecyclerView.visibility = View.GONE
                }
                Status.ERROR -> {
                    binding.countriesRecyclerView.visibility = View.GONE
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onClickData(country: Country) {
        val bundle = Bundle()
        bundle.putParcelable("country", country)
        view?.let {
            Navigation.findNavController(it)
                .navigate(R.id.action_mainFragment_to_detailFragment, bundle)
        }
    }
}