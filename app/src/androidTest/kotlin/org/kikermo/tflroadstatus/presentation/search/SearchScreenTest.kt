package org.kikermo.tflroadstatus.presentation.search

import androidx.compose.runtime.Composable
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.kikermo.tflroadstatus.ui.theme.TflRoadStatusTheme

@RunWith(AndroidJUnit4::class)
class SearchScreenTest {
    @get: Rule
    val composeTestRule = createComposeRule()


    @Test
    fun verify_initial_state() {
        // given
        val initialState = SearchViewModel.ViewState.InitialState {}

        // when
        setContent { InitialState(initialState) }

        // then
        composeTestRule.onNodeWithText("Road Name").assertExists()
    }

    private fun setContent(
        content: @Composable () -> Unit
    ) {
        composeTestRule.setContent {
            TflRoadStatusTheme {
                content()
            }
        }
    }
}