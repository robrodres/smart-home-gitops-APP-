package com.example.smarthomedevops.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smarthomedevops.data.GitHubRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.example.smarthomedevops.domain.DeceptionDetector




data class GitHubUiState(
    val isLoading:Boolean=false,
    val error:String?=null,
    val securityAlert:Boolean=false,
    val confidence:Int=0,
    val maliciousText:String?=null
)

class GitHubViewModel(

    private val repository:GitHubRepository=GitHubRepository()
):ViewModel() {

    private val detector = DeceptionDetector()

    private val _uiState = MutableStateFlow(GitHubUiState())
    val uiState:StateFlow<GitHubUiState> = _uiState.asStateFlow()

    init {
        startPolling()
    }

    private fun startPolling() {
        viewModelScope.launch{
            while(true) {
                fetchGitHubData()
                delay(30000)
            }
        }
    }

    private suspend fun fetchGitHubData(){
        _uiState.value = _uiState.value.copy(
            isLoading = true,
            securityAlert = false,
            confidence = 0,
            maliciousText = null
        )

        try {
            val pullRequests = withContext(Dispatchers.IO) {
                repository.getOpenPullRequests()
            }

            for (pullRequest in pullRequests) {
                val comments = withContext(Dispatchers.IO){
                    repository.getIssueComments(pullRequest.number)
                }

                for (comment in comments) {

                    val result = withContext(Dispatchers.Default) {
                        detector.analyze(comment.body)
                    }

                    if (result.isAttack) {

                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            securityAlert = true,
                            confidence = result.confidence,
                            maliciousText = comment.body
                        )

                        return
                    }
                }

            }
            _uiState.value = _uiState.value.copy(
                isLoading = false
            )

        } catch (e:Exception){

            _uiState.value = _uiState.value.copy(
                isLoading = false,
                error = e.message
            )

        }
    }
}