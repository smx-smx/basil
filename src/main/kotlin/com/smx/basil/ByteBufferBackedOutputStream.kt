package com.smx.basil

import java.io.IOException
import java.io.OutputStream
import java.nio.ByteBuffer


class ByteBufferBackedOutputStream(buf: ByteBuffer) : OutputStream() {
    var buf: ByteBuffer

    @Throws(IOException::class)
    override fun write(b: Int) {
        buf.put(b.toByte())
    }

    @Throws(IOException::class)
    override fun write(bytes: ByteArray, off: Int, len: Int) {
        buf.put(bytes, off, len)
    }

    init {
        this.buf = buf
    }
}