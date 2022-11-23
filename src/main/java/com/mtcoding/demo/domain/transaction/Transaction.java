package com.mtcoding.demo.domain.transaction;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.ForeignKey;
import javax.persistence.ConstraintMode;

import com.mtcoding.demo.config.enums.TransactionEnum;
import com.mtcoding.demo.domain.AudingTime;
import com.mtcoding.demo.domain.account.Account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "transaction")
@Entity
public class Transaction extends AudingTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)) // 포린키 해제 NULL 허용하기 위해서
    @ManyToOne(fetch = FetchType.LAZY)
    private Account withdrawAccount; // 출금 계좌 (보내는 분)

    @JoinColumn(foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    @ManyToOne(fetch = FetchType.LAZY)
    private Account depositAccount; // 입금 계좌 (받는 분)

    @Column(nullable = false)
    private Long amount; // 입출금, 이체 금액
    private Long withdrawAccountBalance; // 출금계좌 현재 잔액
    private Long depositAccountBalance; // 입금계좌 현재 잔액

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionEnum gubun; // 입금(자동화기기로부터), 출금(자동화기기로), 이체(다른계좌)

}
