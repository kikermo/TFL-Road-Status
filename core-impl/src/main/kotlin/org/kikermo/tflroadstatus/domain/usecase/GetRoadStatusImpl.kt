package org.kikermo.tflroadstatus.domain.usecase

internal class GetRoadStatusImpl @Inject constructor(): GetRoadStatus {
    override suspend fun invoke(id: String): GetRoadStatus.Status {
        TODO("Not yet implemented")
    }
}