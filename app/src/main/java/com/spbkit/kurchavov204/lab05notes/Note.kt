package com.spbkit.kurchavov204.lab05notes

import java.io.Serializable

data class Note(var name: String, var content: String): Serializable {
    override fun toString(): String {
        return name
    }
}