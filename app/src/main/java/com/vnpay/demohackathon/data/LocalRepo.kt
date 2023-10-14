package com.vnpay.demohackathon.data

import java.io.File

object LocalRepo {

    //viết query lấy data trong thiết bị vd như ảnh,...
    private const val PATH = "storage/emulated/0/DCIM/dir_003/dir_003"
    private const val LIMIT = 20
    private val file = File(PATH)
    private val listFile = file.listFiles()
    private var count = 0
    private var currentPosition = 0

    fun init() {
        count = listFile?.size ?: -1
        currentPosition = 0
    }

    fun getFileNames(): List<String> {
        val position = currentPosition + LIMIT - 1
        if (position >= count) {
            currentPosition = 0
            return listOf()
        }
        val range = listOf(currentPosition, position)
        val newList = listFile?.slice(range)?.map { file -> file.name } ?: mutableListOf()
        currentPosition += LIMIT
        return newList
    }
}