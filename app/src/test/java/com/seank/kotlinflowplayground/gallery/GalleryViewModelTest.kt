package com.seank.kotlinflowplayground.gallery

import CoroutineTestRule
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.seank.kotlinflowplayground.data.CardsRepository
import com.seank.kotlinflowplayground.domain.Card
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.flow
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
            emit(listOf(Card(name = "Test Card", imgUrl = "img_url")))
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
    fun `show loading true while cards load`() {
        viewModel.fetchCard()

        assertEquals(false, viewModel.showLoading.value)
        assertEquals(true, viewModel.showContent.value)
        assertEquals("Test Card", viewModel.cards.value!![0].name)
        assertEquals("img_url", viewModel.cards.value!![0].imgUrl)
    }
}
