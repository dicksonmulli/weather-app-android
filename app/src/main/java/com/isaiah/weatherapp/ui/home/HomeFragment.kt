package com.isaiah.weatherapp.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.isaiah.weatherapp.databinding.FragmentHomeBinding
import com.isaiah.weatherapp.utils.hide
import com.isaiah.weatherapp.utils.show
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private lateinit var alertDialog: AlertDialog
    private val dismissalDelayMillis = 10000 // 5 seconds

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        val homeViewModel =
//            ViewModelProvider(this).get(HomeViewModel::class.java)


        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setUpToolBar()
        setupClickListeners()
        setupObservers()

        setAutomaticLockOut()

        return root
    }

    private fun setUpToolBar() {
        val textView: TextView = binding.title
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
    }

    private fun setupClickListeners() {
        binding.apply {
            showWeatherDetailsButton.setOnClickListener {
                homeViewModel.fetchWeatherDetails()
            }
            // other click-listeners here
            btnDialog.setOnClickListener {
                openAlertDialog()
            }
        }
    }

    @SuppressLint("StringFormatInvalid")
    private fun setupObservers() {
        homeViewModel.uiState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is WeatherUiState.CoordinatesDetails -> {
                    Timber.tag("HomeFragment").d("********* state = %s", state)
                    showError("")
                    binding.apply {
                        longText.text = state.coordinates?.long.toString()
                        latText.text = state.coordinates?.lat.toString()
                    }
                }

                is WeatherUiState.CoordinatesDetailsError -> TODO()
                is WeatherUiState.Error -> {
                    showError(state.message.toString())
                }

                is WeatherUiState.Loading -> {
                    showError("Loading...")
                }

                is WeatherUiState.Success -> showError("Weather data fetched")
                is WeatherUiState.Weather -> {
                    showError("")
                    binding.apply {
                        weatherIdText.text = state.weatherDetails?.id.toString()
                        mainText.text = state.weatherDetails?.main
                        descriptionText.text = state.weatherDetails?.description
                    }
                }

                is WeatherUiState.WeatherDetailsError -> showError(state.message)
                WeatherUiState.WeatherEmpty -> TODO()
            }
        }
    }

    private fun showError(message: String) {
        binding.apply {
            if (message.isNotEmpty()) {
                errorText.text = message
                errorText.show()
            } else {
                errorText.hide()
            }
        }
    }

    fun openAlertDialog() {
        // Create an AlertDialog.Builder
        val builder = this@HomeFragment.context?.let { AlertDialog.Builder(it) }
        builder?.setTitle("Auto Dismiss Dialog")
        builder?.setMessage("This dialog is a dialog....")
        // Set a positive button and its click listener
        builder?.setPositiveButton("OK") { dialog, which ->
            // Handle positive button click if needed
            dialog.dismiss()
        }
        // Create the AlertDialog
        if (builder != null) {
            alertDialog = builder.create()
        }
        // Show the AlertDialog
        alertDialog.show()
        // Schedule the dismissal after a certain delay
    }

    private fun setAutomaticLockOut() {
        val handler = Handler()
        handler.postDelayed({
            Toast.makeText(this.context, "App locked out", Toast.LENGTH_LONG)

            alertDialog?.let { dialog ->
                if (dialog.isShowing) {
                    dialog.dismiss()
                }
            }
        }, dismissalDelayMillis.toLong())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}