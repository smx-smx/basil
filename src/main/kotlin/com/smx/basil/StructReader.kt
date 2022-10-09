package com.smx.basil

import com.smx.basil.ByteBufferEx.Companion.getBytes
import com.smx.basil.ByteBufferEx.Companion.getUBytes
import com.smx.basil.ByteBufferEx.Companion.ubyte
import com.smx.basil.ByteBufferEx.Companion.uint
import com.smx.basil.ByteBufferEx.Companion.ulong
import com.smx.basil.ByteBufferEx.Companion.ushort
import java.nio.ByteBuffer
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets

class StructReader(val buf: ByteBuffer) {
    private val bitRdr = ByteBufferBitReader(buf)
    private val initialPos:Int = buf.position()

    val byte get() = buf.get()
    val ubyte get() = buf.ubyte
    val short get() = buf.short
    val ushort get() = buf.ushort
    val int get() = buf.int
    val uint get() = buf.uint
    val ulong get() = buf.ulong

    fun sizeof(): Int {
        return buf.position() - initialPos
    }

    fun getUBytes(amount: Int) = buf.getUBytes(amount)
    fun getBytes(amount: Int) = buf.getBytes(amount)

    fun getBits(amount: Int) = bitRdr.getBits(amount)

    fun getString(numCharacters: Int, charset: Charset = StandardCharsets.US_ASCII): String {
        val charSize = charset
            .newEncoder()
            .maxBytesPerChar()
            .toInt()

        val data = getBytes(numCharacters * charSize).let { ByteBuffer.wrap(it) }
        return charset.decode(data)
            .asSequence()
            .takeWhile { it.code != 0 }
            .joinToString("")
    }

    fun <T> performAt(offset: Int, cb: (r: StructReader) -> T): T {
        val nowPos = buf.position()
        buf.position(offset)
        val result = cb(this)
        buf.position(nowPos)
        return result
    }
}