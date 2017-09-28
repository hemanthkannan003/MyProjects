
DROP TABLE IF EXISTS datamart_db.dim_applicant;
CREATE TABLE datamart_db.dim_applicant (
  `applicant_skey` INT(20) NOT NULL DEFAULT '0' COMMENT 'Surrogate Key',
  `applicant_client_id` VARCHAR(7) NOT NULL DEFAULT '0',
  `applicant_last_name` VARCHAR(32) DEFAULT NULL,
  `applicant_first_name` VARCHAR(32) DEFAULT NULL,
  `applicant_middle_name` VARCHAR(32) DEFAULT NULL,
  `current_state_province` VARCHAR(32) DEFAULT NULL,
  `current_country` VARCHAR(128) DEFAULT NULL,
  `province` VARCHAR(32) DEFAULT NULL,
  `country_of_citizenship` VARCHAR(32) DEFAULT NULL,
  `gender` VARCHAR(8) DEFAULT NULL,
  `admission_decision` VARCHAR(12) DEFAULT NULL,
  `version` BIGINT(20) DEFAULT NULL,
  `date_from` CHAR(20) DEFAULT NULL,
  `date_to` CHAR(20) DEFAULT NULL,
  PRIMARY KEY (`applicant_skey`),
  UNIQUE KEY `applicant_client_id` (`applicant_client_id`,`date_from`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;



DROP TABLE IF EXISTS datamart_db.dim_program;
CREATE TABLE datamart_db.dim_program (
  `program_skey` INT(20) NOT NULL DEFAULT '0' COMMENT 'Surrogate Key',
  `program_code` VARCHAR(128) NOT NULL DEFAULT '',
  `department_code` CHAR(7) NOT NULL DEFAULT '',
  `department_name` VARCHAR(50) NOT NULL DEFAULT '',
  `version` BIGINT(20) DEFAULT NULL,
  `date_from` CHAR(20) DEFAULT NULL,
  `date_to` CHAR(20) DEFAULT NULL,
  PRIMARY KEY (`program_skey`),
  UNIQUE KEY program_code(program_code,date_from)
) ENGINE=INNODB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS datamart_db.dim_date;
CREATE TABLE datamart_db.dim_date (
  `date_skey` INT(11) NOT NULL DEFAULT '0',
  `the_date` CHAR(20) DEFAULT NULL,
  `the_year` SMALLINT(6) DEFAULT NULL,
  `the_quarter` TINYINT(4) DEFAULT NULL,
  `the_month` TINYINT(4) DEFAULT NULL,
  `the_week` TINYINT(4) DEFAULT NULL,
  `day_of_year` SMALLINT(6) DEFAULT NULL,
  `day_of_month` TINYINT(4) DEFAULT NULL,
  `day_of_week` TINYINT(4) DEFAULT NULL,
  PRIMARY KEY (`date_skey`)
) ENGINE=MYISAM DEFAULT CHARSET=utf8;



DROP TABLE IF EXISTS datamart_db.fact_admission;
CREATE TABLE datamart_db.fact_admission(
  `submitted_skey` INT(11) NOT NULL DEFAULT '0',
  `printed_skey`  INT(11) NOT NULL DEFAULT '0',
  `program_skey` INT(20) NOT NULL DEFAULT '0' COMMENT 'Surrogate Key',
  `ethnicity_skey` INT(20) DEFAULT '0' COMMENT 'Surrogate Key',
  `applicant_skey` INT(20) NOT NULL DEFAULT '0' COMMENT 'Surrogate Key',
  `IS_APPLIED` VARCHAR(5) NOT NULL DEFAULT '0',
  `IS_ADMITTED` VARCHAR(5) NOT NULL DEFAULT '0',
  `IS_ACCEPTED` VARCHAR(5) NOT NULL DEFAULT '0',
  `NUM_EMAIL_BY_APP` INT(3) COMMENT 'APP:Sent by Applicant, APP: Sent by Applicant',
  `NUM_EMAIL_BY_SCH` INT(3) COMMENT 'SCH:Sent by Applicant, SCH: Sent by School',
  `admission_decision` VARCHAR(12) DEFAULT NULL
) ENGINE=MYISAM DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS datamart_db.dim_ethnicity;
CREATE TABLE datamart_db.dim_ethnicity
  (
    ethnicity_skey     INT(50) NOT NULL AUTO_INCREMENT,
    junk_id           VARCHAR(50),
    ethnicity_indian   VARCHAR(50) NOT NULL DEFAULT ' ',
    ethnicity_asian    VARCHAR(50) NOT NULL DEFAULT ' ',
    ethnicity_black    VARCHAR(50) NOT NULL DEFAULT ' ',
    ethnicity_hawaiian VARCHAR(50) NOT NULL DEFAULT ' ',
    ethnicity_white    VARCHAR(50) NOT NULL DEFAULT ' ',
    ethnicity_hispanic VARCHAR(50) NOT NULL DEFAULT ' ',
    PRIMARY KEY (ethnicity_skey),
    UNIQUE KEY (junk_id)
  );
  
INSERT
INTO datamart_db.dim_ethnicity
  (
    junk_id,
    ethnicity_indian,
    ethnicity_asian,
    ethnicity_black,
    ethnicity_hawaiian,
    ethnicity_white,
    ethnicity_hispanic
  )
SELECT 
  SUBSTRING(MD5(CONCAT(TRIM(a.indian),TRIM(a.asian), TRIM(a.black),TRIM(a.hawaiian),TRIM(a.white),TRIM(a.hispanic))),1,5) group_key,
  a.indian,
  a.asian,
  a.black,
  a.hawaiian,
  a.white,
  a.hispanic
FROM
  (SELECT DISTINCT IFNULL(ethnicity_indian,' ')    AS indian,
    IFNULL(ethnicity_asian,' ')                    AS asian,
    IFNULL(ethnicity_black,' ')                    AS black,
    IFNULL(ethnicity_hawaiian, ' ')                AS hawaiian,
    IFNULL(ethnicity_white, ' ')                   AS white,
    IF(hispanic_ethnicity = 'No', ' ', 'Hispanic') AS hispanic
  FROM source_db.app_profile
  ) a;
  
  
  
