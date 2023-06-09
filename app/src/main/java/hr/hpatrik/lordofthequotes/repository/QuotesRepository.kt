package hr.hpatrik.lordofthequotes.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import hr.hpatrik.lordofthequotes.api.QuotesApi
import hr.hpatrik.lordofthequotes.db.QuotesDatabase
import hr.hpatrik.lordofthequotes.model.Quote
import hr.hpatrik.lordofthequotes.paging.QuotesRemoteMediator
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalPagingApi
class QuotesRepository @Inject constructor(
    private val quotesApi: QuotesApi,
    private val quotesDatabase: QuotesDatabase,
) {
    fun getQuotes(): Flow<PagingData<Quote>> {
        val pagingSource = { quotesDatabase.quotesDao().getQuotes() }

        return Pager(
            config = PagingConfig(pageSize = 5),
            remoteMediator = QuotesRemoteMediator(
                quotesApi,
                quotesDatabase,
            ),
            pagingSourceFactory = pagingSource
        ).flow

    }
}