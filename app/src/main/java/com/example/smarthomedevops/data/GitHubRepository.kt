package com.example.smarthomedevops.data

import com.example.smarthomedevops.data.model.GitHubPullRequest
import com.example.smarthomedevops.data.model.GitHubIssueComment
import com.example.smarthomedevops.data.model.GitHubPRUpdateRequest
import com.example.smarthomedevops.data.remote.GitHubApi
import com.example.smarthomedevops.data.remote.RetrofitClient

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
}