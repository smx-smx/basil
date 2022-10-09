package com.smx.basil

import com.smx.basil.ByteBufferEx.Companion.putUByte
import com.smx.basil.ByteBufferEx.Companion.putUInt
import com.smx.basil.ByteBufferEx.Companion.putULong
import com.smx.basil.ByteBufferEx.Companion.putUShort
import java.nio.ByteBuffer
import java.nio.CharBuffer
import java.nio.charset.Charset

fun Byte.serialize(buf: ByteBuffer){ buf.put(this) }
fun UByte.serialize(buf: ByteBuffer){ buf.putUByte(this) }
fun Short.serialize(buf: ByteBuffer){ buf.putShort(this) }
fun UShort.serialize(buf: ByteBuffer){ buf.putUShort(this) }
fun Int.serialize(buf: ByteBuffer){ buf.putInt(this) }
fun UInt.serialize(buf: ByteBuffer){ buf.putUInt(this) }
fun Long.serialize(buf: ByteBuffer){ buf.putLong(this) }
fun ULong.serialize(buf: ByteBuffer){ buf.putULong(this) }

fun ByteArray.serialize(buf: ByteBuffer){ buf.put(this) }
fun UByteArray.serialize(buf: ByteBuffer){
    this.forEach { it.serialize(buf) }
}

fun String.serialize(buf: ByteBuffer, length: Int, charset: Charset): ByteBuffer? {
    val cb = CharBuffer.allocate(length)
    forEach { cb.put(it) }
    return charset.encode(cb)
}