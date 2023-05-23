package org.kikermo.tflroadstatus.repository

import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import okio.IOException
import org.junit.Test
import org.kikermo.tflroadstatus.domain.model.Road
import org.kikermo.tflroadstatus.domain.model.StatusError
import org.kikermo.tflroadstatus.network.RoadStatusService
import org.kikermo.tflroadstatus.network.mapper.RoadDomainMapper
import org.kikermo.tflroadstatus.network.model.RoadResponse
import kotlin.test.assertIs
import kotlin.test.assertTrue

class RoadStatusRepositoryTest {
    private val mapper: RoadDomainMapper = mockk()
    private val service: RoadStatusService = mockk()

    private val repository = RoadStatusRepository(
        mapper = mapper,
        service = service,
    )

    private val roadResponse = RoadResponse(
        id = "id",
        displayName = "displayName",
        statusSeverity = "statusSeverity",
        statusSeverityDescription = "statusSeverityDescription",
        bounds = "bounds",
        envelope = "envelope",
        url = "url",
    )

    private val road = Road(
        id = "id",
        displayName = "displayName",
        severityStatus = "severityStatus",
        severityStatusDescription = "severityStatusDescription",
    )

    @Test
    fun `when service returns a successful and mapper succeeds, return successful result`() = runTest {
        // given
        coEvery { service.getRoadStatus(any()) } coAnswers { Result.success(listOf(roadResponse)) }
        every { mapper.map(any()) } answers { road }

        // when
        val result = repository.getRoadStatus(road.id)

        // then
        assertTrue(result.isSuccess)
    }

    @Test
    fun `when service returns a successful but mapper fails, return error result`() = runTest {
        // given
        coEvery { service.getRoadStatus(any()) } coAnswers { Result.success(listOf(roadResponse)) }
        every { mapper.map(any()) } answers { null }

        // when
        val result = repository.getRoadStatus(road.id)

        // then
        assertTrue(result.isFailure)
    }

    @Test
    fun `when service returns a successful but list is empty, return error result`() = runTest {
        // given
        coEvery { service.getRoadStatus(any()) } coAnswers { Result.success(listOf()) }
        every { mapper.map(any()) } answers { road }

        // when
        val result = repository.getRoadStatus(road.id)

        // then
        assertTrue(result.isFailure)
    }

    @Test
    fun `when service returns an error due to internet connection, return error result`() = runTest {
        // given
        coEvery { service.getRoadStatus(any()) } coAnswers { Result.failure(IOException()) }

        // when
        val result = repository.getRoadStatus(road.id)

        // then
        assertTrue(result.isFailure)
        assertIs<StatusError.NoInternetConnectionError>(result.exceptionOrNull())
    }
}