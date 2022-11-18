package com.mechamanul.avitointernshipweatherapp.ui.screens.change_city

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.mechamanul.avitointernshipweatherapp.R
import com.mechamanul.avitointernshipweatherapp.databinding.FragmentChangeCityBinding
import com.mechamanul.avitointernshipweatherapp.domain.common.ApiResult
import com.mechamanul.avitointernshipweatherapp.ui.MainViewModel
import com.mechamanul.avitointernshipweatherapp.ui.adapters.CityListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ChangeCityFragment : Fragment() {
    override fun onAttach(context: Context) {
        super.onAttach(context)
        setMenuVisibility(false)
    }

    val searchViewModel: ChangeCityViewModel by viewModels()
    val viewModel: MainViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_change_city, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentChangeCityBinding.bind(view)
        val cityListAdapter = CityListAdapter(::setCityCallback)
        binding.apply {
            citiesRv.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            citiesRv.adapter = cityListAdapter
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                var queryTextChangedJob: Job? = null
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    queryTextChangedJob?.cancel()
                    if (newText != null) {
                        if (newText.isNotEmpty()) {
                            queryTextChangedJob = viewLifecycleOwner.lifecycleScope.launch {
                                delay(500)
                                searchViewModel.searchCity(newText)
                            }
                        } else {
                            cityListAdapter.submitList(listOf())
                        }
                    }

                    return false
                }
            })
            searchView.setIconifiedByDefault(false)
            super.onViewCreated(view, savedInstanceState)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    searchViewModel.suggestionsList.collect {
                        when (it) {
                            is ApiResult.Error -> Snackbar.make(
                                binding.root,
                                "${it.exception.message}", Snackbar.LENGTH_LONG
                            ).show()
                            is ApiResult.Success -> cityListAdapter.submitList(it.data)
                        }
                    }
                }
            }
        }
    }

    private fun setCityCallback(name: String) = lifecycleScope.launch {
        viewModel.setLocation(name)
        val navController = findNavController()
        navController.previousBackStackEntry?.destination?.id?.let {
            if (it == R.id.initial_screen) {
                findNavController().popBackStack(R.id.initial_screen, true)
                return@launch
            }
        }
        navController.popBackStack()
    }

}