package com.example.westagent2.utilities

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import org.simpleframework.xml.core.Persister


fun <T> parseXml(xml: String, clazz: Class<T>): T {
    val serializer = Persister()
    return serializer.read(clazz, xml)
}

// Initialize MasterKey and EncryptedSharedPreferences
fun getEncryptedSharedPreferences(context: Context): SharedPreferences {
    val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    return EncryptedSharedPreferences.create(
        context,
        "secure_prefs", // File name for shared preferences
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )
}

fun saveSessionIdLocally(context: Context, sessionId: String) {
    val encryptedPrefs = getEncryptedSharedPreferences(context)
    encryptedPrefs.edit().putString("session_id", sessionId).apply() // Apply changes asynchronously
}

fun getSessionIdLocally(context: Context): String? {
    val encryptedPrefs = getEncryptedSharedPreferences(context)
    return encryptedPrefs.getString("session_id", null)
}

fun clearSessionIdLocally(context: Context) {
    val encryptedPrefs = getEncryptedSharedPreferences(context)
    encryptedPrefs.edit().remove("session_id").apply()
}

fun saveUsernameLocally(context: Context, username: String) {
    val encryptedPrefs = getEncryptedSharedPreferences(context)
    encryptedPrefs.edit().putString("username", username).apply()
}

fun getUsernameLocally(context: Context): String? {
    val encryptedPrefs = getEncryptedSharedPreferences(context)
    return encryptedPrefs.getString("username", null)
}

fun savePasswordLocally(context: Context, password: String) {
    val encryptedPrefs = getEncryptedSharedPreferences(context)
    encryptedPrefs.edit().putString("password", password).apply()
}

fun getPasswordLocally(context: Context): String? {
    val encryptedPrefs = getEncryptedSharedPreferences(context)
    return encryptedPrefs.getString("password", null)
}
