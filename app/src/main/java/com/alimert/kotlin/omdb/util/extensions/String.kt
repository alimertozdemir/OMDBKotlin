package com.alimert.kotlin.omdb.util.extensions

import android.app.Activity
import android.support.annotation.StringRes
import android.text.Spannable
import android.text.SpannableStringBuilder

fun Activity.getFormattedString(@StringRes sResource: Int, content: String?): SpannableStringBuilder {

    var textContent = content ?: "N/A"
    val formattedString = String.format(getString(sResource), textContent)
    val boldLength = formattedString.length - textContent.length
    val result = SpannableStringBuilder(formattedString)
    result.setSpan(android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0,
            boldLength, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    return result

}