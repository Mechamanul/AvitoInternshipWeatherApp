package com.mechamanul.avitointernshipweatherapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mechamanul.avitointernshipweatherapp.databinding.ForecastRvItemBinding
import com.mechamanul.avitointernshipweatherapp.domain.model.WeatherTime
import java.time.format.DateTimeFormatter

class ForecastAdapter : RecyclerView.Adapter<ForecastAdapter.ViewHolder>() {
    private var _currentList: List<WeatherTime> = emptyList()
    fun setItems(list: List<WeatherTime>) {
        _currentList = list
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ForecastRvItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(forecastTime: WeatherTime) {
            binding.apply {
                time.text = if (forecastTime.time.hour != 0) {
                    "${forecastTime.time.toLocalTime()}"
                } else {
                    forecastTime.time.toLocalDate().format(DateTimeFormatter.ofPattern("dd-MM"))
                }
                temperature.text = forecastTime.temperature.toString()
                Glide.with(binding.root.context).load(forecastTime.weatherIconPath)
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