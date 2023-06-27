package com.dosbots.architecture

import androidx.lifecycle.viewModelScope
import com.dosbots.arch.MVIViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ArchTestViewModel :
    MVIViewModel<ArchTestUserAction, ArchTestUiEvent, ArchTestUiState>(ArchTestUiState()) {

    private var clicks: Int = 0

    override fun handleUserAction(action: ArchTestUserAction, currentState: ArchTestUiState) {
        when (action) {
            ArchTestUserAction.ViewScreen -> {
                fetchSomeData()
            }
            ArchTestUserAction.ClickButton -> {
                clicks++
                emitEvent { ArchTestUiEvent.Toast("Here's your toast number $clicks") }
            }
        }
    }

    private fun fetchSomeData() {
        viewModelScope.launch {
            updateState { copy(loading = true) }
            delay(2000)
            updateState {
                ArchTestUiState(loading = false, text = "Hey! What's up?\nReady for some toasts?")
            }
        }
    }
}