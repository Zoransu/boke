-- 创建数据库
CREATE DATABASE IF NOT EXISTS boke;
USE boke;

-- 创建用户表
CREATE TABLE user(
                     user_id BIGINT NOT NULL AUTO_INCREMENT, -- 用户ID，自增主键
                     user_name VARCHAR(20), -- 用户名
                     user_password VARCHAR(30), -- 用户密码
                     user_email VARCHAR(255), -- 用户邮箱
                     user_profile_photo VARCHAR(255) DEFAULT 'http://localhost:8080/uploads/0.jpg', -- 用户头像图片的URL，默认值
                     registration_date DATETIME, -- 用户注册时间
                     user_telephone_number VARCHAR(11), -- 用户电话号码
                     user_nickname VARCHAR(20), -- 用户昵称
                     PRIMARY KEY (user_id) -- 主键为用户ID
);

-- 创建文章表
CREATE TABLE articles (
                          article_id BIGINT NOT NULL AUTO_INCREMENT, -- 文章ID，自增主键
                          user_id BIGINT NOT NULL, -- 发布文章的用户ID
                          article_title VARCHAR(255), -- 文章标题
                          article_content LONGTEXT, -- 文章内容
                          article_comment_count BIGINT, -- 文章的评论数量
                          article_date DATETIME, -- 文章发布时间
                          article_like_count BIGINT, -- 文章的点赞数量
                          PRIMARY KEY (article_id), -- 主键为文章ID
                          FOREIGN KEY (user_id) REFERENCES user(user_id) -- 外键约束，关联用户表
);

-- 创建评论表
CREATE TABLE comments (
                          comment_id BIGINT NOT NULL AUTO_INCREMENT, -- 评论ID，自增主键
                          article_id BIGINT NOT NULL, -- 评论所属的文章ID
                          user_id BIGINT NOT NULL, -- 发表评论的用户ID
                          comment_date DATETIME, -- 评论发表时间
                          comment_content TEXT, -- 评论内容
                          parent_comment_id BIGINT, -- 父评论ID（用于回复功能）
                          PRIMARY KEY (comment_id), -- 主键为评论ID
                          FOREIGN KEY (article_id) REFERENCES articles(article_id), -- 外键约束，关联文章表
                          FOREIGN KEY (user_id) REFERENCES user(user_id), -- 外键约束，关联用户表
                          FOREIGN KEY (parent_comment_id) REFERENCES comments(comment_id) -- 外键约束，关联本表实现评论嵌套
);

-- 创建标签表
CREATE TABLE labels (
                        label_id BIGINT NOT NULL AUTO_INCREMENT, -- 标签ID，自增主键
                        label_name VARCHAR(20), -- 标签名称
                        label_alias VARCHAR(15), -- 标签别名
                        label_description TEXT, -- 标签描述
                        PRIMARY KEY (label_id) -- 主键为标签ID
);

-- 文章标签关联表
CREATE TABLE set_article_label (
                                   article_id BIGINT NOT NULL, -- 文章ID
                                   label_id BIGINT NOT NULL, -- 标签ID
                                   FOREIGN KEY (article_id) REFERENCES articles(article_id), -- 外键约束，关联文章表
                                   FOREIGN KEY (label_id) REFERENCES labels(label_id), -- 外键约束，关联标签表
                                   PRIMARY KEY (article_id, label_id) -- 主键为文章ID和标签ID的组合
);

-- 用户朋友表
CREATE TABLE user_friends (
                              id BIGINT NOT NULL AUTO_INCREMENT, -- 记录ID，自增主键
                              user_id BIGINT NOT NULL, -- 用户ID
                              user_friend_id BIGINT NOT NULL, -- 好友的用户ID
                              user_note VARCHAR(20), -- 用户给好友的备注
                              user_status VARCHAR(20), -- 好友关系的状态
                              PRIMARY KEY (id), -- 主键为记录ID
                              FOREIGN KEY (user_id) REFERENCES user(user_id), -- 外键约束，关联用户表
                              FOREIGN KEY (user_friend_id) REFERENCES user(user_id) -- 外键约束，关联用户表表示好友
);

-- 创建触发器，在插入评论时同步数据并更新文章的评论数量
DELIMITER $$
CREATE TRIGGER before_comment_insert
    BEFORE INSERT ON comments
    FOR EACH ROW
BEGIN
    -- 同步特定文章的评论数量
    UPDATE articles a
        JOIN (
            SELECT article_id, COUNT(*) AS comment_count
            FROM comments
            WHERE article_id = NEW.article_id
            GROUP BY article_id
        ) c ON a.article_id = c.article_id
    SET a.article_comment_count = c.comment_count
    WHERE a.article_id = NEW.article_id;
    -- 更新评论数量
    UPDATE articles
    SET article_comment_count = article_comment_count + 1
    WHERE article_id = NEW.article_id;
END$$
DELIMITER ;


DELIMITER //
-- 创建触发器，在删除文章时删除相关的评论和文章标签关联
CREATE TRIGGER before_article_delete
    BEFORE DELETE ON articles
    FOR EACH ROW
BEGIN
    -- 删除相关的评论
    DELETE FROM comments WHERE article_id = OLD.article_id;
    -- 删除相关的文章标签关联
    DELETE FROM set_article_label WHERE article_id = OLD.article_id;
END//
DELIMITER ;



DELIMITER //
CREATE PROCEDURE DeleteComment(
    IN comment_id BIGINT,
    IN user_id BIGINT
)
BEGIN
    DECLARE article_owner_id BIGINT;
    DECLARE comment_owner_id BIGINT;
    -- 获取评论的所有者ID
    SELECT user_id INTO comment_owner_id
    FROM comments
    WHERE comment_id = comment_id;
    -- 获取文章的所有者ID
    SELECT user_id INTO article_owner_id
    FROM articles
    WHERE article_id = (SELECT article_id FROM comments WHERE comment_id = comment_id);
    -- 检查请求删除评论的用户是否为评论所有者或文章所有者
    IF comment_owner_id = user_id OR article_owner_id = user_id THEN
        -- 删除评论
        DELETE FROM comments WHERE comment_id = comment_id;
    ELSE
        -- 如果用户无权删除该评论，则抛出错误
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'User not authorized to delete this comment';
    END IF;
END //
DELIMITER ;


#插入标签
DELIMITER //
create
    definer = root@localhost procedure add_article_label(IN p_article_id bigint, IN p_label_name varchar(20))
BEGIN
    DECLARE p_label_id BIGINT;
    -- 获取标签ID
    SELECT label_id
    INTO p_label_id
    FROM labels
    WHERE label_name = p_label_name;
    -- 插入数据到set_article_label表
    INSERT INTO set_article_label (article_id, label_id)
    VALUES (p_article_id, p_label_id);
END;
DELIMITER ;