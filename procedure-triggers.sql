USE `erecruit`;
DROP procedure IF EXISTS `FIND_CANDIDATES`;

DELIMITER $$
USE `erecruit`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `FIND_CANDIDATES`(IN id INT(4))
BEGIN
    DECLARE username VARCHAR(12);
    DECLARE candidate_interviewed int(4) default 0;
    DECLARE pers INT(4);
    DECLARE educ INT(4);
    DECLARE workexp INT(4);
    DECLARE reason VARCHAR(100) DEFAULT "";
    DECLARE inadequate_candidate INT DEFAULT FALSE;
    DECLARE done INT;
    DECLARE done_2 INT;

    DECLARE candidate_cursor CURSOR FOR
    SELECT cand_usrname FROM applies WHERE job_id = id;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;

    OPEN candidate_cursor;
	set done = 0;

    create table temp1 (
    candid varchar(12),
    reas varchar(100)
    );

    create table temp2 (
    candid varchar(12),
    per int(4),
    edu int(4),
    workex int(4),
    total int(4),
    n_ints int(4));

	FETCH candidate_cursor INTO username;
    while( done = 0) do
    SET inadequate_candidate = 0;
    SET reason = "";
    set candidate_interviewed = 0;
    set pers = NULL;
    set educ = NULL;
    set workexp = null;

    begin
    declare continue handler for not found set done_2=1;
	SELECT count(interview.interview_id) INTO candidate_interviewed
    FROM interview
    WHERE interview.candidate = username and interview.job_id=id
    group by interview.candidate;
	set done = 0;
	end;
    IF candidate_interviewed = 0 THEN
	SET reason = CONCAT(reason, "Not interviewed yet");
	SET inadequate_candidate = 1;
	END IF;
if inadequate_candidate = 0 then
SELECT AVG(interview.persscore), avg(interview.edscore), avg(interview.workexpscore)
INTO pers , educ , workexp
FROM interview
WHERE job_id = id AND interview.candidate = username;
end if;


    IF pers = 0 THEN
    SET reason = CONCAT_WS(' ', reason, "Failed the interview" );
    SET inadequate_candidate = 1;
    END IF;

    IF educ = 0 THEN
    SET reason = CONCAT_WS(' ', reason, "Inadequate education" );
    SET inadequate_candidate = 1;
    END IF;

    IF workexp = 0 THEN
    SET reason = CONCAT_WS(' ', reason, "No prior experience" );
    SET inadequate_candidate = 1;
    END IF;

    IF inadequate_candidate = 1 THEN
    Insert into temp1 (candid, reas)
    SELECT username, reason;

    ELSE
    insert into temp2 (candid, per, edu, workex, total, n_ints)
    SELECT interview.candidate, AVG(interview.persscore), Avg(interview.edscore), avg(interview.workexpscore),
    (AVG(interview.persscore) + avg(interview.edscore) + avg(interview.workexpscore)), count(interview.interview_id)
    FROM interview
    where interview.candidate = username and interview.job_id = id
	group by interview.candidate;

    END IF;

	fetch candidate_cursor into username;
    END while;

    CLOSE candidate_cursor;

    select * from temp1;
    select * from temp2;

     drop table temp1, temp2;
END$$

DELIMITER ;



select `user`.`name` AS `NAME`, `user`.`surname` AS SURNAME, etaireia.`name` AS FIRM, job.id AS JOB_ID, job.salary AS SALARY, count(applies.cand_usrname) AS N_CANDIDATES
from job
INNER JOIN recruiter on recruiter.username = job.recruiter
INNER JOIN `user` on `user`.username = recruiter.username
INNER JOIN etaireia on etaireia.AFM = recruiter.firm
INNER JOIN applies on applies.job_id = job.id
where salary>1900
group by job.id;


select candidate.username AS USERNAME, candidate.certificates AS CERTIFICATES, count(has_degree.cand_usrname) AS N_DEGREES, avg(has_degree.grade) AS AVERAGE
from candidate
INNER JOIN has_degree on has_degree.cand_usrname = candidate.username
group by has_degree.cand_usrname having N_DEGREES >1;


select applies.cand_usrname AS USERNAME, count(applies.cand_usrname) AS N_APPLIES, avg(job.salary) AS AVERAGE_SAL
from applies
INNER JOIN job ON applies.job_id = job.id
group by applies.cand_usrname HAVING AVERAGE_SAL > 1800;



select etaireia.`name` AS ETAIRIA, antikeim.descr AS `DESCRIPTION`, antikeim.title AS TITLE
from job
INNER JOIN recruiter on recruiter.username = job.recruiter
INNER JOIN etaireia on etaireia.AFM = recruiter.firm
INNER JOIN requires on requires.job_id = job.id
INNER JOIN antikeim on antikeim.title = requires.antikeim_title
WHERE etaireia.city = 'Patra' AND requires.antikeim_title LIKE '%Program%';



select interview.recruiter AS USERNAME, count(distinct job.id) AS N_JOBS,
 count(distinct interview.interview_id) AS N_INTERVIEWS, avg(job.salary) AS AVG_SALARY
from job
INNER JOIN recruiter on recruiter.username = job.recruiter
INNER JOIN interview on interview.recruiter = recruiter.username
group by job.recruiter having N_JOBS > 2
order by AVG_SALARY desc;


#SET SQL_SAFE_UPDATES = 0;
SET @inserttime=0;
SET @Rusrnamr="";


drop trigger if exists candidate_insert_before ;
DELIMITER //
CREATE TRIGGER candidate_insert_before BEFORE INSERT ON candidate
FOR EACH ROW
BEGIN
SET @insertTime=NOW();
insert into history (history_id, username, h_date, success, `type`, `table_name`)
values( NULL, new.username, NOW(), 0, "Insert", 'candidate');
END //
DELIMITER ;

drop trigger if exists candidate_insert_after ;
DELIMITER //
CREATE TRIGGER candidate_insert_after AFTER INSERT ON candidate
FOR EACH ROW
BEGIN
UPDATE history set success =  1 WHERE h_date =@insertTime;
END //
DELIMITER ;

drop trigger if exists candidate_update_before ;
DELIMITER //
CREATE TRIGGER candidate_update_before BEFORE UPDATE ON candidate
FOR EACH ROW
BEGIN
SET @insertTime=NOW();
INSERT INTO history (history_id, username, h_date, success, `type`, `table_name`)
values( NULL, old.username, NOW(), 0, "Update", 'candidate');
END //
DELIMITER ;

drop trigger if exists candidate_update_after ;
DELIMITER //
CREATE TRIGGER candidate_update_after AFTER UPDATE ON candidate
FOR EACH ROW
BEGIN
UPDATE history set success =  1 WHERE h_date =@insertTime;
END //
DELIMITER ;

drop trigger if exists candidate_delete_before ;
DELIMITER //
CREATE TRIGGER candidate_delete_before BEFORE DELETE ON candidate
FOR EACH ROW
BEGIN
SET @insertTime=NOW();
insert into history (history_id, username, h_date, success, `type`, `table_name`)
values( NULL, old.username, NOW(), 0, "Delete", 'candidate');
END //
DELIMITER ;

drop trigger if exists candidate_delete_after ;
DELIMITER //
CREATE TRIGGER candidate_delete_after AFTER DELETE ON candidate
FOR EACH ROW
BEGIN
UPDATE history set success =  1 WHERE h_date =@insertTime;
END //
DELIMITER ;

drop trigger if exists recruiter_insert_before;
DELIMITER //
CREATE TRIGGER recruiter_insert_before BEFORE INSERT ON recruiter
FOR EACH ROW
BEGIN
SET @insertTime=NOW();
insert into history (history_id, username, h_date, success, `type`, `table_name`)
values( NULL, new.username, NOW(), 0, "Insert", 'recruiter');
END //
DELIMITER ;

drop trigger if exists recruiter_insert_after ;
DELIMITER //
CREATE TRIGGER recruiter_insert_after AFTER INSERT ON recruiter
FOR EACH ROW
BEGIN
UPDATE history set success =  1 WHERE h_date =@insertTime;
END //
DELIMITER ;

drop trigger if exists recruiter_update_before;
DELIMITER //
CREATE TRIGGER recruiter_update_before BEFORE UPDATE ON recruiter
FOR EACH ROW
BEGIN
SET @insertTime=NOW();
INSERT INTO history (history_id, username, h_date, success, `type`, `table_name`)
values( NULL, old.username, NOW(), 0, "Update", 'recruiter');
END //
DELIMITER ;

drop trigger if exists recruiter_update_after ;
DELIMITER //
CREATE TRIGGER recruiter_update_after AFTER UPDATE ON recruiter
FOR EACH ROW
BEGIN
UPDATE history set success =  1 WHERE h_date =@insertTime;
END //
DELIMITER ;

drop trigger if exists recruiter_delete_before;
DELIMITER //
CREATE TRIGGER recruiter_delete_before BEFORE DELETE ON recruiter
FOR EACH ROW
BEGIN
SET @insertTime=NOW();
insert into history (history_id, username, h_date, success, `type`, `table_name`)
values( NULL, old.username, NOW(), 0, "Delete", 'recruiter');
END //
DELIMITER ;

drop trigger if exists recruiter_delete_after ;
DELIMITER //
CREATE TRIGGER recruiter_delete_after AFTER DELETE ON recruiter
FOR EACH ROW
BEGIN
UPDATE history set success =  1 WHERE h_date =@insertTime;
END //
DELIMITER ;

drop trigger if exists user_insert_before;
DELIMITER //
CREATE TRIGGER user_insert_before BEFORE INSERT ON user
FOR EACH ROW
BEGIN
SET @insertTime=NOW();
insert into history (history_id, username, h_date, success, `type`, `table_name`)
values( NULL, new.username, NOW(), 0, "Insert", 'user');
END //
DELIMITER ;

drop trigger if exists user_insert_after ;
DELIMITER //
CREATE TRIGGER user_insert_after AFTER INSERT ON user
FOR EACH ROW
BEGIN
UPDATE history set success =  1 WHERE h_date =@insertTime;
END //
DELIMITER ;

drop trigger if exists user_update_before;
DELIMITER //
CREATE TRIGGER user_update_before BEFORE UPDATE ON `user`
FOR EACH ROW
BEGIN
SET @insertTime=NOW();
INSERT INTO history (history_id, username, h_date, success, `type`, `table_name`)
values( NULL, old.username, NOW(), 0, "Update", 'user');
END //
DELIMITER ;

drop trigger if exists user_update_after ;
DELIMITER //
CREATE TRIGGER user_update_after AFTER UPDATE ON user
FOR EACH ROW
BEGIN
UPDATE history set success =  1 WHERE h_date =@insertTime;
END //
DELIMITER ;

drop trigger if exists user_delete_before;
DELIMITER //
CREATE TRIGGER user_delete_before BEFORE DELETE ON `user`
FOR EACH ROW
BEGIN
SET @insertTime=NOW();
insert into history (history_id, username, h_date, success, `type`, `table_name`)
values( NULL, old.username, NOW(), 0, "Delete", 'user');
END //
DELIMITER ;

drop trigger if exists user_delete_after ;
DELIMITER //
CREATE TRIGGER user_delete_after AFTER DELETE ON user
FOR EACH ROW
BEGIN
UPDATE history set success =  1 WHERE h_date =@insertTime;
END //
DELIMITER ;

drop trigger if exists job_insert_before ;
DELIMITER //
CREATE TRIGGER job_insert_before BEFORE INSERT ON `job`
FOR EACH ROW
BEGIN
SET @insertTime=NOW();
insert into history (history_id, username, h_date, success, `type`, `table_name`)
values( NULL, new.recruiter, NOW(), 0, "Insert", 'job');
END //
DELIMITER ;

drop trigger if exists job_insert_after ;
DELIMITER //
CREATE TRIGGER job_insert_after AFTER INSERT ON job
FOR EACH ROW
BEGIN
UPDATE history set success =  1 WHERE h_date =@insertTime;
END //
DELIMITER ;

drop trigger if exists job_update_before ;
DELIMITER //
CREATE TRIGGER job_update_before BEFORE UPDATE ON `job`
FOR EACH ROW
BEGIN
SET @insertTime=NOW();
insert into history (history_id, username, h_date, success, `type`, `table_name`)
values( NULL, old.recruiter, NOW(), 0, "Update", 'job');
END //
DELIMITER ;

drop trigger if exists job_update_after ;
DELIMITER //
CREATE TRIGGER job_update_after AFTER UPDATE ON job
FOR EACH ROW
BEGIN
UPDATE history set success =  1 WHERE h_date =@insertTime;
END //
DELIMITER ;

drop trigger if exists job_delete_before ;
DELIMITER //
CREATE TRIGGER job_delete_before BEFORE DELETE ON `job`
FOR EACH ROW
BEGIN
SET @insertTime=NOW();
insert into history (history_id, username, h_date, success, `type`, `table_name`)
values( NULL, old.recruiter, NOW(), 0, "Delete", 'job');
END //
DELIMITER ;

drop trigger if exists job_delete_after ;
DELIMITER //
CREATE TRIGGER job_delete_after AFTER DELETE ON job
FOR EACH ROW
BEGIN
UPDATE history set success =  1 WHERE h_date =@insertTime;
END //
DELIMITER ;


drop trigger if exists etaireia_insert_before ;
DELIMITER //
CREATE TRIGGER etaireia_insert_before BEFORE INSERT ON `etaireia`
FOR EACH ROW
BEGIN
SET @insertTime=NOW();
insert into history (history_id, username, h_date, success, `type`, `table_name`)
values( NULL, "admin", NOW(), 0, "Insert", 'etairia');
END //
DELIMITER ;



drop trigger if exists etaireia_insert_after ;
DELIMITER //
CREATE TRIGGER etaireia_insert_after AFTER INSERT ON `etaireia`
FOR EACH ROW
BEGIN
UPDATE history set success =  1 WHERE h_date =@insertTime;
END //
DELIMITER ;

 drop trigger if exists etaireia_update_before ;
 DELIMITER //
 CREATE TRIGGER etaireia_update_before BEFORE UPDATE ON `etaireia`
 FOR EACH ROW
 BEGIN
  set new.AFM = old.AFM;
 set new.DOY = old.DOY;
 set new.name = old.`name`;
 SET @insertTime=NOW();
 select username into @Rusrname from recruiter where firm = new.AFM Limit 0,1;
 insert into history (history_id, username, h_date, success, `type`, `table_name`)
 values( NULL, @Rusrname, NOW(), 0, "Update", 'etairia');
 END //
 DELIMITER ;


drop trigger if exists etaireia_update_after ;
DELIMITER //
CREATE TRIGGER etaireia_update_after AFTER UPDATE ON `etaireia`
FOR EACH ROW
BEGIN
UPDATE history set success =  1 WHERE h_date =@insertTime;
END //
DELIMITER ;

 drop trigger if exists etaireia_delete_before ;
 DELIMITER //
 CREATE TRIGGER etaireia_delete_before BEFORE DELETE ON `etaireia`
 FOR EACH ROW
 BEGIN
 SET @insertTime=NOW();
 select username into @Rusrname from recruiter where firm = old.AFM Limit 0,1;
 insert into history (history_id, username, h_date, success, `type`, `table_name`)
 values( NULL, @Rusrname, NOW(), 1, "Delete", 'etairia');
 END //
 DELIMITER ;
 
 drop trigger if exists etaireia_delete_after ;
DELIMITER //
CREATE TRIGGER etaireia_delete_after AFTER DELETE ON `etaireia`
FOR EACH ROW
BEGIN
UPDATE history set success =  1 WHERE h_date =@insertTime;
END //
DELIMITER ;




