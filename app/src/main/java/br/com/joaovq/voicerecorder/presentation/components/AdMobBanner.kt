package br.com.joaovq.voicerecorder.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import br.com.joaovq.voicerecorder.BuildConfig
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView

@Composable
fun AdMobBanner(
    modifier: Modifier = Modifier,
    adSize: AdSize = AdSize.BANNER,
    adUnitId: String,
    adRequest: AdRequest = AdRequest.Builder().build()
) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            val adView = AdView(context)
            adView.setAdSize(adSize)
            adView.adUnitId = adUnitId
            adView.loadAd(adRequest)
            adView
        }
    )
}