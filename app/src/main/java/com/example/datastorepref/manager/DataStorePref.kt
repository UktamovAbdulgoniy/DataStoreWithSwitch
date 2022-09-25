package com.example.datastorepref.manager

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.datastorepref.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStorePref(
    private val context: Context
) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("MyDataStore")

    companion object{
        val name = stringPreferencesKey("name")
        val age = stringPreferencesKey("age")
        val gender = booleanPreferencesKey("gender")
    }

    suspend fun saveUser(user: User){
        context.dataStore.edit{
            it[name] = user.name
            it[age] = user.age
            it[gender] = user.gender
        }
    }
    suspend fun getUser(): Flow<User> = context.dataStore.data.map{
        User(
            name = it[name] ?: "Anonymous",
            age = it[age] ?: "0",
            gender = (it[gender] ?: "GenderLess") as Boolean
        )
    }
}