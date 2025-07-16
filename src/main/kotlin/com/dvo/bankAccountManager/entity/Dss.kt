package com.dvo.bankAccountManager.entity

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDate

@Entity(name = "d_ss")
@Table(name = "d_ss")
data class Dss(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "rep_date", nullable = false)
    var repDate: LocalDate = LocalDate.now(),

    @Column(name = "sbal_kod", nullable = false, columnDefinition = "varchar(5)")
    var sbalKod: String = "",

    @Column(name = "ss_kod", nullable = false, columnDefinition = "varchar(15)")
    var ssKod: String = "",

    @Column(name = "ost", nullable = false)
    var ost: BigDecimal = BigDecimal(0)
)
