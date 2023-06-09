package hr.hpatrik.lordofthequotes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.paging.ExperimentalPagingApi
import dagger.hilt.android.lifecycle.HiltViewModel
import hr.hpatrik.lordofthequotes.repository.QuotesRepository
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class QuotesViewModel @Inject constructor(
    repository: QuotesRepository
): ViewModel() {
    val quotes = repository.getQuotes()
}