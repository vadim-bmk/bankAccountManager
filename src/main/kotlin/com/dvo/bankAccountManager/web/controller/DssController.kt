package com.dvo.bankAccountManager.web.controller

import com.dvo.bankAccountManager.mapper.DssMapper
import com.dvo.bankAccountManager.service.DssService
import com.dvo.bankAccountManager.web.model.request.UpdateDssRequest
import com.dvo.bankAccountManager.web.model.request.UpsertDssRequest
import com.dvo.bankAccountManager.web.model.response.DssResponse
import io.swagger.v3.oas.annotations.Operation
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/dss")
class DssController(
    private val dssService: DssService,
    private val dssMapper: DssMapper
) {
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all DSS", description = "Get all DSS", tags = ["dss"])
    fun findAll(): ResponseEntity<List<DssResponse>> {
        return ResponseEntity.ok(
            dssService.findAll().map(
                dssMapper::dssToResponse
            )
        )
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get DSS", description = "Get DSS by ID", tags = ["dss"])
    fun findById(@PathVariable id: Long): ResponseEntity<DssResponse> {
        return ResponseEntity.ok(
            dssMapper.dssToResponse(
                dssService.findById(id)
            )
        )
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create DSS", description = "Create DSS", tags = ["dss"])
    fun create(@RequestBody @Valid request: UpsertDssRequest): ResponseEntity<DssResponse> {
        return ResponseEntity.ok(
            dssMapper.dssToResponse(
                dssService.save(
                    dssMapper.requestToDss(request)
                )
            )
        )
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update DSS", description = "Update DSS by ID", tags = ["dss"])
    fun update(
        @PathVariable id: Long,
        @RequestBody @Valid request: UpdateDssRequest
    ): ResponseEntity<DssResponse> {
        return ResponseEntity.ok(
            dssMapper.dssToResponse(
                dssService.update(id, request)
            )
        )
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete DSS", description = "Delete DSS by ID", tags = ["dss"])
    fun deleteById(@PathVariable id: Long) {
        dssService.deleteById(id)
    }
}