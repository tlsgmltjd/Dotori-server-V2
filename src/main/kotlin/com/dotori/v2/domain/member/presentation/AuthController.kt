package com.dotori.v2.domain.member.presentation

import com.dotori.v2.domain.member.presentation.dto.req.SignInReqDto
import com.dotori.v2.domain.member.presentation.dto.req.SignupReqDto
import com.dotori.v2.domain.member.presentation.dto.res.SignInResDto
import com.dotori.v2.domain.member.service.LogoutService
import com.dotori.v2.domain.member.service.SignInService
import com.dotori.v2.domain.member.service.SignupService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/v2/auth")
class AuthController(
    private val signupService: SignupService,
    private val signInService: SignInService,
    private val logoutService: LogoutService,
) {
    @PostMapping("/signup")
    fun signup(@Valid @RequestBody signupReqDto: SignupReqDto): ResponseEntity<Void> =
        signupService.execute(signupReqDto)
            .run { ResponseEntity.ok().build() }

    @PostMapping
    fun signIn(@Valid @RequestBody signInReqDto: SignInReqDto): ResponseEntity<SignInResDto> =
        ResponseEntity.ok(signInService.execute(signInReqDto))

    @DeleteMapping("/logout")
    fun logout(): ResponseEntity<Void> =
        logoutService.execute()
            .run { ResponseEntity.ok().build() }
}