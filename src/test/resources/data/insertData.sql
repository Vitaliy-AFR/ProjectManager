INSERT INTO users (name, password, roles)
VALUES
('admin', '$2a$10$XxhwlaHQEhI/8OdPCG78v.JMyhFPcmcN4.qfV.l1..tJFvjbvEwYu', 'ROLE_ADMIN'),
('user', '$2a$10$YCYLbJm2LWnBqLTqvaPhM.TSRBoMw0rCmn5rh4bnL1zm3hrAwaCPm', 'ROLE_USER');
INSERT INTO projects (id, name, description, start_date, end_date, user_name)
VALUES
('967ac76b-76c4-4aaa-be5d-6a5a952c1335', 'Project 1', null, '2024-10-06T23:49:29.838616', null, null),
('b8bc0819-a259-4f85-8535-3846aa222684', 'Admin project 1', null, '2024-10-26T23:29:06.78769', null, 'admin'),
('c70a5c08-5548-4c33-a875-0fc6025139e4', 'User project 1', 'update 1', '2024-11-08T22:51:56.450606', null, 'user');
INSERT INTO tasks (id, name, description, start_date, end_date, project_id)
VALUES
('fb9e3d43-5fc5-4eff-803c-e2272d170963', 'Task 1', null, '2024-10-10T23:22:22.445246', null, '967ac76b-76c4-4aaa-be5d-6a5a952c1335'),
('9d4983f8-edbf-4337-b937-c94036d15448', 'Task 22', 'update 27.10.2024', '2024-10-10T23:28:51.276032', null, '967ac76b-76c4-4aaa-be5d-6a5a952c1335'),
('c2182559-45df-4b1c-bf05-4b01956de826', 'Task 1', null, '2024-10-27T22:00:20.88718', null, 'b8bc0819-a259-4f85-8535-3846aa222684');
