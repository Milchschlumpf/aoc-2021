package util

import kotlin.math.pow

fun binaryToDecimal(binaryString: String): Float {
    var binaryAsDecimal = 0.0f

    val length = binaryString.length
    binaryString.toCharArray().forEachIndexed { index, c ->
        binaryAsDecimal = binaryAsDecimal.plus(if(c == '1') berechneBinaryValue(length - index - 1) else 0.0f)
    }

    return binaryAsDecimal
}

fun berechneBinaryValue(index: Int): Float {
    return 2.0f.pow(index)
}