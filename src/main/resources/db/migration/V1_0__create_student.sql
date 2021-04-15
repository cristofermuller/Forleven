CREATE TABLE IF NOT EXISTS `student` (

    `id` bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name` varchar(255) NOT NULL,
    `surname` varchar(255) NOT NULL,
    `enrollment` varchar(255) NOT NULL UNIQUE
    );

CREATE TABLE IF NOT EXISTS `phones` (

        `id` bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
        `student_id` bigint NOT NULL,
        `phone` varchar(255) NOT NULL,
        FOREIGN KEY (student_id) REFERENCES student (id)
    );