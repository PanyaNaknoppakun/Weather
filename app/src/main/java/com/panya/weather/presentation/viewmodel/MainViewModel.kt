package com.panya.weather.presentation.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import com.panya.weather.domain.model.WeatherPartialModel
import com.panya.weather.domain.usecase.GetWeatherUseCase
import com.panya.weather.extension.toWeatherPartialModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlin.coroutines.CoroutineContext

class MainViewModel(
    private val getWeatherUseCase: GetWeatherUseCase
) : ViewModel(), CoroutineScope {

    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)
    override val coroutineContext: CoroutineContext
        get() = uiScope.coroutineContext

    private val _uiState = MutableStateFlow<WeatherPartialModel?>(null)
    val onShowWeather: StateFlow<WeatherPartialModel?> = _uiState.asStateFlow()
    private val _uiStateNetworkError = MutableStateFlow<String?>(null)
    val onShowError: StateFlow<String?> = _uiStateNetworkError.asStateFlow()

    @SuppressLint("NewApi")
    fun getBangkokWeather(city: String) {
        getWeatherUseCase.execute(city)
            .flowOn(Dispatchers.IO)
            .onEach { result ->
                _uiState.value = result.toWeatherPartialModel()
            }
            .catch { _error ->
                _uiStateNetworkError.value = _error.message
            }
            .launchIn(this)
    }
}