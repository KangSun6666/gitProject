package org.fkit.springbootmybatistest.service;

import org.fkit.springbootmybatistest.entity.FastAppointment;
import org.fkit.springbootmybatistest.mapper.FastAppointmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FastAppointmentService {
    @Autowired
    private FastAppointmentMapper fastAppointmentMapper;

    public FastAppointment selById(int id) {
        return fastAppointmentMapper.selById(id);
    }

    public List<FastAppointment> sel() {
        return fastAppointmentMapper.sel();
    }

    public FastAppointment selByUse_name(String use_name) {
        return fastAppointmentMapper.selByUse_name(use_name);
    }

    public List<FastAppointment> selByMachine_id(int machine_id) {
        return fastAppointmentMapper.selByMachine_id(machine_id);
    }

    public void addFastAppointment(FastAppointment fastAppointment) {
        fastAppointmentMapper.addFastAppointment(fastAppointment);
    }

    public void delFastAppointment(int id) {
        fastAppointmentMapper.delFastAppointment(id);
    }

    public void delAll(){fastAppointmentMapper.delAll();}


}
