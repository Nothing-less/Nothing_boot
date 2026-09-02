-- Active: 1775095120677@@127.0.0.1@5432@boot_db@public
-- ============================================
-- 系统用户表测试数据（50条）
-- 生成时间: 2026-08-31
-- ============================================

-- TRUNCATE TABLE sys_user RESTART IDENTITY;

INSERT INTO sys_user (
    tab_id, my_user_account, my_user_id, my_email, my_phone, my_phone_index,
    my_employee_id, my_first_name, my_last_name, my_full_name, password_hash,
    my_account_status, time_locked_until, time_password_expires_at, failed_attempts,
    my_roles, my_profiles, my_version, bool_deleted,
    time_created_at, time_updated_at, who_created_by, who_updated_by,
    my_tenant_id, my_key,
    key_01, key_02, key_03, key_04, key_05, key_06
) VALUES (
    7130316806937407692, 'user_cfbcc1e9', 'USR202608310001', 'user_cfbcc1e9@163.com', 'MTM4MjE2Njg3MzLYEA8vb3cNZdZw5Y4DUdiujk9urDQvwjG3sIcW', '1a920b04b055e6b187852b31acd5fcab',
    'EMP662275', '霞', '宋', '宋霞', '$2a$10$0b6c2c0c31184f0e86c9628086f08b83',
    'ACTIVE', NULL, NULL, 1,
    '["USER","DEVELOPER"]'::jsonb, '{"theme":"light","lang":"zh-CN","notify":true,"privacy":"high"}'::jsonb, 6, 0,
    '2025-07-01 06:10:15', '2025-07-05 06:10:15', 'system', 'admin',
    2, NULL,
    'EXT897865', NULL, NULL, NULL, NULL, NULL
);
INSERT INTO sys_user (
    tab_id, my_user_account, my_user_id, my_email, my_phone, my_phone_index,
    my_employee_id, my_first_name, my_last_name, my_full_name, password_hash,
    my_account_status, time_locked_until, time_password_expires_at, failed_attempts,
    my_roles, my_profiles, my_version, bool_deleted,
    time_created_at, time_updated_at, who_created_by, who_updated_by,
    my_tenant_id, my_key,
    key_01, key_02, key_03, key_04, key_05, key_06
) VALUES (
    7130316809978341044, '梁霞97', 'USR202608310002', '梁霞97@qq.com', 'MTM4NDI4NTc5NjZT7MKKcKYcdRChzYkhbKFs/8rqSYdHfobbzLlw', 'f0ca2cf6ebe7edf4d3176a4bcfe3ae66',
    'EMP245051', '霞', '梁', '梁霞', '$2a$10$da64f0e98bc7418c8904b7cd6262813c',
    'LOCKED', '2027-01-19 07:48:07', NULL, 4,
    '["USER"]'::jsonb, '{"theme":"light","lang":"en-US","notify":false}'::jsonb, 4, 0,
    '2025-05-02 08:26:31', '2025-05-12 08:26:31', 'hr_system', 'admin',
    1, NULL,
    NULL, NULL, NULL, NULL, NULL, 'EXT714328'
);
INSERT INTO sys_user (
    tab_id, my_user_account, my_user_id, my_email, my_phone, my_phone_index,
    my_employee_id, my_first_name, my_last_name, my_full_name, password_hash,
    my_account_status, time_locked_until, time_password_expires_at, failed_attempts,
    my_roles, my_profiles, my_version, bool_deleted,
    time_created_at, time_updated_at, who_created_by, who_updated_by,
    my_tenant_id, my_key,
    key_01, key_02, key_03, key_04, key_05, key_06
) VALUES (
    7130316815678339209, 'user_a80e1f38', 'USR202608310003', 'user_a80e1f38@corp.nothingless.com', 'MTM4NDUzNTE0NzlbNphlTr9SAKX6CTm5nXodeygr+CNAQfNUh9hs', '44860da34447c1c72efb5dbcef4fddc8',
    'EMP891952', '华', '唐', '唐华', '$2a$10$690dacfba0f44cae8025475b443b293d',
    'ACTIVE', NULL, NULL, 2,
    '["ADMIN","AUDITOR"]'::jsonb, '{"beta":true,"theme":"dark","lang":"zh-CN"}'::jsonb, 14, 0,
    '2024-04-03 23:40:54', '2024-04-18 23:40:54', 'admin', 'system',
    3, NULL,
    NULL, NULL, NULL, NULL, 'EXT62733', NULL
);
INSERT INTO sys_user (
    tab_id, my_user_account, my_user_id, my_email, my_phone, my_phone_index,
    my_employee_id, my_first_name, my_last_name, my_full_name, password_hash,
    my_account_status, time_locked_until, time_password_expires_at, failed_attempts,
    my_roles, my_profiles, my_version, bool_deleted,
    time_created_at, time_updated_at, who_created_by, who_updated_by,
    my_tenant_id, my_key,
    key_01, key_02, key_03, key_04, key_05, key_06
) VALUES (
    7130316820665346706, 'user_3edb3370', 'USR202608310004', 'user_3edb3370@foxmail.com', 'MTM4ODY2NDQxMDbyfPLQYTAx3LXY0u8bMh/OrTd/YmHlR9hdjux/', '6197438ead25dc23de76891d55d5a431',
    'EMP179046', '晨曦', '张', '张晨曦', '$2a$10$247fe1c300f8442fa86e04246037d5cb',
    'ACTIVE', NULL, NULL, 2,
    '["USER"]'::jsonb, '{"theme":"light","lang":"en-US","notify":false}'::jsonb, 7, 0,
    '2024-05-09 04:15:49', '2024-06-04 04:15:49', 'hr_system', 'hr_system',
    2, NULL,
    'EXT947279', NULL, NULL, 'EXT279085', 'EXT824267', NULL
);
INSERT INTO sys_user (
    tab_id, my_user_account, my_user_id, my_email, my_phone, my_phone_index,
    my_employee_id, my_first_name, my_last_name, my_full_name, password_hash,
    my_account_status, time_locked_until, time_password_expires_at, failed_attempts,
    my_roles, my_profiles, my_version, bool_deleted,
    time_created_at, time_updated_at, who_created_by, who_updated_by,
    my_tenant_id, my_key,
    key_01, key_02, key_03, key_04, key_05, key_06
) VALUES (
    7130316822787781876, 'user_9b9d3e39', 'USR202608310005', 'user_9b9d3e39@nothingless.tech', 'MTM4NTIwOTEzMjUdGfRQHSlfIyJ4zj1+FCnWoYVooHqHykOZ6qEl', '199e66a2c8b967099e3f8e9c5eaefee1',
    NULL, '平', '黄', '黄平', '$2a$10$93aaff26c94941889ad38e3c76798a0a',
    'EXPIRED', NULL, '2025-03-13 06:57:43', 0,
    '["ADMIN"]'::jsonb, '{"theme":"dark","lang":"ja-JP","notify":true}'::jsonb, 8, 0,
    '2024-04-12 21:10:09', '2024-06-10 21:10:09', 'batch_import', 'system',
    2, NULL,
    NULL, NULL, 'EXT738715', NULL, 'EXT847305', NULL
);
INSERT INTO sys_user (
    tab_id, my_user_account, my_user_id, my_email, my_phone, my_phone_index,
    my_employee_id, my_first_name, my_last_name, my_full_name, password_hash,
    my_account_status, time_locked_until, time_password_expires_at, failed_attempts,
    my_roles, my_profiles, my_version, bool_deleted,
    time_created_at, time_updated_at, who_created_by, who_updated_by,
    my_tenant_id, my_key,
    key_01, key_02, key_03, key_04, key_05, key_06
) VALUES (
    7130316825199457104, 'user_50529cc4', 'USR202608310006', 'user_50529cc4@qq.com', 'MTM4NDY1NTM5NTiQa69oh/qAGi/YjRYBqkKGUuLaBDkmTBK9S9xB', 'cd00200cd4568a69ca2cbf3b4be0180a',
    NULL, '军', '邓', '邓军', '$2a$10$65b7b02f2ea949ba8c8e281df8cc54c6',
    'ACTIVE', NULL, '2026-06-13 04:06:37', 0,
    '["USER","MANAGER"]'::jsonb, '{"theme":"light","lang":"en-US","notify":false}'::jsonb, 11, 0,
    '2025-08-28 22:25:14', '2025-10-02 22:25:14', 'hr_system', 'admin',
    1, '7130316809978341044',
    'EXT839742', 'EXT925231', NULL, NULL, 'EXT821391', 'EXT842204'
);
INSERT INTO sys_user (
    tab_id, my_user_account, my_user_id, my_email, my_phone, my_phone_index,
    my_employee_id, my_first_name, my_last_name, my_full_name, password_hash,
    my_account_status, time_locked_until, time_password_expires_at, failed_attempts,
    my_roles, my_profiles, my_version, bool_deleted,
    time_created_at, time_updated_at, who_created_by, who_updated_by,
    my_tenant_id, my_key,
    key_01, key_02, key_03, key_04, key_05, key_06
) VALUES (
    7130316832233289865, 'user_3724c78d', 'USR202608310007', 'user_3724c78d@nothingless.tech', 'MTM4NzE3ODA4NTSznHRyDGLMqI4jjrPMqQ47hVuHEzfesKDfO8Vh', 'dec9a5c537cbea6b616b9a696bf43038',
    'EMP843215', '强', '杨', '杨强', '$2a$10$2f5a47f3c5b04239bf1682528c90a8bd',
    'ACTIVE', NULL, NULL, 0,
    '["USER","MANAGER"]'::jsonb, '{"theme":"light","lang":"zh-CN","notify":true,"privacy":"high"}'::jsonb, 13, 0,
    '2024-02-24 08:24:22', '2024-04-24 08:24:22', 'batch_import', 'admin',
    3, '7130316806937407692',
    'EXT315910', NULL, NULL, NULL, NULL, NULL
);
INSERT INTO sys_user (
    tab_id, my_user_account, my_user_id, my_email, my_phone, my_phone_index,
    my_employee_id, my_first_name, my_last_name, my_full_name, password_hash,
    my_account_status, time_locked_until, time_password_expires_at, failed_attempts,
    my_roles, my_profiles, my_version, bool_deleted,
    time_created_at, time_updated_at, who_created_by, who_updated_by,
    my_tenant_id, my_key,
    key_01, key_02, key_03, key_04, key_05, key_06
) VALUES (
    7130316834376625185, '梁艳48', 'USR202608310008', '梁艳48@corp.nothingless.com', 'MTM4NDg1MDg5MDdr3KTu4uJt8lYrkasveJ5zZUsMF33zJenUY8T9', '01fc75c043a0433f8979dcec3a213f92',
    'EMP254739', '艳', '梁', '梁艳', '$2a$10$9b80aae3342f46acaacfb36686831404',
    'ACTIVE', NULL, NULL, 0,
    '["USER","MANAGER"]'::jsonb, '{"theme":"auto","lang":"zh-CN","notify":true,"2fa":true}'::jsonb, 14, 0,
    '2024-02-09 00:08:18', '2024-03-15 00:08:18', 'admin', 'system',
    5, NULL,
    NULL, NULL, NULL, NULL, 'EXT465071', NULL
);
INSERT INTO sys_user (
    tab_id, my_user_account, my_user_id, my_email, my_phone, my_phone_index,
    my_employee_id, my_first_name, my_last_name, my_full_name, password_hash,
    my_account_status, time_locked_until, time_password_expires_at, failed_attempts,
    my_roles, my_profiles, my_version, bool_deleted,
    time_created_at, time_updated_at, who_created_by, who_updated_by,
    my_tenant_id, my_key,
    key_01, key_02, key_03, key_04, key_05, key_06
) VALUES (
    7130316840835857988, '邓勇70', 'USR202608310009', '邓勇70@foxmail.com', 'MTM4NDcyMjAwOTn4eozhJ5J4i6ujKUZNdsRObSDU0Knu1B9p18cK', '63ad9e15a30f1ad06b6886ed233380a3',
    'EMP902784', '勇', '邓', '邓勇', '$2a$10$728c999fd18e4551ae8e6b7b4f022f19',
    'EXPIRED', NULL, '2024-10-22 10:48:58', 1,
    '["USER"]'::jsonb, '{"theme":"light","lang":"zh-CN","notify":true,"privacy":"high"}'::jsonb, 9, 0,
    '2025-08-08 05:47:39', '2025-09-01 05:47:39', 'hr_system', 'admin',
    2, NULL,
    NULL, NULL, NULL, NULL, 'EXT760359', NULL
);
INSERT INTO sys_user (
    tab_id, my_user_account, my_user_id, my_email, my_phone, my_phone_index,
    my_employee_id, my_first_name, my_last_name, my_full_name, password_hash,
    my_account_status, time_locked_until, time_password_expires_at, failed_attempts,
    my_roles, my_profiles, my_version, bool_deleted,
    time_created_at, time_updated_at, who_created_by, who_updated_by,
    my_tenant_id, my_key,
    key_01, key_02, key_03, key_04, key_05, key_06
) VALUES (
    7130316843947950300, '曹博文94', 'USR202608310010', '曹博文94@nothingless.tech', 'MTM4NzE5NjgxMTZdGYXCp2zop6zCjteBKfAJGrNyIxQPfmYKTnpA', '61ae560fcacf71dbb0129639076ee3b5',
    'EMP219946', '博文', '曹', '曹博文', '$2a$10$8d181ee1595342a693ec8696d0fb601f',
    'EXPIRED', NULL, '2024-06-18 07:09:12', 1,
    '["USER","DEVELOPER"]'::jsonb, '{"theme":"light","lang":"zh-CN","notify":true,"privacy":"high"}'::jsonb, 5, 0,
    '2025-04-15 14:39:30', '2025-05-23 14:39:30', 'system', 'admin',
    2, NULL,
    NULL, NULL, NULL, 'EXT416922', 'EXT208963', NULL
);
INSERT INTO sys_user (
    tab_id, my_user_account, my_user_id, my_email, my_phone, my_phone_index,
    my_employee_id, my_first_name, my_last_name, my_full_name, password_hash,
    my_account_status, time_locked_until, time_password_expires_at, failed_attempts,
    my_roles, my_profiles, my_version, bool_deleted,
    time_created_at, time_updated_at, who_created_by, who_updated_by,
    my_tenant_id, my_key,
    key_01, key_02, key_03, key_04, key_05, key_06
) VALUES (
    7130316849102779202, '谢刚86', 'USR202608310011', '谢刚86@qq.com', 'MTM4ODE1MDM4NTbbvSOuBtf6Nt25607eWor37t+JpX0sjuZ87cKs', 'd6f1734b1d11321904e5249487c9c471',
    NULL, '刚', '谢', '谢刚', '$2a$10$915b432a006e4c5e959ea78a7865637a',
    'ACTIVE', NULL, NULL, 0,
    '["ADMIN","AUDITOR"]'::jsonb, '{"lang":"zh-CN","dashboard":"classic"}'::jsonb, 10, 0,
    '2024-08-05 05:12:43', '2024-09-30 05:12:43', 'admin', 'batch_import',
    1, NULL,
    NULL, NULL, NULL, NULL, NULL, NULL
);
INSERT INTO sys_user (
    tab_id, my_user_account, my_user_id, my_email, my_phone, my_phone_index,
    my_employee_id, my_first_name, my_last_name, my_full_name, password_hash,
    my_account_status, time_locked_until, time_password_expires_at, failed_attempts,
    my_roles, my_profiles, my_version, bool_deleted,
    time_created_at, time_updated_at, who_created_by, who_updated_by,
    my_tenant_id, my_key,
    key_01, key_02, key_03, key_04, key_05, key_06
) VALUES (
    7130316853385231959, 'user_b634bbd0', 'USR202608310012', 'user_b634bbd0@gmail.com', 'MTM4OTkxMTUyMDW88rDZqbToipyAdj1ioT1eYm73jZAzY5d0uFua', '08fee1a69fb22f80fe7cc56c56fb8376',
    NULL, '芳', '许', '许芳', '$2a$10$0a237b3d6af4433b9f1ac404091e2657',
    'LOCKED', '2026-04-09 07:40:38', NULL, 7,
    '["USER"]'::jsonb, '{"theme":"dark","lang":"zh-CN","notify":true}'::jsonb, 9, 0,
    '2025-06-25 16:48:08', '2025-08-24 16:48:08', 'admin', 'hr_system',
    1, '7130316843947950300',
    NULL, NULL, NULL, 'EXT265742', 'EXT501935', NULL
);
INSERT INTO sys_user (
    tab_id, my_user_account, my_user_id, my_email, my_phone, my_phone_index,
    my_employee_id, my_first_name, my_last_name, my_full_name, password_hash,
    my_account_status, time_locked_until, time_password_expires_at, failed_attempts,
    my_roles, my_profiles, my_version, bool_deleted,
    time_created_at, time_updated_at, who_created_by, who_updated_by,
    my_tenant_id, my_key,
    key_01, key_02, key_03, key_04, key_05, key_06
) VALUES (
    7130316854802882492, '张瑾瑜16', 'USR202608310013', '张瑾瑜16@163.com', 'MTM4MjE0MzI3ODd/PNVzwubimNucHjJqbIcpUHpYJlAB0ebwlRB2', 'f406e93ab257ede9a87e816a6760c538',
    'EMP396451', '瑾瑜', '张', '张瑾瑜', '$2a$10$e80b46af8de749f0b8f99411330c2599',
    'ACTIVE', NULL, NULL, 0,
    '["USER","DEVELOPER"]'::jsonb, '{"theme":"dark","lang":"ja-JP","notify":true}'::jsonb, 13, 0,
    '2024-03-30 03:35:07', '2024-05-03 03:35:07', 'admin', 'admin',
    2, '7130316809978341044',
    NULL, 'EXT624948', 'EXT597856', 'EXT461485', NULL, NULL
);
INSERT INTO sys_user (
    tab_id, my_user_account, my_user_id, my_email, my_phone, my_phone_index,
    my_employee_id, my_first_name, my_last_name, my_full_name, password_hash,
    my_account_status, time_locked_until, time_password_expires_at, failed_attempts,
    my_roles, my_profiles, my_version, bool_deleted,
    time_created_at, time_updated_at, who_created_by, who_updated_by,
    my_tenant_id, my_key,
    key_01, key_02, key_03, key_04, key_05, key_06
) VALUES (
    7130316861723482294, 'user_4b5e7cfd', 'USR202608310014', 'user_4b5e7cfd@qq.com', 'MTM4NTMyNjEyNzCADS51CokUWfDijlzf+y7wstGqpDVSqNL9k80S', 'f7327be7edfba6106c0c3dabc5fbea9b',
    'EMP429795', '睿渊', '何', '何睿渊', '$2a$10$2e1fde5fbff645648499a717eb2e85c8',
    'ACTIVE', NULL, NULL, 1,
    '["GUEST"]'::jsonb, '{"theme":"dark","lang":"zh-CN","notify":true}'::jsonb, 14, 0,
    '2024-11-16 23:29:35', '2024-11-19 23:29:35', 'admin', 'admin',
    3, '7130316834376625185',
    NULL, 'EXT214446', NULL, NULL, NULL, 'EXT509257'
);
INSERT INTO sys_user (
    tab_id, my_user_account, my_user_id, my_email, my_phone, my_phone_index,
    my_employee_id, my_first_name, my_last_name, my_full_name, password_hash,
    my_account_status, time_locked_until, time_password_expires_at, failed_attempts,
    my_roles, my_profiles, my_version, bool_deleted,
    time_created_at, time_updated_at, who_created_by, who_updated_by,
    my_tenant_id, my_key,
    key_01, key_02, key_03, key_04, key_05, key_06
) VALUES (
    7130316863434659752, 'user_8979c2a3', 'USR202608310015', 'user_8979c2a3@nothingless.tech', 'MTM4NDAxNTg4MTE67DxO/5WL1PfxfOlKxGFFI43UrogBkJj6TOT3', '2b114508e08255ca3d4c6aa580e32f01',
    'EMP678727', '勇', '谢', '谢勇', '$2a$10$83794debd0384e3ab0b0be76b8c6a195',
    'LOCKED', '2026-10-20 23:42:18', NULL, 10,
    '["ADMIN","AUDITOR"]'::jsonb, '{"theme":"dark","lang":"ja-JP","notify":true}'::jsonb, 7, 0,
    '2025-03-20 03:04:50', '2025-04-13 03:04:50', 'admin', 'hr_system',
    1, NULL,
    NULL, 'EXT400749', NULL, NULL, 'EXT160493', NULL
);
INSERT INTO sys_user (
    tab_id, my_user_account, my_user_id, my_email, my_phone, my_phone_index,
    my_employee_id, my_first_name, my_last_name, my_full_name, password_hash,
    my_account_status, time_locked_until, time_password_expires_at, failed_attempts,
    my_roles, my_profiles, my_version, bool_deleted,
    time_created_at, time_updated_at, who_created_by, who_updated_by,
    my_tenant_id, my_key,
    key_01, key_02, key_03, key_04, key_05, key_06
) VALUES (
    7130316867264088735, '冯强66', 'USR202608310016', '冯强66@qq.com', 'MTM4MjkzNDgyMTLRTybwh63LKajCovkSI3iTdC7eMjPjVZkOF6Yc', '53e487caab375a85ef147c24fdbf0fc3',
    'EMP493049', '强', '冯', '冯强', '$2a$10$fe24d63cb64d4321a83e6cf7726752ed',
    'ACTIVE', NULL, NULL, 0,
    '["GUEST"]'::jsonb, '{"theme":"auto","lang":"zh-CN","notify":true,"2fa":true}'::jsonb, 5, 0,
    '2024-05-15 23:31:45', '2024-05-20 23:31:45', 'admin', 'hr_system',
    2, NULL,
    NULL, NULL, NULL, NULL, NULL, 'EXT488853'
);
INSERT INTO sys_user (
    tab_id, my_user_account, my_user_id, my_email, my_phone, my_phone_index,
    my_employee_id, my_first_name, my_last_name, my_full_name, password_hash,
    my_account_status, time_locked_until, time_password_expires_at, failed_attempts,
    my_roles, my_profiles, my_version, bool_deleted,
    time_created_at, time_updated_at, who_created_by, who_updated_by,
    my_tenant_id, my_key,
    key_01, key_02, key_03, key_04, key_05, key_06
) VALUES (
    7130316875170391310, '张子轩85', 'USR202608310017', '张子轩85@outlook.com', 'MTM4NzEzMTU0NjSaZcT3NnnDt5eXC8qMBBn+knW0cGGARjEUnuER', '998c23f3f02ad8bedae8d4f68c1d87f4',
    'EMP867934', '子轩', '张', '张子轩', '$2a$10$a503b10481064b098e777357334f2c23',
    'ACTIVE', NULL, '2026-09-11 18:29:09', 0,
    '["GUEST"]'::jsonb, '{"theme":"auto","lang":"zh-CN","notify":true,"2fa":true}'::jsonb, 6, 0,
    '2024-04-12 15:38:13', '2024-06-01 15:38:13', 'admin', 'batch_import',
    2, '7130316840835857988',
    'EXT991052', NULL, 'EXT783824', 'EXT844298', NULL, 'EXT148594'
);
INSERT INTO sys_user (
    tab_id, my_user_account, my_user_id, my_email, my_phone, my_phone_index,
    my_employee_id, my_first_name, my_last_name, my_full_name, password_hash,
    my_account_status, time_locked_until, time_password_expires_at, failed_attempts,
    my_roles, my_profiles, my_version, bool_deleted,
    time_created_at, time_updated_at, who_created_by, who_updated_by,
    my_tenant_id, my_key,
    key_01, key_02, key_03, key_04, key_05, key_06
) VALUES (
    7130316878735535287, 'user_2f079890', 'USR202608310018', 'user_2f079890@corp.nothingless.com', 'MTM4MjY1ODc3OTnovIbDvjd38QyncSDtmtE7RxcTm/w7MXhFxui9', '6857a5a741d007c7fcb7cdc344598d26',
    'EMP879238', '沐晴', '冯', '冯沐晴', '$2a$10$78d9c690b6aa420fa8370630a4e2e6fc',
    'LOCKED', '2026-11-22 13:05:20', NULL, 5,
    '["GUEST"]'::jsonb, '{"theme":"light","lang":"en-US","notify":false}'::jsonb, 15, 0,
    '2025-04-23 04:35:20', '2025-05-19 04:35:20', 'batch_import', 'system',
    3, NULL,
    NULL, NULL, NULL, 'EXT386178', NULL, 'EXT377096'
);
INSERT INTO sys_user (
    tab_id, my_user_account, my_user_id, my_email, my_phone, my_phone_index,
    my_employee_id, my_first_name, my_last_name, my_full_name, password_hash,
    my_account_status, time_locked_until, time_password_expires_at, failed_attempts,
    my_roles, my_profiles, my_version, bool_deleted,
    time_created_at, time_updated_at, who_created_by, who_updated_by,
    my_tenant_id, my_key,
    key_01, key_02, key_03, key_04, key_05, key_06
) VALUES (
    7130316879951927508, '黄磊68', 'USR202608310019', '黄磊68@qq.com', 'MTM4OTU4MzEyODYKGap8QGkjam53qEsBjUpCgFk4DUMHt3mlCFmH', '4c2da278408ff62a9a35baadee5ef859',
    NULL, '磊', '黄', '黄磊', '$2a$10$ec6832001b6e40cb88dfe51605bd4adb',
    'ACTIVE', NULL, NULL, 2,
    '["ADMIN"]'::jsonb, '{"beta":true,"theme":"dark","lang":"zh-CN"}'::jsonb, 14, 0,
    '2025-08-27 07:17:19', '2025-09-19 07:17:19', 'admin', 'admin',
    1, NULL,
    NULL, NULL, 'EXT953895', 'EXT547781', NULL, NULL
);
INSERT INTO sys_user (
    tab_id, my_user_account, my_user_id, my_email, my_phone, my_phone_index,
    my_employee_id, my_first_name, my_last_name, my_full_name, password_hash,
    my_account_status, time_locked_until, time_password_expires_at, failed_attempts,
    my_roles, my_profiles, my_version, bool_deleted,
    time_created_at, time_updated_at, who_created_by, who_updated_by,
    my_tenant_id, my_key,
    key_01, key_02, key_03, key_04, key_05, key_06
) VALUES (
    7130316884016119635, 'user_cd63eba0', 'USR202608310020', 'user_cd63eba0@qq.com', 'MTM4MjA4NDM1MTSkSyFAjKbDlujcMjpu3Od0063ozNQwoNqggr9O', 'edcbf4c0d0c7a2246ea0c783ef39bcc9',
    'EMP597305', '博文', '冯', '冯博文', '$2a$10$72c0b5d51e5f42839e4981f8191955f8',
    'ACTIVE', NULL, '2026-03-14 09:56:06', 0,
    '["GUEST"]'::jsonb, '{"theme":"light","lang":"en-US","notify":false}'::jsonb, 11, 0,
    '2025-09-22 12:28:34', '2025-09-30 12:28:34', 'admin', 'system',
    3, '7130316861723482294',
    NULL, NULL, 'EXT911260', 'EXT54945', 'EXT630575', NULL
);
INSERT INTO sys_user (
    tab_id, my_user_account, my_user_id, my_email, my_phone, my_phone_index,
    my_employee_id, my_first_name, my_last_name, my_full_name, password_hash,
    my_account_status, time_locked_until, time_password_expires_at, failed_attempts,
    my_roles, my_profiles, my_version, bool_deleted,
    time_created_at, time_updated_at, who_created_by, who_updated_by,
    my_tenant_id, my_key,
    key_01, key_02, key_03, key_04, key_05, key_06
) VALUES (
    7130316888525034739, 'user_37f380eb', 'USR202608310021', 'user_37f380eb@qq.com', 'MTM4MjU0MTk2NDWOc9sNiA5ci56tswNcSc0jSA8ubsDW6K5QvZ+m', 'fadff549dc2863c0e2b3ebfd226355dd',
    'EMP695164', '俊豪', '梁', '梁俊豪', '$2a$10$5f6944bb41e54cb2ad2bc9ecf9610432',
    'EXPIRED', NULL, '2024-03-06 23:42:50', 0,
    '["MANAGER"]'::jsonb, '{"theme":"auto","lang":"zh-CN","notify":true,"2fa":true}'::jsonb, 1, 0,
    '2025-06-07 11:41:14', '2025-06-12 11:41:14', 'batch_import', 'hr_system',
    4, NULL,
    NULL, NULL, 'EXT120346', NULL, NULL, NULL
);
INSERT INTO sys_user (
    tab_id, my_user_account, my_user_id, my_email, my_phone, my_phone_index,
    my_employee_id, my_first_name, my_last_name, my_full_name, password_hash,
    my_account_status, time_locked_until, time_password_expires_at, failed_attempts,
    my_roles, my_profiles, my_version, bool_deleted,
    time_created_at, time_updated_at, who_created_by, who_updated_by,
    my_tenant_id, my_key,
    key_01, key_02, key_03, key_04, key_05, key_06
) VALUES (
    7130316894820760030, 'user_060da971', 'USR202608310022', 'user_060da971@sohu.com', 'MTM4NTA0NTU4MjFsRoKUpz0D/txZQsJ1tSTLFd8J6yeg28/VlDrP', '4b4c88ab6b33f724cd7cbdf45c574e9a',
    NULL, '明', '李', '李明', '$2a$10$97aef565984249e0865ee5a47255d1e6',
    'ACTIVE', NULL, '2027-04-26 02:41:24', 0,
    '["USER","MANAGER","ADMIN"]'::jsonb, '{"theme":"light","lang":"zh-CN","notify":true,"privacy":"high"}'::jsonb, 2, 0,
    '2024-12-05 04:53:51', '2025-01-28 04:53:51', 'system', 'admin',
    4, NULL,
    NULL, NULL, NULL, NULL, NULL, NULL
);
INSERT INTO sys_user (
    tab_id, my_user_account, my_user_id, my_email, my_phone, my_phone_index,
    my_employee_id, my_first_name, my_last_name, my_full_name, password_hash,
    my_account_status, time_locked_until, time_password_expires_at, failed_attempts,
    my_roles, my_profiles, my_version, bool_deleted,
    time_created_at, time_updated_at, who_created_by, who_updated_by,
    my_tenant_id, my_key,
    key_01, key_02, key_03, key_04, key_05, key_06
) VALUES (
    7130316898658448948, 'user_0d154e0f', 'USR202608310023', 'user_0d154e0f@163.com', 'MTM4NDQzNTU0NzNlWE4mWvzt5aWhTeEi8OKbjBy0JZ7s5xMdvJIn', 'b2eba71cac16b5f6052b3911d46c944e',
    'EMP996840', '建国', '邓', '邓建国', '$2a$10$38f76d52364b4a1ab4aeb6888ea8260d',
    'ACTIVE', NULL, NULL, 2,
    '["GUEST"]'::jsonb, '{"beta":true,"theme":"dark","lang":"zh-CN"}'::jsonb, 1, 0,
    '2024-12-15 07:38:37', '2025-02-11 07:38:37', 'admin', 'admin',
    3, '7130316875170391310',
    NULL, NULL, NULL, 'EXT361120', 'EXT89425', NULL
);
INSERT INTO sys_user (
    tab_id, my_user_account, my_user_id, my_email, my_phone, my_phone_index,
    my_employee_id, my_first_name, my_last_name, my_full_name, password_hash,
    my_account_status, time_locked_until, time_password_expires_at, failed_attempts,
    my_roles, my_profiles, my_version, bool_deleted,
    time_created_at, time_updated_at, who_created_by, who_updated_by,
    my_tenant_id, my_key,
    key_01, key_02, key_03, key_04, key_05, key_06
) VALUES (
    7130316901401503725, '谢子轩77', 'USR202608310024', '谢子轩77@sina.com', 'MTM4NjAwNDMzNDWQxtGtGqshqDDFkYFMqilIs57IQiuewKhBL9i5', '6781e0901bf6ac084b1d29e37eb72d34',
    NULL, '子轩', '谢', '谢子轩', '$2a$10$dde901eab3f945c8b7b6a3da082104ea',
    'ACTIVE', NULL, '2026-09-23 10:21:09', 0,
    '["USER","MANAGER","ADMIN"]'::jsonb, '{"theme":"dark","lang":"ja-JP","notify":true}'::jsonb, 7, 0,
    '2024-04-16 20:24:14', '2024-04-25 20:24:14', 'system', 'batch_import',
    1, NULL,
    NULL, 'EXT553108', NULL, NULL, 'EXT138414', NULL
);
INSERT INTO sys_user (
    tab_id, my_user_account, my_user_id, my_email, my_phone, my_phone_index,
    my_employee_id, my_first_name, my_last_name, my_full_name, password_hash,
    my_account_status, time_locked_until, time_password_expires_at, failed_attempts,
    my_roles, my_profiles, my_version, bool_deleted,
    time_created_at, time_updated_at, who_created_by, who_updated_by,
    my_tenant_id, my_key,
    key_01, key_02, key_03, key_04, key_05, key_06
) VALUES (
    7130316905516148169, '韩翊宸31', 'USR202608310025', '韩翊宸31@foxmail.com', 'MTM4NDE4ODA3NjPjkeV3ep7wY7zh7JDD1lJmRoAa9r40P5EqUovm', '463263eabc517ed4af197155ba5ebefe',
    'EMP254506', '翊宸', '韩', '韩翊宸', '$2a$10$74f4fda8a6974a95a861f78dd6a59434',
    'ACTIVE', NULL, '2026-06-22 11:05:18', 0,
    '["USER","MANAGER","ADMIN"]'::jsonb, '{"theme":"light","lang":"zh-CN","notify":true,"privacy":"high"}'::jsonb, 0, 0,
    '2024-11-18 06:14:20', '2024-11-21 06:14:20', 'hr_system', 'admin',
    3, NULL,
    'EXT394124', NULL, NULL, NULL, 'EXT352577', 'EXT145283'
);
INSERT INTO sys_user (
    tab_id, my_user_account, my_user_id, my_email, my_phone, my_phone_index,
    my_employee_id, my_first_name, my_last_name, my_full_name, password_hash,
    my_account_status, time_locked_until, time_password_expires_at, failed_attempts,
    my_roles, my_profiles, my_version, bool_deleted,
    time_created_at, time_updated_at, who_created_by, who_updated_by,
    my_tenant_id, my_key,
    key_01, key_02, key_03, key_04, key_05, key_06
) VALUES (
    7130316909215559454, '谢军70', 'USR202608310026', '谢军70@foxmail.com', 'MTM4MTI1NTA2NTiDbkzYOJN5mj4YetbqIDj/CHtJldsAtHvVXyu4', '3079ead2aaacc04d8be303587eef93bb',
    NULL, '军', '谢', '谢军', '$2a$10$afaab28084094f88acba2e5963aad18f',
    'LOCKED', '2027-02-27 14:25:22', NULL, 3,
    '["GUEST"]'::jsonb, '{"beta":true,"theme":"dark","lang":"zh-CN"}'::jsonb, 1, 0,
    '2025-05-08 17:45:44', '2025-06-01 17:45:44', 'batch_import', 'batch_import',
    1, NULL,
    NULL, NULL, NULL, NULL, NULL, 'EXT140890'
);
INSERT INTO sys_user (
    tab_id, my_user_account, my_user_id, my_email, my_phone, my_phone_index,
    my_employee_id, my_first_name, my_last_name, my_full_name, password_hash,
    my_account_status, time_locked_until, time_password_expires_at, failed_attempts,
    my_roles, my_profiles, my_version, bool_deleted,
    time_created_at, time_updated_at, who_created_by, who_updated_by,
    my_tenant_id, my_key,
    key_01, key_02, key_03, key_04, key_05, key_06
) VALUES (
    7130316913435044565, '韩艳97', 'USR202608310027', '韩艳97@foxmail.com', 'MTM4MzQ0NjQ5MzhFIOoSlmcWZhWhnsvygRJhkrYYqYs/vN/M4cWt', '8c3dc447fc3564375a82b202702187a6',
    'EMP620306', '艳', '韩', '韩艳', '$2a$10$a373164009f245b38b8f82bfb887bded',
    'ACTIVE', NULL, '2027-02-08 08:00:12', 1,
    '["USER","DEVELOPER"]'::jsonb, '{"theme":"light","lang":"en-US","notify":false}'::jsonb, 13, 0,
    '2024-03-02 07:18:21', '2024-03-29 07:18:21', 'admin', 'admin',
    2, NULL,
    NULL, NULL, NULL, NULL, NULL, NULL
);
INSERT INTO sys_user (
    tab_id, my_user_account, my_user_id, my_email, my_phone, my_phone_index,
    my_employee_id, my_first_name, my_last_name, my_full_name, password_hash,
    my_account_status, time_locked_until, time_password_expires_at, failed_attempts,
    my_roles, my_profiles, my_version, bool_deleted,
    time_created_at, time_updated_at, who_created_by, who_updated_by,
    my_tenant_id, my_key,
    key_01, key_02, key_03, key_04, key_05, key_06
) VALUES (
    7130316918946382170, '宋建国65', 'USR202608310028', '宋建国65@outlook.com', 'MTM4OTU1NjU0ODTPulEPSeARQCJ4u7nEEE7mvb7jJ0a7y6COfzoN', '8e8388919b1c54c76872b9d501b35222',
    'EMP623285', '建国', '宋', '宋建国', '$2a$10$03ec563cc4e145768447e0d0dce534fd',
    'LOCKED', '2026-10-28 13:58:03', NULL, 4,
    '["USER","DEVELOPER"]'::jsonb, '{"lang":"zh-CN","dashboard":"classic"}'::jsonb, 14, 0,
    '2024-06-15 15:52:57', '2024-07-24 15:52:57', 'batch_import', 'hr_system',
    2, NULL,
    'EXT475001', NULL, 'EXT93055', 'EXT887593', 'EXT701280', NULL
);
INSERT INTO sys_user (
    tab_id, my_user_account, my_user_id, my_email, my_phone, my_phone_index,
    my_employee_id, my_first_name, my_last_name, my_full_name, password_hash,
    my_account_status, time_locked_until, time_password_expires_at, failed_attempts,
    my_roles, my_profiles, my_version, bool_deleted,
    time_created_at, time_updated_at, who_created_by, who_updated_by,
    my_tenant_id, my_key,
    key_01, key_02, key_03, key_04, key_05, key_06
) VALUES (
    7130316921911716249, '冯勇99', 'USR202608310029', '冯勇99@163.com', 'MTM4MjYyODg5OTJmR3n8Dbi870IsIZ7L9dLRJUCiJebusEFdQt0c', '4f3e9f5e6142d92361651137b8c1de62',
    'EMP643269', '勇', '冯', '冯勇', '$2a$10$a87bc373361842278cd83c38182ac259',
    'ACTIVE', NULL, NULL, 1,
    '["ADMIN","AUDITOR"]'::jsonb, '{"theme":"dark","lang":"ja-JP","notify":true}'::jsonb, 11, 0,
    '2025-02-07 00:57:21', '2025-04-05 00:57:21', 'batch_import', 'system',
    2, NULL,
    'EXT288782', NULL, NULL, NULL, NULL, NULL
);
INSERT INTO sys_user (
    tab_id, my_user_account, my_user_id, my_email, my_phone, my_phone_index,
    my_employee_id, my_first_name, my_last_name, my_full_name, password_hash,
    my_account_status, time_locked_until, time_password_expires_at, failed_attempts,
    my_roles, my_profiles, my_version, bool_deleted,
    time_created_at, time_updated_at, who_created_by, who_updated_by,
    my_tenant_id, my_key,
    key_01, key_02, key_03, key_04, key_05, key_06
) VALUES (
    7130316929872467322, '梁翊宸87', 'USR202608310030', '梁翊宸87@aliyun.com', 'MTM4MjA5MDE1NDcXh2vVD/6Umvd9z5joJR5Q4dT37WiuSaCjsMxC', 'bd1b5c5d2049abffb4a283d9b6830ec0',
    'EMP639995', '翊宸', '梁', '梁翊宸', '$2a$10$6c0df061d8974835a03358a26e4eb6f2',
    'LOCKED', '2026-03-24 11:24:43', NULL, 8,
    '["USER","MANAGER"]'::jsonb, '{"beta":true,"theme":"dark","lang":"zh-CN"}'::jsonb, 3, 0,
    '2024-07-26 18:02:00', '2024-08-23 18:02:00', 'admin', 'admin',
    1, NULL,
    'EXT907141', NULL, NULL, 'EXT168457', 'EXT980109', NULL
);
INSERT INTO sys_user (
    tab_id, my_user_account, my_user_id, my_email, my_phone, my_phone_index,
    my_employee_id, my_first_name, my_last_name, my_full_name, password_hash,
    my_account_status, time_locked_until, time_password_expires_at, failed_attempts,
    my_roles, my_profiles, my_version, bool_deleted,
    time_created_at, time_updated_at, who_created_by, who_updated_by,
    my_tenant_id, my_key,
    key_01, key_02, key_03, key_04, key_05, key_06
) VALUES (
    7130316931365651737, 'user_77ed510c', 'USR202608310031', 'user_77ed510c@outlook.com', 'MTM4NjI3ODAwNzLqe260GBmQ/fCSBDfcRIe7zrsXzRpjuZMlxeaP', 'edba99a8c15b12ea0473d7286206a9d1',
    'EMP753516', '晨曦', '郭', '郭晨曦', '$2a$10$6fd79a0b165c48e7bcefcb919114088d',
    'EXPIRED', NULL, '2024-04-02 06:47:52', 0,
    '["ADMIN"]'::jsonb, '{"theme":"dark","lang":"en-US","timezone":"Asia/Shanghai"}'::jsonb, 11, 0,
    '2025-09-09 09:57:29', '2025-09-30 09:57:29', 'admin', 'batch_import',
    1, NULL,
    NULL, NULL, NULL, NULL, NULL, NULL
);
INSERT INTO sys_user (
    tab_id, my_user_account, my_user_id, my_email, my_phone, my_phone_index,
    my_employee_id, my_first_name, my_last_name, my_full_name, password_hash,
    my_account_status, time_locked_until, time_password_expires_at, failed_attempts,
    my_roles, my_profiles, my_version, bool_deleted,
    time_created_at, time_updated_at, who_created_by, who_updated_by,
    my_tenant_id, my_key,
    key_01, key_02, key_03, key_04, key_05, key_06
) VALUES (
    7130316936176505977, 'user_6b447b43', 'USR202608310032', 'user_6b447b43@corp.nothingless.com', 'MTM4NTUxNDcyODD6mfMIvKk41Z0N8od0GvVXwkt8EDhhCeGg1k3T', '2c3cf719611f173ac4bb35ee165c385b',
    'EMP530311', '翊宸', '徐', '徐翊宸', '$2a$10$03afde2555074ebcb5c6ba7279d752e7',
    'LOCKED', '2027-08-25 22:31:50', NULL, 10,
    '["USER"]'::jsonb, '{"theme":"auto","lang":"zh-CN","notify":true,"2fa":true}'::jsonb, 6, 0,
    '2025-03-11 15:41:14', '2025-03-31 15:41:14', 'hr_system', 'admin',
    4, NULL,
    NULL, 'EXT359955', NULL, NULL, 'EXT714813', NULL
);
INSERT INTO sys_user (
    tab_id, my_user_account, my_user_id, my_email, my_phone, my_phone_index,
    my_employee_id, my_first_name, my_last_name, my_full_name, password_hash,
    my_account_status, time_locked_until, time_password_expires_at, failed_attempts,
    my_roles, my_profiles, my_version, bool_deleted,
    time_created_at, time_updated_at, who_created_by, who_updated_by,
    my_tenant_id, my_key,
    key_01, key_02, key_03, key_04, key_05, key_06
) VALUES (
    7130316941385863038, 'user_15fa3e86', 'USR202608310033', 'user_15fa3e86@outlook.com', 'MTM4Mzc4MjAzNTT6ovWyjJM+wsqwSpQVkyix4oP1bWeKi0Y3enwZ', '8db20812c34494a88fd7e0c5c5f73bd4',
    'EMP656837', '超', '黄', '黄超', '$2a$10$fda131fd8a9d4d15aca66788c208e147',
    'ACTIVE', NULL, NULL, 2,
    '["ADMIN"]'::jsonb, '{"theme":"dark","lang":"en-US","timezone":"Asia/Shanghai"}'::jsonb, 10, 0,
    '2025-07-11 04:31:24', '2025-08-10 04:31:24', 'system', 'admin',
    1, '7130316849102779202',
    NULL, 'EXT922068', NULL, NULL, 'EXT302198', NULL
);
INSERT INTO sys_user (
    tab_id, my_user_account, my_user_id, my_email, my_phone, my_phone_index,
    my_employee_id, my_first_name, my_last_name, my_full_name, password_hash,
    my_account_status, time_locked_until, time_password_expires_at, failed_attempts,
    my_roles, my_profiles, my_version, bool_deleted,
    time_created_at, time_updated_at, who_created_by, who_updated_by,
    my_tenant_id, my_key,
    key_01, key_02, key_03, key_04, key_05, key_06
) VALUES (
    7130316945378783964, 'user_bcd47fc3', 'USR202608310034', 'user_bcd47fc3@nothingless.tech', 'MTM4MTQ4Nzc0NTHL/V+UEwSYNquR6PxE74tiOalT6oNfB6yXYlnP', 'db4f637b0a2d95855c672e6c47b7cb00',
    'EMP640310', '瑾瑜', '郑', '郑瑾瑜', '$2a$10$5deeb9e2566d4be6893de8d5ff0d8a5c',
    'ACTIVE', NULL, NULL, 0,
    '["ADMIN"]'::jsonb, '{"theme":"auto","lang":"zh-CN","notify":true,"2fa":true}'::jsonb, 4, 0,
    '2025-01-05 20:23:22', '2025-01-25 20:23:22', 'admin', 'system',
    2, NULL,
    NULL, NULL, NULL, NULL, NULL, NULL
);
INSERT INTO sys_user (
    tab_id, my_user_account, my_user_id, my_email, my_phone, my_phone_index,
    my_employee_id, my_first_name, my_last_name, my_full_name, password_hash,
    my_account_status, time_locked_until, time_password_expires_at, failed_attempts,
    my_roles, my_profiles, my_version, bool_deleted,
    time_created_at, time_updated_at, who_created_by, who_updated_by,
    my_tenant_id, my_key,
    key_01, key_02, key_03, key_04, key_05, key_06
) VALUES (
    7130316948344205820, 'user_6a0e2b4b', 'USR202608310035', 'user_6a0e2b4b@163.com', 'MTM4MjU3NTkyNzSXvHFwRPRO6L/U8W9+KeS5JzkfZ0xUp+I7afou', 'fb26e6615972ca3c388e893b7f349b8f',
    'EMP567122', '磊', '梁', '梁磊', '$2a$10$96c3f15b78ae418d82aaf15f3c23ce49',
    'ACTIVE', NULL, NULL, 1,
    '["GUEST"]'::jsonb, '{"beta":true,"theme":"dark","lang":"zh-CN"}'::jsonb, 1, 0,
    '2025-03-10 00:44:05', '2025-04-08 00:44:05', 'batch_import', 'system',
    4, NULL,
    NULL, NULL, NULL, NULL, 'EXT568451', 'EXT980981'
);
INSERT INTO sys_user (
    tab_id, my_user_account, my_user_id, my_email, my_phone, my_phone_index,
    my_employee_id, my_first_name, my_last_name, my_full_name, password_hash,
    my_account_status, time_locked_until, time_password_expires_at, failed_attempts,
    my_roles, my_profiles, my_version, bool_deleted,
    time_created_at, time_updated_at, who_created_by, who_updated_by,
    my_tenant_id, my_key,
    key_01, key_02, key_03, key_04, key_05, key_06
) VALUES (
    7130316954832752411, 'user_c8c3fcd7', 'USR202608310036', 'user_c8c3fcd7@163.com', 'MTM4NjEzMzU1NDXlwcApRLI8W8lBcgELmO3ZwnV+67FPjWA5ENYI', 'd7e367aa7f94e2109e7d4c596c9b3722',
    'EMP170559', '平', '李', '李平', '$2a$10$f03ba1a2fe684ce9998e71790fb44cc0',
    'ACTIVE', NULL, NULL, 2,
    '["USER"]'::jsonb, '{"theme":"dark","lang":"ja-JP","notify":true}'::jsonb, 1, 0,
    '2024-11-08 10:46:10', '2024-12-06 10:46:10', 'admin', 'admin',
    2, '7130316820665346706',
    NULL, 'EXT246536', 'EXT768051', NULL, NULL, 'EXT859136'
);
INSERT INTO sys_user (
    tab_id, my_user_account, my_user_id, my_email, my_phone, my_phone_index,
    my_employee_id, my_first_name, my_last_name, my_full_name, password_hash,
    my_account_status, time_locked_until, time_password_expires_at, failed_attempts,
    my_roles, my_profiles, my_version, bool_deleted,
    time_created_at, time_updated_at, who_created_by, who_updated_by,
    my_tenant_id, my_key,
    key_01, key_02, key_03, key_04, key_05, key_06
) VALUES (
    7130316956565043624, '邓洋76', 'USR202608310037', '邓洋76@gmail.com', 'MTM4MTgxODE1OTVZ2v0YsMOj1dFMmcBe8ntzmUntHdPVRMZ8gmip', 'fda11351367d562a20e5d4ecfcb98a91',
    'EMP571635', '洋', '邓', '邓洋', '$2a$10$3d0bdd419c334927b22ae5eca2797904',
    'ACTIVE', NULL, NULL, 0,
    '["USER","MANAGER"]'::jsonb, '{"theme":"dark","lang":"zh-CN","notify":true}'::jsonb, 8, 0,
    '2024-10-19 22:55:14', '2024-12-01 22:55:14', 'admin', 'admin',
    1, '7130316854802882492',
    'EXT617126', 'EXT586997', NULL, NULL, 'EXT318915', 'EXT17078'
);
INSERT INTO sys_user (
    tab_id, my_user_account, my_user_id, my_email, my_phone, my_phone_index,
    my_employee_id, my_first_name, my_last_name, my_full_name, password_hash,
    my_account_status, time_locked_until, time_password_expires_at, failed_attempts,
    my_roles, my_profiles, my_version, bool_deleted,
    time_created_at, time_updated_at, who_created_by, who_updated_by,
    my_tenant_id, my_key,
    key_01, key_02, key_03, key_04, key_05, key_06
) VALUES (
    7130316960289571777, '宋俊豪99', 'USR202608310038', '宋俊豪99@sohu.com', 'MTM4NDIxMjI0NTekkMLut52FuP7uMvCjaL2g0xdxSgiF1ZdOZKh1', '4af261d3840bed7203e062570145eb89',
    'EMP973844', '俊豪', '宋', '宋俊豪', '$2a$10$7d05d34d84e24ef1a77e6dffb9d68588',
    'ACTIVE', NULL, NULL, 1,
    '["ADMIN","AUDITOR"]'::jsonb, '{"lang":"zh-CN","dashboard":"classic"}'::jsonb, 15, 0,
    '2025-07-16 00:05:51', '2025-08-26 00:05:51', 'hr_system', 'hr_system',
    1, '7130316898658448948',
    NULL, 'EXT513036', NULL, 'EXT673985', 'EXT550395', NULL
);
INSERT INTO sys_user (
    tab_id, my_user_account, my_user_id, my_email, my_phone, my_phone_index,
    my_employee_id, my_first_name, my_last_name, my_full_name, password_hash,
    my_account_status, time_locked_until, time_password_expires_at, failed_attempts,
    my_roles, my_profiles, my_version, bool_deleted,
    time_created_at, time_updated_at, who_created_by, who_updated_by,
    my_tenant_id, my_key,
    key_01, key_02, key_03, key_04, key_05, key_06
) VALUES (
    7130316967214334349, 'user_03c1f52d', 'USR202608310039', 'user_03c1f52d@gmail.com', 'MTM4MzkzNTYwNTfpvx73+QgD0gYIjJII3Fs2MUx7YoG1iMsov8/r', '7acf9bd2a9c5496e8238539d609827e8',
    'EMP831105', '伟', '唐', '唐伟', '$2a$10$b9cddb18aed74c7288e9a2b6169a87ae',
    'ACTIVE', NULL, NULL, 1,
    '["ADMIN"]'::jsonb, '{"theme":"dark","lang":"zh-CN","notify":true}'::jsonb, 2, 0,
    '2024-11-10 18:56:06', '2024-12-04 18:56:06', 'hr_system', 'admin',
    5, NULL,
    NULL, NULL, NULL, NULL, 'EXT594343', 'EXT100994'
);
INSERT INTO sys_user (
    tab_id, my_user_account, my_user_id, my_email, my_phone, my_phone_index,
    my_employee_id, my_first_name, my_last_name, my_full_name, password_hash,
    my_account_status, time_locked_until, time_password_expires_at, failed_attempts,
    my_roles, my_profiles, my_version, bool_deleted,
    time_created_at, time_updated_at, who_created_by, who_updated_by,
    my_tenant_id, my_key,
    key_01, key_02, key_03, key_04, key_05, key_06
) VALUES (
    7130316971614152538, 'user_043bb7b7', 'USR202608310040', 'user_043bb7b7@nothingless.tech', 'MTM4ODU2MzMwMTMQW6EIaknLJ5lTe8epxEcosRsy33YmrsunD4vm', '0fed2f3c7d3599390a3d93f6f687ee5a',
    'EMP473095', '瑾瑜', '黄', '黄瑾瑜', '$2a$10$fa93dfbd72a0477bbbe721522b7f129e',
    'LOCKED', '2026-10-19 14:27:59', NULL, 9,
    '["MANAGER"]'::jsonb, '{"theme":"dark","lang":"en-US","timezone":"Asia/Shanghai"}'::jsonb, 2, 0,
    '2025-08-22 18:14:37', '2025-09-30 18:14:37', 'batch_import', 'admin',
    1, NULL,
    NULL, 'EXT665693', NULL, NULL, NULL, NULL
);
INSERT INTO sys_user (
    tab_id, my_user_account, my_user_id, my_email, my_phone, my_phone_index,
    my_employee_id, my_first_name, my_last_name, my_full_name, password_hash,
    my_account_status, time_locked_until, time_password_expires_at, failed_attempts,
    my_roles, my_profiles, my_version, bool_deleted,
    time_created_at, time_updated_at, who_created_by, who_updated_by,
    my_tenant_id, my_key,
    key_01, key_02, key_03, key_04, key_05, key_06
) VALUES (
    7130316976014048827, 'user_d363c6dc', 'USR202608310041', 'user_d363c6dc@qq.com', 'MTM4ODQ3NTcyNjXQNA0ttS+mxQaV08YrfFbCVkeJmon8SiBV3o3X', '59138b0b123af326b37af57cf97cf7f6',
    'EMP920685', '霞', '孙', '孙霞', '$2a$10$25502b5ea1004ff8aae31b56a6e97718',
    'ACTIVE', NULL, NULL, 1,
    '["USER","MANAGER"]'::jsonb, '{"beta":true,"theme":"dark","lang":"zh-CN"}'::jsonb, 6, 0,
    '2024-12-21 15:09:34', '2024-12-27 15:09:34', 'admin', 'batch_import',
    1, '7130316894820760030',
    'EXT399343', 'EXT346549', NULL, NULL, 'EXT683661', 'EXT143736'
);
INSERT INTO sys_user (
    tab_id, my_user_account, my_user_id, my_email, my_phone, my_phone_index,
    my_employee_id, my_first_name, my_last_name, my_full_name, password_hash,
    my_account_status, time_locked_until, time_password_expires_at, failed_attempts,
    my_roles, my_profiles, my_version, bool_deleted,
    time_created_at, time_updated_at, who_created_by, who_updated_by,
    my_tenant_id, my_key,
    key_01, key_02, key_03, key_04, key_05, key_06
) VALUES (
    7130316977448502893, 'user_e26c6f1c', 'USR202608310042', 'user_e26c6f1c@corp.nothingless.com', 'MTM4NzU5MDE4ODV8psCPybo6ZlwN7GvglSPR/0ebe4FO2MEl5fXN', '0ad7147fc832fa4a0d6f3daa0b8b6cf9',
    'EMP825309', '桂英', '郭', '郭桂英', '$2a$10$889e41a44a7245d8aea1fd9a06c98987',
    'ACTIVE', NULL, NULL, 2,
    '["ADMIN","AUDITOR"]'::jsonb, '{"theme":"light","lang":"en-US","notify":false}'::jsonb, 3, 0,
    '2025-08-20 06:20:32', '2025-09-04 06:20:32', 'batch_import', 'admin',
    1, NULL,
    NULL, NULL, 'EXT348704', 'EXT452365', NULL, NULL
);
INSERT INTO sys_user (
    tab_id, my_user_account, my_user_id, my_email, my_phone, my_phone_index,
    my_employee_id, my_first_name, my_last_name, my_full_name, password_hash,
    my_account_status, time_locked_until, time_password_expires_at, failed_attempts,
    my_roles, my_profiles, my_version, bool_deleted,
    time_created_at, time_updated_at, who_created_by, who_updated_by,
    my_tenant_id, my_key,
    key_01, key_02, key_03, key_04, key_05, key_06
) VALUES (
    7130316981307240003, 'user_68eb8a0b', 'USR202608310043', 'user_68eb8a0b@sohu.com', 'MTM4NTYzMDA3MzgM9zWX1Cs7SLKfr+lp97LzMeDnoyKZFjoLrzdU', '1281133a76be2bf9c702c2edb1e5f0af',
    'EMP876459', '博文', '孙', '孙博文', '$2a$10$ac2ea279d853420ca5439a3d5e7ce8d2',
    'ACTIVE', NULL, NULL, 2,
    '["MANAGER"]'::jsonb, '{"theme":"light","lang":"zh-CN","notify":true,"privacy":"high"}'::jsonb, 13, 0,
    '2024-12-24 21:18:49', '2025-01-07 21:18:49', 'hr_system', 'admin',
    1, '7130316918946382170',
    NULL, 'EXT926994', NULL, NULL, NULL, 'EXT216780'
);
INSERT INTO sys_user (
    tab_id, my_user_account, my_user_id, my_email, my_phone, my_phone_index,
    my_employee_id, my_first_name, my_last_name, my_full_name, password_hash,
    my_account_status, time_locked_until, time_password_expires_at, failed_attempts,
    my_roles, my_profiles, my_version, bool_deleted,
    time_created_at, time_updated_at, who_created_by, who_updated_by,
    my_tenant_id, my_key,
    key_01, key_02, key_03, key_04, key_05, key_06
) VALUES (
    7130316985744769857, 'user_14d18f77', 'USR202608310044', 'user_14d18f77@aliyun.com', 'MTM4NDUyMTI0Nzb6oMbIOrK06liYK0SgPHqcO12/SMbWRsTYX/lY', 'd2b6027e12af55057f2cbdef97b8d782',
    'EMP613426', '睿渊', '许', '许睿渊', '$2a$10$e90698cc687d492ea8a63441dadcb02f',
    'ACTIVE', NULL, NULL, 0,
    '["USER","MANAGER","ADMIN"]'::jsonb, '{"theme":"dark","lang":"zh-CN","notify":true}'::jsonb, 11, 0,
    '2024-01-08 10:38:53', '2024-02-08 10:38:53', 'admin', 'admin',
    4, '7130316941385863038',
    NULL, NULL, NULL, 'EXT466412', NULL, NULL
);
INSERT INTO sys_user (
    tab_id, my_user_account, my_user_id, my_email, my_phone, my_phone_index,
    my_employee_id, my_first_name, my_last_name, my_full_name, password_hash,
    my_account_status, time_locked_until, time_password_expires_at, failed_attempts,
    my_roles, my_profiles, my_version, bool_deleted,
    time_created_at, time_updated_at, who_created_by, who_updated_by,
    my_tenant_id, my_key,
    key_01, key_02, key_03, key_04, key_05, key_06
) VALUES (
    7130316988873763160, 'user_f248edcf', 'USR202608310045', 'user_f248edcf@outlook.com', 'MTM4NzY0Nzk4MzE5Oom7FeFv2TR+mBDmhrIs4Dx5a7PbVEdpaR6z', 'd50a5dfca093f5943a2d1875e6102b31',
    'EMP725850', '霞', '李', '李霞', '$2a$10$1e7d25b6c7f94ef3898f3b088c624372',
    'LOCKED', '2026-05-11 18:46:53', NULL, 8,
    '["USER","DEVELOPER"]'::jsonb, '{"lang":"zh-CN","dashboard":"classic"}'::jsonb, 8, 0,
    '2025-10-28 10:35:35', '2025-11-29 10:35:35', 'system', 'admin',
    4, '7130316878735535287',
    'EXT134051', 'EXT140136', NULL, 'EXT869726', NULL, 'EXT412776'
);
INSERT INTO sys_user (
    tab_id, my_user_account, my_user_id, my_email, my_phone, my_phone_index,
    my_employee_id, my_first_name, my_last_name, my_full_name, password_hash,
    my_account_status, time_locked_until, time_password_expires_at, failed_attempts,
    my_roles, my_profiles, my_version, bool_deleted,
    time_created_at, time_updated_at, who_created_by, who_updated_by,
    my_tenant_id, my_key,
    key_01, key_02, key_03, key_04, key_05, key_06
) VALUES (
    7130316995039369369, 'user_200d590e', 'USR202608310046', 'user_200d590e@qq.com', 'MTM4NzkyMDQ0NjiOvOj5pwEv6rcgy2/bbP2JpZGsQvivGBcy6wg/', '4b5cb133d57f147af7777f75065b43b8',
    'EMP265657', '雅婷', '郑', '郑雅婷', '$2a$10$0e5e3879d36b4e51acb50930b7c91904',
    'ACTIVE', NULL, NULL, 1,
    '["GUEST"]'::jsonb, '{"theme":"dark","lang":"ja-JP","notify":true}'::jsonb, 4, 1,
    '2025-11-16 04:25:34', '2025-12-27 04:25:34', 'batch_import', 'admin',
    2, NULL,
    NULL, NULL, NULL, NULL, NULL, NULL
);
INSERT INTO sys_user (
    tab_id, my_user_account, my_user_id, my_email, my_phone, my_phone_index,
    my_employee_id, my_first_name, my_last_name, my_full_name, password_hash,
    my_account_status, time_locked_until, time_password_expires_at, failed_attempts,
    my_roles, my_profiles, my_version, bool_deleted,
    time_created_at, time_updated_at, who_created_by, who_updated_by,
    my_tenant_id, my_key,
    key_01, key_02, key_03, key_04, key_05, key_06
) VALUES (
    7130317000248716732, '郑丽10', 'USR202608310047', '郑丽10@gmail.com', 'MTM4ODk2ODIzMTNz4wQErz3YQ/QkdcQtNDSgvJlGw0RJIwRU4bNt', '9fd5765c9d43d8b3ed8ee4b46f4909f7',
    'EMP258727', '丽', '郑', '郑丽', '$2a$10$047be39fb35042a68cff9ecef58e6c7a',
    'ACTIVE', NULL, NULL, 2,
    '["USER","MANAGER"]'::jsonb, '{"theme":"light","lang":"en-US","notify":false}'::jsonb, 3, 1,
    '2024-04-18 18:02:02', '2024-06-05 18:02:02', 'system', 'admin',
    4, NULL,
    NULL, 'EXT260822', NULL, NULL, NULL, NULL
);
INSERT INTO sys_user (
    tab_id, my_user_account, my_user_id, my_email, my_phone, my_phone_index,
    my_employee_id, my_first_name, my_last_name, my_full_name, password_hash,
    my_account_status, time_locked_until, time_password_expires_at, failed_attempts,
    my_roles, my_profiles, my_version, bool_deleted,
    time_created_at, time_updated_at, who_created_by, who_updated_by,
    my_tenant_id, my_key,
    key_01, key_02, key_03, key_04, key_05, key_06
) VALUES (
    7130317003935523035, 'user_833972a3', 'USR202608310048', 'user_833972a3@sohu.com', 'MTM4NDk2NjY5ODIbev3FOXv/JAa4okPG17tY8SUIIgeGbhQey5LU', '18fa73ac58980c25033f7925224fd658',
    'EMP521818', '刚', '冯', '冯刚', '$2a$10$de251f5187dd4ab993153569e751b9ce',
    'ACTIVE', NULL, NULL, 2,
    '["MANAGER"]'::jsonb, '{"lang":"zh-CN","dashboard":"classic"}'::jsonb, 2, 1,
    '2024-08-28 17:04:59', '2024-09-02 17:04:59', 'admin', 'admin',
    1, NULL,
    NULL, NULL, 'EXT715138', NULL, NULL, NULL
);
INSERT INTO sys_user (
    tab_id, my_user_account, my_user_id, my_email, my_phone, my_phone_index,
    my_employee_id, my_first_name, my_last_name, my_full_name, password_hash,
    my_account_status, time_locked_until, time_password_expires_at, failed_attempts,
    my_roles, my_profiles, my_version, bool_deleted,
    time_created_at, time_updated_at, who_created_by, who_updated_by,
    my_tenant_id, my_key,
    key_01, key_02, key_03, key_04, key_05, key_06
) VALUES (
    7130317009614598618, '赵丽24', 'USR202608310049', '赵丽24@foxmail.com', 'MTM4NTE1MTIyMDlZPQZGBVP+sYRVvkCJPA+r24sghif+6bgc/1W8', '45ac6f5f38be68936f6376b0e65b7e76',
    'EMP910894', '丽', '赵', '赵丽', '$2a$10$9431944f9029471dbb0d6a4b24cb94b5',
    'ACTIVE', NULL, NULL, 2,
    '["ADMIN"]'::jsonb, '{"theme":"dark","lang":"ja-JP","notify":true}'::jsonb, 0, 1,
    '2024-02-04 04:55:33', '2024-03-29 04:55:33', 'system', 'admin',
    1, NULL,
    NULL, NULL, 'EXT900138', NULL, 'EXT749705', 'EXT440740'
);
INSERT INTO sys_user (
    tab_id, my_user_account, my_user_id, my_email, my_phone, my_phone_index,
    my_employee_id, my_first_name, my_last_name, my_full_name, password_hash,
    my_account_status, time_locked_until, time_password_expires_at, failed_attempts,
    my_roles, my_profiles, my_version, bool_deleted,
    time_created_at, time_updated_at, who_created_by, who_updated_by,
    my_tenant_id, my_key,
    key_01, key_02, key_03, key_04, key_05, key_06
) VALUES (
    7130317010675779569, 'user_9692ff3b', 'USR202608310050', 'user_9692ff3b@qq.com', 'MTM4NzczODI3OTQaW3IXzSPukJ+nLOkEvGaVm3ztvPxkfU0D0Qx3', '159e2597fe508b6d5ffe9b7ccacb388f',
    'EMP766833', '桂英', '朱', '朱桂英', '$2a$10$25ce52f83a864aefa7f13c978cb242b6',
    'ACTIVE', NULL, NULL, 1,
    '["GUEST"]'::jsonb, '{"lang":"zh-CN","dashboard":"classic"}'::jsonb, 3, 1,
    '2024-06-08 09:06:01', '2024-07-11 09:06:01', 'admin', 'hr_system',
    5, '7130316843947950300',
    'EXT98545', NULL, NULL, 'EXT434121', 'EXT391586', NULL
);

-- ============================================
-- 数据统计
-- ============================================
-- SELECT my_account_status, COUNT(*) FROM sys_user GROUP BY my_account_status;
-- SELECT bool_deleted, COUNT(*) FROM sys_user GROUP BY bool_deleted;
-- SELECT my_tenant_id, COUNT(*) FROM sys_user GROUP BY my_tenant_id;