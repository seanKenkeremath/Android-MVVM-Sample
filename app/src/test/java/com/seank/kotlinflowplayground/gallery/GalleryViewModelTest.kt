package com.seank.kotlinflowplayground.gallery

import CoroutineTestRule
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.seank.kotlinflowplayground.data.CardsRepository
import com.seank.kotlinflowplayground.domain.Card
import com.seank.kotlinflowplayground.domain.GenericException
import com.seank.kotlinflowplayground.domain.NetworkException
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GalleryViewModelTest {
    @get:Rule
    val instantTaskExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    @MockK
    private lateinit var cardsRepository: CardsRepository

    private lateinit var viewModel: GalleryViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        every {
            cardsRepository.getCards()
        } returns flow {
            emit(Result.success(listOf(Card(name = "Test Card", imgUrl = "img_url"))))
        }
        viewModel = GalleryViewModel(cardsRepository)
    }

    @Test
    fun `cards load successfully`() {
        viewModel.fetchCard()

        assertEquals(false, viewModel.showLoading.value)
        assertEquals(true, viewModel.showContent.value)
        assertEquals("Test Card", viewModel.cards.value!![0].name)
        assertEquals("img_url", viewModel.cards.value!![0].imgUrl)
    }

    @Test
    fun `show loading true while cards load`() = runTest {
        every {
            cardsRepository.getCards()
        } returns flow {
            delay(1000L)
            emit(Result.success(listOf(Card(name = "Test Card", imgUrl = "img_url"))))
        }
        viewModel.fetchCard()

        //Advance to middle of load
        advanceTimeBy(500L)

        assertEquals(true, viewModel.showLoading.value)
        assertEquals(false, viewModel.showContent.value)

        //Advance past end of load
        advanceTimeBy(1000L)
        assertEquals(false, viewModel.showLoading.value)
        assertEquals(true, viewModel.showContent.value)
        assertEquals("Test Card", viewModel.cards.value!![0].name)
        assertEquals("img_url", viewModel.cards.value!![0].imgUrl)
    }

    @Test
    fun `show network error when cards fail to load from network error`() {
        every {
            cardsRepository.getCards()
        } returns flow {
            emit(Result.failure(NetworkException()))
        }

        viewModel.fetchCard()
        assertEquals(false, viewModel.showLoading.value)
        assertEquals(false, viewModel.showContent.value)
        assertEquals("Network Error", viewModel.error.value)
    }

    @Test
    fun `show generic error when cards fail to load`() {
        every {
            cardsRepository.getCards()
        } returns flow {
            emit(Result.failure(GenericException("Error")))
        }

        viewModel.fetchCard()
        assertEquals(false, viewModel.showLoading.value)
        assertEquals(false, viewModel.showContent.value)
        assertEquals("Generic Error", viewModel.error.value)
    }
}
