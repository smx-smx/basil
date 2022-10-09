package com.smx.basil

class SequenceEx {
    companion object {
        @JvmStatic
        fun Sequence<Byte>.takeBytes(count:Int): ByteArray {
            val arr = ByteArray(count)
            this.take(count)
                .forEachIndexed { index, byte -> arr[index] = byte }
            return arr
        }

        @JvmStatic
        fun Sequence<UByte>.takeBytes(count:Int): UByteArray {
            val arr = UByteArray(count)
            this.take(count)
                .forEachIndexed { index, byte -> arr[index] = byte }
            return arr
        }
    }
}