package com.dvo.bankAccountManager.validation

import com.dvo.bankAccountManager.web.filter.ShFilter
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import org.apache.commons.lang3.ObjectUtils

class ShFilterValidValidator: ConstraintValidator<ShFilterValid, ShFilter> {
    override fun isValid(p0: ShFilter, p1: ConstraintValidatorContext): Boolean {
       return !ObjectUtils.anyNull(p0.pageNumber, p0.pageSize)
    }
}