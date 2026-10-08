-- ============================================================
-- 表：t_product  （对应实体 qu.nothingless.entity.Product）
-- 数据库：PostgreSQL 12+
-- ============================================================

CREATE TABLE t_product (
    product_id          BIGINT          NOT NULL,

    product_name        VARCHAR(128)    NOT NULL,
    product_description TEXT,
    product_price       NUMERIC(10, 2)  NOT NULL DEFAULT 0.00,
    product_image       VARCHAR(512),
    product_category    VARCHAR(64),
    product_brand       VARCHAR(64),
    product_stock       INTEGER         NOT NULL DEFAULT 0,

    product_status      INTEGER        NOT NULL DEFAULT 65280,
    -- LocalDateTime
    product_created_at  TIMESTAMP(6)    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    product_updated_at  TIMESTAMP(6)    NOT NULL DEFAULT CURRENT_TIMESTAMP,

    product_sku         VARCHAR(64)     NOT NULL,
    -- @TableLogic(value = "0", delval = "1")
    is_deleted          SMALLINT        NOT NULL DEFAULT 0,
    -- @Version 乐观锁
    version             INTEGER         NOT NULL DEFAULT 0,

    CONSTRAINT pk_t_product PRIMARY KEY (product_id),
    --CONSTRAINT uk_t_product_sku UNIQUE (product_sku),
    CONSTRAINT chk_t_product_price CHECK (product_price >= 0),
    CONSTRAINT chk_t_product_stock CHECK (product_stock >= 0)
);

COMMENT ON TABLE  t_product IS '商品表';
COMMENT ON COLUMN t_product.product_id         IS '主键，雪花ID';
COMMENT ON COLUMN t_product.product_name       IS '商品名称';
COMMENT ON COLUMN t_product.product_description IS '商品描述';
COMMENT ON COLUMN t_product.product_price      IS '商品价格，精度10位小数2位';
COMMENT ON COLUMN t_product.product_image      IS '商品图片URL';
COMMENT ON COLUMN t_product.product_category   IS '商品分类';
COMMENT ON COLUMN t_product.product_brand      IS '商品品牌';
COMMENT ON COLUMN t_product.product_stock      IS '库存数量';
COMMENT ON COLUMN t_product.product_status     IS '状态：在售 下架 锁定 过期';
COMMENT ON COLUMN t_product.product_created_at IS '创建时间';
COMMENT ON COLUMN t_product.product_updated_at IS '更新时间';
COMMENT ON COLUMN t_product.product_sku        IS '库存单位唯一编码';
COMMENT ON COLUMN t_product.is_deleted         IS '逻辑删除：0未删除 1已删除';
COMMENT ON COLUMN t_product.version            IS '乐观锁版本号';

-- 常用查询索引
CREATE INDEX idx_t_product_status   ON t_product (product_status) WHERE is_deleted = 0;
CREATE INDEX idx_t_product_category ON t_product (product_category) WHERE is_deleted = 0;
CREATE INDEX idx_t_product_brand    ON t_product (product_brand) WHERE is_deleted = 0;
CREATE INDEX idx_t_product_ctime    ON t_product (product_created_at DESC);
