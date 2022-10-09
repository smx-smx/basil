package com.smx.basil

import com.smx.basil.ByteBufferEx.Companion.asBitSequence
import java.math.BigInteger
import java.nio.ByteBuffer
import kotlin.math.ceil

class ByteBufferBitReader(val buf:ByteBuffer) {
    var order: BitOrder = BitOrder.LSB_FIRST
    private val bits = buf.asBitSequence(order).iterator()
    private var read = 0

    fun getBits(amount: Int) : BigInteger {
        var n = BigInteger.ZERO
        bits.asSequence().take(amount).forEachIndexed { idx, bit ->
            ++read
            if(bit){
                n = n.setBit(idx)
            } else {
                n = n.clearBit(idx)
            }
        }
        return n
    }

    fun sizeof(): Int {
        return ceil(read.toDouble() / 8).toInt()
    }
}