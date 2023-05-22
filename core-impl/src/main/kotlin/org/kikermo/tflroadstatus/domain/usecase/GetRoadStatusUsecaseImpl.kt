package org.kikermo.tflroadstatus.domain.usecase

import javax.inject.Inject

internal class GetRoadStatusUsecaseImpl @Inject constructor() : GetRoadStatusUsecase {
    override suspend fun invoke(id: String): GetRoadStatusUsecase.Status {
        TODO("Not yet implemented")
    }
}
