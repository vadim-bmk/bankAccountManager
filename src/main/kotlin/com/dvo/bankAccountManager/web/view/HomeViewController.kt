package com.dvo.bankAccountManager.web.view

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class HomeViewController {
    @GetMapping("/")
    fun home(): String {
        return "index"
    }
}