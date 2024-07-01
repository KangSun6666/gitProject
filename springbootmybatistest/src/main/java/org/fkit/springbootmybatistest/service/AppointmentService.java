package org.fkit.springbootmybatistest.service;

import org.fkit.springbootmybatistest.entity.Appointment;
import org.fkit.springbootmybatistest.mapper.AppointmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class AppointmentService {
    @Autowired
    AppointmentMapper appointmentMapper;

    public List<Appointment>sel(){return appointmentMapper.sel();}

    public Appointment selById(int id){return appointmentMapper.selById(id);}

    public List<Appointment> selByMachine_id(int machine_id){return appointmentMapper.selByMachine_id(machine_id);}

    public List<Appointment> selByUse_name(String use_name){return appointmentMapper.selByUse_name(use_name);}

    public List<Appointment> selByNameAndPstart_use_time(String use_name, Date pstart_use_time){return appointmentMapper.selByNameAndStart_use_time(use_name,pstart_use_time);}

    public Appointment selByTime(Timestamp pstart_use_time, Timestamp pend_use_time){return appointmentMapper.SelByTime(pstart_use_time,pend_use_time);}

    public int addAppointment(Appointment appointment){return appointmentMapper.addAppointment(appointment);}

    public void delAppointment(int id){ appointmentMapper.delAppointment(id);}

    public void updateAppoinyment(Appointment appointment){appointmentMapper.updateAppointment(appointment);}

    public List<Appointment> ifAvaliable(Appointment appointment) {return appointmentMapper.ifAvaliable(appointment);}  //判断此设备在选择的时间段是否存在预约

    public List<Appointment> ifHadAppointment(Appointment appointment) {return appointmentMapper.ifHadAppointment(appointment);}    //判断此人在选择的时间段是否已有预约

    public List<Appointment> selLikeUse_nameAndMachine_usage(String use_name,String machine_usage){return appointmentMapper.selLikeUse_nameAndMachine_usage(use_name,machine_usage);}

}
