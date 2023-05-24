package org.kikermo.tflroadstatus.presentation.roadstatus

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.kikermo.tflroadstatus.R
import org.kikermo.tflroadstatus.domain.coroutines.CoroutinesContextProvider
import org.kikermo.tflroadstatus.domain.model.Road
import org.kikermo.tflroadstatus.domain.usecase.GetRoadStatusUseCase
import org.kikermo.tflroadstatus.ui.utils.StringProvider
import org.kikermo.tflroadstatus.ui.utils.toText
import javax.inject.Inject

@HiltViewModel
internal class RoadStatusViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val coroutinesContextProvider: CoroutinesContextProvider,
    private val getRoadStatus: GetRoadStatusUseCase,
    private val stringProvider: StringProvider,
) : ViewModel() {
    private val _viewState = MutableStateFlow<ViewState>(ViewState.Loading)
    val viewState: StateFlow<ViewState> = _viewState.asStateFlow()

    private val roadId: String = checkNotNull(savedStateHandle["roadId"])

    init {
        loadData(roadId)
    }

    private fun loadData(roadName: String) {
        _viewState.value = ViewState.Loading
        viewModelScope.launch(coroutinesContextProvider.main) {
            val status = getRoadStatus(roadName)
            handleResponse(status)
        }
    }

    private fun handleResponse(status: GetRoadStatusUseCase.Status) {
        _viewState.value = when (status) {
            is GetRoadStatusUseCase.Status.Failure -> ViewState.ErrorState(
                errorMessage = status.error.toText(stringProvider),
                errorAction = { loadData(roadId) },
            )

            is GetRoadStatusUseCase.Status.RoadNotValid -> ViewState.ErrorState(
                errorMessage = status.message
                    ?: stringProvider.getString(R.string.road_status_road_not_found_generic),
                errorAction = { loadData(roadId) },
            )

            is GetRoadStatusUseCase.Status.Success -> ViewState.RoadStatus(status.road)
        }
    }

    sealed class ViewState {
        object Loading : ViewState()

        data class RoadStatus(val road: Road) : ViewState()

        data class ErrorState(
            val errorMessage: String,
            val errorAction: () -> Unit,
        ) : ViewState()
    }
}
