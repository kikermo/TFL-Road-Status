package org.kikermo.tflroadstatus.presentation.roadstatus

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.kikermo.tflroadstatus.domain.model.Road
import javax.inject.Inject

@HiltViewModel
internal class RoadStatusViewModel @Inject constructor(

) : ViewModel() {
    private val _viewState = MutableStateFlow<ViewState>(ViewState.InitialState(::loadData))
    val viewState: StateFlow<ViewState> = _viewState.asStateFlow()

    private fun loadData(roadName: String) {
        _viewState.value = ViewState.Loading

        // Interactor call

    }

    private fun handleResponse() {

    }

    sealed class ViewState {
        object Loading : ViewState()
        data class InitialState(val onRoadNameSubmitted: (String) -> Unit) : ViewState()

        data class RoadStatus(val road: Road) : ViewState()

        data class ErrorState(val errorMessage: String) : ViewState()
    }
}