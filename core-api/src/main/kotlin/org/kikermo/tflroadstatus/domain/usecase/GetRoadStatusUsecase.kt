package org.kikermo.tflroadstatus.domain.usecase

import org.kikermo.tflroadstatus.domain.model.Road
import org.kikermo.tflroadstatus.domain.model.StatusError

interface GetRoadStatusUsecase {
    suspend operator fun invoke(id: String): Status

    sealed class Status {
        data class Success(val road: Road) : Status()
        object RoadNotValid : Status()
        data class Failure(val error: StatusError) : Status()
    }
}
