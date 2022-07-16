package com.jean.cleanarchitecture.account.adapter.out.persistence

import org.springframework.data.jpa.repository.JpaRepository

interface SpringDataJpaAccountRepository: JpaRepository<AccountJpaEntity, Long>
