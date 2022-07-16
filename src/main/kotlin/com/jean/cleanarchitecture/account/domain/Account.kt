package com.jean.cleanarchitecture.account.domain

import org.springframework.beans.factory.annotation.Value
import java.time.LocalDateTime
import java.util.Optional
import javax.persistence.Embeddable

class Account(
    val id: AccountId?,
    var baselineBalance: Money,
    var activityWindow: ActivityWindow
) {

    fun getId(): Optional<AccountId> {
        return Optional.ofNullable(this.id)
    }

    fun calculateBalance(): Money {
        return Money.add(
            this.baselineBalance,
            this.activityWindow.calculateBalance(this.id!!)
        )
    }

    fun withdraw(money: Money, targetAccountId: AccountId): Boolean {

        if (!mayWithdraw(money)) {
            return false
        }

        val withdrawal = Activity(
            this.id!!, this.id!!, targetAccountId, LocalDateTime.now(), money
        )

        this.activityWindow.addActivity(withdrawal)
        return true
    }

    private fun mayWithdraw(money: Money): Boolean {
        return Money.add(
            this.calculateBalance()!!,
            money.negate())
            .isPositiveOrZero()
    }

    fun deposit(money: Money, sourceAccountId: AccountId): Boolean {
        val deposit = Activity(this.id!!, sourceAccountId, this.id!!, LocalDateTime.now(), money)
        this.activityWindow.addActivity(deposit)
        return true
    }

    companion object {

        fun withoutId(baselineBalance: Money,
                      activityWindow: ActivityWindow): Account {
            return Account(null, baselineBalance, activityWindow)
        }

        fun withId(accountId: AccountId,
                   baselineBalance: Money,
                   activityWindow: ActivityWindow): Account {
            return Account(accountId, baselineBalance, activityWindow)
        }
    }

    class AccountId(val value: Long)
}

