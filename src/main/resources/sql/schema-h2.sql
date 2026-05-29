-- =============================================
-- 剧本杀管理系统 H2 建表脚本（开发环境）
-- =============================================

-- 用户表
CREATE TABLE IF NOT EXISTS t_user (
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    username        VARCHAR(50)  NOT NULL,
    password        VARCHAR(255) NOT NULL,
    real_name       VARCHAR(50)  DEFAULT NULL,
    phone           VARCHAR(20)  DEFAULT NULL,
    avatar          VARCHAR(255) DEFAULT NULL,
    role            VARCHAR(20)  NOT NULL DEFAULT 'PLAYER',
    status          VARCHAR(20)  NOT NULL DEFAULT 'ACTIVE',
    open_id         VARCHAR(100) DEFAULT NULL,
    commission_rate INT          DEFAULT 0,
    introduction    VARCHAR(500) DEFAULT NULL,
    create_time     TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    update_time     TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    deleted         TINYINT      DEFAULT 0,
    UNIQUE (username)
);

-- 剧本表
CREATE TABLE IF NOT EXISTS t_script (
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    name             VARCHAR(100)   NOT NULL,
    cover_image      VARCHAR(255)   DEFAULT NULL,
    category         VARCHAR(50)    DEFAULT NULL,
    difficulty       VARCHAR(20)    DEFAULT 'NOVICE',
    player_count     INT            DEFAULT 6,
    duration         INT            DEFAULT 180,
    description      VARCHAR(2000)  DEFAULT NULL,
    characters       VARCHAR(2000)  DEFAULT NULL,
    price            DECIMAL(10,2)  DEFAULT 0.00,
    member_price     DECIMAL(10,2)  DEFAULT 0.00,
    holiday_surcharge DECIMAL(10,2) DEFAULT 0.00,
    dm_material_url  VARCHAR(255)   DEFAULT NULL,
    dm_manual_url    VARCHAR(255)   DEFAULT NULL,
    status           VARCHAR(20)    NOT NULL DEFAULT 'DRAFT',
    play_count       INT            DEFAULT 0,
    rating           DECIMAL(3,2)   DEFAULT 5.00,
    sort_order       INT            DEFAULT 0,
    create_time      TIMESTAMP      DEFAULT CURRENT_TIMESTAMP,
    update_time      TIMESTAMP      DEFAULT CURRENT_TIMESTAMP,
    deleted          TINYINT        DEFAULT 0
);

-- 房间表
CREATE TABLE IF NOT EXISTS t_room (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(50)  NOT NULL,
    style       VARCHAR(30)  DEFAULT NULL,
    capacity    INT          DEFAULT 8,
    description VARCHAR(255) DEFAULT NULL,
    status      VARCHAR(20)  NOT NULL DEFAULT 'IDLE',
    create_time TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    deleted     TINYINT      DEFAULT 0
);

-- 场次时段表
CREATE TABLE IF NOT EXISTS t_session (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(30) NOT NULL,
    start_time  TIME        NOT NULL,
    end_time    TIME        NOT NULL,
    sort_order  INT         DEFAULT 0,
    enabled     TINYINT     DEFAULT 1,
    create_time TIMESTAMP   DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP   DEFAULT CURRENT_TIMESTAMP,
    deleted     TINYINT     DEFAULT 0
);

-- 剧本场次排期表
CREATE TABLE IF NOT EXISTS t_script_session (
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    script_id       BIGINT       NOT NULL,
    room_id         BIGINT       DEFAULT NULL,
    session_id      BIGINT       DEFAULT NULL,
    dm_user_id      BIGINT       DEFAULT NULL,
    schedule_date   DATE         NOT NULL,
    max_players     INT          DEFAULT 6,
    current_players INT          DEFAULT 0,
    status          VARCHAR(20)  NOT NULL DEFAULT 'OPEN',
    is_full_booking TINYINT      DEFAULT 0,
    create_time     TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    update_time     TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    deleted         TINYINT      DEFAULT 0
);

-- 订单表
CREATE TABLE IF NOT EXISTS t_order (
    id                BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_no          VARCHAR(32)   NOT NULL,
    script_session_id BIGINT        NOT NULL,
    user_id           BIGINT        NOT NULL,
    script_id         BIGINT        NOT NULL,
    script_name       VARCHAR(100)  DEFAULT NULL,
    room_name         VARCHAR(50)   DEFAULT NULL,
    session_time      TIMESTAMP     DEFAULT NULL,
    dm_name           VARCHAR(50)   DEFAULT NULL,
    total_amount      DECIMAL(10,2) DEFAULT 0.00,
    paid_amount       DECIMAL(10,2) DEFAULT 0.00,
    deposit_amount    DECIMAL(10,2) DEFAULT 0.00,
    pay_method        VARCHAR(20)   DEFAULT NULL,
    order_type        VARCHAR(20)   NOT NULL,
    status            VARCHAR(30)   NOT NULL DEFAULT 'WAITING_CARPOOL',
    pay_time          TIMESTAMP     DEFAULT NULL,
    refund_time       TIMESTAMP     DEFAULT NULL,
    refund_amount     DECIMAL(10,2) DEFAULT 0.00,
    remark            VARCHAR(500)  DEFAULT NULL,
    create_time       TIMESTAMP     DEFAULT CURRENT_TIMESTAMP,
    update_time       TIMESTAMP     DEFAULT CURRENT_TIMESTAMP,
    deleted           TINYINT       DEFAULT 0,
    UNIQUE (order_no)
);

-- 订单参与者表
CREATE TABLE IF NOT EXISTS t_order_participant (
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id      BIGINT      NOT NULL,
    user_id       BIGINT      DEFAULT NULL,
    player_name   VARCHAR(50)  DEFAULT NULL,
    player_phone  VARCHAR(20)  DEFAULT NULL,
    checked_in    TINYINT      DEFAULT 0,
    check_in_time TIMESTAMP    DEFAULT NULL,
    create_time   TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    update_time   TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    deleted       TINYINT      DEFAULT 0
);

-- 会员表
CREATE TABLE IF NOT EXISTS t_member (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id     BIGINT        NOT NULL,
    level       VARCHAR(20)   DEFAULT 'NORMAL',
    points      INT           DEFAULT 0,
    total_spent DECIMAL(12,2) DEFAULT 0.00,
    total_plays INT           DEFAULT 0,
    create_time TIMESTAMP     DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP     DEFAULT CURRENT_TIMESTAMP,
    deleted     TINYINT       DEFAULT 0,
    UNIQUE (user_id)
);

-- 优惠券表
CREATE TABLE IF NOT EXISTS t_coupon (
    id             BIGINT AUTO_INCREMENT PRIMARY KEY,
    name           VARCHAR(100)  NOT NULL,
    type           VARCHAR(20)   DEFAULT 'CASH',
    coupon_value   DECIMAL(10,2) DEFAULT 0.00,
    min_amount     DECIMAL(10,2) DEFAULT 0.00,
    total_count    INT           DEFAULT 0,
    claimed_count  INT           DEFAULT 0,
    limit_per_user INT           DEFAULT 1,
    valid_from     TIMESTAMP     DEFAULT NULL,
    valid_to       TIMESTAMP     DEFAULT NULL,
    enabled        TINYINT       DEFAULT 1,
    create_time    TIMESTAMP     DEFAULT CURRENT_TIMESTAMP,
    update_time    TIMESTAMP     DEFAULT CURRENT_TIMESTAMP,
    deleted        TINYINT       DEFAULT 0
);

-- DM绩效表
CREATE TABLE IF NOT EXISTS t_dm_performance (
    id                   BIGINT AUTO_INCREMENT PRIMARY KEY,
    dm_user_id           BIGINT        NOT NULL,
    stat_date            DATE          NOT NULL,
    daily_session_count  INT           DEFAULT 0,
    monthly_session_count INT          DEFAULT 0,
    daily_commission     DECIMAL(10,2) DEFAULT 0.00,
    monthly_commission   DECIMAL(10,2) DEFAULT 0.00,
    avg_rating           DECIMAL(3,2)  DEFAULT 5.00,
    create_time          TIMESTAMP     DEFAULT CURRENT_TIMESTAMP,
    update_time          TIMESTAMP     DEFAULT CURRENT_TIMESTAMP,
    deleted              TINYINT       DEFAULT 0
);

-- 系统配置表
CREATE TABLE IF NOT EXISTS t_system_config (
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    config_key   VARCHAR(100)  NOT NULL,
    config_value VARCHAR(500)  DEFAULT NULL,
    description  VARCHAR(255)  DEFAULT NULL,
    config_group VARCHAR(30)   DEFAULT 'BUSINESS',
    create_time  TIMESTAMP     DEFAULT CURRENT_TIMESTAMP,
    update_time  TIMESTAMP     DEFAULT CURRENT_TIMESTAMP,
    deleted      TINYINT       DEFAULT 0,
    UNIQUE (config_key)
);

-- 支付记录表
CREATE TABLE IF NOT EXISTS t_payment_record (
    id             BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id       BIGINT        NOT NULL,
    transaction_no VARCHAR(64)   DEFAULT NULL,
    amount         DECIMAL(10,2) DEFAULT 0.00,
    type           VARCHAR(10)   DEFAULT 'PAY',
    pay_method     VARCHAR(20)   DEFAULT NULL,
    status         VARCHAR(20)   DEFAULT 'PENDING',
    pay_time       TIMESTAMP     DEFAULT NULL,
    create_time    TIMESTAMP     DEFAULT CURRENT_TIMESTAMP,
    update_time    TIMESTAMP     DEFAULT CURRENT_TIMESTAMP,
    deleted        TINYINT       DEFAULT 0
);
