package com.dosbots.architecture

import com.dosbots.arch.UiEvent
import com.dosbots.arch.UiState
import com.dosbots.arch.UserAction

data class ArchTestUiState(
    val loading: Boolean = false,
    val text: String = ""
) : UiState

sealed class ArchTestUiEvent : UiEvent {
    data class Toast(val message: String) : ArchTestUiEvent()
}

sealed class ArchTestUserAction : UserAction {
    object ViewScreen : ArchTestUserAction()
    object ClickButton : ArchTestUserAction()
}