package com.example.service.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.service.api.ApiClient
import com.example.service.api.dto.SwitchDto
import com.example.service.api.dto.SwitchPatch
import kotlinx.coroutines.launch

class SwitchesViewModel : ViewModel() {

    var switches by mutableStateOf<List<SwitchDto>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var error by mutableStateOf<String?>(null)
        private set

    init { loadSwitches() }

    fun loadSwitches() {
        viewModelScope.launch {
            isLoading = true
            error = null
            try {
                switches = ApiClient.api.getSwitches()
            } catch (e: Exception) {
                error = e.message ?: "Ошибка загрузки"
            } finally {
                isLoading = false
            }
        }
    }

    fun toggle(device: SwitchDto, turnOn: Boolean) {
        viewModelScope.launch {
            try {
                ApiClient.api.setState(device.id, SwitchPatch(on_off = turnOn))
                switches = switches.map {
                    if (it.id == device.id) it.copy(on_off = turnOn) else it
                }
            } catch (e: Exception) {
                error = e.message
            }
        }
    }
}