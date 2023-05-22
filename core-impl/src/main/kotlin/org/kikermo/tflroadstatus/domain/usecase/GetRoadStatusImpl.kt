package org.kikermo.tflroadstatus.domain.usecase

import javax.inject.Inject

internal class GetRoadStatusImpl @Inject constructor() : GetRoadStatus {
    override suspend fun invoke(id: String): GetRoadStatus.Status {
        TODO("Not yet implemented")
    }
}
