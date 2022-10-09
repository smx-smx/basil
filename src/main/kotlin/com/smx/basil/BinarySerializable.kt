package com.smx.basil

import java.nio.ByteBuffer

interface BinarySerializable {
    fun serialize(buf: ByteBuffer)
}