package com.dvo.bankAccountManager.mapper

import com.dvo.bankAccountManager.entity.Pins
import com.dvo.bankAccountManager.web.model.request.UpdatePinsRequest
import com.dvo.bankAccountManager.web.model.request.UpsertPinsRequest
import com.dvo.bankAccountManager.web.model.response.PinsResponse
import org.mapstruct.*

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
interface PinsMapper {
    fun pinsToResponse(pins: Pins): PinsResponse

    @Mapping(target = "id", ignore = true)
    fun requestToPins(request: UpsertPinsRequest): Pins

    @Mappings(
        Mapping(target = "id", ignore = true),
        Mapping(target = "pinEq", ignore = true)
    )
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    fun updateRequestToPins(request: UpdatePinsRequest, @MappingTarget pins: Pins): Pins
}