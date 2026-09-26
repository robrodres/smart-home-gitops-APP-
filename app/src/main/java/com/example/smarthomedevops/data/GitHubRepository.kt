package com.example.smarthomedevops.data

import com.example.smarthomedevops.data.model.GitHubPullRequest
import com.example.smarthomedevops.data.model.GitHubIssueComment
import com.example.smarthomedevops.data.model.GitHubPRUpdateRequest
import com.example.smarthomedevops.data.remote.GitHubApi
import com.example.smarthomedevops.data.remote.RetrofitClient
//LAB 2
import com.example.smarthomedevops.data.model.GitHubFileContent
import com.example.smarthomedevops.data.model.GitHubFileUpdateResponse
import com.example.smarthomedevops.data.model.GitHubFileUpdateRequest

class GitHubRepository(
    private val api:GitHubApi = RetrofitClient.api
) {
    suspend fun getOpenPullRequests(): List<GitHubPullRequest> {
        return api.getOpenPullRequests(
            owner="robrodres",
            repo="smart-home-gitops"
        )
    }

    suspend fun getIssueComments(issueNumber:Int): List<GitHubIssueComment> {
        return api.getIssueComments(
            owner="robrodres",
            repo="smart-home-gitops",
            issueNumber=issueNumber
        )
    }

    suspend fun updatePullRequest(pullNumber:Int) {
        api.updatePullRequest(
            owner="robrodres",
            repo="smart-home-gitops",
            pullNumber = pullNumber,
            body = GitHubPRUpdateRequest(state="closed")
        )
    }

    suspend fun getFileContent(): GitHubFileContent{
        return api.getFileContent(
            owner="robrodres",
            repo="smart-home-gitops",
            path="house_config.json"
        )
    }

    suspend fun updateFileContent(
        content:String,
        sha:String
    ): GitHubFileUpdateResponse{
        return api.updateFileContent(
            owner="robrodres",
            repo="smart-home-gitops",
            path="house_config.json",
            body = GitHubFileUpdateRequest(
                message="Update house configuration from Android",
                content = content,
                sha = sha,
                branch = "main"
            )
        )
    }
}