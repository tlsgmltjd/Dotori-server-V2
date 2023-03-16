package com.dotori.v2.domain.self_study.presentation.admin

import com.dotori.v2.domain.self_study.presentation.dto.req.SelfStudyLimitReqDto
import com.dotori.v2.domain.self_study.presentation.dto.res.SelfStudyInfoResDto
import com.dotori.v2.domain.self_study.presentation.dto.res.SelfStudyMemberListResDto
import com.dotori.v2.domain.self_study.service.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v2/admin/self-study")
class AdminSelfStudyController(
    private val getSelfStudyInfoService: GetSelfStudyInfoService,
    private val getSelfStudyRankService: GetSelfStudyRankService,
    private val getSelfStudyByMemberNameService: GetSelfStudyByMemberNameService,
    private val getSelfStudyByStuNumService: GetSelfStudyByStuNumService,
    private val banSelfStudyService: BanSelfStudyService,
    private val cancelBanSelfStudyService: CancelBanSelfStudyService,
    private val changeSelfStudyLimitService: ChangeSelfStudyLimitService
) {

    @GetMapping("/info")
    fun getSelfStudyInfo(): ResponseEntity<SelfStudyInfoResDto> =
        ResponseEntity.ok(getSelfStudyInfoService.execute())

    @GetMapping("/rank")
    fun getSelfStudyRank(): ResponseEntity<SelfStudyMemberListResDto> =
        ResponseEntity.ok(getSelfStudyRankService.execute())

    @GetMapping
    fun getSelfStudyByMemberName(@RequestParam memberName: String): ResponseEntity<SelfStudyMemberListResDto> =
        ResponseEntity.ok(getSelfStudyByMemberNameService.execute(memberName))

    @GetMapping("/{classId}")
    fun getSelfStudyByStuNum(@PathVariable classId: String): ResponseEntity<SelfStudyMemberListResDto> =
        ResponseEntity.ok(getSelfStudyByStuNumService.execute(classId))

    @PutMapping("/ban/{user_id}")
    fun banSelfStudyDeveloper(@PathVariable("user_id") id: Long): ResponseEntity<Void> =
        banSelfStudyService.execute(id)
            .run { ResponseEntity.ok().build() }

    @PutMapping("/ban/cancel/{user_id}")
    fun cancelBanSelfStudyDeveloper(@PathVariable("user_id") id: Long): ResponseEntity<Void> =
        cancelBanSelfStudyService.execute(id)
            .run { ResponseEntity.ok().build() }

    @PatchMapping("/limit")
    fun updateSelfStudyLimit(@RequestBody changeSelfStudyLimitReqDto: SelfStudyLimitReqDto): ResponseEntity<Void> =
        changeSelfStudyLimitService.execute(changeSelfStudyLimitReqDto)
            .run { ResponseEntity.ok().build() }
}