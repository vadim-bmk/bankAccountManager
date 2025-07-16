package com.dvo.bankAccountManager.repository

import com.dvo.bankAccountManager.entity.Pins
import com.dvo.bankAccountManager.web.filter.PinsFilter
import org.springframework.data.jpa.domain.Specification

object PinsSpecification {
    fun withFilter(filter: PinsFilter): Specification<Pins>?{
        return byId(filter.id)
            ?.and(byPinEq(filter.pinEq))
            ?.and(byInn(filter.inn))
            ?.and(byFirstName(filter.firstName))
            ?.and(byLastName(filter.lastName))
            ?.and(byPatronymic(filter.patronymic))
    }

    fun byId(id: Long?): Specification<Pins>? {
        if (id == null) return null

        return Specification { root, _, cr ->
            cr.equal(root.get<Long>(Pins::id.name), id)
        }
    }

    fun byPinEq(pinEq: String?): Specification<Pins>? {
        if (pinEq == null) return null

        return Specification { root, _, cr ->
            cr.like(root.get<String>(Pins::pinEq.name), "%$pinEq%")
        }
    }

    fun byInn(inn: String?): Specification<Pins>? {
        if (inn == null) return null

        return Specification { root, _, cr ->
            cr.like(root.get<String>(Pins::inn.name), "%$inn%")
        }
    }

    fun byFirstName(firstName: String?): Specification<Pins>? {
        if (firstName == null) return null

        return Specification { root, _, cr ->
            cr.like(root.get<String>(Pins::firstName.name), "%$firstName%")
        }
    }

    fun byLastName(lastName: String?): Specification<Pins>? {
        if (lastName == null) return null

        return Specification { root, _, cr ->
            cr.like(root.get<String>(Pins::lastName.name), "%$lastName%")
        }
    }

    fun byPatronymic(patronymic: String?): Specification<Pins>? {
        if (patronymic == null) return null

        return Specification { root, _, cr ->
            cr.like(root.get<String>(Pins::patronymic.name), "%$patronymic%")
        }
    }

}