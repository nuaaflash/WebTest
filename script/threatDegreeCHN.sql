CREATE TABLE Node(
node_id int,
node_name varchar(100),
parent_id int,
num_of_children int,
node_value numeric(18,2),
node_level int
);

INSERT INTO node(node_id, node_name, parent_id, num_of_children, node_value, node_level)
values(1,'威胁度',0,3,0,0);

INSERT INTO node(node_id, node_name, parent_id, num_of_children, node_value, node_level)
values(2,'海面、水下、天空目标威胁因素',1,3,0,1);

INSERT INTO node(node_id, node_name, parent_id, num_of_children, node_value, node_level)
values(3,'太空威胁因素',1,3,0,1);

INSERT INTO node(node_id, node_name, parent_id, num_of_children, node_value, node_level)
values(4,'电磁威胁因素',1,3,0,1);

INSERT INTO node(node_id, node_name, parent_id, num_of_children, node_value, node_level)
values(5,'攻击意图',2,3,0,2);

INSERT INTO node(node_id, node_name, parent_id, num_of_children, node_value, node_level)
values(6,'攻击能力',2,2,0,2);

INSERT INTO node(node_id, node_name, parent_id, num_of_children, node_value, node_level)
values(7,'攻击紧迫性',2,2,0,2);

INSERT INTO node(node_id, node_name, parent_id, num_of_children, node_value, node_level)
values(8,'目标搜索能力',3,3,0,2);

INSERT INTO node(node_id, node_name, parent_id, num_of_children, node_value, node_level)
values(9,'信息传输能力',3,2,0,2);

INSERT INTO node(node_id, node_name, parent_id, num_of_children, node_value, node_level)
values(10,'搜索干扰能力',4,2,0,2);

INSERT INTO node(node_id, node_name, parent_id, num_of_children, node_value, node_level)
values(11,'通信干扰能力',4,2,0,2);

INSERT INTO node(node_id, node_name, parent_id, num_of_children, node_value, node_level)
values(12,'航路捷径',5,0,0,3);

INSERT INTO node(node_id, node_name, parent_id, num_of_children, node_value, node_level)
values(13,'攻击航向角',5,0,0,3);

INSERT INTO node(node_id, node_name, parent_id, num_of_children, node_value, node_level)
values(14,'目标类型',5,0,0,3);

INSERT INTO node(node_id, node_name, parent_id, num_of_children, node_value, node_level)
values(15,'武器类型',6,0,0,3);

INSERT INTO node(node_id, node_name, parent_id, num_of_children, node_value, node_level)
values(16,'武器数量',6,0,0,3);

INSERT INTO node(node_id, node_name, parent_id, num_of_children, node_value, node_level)
values(17,'速度',7,0,0,3);

INSERT INTO node(node_id, node_name, parent_id, num_of_children, node_value, node_level)
values(18,'阵位距离',7,0,0,3);

INSERT INTO node(node_id, node_name, parent_id, num_of_children, node_value, node_level)
values(19,'地面覆盖面积',8,0,0,3);

INSERT INTO node(node_id, node_name, parent_id, num_of_children, node_value, node_level)
values(20,'重访时间',8,0,0,3);

INSERT INTO node(node_id, node_name, parent_id, num_of_children, node_value, node_level)
values(21,'分辨率',8,0,0,3);

INSERT INTO node(node_id, node_name, parent_id, num_of_children, node_value, node_level)
values(22,'数据传输速率',9,0,0,3);

INSERT INTO node(node_id, node_name, parent_id, num_of_children, node_value, node_level)
values(23,'通信容量',9,0,0,3);

INSERT INTO node(node_id, node_name, parent_id, num_of_children, node_value, node_level)
values(24,'噪声大小',10,0,0,3);

INSERT INTO node(node_id, node_name, parent_id, num_of_children, node_value, node_level)
values(25,'探测距离',10,0,0,3);

INSERT INTO node(node_id, node_name, parent_id, num_of_children, node_value, node_level)
values(26,'信噪比',11,0,0,3);

INSERT INTO node(node_id, node_name, parent_id, num_of_children, node_value, node_level)
values(27,'误码比率',11,0,0,3);

SELECT node_id,node_name,parent_id,num_of_children,node_value, node_level
FROM Node;