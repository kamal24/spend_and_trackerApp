package com.coderz.creative.music.Model

/**
 * Created by kamal on 16/12/17.
 */
data class Catagory(val id:Int?,val name:String, val type:String){
    override fun toString(): String {
        return name
    }

    fun toType(): String{
        return type
    }
}