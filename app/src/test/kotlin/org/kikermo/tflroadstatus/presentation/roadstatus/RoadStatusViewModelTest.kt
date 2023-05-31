package org.kikermo.tflroadstatus.presentation.roadstatus

import androidx.lifecycle.SavedStateHandle
import com.appmattus.kotlinfixture.kotlinFixture
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Test
import org.kikermo.tflroadstatus.coroutines.TestCoroutineContextProvider
import org.kikermo.tflroadstatus.domain.model.Road
import org.kikermo.tflroadstatus.domain.model.StatusError
import org.kikermo.tflroadstatus.domain.usecase.GetRoadStatusUseCase
import org.kikermo.tflroadstatus.ui.utils.StringProvider
import kotlin.test.assertIs

class RoadStatusViewModelTest {
    private val getRoadStatusUseCase: GetRoadStatusUseCase = mockk()
    private val stringProvider: StringProvider = mockk()
    private val savedStateHandle: SavedStateHandle = mockk()

    private val fixture = kotlinFixture()
    private val road = fixture<Road>()

    private lateinit var viewModel: RoadStatusViewModel


    @Before
    fun setup() {
        every { stringProvider.getString(any()) } answers { "A string" }
        every { savedStateHandle.get<String>(any()) } answers { "roadId" }
    }

    @Test
    fun `when screen starts, shows loading status`() {
        // when
        initViewModel()

        // then
        assertIs<RoadStatusViewModel.ViewState.Loading>(viewModel.viewState.value)
    }

    @Test
    fun `when road loads successfully, show road status`() {
        // given
        coEvery { getRoadStatusUseCase(any()) } answers { GetRoadStatusUseCase.Status.Success(road) }

        // when
        initViewModel()

        // then
        assertIs<RoadStatusViewModel.ViewState.RoadStatus>(viewModel.viewState.value)
    }

    @Test
    fun `when road doesn't exist, show error status`() {
        // given
        coEvery { getRoadStatusUseCase(any()) } answers { GetRoadStatusUseCase.Status.RoadNotValid("") }

        // when
        initViewModel()

        // then
        assertIs<RoadStatusViewModel.ViewState.ErrorState>(viewModel.viewState.value)
    }

    @Test
    fun `when there is a generic error loading road, show error status`() {
        // given
        coEvery { getRoadStatusUseCase(any()) } answers {
            GetRoadStatusUseCase.Status.Failure(
                StatusError.UnexpectedError
            )
        }

        // when
        initViewModel()

        // then
        assertIs<RoadStatusViewModel.ViewState.ErrorState>(viewModel.viewState.value)
    }

    @Test
    fun `when road status query fails and user presses try again, load road again`() {
        // given
        coEvery { getRoadStatusUseCase(any()) } answers {
            GetRoadStatusUseCase.Status.Failure(
                StatusError.UnexpectedError
            )
        }
        initViewModel()
        val errorState = viewModel.viewState.value as RoadStatusViewModel.ViewState.ErrorState

        // when
        errorState.errorAction()

        // then
        coVerify(exactly = 2) { getRoadStatusUseCase(any()) }
    }

    private fun initViewModel() {
        viewModel = RoadStatusViewModel(
            coroutinesContextProvider = TestCoroutineContextProvider(),
            getRoadStatus = getRoadStatusUseCase,
            stringProvider = stringProvider,
            savedStateHandle = savedStateHandle,
        )
    }
}