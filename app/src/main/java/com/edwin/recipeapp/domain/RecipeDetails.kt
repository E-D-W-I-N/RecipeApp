package com.edwin.recipeapp.domain

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.edwin.recipeapp.util.DATE_PATTERN
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import java.text.SimpleDateFormat
import java.util.*

@Parcelize
@Entity(tableName = "recipeDetails")
data class RecipeDetails(
    @PrimaryKey val uuid: String,
    val name: String,
    val images: List<String>,
    val lastUpdated: Long,
    val description: String?,
    val instructions: String?,
    val difficulty: Float,
    val similar: List<RecipeBrief>
) : Parcelable {
    @Ignore
    @IgnoredOnParcel
    // $lastUpdated multiplied by 1000 because Date takes timestamp in milliseconds
    val updateDate: String = SimpleDateFormat(DATE_PATTERN, Locale.getDefault())
        .format(Date(lastUpdated * 1000))
}

@Parcelize
data class RecipeBrief(
    val uuid: String,
    val name: String,
    val image: String
) : Parcelable