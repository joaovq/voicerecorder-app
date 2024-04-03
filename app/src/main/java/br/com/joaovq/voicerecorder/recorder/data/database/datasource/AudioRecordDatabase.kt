package br.com.joaovq.voicerecorder.recorder.data.database.datasource

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.joaovq.voicerecorder.recorder.data.database.dao.AudioRecordDao
import br.com.joaovq.voicerecorder.recorder.data.database.entity.AudioRecordEntity

@Database(entities = [AudioRecordEntity::class], version = 1)
abstract class AudioRecordDatabase : RoomDatabase() {
    abstract fun audioRecordDao(): AudioRecordDao
}