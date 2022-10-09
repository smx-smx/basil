package com.smx.basil

import java.nio.ByteBuffer

class ByteArrayEx {
    companion object {
        @JvmStatic
        fun ByteArray.toByteBuffer(): ByteBuffer {
            return ByteBuffer.wrap(this)!!
        }
    }
}