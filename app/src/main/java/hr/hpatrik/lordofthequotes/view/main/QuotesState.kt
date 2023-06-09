package hr.hpatrik.lordofthequotes.view.main

import androidx.paging.ExperimentalPagingApi
import hr.hpatrik.lordofthequotes.viewmodel.QuotesViewModel

@ExperimentalPagingApi
class QuotesState (quotesViewModel: QuotesViewModel){
    val quotes = quotesViewModel.quotes
}