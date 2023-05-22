package org.kikermo.tflroadstatus.domain.usecase

import org.kikermo.tflroadstatus.domain.model.StatusError
import org.kikermo.tflroadstatus.repository.RoadStatusRepository
import javax.inject.Inject

internal class GetRoadStatusUseCaseImpl @Inject constructor(
    private val repository: RoadStatusRepository,
) : GetRoadStatusUseCase {
    override suspend fun invoke(id: String): GetRoadStatusUseCase.Status {
        val response = repository.getRoadStatus(roadId = id).getOrElse {
            return if (it is StatusError.ResourceUnavailableError) {
                GetRoadStatusUseCase.Status.RoadNotValid
            } else {
                GetRoadStatusUseCase.Status.Failure(it as StatusError)
            }
        }

        return GetRoadStatusUseCase.Status.Success(response)
    }
}
