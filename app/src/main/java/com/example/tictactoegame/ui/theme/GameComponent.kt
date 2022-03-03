package com.example.tictactoegame.ui.theme

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tictactoegame.GridState
import com.example.tictactoegame.MainViewModel
import com.google.accompanist.flowlayout.FlowRow

@Composable
fun GameBoard() {

    val TAG = "GameBoard"
    val viewModel: MainViewModel = viewModel()
    val stateList = ArrayList<MutableState<GridState>>().apply {
        repeat(9) {
            var state =  remember {
                mutableStateOf(GridState.None)
            }
            add(state)
        }
    }


    Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        FlowRow(Modifier.width(300.dp)) {
            repeat(9){
                GameGrid(stateList[it].value){
                    stateList[it].value = viewModel.curState
                    viewModel.changeCurGridState()
                    Log.e(TAG, "GameBoard: ${stateList.get(it)}]", )
                }
            }
        }
    }

}

@Composable
fun GameGrid(state: GridState, onClick: () -> Unit) {
    val TAG = "Grid"
    Box(
        Modifier
            .size(100.dp)
            .border(width = 1.dp, color = Color.White)
            .clickable { onClick() }
            .background(color = Color.Gray), contentAlignment = Alignment.Center) {
        Log.e(TAG, "GameGrid: ${state.name}")
        when (state) {
            GridState.X -> {
                Text(text = "X", color = Color.Blue)
            }
            GridState.O -> {
                Text(text = "O", color = Color.Red)
            }
        }
    }
}

@Preview
@Composable
fun GameBoardPrev() {
    GameBoard()
}

