package br.com.joaovq.voicerecorder

import android.app.Application
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.appopen.AppOpenAd
import com.google.android.gms.ads.appopen.AppOpenAd.AppOpenAdLoadCallback
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class VoiceRecorderApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        MobileAds.initialize(this)
        val request = AdRequest.Builder().build()
        AppOpenAd.load(
            this,
            BuildConfig.OPEN_APP_ID,
            request,
            object : AppOpenAdLoadCallback() {}
        )
    }
}