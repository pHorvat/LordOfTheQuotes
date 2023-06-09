package hr.hpatrik.lordofthequotes.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import hr.hpatrik.lordofthequotes.R

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )

    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)

val middleearth = FontFamily(Font(R.font.middleearth))
val hobbiton = FontFamily(Font(R.font.hobbiton))
val halfelven = FontFamily(Font(R.font.halfelven))
val lambency = FontFamily(Font(R.font.lambency))
val blackmountain = FontFamily(Font(R.font.blackmountain))
val elvencommonspeak = FontFamily(Font(R.font.elvencommonspeak))
val ringbearer = FontFamily(Font(R.font.ringbearer))