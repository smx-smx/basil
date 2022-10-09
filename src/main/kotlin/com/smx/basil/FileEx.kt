package com.smx.basil

import java.io.File
import java.io.RandomAccessFile
import java.nio.ByteBuffer
import java.nio.channels.FileChannel

class FileEx {
    companion object {
        @JvmStatic
        fun File.toByteBuffer() : ByteBuffer {
            return RandomAccessFile(path, "r")
                .channel.map(FileChannel.MapMode.READ_ONLY, 0, length())
        }
    }
}