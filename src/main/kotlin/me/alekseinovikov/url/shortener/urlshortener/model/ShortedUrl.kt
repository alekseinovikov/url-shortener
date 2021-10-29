package me.alekseinovikov.url.shortener.urlshortener.model

typealias ShortUrl = String
typealias Url = String


data class ShortedUrl(val short: ShortUrl, val url: Url)