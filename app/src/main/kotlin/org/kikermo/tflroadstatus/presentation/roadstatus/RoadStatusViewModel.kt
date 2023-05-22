package org.kikermo.tflroadstatus.presentation.roadstatus

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.kikermo.tflroadstatus.domain.coroutines.CoroutinesContextProvider
import org.kikermo.tflroadstatus.domain.model.Road
import org.kikermo.tflroadstatus.domain.usecase.GetRoadStatusUsecase
import javax.inject.Inject

@HiltViewModel
internal class RoadStatusViewModel @Inject constructor(
    private val coroutinesContextProvider: CoroutinesContextProvider,
    private val getRoadStatus: GetRoadStatusUsecase,
) : ViewModel() {
    private val _viewState = MutableStateFlow<ViewState>(ViewState.InitialState(::loadData))
    val viewState: StateFlow<ViewState> = _viewState.asStateFlow()

    private fun loadData(roadName: String) {
        _viewState.value = ViewState.Loading
        viewModelScope.launch(coroutinesContextProvider.main) {
            val status = getRoadStatus(roadName)
            handleResponse(status)
        }
    }

    private fun handleResponse(status: GetRoadStatusUsecase.Status) {
        _viewState.value = when (status) {
            is GetRoadStatusUsecase.Status.Failure -> ViewState.ErrorState(
                errorMessage = "error",
                errorActionText = "try again",
                errorAction = {},
            )

            GetRoadStatusUsecase.Status.RoadNotValid -> ViewState.ErrorState(
                errorMessage = "error",
                errorActionText = "try again",
                errorAction = {},
            )

            is GetRoadStatusUsecase.Status.Success -> ViewState.RoadStatus(status.road)
        }
    }

    sealed class ViewState {
        object Loading : ViewState()
        data class InitialState(val onRoadNameSubmitted: (String) -> Unit) : ViewState()

        data class RoadStatus(val road: Road) : ViewState()

        data class ErrorState(
            val errorMessage: String,
            val errorAction: () -> Unit,
            val errorActionText: String,
        ) : ViewState()
    }
}
