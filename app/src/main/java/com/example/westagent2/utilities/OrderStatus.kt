package com.example.westagent2

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

enum class OrderStatus {
    IN_PROGRESS,
    READY,
    SENT;

    @Composable
    fun toStringMyImplementation(): String {
        return when (this) {
            IN_PROGRESS -> stringResource(id = R.string.in_progress)
            READY -> stringResource(id = R.string.ready)
            SENT -> stringResource(id = R.string.sent)
        }
    }
}