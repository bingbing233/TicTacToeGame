package com.example.tictactoegame.ui.theme

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
    val viewModel:MainViewModel = viewModel()
    val gridStateList = viewModel.gridStateList.observeAsState()

    Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        FlowRow(Modifier.width(300.dp)) {
            repeat(9){
                GameGrid(gridStateList.value!![it]){
                    val stateList = viewModel.gridStateList.value
                    stateList!![it] = viewModel.curState.value!!
                    viewModel.gridStateList.postValue(stateList)
                    viewModel.changeCurGridState()
                    Log.e(TAG, "GameBoard: curState = ${viewModel.curState.value!!.name}", )
                }
            }
        }
    }
}

@Composable
fun GameGrid(state: GridState = GridState.None,onClick:()->Unit) {
    Box(
        Modifier
            .size(100.dp)
            .border(width = 1.dp, color = Color.White)
            .clickable { onClick() }
            .background(color = Color.Gray), contentAlignment = Alignment.Center) {
        when(state){
            GridState.X -> {
                Text(text = "X")
            }
            GridState.O -> {
                Text(text = "O")
            }
        }
    }
}

@Preview
@Composable
fun GameBoardPrev() {
    GameBoard()
}