package com.dvo.bankAccountManager.validation

import jakarta.validation.Constraint
import kotlin.reflect.KClass

@MustBeDocumented
@Constraint(validatedBy = [PinsFilterValidValidator::class])
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class PinsFilterValid(
    val message: String = "Поля для пагинации должны быть указаны!",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Any>> = []
)
