package org.fkit.springbootmybatistest.service;

import org.fkit.springbootmybatistest.entity.Record;
import org.fkit.springbootmybatistest.mapper.RecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class RecordService {
    @Autowired
    RecordMapper recordMapper;

    public List<Record> sel(){ return recordMapper.sel();}

    public Record selById(int id){return recordMapper.selById(id);}

    public List<Record> selByMachine_id(int machine_id){return recordMapper.selByMachine_id(machine_id);}

    public List<Record> selByUse_name(String use_name){return recordMapper.selByUse_name(use_name);}

    List<Record> SelByTime(Date start_use_time, Date end_use_time){return recordMapper.SelByTime(start_use_time,end_use_time);}

    public int addRecord(Record record){return recordMapper.addRecord(record);}

    public void delRecord(int id){ recordMapper.delRecord(id);}

    public void updateRecord(Record record){ recordMapper.updateRecord(record);}

    public List<Record> selLikeUse_nameAndMachine_usage(String use_name,String machine_usage){return recordMapper.selLikeUse_nameAndMachine_usage(use_name,machine_usage);}

}
