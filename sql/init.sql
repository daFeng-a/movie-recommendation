-- 创建数据库
CREATE DATABASE IF NOT EXISTS movie_recommend DEFAULT CHARSET utf8mb4;
USE movie_recommend;

-- 用户表
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY COMMENT '用户ID',
    username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名（唯一）',
    password VARCHAR(100) NOT NULL COMMENT '密码（加密存储）',
    email VARCHAR(100) NOT NULL COMMENT '邮箱',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 电影表（增加video_url字段，存储电影播放地址）
CREATE TABLE IF NOT EXISTS movies (
    id INT AUTO_INCREMENT PRIMARY KEY COMMENT '电影ID',
    title VARCHAR(200) NOT NULL COMMENT '电影名称',
    year INT COMMENT '上映年份',
    genres VARCHAR(100) COMMENT '电影类型（多个类型可用逗号分隔）',
    director VARCHAR(100) COMMENT '导演',
    actors VARCHAR(300) COMMENT '演员列表',
    plot TEXT COMMENT '剧情简介',
    poster_url VARCHAR(300) COMMENT '海报图片链接',
    video_url VARCHAR(300) COMMENT '电影播放路径'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='电影表';

-- 评分表
CREATE TABLE IF NOT EXISTS ratings (
    id INT AUTO_INCREMENT PRIMARY KEY COMMENT '评分ID',
    user_id INT NOT NULL COMMENT '关联的用户ID',
    movie_id INT NOT NULL COMMENT '关联的电影ID',
    rating DOUBLE NOT NULL CHECK (rating >= 0.5 AND rating <= 5.0) COMMENT '评分（0.5-5.0）',
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '评分时间',
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (movie_id) REFERENCES movies(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='评分表';

-- 推荐结果表
CREATE TABLE IF NOT EXISTS recommendations (
    id INT AUTO_INCREMENT PRIMARY KEY COMMENT '推荐ID',
    user_id INT NOT NULL COMMENT '关联的用户ID',
    movie_id INT NOT NULL COMMENT '关联的电影ID',
    predicted_rating DOUBLE NOT NULL COMMENT '预测评分',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '推荐生成时间',
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (movie_id) REFERENCES movies(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='推荐结果表';

-- 用户行为表（用于存储隐式反馈）
CREATE TABLE IF NOT EXISTS user_actions (
    id INT AUTO_INCREMENT PRIMARY KEY COMMENT '行为ID',
    user_id INT NOT NULL COMMENT '用户ID',
    movie_id INT NOT NULL COMMENT '电影ID',
    action_type VARCHAR(20) NOT NULL COMMENT '行为类型(view/watch/favorite/like)',
    action_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '行为时间',
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (movie_id) REFERENCES movies(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户行为表';

-- 复合索引提高评分表查询效率
CREATE INDEX idx_user_movie ON ratings(user_id, movie_id);


-- 初始化 movies 表数据示例（含视频路径）
INSERT INTO movies (title, year, genres, director, actors, plot, poster_url, video_url) VALUES
                                                                                            ('肖申克的救赎', 1994, '剧情,犯罪', '弗兰克·德拉邦特', '蒂姆·罗宾斯,摩根·弗里曼', '一个关于希望与自由的故事，银行家安迪因冤狱入狱，最终越狱重获自由。', 'https://img.example.com/shawshank.jpg', '/videos/shawshank.mp4'),
                                                                                            ('阿甘正传', 1994, '剧情,爱情', '罗伯特·泽米吉斯', '汤姆·汉克斯,罗宾·怀特', '智商略低的阿甘用自己的真诚和坚持，见证并影响了美国的历史大事件。', 'https://img.example.com/forrestgump.jpg', '/videos/forrestgump.mp4'),
                                                                                            ('霸王别姬', 1993, '剧情,爱情,历史', '陈凯歌', '张国荣,巩俐', '讲述两位京剧演员半个世纪的纠葛人生，展现中国社会变迁。', 'https://img.example.com/farewell.jpg', '/videos/farewell.mp4'),
                                                                                            ('盗梦空间', 2010, '科幻,悬疑,动作', '克里斯托弗·诺兰', '莱昂纳多·迪卡普里奥,艾伦·佩吉', '一支盗梦团队通过潜入他人梦境世界，完成一项看似不可能的任务。', 'https://img.example.com/inception.jpg', '/videos/inception.mp4'),
                                                                                            ('千与千寻', 2001, '动画,奇幻,冒险', '宫崎骏', '柊瑠美,入野自由', '少女千寻误入神灵世界，经历成长与冒险，最终救出父母。', 'https://img.example.com/spiritedaway.jpg', '/videos/spiritedaway.mp4'),
                                                                                            ('星际穿越', 2014, '科幻,冒险,剧情', '克里斯托弗·诺兰', '马修·麦康纳,安妮·海瑟薇', '人类面临灭绝危机，一组宇航员穿越虫洞寻找新家园。', 'https://img.example.com/interstellar.jpg', '/videos/interstellar.mp4'),
                                                                                            ('美丽人生', 1997, '剧情,爱情,战争', '罗伯托·贝尼尼', '罗伯托·贝尼尼,尼可莱塔·布拉斯基', '犹太父亲用幽默和爱保护儿子躲过纳粹集中营的苦难。', 'https://img.example.com/lifeisbeautiful.jpg', '/videos/lifeisbeautiful.mp4'),
                                                                                            ('当幸福来敲门', 2006, '剧情,传记', '加布里尔·穆奇诺', '威尔·史密斯,贾登·史密斯', '一位父亲在事业和生活困境中坚持不懈，最终实现人生逆转。', 'https://img.example.com/pursuit.jpg', '/videos/pursuit.mp4'),
                                                                                            ('海上钢琴师', 1998, '剧情,音乐', '朱塞佩·托纳多雷', '蒂姆·罗斯,普路特·泰勒·文斯', '孤儿1900在轮船上成长为传奇钢琴家，终生未曾离开大海。', 'https://img.example.com/pianist.jpg', '/videos/pianist.mp4'),
                                                                                            ('机器人总动员', 2008, '动画,科幻,冒险', '安德鲁·斯坦顿', '本·贝尔特,艾丽莎·奈特', '清扫机器人瓦力在废墟地球上遇见伊芙，展开奇妙宇宙之旅。', 'https://img.example.com/walle.jpg', '/videos/walle.mp4');