package org.fkit.springbootmybatistest.mapper;

import org.fkit.springbootmybatistest.entity.OtherInfo;
import org.springframework.stereotype.Repository;

@Repository
public interface OtherInfoMapper {
    OtherInfo selByMachineId(int machineId);
    void insertInfo(OtherInfo otherInfo);
    void updateInfo(OtherInfo otherInfo);
}
