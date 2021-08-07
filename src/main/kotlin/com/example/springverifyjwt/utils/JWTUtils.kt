package com.example.springverifyjwt.utils

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import java.io.IOException
import java.security.GeneralSecurityException
import java.security.KeyFactory
import java.security.interfaces.RSAPublicKey
import java.security.spec.X509EncodedKeySpec
import java.util.*

val decoder: Base64.Decoder = Base64.getDecoder()
val regex1 = Regex("\\.")

fun decodeJWTToken(token: String): String? {
    val chunks: List<String> = token.split(regex1)
    val header: String = String(decoder.decode(chunks[0]))
    val payload: String = String(decoder.decode(chunks[1]))
    return "$header $payload"
}

@Throws(Exception::class)
fun decodeJWTToken(token: String, secretKey: String): String? {
    val chunks: List<String> = token.split(regex1)
    val header = String(decoder.decode(chunks[0]))
    val payload = String(decoder.decode(chunks[1]))

    val algorithm = Algorithm.RSA256(getPublicKeyFromString(secretKey), null);
    val verifier = JWT.require(algorithm).build()
    verifier.verify(token)

    return "$header $payload"
}

@Throws(IOException::class, GeneralSecurityException::class)
fun getPublicKeyFromString(key: String): RSAPublicKey {

    var publicKeyPEM = key
    publicKeyPEM = publicKeyPEM.replace("-----BEGIN PUBLIC KEY-----\n", "")
    publicKeyPEM = publicKeyPEM.replace("-----END PUBLIC KEY-----", "")

    val encoded: ByteArray = Base64.getDecoder().decode(publicKeyPEM)

    val kf: KeyFactory = KeyFactory.getInstance("RSA")
    return kf.generatePublic(X509EncodedKeySpec(encoded)) as RSAPublicKey
}