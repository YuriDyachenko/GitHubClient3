package yuri.dyachenko.githubclient.model

private const val MSG = "Repo not found"

class RepoNotFoundException : Exception(MSG)