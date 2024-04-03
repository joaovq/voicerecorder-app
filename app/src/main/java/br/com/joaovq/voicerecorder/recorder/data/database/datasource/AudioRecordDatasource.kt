package br.com.joaovq.voicerecorder.recorder.data.database.datasource

import br.com.joaovq.voicerecorder.recorder.data.database.entity.AudioRecordEntity
import kotlinx.coroutines.flow.Flow

interface AudioRecordDatasource<E, S> {
    fun getRecords(): Flow<List<E>>
    suspend fun searchRecordByName(query: String): Flow<List<AudioRecordEntity>>
    suspend fun saveRecord(entity: E)
    suspend fun deleteRecord(entity: E)
}