package com.dvo.bankAccountManager.web.controller

import com.dvo.bankAccountManager.mapper.PinsMapper
import com.dvo.bankAccountManager.service.PinsService
import com.dvo.bankAccountManager.web.filter.PinsFilter
import com.dvo.bankAccountManager.web.model.request.UpdatePinsRequest
import com.dvo.bankAccountManager.web.model.request.UpsertPinsRequest
import com.dvo.bankAccountManager.web.model.response.PinsResponse
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
@RequestMapping("/api/pins")
class PinsController(
    private val pinsService: PinsService,
    private val pinsMapper: PinsMapper
) {
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get pins", description = "Get all pins", tags = ["pins"])
    fun findAll(): ResponseEntity<List<PinsResponse>> {
        val pins = pinsService.findAll().map(pinsMapper::pinsToResponse)

        return ResponseEntity.ok(pins)
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get pins", description = "Get all pins by filter", tags = ["pins"])
    fun findAllByFilter(@Valid filter: PinsFilter): ResponseEntity<List<PinsResponse>> {
        val pinsList = pinsService.findAllByFilter(filter)

        return ResponseEntity.ok(pinsList.map(pinsMapper::pinsToResponse))
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get pin", description = "Get pin by id", tags = ["pins"])
    fun findById(@PathVariable id: Long): ResponseEntity<PinsResponse> {
        return ResponseEntity.ok(
            pinsMapper.pinsToResponse(
                pinsService.findById(id)
            )
        )
    }

    @GetMapping("/pin/{pinEq}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get pin", description = "Get pin by pinEq", tags = ["pins"])
    fun findByPinEq(@PathVariable pinEq: String): ResponseEntity<PinsResponse> {
        return ResponseEntity.ok(
            pinsMapper.pinsToResponse(
                pinsService.findByPinEq(pinEq)
            )
        )
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create pin", description = "Create pin", tags = ["pins"])
    fun create(@RequestBody @Valid request: UpsertPinsRequest): ResponseEntity<PinsResponse> {
        return ResponseEntity.ok(
            pinsMapper.pinsToResponse(
                pinsService.save(
                    pinsMapper.requestToPins(request)
                )
            )
        )
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update pin", description = "Update pin by ID", tags = ["pins"])
    fun update(
        @PathVariable id: Long,
        @RequestBody @Valid request: UpdatePinsRequest
    ): ResponseEntity<PinsResponse> {
        return ResponseEntity.ok(
            pinsMapper.pinsToResponse(
                pinsService.update(id, request)
            )
        )
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete pin", description = "Delete pin by ID", tags = ["pins"])
    fun delete(@PathVariable id: Long) {
        pinsService.deleteById(id)
    }

    @DeleteMapping("/delete/pin/{pinEq}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete pin", description = "Delete pin by pinEq", tags = ["pins"])
    fun deleteByPinEq(@PathVariable pinEq: String) {
        pinsService.deleteByPinEq(pinEq)
    }
}