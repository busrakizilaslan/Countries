package com.busrayalcin.countries.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.busrayalcin.countries.R
import com.busrayalcin.countries.databinding.FragmentDetailBinding
import com.busrayalcin.countries.model.Country
import com.busrayalcin.countries.utils.Constants
import com.busrayalcin.countries.utils.Status
import com.busrayalcin.countries.utils.loadImage
import com.busrayalcin.countries.viewmodel.DetailViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    private val detailViewModel: DetailViewModel by viewModels()
    private var country: Country? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)

        country = arguments?.getParcelable(Constants.SHARED_PREFERENCES_KEY)
        country?.let {
            getDataFromAPI()
            isSaved()
        }

        binding.backButtonImageView.setOnClickListener {
            requireActivity().onBackPressed()
        }

        binding.saveButtonImageView.setOnClickListener {
            country?.let {
                val isSaved = detailViewModel.favouriteManager.countryIsSaved(it)
                if (isSaved)
                    detailViewModel.favouriteManager.removeCountry(it)
                else
                    detailViewModel.favouriteManager.setCountry(it)
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val view = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        view.visibility = View.GONE
    }

    private fun getDataFromAPI() {
        detailViewModel.favouriteManager.getSavedLiveData()?.observe(viewLifecycleOwner) {
            isSaved()
        }

        detailViewModel.fetchCountryDetail(country?.code.orEmpty())
            .observe(viewLifecycleOwner) { countryDetailResponse ->
                when (countryDetailResponse.status) {
                    Status.LOADING -> {
                        binding.progress.visibility = View.VISIBLE
                        binding.linearLayout.visibility = View.GONE
                    }
                    Status.SUCCESS -> {
                        binding.countryFlagImageView.loadImage(countryDetailResponse.data?.data?.flagImageUri)
                        binding.titleToolbarTextView.text = resources.getString(
                            R.string.country_name,
                            countryDetailResponse.data?.data?.name
                        )
                        binding.countryCodeTextView.text =
                            resources.getString(R.string.countryCode, country?.code)
                        binding.wikiButton.setOnClickListener {
                            val queryUrl: Uri =
                                Uri.parse("${Constants.WIKI_DATA_URL}${countryDetailResponse.data?.data?.wikiDataId}")
                            val intent = Intent(Intent.ACTION_VIEW, queryUrl)
                            context?.startActivity(intent)
                        }
                        binding.progress.visibility = View.GONE
                        binding.linearLayout.visibility = View.VISIBLE
                    }
                    Status.ERROR -> {
                        binding.linearLayout.visibility = View.GONE
                        Toast.makeText(
                            requireContext(),
                            countryDetailResponse.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
    }

    private fun isSaved() {
        country?.let {
            val isSaved = detailViewModel.favouriteManager.countryIsSaved(it)

            if (isSaved)
                binding.saveButtonImageView.setImageResource(R.drawable.ic_saved)
            else
                binding.saveButtonImageView.setImageResource(R.drawable.ic_save)
        }
    }

}