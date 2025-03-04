DROP TABLE IF EXISTS loan_account;
CREATE TABLE loan_account (
    loan_id VARCHAR(50) PRIMARY KEY,
    customer_id VARCHAR(50) NOT NULL,
    product_id VARCHAR(20) NOT NULL,
    disbursement_date DATE,
    currency VARCHAR(5) NOT NULL,
    loan_status VARCHAR(10) NOT NULL,
    loan_form VARCHAR(10) NOT NULL,
    repayment_method VARCHAR(10) NOT NULL,
    interest_rate DECIMAL(7, 4) NOT NULL,
    maturity_date DATE,
    original_maturity_date DATE,
    repayment_cycle VARCHAR(10) NOT NULL,
    total_terms INT NOT NULL,
    grace_period INT NOT NULL,
    principle_amount DECIMAL(19, 2) NOT NULL,
    interest_amount DECIMAL(19, 2) NOT NULL,
    penalty_amount DECIMAL(19, 2) NOT NULL,
    principle_balance DECIMAL(19, 2) NOT NULL,
    overdue_principle DECIMAL(19, 2) NOT NULL,
    overdue_interest_rate DECIMAL(7, 4) NOT NULL,
    clear_date DATE,
    daily_accrual_interest DECIMAL(19, 2) NOT NULL,
    total_overdue_terms INT NOT NULL,
    current_overdue_term INT NOT NULL,
    current_overdue_days INT NOT NULL,
    last_repayment_date DATE,
    createdDate TIMESTAMP,
    lastModifiedDate TIMESTAMP
);

DROP TABLE IF EXISTS loan_bank_account;
CREATE TABLE loan_bank_account (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    LOAN_ID VARCHAR(50) NOT NULL,
    customer_name VARCHAR(200),
    identity_no VARCHAR(50),
    account_no VARCHAR(50),
    bank_name VARCHAR(200),
    bank_no VARCHAR(100),
    bank_account_type VARCHAR(10),
    createdDate TIMESTAMP,
    lastModifiedDate TIMESTAMP
);

DROP TABLE IF EXISTS loan_disbursement;
CREATE TABLE loan_disbursement (
    loan_id VARCHAR(50) PRIMARY KEY,
    product_id VARCHAR(20) NOT NULL,
    disbursement_date DATE NOT NULL,
    tracking_id VARCHAR(50),
    disbursement_amount DECIMAL(19,2) NOT NULL,
    customer_id VARCHAR(50) NOT NULL,
    maturity_date DATE NOT NULL,
    currency VARCHAR(10) NOT NULL,
    repayment_method VARCHAR(10) NOT NULL,
    interest_rate DECIMAL(7,4) NOT NULL,
    overdue_interest_rate DECIMAL(7,4) NOT NULL,
    repayment_cycle VARCHAR(10) NOT NULL,
    total_terms INT NOT NULL,
    grace_period INT NOT NULL,
    disbursement_status VARCHAR(10) NOT NULL,
    fail_type VARCHAR(10),
    createdDate TIMESTAMP,
    lastModifiedDate TIMESTAMP
);

DROP TABLE IF EXISTS loan_repayment_schedule;
CREATE TABLE loan_repayment_schedule (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    LOAN_ID VARCHAR(50) NOT NULL,
    period_no INT NOT NULL,
    principle_amount DECIMAL(19,2) NOT NULL,
    interest_amount DECIMAL(19,2) NOT NULL,
    penalty_amount DECIMAL(19,2) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    clear_date DATE,
    repay_principle_amount DECIMAL(19,2),
    repay_interest_amount DECIMAL(19,2),
    repay_penalty_amount DECIMAL(19,2),
    schedule_status VARCHAR(10) NOT NULL,
    createdDate TIMESTAMP,
    lastModifiedDate TIMESTAMP
);
