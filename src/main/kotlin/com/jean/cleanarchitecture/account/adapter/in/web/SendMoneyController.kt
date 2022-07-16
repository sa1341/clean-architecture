package com.jean.cleanarchitecture.account.adapter.`in`.web

import com.jean.cleanarchitecture.account.application.port.`in`.SendMoneyCommand
import com.jean.cleanarchitecture.account.application.port.`in`.SendMoneyUseCase
import com.jean.cleanarchitecture.account.domain.Account.AccountId
import com.jean.cleanarchitecture.account.domain.Money
import com.jean.cleanarchitecture.common.WebAdapter
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RequestMapping("/api/v1/accounts")
@WebAdapter
@RestController
class SendMoneyController(
    private val sendMoneyUseCase: SendMoneyUseCase
) {

    @PostMapping(path = ["/send/{sourceAccountId}/{targetAccountId}/{amount}"])
    fun sendMoney(
        @PathVariable("sourceAccountId") sourceAccountId: Long,
        @PathVariable("targetAccountId") targetAccountId: Long,
        @PathVariable amount: Long
    ) {

        val command = SendMoneyCommand(
            AccountId(sourceAccountId),
            AccountId(targetAccountId),
            Money.of(amount)
        )

        sendMoneyUseCase.sendMoney(command)
    }
}
