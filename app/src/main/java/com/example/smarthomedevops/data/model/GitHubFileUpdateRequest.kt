package com.example.smarthomedevops.data.model

data class GitHubFileUpdateRequest(
    val message:String,
    val content:String,
    val sha:String,
    val branch:String
)