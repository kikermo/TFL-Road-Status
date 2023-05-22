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
    private val mutableViewState = MutableStateFlow<ViewState>(ViewState.Loading)
    val viewState: StateFlow<ViewState> = mutableViewState.asStateFlow()

    init {

    }

    fun loadData() {

    }

    sealed class ViewState {
        object Loading : ViewState()

        data class Data(val road: Road) : ViewState()

        data class Error(val errorMessage: String) : ViewState()
    }
}