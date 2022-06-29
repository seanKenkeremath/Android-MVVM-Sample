package com.seank.kotlinflowplayground.data

import CoroutineTestRule
import com.seank.kotlinflowplayground.data.model.ApiCard
import com.seank.kotlinflowplayground.data.model.ApiCardsResponse
import com.seank.kotlinflowplayground.domain.Card
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class CardsRepositoryImplTest {

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    @MockK
    private lateinit var cardsService: CardsService

    private lateinit var repository: CardsRepositoryImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        repository = CardsRepositoryImpl(cardsService, coroutinesTestRule.testDispatcherProvider)
    }

    @Test
    fun `When getCards is called then domain models returned`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            coEvery {
                cardsService.getCards(any())
            } returns ApiCardsResponse(
                listOf(
                    ApiCard("Test Card", "http://www.image.com"),
                    ApiCard("Test Card 2", "http://www.image.org")
                )
            )
            repository.getCards().collect { result ->
                val list = result.getOrNull()!!
                assert(list.size == 2)
                assert(list[0] == Card(name = "Test Card", imgUrl = "http://www.image.com"))
                assert(list[1] == Card(name = "Test Card 2", imgUrl = "http://www.image.org"))
            }
            coVerify(exactly = 1) {
                cardsService.getCards(any())
            }
        }

    @Test
    fun `Given card service returns empty list, when getCards is called then empty array returned`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            coEvery {
                cardsService.getCards(any())
            } returns ApiCardsResponse(
                emptyList()
            )

            repository.getCards().collect { result ->
                val list = result.getOrNull()!!
                assert(list.isEmpty())
            }
            coVerify(exactly = 1) {
                cardsService.getCards(any())
            }
        }
}