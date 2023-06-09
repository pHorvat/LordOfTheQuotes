package hr.hpatrik.lordofthequotes.view.main

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontStyle.Companion.Italic
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.ExperimentalPagingApi
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import hr.hpatrik.lordofthequotes.model.Quote
import hr.hpatrik.lordofthequotes.ui.theme.halfelven
import hr.hpatrik.lordofthequotes.ui.theme.middleearth
import hr.hpatrik.lordofthequotes.ui.theme.ringbearer

@ExperimentalPagingApi
@Composable
fun QuotesScreen(
    modifier: Modifier = Modifier,
    quotesState: QuotesState
) {
    val quotes = quotesState.quotes.collectAsLazyPagingItems()

    Box(Modifier.padding(bottom = 40.dp)) {
        LazyColumn(
            modifier = modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(all = 12.dp),
        ) {
            items(
                items = quotes,
                key = { quote -> quote.id }
            ) { quote ->
                quote?.let { QuoteItem(modifier, quote = it) }

            }

        }

    }

}


@OptIn(ExperimentalTextApi::class, ExperimentalMaterialApi::class, ExperimentalAnimationApi::class)
@Composable
fun QuoteItem(modifier: Modifier, quote: Quote) {
    val gradientColors = listOf(Color(0xFFFBDF7A), Color(0xFFFBC841), Color(0xFFDC824F))
    val state = remember {
        MutableTransitionState(false).apply {
            targetState = true
        }
    }

    AnimatedVisibility(
        visibleState = state,
        enter = fadeIn() + slideInHorizontally(
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessMedium
            )
        )
    ) {

        Card(
            backgroundColor = Color(0xFF5C5C5C),
            shape = CutCornerShape(topStart = 16.dp, bottomEnd = 16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = quote.dialog,

                    style = TextStyle(
                        brush = Brush.linearGradient(
                            colors = gradientColors
                        ),
                        fontWeight = FontWeight.Thin,
                        fontStyle = Italic,
                        fontSize = 30.sp,
                        fontFamily = middleearth
                    ),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Normal,
                                fontSize = 17.sp,
                                letterSpacing = 0.15.sp,
                                color = Color.White,
                                fontFamily = halfelven
                            )
                        ) {
                            append("by ")
                        }
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Normal,
                                fontSize = 20.sp,
                                letterSpacing = 0.15.sp,
                                color = Color.White,
                                fontFamily = middleearth
                            )
                        ) {
                            append(quote.name)
                        }
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Normal,
                                fontSize = 17.sp,
                                letterSpacing = 0.15.sp,
                                color = Color.White,
                                fontFamily = halfelven
                            )
                        ) {
                            append(" in ")
                        }
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Normal,
                                fontSize = 20.sp,
                                letterSpacing = 0.15.sp,
                                color = Color.White,
                                fontFamily = ringbearer
                            )
                        ) {
                            append(quote.movieName)
                        }


                    },
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Box(
                    modifier = Modifier
                        .size(350.dp)
                        .align(Alignment.CenterHorizontally)
                        .border(4.dp, Color(0xFFD9AB6E), RoundedCornerShape(16.dp))
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color.Gray)
                            .fillMaxSize()
                            .blur(15.dp, 15.dp),
                        model = quote.wikiUrl,
                        contentDescription = "Character image",
                        contentScale = ContentScale.Crop
                    )
                    Image(
                        painter = rememberAsyncImagePainter(
                            quote.wikiUrl,
                        ),
                        contentDescription = "Character image",
                        modifier = Modifier
                            .fillMaxSize()


                    )
                }

                ExpandableCard(
                    title = {
                        Text(
                            text = "Character & movie info",
                            fontSize = MaterialTheme.typography.h6.fontSize,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                    description = {
                        Column {
                            FormattedText(title = "Race: ", content = quote.race)
                            FormattedText(title = "Realm: ", content = quote.realm)
                            FormattedText(title = "Birth: ", content = quote.birth)
                            FormattedText(title = "Death: ", content = quote.death)
                            FormattedText(title = "Height: ", content = quote.height)
                            Spacer(modifier = Modifier.height(8.dp))
                            FormattedText(
                                title = "Movie Academy Wins: ",
                                content = "${quote.academyAwardWins}"
                            )
                            FormattedText(
                                title = "Movie Rotten Tomatoes Score: ",
                                content = "${quote.rottenTomatoesScore}%"
                            )
                            FormattedText(
                                title = "Movie runtime: ",
                                content = "${quote.runtimeInMinutes} minutes"
                            )
                            FormattedText(
                                title = "Movie budget: ",
                                content = "${quote.budgetInMillions} million USD"
                            )
                            FormattedText(
                                title = "Movie revenue: ",
                                content = "${quote.boxOfficeRevenueInMillions} million USD"
                            )

                        }
                    }
                )


            }
        }
    }
}


@Composable
fun FormattedText(title: String, content: String) {
    val text: String = content.ifEmpty {
        "Unknown"
    }

    Text(
        buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append(title)
            }
            withStyle(style = SpanStyle(fontWeight = FontWeight.Thin, fontStyle = Italic)) {
                append(text)
            }
        }
    )
}


@ExperimentalMaterialApi
@Composable
fun ExpandableCard(
    title: @Composable () -> Unit,
    description: @Composable () -> Unit,
    shape: Shape = RoundedCornerShape(8.dp),
    padding: Dp = 16.dp
) {
    var expandedState by remember { mutableStateOf(false) }

    val gradientBrush = remember {
        Brush.verticalGradient(
            colors = listOf(
                Color(0xFFB59D7C),
                Color(0xFF634B27)
            )
        )
    }

    Card(
        modifier = Modifier
            .padding(top = 8.dp)
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            ),
        backgroundColor = Color.Transparent,
        shape = shape,
        onClick = { expandedState = !expandedState }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(gradientBrush)
        ) {
            Column(modifier = Modifier.padding(padding)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.weight(6f)) {
                        title()
                    }
                    IconButton(
                        modifier = Modifier.weight(1f),
                        onClick = { expandedState = !expandedState }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Drop-Down Arrow",
                            tint = if (expandedState) Color.White else Color.Black
                        )
                    }
                }
                AnimatedVisibility(visible = expandedState) {
                    Spacer(modifier = Modifier.height(8.dp))
                    description()
                }
            }
        }
    }
}

