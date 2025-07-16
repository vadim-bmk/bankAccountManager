package com.dvo.bankAccountManager.entity

import jakarta.persistence.*

@Entity(name = "pins")
@Table(name = "pins")
data class Pins(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "pin_eq", unique = true, nullable = false, columnDefinition = "varchar(6)")
    var pinEq: String,

    @Column(name = "inn", columnDefinition = "varchar(12)")
    var inn: String? = null,

    @Column(name = "first_name", columnDefinition = "varchar(255)")
    var firstName: String? = null,

    @Column(name = "last_name", columnDefinition = "varchar(255)")
    var lastName: String? = null,

    @Column(name = "patronymic", columnDefinition = "varchar(255)")
    var patronymic: String? = null
)
