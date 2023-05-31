package org.kikermo.tflroadstatus.presentation.search

import com.appmattus.kotlinfixture.kotlinFixture
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

class SearchViewModelTest {
    private val getRoadStatusUseCase: GetRoadStatusUseCase = mockk()
    private val stringProvider: StringProvider = mockk()

    private val fixture = kotlinFixture()
    private val road = fixture<Road>()

    private lateinit var viewModel: SearchViewModel


    @Before
    fun setup() {
        every { stringProvider.getString(any()) } answers { "" }
    }

    @Test
    fun `when screen starts, show initial status`() {
        // when
        initViewModel()

        // then
        assertIs<SearchViewModel.ViewState.InitialState>(viewModel.viewState.value)
    }

    @Test
    fun `when user submits road name, show loading status`() {
        // given
        initViewModel()
        val initialState = viewModel.viewState.value as SearchViewModel.ViewState.InitialState

        // when
        initialState.findRoadAction(road.id)

        // then
        assertIs<SearchViewModel.ViewState.Loading>(viewModel.viewState.value)
    }

    @Test
    fun `when user submits road name and it is successful, show road status`() {
        // given
        initViewModel()
        val initialState = viewModel.viewState.value as SearchViewModel.ViewState.InitialState
        coEvery { getRoadStatusUseCase(any()) } answers { GetRoadStatusUseCase.Status.Success(road) }

        // when
        initialState.findRoadAction(road.id)

        // then
        assertIs<SearchViewModel.ViewState.RoadStatus>(viewModel.viewState.value)
    }

    @Test
    fun `when user submits road name, but it doesn't exist, show error status`() {
        // given
        initViewModel()
        val initialState = viewModel.viewState.value as SearchViewModel.ViewState.InitialState
        coEvery { getRoadStatusUseCase(any()) } answers { GetRoadStatusUseCase.Status.RoadNotValid("") }

        // when
        initialState.findRoadAction(road.id)

        // then
        assertIs<SearchViewModel.ViewState.ErrorState>(viewModel.viewState.value)
    }

    @Test
    fun `when user submits road name but it fails, show error status`() {
        // given
        initViewModel()
        val initialState = viewModel.viewState.value as SearchViewModel.ViewState.InitialState
        coEvery { getRoadStatusUseCase(any()) } answers { GetRoadStatusUseCase.Status.Failure(StatusError.UnexpectedError) }

        // when
        initialState.findRoadAction(road.id)

        // then
        assertIs<SearchViewModel.ViewState.ErrorState>(viewModel.viewState.value)
    }

    @Test
    fun `when road status query fails and user presses try again, initial status`() {
        // given
        initViewModel()
        val initialState = viewModel.viewState.value as SearchViewModel.ViewState.InitialState
        coEvery { getRoadStatusUseCase(any()) } answers { GetRoadStatusUseCase.Status.RoadNotValid("") }
        initialState.findRoadAction(road.id)
        val errorState = viewModel.viewState.value as SearchViewModel.ViewState.ErrorState

        // when
        errorState.errorAction()

        // then
        assertIs<SearchViewModel.ViewState.InitialState>(viewModel.viewState.value)
    }

    private fun initViewModel() {
        viewModel = SearchViewModel(
            coroutinesContextProvider = TestCoroutineContextProvider(),
            getRoadStatus = getRoadStatusUseCase,
            stringProvider = stringProvider,
        )
    }
}
