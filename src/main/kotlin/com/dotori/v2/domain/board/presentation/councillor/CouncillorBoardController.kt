package com.dotori.v2.domain.board.presentation.councillor

import com.dotori.v2.domain.board.presentation.data.req.CreateBoardReqDto
import com.dotori.v2.domain.board.presentation.data.req.ModifyBoardReqDto
import com.dotori.v2.domain.board.service.CreateBoardService
import com.dotori.v2.domain.board.service.ModifyBoardService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import javax.validation.Valid

@RestController
@RequestMapping("/v2/councillor/board")
class CouncillorBoardController(
    private val createBoardService: CreateBoardService,
    private val modifyBoardService: ModifyBoardService
) {
    @PostMapping
    fun createBoard(
        @RequestPart(value = "files", required = false) multipartFiles: List<MultipartFile>?,
        @Valid @RequestPart(value = "boardDto") createBoardReqDto: CreateBoardReqDto
    ): ResponseEntity<Void> =
        createBoardService.execute(createBoardReqDto, multipartFiles)
            .run { ResponseEntity.status(HttpStatus.CREATED).build() }

    @PutMapping("/{board_id}")
    fun modifyBoard(
        @Valid @RequestBody modifyBoardReqDto: ModifyBoardReqDto,
        @PathVariable board_id: Long
    ): ResponseEntity<Void> =
        modifyBoardService.execute(modifyBoardReqDto, board_id)
            .run { ResponseEntity.status(HttpStatus.OK).build() }

}