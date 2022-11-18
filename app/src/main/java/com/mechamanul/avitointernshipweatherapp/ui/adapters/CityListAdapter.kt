package com.mechamanul.avitointernshipweatherapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mechamanul.avitointernshipweatherapp.databinding.CitiesRvItemBinding
import com.mechamanul.avitointernshipweatherapp.domain.model.City

class DiffCallback : DiffUtil.ItemCallback<City>() {

    override fun areItemsTheSame(oldItem: City, newItem: City): Boolean = oldItem.id == newItem.id


    override fun areContentsTheSame(oldItem: City, newItem: City): Boolean = oldItem == newItem
}

class CityListAdapter(private val selectCityCallback: (name: String) -> Unit) :
    ListAdapter<City, CityListAdapter.ViewHolder>(DiffCallback()) {
    inner class ViewHolder(private val binding: CitiesRvItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(city: City) {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val city = getItem(position)
                    selectCityCallback(city.name)
                }
            }
            binding.apply {

                cityName.text = "${city.country} - ${city.name}"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            CitiesRvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}