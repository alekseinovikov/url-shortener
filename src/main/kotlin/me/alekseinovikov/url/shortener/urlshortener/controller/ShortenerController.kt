package me.alekseinovikov.url.shortener.urlshortener.controller

import me.alekseinovikov.url.shortener.urlshortener.service.ShorterService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
class ShortenerController(private val shorterService: ShorterService) {

    @PostMapping
    suspend fun create(@RequestBody url: String): String = shorterService.createIfNotExists(url)

    @GetMapping("/{short}")
    suspend fun redirectOriginal(@PathVariable short: String): ResponseEntity<Any> {
        shorterService.getOriginal(short)?.let {
            return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(it))
                .build()
        }

        return ResponseEntity.notFound().build()
    }

}