package com.example.gojek.extensions

import android.view.View

fun View.visible() {
    if (this.visibility != View.VISIBLE) {
        this.visibility = View.VISIBLE
    }
}

fun View.gone() {
    if (this.visibility != View.GONE) {
        this.visibility = View.GONE
    }
}

fun View.isVisible(): Boolean {
    if (this.visibility == View.VISIBLE)
        return true
    return false
}
fun View.isGonee():Boolean{
    if(this.visibility==View.GONE)
        return true
    return false
}