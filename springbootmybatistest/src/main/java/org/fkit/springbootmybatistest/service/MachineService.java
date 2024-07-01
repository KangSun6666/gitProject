package org.fkit.springbootmybatistest.service;

import org.apache.ibatis.annotations.Param;
import org.fkit.springbootmybatistest.entity.Machine;
import org.fkit.springbootmybatistest.mapper.MachineMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MachineService {
    @Autowired
    MachineMapper machineMapper;

    public List<Machine> sel(){return machineMapper.sel();}

    public Machine selById(int machine_id){return machineMapper.selById(machine_id);}

    public List<Machine> selByUsage(String m_usage){return machineMapper.selByUsage(m_usage);}

    public List<Machine> selLikeUsage(String m_usage){return machineMapper.selLikeUsage(m_usage);}

    public Machine selByStatus(String use_status){return machineMapper.selByStatus(use_status);}

    public int addMachine(Machine machine){return machineMapper.addMachine(machine);}

    public void delMachine(int machine_id){ machineMapper.delMachine(machine_id);}

    public void updateMachine(Machine machine){ machineMapper.updateMachine(machine);}

    public Machine selByMachineId(int id){return machineMapper.selByMachineId(id);}

    public List<Machine> guestSel(){return machineMapper.guestSel();}

    public List<Machine> guestSelByUsage(String m_usage){return machineMapper.guestSelByUsage(m_usage);}

    public List<Machine> fastSel(){return machineMapper.fastSel();}

    public List<Machine> fastSelByUsage(String m_usage){return machineMapper.fastSelByUsage(m_usage);}

    public void updateMachineInfo(@Param("machine_id") int machine_id,@Param("description") String description){machineMapper.updateMachineInfo(machine_id,description);}

    public void updateMachineStatus(int machine_id,String machine_status){machineMapper.updateMachineStatus(machine_id,machine_status);}
}
