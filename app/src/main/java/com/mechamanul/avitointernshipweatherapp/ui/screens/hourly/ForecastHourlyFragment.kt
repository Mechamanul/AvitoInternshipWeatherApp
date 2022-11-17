package com.mechamanul.avitointernshipweatherapp.ui.screens.hourly

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
import com.mechamanul.avitointernshipweatherapp.databinding.FragmentHourlyForecastBinding
import com.mechamanul.avitointernshipweatherapp.domain.common.ApiResult
import com.mechamanul.avitointernshipweatherapp.ui.MainViewModel
import com.mechamanul.avitointernshipweatherapp.ui.adapters.ForecastAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ForecastHourlyFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
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
                viewModel.hourlyForecast.collect { apiResult ->
                    when (apiResult) {
                        is ApiResult.Error -> Snackbar.make(
                            binding.root,
                            apiResult.exception.message.toString(),
                            Snackbar.LENGTH_LONG
                        )
                        is ApiResult.Success -> binding.apply {
                            cityName.text = "Pavlodar"
                            humidity.text =
                                getString(R.string.humidity_textview, apiResult.data.humidity)
                            uv.text = getString(R.string.uv_textview, apiResult.data.uv)
                            temperature.text = "${apiResult.data.temperature}"
                            weatherDescription.text = apiResult.data.weatherDescription
                            windSpeed.text = getString(R.string.wind_mph, apiResult.data.windSpeed)
                            feelsLike.text = getString(
                                R.string.feels_like_textview,
                                apiResult.data.feelsLikeTemperature
                            )
                            Glide.with(requireContext()).load(apiResult.data.iconPath)
                                .override(128, 128)
                                .into(weatherIcon)
                            forecastAdapter.setItems(apiResult.data.forecast)
                        }
                    }
                }
            }
        }
    }

}