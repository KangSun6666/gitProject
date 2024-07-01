package org.fkit.springbootmybatistest.controller;

import org.fkit.springbootmybatistest.entity.Appointment;
import org.fkit.springbootmybatistest.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * 预约控制器，管理设备的预约，用户的使用等操作
 *
 * @sk
 */
@Controller
@RequestMapping("/appo")
public class AppointmentController {
    @Autowired
    AppointmentService appointmentService;

    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request) {

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   //转换日期
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));    // CustomDateEditor为自定义日期编辑器
    }

    //预约控制
    @RequestMapping("/appointment")
    public String Appointment(Model model, Appointment appointment) throws Exception {

//        int machine_id =appointment.getMachine_id();
//        String machine_usage = appointment.getMachine_usage();
//        int use_id =appointment.getUse_id();
//        String use_name = appointment.getUse_name();
//        Date pstart_use_time = appointment.getPstart_use_time();
//        Date pend_use_time = appointment.getPend_use_time();
        List<Appointment> appo = appointmentService.ifAvaliable(appointment);   //查询当前设备在所选时间段是否已被预约
        List<Appointment> appo1 = appointmentService.ifHadAppointment(appointment);  //查询当前用户在所选时间段是否已经预约设备
        if (appo.isEmpty() && appo1.isEmpty()) {
            appointmentService.addAppointment(appointment);
            return "success";
        } else if (!appo.isEmpty()) {
            model.addAttribute("appo", appo);
            String msg = "该设备在您所选的时间段已被预约，请重新选择预约时间";   //存储错误信息
            model.addAttribute("msg", msg);  //添加错误信息
            return "fail";

        } else if (!appo1.isEmpty()) {
            model.addAttribute("appo", appo1);
            String msg = "您所选的时间段已存在预约，请重新选择预约时间";    //存储错误信息
            model.addAttribute("msg", msg);  //添加错误信息
            return "fail";
        } else {
            return "fail";
        }
    }

    //查看某设备已被预约的时间段
    @RequestMapping("/haveAppointed")
    public void HaveAppointed(int machine_id, HttpServletResponse response) throws Exception {

        List<Appointment> appointed = appointmentService.selByMachine_id(machine_id);   //查询该设备所有的预约时间
        SimpleDateFormat sdf = new SimpleDateFormat("MM.dd HH:mm");   //格式化时间

        PrintWriter out = response.getWriter(); //写入信息后返回给ajax
        String msg = "";
        for (int i = 0; i < appointed.size(); i++) {
            Appointment appointment = appointed.get(i);
            msg += (" " + sdf.format(appointment.getPstart_use_time()) + "--" + sdf.format(appointment.getPend_use_time()) + " || "); //写入msg
        }
        out.write(msg); //发送信息
        out.flush();
        out.close();
    }

    //查看个人预约
    @RequestMapping("/viewAppo")
    public String ViewAppointment(Model model, String use_name) {

        List<Appointment> appo1 = appointmentService.selByUse_name(use_name);
        model.addAttribute("appo1", appo1);
        return "myappointment";
    }

    //查看所有预约
    @RequestMapping("/selAll")
    public String selAll(Model model, HttpServletRequest request) {
        String use_name = request.getParameter("use_name");
        System.out.println(use_name);
        String machine_usage = request.getParameter("machine_usage");
        if ((use_name != null) || (machine_usage != null)) {
            List<Appointment> appointment = appointmentService.selLikeUse_nameAndMachine_usage(use_name, machine_usage);
            model.addAttribute("appointment", appointment);
        } else {
            List<Appointment> appointment = appointmentService.sel();
            model.addAttribute("appointment", appointment);
        }
        return "/appointmanager/appointmentinfo";
    }

    //预更新
    @RequestMapping("/toUpdate")
    public String toUpdate(int id, Model model) {

        Appointment appo1 = appointmentService.selById(id);
        model.addAttribute("appo1", appo1);
        return "/appointmanager/updateappointment";
    }

    //更新预约信息
    @RequestMapping("/update")
    public String Update(Appointment appointment) {

        appointmentService.updateAppoinyment(appointment);
        return "forward:/appo/selAll";
    }

    //驳回预约申请
    @RequestMapping("/delAppo")
    public String delAppo(int id) {

        appointmentService.delAppointment(id);
        return "forward:/appo/selAll";
    }

    //判断预约时间到没到，到了则开始使用
    @RequestMapping("/useMachine")
    public void useMachine(int id, HttpServletRequest request, HttpServletResponse response) throws Exception {

        request.setCharacterEncoding("UTF-8");  //请求中文乱码处理
        response.setHeader("Content-Type", "text/html;charset=utf-8");  //响应中文乱码处理
        PrintWriter out = response.getWriter();
        String msg; //定义返回信息
//        String basePath = request.getScheme() + "://" + request.getServerName() + ":"  + request.getServerPort()+request.getContextPath();   //获取请求路径
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   //将数据库日期转换为标准日期格式
        Date start_use_time = formatter.parse(request.getParameter("pstart_use_time"));
        Date end_use_time = formatter.parse(request.getParameter("pend_use_time"));
        //System.out.println(start_use_time+"--"+end_use_time);
        Date current_time = new Date();

        int res = start_use_time.compareTo(current_time);   //当前时间与预约时间比较
        int res1 = end_use_time.compareTo(current_time);
        if (res == 1 || res1 == -1) {
            msg = "抱歉，当前时间不在您预约时间范围！";
            out.write(msg);

        } else if ((res == -1 || res == 0) && (res1 == 0 || res1 == 1)) {
            request.getSession().setAttribute("appo", appointmentService.selById(id));
            msg = "时间正确，允许使用";
            out.write(msg);
//            response.setHeader("REDIRECT", "REDIRECT");  //告诉ajax要重定向
//            response.setHeader("CONTENTPATH", basePath+"/appo/using");  //告诉ajax重定向的路径
//            response.setStatus(HttpServletResponse.SC_ACCEPTED);
        }
        out.flush();
        out.close();
    }

    @RequestMapping("/cancelAppointment")
    public void CancelAppo(int id, HttpServletRequest request, HttpServletResponse response) throws Exception {

        request.setCharacterEncoding("UTF-8");  // 请求中文乱码处理
        response.setHeader("Content-Type", "text/html;charset=utf-8");  // 响应中文乱码处理
        PrintWriter out = response.getWriter();
        String msg; //定义错误信息
        Appointment appointment = appointmentService.selById(id);
        Date date1 = appointment.getPstart_use_time();
        Date current_time = new Date();
//        long d = date1.getMinutes() - current_time.getMinutes();
        long d = date1.getTime() - current_time.getTime();
//        long h = date1.getHours() - current_time.getHours();
        System.out.println(d);
        if (date1.compareTo(current_time) == -1) {
            msg = "抱歉，已超过取消时限！";
            out.write(msg);
        } else if (d < (60*60*1000)) {   //距离预约的时间小于一小时
            msg = "距离预约使用的时间不足一小时，不可取消！";
            out.write(msg);
        } else {
            appointmentService.delAppointment(id);
            msg = "取消成功！";
            out.write(msg);
        }
        out.flush();
        out.close();
    }

    //跳转到设备使用界面
    @RequestMapping("/using")
    public String Using(HttpServletRequest request, Model model) {
        Appointment appo = (Appointment) request.getSession().getAttribute("appo");
        model.addAttribute("app", appo);
        return "/usemachine";
    }

}
