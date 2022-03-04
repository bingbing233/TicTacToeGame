package com.example.tictactoegame.ui.theme

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tictactoegame.GameState
import com.example.tictactoegame.GridState
import com.example.tictactoegame.MainViewModel
import com.google.accompanist.flowlayout.FlowRow

@Composable
fun GameBoard() {

    val TAG = "GameBoard"
    val viewModel: MainViewModel = viewModel()
    val curState = viewModel.curState.observeAsState()
    val step = viewModel.step.observeAsState()
    val gridStates = ArrayList<State<GridState>>().apply {
        viewModel.allGrid.forEach {
            this.add(it.observeAsState() as State<GridState>)
        }
    }

    Column(
        Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {

        PlayButton(player = "X", state = curState.value!!)
        Spacer(modifier = Modifier.height(20.dp))

        FlowRow(Modifier.width(300.dp)) {
            repeat(9) {
                GameGrid(gridStates[it].value) {
                    viewModel.step.value = viewModel.step.value!!+1
                    viewModel.allGrid[it].value = curState.value
                    if (viewModel.judgeWin(it)) {
                        viewModel.gameState.value = GameState.Win
                        viewModel.winner.value = curState.value!!.name
                    }
                    if(step.value == 9){
                        viewModel.gameState.value = GameState.Draw
                    }
                    Log.e(TAG, "GameBoard: step = ${step.value}", )
                    viewModel.changeCurGridState()
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        PlayButton(player = "O", state = curState.value!!)

    }
}

@Composable
fun GameGrid(state: GridState, onClick: () -> Unit) {
    var enable by remember {
        mutableStateOf(true)
    }
    val animation:Float by animateFloatAsState( if (enable) 0f else 180f, animationSpec = TweenSpec(durationMillis = 800))
    Box(
        Modifier
            .size(100.dp)
            .border(width = 1.dp, color = Color.White)
            .graphicsLayer(rotationY = animation,)
            .clickable(enabled = state == GridState.None) {
                onClick()
                enable = false
            }
            .background(color = GridColor), contentAlignment = Alignment.Center) {

        AnimatedVisibility(visible = state == GridState.X) {
            Text(text = "X", color = Color.Blue, fontSize = 18.sp)
        }

        AnimatedVisibility(visible = state == GridState.O) {
            Text(text = "O", color = Color.Red, fontSize = 18.sp)
        }

    }
}

@Composable
fun PlayButton(player: String, state: GridState) {
    val hashMap = HashMap<String, GridState>()
    hashMap["X"] = GridState.X
    hashMap["O"] = GridState.O
    var textColor = if (hashMap[player] == state) {
        if (player == "X")
            Color.Blue
        else
            Color.Red
    } else Color.Black

    Column(
        Modifier
            .fillMaxWidth()
            .height(200.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape),
            elevation = 8.dp,
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = player, color = textColor)
            }
        }
        AnimatedVisibility(visible = hashMap[player] == state) {
            Text(text = "轮到你了", color = textColor)
        }
    }

}

@Composable
fun WinDialog(winner: String) {
    val viewModel:MainViewModel = viewModel()
    AlertDialog(
        onDismissRequest = { /*TODO*/ },
        title = { Text(text = "游戏结束") },
        text = { 
               when(viewModel.gameState.value){
                   GameState.Win ->{
                        Text(text = "玩家$winner 获得胜利！！")
                   }
                   GameState.Draw->{
                       Text(text = "平局")
                   }
               }
        },
        dismissButton = {
            TextButton(onClick = { viewModel.gameState.value = GameState.Start }) {
                Text(text = "结束")
            }
        },
        confirmButton = {
            TextButton(onClick = {viewModel.gameState.value = GameState.Gaming
                viewModel.reset()
            }) {
                Text(text = "重玩")
            }
        },
        properties = DialogProperties()
    )
}

@Preview
@Composable
fun GameBoardPrev() {
    GameBoard()
}

