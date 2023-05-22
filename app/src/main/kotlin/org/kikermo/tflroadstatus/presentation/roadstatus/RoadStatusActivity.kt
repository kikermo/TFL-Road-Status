package org.kikermo.tflroadstatus.presentation.roadstatus

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import org.kikermo.tflroadstatus.ui.theme.TflRoadStatusTheme

@AndroidEntryPoint
class RoadStatusActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TflRoadStatusTheme {
                RoadStatusScreen()
            }
        }
    }
}