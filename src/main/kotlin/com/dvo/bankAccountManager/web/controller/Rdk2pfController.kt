package com.dvo.bankAccountManager.web.controller

import com.dvo.bankAccountManager.mapper.Rdk2pfMapper
import com.dvo.bankAccountManager.service.Rdk2pfService
import com.dvo.bankAccountManager.web.model.request.UpdateRdk2pfRequest
import com.dvo.bankAccountManager.web.model.request.UpsertRdk2pfRequest
import com.dvo.bankAccountManager.web.model.response.Rdk2pfResponse
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
@RequestMapping("/api/rdk2pf")
class Rdk2pfController(
    private val rdk2pfService: Rdk2pfService,
    private val rdk2pfMapper: Rdk2pfMapper
) {
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all RDK2PF", description = "Get all RDK2PF", tags = ["rdk2pf"])
    fun findAll(): ResponseEntity<List<Rdk2pfResponse>> {
        return ResponseEntity.ok(
            rdk2pfService.findAll().map(
                rdk2pfMapper::rdk2pfToResponse
            )
        )
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get RDK2PF", description = "Get RDK2PF by ID", tags = ["rdk2pf"])
    fun findById(@PathVariable id: Long): ResponseEntity<Rdk2pfResponse> {
        return ResponseEntity.ok(
            rdk2pfMapper.rdk2pfToResponse(
                rdk2pfService.findById(id)
            )
        )
    }

    @GetMapping("/pin/{pinEq}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get RDK2PF", description = "Get RDK2PF by pinEq", tags = ["rdk2pf"])
    fun findByPinEq(@PathVariable pinEq: String): ResponseEntity<List<Rdk2pfResponse>> {
        return ResponseEntity.ok(
            rdk2pfService.findByPinEq(pinEq).map(
                rdk2pfMapper::rdk2pfToResponse
            )
        )
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create RDK2PF", description = "Create RDK2PF", tags = ["rdk2pf"])
    fun create(@RequestBody @Valid request: UpsertRdk2pfRequest): ResponseEntity<Rdk2pfResponse> {
        return ResponseEntity.ok(
            rdk2pfMapper.rdk2pfToResponse(
                rdk2pfService.save(
                    rdk2pfMapper.requestToRdk2pf(request)
                )
            )
        )
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update RDK2PF", description = "Update RDK2PF by ID", tags = ["rdk2pf"])
    fun update(
        @PathVariable id: Long,
        @RequestBody @Valid request: UpdateRdk2pfRequest
    ): ResponseEntity<Rdk2pfResponse> {
        return ResponseEntity.ok(
            rdk2pfMapper.rdk2pfToResponse(
                rdk2pfService.update(id, request)
            )
        )
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Delete RDK2PF", description = "Delete RDK2PF by ID", tags = ["rdk2pf"])
    fun deleteById(@PathVariable id: Long){
        rdk2pfService.deleteById(id)
    }

}