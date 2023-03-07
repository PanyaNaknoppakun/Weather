package com.panya.weather.presentation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.panya.weather.R
import com.panya.weather.databinding.ActivityMainBinding
import com.panya.weather.presentation.viewmodel.MainViewModel
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    companion object {
        private const val BANGKOK = "Bangkok"
    }

    private val viewModel by viewModel<MainViewModel>()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observeViewModel()
        viewModel.getBangkokWeather(BANGKOK)
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.onShowWeather.collect { _weatherModel ->
                        binding.apply {
                            _weatherModel?.let {
                                progressLoading.visibility = View.GONE
                                txtLoading.visibility = View.GONE
                                txtTitle.text = it.cityName
                                txtTemperature.text =
                                    getString(R.string.temperature, it.temperature)
                                txtHumidityValue.text =
                                    getString(R.string.humidity_value, it.humidity)
                                txtSunsetTitle.text = getString(it.sunState.title)
                                txtSunsetValue.text = it.sunState.value
                                txtSyncedAtValue.text = it.syncedAt
                                val highTemp = it.temperatureMax
                                val lowTemp = it.temperatureMin
                                txtTemperatureLength.text =
                                    getString(R.string.temperature_h_l, highTemp, lowTemp)
                                txtWeather.text = it.weatherDesc
                                txtHumidityTitle.text = getString(R.string.humidity)
                                txtSyncedAtTitle.text = getString(R.string.synced_at)
                                txtIconStatus.text = getString(it.iconState)
                            }
                        }
                    }
                }
                launch {
                    viewModel.onShowError.filterNotNull().collect {
                        binding.apply {
                            progressLoading.visibility = View.GONE
                            txtLoading.visibility = View.GONE
                            imgError.visibility = View.VISIBLE
                            txtTitleErrorMsg.visibility = View.VISIBLE
                            txtSubtitleErrorMsg.visibility = View.VISIBLE
                        }
                    }
                }
            }

        }
    }
}