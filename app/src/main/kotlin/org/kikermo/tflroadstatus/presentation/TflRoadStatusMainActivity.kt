package org.kikermo.tflroadstatus.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import org.kikermo.tflroadstatus.presentation.navigation.TflRoadStatusNavHost
import org.kikermo.tflroadstatus.ui.theme.TflRoadStatusTheme

@AndroidEntryPoint
class TflRoadStatusMainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TflRoadStatusTheme {
                TflRoadStatusNavHost()
            }
        }
    }
}
