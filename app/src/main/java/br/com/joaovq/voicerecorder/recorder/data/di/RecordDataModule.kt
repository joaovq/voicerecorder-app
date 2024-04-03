package br.com.joaovq.voicerecorder.recorder.data.di

import android.content.Context
import androidx.room.Room
import br.com.joaovq.voicerecorder.data.service.AudioRecorder
import br.com.joaovq.voicerecorder.recorder.data.AndroidMediaRecorder
import br.com.joaovq.voicerecorder.recorder.data.database.datasource.AudioRecordDatabase
import br.com.joaovq.voicerecorder.recorder.data.database.dao.AudioRecordDao
import br.com.joaovq.voicerecorder.recorder.data.database.datasource.AudioRecordDataState
import br.com.joaovq.voicerecorder.recorder.data.database.datasource.AudioRecordDatasource
import br.com.joaovq.voicerecorder.recorder.data.database.entity.AudioRecordEntity
import br.com.joaovq.voicerecorder.recorder.data.local.AudioRecordLocalDatasource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RecordDataModule {

    @Binds
    abstract fun bindsAudioRecordLocalDatasource(
        audioRecordLocalDatasource: AudioRecordLocalDatasource
    ): AudioRecordDatasource<AudioRecordEntity, AudioRecordDataState>

    @Binds
    abstract fun bindAudioRecorder(
        androidMediaRecorder: AndroidMediaRecorder
    ): AudioRecorder

    companion object {
        @Provides
        fun providesAudioRecordDatabase(
            @ApplicationContext context: Context
        ): AudioRecordDao = Room.databaseBuilder(
            context,
            AudioRecordDatabase::class.java,
            "audio_record_db"
        ).build().audioRecordDao()
    }
}