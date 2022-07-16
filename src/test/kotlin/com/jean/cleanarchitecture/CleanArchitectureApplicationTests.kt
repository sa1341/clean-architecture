package com.jean.cleanarchitecture

import com.jean.cleanarchitecture.account.domain.Account
import com.jean.cleanarchitecture.account.domain.Activity
import com.jean.cleanarchitecture.account.domain.Money
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime

@SpringBootTest
class CleanArchitectureApplicationTests {

	@Test
	fun contextLoads() {
		val activity = Activity(Account.Companion.AccountId(1L),
			sourceAccountId = Account.Companion.AccountId(2L),
			targetAccountId = Account.Companion.AccountId(3L),
			timestamp = LocalDateTime.now(),
			Money.of(3000),
			"jean"
		)

	}

}
