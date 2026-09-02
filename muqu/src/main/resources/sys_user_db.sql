-- Active: 1775095120677@@127.0.0.1@5432@boot_db
-- ============================================
-- 系统用户表
-- ============================================
DROP TABLE IF EXISTS sys_user CASCADE;

CREATE TABLE sys_user (
    -- 主键（雪花 ID）
    tab_id                  BIGINT          NOT NULL,
    -- 核心字段
    my_user_account            VARCHAR(255)    NOT NULL,
    my_user_id              VARCHAR(64)     NOT NULL,
    my_email                VARCHAR(255),
    my_phone                TEXT,                           -- AES-GCM 加密存储，等值查询需配合 phoneIndex
    my_phone_index          VARCHAR(64),                    -- 手机号盲索引（HMAC-SHA256），用于唯一校验与等值查询
    my_employee_id          VARCHAR(64),
    -- 姓名信息
    my_first_name           VARCHAR(255),
    my_last_name            VARCHAR(255),
    my_full_name            VARCHAR(255),
    -- 安全字段
    password_hash           VARCHAR(255),                   -- 仅插入，不更新，查询时排除
    my_account_status       VARCHAR(50)     DEFAULT 'ACTIVE',
    time_locked_until       TIMESTAMP,
    time_password_expires_at TIMESTAMP,
    failed_attempts         INT             DEFAULT 0,
    -- 角色与配置（JSON 字段）
    my_roles                JSONB           DEFAULT '[]',
    my_profiles             JSONB           DEFAULT '[]',
    -- 乐观锁
    my_version              INT             DEFAULT 0,
    -- 逻辑删除
    bool_deleted            INT             DEFAULT 0,      -- 0=未删除, 1=已删除
    -- 审计字段
    time_created_at         TIMESTAMP       DEFAULT CURRENT_TIMESTAMP,
    time_updated_at         TIMESTAMP       DEFAULT CURRENT_TIMESTAMP,
    who_created_by          VARCHAR(255),
    who_updated_by          VARCHAR(255),
    -- 租户字段
    my_tenant_id            BIGINT,
    -- 外键与备用键
    my_key                  VARCHAR(255),
    key_01                  VARCHAR(255),
    key_02                  VARCHAR(255),
    key_03                  VARCHAR(255),
    key_04                  VARCHAR(255),
    key_05                  VARCHAR(255),
    key_06                  VARCHAR(255),
    -- 约束
    CONSTRAINT pk_sys_user PRIMARY KEY (tab_id)
);

-- ============================================
-- 索引
-- ============================================

-- 业务 userId 唯一索引（登录查询用）
CREATE UNIQUE INDEX idx_sys_user_user_id 
    ON sys_user(my_user_id) 
    WHERE bool_deleted = 0;

-- 手机号盲索引（等值查询用）
CREATE UNIQUE INDEX idx_sys_user_phone_index 
    ON sys_user(my_phone_index) 
    WHERE bool_deleted = 0;

-- 邮箱索引
CREATE INDEX idx_sys_user_email 
    ON sys_user(my_email);

-- 员工号索引
CREATE INDEX idx_sys_user_employee_id 
    ON sys_user(my_employee_id);

-- 租户隔离索引（多租户场景高频查询）
CREATE INDEX idx_sys_user_tenant_id 
    ON sys_user(my_tenant_id);

-- 账号状态 + 删除标记 复合索引（列表查询用）
CREATE INDEX idx_sys_user_status_deleted 
    ON sys_user(my_account_status, bool_deleted);

-- 创建时间索引（排序/分页用）
CREATE INDEX idx_sys_user_created_at 
    ON sys_user(time_created_at DESC);

-- 外键索引
CREATE INDEX idx_sys_user_my_key 
    ON sys_user(my_key);

-- ============================================
-- 字段注释
-- ============================================

COMMENT ON TABLE sys_user IS '系统用户表';

COMMENT ON COLUMN sys_user.tab_id IS '主键（雪花 ID）';
COMMENT ON COLUMN sys_user.my_user_account IS '用户名';
COMMENT ON COLUMN sys_user.my_user_id IS '业务用户唯一标识';
COMMENT ON COLUMN sys_user.my_email IS '邮箱';
COMMENT ON COLUMN sys_user.my_phone IS '手机号：AES-GCM 加密存储';
COMMENT ON COLUMN sys_user.my_phone_index IS '手机号盲索引（HMAC-SHA256），用于唯一校验与等值查询';
COMMENT ON COLUMN sys_user.my_employee_id IS '员工编号';
COMMENT ON COLUMN sys_user.my_first_name IS '名';
COMMENT ON COLUMN sys_user.my_last_name IS '姓';
COMMENT ON COLUMN sys_user.my_full_name IS '全名';
COMMENT ON COLUMN sys_user.password_hash IS '密码哈希（BCrypt/Argon2）';
COMMENT ON COLUMN sys_user.my_account_status IS '账号状态：ACTIVE, LOCKED, EXPIRED 等';
COMMENT ON COLUMN sys_user.time_locked_until IS '锁定截止时间';
COMMENT ON COLUMN sys_user.time_password_expires_at IS '密码过期时间';
COMMENT ON COLUMN sys_user.failed_attempts IS '连续登录失败次数';
COMMENT ON COLUMN sys_user.my_roles IS '角色集合（JSON）';
COMMENT ON COLUMN sys_user.my_profiles IS '配置集合（JSON）';
COMMENT ON COLUMN sys_user.my_version IS '乐观锁版本号';
COMMENT ON COLUMN sys_user.bool_deleted IS '逻辑删除标记：0=正常, 1=已删除';
COMMENT ON COLUMN sys_user.time_created_at IS '创建时间';
COMMENT ON COLUMN sys_user.time_updated_at IS '更新时间';
COMMENT ON COLUMN sys_user.who_created_by IS '创建人';
COMMENT ON COLUMN sys_user.who_updated_by IS '更新人';
COMMENT ON COLUMN sys_user.my_tenant_id IS '租户 ID';
COMMENT ON COLUMN sys_user.my_key IS '外键（自关联）';
COMMENT ON COLUMN sys_user.key_01 IS '备用键 01';
COMMENT ON COLUMN sys_user.key_02 IS '备用键 02';
COMMENT ON COLUMN sys_user.key_03 IS '备用键 03';
COMMENT ON COLUMN sys_user.key_04 IS '备用键 04';
COMMENT ON COLUMN sys_user.key_05 IS '备用键 05';
COMMENT ON COLUMN sys_user.key_06 IS '备用键 06';

DROP INDEX public.idx_sys_user_phone_index;

CREATE INDEX idx_sys_user_phone_index 
ON public.sys_user USING btree (my_phone_index) 
WHERE (bool_deleted = 0);