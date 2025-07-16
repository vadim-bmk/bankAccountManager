package com.dvo.bankAccountManager.mapper

import com.dvo.bankAccountManager.entity.Rdk2pf
import com.dvo.bankAccountManager.web.model.request.UpdateRdk2pfRequest
import com.dvo.bankAccountManager.web.model.request.UpsertRdk2pfRequest
import com.dvo.bankAccountManager.web.model.response.Rdk2pfResponse
import org.mapstruct.*

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
interface Rdk2pfMapper {
    fun rdk2pfToResponse(rdk2pf: Rdk2pf): Rdk2pfResponse

    @Mapping(target = "id", ignore = true)
    fun requestToRdk2pf(request: UpsertRdk2pfRequest): Rdk2pf
    
    @Mappings(
        Mapping(target = "id", ignore = true),
        Mapping(target = "pinEq", ignore = true)
    )
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    fun updateRequestToRdk2pf(request: UpdateRdk2pfRequest, @MappingTarget rdk2pf: Rdk2pf): Rdk2pf
}