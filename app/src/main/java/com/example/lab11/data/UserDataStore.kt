package com.example.lab11.data


import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Extension to create the DataStore
private val Context.dataStore by preferencesDataStore("user_prefs")

class UserDataStore(private val context: Context) {
    companion object {
        val FIRST_NAME_KEY = stringPreferencesKey("first_name")
        val LAST_NAME_KEY = stringPreferencesKey("last_name")
        val EMAIL_KEY = stringPreferencesKey("email")
        val BIRTH_DATE_KEY = stringPreferencesKey("birth_date")
        val AGE_KEY = intPreferencesKey("age")
    }

    // Save user data
    suspend fun saveUserData(
        firstName: String,
        lastName: String,
        email: String,
        birthDate: String,
        age: Int
    ) {
        context.dataStore.edit { prefs ->
            prefs[FIRST_NAME_KEY] = firstName
            prefs[LAST_NAME_KEY] = lastName
            prefs[EMAIL_KEY] = email
            prefs[BIRTH_DATE_KEY] = birthDate
            prefs[AGE_KEY] = age
        }
    }

    // Get user data
    val userData: Flow<UserData> = context.dataStore.data.map { prefs ->
        UserData(
            firstName = prefs[FIRST_NAME_KEY] ?: "",
            lastName = prefs[LAST_NAME_KEY] ?: "",
            email = prefs[EMAIL_KEY] ?: "",
            birthDate = prefs[BIRTH_DATE_KEY] ?: "",
            age = prefs[AGE_KEY] ?: 0
        )
    }
}

// Data class to represent user data
data class UserData(
    val firstName: String,
    val lastName: String,
    val email: String,
    val birthDate: String,
    val age: Int
)