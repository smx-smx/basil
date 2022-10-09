package com.smx.basil

import com.smx.basil.IntRangeEx.Companion.exclusive
import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer
import java.nio.charset.StandardCharsets

class ByteBufferEx {
    companion object {
        @JvmStatic
        inline fun <T> ByteBuffer.performAt(offset:Int, cb: ByteBuffer.() -> T): T{
            val savedPos = this.position()
            this.position(offset)
            val ret = cb(this)
            this.position(savedPos)
            return ret
        }

        @JvmStatic
        fun ByteBuffer.asSequence(): Sequence<Byte> {
            return sequence {
                val pos = position()
                while (hasRemaining()) {
                    yield(get())
                }
                position(pos)
            }
        }

        @JvmStatic
        fun ByteBuffer.asBitSequence(order: BitOrder = BitOrder.MSB_FIRST): Sequence<Boolean> {
            return sequence {
                while(hasRemaining()) {
                    val b = get().toInt()
                    when(order){
                        BitOrder.LSB_FIRST -> {
                            yield(((b shr 0) and 1) == 1)
                            yield(((b shr 1) and 1) == 1)
                            yield(((b shr 2) and 1) == 1)
                            yield(((b shr 3) and 1) == 1)
                            yield(((b shr 4) and 1) == 1)
                            yield(((b shr 5) and 1) == 1)
                            yield(((b shr 6) and 1) == 1)
                            yield(((b shr 7) and 1) == 1)
                        }
                        BitOrder.MSB_FIRST -> {
                            yield(((b shr 7) and 1) == 1)
                            yield(((b shr 6) and 1) == 1)
                            yield(((b shr 5) and 1) == 1)
                            yield(((b shr 4) and 1) == 1)
                            yield(((b shr 3) and 1) == 1)
                            yield(((b shr 2) and 1) == 1)
                            yield(((b shr 1) and 1) == 1)
                            yield(((b shr 0) and 1) == 1)
                        }
                    }
                }
            }
        }

        val ByteBuffer.byte: Byte
            get() = this.get()

        val ByteBuffer.ubyte: UByte
            get() = this.get().toUByte()

        val ByteBuffer.ushort: UShort
            get() = short.toUShort()
        
        val ByteBuffer.uint: UInt
            get() = int.toUInt()

        val ByteBuffer.ulong: ULong
            get() = long.toULong()
        
        @JvmStatic
        fun ByteBuffer.getUShort(index: Int): UShort {
            return getShort(index).toUShort()
        }

        @JvmStatic
        fun ByteBuffer.getUInt(index: Int): UInt {
            return getInt(index).toUInt()
        }

        @JvmStatic
        fun ByteBuffer.getBytes(amount:Int): ByteArray {
            return ByteArray(amount).also { get(it) }
        }

        @JvmStatic
        fun ByteBuffer.getUBytes(amount:Int): UByteArray {
            return UByteArray(amount).also { arr ->
                IntRange.exclusive(0, amount).forEach {
                    arr[it] = get().toUByte()
                }
            }
        }

        @JvmStatic
        fun ByteBuffer.getBytes(): ByteArray{
            return getBytes(limit())
        }

        @JvmStatic
        fun ByteBuffer.readCString(): String {
            val baos = ByteArrayOutputStream()

            while (hasRemaining()) {
                val ch = get()
                if (ch == 0x00.toByte()) break
                baos.write(ch.toInt())
            }

            return String(baos.toByteArray(), StandardCharsets.UTF_8)
        }

        @JvmStatic
        fun ByteBuffer.readRemaining() : ByteArray {
            return getBytes(remaining())
        }

        @JvmStatic
        fun ByteBuffer.putUByte(value: UByte){
            put(value.toByte())
        }

        @JvmStatic
        fun ByteBuffer.putUShort(value: UShort){
            putShort(value.toShort())
        }

        @JvmStatic
        fun ByteBuffer.putUInt(value: UInt) {
            putInt(value.toInt())
        }

        @JvmStatic
        fun ByteBuffer.putULong(value: ULong){
            putLong(value.toLong())
        }
    }
}