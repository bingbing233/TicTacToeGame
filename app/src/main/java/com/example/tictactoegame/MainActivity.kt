package com.example.tictactoegame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tictactoegame.ui.theme.GameBoard
import com.example.tictactoegame.ui.theme.TicTacToeGameTheme
import com.example.tictactoegame.ui.theme.WinDialog
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TicTacToeGameTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val viewModel:MainViewModel = viewModel()
                    val gameState = viewModel.gameState.observeAsState()

                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                        GameBoard()

                        AnimatedVisibility(visible = gameState.value == GameState.Win) {
                            WinDialog(winner = viewModel.winner.value!!)
                        }
                    }
                }
            }
        }
    }
}
