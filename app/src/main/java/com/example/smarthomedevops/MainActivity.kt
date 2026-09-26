package com.example.smarthomedevops

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.smarthomedevops.presentation.viewmodel.GitHubViewModel
import com.example.smarthomedevops.presentation.viewmodel.GitHubUiState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.Row
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults





class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val viewModel: GitHubViewModel = viewModel()
            val uiState by viewModel.uiState.collectAsState()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    GitOpsScreen(
                        uiState = uiState,
                        viewModel = viewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }

    }
}

@Composable
fun GitOpsScreen(
    uiState: GitHubUiState,
    viewModel: GitHubViewModel,
    modifier: Modifier = Modifier
){
    val buttonShape = RoundedCornerShape(2.dp)

    val buttonColors = ButtonDefaults.buttonColors(
        containerColor = Color.LightGray
    )


    val backgroundColor = if (uiState.securityAlert) {
        Color.Red
    } else { Color.Green}

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        if (uiState.error != null) {
            Text(
                text = "ERROR: ${uiState.error}",
                fontSize = 18.sp
            )
        }  else if (uiState.securityAlert) {

            Text(
                text = "SECURITY ALERT",
                fontSize = 32.sp
            )

            Text(
                text = "Confidence: ${uiState.confidence}%",
                fontSize = 24.sp
            )

            Text(
                text = uiState.maliciousText ?: "",
                fontSize = 18.sp
            )


                            //BUTTONS
            Spacer(modifier = Modifier.padding(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp))

            {
                Button(onClick = {
                    viewModel.forceMerge()
                },
                    shape = buttonShape,
                    colors = buttonColors
                ) { Text(
                    text = "Force Merge",
                    color = Color.Black) }

                Button(onClick = {
                    viewModel.forceReject()
                },
                    shape = buttonShape,
                    colors = buttonColors
                ) { Text(
                    text = "Force Reject",
                    color = Color.Black) }
            }

        } else {
            Text(
                text = "NORMAL",
                fontSize = 32.sp
            )

            Text(
                text = "No active attack detected",
                fontSize = 18.sp
            )
        }
    }




}

