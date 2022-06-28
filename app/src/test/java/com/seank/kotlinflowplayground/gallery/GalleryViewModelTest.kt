package com.seank.kotlinflowplayground.gallery

import CoroutineTestRule
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.seank.kotlinflowplayground.data.CardsRepository
import com.seank.kotlinflowplayground.domain.Card
import kotlinx.coroutines.flow.flow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations.initMocks
import org.mockito.MockitoAnnotations.openMocks

class GalleryViewModelTest {
    @get:Rule
    val instantTaskExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    @Mock
    private lateinit var cardsRepository: CardsRepository

    @Mock
    private lateinit var cardsObserver: Observer<List<Card>>

    @Mock
    private lateinit var showContentObserver: Observer<Boolean>

    private lateinit var viewModel: GalleryViewModel

    @Before
    fun setUp() {
        openMocks(this)
        `when`(cardsRepository.getCards()).thenReturn(flow {
            emit(listOf(Card(name = "Test Card", imgUrl = "img_url")))
        })
        viewModel = GalleryViewModel(cardsRepository)
        viewModel.cards.observeForever(cardsObserver)
        viewModel.showContent.observeForever(showContentObserver)
    }

    @Test
    fun `when fetchCard then loading set to false then true when content loads`() {
        viewModel.fetchCard()
        val showContentArgumentCaptor = argumentCaptor<Boolean>()
        verify(showContentObserver, atLeastOnce()).onChanged(showContentArgumentCaptor.capture())
        assert(showContentArgumentCaptor.allValues[0] == false)
        assert(showContentArgumentCaptor.allValues[1] == true)

        val cardsArgumentCaptor = argumentCaptor<List<Card>>()
        verify(cardsObserver).onChanged(cardsArgumentCaptor.capture())
        assert(cardsArgumentCaptor.allValues.size == 1)
        assert(cardsArgumentCaptor.allValues[0].size == 1)
        assert(cardsArgumentCaptor.lastValue[0] == Card(name = "Test Card", imgUrl = "img_url"))
    }
}
