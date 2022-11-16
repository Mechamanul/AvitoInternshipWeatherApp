package com.mechamanul.avitointernshipweatherapp.ui.screens.change_city

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mechamanul.avitointernshipweatherapp.R

class ChangeCityFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_change_city, container, false)
    }
}