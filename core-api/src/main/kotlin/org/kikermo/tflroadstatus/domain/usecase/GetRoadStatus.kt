package org.kikermo.tflroadstatus.domain.usecase

import org.kikermo.tflroadstatus.domain.model.Road

interface GetRoadStatus {
    suspend operator fun invoke(id: String):Status

    sealed class Status {
        data class Success(val road: Road):Status()
        object RoadNotValid: Status()
        object Failure: Status()
    }
}