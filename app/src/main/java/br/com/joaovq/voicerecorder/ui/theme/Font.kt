package br.com.joaovq.voicerecorder.ui.theme

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import br.com.joaovq.voicerecorder.R

val fontProvider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)
val inter = GoogleFont("Inter")
val oswald = GoogleFont("Oswald")

val FontFamily.Companion.Inter: FontFamily
    get() = FontFamily(
        Font(googleFont = inter, fontProvider = fontProvider)
    )

val FontFamily.Companion.Oswald: FontFamily
    get() = FontFamily(
        Font(googleFont = oswald, fontProvider = fontProvider)
    )