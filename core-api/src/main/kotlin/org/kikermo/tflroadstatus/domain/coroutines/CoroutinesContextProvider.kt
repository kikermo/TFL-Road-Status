package org.kikermo.tflroadstatus.domain.coroutines

import kotlin.coroutines.CoroutineContext

interface CoroutinesContextProvider {
    val main: CoroutineContext
    val io: CoroutineContext
}
