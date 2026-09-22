package com.example.smarthomedevops.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import com.example.smarthomedevops.data.model.GitHubPullRequest
import com.example.smarthomedevops.data.model.GitHubIssueComment

interface GitHubApi {
    @GET ("repos/{owner}/{repo}/pulls")
    suspend fun getOpenPullRequests(
        @Path("owner")owner:String,
        @Path("repo")repo:String,
        @Query("state")state:String="open"
    ): List<GitHubPullRequest>


    @GET("repos/{owner}/{repo}/issues/{issue_number}/comments")
    suspend fun getIssueComments(
        @Path("owner")owner:String,
        @Path("repo")repo:String,
        @Path("issue_number")issueNumber:Int
    ): List<GitHubIssueComment>
}