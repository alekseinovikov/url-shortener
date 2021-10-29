package me.alekseinovikov.url.shortener.urlshortener.persistent

import me.alekseinovikov.url.shortener.urlshortener.model.ShortUrl
import me.alekseinovikov.url.shortener.urlshortener.model.ShortedUrl
import me.alekseinovikov.url.shortener.urlshortener.model.Url

interface ShorterRepository {
    suspend fun saveIfNotExists(shorted: ShortedUrl): ShortUrl?
    suspend fun findByShort(short: ShortUrl): ShortedUrl?
    suspend fun findByOriginal(url: Url): ShortedUrl?
}