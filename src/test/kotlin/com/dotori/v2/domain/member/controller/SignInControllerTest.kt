package com.dotori.v2.domain.member.controller

import com.dotori.v2.domain.auth.presentation.AuthController
import com.dotori.v2.domain.auth.util.AuthConverter
import com.dotori.v2.domain.auth.util.impl.AuthConverterImpl
import com.dotori.v2.domain.auth.presentation.data.dto.SignInGAuthDto
import com.dotori.v2.domain.auth.presentation.data.req.SignInGAuthReqDto
import com.dotori.v2.domain.auth.presentation.data.res.SignInResDto
import com.dotori.v2.domain.auth.service.LogoutService
import com.dotori.v2.domain.auth.service.RefreshTokenService
import com.dotori.v2.domain.auth.service.SignInEmailAndPasswordService
import com.dotori.v2.domain.auth.service.SignInGAuthService
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpStatus
import java.time.ZonedDateTime

class SignInControllerTest : BehaviorSpec({
    @Bean
    fun authConverter(): AuthConverter =
        AuthConverterImpl()

    val refreshTokenService = mockk<RefreshTokenService>()
    val signInGAuthService = mockk<SignInGAuthService>()
    val signInEmailAndPasswordService = mockk<SignInEmailAndPasswordService>()
    val logoutService = mockk<LogoutService>()
    val authController = AuthController(
        authConverter = authConverter(),
        signInGAuthService = signInGAuthService,
        signInEmailAndPasswordService =signInEmailAndPasswordService,
        refreshTokenService = refreshTokenService,
        logoutService = logoutService
    )
    given("요청이 들어오면") {
        val dto = SignInGAuthDto(
            code = "thisIsCode"
        )
        val request = SignInGAuthReqDto(
            code = "thisIsCode"
        )
        `when`("is received") {
            every { signInGAuthService.execute(dto) } returns SignInResDto(
                accessToken = "thisIsAccess",
                refreshToken = "thisIsRefresh",
                accessExp = ZonedDateTime.now(),
                refreshExp = ZonedDateTime.now()
            )
            val response = authController.signInGAuth(request)

            then("서비스가 한번은 실행되어야 함") {
                verify(exactly = 1) { signInGAuthService.execute(dto) }
            }
            then("response status should be success") {
                response.statusCode shouldBe HttpStatus.OK
            }
        }
    }
})