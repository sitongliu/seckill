CREATE PROCEDURE executeSeckill(IN v_seckillId BIGINT, IN v_phone BIGINT, IN v_killtime TIMESTAMP, OUT r_result INT)
  begin
     declare insertcount int default 0;
     start transaction;
     insert ignore into success_killed (seckill_id,user_phone,create_time)
	 	 values(v_seckillId,v_phone,v_killtime);
	  select row_count() into insertcount;
	  if(insertcount = 0) 
	    then rollback ;
	 	 set r_result=-1;
	  elseif(insertcount <0) 
	    then rollback;
	 	 set r_result=-2;
	  else 
	    update seckill set number = number-1 
	 	 where number>0 and v_killtime < end_time and v_killtime > start_time and seckill_id = v_seckillId;
	  select row_count() into insertcount;
	   if(insertcount = 0) then rollback ;
	 	 set r_result=0;
	  elseif(insertcount <0) then rollback;
	 	 set r_result=-2;
	  else commit;
	   set r_result=1;
	end if;
	end if;
	
end;
