DROP TABLE IF EXISTS loan_account;
CREATE TABLE loan_account (
    loan_id VARCHAR(50) PRIMARY KEY,
    customer_id VARCHAR(50),
    product_id VARCHAR(20),
    disbursement_date DATE,
    currency VARCHAR(5),
    loan_status VARCHAR(10),
    loan_form VARCHAR(10),
    repayment_method VARCHAR(20),
    interest_rate DECIMAL(7, 4),
    maturity_date DATE,
    original_maturity_date DATE,
    repayment_cycle VARCHAR(10),
    total_terms INT,
    grace_period INT,
    principle_amount DECIMAL(19, 2),
    interest_amount DECIMAL(19, 2),
    penalty_amount DECIMAL(19, 2),
    principle_balance DECIMAL(19, 2),
    overdue_principle DECIMAL(19, 2),
    overdue_interest_rate DECIMAL(7, 4),
    clear_date DATE,
    daily_accrual_interest DECIMAL(19, 2),
    total_overdue_terms INT,
    current_overdue_term INT,
    current_overdue_days INT,
    last_repayment_date DATE,
    fundingRatio DECIMAL(7,4),
    created_date TIMESTAMP,
    last_modified_date TIMESTAMP
);

DROP TABLE IF EXISTS loan_bank_account;
CREATE TABLE loan_bank_account (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    LOAN_ID VARCHAR(50),
    customer_name VARCHAR(200),
    identity_no VARCHAR(50),
    account_no VARCHAR(50),
    bank_name VARCHAR(200),
    bank_no VARCHAR(100),
    bank_account_type VARCHAR(20),
    created_date TIMESTAMP,
    last_modified_date TIMESTAMP
);

DROP TABLE IF EXISTS loan_disbursement;
CREATE TABLE loan_disbursement (
    loan_id VARCHAR(50) PRIMARY KEY,
    product_id VARCHAR(20),
    disbursement_date DATE,
    tracking_id VARCHAR(32),
    disbursement_amount DECIMAL(19,2),
    customer_id VARCHAR(50),
    maturity_date DATE,
    currency VARCHAR(10),
    repayment_method VARCHAR(20),
    interest_rate DECIMAL(7,4),
    overdue_interest_rate DECIMAL(7,4),
    repayment_cycle VARCHAR(10),
    total_terms INT,
    grace_period INT,
    disbursement_status VARCHAR(10),
    fail_type VARCHAR(10),
    partnerId VARCHAR(20),
    created_date TIMESTAMP,
    last_modified_date TIMESTAMP
);

DROP TABLE IF EXISTS loan_repayment_schedule;
CREATE TABLE loan_repayment_schedule (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    LOAN_ID VARCHAR(50),
    period_no INT,
    principle_amount DECIMAL(19,2),
    interest_amount DECIMAL(19,2),
    penalty_amount DECIMAL(19,2),
    start_date DATE,
    end_date DATE,
    clear_date DATE,
    repay_principle_amount DECIMAL(19,2),
    repay_interest_amount DECIMAL(19,2),
    repay_penalty_amount DECIMAL(19,2),
    schedule_status VARCHAR(10),
    created_date TIMESTAMP,
    last_modified_date TIMESTAMP
);

DROP TABLE IF EXISTS loan_payment_outbox;
CREATE TABLE loan_payment_outbox (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    tracking_id VARCHAR(32) NOT NULL,
    domain_event_type VARCHAR(50) NOT NULL,
    payload TEXT NOT NULL,
    outbox_status VARCHAR(20) NOT NULL,
    created_date TIMESTAMP,
    last_modified_date TIMESTAMP,
    UNIQUE INDEX uk_tracking_id (tracking_id)
);
