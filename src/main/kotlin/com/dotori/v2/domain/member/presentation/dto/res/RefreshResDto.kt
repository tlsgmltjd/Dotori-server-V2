package com.dotori.v2.domain.member.presentation.dto.res

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.ZonedDateTime

data class RefreshResDto (
    val accessToken: String,
    val refreshToken: String,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    val expiresAt: ZonedDateTime,
)