CREATE DATABASE seckill;
use seckill;
CREATE TABLE seckill(
 'seckill_id'  BIGINT NOT NULL AUTO_INCREMENT COMMENT '商品库存id',
 'name' VARCHAR (120) NOT NULL COMMENT '商品名称',
 'number' INT NOT NULL COMMENT '库存数量',
 'start_time' TIMESTAMP NOT NULL COMMENT '开始时间',
'end_time' TIMESTAMP NOT NULL COMMENT '结束时间',
'create_time' TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',

PRIMARY KEY (seckill_id),
KEY  idx_start_time(start_time),
KEY  idx_end_time(end_time),
KEY  idx_create_time(create_time)

)ENGINE= InnoDB AUTO_INCREMENT = 1000 DEFAULT CHARSET = UTF8 COMMENT='秒杀库存表';

CREATE TABLE success_killed(
 'seckill_id'  BIGINT NOT NULL  COMMENT '秒杀商品的id',
 'user_phone' BIGINT NOT NULL COMMENT '用户电话',
 'state' tinyint NOT NULL DEFAULT -1 COMMENT '状态标识：-1无效 0成功 1付款',
'create_time' TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',

PRIMARY KEY (seckill_id,user_phone ),
KEY  idx_create_time(create_time)

)ENGINE= InnoDB  DEFAULT CHARSET = UTF8 COMMENT='秒杀明细表';



