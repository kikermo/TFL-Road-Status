package org.kikermo.tflroadstatus.presentation.roadstatus

import io.mockk.coEvery
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

    private val road = Road(
        id = "id",
        displayName = "displayName",
        severityStatus = "severityStatus",
        severityStatusDescription = "severityStatusDescription",
    )

    private lateinit var viewModel: RoadStatusViewModel


    @Before
    fun setup() {
        every { stringProvider.getString(any()) } answers { "" }
    }

    @Test
    fun `when screen starts, show initial status`() {
        // when
        initViewModel()

        // then
        assertIs<RoadStatusViewModel.ViewState.InitialState>(viewModel.viewState.value)
    }

    @Test
    fun `when user submits road name, show loading status`() {
        // given
        initViewModel()
        val initialState = viewModel.viewState.value as RoadStatusViewModel.ViewState.InitialState

        // when
        initialState.onRoadNameSubmitted(road.id)

        // then
        assertIs<RoadStatusViewModel.ViewState.Loading>(viewModel.viewState.value)
    }

    @Test
    fun `when user submits road name and it is successful, show road status`() {
        // given
        initViewModel()
        val initialState = viewModel.viewState.value as RoadStatusViewModel.ViewState.InitialState
        coEvery { getRoadStatusUseCase(any()) } answers { GetRoadStatusUseCase.Status.Success(road) }

        // when
        initialState.onRoadNameSubmitted(road.id)

        // then
        assertIs<RoadStatusViewModel.ViewState.RoadStatus>(viewModel.viewState.value)
    }

    @Test
    fun `when user submits road name, but it doesn't exist, show error status`() {
        // given
        initViewModel()
        val initialState = viewModel.viewState.value as RoadStatusViewModel.ViewState.InitialState
        coEvery { getRoadStatusUseCase(any()) } answers { GetRoadStatusUseCase.Status.RoadNotValid("") }

        // when
        initialState.onRoadNameSubmitted(road.id)

        // then
        assertIs<RoadStatusViewModel.ViewState.ErrorState>(viewModel.viewState.value)
    }

    @Test
    fun `when user submits road name but it fails, show error status`() {
        // given
        initViewModel()
        val initialState = viewModel.viewState.value as RoadStatusViewModel.ViewState.InitialState
        coEvery { getRoadStatusUseCase(any()) } answers { GetRoadStatusUseCase.Status.Failure(StatusError.UnexpectedError) }

        // when
        initialState.onRoadNameSubmitted(road.id)

        // then
        assertIs<RoadStatusViewModel.ViewState.ErrorState>(viewModel.viewState.value)
    }

    @Test
    fun `when road status query fails and user presses try again, initial status`() {
        // given
        initViewModel()
        val initialState = viewModel.viewState.value as RoadStatusViewModel.ViewState.InitialState
        coEvery { getRoadStatusUseCase(any()) } answers { GetRoadStatusUseCase.Status.RoadNotValid("") }
        initialState.onRoadNameSubmitted(road.id)
        val errorState = viewModel.viewState.value as RoadStatusViewModel.ViewState.ErrorState

        // when
        errorState.errorAction()

        // then
        assertIs<RoadStatusViewModel.ViewState.InitialState>(viewModel.viewState.value)
    }

    private fun initViewModel() {
        viewModel = RoadStatusViewModel(
            coroutinesContextProvider = TestCoroutineContextProvider(),
            getRoadStatus = getRoadStatusUseCase,
            stringProvider = stringProvider,
        )
    }
}