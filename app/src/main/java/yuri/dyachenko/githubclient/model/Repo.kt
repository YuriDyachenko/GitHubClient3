package yuri.dyachenko.githubclient.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

const val REPOS_TABLE_NAME = "repos"

@Entity(tableName = REPOS_TABLE_NAME)
data class Repo(
    @ColumnInfo(name = "user_login")
    var userLogin: String,

    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    val id: Int,

    @ColumnInfo(name = "name")
    @SerializedName("name")
    val name: String,

    @ColumnInfo(name = "forks_count")
    @SerializedName("forks_count")
    val forksCount: Int
)
