package com.dotori.v2.domain.email.service.impl

import com.dotori.v2.domain.email.domain.repository.EmailCertificateRepository
import com.dotori.v2.domain.email.service.EmailCheckService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class EmailCheckServiceImpl(
    private val emailCertificateRepository: EmailCertificateRepository,
) : EmailCheckService {
    @Transactional(rollbackFor = [Exception::class])
    override fun execute(key: String): Boolean {
        val findByKey = emailCertificateRepository.findByKey(key) ?: throw RuntimeException()
        if (!findByKey.expiredTime.isAfter(LocalDateTime.now()))
            throw RuntimeException()
        val emailCertificate = findByKey.verify()
        emailCertificateRepository.save(emailCertificate)
        return true
    }
}
