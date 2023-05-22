package org.kikermo.tflroadstatus.coroutines

import kotlinx.coroutines.Dispatchers
import org.kikermo.tflroadstatus.domain.coroutines.CoroutinesContextProvider
import javax.inject.Inject

internal class AppCoroutinesContextProvider @Inject constructor() : CoroutinesContextProvider {
    override val main = Dispatchers.Main
    override val io = Dispatchers.IO
}
