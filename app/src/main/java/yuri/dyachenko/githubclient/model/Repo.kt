package yuri.dyachenko.githubclient.model

import com.google.gson.annotations.SerializedName

data class Repo(
    val userLogin: String,
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("forks_count") val forksCount: Int
)
