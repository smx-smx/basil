package com.smx.basil

import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

class CryptoStream() {

    fun decrypt(
        data:Sequence<Byte>,
        cipher: Cipher,
        key:ByteArray,
        ivec:ByteArray? = null
    ) : Sequence<Byte> {
        val keyParam = SecretKeySpec(key, cipher.algorithm.split('/').get(0))
        val ivParam = when(ivec){
            null -> null
            else -> IvParameterSpec(ivec)
        }
        when(ivParam){
            null -> cipher.init(Cipher.DECRYPT_MODE, keyParam)
            else -> cipher.init(Cipher.DECRYPT_MODE, keyParam, ivParam)
        }

        return sequence<ByteArray> {
            var seq = data
            while(true) {
                val block = seq.take(cipher.blockSize)
                    .also { seq = seq.drop(cipher.blockSize) }
                    .toList()
                    .toByteArray()

                if(block.isEmpty()) return@sequence

                val isFullBlock = (block.size % cipher.blockSize) == 0
                val decrypted = when (isFullBlock) {
                    true -> cipher.update(block)
                    else -> cipher.doFinal(block)
                }
                yield(decrypted)
            }
        }.flatMap { it.iterator().asSequence() }
    }
}