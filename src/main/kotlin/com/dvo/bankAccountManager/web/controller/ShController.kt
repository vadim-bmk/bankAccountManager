package com.dvo.bankAccountManager.web.controller

import com.dvo.bankAccountManager.mapper.ShMapper
import com.dvo.bankAccountManager.service.ShService
import com.dvo.bankAccountManager.web.model.request.UpdateShRequest
import com.dvo.bankAccountManager.web.model.request.UpsertShRequest
import com.dvo.bankAccountManager.web.model.response.ShResponse
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
@RequestMapping("/api/sh")
class ShController(
    private val shService: ShService,
    private val shMapper: ShMapper
) {

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all SH", description = "Get all SH", tags = ["sh"])
    fun findAll(): ResponseEntity<List<ShResponse>> {
        return ResponseEntity.ok(
            shService.findAll().map(shMapper::shToResponse)
        )
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get SH", description = "Get SH by ID", tags = ["sh"])
    fun findById(@PathVariable id: Long): ResponseEntity<ShResponse> {
        return ResponseEntity.ok(
            shMapper.shToResponse(
                shService.findById(id)
            )
        )
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create SH", description = "Create SH", tags = ["sh"])
    fun create(@RequestBody @Valid request: UpsertShRequest): ResponseEntity<ShResponse> {
        return ResponseEntity.ok(
            shMapper.shToResponse(
                shService.save(
                    shMapper.requestToSh(request)
                )
            )
        )
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update SH", description = "Update SH by ID", tags = ["sh"])
    fun update(
        @PathVariable id: Long,
        @RequestBody @Valid request: UpdateShRequest
    ): ResponseEntity<ShResponse> {
        return ResponseEntity.ok(
            shMapper.shToResponse(
                shService.update(id, request)
            )
        )
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete SH", description = "Delete SH by ID", tags = ["sh"])
    fun deleteById(@PathVariable id: Long) {
        shService.deleteById(id)
    }
}