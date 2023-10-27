package com.oshanti.seersolution.base

sealed interface UiState <out T> {
    object Loading: UiState<Nothing>

    data class Success<T> (val data: T): UiState<T>

    data class Error(val msg: String): UiState<Nothing>
}