package br.com.joaovq.voicerecorder.data.service

import java.io.File
import java.io.FileDescriptor

interface AudioPlayer {
    fun play(file: File)
    fun stop()
}