package com.edwin.recipeapp.database

import androidx.room.TypeConverter
import com.edwin.recipeapp.domain.RecipeBrief
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun fromString(stringListString: String): List<String> {
        return stringListString.split("<&>").map { it }
    }

    @TypeConverter
    fun toString(stringList: List<String>): String {
        return stringList.joinToString(separator = "<&>")
    }

    @TypeConverter
    fun fromCountryLangList(value: List<RecipeBrief>): String {
        val gson = Gson()
        val type = object : TypeToken<List<RecipeBrief>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toCountryLangList(value: String): List<RecipeBrief> {
        val gson = Gson()
        val type = object : TypeToken<List<RecipeBrief>>() {}.type
        return gson.fromJson(value, type)
    }
}