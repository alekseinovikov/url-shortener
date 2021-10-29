package me.alekseinovikov.url.shortener.urlshortener.service

import me.alekseinovikov.url.shortener.urlshortener.model.ShortUrl
import me.alekseinovikov.url.shortener.urlshortener.model.Url

interface ShorterService {
    suspend fun createIfNotExists(url: Url): ShortUrl
    suspend fun getOriginal(short: ShortUrl): Url?
}