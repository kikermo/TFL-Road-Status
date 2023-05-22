package org.kikermo.tflroadstatus.presentation.roadstatus

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import org.kikermo.tflroadstatus.R
import org.kikermo.tflroadstatus.domain.model.Road
import org.kikermo.tflroadstatus.ui.theme.TflRoadStatusTheme
import org.kikermo.tflroadstatus.ui.views.ErrorLayout
import org.kikermo.tflroadstatus.ui.views.LoadingLayout

@Composable
internal fun RoadStatusScreen(
    viewModel: RoadStatusViewModel = viewModel(),
) {
    when (val viewState = viewModel.viewState.collectAsStateWithLifecycle().value) {
        is RoadStatusViewModel.ViewState.InitialState -> InitialData(onActionSubmitted = viewState.onRoadNameSubmitted)
        RoadStatusViewModel.ViewState.Loading -> Loading()
        is RoadStatusViewModel.ViewState.RoadStatus -> RoadStatus(viewState)
        is RoadStatusViewModel.ViewState.ErrorState -> ErrorState(viewState)
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
            horizontalAlignment = CenterHorizontally,
        ) {
            OutlinedTextField(
                value = value,
                onValueChange = { value = it },
                label = { Text(stringResource(id = R.string.road_status_input_label)) },
            )
            Spacer(modifier = Modifier.size(16.dp))
            Button(
                onClick = { onActionSubmitted(value) },
            ) {
                Text(stringResource(id = R.string.road_status_button_submit))
            }
        }
    }
}

@Composable
private fun RoadStatus(viewState: RoadStatusViewModel.ViewState.RoadStatus) {
    Box(
        Modifier
            .fillMaxSize(),
    ) {
        Column(
            Modifier
                .align(alignment = Alignment.Center),
            horizontalAlignment = CenterHorizontally,
        ) {
            Text(
                text = viewState.road.displayName,
                style = MaterialTheme.typography.titleMedium,
            )
            Spacer(modifier = Modifier.size(32.dp))
            Text(
                text = viewState.road.severityStatus,
                style = MaterialTheme.typography.bodyLarge,
            )
            viewState.road.severityStatusDescription?.let { statusDescription ->
                Text(
                    text = statusDescription,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
            Spacer(modifier = Modifier.size(32.dp))
            Button(
                onClick = viewState.searchAgainAction,
            ) {
                Text(stringResource(id = R.string.road_status_button_search_again))
            }
        }
    }
}

@Composable
private fun Loading() {
    LoadingLayout()
}

@Composable
private fun ErrorState(
    viewState: RoadStatusViewModel.ViewState.ErrorState,
) {
    ErrorLayout(
        errorMessage = viewState.errorMessage,
        errorAction = viewState.errorAction,
        errorActionText = stringResource(id = R.string.general_try_again),
    )
}

@Composable
@Preview
fun PreviewInitialData() {
    TflRoadStatusTheme {
        InitialData(onActionSubmitted = {})
    }
}

@Composable
@Preview
fun PreviewRoadStatus() {
    TflRoadStatusTheme {
        RoadStatus(
            RoadStatusViewModel.ViewState.RoadStatus(
                Road(
                    id = "M1",
                    displayName = "M1",
                    severityStatus = "Heavy traffic",
                    severityStatusDescription = "Retention on Junction 2, near St Albans",
                )
            ) {},
        )
    }
}
