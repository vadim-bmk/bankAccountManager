package com.dvo.bankAccountManager.validation

import com.dvo.bankAccountManager.web.filter.PinsFilter
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import org.apache.commons.lang3.ObjectUtils

class PinsFilterValidValidator: ConstraintValidator<PinsFilterValid, PinsFilter> {
    override fun isValid(p0: PinsFilter, p1: ConstraintValidatorContext): Boolean {
        return !ObjectUtils.anyNull(p0.pageNumber, p0.pageSize)
    }
}