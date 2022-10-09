package com.smx.basil

import org.junit.jupiter.api.Test
import java.nio.ByteBuffer
import javax.crypto.Cipher
import kotlin.test.assertEquals

class CryptoStreamTest {
    @Test
    fun decryptCryptoStream() {
        // 16 ints, 64 bytes, 4 blocks
        val buf = ByteBuffer.allocate(16*4)
        repeat(16){
            buf.putInt(it)
        }

        val key = ByteArray(16)
        val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")

        val encrypted = Util.doCrypto(cipher, buf.array(), Cipher.ENCRYPT_MODE, key)
        val seq = CryptoStream().decrypt(encrypted.asSequence(), cipher, key)
        val decrypted = ByteBuffer.wrap(seq.toList().toByteArray())
        repeat(16){
            assertEquals(it, decrypted.int)
        }
    }
}