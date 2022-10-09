package com.smx.basil

class UIntDissector(val value:UInt) {
    fun extractMSB(offset: Int, amount: Int): UInt {
        val mask = amount.toUInt() - 1u
        return value.shr(32 - offset).and(mask)
    }
}