package org.kikermo.tflroadstatus.presentation.roadstatus

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import org.kikermo.tflroadstatus.R
import org.kikermo.tflroadstatus.ui.theme.TflRoadStatusTheme

@Composable
internal fun RoadStatusScreen(
    viewModel: RoadStatusViewModel = viewModel(),
) {
    when (val viewState = viewModel.viewState.collectAsStateWithLifecycle().value) {
        is RoadStatusViewModel.ViewState.InitialState -> InitialData(onActionSubmitted = viewState.onRoadNameSubmitted)
        RoadStatusViewModel.ViewState.Loading -> Loading()
        is RoadStatusViewModel.ViewState.RoadStatus -> RoadStatus()
        is RoadStatusViewModel.ViewState.ErrorState -> ErrorState()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun InitialData(
    onActionSubmitted: (String) -> Unit,
) {
    var value: String by remember { mutableStateOf("") }

    Box(
        Modifier
            .fillMaxSize(),
    ) {
        Column(
            Modifier
                .align(alignment = Alignment.Center),
        ) {
            OutlinedTextField(
                value = value,
                onValueChange = { value = it },
                label = { Text(stringResource(id = R.string.road_status_input_label)) },
            )
            Button(
                modifier = Modifier.align(alignment = CenterHorizontally),
                onClick = { onActionSubmitted(value) },
            ) {
                Text(stringResource(id = R.string.road_status_button_submit))
            }
        }
    }
}

@Composable
private fun Loading() {
}

@Composable
private fun ErrorState() {
}

@Composable
private fun RoadStatus() {
}

@Composable
@Preview
fun PreviewInitialData() {
    TflRoadStatusTheme {
        InitialData(onActionSubmitted = {})
    }
}