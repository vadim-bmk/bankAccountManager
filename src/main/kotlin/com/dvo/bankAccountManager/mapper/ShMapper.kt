package com.dvo.bankAccountManager.mapper

import com.dvo.bankAccountManager.entity.Sh
import com.dvo.bankAccountManager.web.model.request.UpdateShRequest
import com.dvo.bankAccountManager.web.model.request.UpsertShRequest
import com.dvo.bankAccountManager.web.model.response.ShResponse
import org.mapstruct.*

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
interface ShMapper {
    fun shToResponse(sh: Sh): ShResponse

    @Mapping(target = "id", ignore = true)
    fun requestToSh(request: UpsertShRequest): Sh

    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    fun updateRequestToSh(request: UpdateShRequest, @MappingTarget sh: Sh): Sh
}