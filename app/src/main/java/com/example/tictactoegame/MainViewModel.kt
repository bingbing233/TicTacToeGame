package com.example.tictactoegame

import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    var curState = GridState.X

    fun changeCurGridState() {
        when (curState) {
            GridState.X -> {
                curState = GridState.O
            }
            GridState.O -> {
                curState = GridState.X
            }
            else -> {

            }
        }
    }
}

/**
 * 格子状态
 */
enum class GridState {
    None, //无内容
    X,    //显示X
    O,    //显示O
}