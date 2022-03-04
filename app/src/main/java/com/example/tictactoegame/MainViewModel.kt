package com.example.tictactoegame

import androidx.compose.runtime.MutableState
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    var curState = MutableLiveData(GridState.X)
    var gameState = MutableLiveData(GameState.Start)
    var winner = MutableLiveData("")

    fun changeCurGridState() {
        when (curState.value) {
            GridState.X -> {
                curState.value = GridState.O
            }
            GridState.O -> {
                curState.value = GridState.X
            }
            else -> {

            }
        }
    }

    /**
     * 判断游戏是否结束
     */
    fun judgeWin(curGrid: Int, allGrid: ArrayList<MutableState<GridState>>): Boolean {
        if (curGrid in 0..2) {
            if (allGrid[curGrid].value == allGrid[curGrid + 3].value && allGrid[curGrid].value == allGrid[curGrid + 6].value) {
                return true
            }
            if (allGrid[0].value == allGrid[1].value && allGrid[1].value == allGrid[2].value) {
                return true
            }
        }
        if (curGrid in 3..5) {
            if (allGrid[curGrid].value == allGrid[curGrid + 3].value && allGrid[curGrid].value == allGrid[curGrid - 3].value) {
                return true
            }
            if (allGrid[3].value == allGrid[4].value && allGrid[4].value == allGrid[5].value) {
                return true
            }
        }

        if (curGrid in 6..8) {
            if (allGrid[curGrid].value == allGrid[curGrid - 3].value && allGrid[curGrid].value == allGrid[curGrid - 6].value) {
                return true
            }
            if (allGrid[6].value == allGrid[7].value && allGrid[7].value == allGrid[8].value) {
                return true
            }
        }

        if (curGrid == 0 || curGrid == 4 || curGrid == 8) {
            if (allGrid[0].value == allGrid[4].value && allGrid[4].value == allGrid[8].value) {
                return true
            }
        }

        if (curGrid == 2 || curGrid == 4 || curGrid == 6) {
            if (allGrid[2].value == allGrid[4].value && allGrid[4].value == allGrid[6].value) {
                return true
            }
        }
        return false
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

enum class GameState {
    Start,//开始
    Gaming,//游戏中
    Win,//胜利
}