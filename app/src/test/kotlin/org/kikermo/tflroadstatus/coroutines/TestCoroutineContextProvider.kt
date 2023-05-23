package org.kikermo.tflroadstatus.coroutines

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.kikermo.tflroadstatus.domain.coroutines.CoroutinesContextProvider
import kotlin.coroutines.CoroutineContext

@OptIn(ExperimentalCoroutinesApi::class)
class TestCoroutineContextProvider : CoroutinesContextProvider {
    private val testDispatcher = UnconfinedTestDispatcher()

    override val main: CoroutineContext = testDispatcher
    override val io: CoroutineContext = testDispatcher
}