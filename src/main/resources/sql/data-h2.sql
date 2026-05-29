-- =============================================
-- 剧本杀管理系统 H2 初始数据
-- =============================================

-- 管理员: admin / admin123 (BCrypt 加密)
INSERT INTO t_user (username, password, real_name, role, status) VALUES
('admin', '$2a$10$/6Gc8rocWYEGAVWI90RXc.3YELH.FUamW2h2vfL4tVQrODgw03KLK', '系统管理员', 'ADMIN', 'ACTIVE');

-- DM主持人
INSERT INTO t_user (username, password, real_name, phone, role, status, commission_rate, introduction) VALUES
('dm_xiaoming', '$2a$10$/6Gc8rocWYEGAVWI90RXc.3YELH.FUamW2h2vfL4tVQrODgw03KLK', '小明', '13800000001', 'DM', 'ACTIVE', 30, '资深DM，擅长硬核推理本'),
('dm_xiaohong', '$2a$10$/6Gc8rocWYEGAVWI90RXc.3YELH.FUamW2h2vfL4tVQrODgw03KLK', '小红', '13800000002', 'DM', 'ACTIVE', 25, '擅长情感沉浸本');

-- 玩家
INSERT INTO t_user (username, password, real_name, phone, role, status) VALUES
('player1', '$2a$10$/6Gc8rocWYEGAVWI90RXc.3YELH.FUamW2h2vfL4tVQrODgw03KLK', '玩家1', '13900000001', 'PLAYER', 'ACTIVE'),
('player2', '$2a$10$/6Gc8rocWYEGAVWI90RXc.3YELH.FUamW2h2vfL4tVQrODgw03KLK', '玩家2', '13900000002', 'PLAYER', 'ACTIVE');

-- 剧本数据
INSERT INTO t_script (name, cover_image, category, difficulty, player_count, duration, description, characters, price, member_price, status, play_count, rating, sort_order) VALUES
('豪门惊情', '/images/script1.jpg', '硬核推理', 'HARD', 6, 240, '民国豪门家族恩怨，一桩离奇命案揭开家族隐秘...', '[{"name":"大少爷","desc":"家族长子"},{"name":"二小姐","desc":"留洋归来的千金"}]', 128.00, 108.00, 'ONLINE', 156, 4.8, 100),
('长安十二时', '/images/script2.jpg', '古风', 'ADVANCED', 7, 210, '大唐长安城内发生惊天大案，十二时辰内侦破谜案...', '[{"name":"李小白","desc":"大理寺少卿"}]', 138.00, 118.00, 'ONLINE', 89, 4.6, 90),
('校园怪谈', '/images/script3.jpg', '恐怖', 'NOVICE', 5, 180, '深夜的校园里总传来诡异的脚步声...', '[{"name":"学生会长","desc":"校园风云人物"}]', 98.00, 78.00, 'ONLINE', 45, 4.3, 80),
('分手合约', '/images/script4.jpg', '情感沉浸', 'NOVICE', 4, 150, '一场关于爱情的剧本，感动千万玩家...', '[{"name":"男主角","desc":"深情的恋人"}]', 88.00, 68.00, 'ONLINE', 120, 4.9, 70),
('东方快车', '/images/script5.jpg', '硬核推理', 'HARD', 8, 300, '著名侦探小说改编，经典重现...', '[{"name":"侦探","desc":"私家侦探"}]', 158.00, 138.00, 'ONLINE', 67, 4.7, 60),
('血月之夜', '/images/script6.jpg', '机制阵营', 'ADVANCED', 6, 220, '狼人杀+剧本杀结合，全新体验...', '[{"name":"预言家","desc":"知晓真相之人"}]', 118.00, 98.00, 'DRAFT', 0, 0, 50);

-- 房间数据
INSERT INTO t_room (name, style, capacity, description, status) VALUES
('恐怖密室', 'HORROR', 8, '全黑环境，专业音效灯光', 'IDLE'),
('古风雅阁', 'ANCIENT', 8, '古色古香，屏风字画', 'IDLE'),
('现代简约', 'MODERN', 6, '明亮舒适，现代风格', 'IDLE'),
('沉浸剧场', 'IMMERSIVE', 10, '360度环绕投影', 'IDLE');

-- 场次时段
INSERT INTO t_session (name, start_time, end_time, sort_order, enabled) VALUES
('下午场', '14:00', '18:00', 1, 1),
('晚场', '19:00', '23:00', 2, 1),
('通宵场', '23:30', '04:00', 3, 1);

-- 剧本场次排期（今天和明天的排期）
INSERT INTO t_script_session (script_id, room_id, session_id, dm_user_id, schedule_date, max_players, current_players, status) VALUES
(1, 1, 1, 2, CURRENT_DATE(), 6, 3, 'OPEN'),
(1, 1, 2, 2, CURRENT_DATE(), 6, 0, 'OPEN'),
(2, 2, 1, 3, CURRENT_DATE(), 7, 0, 'OPEN'),
(2, 2, 2, 3, CURRENT_DATE(), 7, 4, 'OPEN'),
(4, 3, 1, 2, CURRENT_DATE(), 4, 0, 'OPEN'),
(1, 1, 1, 2, DATEADD('DAY', 1, CURRENT_DATE()), 6, 2, 'OPEN'),
(2, 2, 2, 3, DATEADD('DAY', 1, CURRENT_DATE()), 7, 0, 'OPEN');

-- 会员数据
INSERT INTO t_member (user_id, level, points, total_spent, total_plays) VALUES
(4, 'GOLD', 1500, 3000.00, 12),
(5, 'SILVER', 500, 800.00, 4);

-- 系统配置
INSERT INTO t_system_config (config_key, config_value, description, config_group) VALUES
('business_hours', '10:00-02:00', '营业时间', 'BUSINESS'),
('refund_hours_before', '2', '开场前N小时可免费取消', 'REFUND'),
('deposit_rate', '0.3', '定金比例', 'BUSINESS'),
('home_notice', '欢迎光临XX剧本杀门店！新本《东方快车》已上线，快来体验吧！', '首页公告', 'NOTICE');
