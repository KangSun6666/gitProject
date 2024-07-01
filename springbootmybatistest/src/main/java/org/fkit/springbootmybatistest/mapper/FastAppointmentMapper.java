package org.fkit.springbootmybatistest.mapper;

import org.fkit.springbootmybatistest.entity.FastAppointment;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FastAppointmentMapper {
    List<FastAppointment> sel();

    FastAppointment selById(int id);

    FastAppointment selByUse_name(String use_name);

    List<FastAppointment> selByMachine_id(int machine_id);

    void addFastAppointment(FastAppointment fastAppointment);

    void delFastAppointment(int id);

    void delAll();
}
