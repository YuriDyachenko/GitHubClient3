package yuri.dyachenko.githubclient.model

private const val MSG = "User not found"

class UserNotFoundException : Exception(MSG)