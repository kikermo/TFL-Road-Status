package org.kikermo.tflroadstatus.network

import org.kikermo.tflroadstatus.network.model.RoadResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface RoadStatusService {

    @GET("/Road/{roadId}")
    suspend fun getRoadStatus(
        @Path("roadId") roadId: String,
    ): Result<RoadResponse>
}