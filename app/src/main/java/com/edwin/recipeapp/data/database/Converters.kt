package com.edwin.recipeapp.data.database

import androidx.room.TypeConverter
import com.edwin.recipeapp.domain.RecipeBrief
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun fromStringList(stringList: List<String>): String {
        return stringList.joinToString(separator = "<&>")
    }

    @TypeConverter
    fun toStringList(stringListString: String): List<String> {
        return stringListString.split("<&>").map { it }
    }

    @TypeConverter
    fun fromRecipeBriefList(value: List<RecipeBrief>): String {
        val type = object : TypeToken<List<RecipeBrief>>() {}.type
        return Gson().toJson(value, type)
    }

    @TypeConverter
    fun toRecipeBriefList(value: String): List<RecipeBrief> {
        val type = object : TypeToken<List<RecipeBrief>>() {}.type
        return Gson().fromJson(value, type)
    }
}