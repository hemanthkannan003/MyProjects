
DROP TABLE IF EXISTS staging_db.applicant_stg;
CREATE TABLE staging_db.applicant_stg (
  `applicant_client_id` VARCHAR(7) NOT NULL,
  `applicant_last_name` VARCHAR(32) DEFAULT NULL,
  `applicant_first_name` VARCHAR(32) DEFAULT NULL,
  `applicant_middle_name` VARCHAR(32) DEFAULT NULL,
  `current_state_province` VARCHAR(32) DEFAULT NULL,
  `current_country` VARCHAR(128) DEFAULT NULL,
  `province` VARCHAR(32) DEFAULT NULL,
  `country_of_citizenship` VARCHAR(32) DEFAULT NULL,
  `gender` VARCHAR(8) DEFAULT NULL,
  `admission_decision` VARCHAR(12) DEFAULT NULL,
  PRIMARY KEY (`applicant_client_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS staging_db.program_stg;
CREATE TABLE staging_db.program_stg (
  `program_code` VARCHAR(128) NOT NULL DEFAULT '',
  `department_code` CHAR(7) NOT NULL DEFAULT '',
  `department_name` VARCHAR(50) NOT NULL DEFAULT '',
  PRIMARY KEY (`program_code`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;


