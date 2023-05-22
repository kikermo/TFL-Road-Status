package org.kikermo.tflroadstatus.repository

import org.kikermo.tflroadstatus.domain.model.Road
import org.kikermo.tflroadstatus.domain.model.StatusError
import org.kikermo.tflroadstatus.network.RoadStatusService
import org.kikermo.tflroadstatus.network.mapper.RoadDomainMapper
import org.kikermo.tflroadstatus.network.mapper.toStatusError
import javax.inject.Inject

internal class RoadStatusRepository @Inject constructor(
    private val service: RoadStatusService,
    private val mapper: RoadDomainMapper,
) {
    suspend fun getRoadStatus(roadId: String): Result<Road> {
        val networkResponse = service
            .getRoadStatus(roadId)
            .getOrElse {
                return Result.failure(it.toStatusError())
            }

        val road = mapper.map(networkResponse) ?: return Result.failure(StatusError.UnexpectedError)
        return Result.success(road)
    }
}
