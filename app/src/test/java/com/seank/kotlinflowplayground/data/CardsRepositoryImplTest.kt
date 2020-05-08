package com.seank.kotlinflowplayground.data

import CoroutineTestRule
import com.seank.kotlinflowplayground.data.model.ApiCard
import com.seank.kotlinflowplayground.data.model.ApiCardsResponse
import com.seank.kotlinflowplayground.domain.Card
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations.initMocks


class CardsRepositoryImplTest {

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    @Mock
    private lateinit var cardsService: CardsService

    private lateinit var repository: CardsRepositoryImpl

    @Before
    fun setUp() {
        initMocks(this)
        repository = CardsRepositoryImpl(cardsService, coroutinesTestRule.testDispatcherProvider)
    }

    @Test
    fun `When getCards is called then domain models returned`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            `when`(cardsService.getCards(ArgumentMatchers.any())).thenReturn(
                ApiCardsResponse(
                    listOf(
                        ApiCard("Test Card", "http://www.image.com"),
                        ApiCard("Test Card 2", "http://www.image.org")
                    )
                )
            )

            repository.getCards().collect { list ->
                assert(list.size == 2)
                assert(list[0] == Card(name = "Test Card", imgUrl = "http://www.image.com"))
                assert(list[1] == Card(name = "Test Card 2", imgUrl = "http://www.image.org"))
            }
            verify(cardsService).getCards(ArgumentMatchers.any())
        }

    @Test
    fun `Given card service returns empty list, when getCards is called then empty array returned`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            `when`(cardsService.getCards(ArgumentMatchers.any())).thenReturn(
                ApiCardsResponse(
                    emptyList()
                )
            )

            repository.getCards().collect { list ->
                assert(list.isEmpty())
            }
            verify(cardsService).getCards(ArgumentMatchers.any())
        }

}