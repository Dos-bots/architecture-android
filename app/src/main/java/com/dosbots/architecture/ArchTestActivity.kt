package com.dosbots.architecture

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.dosbots.arch.MVIActivity
import com.dosbots.architecture.databinding.ActivityArchTestBinding

class ArchTestActivity : MVIActivity<ArchTestUserAction, ArchTestUiEvent, ArchTestUiState>() {

    override val viewModel: ArchTestViewModel by viewModels()

    private lateinit var binding: ActivityArchTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArchTestBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        viewModel.dispatchAction(ArchTestUserAction.ViewScreen)

        binding.buttonArchTestFeedMe.setOnClickListener {
            viewModel.dispatchAction(ArchTestUserAction.ClickButton)
        }
    }

    override fun handleNewUiState(newState: ArchTestUiState) {
        binding.apply {
            if (newState.loading) {
                groupArchTestMainView.visibility = View.GONE
                progressBarArchTestLoading.visibility = View.VISIBLE
            } else {
                groupArchTestMainView.visibility = View.VISIBLE
                progressBarArchTestLoading.visibility = View.GONE
            }
            textViewArchTestWelcomeMessage.text = newState.text
        }
    }

    override fun handleNewUiEvent(newEvent: ArchTestUiEvent) {
        when (newEvent) {
            is ArchTestUiEvent.Toast -> {
                Toast.makeText(this, newEvent.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}