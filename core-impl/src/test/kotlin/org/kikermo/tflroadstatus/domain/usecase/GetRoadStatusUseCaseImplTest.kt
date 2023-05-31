package org.kikermo.tflroadstatus.domain.usecase

import com.appmattus.kotlinfixture.kotlinFixture
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.kikermo.tflroadstatus.domain.model.Road
import org.kikermo.tflroadstatus.domain.model.StatusError
import org.kikermo.tflroadstatus.repository.RoadStatusRepository
import kotlin.test.assertIs

class GetRoadStatusUseCaseImplTest {
    private val repository: RoadStatusRepository = mockk()

    private val useCase = GetRoadStatusUseCaseImpl(repository)

    private val fixture = kotlinFixture()
    private val road = fixture<Road>()

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun `when road status is fetched successfully, return Success Status`() = runTest {
        // given
        coEvery { repository.getRoadStatus(any()) } coAnswers { Result.success(road) }

        // when
        val status = useCase(road.id)

        // then
        assertIs<GetRoadStatusUseCase.Status.Success>(status)
    }

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun `when road status fails due to internet connection, return Error Status`() = runTest {
        // given
        coEvery { repository.getRoadStatus(any()) } coAnswers { Result.failure(StatusError.NoInternetConnectionError) }

        // when
        val status = useCase(road.id)

        // then
        assertIs<GetRoadStatusUseCase.Status.Failure>(status)
    }

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun `when road status fails due to unexpected error, return Failure Status`() = runTest {
        // given
        coEvery { repository.getRoadStatus(any()) } coAnswers { Result.failure(StatusError.UnexpectedError) }

        // when
        val status = useCase(road.id)

        // then
        assertIs<GetRoadStatusUseCase.Status.Failure>(status)
    }

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun `when road status fails due to not road not found, return RoadNotValid Status`() = runTest {
        // given
        coEvery { repository.getRoadStatus(any()) } coAnswers { Result.failure(StatusError.ResourceUnavailableError("")) }

        // when
        val status = useCase(road.id)

        // then
        assertIs<GetRoadStatusUseCase.Status.RoadNotValid>(status)
    }
}