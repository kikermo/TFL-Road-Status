package org.kikermo.tflroadstatus.presentation.roadstatus

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
internal fun RoadStatusScreen(
    viewModel: RoadStatusViewModel = viewModel()
) {
    Box(
        Modifier.fillMaxSize()
    ) {
        InitialData()
    }
}

@Composable
private fun InitialData() {
    Text(text = "Test")
}