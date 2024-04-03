package br.com.joaovq.voicerecorder.recorder.data.local

import br.com.joaovq.voicerecorder.recorder.data.database.datasource.AudioRecordDataState
import br.com.joaovq.voicerecorder.recorder.data.database.datasource.AudioRecordDatasource
import br.com.joaovq.voicerecorder.recorder.data.database.dao.AudioRecordDao
import br.com.joaovq.voicerecorder.recorder.data.database.entity.AudioRecordEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AudioRecordLocalDatasource @Inject constructor(
    private val audioRecordDao: AudioRecordDao
) : AudioRecordDatasource<AudioRecordEntity, AudioRecordDataState> {
    override fun getRecords(): Flow<List<AudioRecordEntity>> {
        return try {
            audioRecordDao.getRecords()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyFlow()
        }
    }

    override suspend fun searchRecordByName(query: String): Flow<List<AudioRecordEntity>> {
        return audioRecordDao.searchRecordByName(query)
    }

    override suspend fun saveRecord(entity: AudioRecordEntity) {
        try {
            audioRecordDao.saveAudioRecord(entity)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun deleteRecord(entity: AudioRecordEntity) {
        try {
            audioRecordDao.deleteRecord(entity)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}