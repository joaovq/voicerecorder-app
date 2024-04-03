package br.com.joaovq.voicerecorder.data.storage

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

val Context.datastore by preferencesDataStore("preferences-datastore")