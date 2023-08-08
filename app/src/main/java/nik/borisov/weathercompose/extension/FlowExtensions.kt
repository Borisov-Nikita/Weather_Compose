package nik.borisov.weathercompose.extension

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.merge

fun <T> Flow<T>.mergeWith(vararg another: Flow<T>): Flow<T> {
    return merge(this, another.asIterable().merge())
}