package com.example.tictactoegame.ui.theme

import android.os.Build.VERSION_CODES.Q
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tictactoegame.GridState
import com.example.tictactoegame.MainViewModel
import com.google.accompanist.flowlayout.FlowRow

@Composable
fun GameBoard() {

    val TAG = "GameBoard"
    val viewModel: MainViewModel = viewModel()
    val curState = viewModel.curState.observeAsState()
    val stateList = ArrayList<MutableState<GridState>>().apply {
        repeat(9) {
            val state = remember {
                mutableStateOf(GridState.None)
            }
            add(state)
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
                GameGrid(stateList[it].value) {
                    stateList[it].value = viewModel.curState.value!!
                    viewModel.changeCurGridState()
                    Log.e(TAG, "GameBoard: ${stateList.get(it)}]")
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        PlayButton(player = "O", state = curState.value!!)
    }
}

@Composable
fun GameGrid(state: GridState, onClick: () -> Unit) {
    val TAG = "Grid"
    Box(
        Modifier
            .size(100.dp)
            .border(width = 1.dp, color = Color.White)
            .clickable(enabled = state == GridState.None) {
                onClick()
            }
            .background(color = GridColor), contentAlignment = Alignment.Center) {
        Log.e(TAG, "GameGrid: ${state.name}")

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
            .height(200.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
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
        AnimatedVisibility(visible =hashMap[player] == state ) {
            Text(text = "轮到你了", color = textColor)
        }
    }

}

@Preview
@Composable
fun GameBoardPrev() {
    GameBoard()
}

