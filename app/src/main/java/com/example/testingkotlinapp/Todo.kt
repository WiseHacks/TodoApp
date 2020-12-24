package com.example.testingkotlinapp

import java.lang.reflect.Constructor

data class Todo(
        var id:Int,
        var title:String,
        var isChecked:String
){
    constructor() : this(0, "", "")
}
