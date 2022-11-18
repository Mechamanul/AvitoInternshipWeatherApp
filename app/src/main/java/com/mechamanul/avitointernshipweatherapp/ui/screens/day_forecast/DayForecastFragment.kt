package com.mechamanul.avitointernshipweatherapp.ui.screens.day_forecast

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.mechamanul.avitointernshipweatherapp.R
import com.mechamanul.avitointernshipweatherapp.databinding.FragmentHourlyForecastBinding
import com.mechamanul.avitointernshipweatherapp.domain.common.ApiResult
import com.mechamanul.avitointernshipweatherapp.ui.MainViewModel
import com.mechamanul.avitointernshipweatherapp.ui.adapters.ForecastAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DayForecastFragment : Fragment() {

    val viewModel: MainViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hourly_forecast, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentHourlyForecastBinding.bind(view)
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
                        is ApiResult.Error -> Snackbar.make(
                            binding.root,
                            "${apiResult.exception.message}",
                            Snackbar.LENGTH_LONG
                        ).show()
                        is ApiResult.Success -> binding.apply {
                            cityName.text = apiResult.data.city.name
                            humidity.text =
                                getString(
                                    R.string.humidity_textview,
                                    apiResult.data.dayForecastDetails.humidity
                                )
                            uv.text = getString(
                                R.string.uv_textview,
                                apiResult.data.dayForecastDetails.uv
                            )
                            temperature.text = "${apiResult.data.dayForecastDetails.temperature}"
                            weatherDescription.text =
                                apiResult.data.dayForecastDetails.weatherDescription
                            windSpeed.text = getString(
                                R.string.wind_mph,
                                apiResult.data.dayForecastDetails.windSpeed
                            )
                            feelsLike.text = getString(
                                R.string.feels_like_textview,
                                apiResult.data.dayForecastDetails.feelsLikeTemperature
                            )
                            Glide.with(requireContext())
                                .load(apiResult.data.dayForecastDetails.iconPath)
                                .override(128, 128)
                                .into(weatherIcon)
                            forecastAdapter.setItems(apiResult.data.dayForecast)
                        }
                        null -> findNavController().navigate(R.id.action_day_forecast_to_initial_screen)
                    }
                }
            }
        }
    }


}