DROP TABLE Node;

CREATE TABLE Node(
node_id int,
node_name varchar(100),
parent_id int,
num_of_children int,
node_value numeric(18,2)
);

INSERT INTO node(node_id, node_name, parent_id, num_of_children, node_value)
values(1,'TreatDegree',0,3,0);

INSERT INTO node(node_id, node_name, parent_id, num_of_children, node_value)
values(2,'Target',1,3,0);

INSERT INTO node(node_id, node_name, parent_id, num_of_children, node_value)
values(3,'Space',1,3,0);

INSERT INTO node(node_id, node_name, parent_id, num_of_children, node_value)
values(4,'Electric',1,3,0);

INSERT INTO node(node_id, node_name, parent_id, num_of_children, node_value)
values(5,'YT',2,3,0);

INSERT INTO node(node_id, node_name, parent_id, num_of_children, node_value)
values(6,'NL',2,2,0);

INSERT INTO node(node_id, node_name, parent_id, num_of_children, node_value)
values(7,'JPX',2,2,0);

INSERT INTO node(node_id, node_name, parent_id, num_of_children, node_value)
values(8,'MBSSNL',3,3,0);

INSERT INTO node(node_id, node_name, parent_id, num_of_children, node_value)
values(9,'XXCXNL',3,2,0);

INSERT INTO node(node_id, node_name, parent_id, num_of_children, node_value)
values(10,'SSGRNL',4,2,0);

INSERT INTO node(node_id, node_name, parent_id, num_of_children, node_value)
values(11,'TXGRNL',4,2,0);

SELECT node_id,node_name,parent_id,num_of_children,node_value
FROM Node;