package com.example.getusagestats

import android.app.Application
import android.content.Context
import android.util.Log
import java.io.BufferedReader
import java.io.File

class Repository(val context: Context) {
    fun saveFile(fileName: String, str: String){
        context.openFileOutput(fileName, Context.MODE_PRIVATE).use {
            it.write(str.toByteArray())
        }
    }


    fun readFile(fileName: String): String?{
        val readFile = File(context.filesDir, fileName)

        if(!readFile.exists()){
            Log.d("debug","No file exists")
            return null
        }
        else{
            return readFile.bufferedReader().use(BufferedReader::readText)
        }
    }

}