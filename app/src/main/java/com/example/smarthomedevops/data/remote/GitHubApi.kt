package com.example.smarthomedevops.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.PATCH
import retrofit2.http.Body
import retrofit2.http.PUT
import com.example.smarthomedevops.data.model.GitHubPullRequest
import com.example.smarthomedevops.data.model.GitHubIssueComment

interface GitHubApi {

    //LAB 1
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


    //LAB 2
    @PATCH("repos/{owner}/{repo}/pulls/{pull_number}")
    suspend fun updatePullRequest(
        @Path("owner") owner:String,
        @Path("repo") repo:String,
        @Path("pull_number") pullNumber:Int,
        @Body body : GitHubPRUpdateRequest
    ): GithubPullRequest

    @GET("repos/{owner}/{repo}/contents/{path}")
    suspend fun getFileContent(
        @Path("owner")owner:String,
        @Path("repo")repo:String,
        @Path("path")path:String,
        @Query("ref")ref:String="main"
    ): GitHubFileContent

    @PUT("repo/{owner}/{repo}/contents/{path}")
    suspend fun updateFileContent(
        @Path("owner")owner:String,
        @Path("repo")repo:String,
        @Body body : GitHubFileUpdateRequest
    ): GitHubFileUpdateResponse

}