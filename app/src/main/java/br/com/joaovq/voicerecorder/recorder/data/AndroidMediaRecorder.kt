package br.com.joaovq.voicerecorder.recorder.data

import android.content.Context
import android.media.MediaRecorder
import android.os.Build
import br.com.joaovq.voicerecorder.data.service.AudioRecorder
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject
import kotlin.concurrent.timer

class AndroidMediaRecorder @Inject constructor(
    @ApplicationContext private val context: Context
) : AudioRecorder {
    private var recorder: MediaRecorder? = null
    private val log = Timber.tag(this::class.java.simpleName)

    private fun initRecorder(outputFile: File): MediaRecorder? {
        recorder = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
            MediaRecorder()
        } else {
            MediaRecorder(context)
        }
        recorder?.setAudioSource(MediaRecorder.AudioSource.MIC)
        recorder?.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
        recorder?.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
        recorder?.setOutputFile(FileOutputStream(outputFile).fd)
        recorder?.prepare()
        recorder?.setOnErrorListener { mr, what, extra ->
            if (what == MediaRecorder.MEDIA_RECORDER_ERROR_UNKNOWN) {
                log.e("Error unknown")
            }
        }
        recorder?.setOnInfoListener { mr, what, extra ->
            if (what == MediaRecorder.MEDIA_RECORDER_INFO_MAX_DURATION_REACHED) {
                log.e("Max duration media recorder reached")
            }
        }
        recorder?.start()
        return recorder
    }

    override fun start(outputFile: File) {
        try {
            initRecorder(outputFile)
        } catch (e: Exception) {
            when (e) {
                is IOException -> {
                    log.e("Prepare failed")
                }

                else -> {
                    log.e("setOutputFile failed")
                }
            }
            e.printStackTrace()
        }
    }

    override fun resume() {
        recorder?.resume()
    }

    override fun stop() {
        try {
            recorder?.stop()
            recorder?.reset()
            recorder = null
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun pause() {
        recorder?.pause()
    }

    override fun release() {
        recorder?.release()
    }
}