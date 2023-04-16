package com.dosbots.arch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

abstract class MVIViewModel<Action : UserAction, Event : UiEvent, State : UiState>(
    initialState: State
) : ViewModel() {

    private val _uiState : MutableStateFlow<State> = MutableStateFlow(initialState)
    val uiState = _uiState.asStateFlow()

    private val _uiEvent : Channel<Event> = Channel()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun dispatchAction(userAction: Action) {
        val currentState = _uiState.value
        handleUserAction(userAction, currentState)
    }

    protected fun updateState(newStateBuilder: State.() -> State) {
        _uiState.value = _uiState.value.newStateBuilder()
    }

    protected fun emitEvent(newEventBuilder: () -> Event) {
        viewModelScope.launch { _uiEvent.send(newEventBuilder()) }
    }

    protected fun runCoroutine(task: suspend () -> Unit) {
        viewModelScope.launch { task() }
    }

    protected abstract fun handleUserAction(action: Action, currentState: State)
}