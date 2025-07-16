package com.dvo.bankAccountManager.entity

import jakarta.persistence.*
import java.time.LocalDate

@Entity(name = "sh")
@Table(name = "sh")
data class Sh(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "sbal_kod", nullable = false, columnDefinition = "varchar(5)")
    var sbalKod: String = "",

    @Column(name = "ss_kod", nullable = false, columnDefinition = "varchar(15)")
    var ssKod: String = "",

    @Column(name = "ss_naim", columnDefinition = "varchar(255)")
    var ssNaim: String? = null,

    @Column(name = "act_p")
    var actP: Boolean? = null,

    @Column(name = "ss_open", nullable = false)
    var ssOpen: LocalDate = LocalDate.now(),

    @Column(name = "ss_close")
    var ssClose: LocalDate? = null,

    @Column(name = "kv", nullable = false, columnDefinition = "varchar(3)")
    var kv: String = "",

    @Column(name = "pin_eq", nullable = false, columnDefinition = "varchar(6)")
    var pinEq: String = ""
)
