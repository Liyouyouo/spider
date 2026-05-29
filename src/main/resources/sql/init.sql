-- =============================================
-- 剧本杀管理系统 V1.0 数据库初始化脚本
-- =============================================

CREATE DATABASE IF NOT EXISTS jubensha DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE jubensha;

-- ============== 用户表 ==============
DROP TABLE IF EXISTS t_user;
CREATE TABLE t_user (
    id              BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
    username        VARCHAR(50)  NOT NULL COMMENT '用户名',
    password        VARCHAR(255) NOT NULL COMMENT '密码（BCrypt加密）',
    real_name       VARCHAR(50)  DEFAULT NULL COMMENT '真实姓名',
    phone           VARCHAR(20)  DEFAULT NULL COMMENT '手机号',
    avatar          VARCHAR(255) DEFAULT NULL COMMENT '头像URL',
    role            VARCHAR(20)  NOT NULL DEFAULT 'PLAYER' COMMENT '角色: PLAYER/DM/ADMIN',
    status          VARCHAR(20)  NOT NULL DEFAULT 'ACTIVE' COMMENT '状态: ACTIVE/FROZEN',
    open_id         VARCHAR(100) DEFAULT NULL COMMENT '微信openId',
    commission_rate INT          DEFAULT 0 COMMENT 'DM提成比例(%)',
    introduction    VARCHAR(500) DEFAULT NULL COMMENT 'DM个人简介',
    create_time     DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time     DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted         TINYINT      NOT NULL DEFAULT 0 COMMENT '逻辑删除: 0-未删除, 1-已删除',
    UNIQUE KEY uk_username (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 默认管理员: admin / admin123
INSERT INTO t_user (username, password, real_name, role, status) VALUES
('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5Eh', '系统管理员', 'ADMIN', 'ACTIVE');

-- ============== 剧本表 ==============
DROP TABLE IF EXISTS t_script;
CREATE TABLE t_script (
    id               BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
    name             VARCHAR(100)   NOT NULL COMMENT '剧本名称',
    cover_image      VARCHAR(255)   DEFAULT NULL COMMENT '封面图片URL',
    category         VARCHAR(50)    DEFAULT NULL COMMENT '题材分类: 硬核推理/情感沉浸/恐怖/欢乐/机制阵营/民国/古风/现代',
    difficulty       VARCHAR(20)    DEFAULT 'NOVICE' COMMENT '难度: NOVICE/ADVANCED/HARD',
    player_count     INT            DEFAULT 6 COMMENT '所需人数',
    duration         INT            DEFAULT 180 COMMENT '游玩时长(分钟)',
    description      TEXT           COMMENT '剧本简介',
    characters       TEXT           COMMENT '人物介绍(JSON数组)',
    price            DECIMAL(10,2)  DEFAULT 0.00 COMMENT '标准价格',
    member_price     DECIMAL(10,2)  DEFAULT 0.00 COMMENT '会员价格',
    holiday_surcharge DECIMAL(10,2) DEFAULT 0.00 COMMENT '节假日加价',
    dm_material_url  VARCHAR(255)   DEFAULT NULL COMMENT 'DM复盘资料URL',
    dm_manual_url    VARCHAR(255)   DEFAULT NULL COMMENT 'DM组织者手册URL',
    status           VARCHAR(20)    NOT NULL DEFAULT 'DRAFT' COMMENT '状态: ONLINE/OFFLINE/DRAFT',
    play_count       INT            DEFAULT 0 COMMENT '累计游玩次数',
    rating           DECIMAL(3,2)   DEFAULT 5.00 COMMENT '综合评分',
    sort_order       INT            DEFAULT 0 COMMENT '排序权重',
    create_time      DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time      DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted          TINYINT        NOT NULL DEFAULT 0 COMMENT '逻辑删除'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='剧本表';

-- ============== 房间表 ==============
DROP TABLE IF EXISTS t_room;
CREATE TABLE t_room (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
    name        VARCHAR(50)  NOT NULL COMMENT '包间名称',
    style       VARCHAR(30)  DEFAULT NULL COMMENT '风格: HORROR/ANCIENT/MODERN/IMMERSIVE',
    capacity    INT          DEFAULT 8 COMMENT '容纳人数',
    description VARCHAR(255) DEFAULT NULL COMMENT '房间描述',
    status      VARCHAR(20)  NOT NULL DEFAULT 'IDLE' COMMENT '状态: IDLE/OCCUPIED/MAINTENANCE',
    create_time DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted     TINYINT      NOT NULL DEFAULT 0 COMMENT '逻辑删除'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='房间表';

-- ============== 场次时段表 ==============
DROP TABLE IF EXISTS t_session;
CREATE TABLE t_session (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
    name        VARCHAR(30) NOT NULL COMMENT '场次名称: 如"下午场"/"晚场"',
    start_time  TIME        NOT NULL COMMENT '开始时间',
    end_time    TIME        NOT NULL COMMENT '结束时间',
    sort_order  INT         DEFAULT 0 COMMENT '排序',
    enabled     TINYINT     DEFAULT 1 COMMENT '是否启用',
    create_time DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted     TINYINT     NOT NULL DEFAULT 0 COMMENT '逻辑删除'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='场次时段表';

-- 默认场次
INSERT INTO t_session (name, start_time, end_time, sort_order) VALUES
('下午场', '14:00:00', '18:00:00', 1),
('晚场',   '19:00:00', '23:00:00', 2),
('通宵场', '23:30:00', '04:00:00', 3);

-- ============== 剧本场次排期表 ==============
DROP TABLE IF EXISTS t_script_session;
CREATE TABLE t_script_session (
    id              BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
    script_id       BIGINT       NOT NULL COMMENT '剧本ID',
    room_id         BIGINT       DEFAULT NULL COMMENT '房间ID',
    session_id      BIGINT       DEFAULT NULL COMMENT '场次时段ID',
    dm_user_id      BIGINT       DEFAULT NULL COMMENT 'DM用户ID',
    schedule_date   DATE         NOT NULL COMMENT '排期日期',
    max_players     INT          DEFAULT 6 COMMENT '最大拼车人数',
    current_players INT          DEFAULT 0 COMMENT '当前已拼人数',
    status          VARCHAR(20)  NOT NULL DEFAULT 'OPEN' COMMENT '状态: OPEN/FULL/CANCELLED/IN_PROGRESS/FINISHED',
    is_full_booking TINYINT      DEFAULT 0 COMMENT '是否整车包场',
    create_time     DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time     DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted         TINYINT      NOT NULL DEFAULT 0 COMMENT '逻辑删除',
    KEY idx_script_date (script_id, schedule_date),
    KEY idx_dm_date (dm_user_id, schedule_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='剧本场次排期表';

-- ============== 订单表 ==============
DROP TABLE IF EXISTS t_order;
CREATE TABLE t_order (
    id                BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
    order_no          VARCHAR(32)   NOT NULL COMMENT '订单编号',
    script_session_id BIGINT        NOT NULL COMMENT '剧本场次ID',
    user_id           BIGINT        NOT NULL COMMENT '下单玩家ID',
    script_id         BIGINT        NOT NULL COMMENT '剧本ID',
    script_name       VARCHAR(100)  DEFAULT NULL COMMENT '剧本名称(冗余)',
    room_name         VARCHAR(50)   DEFAULT NULL COMMENT '房间名称(冗余)',
    session_time      DATETIME      DEFAULT NULL COMMENT '场次时间(冗余)',
    dm_name           VARCHAR(50)   DEFAULT NULL COMMENT 'DM姓名(冗余)',
    total_amount      DECIMAL(10,2) DEFAULT 0.00 COMMENT '订单总金额',
    paid_amount       DECIMAL(10,2) DEFAULT 0.00 COMMENT '实付金额',
    deposit_amount    DECIMAL(10,2) DEFAULT 0.00 COMMENT '定金金额',
    pay_method        VARCHAR(20)   DEFAULT NULL COMMENT '支付方式: WECHAT/ALIPAY/OFFLINE',
    order_type        VARCHAR(20)   NOT NULL COMMENT '订单类型: CARPOOL/FULL_BOOKING',
    status            VARCHAR(30)   NOT NULL DEFAULT 'WAITING_CARPOOL' COMMENT '状态',
    pay_time          DATETIME      DEFAULT NULL COMMENT '支付时间',
    refund_time       DATETIME      DEFAULT NULL COMMENT '退款时间',
    refund_amount     DECIMAL(10,2) DEFAULT 0.00 COMMENT '退款金额',
    remark            VARCHAR(500)  DEFAULT NULL COMMENT '玩家备注',
    create_time       DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time       DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted           TINYINT       NOT NULL DEFAULT 0 COMMENT '逻辑删除',
    UNIQUE KEY uk_order_no (order_no),
    KEY idx_user_id (user_id),
    KEY idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';

-- ============== 订单参与者表 ==============
DROP TABLE IF EXISTS t_order_participant;
CREATE TABLE t_order_participant (
    id            BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
    order_id      BIGINT      NOT NULL COMMENT '订单ID',
    user_id       BIGINT      DEFAULT NULL COMMENT '玩家用户ID',
    player_name   VARCHAR(50)  DEFAULT NULL COMMENT '玩家姓名',
    player_phone  VARCHAR(20)  DEFAULT NULL COMMENT '玩家手机号',
    checked_in    TINYINT      DEFAULT 0 COMMENT '是否已签到',
    check_in_time DATETIME     DEFAULT NULL COMMENT '签到时间',
    create_time   DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time   DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted       TINYINT      NOT NULL DEFAULT 0 COMMENT '逻辑删除',
    KEY idx_order_id (order_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单参与者表';

-- ============== 会员表 ==============
DROP TABLE IF EXISTS t_member;
CREATE TABLE t_member (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
    user_id     BIGINT        NOT NULL COMMENT '关联用户ID',
    level       VARCHAR(20)   DEFAULT 'NORMAL' COMMENT '会员等级: NORMAL/SILVER/GOLD/DIAMOND',
    points      INT           DEFAULT 0 COMMENT '账户积分',
    total_spent DECIMAL(12,2) DEFAULT 0.00 COMMENT '累计消费金额',
    total_plays INT           DEFAULT 0 COMMENT '累计开本次数',
    create_time DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted     TINYINT       NOT NULL DEFAULT 0 COMMENT '逻辑删除',
    UNIQUE KEY uk_user_id (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会员表';

-- ============== 优惠券表 ==============
DROP TABLE IF EXISTS t_coupon;
CREATE TABLE t_coupon (
    id             BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
    name           VARCHAR(100)  NOT NULL COMMENT '优惠券名称',
    type           VARCHAR(20)   DEFAULT 'CASH' COMMENT '类型: DISCOUNT/CASH',
    coupon_value   DECIMAL(10,2) DEFAULT 0.00 COMMENT '面额/折扣值',
    min_amount     DECIMAL(10,2) DEFAULT 0.00 COMMENT '最低消费门槛',
    total_count    INT           DEFAULT 0 COMMENT '发放总量',
    claimed_count  INT           DEFAULT 0 COMMENT '已领取数量',
    limit_per_user INT           DEFAULT 1 COMMENT '每人限领',
    valid_from     DATETIME      DEFAULT NULL COMMENT '有效期开始',
    valid_to       DATETIME      DEFAULT NULL COMMENT '有效期结束',
    enabled        TINYINT       DEFAULT 1 COMMENT '是否启用',
    create_time    DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time    DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted        TINYINT       NOT NULL DEFAULT 0 COMMENT '逻辑删除'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='优惠券表';

-- ============== DM绩效表 ==============
DROP TABLE IF EXISTS t_dm_performance;
CREATE TABLE t_dm_performance (
    id                   BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
    dm_user_id           BIGINT        NOT NULL COMMENT 'DM用户ID',
    stat_date            DATE          NOT NULL COMMENT '统计日期',
    daily_session_count  INT           DEFAULT 0 COMMENT '当天开本数量',
    monthly_session_count INT          DEFAULT 0 COMMENT '当月开本数量',
    daily_commission     DECIMAL(10,2) DEFAULT 0.00 COMMENT '当天提成金额',
    monthly_commission   DECIMAL(10,2) DEFAULT 0.00 COMMENT '当月累计提成',
    avg_rating           DECIMAL(3,2)  DEFAULT 5.00 COMMENT '玩家评分均分',
    create_time          DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time          DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted              TINYINT       NOT NULL DEFAULT 0 COMMENT '逻辑删除',
    KEY idx_dm_date (dm_user_id, stat_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='DM绩效表';

-- ============== 系统配置表 ==============
DROP TABLE IF EXISTS t_system_config;
CREATE TABLE t_system_config (
    id           BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
    config_key   VARCHAR(100)  NOT NULL COMMENT '配置键',
    config_value VARCHAR(500)  DEFAULT NULL COMMENT '配置值',
    description  VARCHAR(255)  DEFAULT NULL COMMENT '配置说明',
    config_group VARCHAR(30)   DEFAULT 'BUSINESS' COMMENT '分组: BUSINESS/REFUND/NOTICE',
    create_time  DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time  DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted      TINYINT       NOT NULL DEFAULT 0 COMMENT '逻辑删除',
    UNIQUE KEY uk_config_key (config_key)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统配置表';

-- 默认配置
INSERT INTO t_system_config (config_key, config_value, description, config_group) VALUES
('business_hours', '10:00-02:00', '营业时间', 'BUSINESS'),
('refund_hours_before', '2', '开场前N小时可免费取消', 'REFUND'),
('deposit_rate', '0.3', '定金比例', 'BUSINESS'),
('home_notice', '欢迎光临XX剧本杀门店！', '首页公告', 'NOTICE');

-- ============== 支付记录表 ==============
DROP TABLE IF EXISTS t_payment_record;
CREATE TABLE t_payment_record (
    id             BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
    order_id       BIGINT        NOT NULL COMMENT '订单ID',
    transaction_no VARCHAR(64)   DEFAULT NULL COMMENT '交易流水号',
    amount         DECIMAL(10,2) DEFAULT 0.00 COMMENT '支付金额',
    type           VARCHAR(10)   DEFAULT 'PAY' COMMENT '类型: PAY/REFUND',
    pay_method     VARCHAR(20)   DEFAULT NULL COMMENT '支付方式: WECHAT/ALIPAY/OFFLINE',
    status         VARCHAR(20)   DEFAULT 'PENDING' COMMENT '状态: PENDING/SUCCESS/FAILED',
    pay_time       DATETIME      DEFAULT NULL COMMENT '支付完成时间',
    create_time    DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time    DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted        TINYINT       NOT NULL DEFAULT 0 COMMENT '逻辑删除',
    KEY idx_order_id (order_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='支付记录表';
