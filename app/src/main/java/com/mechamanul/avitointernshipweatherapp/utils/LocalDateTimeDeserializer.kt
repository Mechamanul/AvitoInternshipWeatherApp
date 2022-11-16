package com.mechamanul.avitointernshipweatherapp.utils

import android.util.Log
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import java.lang.reflect.Type
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class LocalDateDeserializer : JsonDeserializer<LocalDate> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): LocalDate {

        if (typeOfT == LocalDate::class.java) {
            json?.let {
                val formatter = DateTimeFormatter.ISO_DATE
                return LocalDate.parse(it.asString, formatter)
            } ?: throw JsonParseException("Error during date parse")
        }
        throw IllegalArgumentException("unknown type: $typeOfT")

    }

}

class LocalDateTimeDeserializer : JsonDeserializer<LocalDateTime> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): LocalDateTime {
        if (typeOfT == LocalDateTime::class.java) {
            json?.let {
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
                return LocalDateTime.parse(it.asString, formatter)
            } ?: throw JsonParseException("Error during datetime parse")
        }
        throw IllegalArgumentException("unknown type: $typeOfT")

    }

}