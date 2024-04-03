package br.com.joaovq.voicerecorder.home.data

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioRouting
import android.media.MediaPlayer
import android.os.Build
import android.os.Handler
import androidx.core.net.toUri
import br.com.joaovq.voicerecorder.data.service.AudioPlayer
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import java.io.File
import java.io.IOException
import javax.inject.Inject

class AndroidMediaPlayer @Inject constructor(
    @ApplicationContext private val context: Context
) : AudioPlayer {
    private var mediaPlayer: MediaPlayer? = null
    private val log = Timber.tag(this::class.java.simpleName)


    override fun play(file: File) {
        try {
            log.d("File passed in media player ${file.absolutePath}")
            MediaPlayer.create(context, file.toUri()).apply {
                mediaPlayer = this
                mediaPlayer?.start()
            }
            mediaPlayer?.setOnPreparedListener {
                log.d("Media player prepared for play")
            }
            mediaPlayer?.setOnPreparedListener {
                log.d("Media player completed")
            }
        } catch (e: Exception) {
            when (e) {
                is IOException,
                is IllegalArgumentException -> {
                    log.e("File not found: ${e.message}")
                }
            }
            e.printStackTrace()
        }
    }

    override fun stop() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}