package com.dvo.bankAccountManager.repository

import com.dvo.bankAccountManager.entity.Rdk2pf
import com.dvo.bankAccountManager.web.filter.Rdk2pfFilter
import org.springframework.data.jpa.domain.Specification
import java.time.LocalDate

object Rdk2pfSpecification {
    fun withFilter(filter: Rdk2pfFilter): Specification<Rdk2pf>? {
        return byId(filter.id)
            ?.and(byPinEq(filter.pinEq))
            ?.and(bySer(filter.ser))
            ?.and(byNum(filter.num))
            ?.and(byOtd(filter.otd))
            ?.and(byDate(filter.open, filter.close))
    }

    fun byId(id: Long?): Specification<Rdk2pf>? {
        if (id == null) return null

        return Specification { root, _, cr ->
            cr.equal(root.get<Long>(Rdk2pf::id.name), id)
        }
    }

    fun byPinEq(pinEq: String?): Specification<Rdk2pf>? {
        if (pinEq == null) return null

        return Specification { root, _, cr ->
            cr.like(root.get<String>(Rdk2pf::pinEq.name), "%$pinEq%")
        }
    }

    fun bySer(ser: String?): Specification<Rdk2pf>? {
        if (ser == null) return null

        return Specification { root, _, cr ->
            cr.like(root.get<String>(Rdk2pf::ser.name), "%$ser%")
        }
    }

    fun byNum(num: String?): Specification<Rdk2pf>? {
        if (num == null) return null

        return Specification { root, _, cr ->
            cr.like(root.get<String>(Rdk2pf::num.name), "%$num%")
        }
    }

    fun byOtd(otd: String?): Specification<Rdk2pf>? {
        if (otd == null) return null

        return Specification { root, _, cr ->
            cr.like(root.get<String>(Rdk2pf::otd.name), "%$otd%")
        }
    }

    fun byDate(open: LocalDate?, close: LocalDate?): Specification<Rdk2pf>? {
        if (open == null && close == null) return null

        if (open == null) {
            return Specification { root, _, cr ->
                cr.lessThanOrEqualTo(root.get<LocalDate>(Rdk2pf::close.name), close)
            }
        }

        if (close == null) {
            return Specification { root, _, cr ->
                cr.greaterThanOrEqualTo(root.get<LocalDate>(Rdk2pf::open.name), open)
            }
        }

        return Specification { root, _, cr ->
            cr.between(root.get<LocalDate>(Rdk2pf::open.name), open, close)
        }
    }
}