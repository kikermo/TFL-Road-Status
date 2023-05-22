package org.kikermo.tflroadstatus.network.mapper

import org.kikermo.tflroadstatus.domain.model.Road
import org.kikermo.tflroadstatus.network.model.RoadResponse
import javax.inject.Inject

internal class RoadDomainMapper @Inject constructor() {
    fun map(response: RoadResponse): Road? {
        if (
            response.id == null ||
            response.displayName == null ||
            response.statusSeverity == null
        ) {
            return null
        }

        return Road(
            id = response.id,
            displayName = response.displayName,
            severityStatus = response.statusSeverity,
            severityStatusDescription = response.statusSeverityDescription,
        )
    }
}
