package com.smx.basil

import java.io.ByteArrayOutputStream
import javax.crypto.Cipher
import javax.crypto.CipherOutputStream
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import kotlin.experimental.and

class Util {
    companion object {
        @JvmStatic
        fun swapNibbles(data: ByteArray): ByteArray {
            val output = ByteArray(data.size)
            data.forEachIndexed { i, b ->
                var nibLow = b.and(0x0F)
                var nibHigh = b.toInt().shr(4).and(0x0F)

                var swapped = nibLow.times(16).or(nibHigh).toByte()
                output[i] = swapped
            }
            return output
        }

        @JvmStatic
        fun doCrypto(cipher: Cipher, data:ByteArray, mode:Int, key:ByteArray, ivec:ByteArray? = null): ByteArray {
            val keyParam = SecretKeySpec(key, cipher.algorithm.split('/').get(0))
            val ivParam = if(ivec != null) IvParameterSpec(ivec)
            else null

            if(ivParam != null) {
                cipher.init(mode, keyParam, ivParam)
            } else {
                cipher.init(mode, keyParam)
            }

            val baos = ByteArrayOutputStream()
            // must close CipherOutputStream to write trailing padding
            CipherOutputStream(baos, cipher).use {
                it.write(data)
            }
            return baos.toByteArray()
        }
    }
}