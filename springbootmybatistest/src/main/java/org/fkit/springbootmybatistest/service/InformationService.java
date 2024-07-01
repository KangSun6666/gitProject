package org.fkit.springbootmybatistest.service;

import org.apache.ibatis.annotations.Param;
import org.fkit.springbootmybatistest.entity.Information;
import org.fkit.springbootmybatistest.mapper.InformationMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class InformationService {

    @Resource
    private InformationMapper informationMapper;

    public List<Information> selByMachineId(int machineId){return informationMapper.selByMachineId(machineId);}

    public void addInfo(Information information){informationMapper.addInformation(information);}

    public List<Information> selByMachineIdAndInfoNum(@Param("machineId") int machineId,@Param("start") int start,@Param("end") int end){return informationMapper.selByMachineIdAndInfoNum(machineId,start,end);};

}
