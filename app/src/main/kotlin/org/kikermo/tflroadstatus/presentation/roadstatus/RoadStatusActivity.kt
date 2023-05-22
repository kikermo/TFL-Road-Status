package org.kikermo.tflroadstatus.presentation.roadstatus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import org.kikermo.tflroadstatus.ui.theme.TflRoadStatusTheme

@AndroidEntryPoint
class RoadStatusActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TflRoadStatusTheme {
                RoadStatusScreen()
            }
        }
    }
}