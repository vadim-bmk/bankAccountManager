package com.dvo.bankAccountManager.mapper

import com.dvo.bankAccountManager.entity.Dss
import com.dvo.bankAccountManager.web.model.request.UpdateDssRequest
import com.dvo.bankAccountManager.web.model.request.UpsertDssRequest
import com.dvo.bankAccountManager.web.model.response.DssResponse
import org.mapstruct.*

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
interface DssMapper {
    fun dssToResponse(dss: Dss): DssResponse

    @Mapping(target = "id", ignore = true)
    fun requestToDss(request: UpsertDssRequest): Dss

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "repDate", ignore = true)
    @Mapping(target = "sbalKod", ignore = true)
    @Mapping(target = "ssKod", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    fun updateRequestToDss(request: UpdateDssRequest, @MappingTarget dss: Dss): Dss
}