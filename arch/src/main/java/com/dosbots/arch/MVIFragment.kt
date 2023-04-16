package com.dosbots.arch

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.launch

abstract class MVIFragment<Action : UserAction, Event : UiEvent, State : UiState> : Fragment() {

    abstract val viewModel: MVIViewModel<Action, Event, State>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
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
