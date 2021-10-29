package me.alekseinovikov.url.shortener.urlshortener.service

import me.alekseinovikov.url.shortener.urlshortener.knanoid.randomNanoId
import me.alekseinovikov.url.shortener.urlshortener.model.ShortUrl
import me.alekseinovikov.url.shortener.urlshortener.model.ShortedUrl
import me.alekseinovikov.url.shortener.urlshortener.model.Url
import me.alekseinovikov.url.shortener.urlshortener.persistent.ShorterRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class ShorterServiceImpl(
    private val shorterRepository: ShorterRepository,
    @Value("\${short.length}") private val shortLength: Int
) : ShorterService {

    override suspend fun createIfNotExists(url: Url): ShortUrl {
        shorterRepository.findByOriginal(url)?.let { return it.short }

        var savedShort: String? = null
        while (savedShort == null) {
            val short = randomNanoId(size = shortLength)
            savedShort = shorterRepository.saveIfNotExists(ShortedUrl(short = short, url = url))
        }

        return savedShort
    }

    override suspend fun getOriginal(short: ShortUrl): Url? = shorterRepository.findByShort(short)?.url

}