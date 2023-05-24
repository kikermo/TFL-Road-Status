package org.kikermo.tflroadstatus.presentation.search

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Card
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
import androidx.compose.ui.res.painterResource
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

@Composable
internal fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    statusDetailsNavigation: (String) -> Unit,
) {
    when (val viewState = viewModel.viewState.collectAsStateWithLifecycle().value) {
        SearchViewModel.ViewState.Loading -> Loading()
        is SearchViewModel.ViewState.ErrorState -> ErrorState(viewState)
        is SearchViewModel.ViewState.InitialState -> InitialState(viewState)
        is SearchViewModel.ViewState.RoadStatus -> RoadStatus(
            viewState = viewState,
            statusDetailsNavigation = statusDetailsNavigation,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun InitialState(
    viewState: SearchViewModel.ViewState.InitialState,
) {
    Box(
        Modifier
            .fillMaxSize(),
    ) {
        Column(
            Modifier
                .align(alignment = Alignment.Center),
            horizontalAlignment = CenterHorizontally,
        ) {
            Search(searchAction = viewState.findRoadAction)
        }
    }
}

@Composable
private fun RoadStatus(
    viewState: SearchViewModel.ViewState.RoadStatus,
    statusDetailsNavigation: (String) -> Unit,
) {
    Box(
        Modifier
            .fillMaxSize(),
    ) {
        Column(
            Modifier
                .align(alignment = Alignment.Center),
            horizontalAlignment = CenterHorizontally,
        ) {
            Search(searchAction = viewState.findRoadAction)
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable { statusDetailsNavigation(viewState.road.id) },
                border = BorderStroke(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline
                )
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
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
                    Spacer(modifier = Modifier.weight(1f))
                    Image(
                        painter = painterResource(id = R.drawable.ic_chevron_right),
                        contentDescription = stringResource(
                            id = R.string.road_status_navigation_details
                        )
                    )
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
    viewState: SearchViewModel.ViewState.ErrorState,
) {
    ErrorLayout(
        errorMessage = viewState.errorMessage,
        errorAction = viewState.errorAction,
        errorActionText = stringResource(id = R.string.general_try_again),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Search(searchAction: (String) -> Unit) {
    var value: String by remember { mutableStateOf("") }

    Row(
        Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        OutlinedTextField(
            modifier = Modifier.weight(1f),
            value = value,
            onValueChange = { value = it },
            label = { Text(stringResource(id = R.string.road_status_input_label)) },
        )
        Spacer(modifier = Modifier.size(8.dp))
        Button(
            enabled = value.isNotBlank(),
            onClick = { searchAction(value) },
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_magnifier),
                contentDescription = stringResource(
                    id = R.string.road_status_button_submit
                )
            )
        }
    }
}


@Composable
@Preview
fun PreviewInitialData() {
    TflRoadStatusTheme {
        InitialState(SearchViewModel.ViewState.InitialState {})
    }
}

@Composable
@Preview
fun PreviewRoadStatus() {
    TflRoadStatusTheme {
        RoadStatus(
            SearchViewModel.ViewState.RoadStatus(
                Road(
                    id = "M1",
                    displayName = "M1",
                    severityStatus = "Heavy traffic",
                    severityStatusDescription = "Retention on Junction 2, near St Albans",
                )
            ) {}
        ) {}
    }
}
