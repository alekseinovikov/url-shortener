package me.alekseinovikov.url.shortener.urlshortener.knanoid

import kotlin.math.ln
import kotlin.random.Random

private val defaultRandom = Random
private val defaultAlphabet = "_-0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray()

private const val defaultSize = 21

fun randomNanoId(
    size: Int = defaultSize,
    alphabet: CharArray = defaultAlphabet,
    random: Random = defaultRandom
): String {
    validateArguments(alphabet, size)

    val mask = calculateMask(alphabet)
    val step = calculateStep(mask, size, alphabet)

    val resultStringBuilder = StringBuilder()

    while (true) {
        val bytes = ByteArray(step)
        random.nextBytes(bytes)

        for (i in 0 until step) {
            val index = bytes[i].toInt() and mask
            if (index < alphabet.size) {
                resultStringBuilder.append(alphabet[index])

                if (size == resultStringBuilder.length) {
                    return resultStringBuilder.toString()
                }
            }
        }
    }
}

private fun calculateStep(mask: Int, size: Int, chars: CharArray) =
    kotlin.math.ceil(1.6 * mask * size / chars.size).toInt()

private fun calculateMask(chars: CharArray) =
    (2 shl (kotlin.math.floor(ln((chars.size - 1).toDouble()) / ln(2.0))).toInt()) - 1

private fun validateArguments(chars: CharArray, size: Int) {
    if (chars.isEmpty() || chars.size >= 256) throw IllegalArgumentException("chars must be in range (0..256)")
    if (size <= 0) throw IllegalArgumentException("size must be more than zero")
}