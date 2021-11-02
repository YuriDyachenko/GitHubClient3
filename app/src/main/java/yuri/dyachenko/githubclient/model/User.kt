package yuri.dyachenko.githubclient.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

const val USERS_TABLE_NAME = "users"

@Entity(tableName = USERS_TABLE_NAME)
data class User(
    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    val id: Int,

    @ColumnInfo(name = "login")
    @SerializedName("login")
    val login: String,

    @ColumnInfo(name = "avatar_url")
    @SerializedName("avatar_url")
    val avatarUrl: String?
)
