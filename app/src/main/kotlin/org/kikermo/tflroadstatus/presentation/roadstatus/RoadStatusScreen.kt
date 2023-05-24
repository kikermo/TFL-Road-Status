package org.kikermo.tflroadstatus.presentation.roadstatus

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.kikermo.tflroadstatus.R
import org.kikermo.tflroadstatus.domain.model.Road
import org.kikermo.tflroadstatus.ui.theme.TflRoadStatusTheme
import org.kikermo.tflroadstatus.ui.views.ErrorLayout
import org.kikermo.tflroadstatus.ui.views.LoadingLayout
import org.kikermo.tflroadstatus.ui.views.SimpleAppBarScaffold

@Composable
internal fun RoadStatusScreen(
    viewModel: RoadStatusViewModel = hiltViewModel(),
    onBackPressed: () -> Unit,
) {
    when (val viewState = viewModel.viewState.collectAsStateWithLifecycle().value) {
        RoadStatusViewModel.ViewState.Loading -> Loading()
        is RoadStatusViewModel.ViewState.RoadStatus -> RoadStatusDetails(viewState, onBackPressed)
        is RoadStatusViewModel.ViewState.ErrorState -> ErrorState(viewState)
    }
}

@Composable
private fun RoadStatusDetails(
    viewState: RoadStatusViewModel.ViewState.RoadStatus,
    onBackPressed: () -> Unit
) {
    SimpleAppBarScaffold(
        title = stringResource(id = R.string.road_status_title),
        backNavigationAction = onBackPressed::invoke
    ) {

        Column(
            Modifier
                .fillMaxSize()
                .padding(top = 64.dp),
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                border = BorderStroke(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline
                )
            ) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = stringResource(R.string.road_status_road_name),
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Text(
                        text = viewState.road.displayName,
                        style = MaterialTheme.typography.titleLarge,
                    )
                    Text(
                        text = stringResource(R.string.road_status_road_status),
                        style = MaterialTheme.typography.titleSmall,
                    )
                    Text(
                        text = viewState.road.severityStatus,
                        style = MaterialTheme.typography.titleMedium,
                    )
                    viewState.road.severityStatusDescription?.let { statusDescription ->
                        Text(
                            text = stringResource(R.string.road_status_road_status_details),
                            style = MaterialTheme.typography.titleSmall,
                        )
                        Text(
                            text = statusDescription,
                            style = MaterialTheme.typography.bodyLarge,
                        )
                    }
                }
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
fun PreviewRoadStatus() {
    TflRoadStatusTheme {
        RoadStatusDetails(
            RoadStatusViewModel.ViewState.RoadStatus(
                Road(
                    id = "M1",
                    displayName = "M1",
                    severityStatus = "Heavy traffic",
                    severityStatusDescription = "Retention on Junction 2, near St Albans",
                )
            )
        ) {}
    }
}
