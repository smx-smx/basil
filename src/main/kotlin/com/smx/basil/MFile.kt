package com.smx.basil

import java.io.File
import java.io.RandomAccessFile
import java.nio.ByteBuffer
import java.nio.channels.FileChannel
import java.nio.file.Path

class MFile : AutoCloseable {
    private val raf:RandomAccessFile;

    var mem: ByteBuffer
        get() = field
        private set(value){
            field = value
        }

    constructor(filePath: String) : this(Path.of(filePath))
    constructor(filePath: Path) : this(filePath.toFile())
    constructor(file: File, openMode: String = "r"){
        raf = RandomAccessFile(file, openMode)

        val mapMode = if(openMode.contains("w")){
            FileChannel.MapMode.READ_WRITE
        } else {
            FileChannel.MapMode.READ_ONLY
        }

        mem = raf.channel.map(mapMode, 0, raf.length())
    }

    override fun close() {
        raf.close()
    }
}