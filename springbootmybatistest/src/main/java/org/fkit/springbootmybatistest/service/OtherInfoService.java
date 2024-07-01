package org.fkit.springbootmybatistest.service;

import org.fkit.springbootmybatistest.entity.OtherInfo;
import org.fkit.springbootmybatistest.mapper.OtherInfoMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class OtherInfoService {
    @Resource
    private OtherInfoMapper otherInfoMapper;

    public OtherInfo selByMachineId(int machineId){return otherInfoMapper.selByMachineId(machineId);};
    public void insertInfo(OtherInfo otherInfo){otherInfoMapper.insertInfo(otherInfo);};
    public void updateInfo(OtherInfo otherInfo){otherInfoMapper.updateInfo(otherInfo);};
}
