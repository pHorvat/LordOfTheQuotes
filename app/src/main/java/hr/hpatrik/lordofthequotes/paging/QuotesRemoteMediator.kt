package hr.hpatrik.lordofthequotes.paging

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import hr.hpatrik.lordofthequotes.api.QuotesApi
import hr.hpatrik.lordofthequotes.db.QuotesDatabase
import hr.hpatrik.lordofthequotes.model.Quote
import hr.hpatrik.lordofthequotes.model.QuoteRemoteKeys
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import java.net.URLDecoder
import java.text.Normalizer

@ExperimentalPagingApi
class QuotesRemoteMediator(
    private val quotesApi: QuotesApi,
    private val quoteDatabase: QuotesDatabase,
) : RemoteMediator<Int, Quote>() {
    private val quoteDao = quoteDatabase.quotesDao()
    private val quoteRemoteKeysDao = quoteDatabase.quoteRemoteKeysDao()

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Quote>): MediatorResult {
        Log.d("MEDIATOR", loadType.toString())

        return try {

            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    val quoteRemoteKeys: QuoteRemoteKeys? =
                        getQuoteRemoteKeysClosestToCurrentPosition(state)
                    quoteRemoteKeys?.nextPage?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    val quoteRemoteKeys: QuoteRemoteKeys? = getQuoteRemoteKeysForFirstItem(state)
                    val prevPage = quoteRemoteKeys?.prevPage
                        ?: return MediatorResult.Success(quoteRemoteKeys != null)
                    prevPage
                }
                LoadType.APPEND -> {
                    val quoteRemoteKeys: QuoteRemoteKeys? = getQuoteRemoteKeysForLastItem(state)
                    val nextPage = quoteRemoteKeys?.nextPage
                        ?: return MediatorResult.Success(quoteRemoteKeys != null)
                    nextPage
                }
            }

            val response = quotesApi.getQuotes(page = currentPage)
            val endOfPaginationReached = response.quotes.isEmpty()

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            for (q in response.quotes) {
                val response2 = quotesApi.getCharacters(id = q.character)
                val response3 = quotesApi.getMovie(id = q.movie)
                for (c in response2.character) {
                    q.death = c.death
                    q.birth = c.birth
                    q.hair = c.hair
                    q.height = c.height
                    q.race = c.race
                    q.realm = c.realm
                    q.gender = c.gender
                    q.spouse = c.spouse
                    q.name = c.name
                    try {
                        val imageURL = getOpenGraphImage(c.wikiUrl)
                        if (imageURL != null) {
                            q.wikiUrl = imageURL
                        }
                        Log.d("wikiUrl:", q.wikiUrl)
                        Log.d("imageURL:", imageURL.toString())

                    } catch (e: Exception) {
                        Log.d("wikiUrlERROR:", e.toString())
                        q.wikiUrl =
                            "https://static.wikia.nocookie.net/lotr/images/7/79/Slider_-_One_Ring.jpg/revision/latest/scale-to-width-down/670?cb=20150328112455"
                    }

                }
                for (m in response3.movie) {
                    q.movieName = m.name
                    q.runtimeInMinutes = m.runtimeInMinutes
                    q.budgetInMillions = m.budgetInMillions
                    q.boxOfficeRevenueInMillions = m.boxOfficeRevenueInMillions
                    q.academyAwardWins = m.academyAwardWins
                    q.academyAwardNominations = m.academyAwardNominations
                    q.rottenTomatoesScore = m.rottenTomatoesScore
                }

            }

            quoteDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    quoteDao.deleteQuotes()
                    quoteRemoteKeysDao.deleteQuoteRemoteKeys()
                }
                val quoteRemoteKeys = response.quotes.map { quote ->
                    QuoteRemoteKeys(
                        id = quote.id,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }

                quoteRemoteKeysDao.addQuoteRemoteKeys(quoteRemoteKeys = quoteRemoteKeys)
                quoteDao.addQuotes(quotes = response.quotes)
            }

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            Log.d("MEDIATOR_ERROR", e.toString())
            MediatorResult.Error(e)
        }
    }

    private suspend fun getQuoteRemoteKeysForFirstItem(state: PagingState<Int, Quote>): QuoteRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { quote ->
            quoteRemoteKeysDao.getQuoteRemoteKeys(id = quote.id)
        }
    }

    private suspend fun getQuoteRemoteKeysForLastItem(state: PagingState<Int, Quote>): QuoteRemoteKeys? {
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { quote ->
            quoteRemoteKeysDao.getQuoteRemoteKeys(id = quote.id)
        }
    }

    private suspend fun getQuoteRemoteKeysClosestToCurrentPosition(state: PagingState<Int, Quote>): QuoteRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                quoteRemoteKeysDao.getQuoteRemoteKeys(id = id)
            }
        }
    }

    suspend fun getOpenGraphImage(url: String): String? {
        url.addCharAtIndex('s', 4)
        val decoded1 = URLDecoder.decode(url, "UTF-8")
        val decoded2 = URLDecoder.decode(decoded1, "UTF-8")
        return withContext(Dispatchers.IO) {
            try {
                Log.d("cleanURL:", decoded2)
                val doc = Jsoup.connect(decoded2)
                    .followRedirects(false)
                    .header("Content-Type", "text/html; charset=utf-8")
                    .get()
                val metaOgImage = doc.select("meta[property=og:image]").first()
                return@withContext metaOgImage?.attr("content")
            } catch (e: Exception) {
                return@withContext e.toString()
            }
        }
    }


    fun removeDiacritics(input: String): String {
        val normalizedString = Normalizer.normalize(input, Normalizer.Form.NFD)
        val pattern = "\\p{InCombiningDiacriticalMarks}+".toRegex()
        return pattern.replace(normalizedString, "").replace("ß", "ss").replace("Ë", "E")
            .replace("Ü", "U")
    }

    fun String.addCharAtIndex(char: Char, index: Int) =
        StringBuilder(this).apply { insert(index, char) }.toString()
}