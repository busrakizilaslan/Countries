package com.busrayalcin.countries.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.busrayalcin.countries.R
import com.busrayalcin.countries.adapters.SavedAdapter
import com.busrayalcin.countries.databinding.FragmentSavedBinding
import com.busrayalcin.countries.model.Country
import com.busrayalcin.countries.utils.ClickListener
import com.busrayalcin.countries.utils.Constants
import com.busrayalcin.countries.viewmodel.HomeViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SavedFragment : Fragment(), ClickListener {
    private lateinit var binding: FragmentSavedBinding
    private val homeViewModel: HomeViewModel by viewModels()
    private val adapter: SavedAdapter by lazy {
        SavedAdapter(this, homeViewModel.favouriteManager)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSavedBinding.inflate(inflater, container, false)

        refreshData()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val view = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        view.visibility = View.VISIBLE
    }

    private fun refreshData() {
        checkEmptyState()
        homeViewModel.favouriteManager.getSavedLiveData()?.observe(viewLifecycleOwner) {
            adapter.setData(homeViewModel.favouriteManager.getCountries() ?: arrayListOf())
            checkEmptyState()
        }

        binding.savedRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.savedRecyclerView.adapter = adapter
    }

    override fun onClickData(country: Country) {
        val bundle = Bundle()
        bundle.putParcelable(Constants.SHARED_PREFERENCES_KEY, country)
        view?.let {
            Navigation.findNavController(it)
                .navigate(R.id.action_savedFragment_to_detailFragment, bundle)
        }
    }

    private fun checkEmptyState() {
        val countries = homeViewModel.favouriteManager.getCountries() ?: arrayListOf()
        if (countries.isEmpty()) {
            binding.textViewEmpty.visibility = View.VISIBLE
            binding.savedRecyclerView.visibility = View.GONE
            binding.progressBarFav.visibility = View.GONE
        } else {
            binding.textViewEmpty.visibility = View.GONE
            binding.savedRecyclerView.visibility = View.VISIBLE
        }
    }
}
