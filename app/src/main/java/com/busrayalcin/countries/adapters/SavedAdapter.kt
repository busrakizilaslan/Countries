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

class SavedAdapter(
    private val listener: ClickListener,
    private val favouriteManager: FavouriteManager
) : RecyclerView.Adapter<SavedAdapter.ItemViewHolder>() {

    private var countriesSaved = favouriteManager.getCountries() ?: arrayListOf()

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = RowCountryBinding.bind(itemView)

        fun bind(country: Country) {
            binding.countryNameTextView.text = country.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder =
        ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.row_country, parent,
                false
            )
        )

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(countriesSaved[position])
        holder.binding.countryNameTextView.setOnClickListener {
            listener.onClickData(countriesSaved[position])
        }

        holder.binding.savedImageView.setImageResource(R.drawable.ic_saved)

        holder.binding.savedImageView.setOnClickListener {
            favouriteManager.removeCountry(countriesSaved[position])
        }
    }

    override fun getItemCount(): Int = countriesSaved.size

    fun setData(countries: ArrayList<Country>) {
        this.countriesSaved = countries
        notifyDataSetChanged()
    }
}