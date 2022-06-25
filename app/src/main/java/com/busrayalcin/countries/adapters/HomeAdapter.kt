package com.busrayalcin.countries.adapters

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.busrayalcin.countries.R
import com.busrayalcin.countries.databinding.RowCountryBinding
import com.busrayalcin.countries.model.Country
import com.busrayalcin.countries.utils.ClickListener
import com.busrayalcin.countries.utils.FavouriteManager


class HomeAdapter constructor(
    private val listener: ClickListener,
    private val favouriteManager: FavouriteManager
) :
    RecyclerView.Adapter<HomeAdapter.DataViewHolder>() {

    private var countries: ArrayList<Country> = ArrayList()

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = RowCountryBinding.bind(itemView)
        fun bind(country: Country) {
            binding.countryNameTextView.text = country.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.row_country, parent,
                false
            )
        )

    override fun getItemCount(): Int = countries.size

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(countries[position])
        holder.binding.countryNameTextView.setOnClickListener {
            listener.onClickData(countries[position])
        }

        val isSaved = favouriteManager.countryIsSaved(countries[position])

        if (isSaved) {
            holder.binding.savedImageView.setImageResource(R.drawable.ic_saved)
        } else {
            holder.binding.savedImageView.setImageResource(R.drawable.ic_save)
        }

        holder.binding.savedImageView.setOnClickListener {
            val isInSaved = favouriteManager.countryIsSaved(countries[position])

            if (isInSaved) {
                favouriteManager.removeCountry(countries[position])
            } else {
                favouriteManager.setCountry(countries[position])
            }

            notifyDataSetChanged()
        }
    }

    fun setData(countries: List<Country>) {
        this.countries.apply {
            clear()
            addAll(countries)
        }
    }
}