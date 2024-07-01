package org.fkit.springbootmybatistest.mapper;

import org.apache.ibatis.annotations.Param;
import org.fkit.springbootmybatistest.entity.Machine;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MachineMapper {

    List<Machine> sel();

    Machine selById(int machine_id);

    List<Machine> selByUsage(String m_usage);

    List<Machine> selLikeUsage(String m_usage);

    Machine selByStatus(String use_status);

    int addMachine(Machine machine);

    void delMachine(int machine_id);

    void updateMachine(Machine machine);

    Machine selByMachineId(int id);

    List<Machine> guestSel();

    List<Machine> guestSelByUsage(String m_usage);

    List<Machine> fastSel();

    List<Machine> fastSelByUsage(String m_usage);

    void updateMachineInfo(@Param("machine_id") int machine_id,@Param("description") String description);

    void updateMachineStatus(@Param("machine_id") int machine_id,@Param("use_status") String use_status);

}
