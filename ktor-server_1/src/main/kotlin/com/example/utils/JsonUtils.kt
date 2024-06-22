package com.example.utils

import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json

object JsonUtils {
    private val json = Json {
        prettyPrint = true
    }

    // Функция для сериализации объекта в JSON
    fun <T : Any> toJson(obj: T, serializer: KSerializer<T>): String {
        return json.encodeToString(serializer, obj)
    }

    // Функция для десериализации JSON в объект
    fun <T : Any> fromJson(jsonString: String, serializer: KSerializer<T>): T {
        return json.decodeFromString(serializer, jsonString)
    }
}
