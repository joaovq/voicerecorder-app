package br.com.joaovq.voicerecorder.recorder.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity("audio_record_tb")
data class AudioRecordEntity(
    @PrimaryKey
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val uriToAudio: String
)