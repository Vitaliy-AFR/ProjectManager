INSERT INTO projects (project_id, project_name, description, start_date, end_date, user_name)
VALUES
('967ac76b-76c4-4aaa-be5d-6a5a952c1335', 'Project 1', null, 'Oct 6, 2024, 11:49:29 PM', null, null),
('b8bc0819-a259-4f85-8535-3846aa222684', 'Admin project 1', null, 'Oct 26, 2024, 11:29:06 PM', null, 'admin'),
('c70a5c08-5548-4c33-a875-0fc6025139e4', 'User project 1', 'update 1', 'Nov 8, 2024, 10:51:56 PM', null, 'user');
INSERT INTO tasks (task_id, task_name, description, start_date, end_date, project_id)
VALUES
('fb9e3d43-5fc5-4eff-803c-e2272d170963', 'Task 1', null, 'Oct 10, 2024, 11:22:22 PM', null, '967ac76b-76c4-4aaa-be5d-6a5a952c1335'),
('9d4983f8-edbf-4337-b937-c94036d15448', 'Task 22', 'update 27.10.2024', 'Oct 10, 2024, 11:28:51 PM', null, '967ac76b-76c4-4aaa-be5d-6a5a952c1335'),
('c2182559-45df-4b1c-bf05-4b01956de826', 'Task 1', null, 'Oct 27, 2024, 10:00:20 PM', null, 'b8bc0819-a259-4f85-8535-3846aa222684');
INSERT INTO users (user_name, user_password, user_role)
VALUES
('admin', '$2a$10$XxhwlaHQEhI/8OdPCG78v.JMyhFPcmcN4.qfV.l1..tJFvjbvEwYu', 'ROLE_ADMIN'),
('user', '$2a$10$YCYLbJm2LWnBqLTqvaPhM.TSRBoMw0rCmn5rh4bnL1zm3hrAwaCPm', 'ROLE_USER');