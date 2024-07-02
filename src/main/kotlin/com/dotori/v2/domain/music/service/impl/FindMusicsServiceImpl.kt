package com.dotori.v2.domain.music.service.impl

import com.dotori.v2.domain.music.domain.entity.Music
import com.dotori.v2.domain.music.domain.repository.MusicRepository
import com.dotori.v2.domain.music.presentation.data.res.MusicListResDto
import com.dotori.v2.domain.music.presentation.data.res.MusicResDto
import com.dotori.v2.domain.music.service.FindMusicsService
import com.dotori.v2.global.config.redis.service.RedisCacheService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.LocalDateTime

@Service
@Transactional(readOnly = true, rollbackFor = [Exception::class])
class FindMusicsServiceImpl(
    private val musicRepository: MusicRepository,
    private val redisCacheService: RedisCacheService
) : FindMusicsService {

    val CACHE_KEY = "musicList"

    override fun execute(date: LocalDate): MusicListResDto {

        val cachedData = redisCacheService.getFromCache(CACHE_KEY)
        if(cachedData != null) {
            return cachedData as MusicListResDto
        }

        val response = MusicListResDto(
            content = musicRepository.findAllByCreatedDate(date)
                .map { toDto(it) }
        )

        if(date.isEqual(LocalDate.now())) {
            redisCacheService.putToCache(CACHE_KEY, response)
        }

        return response
    }

    private fun toDto(music: Music): MusicResDto =
        MusicResDto(
            id = music.id,
            url = music.url,
            title = music.title,
            thumbnail = music.thumbnail,
            username = music.member.memberName,
            email = music.member.email,
            createdTime = music.createdDate,
            stuNum = music.member.stuNum
        )
}