package com.mechamanul.avitointernshipweatherapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.mechamanul.avitointernshipweatherapp.R
import com.mechamanul.avitointernshipweatherapp.databinding.FragmentForecastBinding

class ForecastFragment : Fragment() {

    private val args: ForecastFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_forecast, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentForecastBinding.bind(view)
        binding.forecastTypeTitle.text = when (args.forecastType) {
            ForecastType.HOURLY -> "Hourly forecast"
            ForecastType.DAILY -> "Daily forecast"
        }
        super.onViewCreated(view, savedInstanceState)

    }

}