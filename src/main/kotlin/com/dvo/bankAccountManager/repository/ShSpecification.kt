package com.dvo.bankAccountManager.repository

import com.dvo.bankAccountManager.entity.Sh
import com.dvo.bankAccountManager.web.filter.ShFilter
import org.springframework.data.jpa.domain.Specification
import java.time.LocalDate

object ShSpecification {

    fun withFilter(filter: ShFilter): Specification<Sh>? {
        return byId(filter.id)
            ?.and(bySbalKod(filter.sbalKod))
            ?.and(bySsKod(filter.ssKod))
            ?.and(bySsNaim(filter.ssNaim))
            ?.and(actP(filter.actP))
            ?.and(byDate(filter.ssOpen, filter.ssClose))
            ?.and(byKv(filter.kv))
            ?.and(byPinEq(filter.pinEq))
    }

    private fun byId(id: Long?): Specification<Sh>? {
        if (id == null) return null

        return Specification { root, _, cr ->
            cr.equal(root.get<Long>(Sh::id.name), id)
        }
    }

    private fun bySbalKod(sbalKod: String?): Specification<Sh>? {
        if (sbalKod == null) return null

        return Specification { root, _, cr ->
            cr.like(root.get<String>(Sh::sbalKod.name), "%$sbalKod%")
        }
    }

    private fun bySsKod(ssKod: String?): Specification<Sh>? {
        if (ssKod == null) return null

        return Specification { root, _, cr ->
            cr.like(root.get<String>(Sh::ssKod.name), "%$ssKod%")
        }
    }

    private fun bySsNaim(ssNaim: String?): Specification<Sh>? {
        if (ssNaim == null) return null

        return Specification { root, _, cr ->
            cr.like(root.get<String>(Sh::ssNaim.name), "%$ssNaim%")
        }
    }

    private fun actP(actP: Boolean?): Specification<Sh>? {
        if (actP == null) return null

        return Specification { root, _, cr ->
            cr.equal(root.get<Boolean>(Sh::actP.name), actP)
        }
    }

    private fun byDate(ssOpen: LocalDate?, ssClose: LocalDate?): Specification<Sh>? {
        if (ssOpen == null && ssClose == null) return null

        if (ssOpen == null) {
            return Specification { root, _, cr ->
                cr.lessThanOrEqualTo(root.get<LocalDate>(Sh::ssClose.name), ssClose)
            }
        }

        if (ssClose == null) {
            return Specification { root, _, cr ->
                cr.greaterThanOrEqualTo(root.get<LocalDate>(Sh::ssOpen.name), ssOpen)
            }
        }

        return Specification { root, _, cr ->
            cr.between(root.get<LocalDate>(Sh::ssOpen.name), ssOpen, ssClose)
        }
    }

    private fun byKv(kv: String?): Specification<Sh>? {
        if (kv == null) return null

        return Specification { root, _, cr ->
            cr.like(root.get<String>(Sh::kv.name), "%$kv%")
        }
    }

    private fun byPinEq(pinEq: String?): Specification<Sh>? {
        if (pinEq == null) return null

        return Specification { root, _, cr ->
            cr.like(root.get<String>(Sh::pinEq.name), "%$pinEq%")
        }
    }
}