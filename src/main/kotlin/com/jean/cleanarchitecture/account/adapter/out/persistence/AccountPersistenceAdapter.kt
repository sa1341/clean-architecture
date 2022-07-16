package com.jean.cleanarchitecture.account.adapter.out.persistence

import com.jean.cleanarchitecture.account.application.port.out.LoadAccountPort
import com.jean.cleanarchitecture.account.application.port.out.UpdateAccountStatePort
import com.jean.cleanarchitecture.account.domain.Account
import com.jean.cleanarchitecture.account.domain.Account.AccountId
import com.jean.cleanarchitecture.common.PersistenceAdapter
import java.time.LocalDateTime
import javax.persistence.EntityNotFoundException

@PersistenceAdapter
class AccountPersistenceAdapter(
    val accountRepository: SpringDataJpaAccountRepository,
    val activityRepository: ActivityRepository,
    val accountMapper: AccountMapper
): LoadAccountPort, UpdateAccountStatePort {


    override fun loadAccount(accountId: AccountId,
                             baselineDate: LocalDateTime): Account {

        val account = accountRepository.findById(accountId.value)
            .orElseThrow { EntityNotFoundException() }

        val activities = activityRepository.findByOwnerSince(
            accountId.value,
            baselineDate
        )

        val withdrawalBalance = orZero(
            activityRepository
                .getWithdrawalBalanceUntil(
                    accountId.value,
                    baselineDate
                )
        )

        val depositBalance = orZero(
            activityRepository.getDepositBalanceUntil(
                accountId.value,
                baselineDate
            )
        )

        return accountMapper.mapToDomainEntity(
            account,
            activities,
            withdrawalBalance,
            depositBalance
        )
    }

    override fun updateActivities(account: Account) {
        for (activity in account.activityWindow.getActivities()) {
            if (activity.id == null) {
                activityRepository.save(accountMapper.mapToJpaEntity(activity)!!)
            }
        }
    }

    fun orZero(value: Long?): Long {
        return value ?: return 0L
    }
}
