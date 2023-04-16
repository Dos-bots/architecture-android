package com.dosbots.arch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.launch

abstract class MVIActivity<Action : UserAction, Event : UiEvent, State : UiState> : AppCompatActivity() {

    abstract val viewModel: MVIViewModel<Action, Event, State>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.uiState.collect { newState -> handleNewUiState(newState) }
                }
                launch {
                    viewModel.uiEvent.collect { newEvent -> handleNewUiEvent(newEvent) }
                }
            }
        }
    }

    protected abstract fun handleNewUiState(newState: State)
    protected abstract fun handleNewUiEvent(newEvent: Event)
}