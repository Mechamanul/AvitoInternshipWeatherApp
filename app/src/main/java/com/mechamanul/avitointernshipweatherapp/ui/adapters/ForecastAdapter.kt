package com.mechamanul.avitointernshipweatherapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mechamanul.avitointernshipweatherapp.databinding.ForecastRvItemBinding
import com.mechamanul.avitointernshipweatherapp.domain.model.ForecastTime

class ForecastAdapter : RecyclerView.Adapter<ForecastAdapter.ViewHolder>() {
    private var _currentList: List<ForecastTime> = emptyList()
    fun setItems(list: List<ForecastTime>) {
        _currentList = list
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ForecastRvItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(forecastTime: ForecastTime) {
            binding.apply {
                time.text = forecastTime.datetime.toString()
                temperature.text = forecastTime.temperature.toString()
                Glide.with(binding.root).load(forecastTime.weatherIconPath).centerCrop()
                    .into(weatherIcon)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ForecastRvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(_currentList[position])
    }

    override fun getItemCount(): Int = _currentList.size

}