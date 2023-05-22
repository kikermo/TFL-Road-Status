package org.kikermo.tflroadstatus.domain.usecase

import org.kikermo.tflroadstatus.domain.model.StatusError
import org.kikermo.tflroadstatus.repository.RoadStatusRepository
import javax.inject.Inject

internal class GetRoadStatusUsecaseImpl @Inject constructor(
    private val repository: RoadStatusRepository,
) : GetRoadStatusUsecase {
    override suspend fun invoke(id: String): GetRoadStatusUsecase.Status {
        val response = repository.getRoadStatus(roadId = id).getOrElse {
            return if (it is StatusError.ResourceUnavailableError) {
                GetRoadStatusUsecase.Status.RoadNotValid
            } else {
                GetRoadStatusUsecase.Status.Failure(it as StatusError)
            }
        }

        return GetRoadStatusUsecase.Status.Success(response)
    }
}
