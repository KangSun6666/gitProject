package org.fkit.springbootmybatistest.mapper;

import org.apache.ibatis.annotations.Param;
import org.fkit.springbootmybatistest.entity.Appointment;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Repository
public interface AppointmentMapper {

    List<Appointment> sel();

    Appointment selById(int id);

    Appointment selByAll(Appointment appointment);

    List<Appointment> selByMachine_id(int machine_id);

    List<Appointment> selByUse_name(String use_name);

    List<Appointment> selByNameAndStart_use_time(@Param("use_name") String use_name,@Param("pstart_use_time") Date pstart_use_time);

    Appointment SelByTime(@Param("pstart_use_time") Timestamp pstart_use_time, @Param("pend_use_time") Timestamp pend_use_time);

    int addAppointment(Appointment appointment);

    void delAppointment(int id);

    void updateAppointment(Appointment appointment);

    List<Appointment> ifAvaliable(Appointment appointment);

    List<Appointment> ifHadAppointment(Appointment appointment);

    List<Appointment> selLikeUse_nameAndMachine_usage(@Param("use_name") String use_name,@Param("machine_usage") String machine_usage);

}
