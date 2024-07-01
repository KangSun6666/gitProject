package org.fkit.springbootmybatistest.mapper;

import org.apache.ibatis.annotations.Param;
import org.fkit.springbootmybatistest.entity.Record;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface RecordMapper {

    List<Record> sel();

    Record selById(int id);

    List<Record> selByMachine_id(int machine_id);

    List<Record> selByUse_name(String use_name);

    List<Record> SelByTime(@Param("start_use_time") Date start_use_time,@Param("end_use_time") Date end_use_time);

    int addRecord(Record record);

    void delRecord(int id);

    void updateRecord(Record record);

    List<Record> selLikeUse_nameAndMachine_usage(@Param("use_name") String use_name,@Param("machine_usage") String machine_usage);

}
