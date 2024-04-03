package br.com.joaovq.voicerecorder.data.service

import java.io.File

interface AudioRecorder {
    fun start(outputFile: File)
    fun resume()
    fun stop()
    fun pause()
    fun release()
}