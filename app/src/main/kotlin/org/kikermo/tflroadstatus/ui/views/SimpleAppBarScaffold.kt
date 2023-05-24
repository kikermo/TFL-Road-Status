package org.kikermo.tflroadstatus.ui.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import org.kikermo.tflroadstatus.R
import org.kikermo.tflroadstatus.ui.theme.TflRoadStatusTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleAppBarScaffold(
    title: String,
    backNavigationAction: () -> Unit,
    content: @Composable (PaddingValues) -> Unit,
) {
    Scaffold(
        topBar = {
            BasicAppBar(title = title, backNavigationAction = backNavigationAction)
        },

        content = content,
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicAppBar(
    title: String,
    backNavigationAction: () -> Unit,
) {
    TopAppBar(
        title = { Text(text = title, style = MaterialTheme.typography.titleLarge) },
        navigationIcon = {
            Image(
                modifier = Modifier.clickable(onClick = backNavigationAction),
                painter = painterResource(id = R.drawable.ic_arrow_left),
                contentDescription = stringResource(id = R.string.general_back)
            )
        }
    )
}

@Composable
@Preview
fun PreviewAppBar() {
    TflRoadStatusTheme {
        BasicAppBar(
            title = "A Title",
            backNavigationAction = {}
        )
    }
}