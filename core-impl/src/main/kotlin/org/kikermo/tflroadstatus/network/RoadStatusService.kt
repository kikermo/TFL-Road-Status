package org.kikermo.tflroadstatus.network

import org.kikermo.tflroadstatus.network.model.RoadResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RoadStatusService {

    @GET("/Road/{roadId}")
    suspend fun getRoadStatus(
        @Path("roadId") roadId: String,
        @Query("api_key") apiKey: String, // TODO And an interceptor to inject the api key
    ): Result<RoadResponse>
}