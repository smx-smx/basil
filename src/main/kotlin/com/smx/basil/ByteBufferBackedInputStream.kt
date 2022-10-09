package com.smx.basil

import java.io.IOException
import java.io.InputStream
import java.nio.ByteBuffer
import kotlin.math.min


class ByteBufferBackedInputStream(buf: ByteBuffer) : InputStream() {
    var buf: ByteBuffer

    @Throws(IOException::class)
    override fun read(): Int {
        return if (!buf.hasRemaining()) {
            -1
        } else buf.get().toInt()
    }

    @Throws(IOException::class)
    override fun read(bytes: ByteArray, off: Int, len: Int): Int {
        var len = len
        if (!buf.hasRemaining()) {
            return -1
        }
        len = min(len, buf.remaining())
        buf.get(bytes, off, len)
        return len
    }

    init {
        this.buf = buf
    }
}