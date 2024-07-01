package org.fkit.springbootmybatistest.mapper;

import org.apache.ibatis.annotations.Param;
import org.fkit.springbootmybatistest.entity.Information;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InformationMapper {

    List<Information> selByMachineId(int machineId);

    void addInformation(Information information);

    List<Information> selByMachineIdAndInfoNum(@Param("machineId") int machineId,@Param("start") int start,@Param("end") int end);

}
