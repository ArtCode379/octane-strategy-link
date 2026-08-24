package octanecde.digitalization.octanestrategylink.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import octanecde.digitalization.octanestrategylink.R

private val fontProvider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs,
)
private val headingFont = FontFamily(
    Font(GoogleFont("DM Sans"), fontProvider, FontWeight.Normal),
    Font(GoogleFont("DM Sans"), fontProvider, FontWeight.SemiBold),
    Font(GoogleFont("DM Sans"), fontProvider, FontWeight.Bold),
)
private val bodyFont = FontFamily(
    Font(GoogleFont("Nunito"), fontProvider, FontWeight.Normal),
    Font(GoogleFont("Nunito"), fontProvider, FontWeight.SemiBold),
    Font(GoogleFont("Nunito"), fontProvider, FontWeight.Bold),
)

val AppTypography = Typography(
    headlineLarge = TextStyle(
        fontFamily = headingFont,
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp,
        lineHeight = 36.sp,
    ),
    headlineMedium = TextStyle(
        fontFamily = headingFont,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 30.sp,
    ),
    titleLarge = TextStyle(
        fontFamily = headingFont,
        fontWeight = FontWeight.SemiBold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
    ),
    titleMedium = TextStyle(
        fontFamily = headingFont,
        fontWeight = FontWeight.SemiBold,
        fontSize = 17.sp,
        lineHeight = 22.sp,
    ),
    bodyLarge = TextStyle(
        fontFamily = bodyFont,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = bodyFont,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 21.sp,
    ),
)

val Typography = AppTypography
