package com.jean.cleanarchitecture.account.application.service

import com.jean.cleanarchitecture.account.application.port.`in`.SendMoneyCommand
import com.jean.cleanarchitecture.account.application.port.`in`.SendMoneyUseCase
import com.jean.cleanarchitecture.account.application.port.out.AccountLock
import com.jean.cleanarchitecture.account.application.port.out.LoadAccountPort
import com.jean.cleanarchitecture.account.application.port.out.UpdateAccountStatePort
import com.jean.cleanarchitecture.common.UseCase
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime


@UseCase
@Transactional
class SendMoneyService(
    val loadAccountPort: LoadAccountPort,
    val accountLock: AccountLock,
    val updateAccountStatePort: UpdateAccountStatePort,
): SendMoneyUseCase {

    override fun sendMoney(command: SendMoneyCommand): Boolean {

        checkThreshold(command)

        val baselineDate = LocalDateTime.now().minusDays(10)

        val sourceAccount = loadAccountPort.loadAccount(
            command.sourceAccountId,
            baselineDate
        )

        val targetAccount = loadAccountPort.loadAccount(
            command.targetAccountId,
            baselineDate
        )

        val sourceAccountId = sourceAccount.getId()
            .orElseThrow { IllegalStateException("expected source account ID not to be empty") }

        val targetAccountId = targetAccount.getId()
            .orElseThrow { IllegalStateException("expected source account ID not to be empty") }

        accountLock.lockAccount(sourceAccountId)
        if (!sourceAccount.withdraw(command.money, targetAccountId)) {
            accountLock.releaseAccount(sourceAccountId)
            return false
        }

        accountLock.lockAccount(targetAccountId)
        if (!targetAccount.deposit(command.money, sourceAccountId)) {
            accountLock.releaseAccount(sourceAccountId)
            accountLock.releaseAccount(targetAccountId)
            return false
        }

        updateAccountStatePort.updateActivities(sourceAccount)
        updateAccountStatePort.updateActivities(targetAccount)

        accountLock.releaseAccount(sourceAccountId)
        accountLock.releaseAccount(targetAccountId)

        return true
    }

    private fun checkThreshold(command: SendMoneyCommand) {
        if (command.money.isGreaterThan(MoneyTransferProperties.maximumTransferThreshold)) {
            throw ThresholdExceededException(MoneyTransferProperties.maximumTransferThreshold, command.money)
        }
    }
}
