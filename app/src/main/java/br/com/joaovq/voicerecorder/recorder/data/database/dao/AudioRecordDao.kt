package br.com.joaovq.voicerecorder.recorder.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import br.com.joaovq.voicerecorder.recorder.data.database.entity.AudioRecordEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AudioRecordDao {
    @Insert
    suspend fun saveAudioRecord(audioRecordEntity: AudioRecordEntity)

    @Query("select * from audio_record_tb")
    fun getRecords(): Flow<List<AudioRecordEntity>>
    @Query("select * from audio_record_tb where name like  '%' || :query || '%'")
    fun searchRecordByName(query: String): Flow<List<AudioRecordEntity>>
    @Delete
    suspend fun deleteRecord(audioRecordEntity: AudioRecordEntity)
}