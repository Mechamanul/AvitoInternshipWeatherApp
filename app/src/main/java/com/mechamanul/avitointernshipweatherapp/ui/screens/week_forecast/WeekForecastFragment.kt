package com.mechamanul.avitointernshipweatherapp.ui.screens.week_forecast

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.mechamanul.avitointernshipweatherapp.R
import com.mechamanul.avitointernshipweatherapp.databinding.FragmentDailyForecastBinding
import com.mechamanul.avitointernshipweatherapp.domain.common.ApiResult
import com.mechamanul.avitointernshipweatherapp.ui.MainViewModel
import com.mechamanul.avitointernshipweatherapp.ui.adapters.ForecastAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WeekForecastFragment : Fragment() {
    val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_daily_forecast, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentDailyForecastBinding.bind(view)
        val rvLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val forecastAdapter: ForecastAdapter = ForecastAdapter()
        binding.rv.apply {
            layoutManager = rvLayoutManager
            adapter = forecastAdapter
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.detailedForecast.collect { apiResult ->
                    when (apiResult) {
                        is ApiResult.Error -> {
                            Snackbar.make(
                                binding.root,
                                apiResult.exception.message.toString(),
                                Snackbar.LENGTH_LONG
                            ).show()
                        }
                        is ApiResult.Success -> {
                            binding.apply {

                                cityName.text = apiResult.data.city.name
                                humidity.text =
                                    getString(
                                        R.string.avg_humidity,
                                        apiResult.data.weekForecastDetails.avgHumidity
                                    )
                                uv.text = getString(
                                    R.string.uv_textview,
                                    apiResult.data.weekForecastDetails.uv
                                )
                                temperature.text =
                                    "${apiResult.data.weekForecastDetails.avgTemperature}"
                                weatherDescription.text =
                                    apiResult.data.weekForecastDetails.weatherDescription
                                windSpeed.text =
                                    getString(
                                        R.string.max_wind_mph,
                                        apiResult.data.weekForecastDetails.maxWindSpeed
                                    )
                                rainChance.text =
                                    getString(
                                        R.string.rain_chance,
                                        apiResult.data.weekForecastDetails.chanceOfRain
                                    )
                                Glide.with(requireContext())
                                    .load(apiResult.data.weekForecastDetails.iconPath)
                                    .override(128, 128)
                                    .into(weatherIcon)
                                forecastAdapter.setItems(apiResult.data.weekForecast)
                            }
                        }
                        else -> Unit
                    }
                }
            }
        }
        super.onViewCreated(view, savedInstanceState)
    }


}