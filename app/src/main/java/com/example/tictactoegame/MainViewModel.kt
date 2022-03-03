package com.example.tictactoegame

import androidx.compose.runtime.remember
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

        val gridStateList = MutableLiveData<ArrayList<GridState>>(ArrayList()).apply {
        repeat(9){
            this.value?.add(GridState.None)
        }
    }

    var curState = MutableLiveData(GridState.X)

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
}

/**
 * 格子状态
 */
enum class GridState {
    None, //无内容
    X,    //显示X
    O,    //显示O
}