package com.youer.genshin.presenter

/**
 * @author youer
 * @date 10/9/23
 */
interface ResultCallback<T> {
    fun success(t: T)
    fun fail(s: String)
}