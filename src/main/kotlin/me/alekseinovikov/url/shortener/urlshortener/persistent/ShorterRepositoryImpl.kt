package me.alekseinovikov.url.shortener.urlshortener.persistent

import me.alekseinovikov.url.shortener.urlshortener.model.ShortUrl
import me.alekseinovikov.url.shortener.urlshortener.model.ShortedUrl
import me.alekseinovikov.url.shortener.urlshortener.model.Url
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.awaitSingleOrNull
import org.springframework.stereotype.Repository

@Repository
class ShorterRepositoryImpl(private val databaseClient: DatabaseClient) : ShorterRepository {

    override suspend fun saveIfNotExists(shorted: ShortedUrl): ShortUrl? = databaseClient.sql(
        """
        INSERT INTO urls(short, url) 
        VALUES(:short, :url) ON CONFLICT(short) DO NOTHING 
        RETURNING short 
    """.trimIndent()
    )
        .bind("short", shorted.short)
        .bind("url", shorted.url)
        .fetch()
        .awaitSingleOrNull()
        ?.parseShortUrl()

    override suspend fun findByShort(short: ShortUrl): ShortedUrl? = databaseClient.sql(
        """
        SELECT short, url FROM urls WHERE short = :short
    """.trimIndent()
    )
        .bind("short", short)
        .fetch()
        .awaitSingleOrNull()
        ?.parseShortedUrl()

    override suspend fun findByOriginal(url: Url): ShortedUrl? = databaseClient.sql(
        """
        SELECT short, url FROM urls WHERE url = :url
    """.trimIndent()
    )
        .bind("url", url)
        .fetch()
        .awaitSingleOrNull()
        ?.parseShortedUrl()

    private fun Map<String, Any>.parseShortUrl() = this["short"] as ShortUrl
    private fun Map<String, Any>.parseShortedUrl() = ShortedUrl(
        short = this["short"] as ShortUrl,
        url = this["url"] as Url
    )

}