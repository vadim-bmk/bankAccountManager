package com.dvo.bankAccountManager.entity

import jakarta.persistence.*
import java.time.LocalDate

@Entity(name = "rdk2pf")
@Table(name = "rdk2pf")
data class Rdk2pf(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "pin_eq", nullable = false, columnDefinition = "varchar(6)")
    var pinEq: String = "",

    @Column(name = "ser", columnDefinition = "varchar(4)")
    var ser: String? = null,

    @Column(name = "num", columnDefinition = "varchar(10)")
    var num: String? = null,

    @Column(name = "otd", columnDefinition = "varchar(255)")
    var otd: String? = null,

    @Column(name = "open")
    var open: LocalDate? = LocalDate.now(),

    @Column(name = "close")
    var close: LocalDate? = null
)
