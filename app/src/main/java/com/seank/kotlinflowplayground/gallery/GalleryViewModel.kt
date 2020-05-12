package com.seank.kotlinflowplayground.gallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seank.kotlinflowplayground.data.CardsRepository
import com.seank.kotlinflowplayground.domain.Card
import com.seank.kotlinflowplayground.util.test.idlingresource.BackgroundTaskIdlingResource
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class GalleryViewModel(private val repository: CardsRepository) : ViewModel() {

    private val _cards = MutableLiveData<List<Card>>()
    val cards: LiveData<List<Card>> get() = _cards

    private val _showContent = MutableLiveData<Boolean>()
    val showContent: LiveData<Boolean> get() = _showContent

    private val _showLoading = MutableLiveData<Boolean>()
    val showLoading: LiveData<Boolean> get() = _showLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    fun fetchCard() {
        BackgroundTaskIdlingResource.taskStarted()
        viewModelScope.launch {
            repository.getCards()
                .onStart {
                    _showContent.value = false
                    _showLoading.value = true
                    _error.value = null
                }
                .catch { exception ->
                    _showContent.value = false
                    _showLoading.value = false
                    _error.value = exception.message
                }
                .onCompletion {
                    BackgroundTaskIdlingResource.taskEnded()
                }
                .collect {
                    _showContent.value = true
                    _showLoading.value = false
                    _error.value = null
                    _cards.value = it
                }
        }
    }
}