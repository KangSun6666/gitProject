package org.fkit.springbootmybatistest.controller;

import org.apache.tomcat.util.buf.HexUtils;
import org.fkit.springbootmybatistest.entity.*;
import org.fkit.springbootmybatistest.service.*;
import org.fkit.springbootmybatistest.utils.Client;
import org.fkit.springbootmybatistest.utils.ShareMsg;
import org.fkit.springbootmybatistest.utils.StdEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 使用记录控制器
 * @sk
 */
@Controller
@RequestMapping("/record")
public class RecordController {
    @Autowired
    RecordService recordService;

    @Autowired
    AppointmentService appointmentService;

    @Resource
    InformationService informationService;

    @Resource
    OtherInfoService otherInfoService;

    @Resource
    MachineService machineService;

    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request) {

        DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   //转换日期
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));    // CustomDateEditor为自定义日期编辑器
    }

    @RequestMapping("/viewRecord")
    public String viewRecord(HttpServletRequest request,Model model){
        String use_name = request.getParameter("use_name");
        List<Record> records = recordService.selByUse_name(use_name);
        model.addAttribute("myrecords",records);
        return "myrecords";
    }

    @RequestMapping("/sel")
    public String Sel(Model model,HttpServletRequest request){
        String use_name = request.getParameter("use_name");
        String machine_usage = request.getParameter("machine_usage");
        if ((use_name != null)||(machine_usage != null)){
            List<Record> records = recordService.selLikeUse_nameAndMachine_usage(use_name,machine_usage);
            model.addAttribute("records", records);
        }else {
            List<Record> records = recordService.sel();
            model.addAttribute("records", records);
        }
        return "/recordmanager/recordinfo";
    }

    @RequestMapping("/toUpdate")
    public String toUpdate(int id, Model model){
        Record record = recordService.selById(id);
        model.addAttribute("record",record);
        return "recordmanager/updaterecord";
    }

    @RequestMapping("/update")
    public String Update(Record record){
        recordService.updateRecord(record);
        return "forward:/record/sel";
    }

    @RequestMapping("/delRecord")
    public String DelRecord(int id){
        recordService.delRecord(id);
        return "forward:/record/sel";
    }

    @RequestMapping("/addRecord")
    public String AddRecord(Record record, HttpServletRequest request){
        System.out.println("提交成功");
        String p=request.getParameter("id");
        int  i=Integer.parseInt(p);
        appointmentService.delAppointment(i);  //删除预约表中对应的记录
        recordService.addRecord(record);    //添加使用记录
        return "forward:/user/welcome";
    }

    @RequestMapping("/countTime")
    public void CountTime(HttpServletRequest request,HttpServletResponse response)throws Exception{
        request.setCharacterEncoding("UTF-8");  // 请求中文乱码处理
        response.setHeader("Content-Type", "text/html;charset=utf-8");  // 响应中文乱码处理
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");
        Date current_date = new Date();
        Date date1 = sim.parse(request.getParameter("endTime"));
        String msg = "";
        System.out.println(current_date);
        System.out.println(request.getParameter("endTime"));
        if (date1.compareTo(current_date)==1){
            msg="您的剩余使用时间为："+(date1.getMinutes()-current_date.getMinutes()+((date1.getHours()-current_date.getHours())*60)+"分钟");
        }else{
            msg = "1";  //发送使用结束信息，提醒页面提交表单
        }
        PrintWriter out = response.getWriter();
        out.write(msg);
        out.flush();
        out.close();
    }

    @ResponseBody
    @RequestMapping("/information")
    public List<Information> Socket(int id){
        Appointment app = appointmentService.selById(id);
        ShareMsg.setMachine_id(app.getMachine_id());
        String msg = "0x3A0x010x1F0x040x010x030x010x05";
        Client.sendMsg(msg);
        List<Information> info = informationService.selByMachineIdAndInfoNum(ShareMsg.machine_id, ShareMsg.getStartInfoId(), ShareMsg.getEndInfoId());
        return info;
    }

    @ResponseBody
    @RequestMapping("/otherInfo")
    public OtherInfo otherInfo(int id){
        Appointment app = appointmentService.selById(id);
        ShareMsg.setMachine_id(app.getMachine_id());
        String msg = "0x3A0x010x1F0x030x000x00";
        Client.sendMsg(msg);
        OtherInfo infos = otherInfoService.selByMachineId(ShareMsg.machine_id);
        return infos;
    }

    @ResponseBody
    @RequestMapping("/std")
    public List<String> getStd(){
        List list = new ArrayList();
        for (int i=0;i<StdEnum.values().length;i++){
            list.add(StdEnum.getName(i));
        }
        return list;
    }

    @ResponseBody
    @RequestMapping("/machineInfo")
    public Machine getMachineInfo(int id){
        Appointment app = appointmentService.selById(id);
        Machine machine = machineService.selByMachineId(app.getMachine_id());
        return machine;
    }

    @ResponseBody
    @RequestMapping("/writeMsg")
    public String writeMsg(HttpServletRequest request){
        String status = "write success !";
        String testAmountStr = request.getParameter("testAmount");
        int testAmount = Integer.parseInt(testAmountStr);
        String languageStr = request.getParameter("language");
        int language = Integer.parseInt(languageStr);
        String testDateStr = request.getParameter("testDate");
        String[] s = testDateStr.split("-");
        int year = Integer.parseInt(s[0]);
        String testYear = "0x";
        if (year>15){
            testYear+=Integer.toHexString(year);
        }else {
            testYear="0x0"+Integer.toHexString(year);
        }
        int month = Integer.parseInt(s[1]);
        String testMonth = "0x";
        if (month>15){
            testMonth+=Integer.toHexString(month);
        }else {
            testMonth="0x0"+Integer.toHexString(month);
        }
        int day = Integer.parseInt(s[2]);
        String testDay = "0x";
        if (day>15){
            testDay+=Integer.toHexString(day);
        }else {
            testDay="0x0"+Integer.toHexString(day);
        }
        String printTypeStr = request.getParameter("printType");
        int printType = Integer.parseInt(printTypeStr);
        String outTypeStr = request.getParameter("outType");
        int outType = Integer.parseInt(outTypeStr);
        String stdIdStr = request.getParameter("stdId");
        int stdId = Integer.parseInt(stdIdStr);
        String testStd = "0x";
        if (stdId>15){
            testStd+=Integer.toHexString(stdId);
        }else {
            testStd="0x0"+Integer.toHexString(stdId);
        }
        String msg = "0x3A0x010x1F0x030x800x08"+"0x0"+ Integer.toHexString(testAmount)+"0x0"+Integer.toHexString(language)+testYear+testMonth+testDay+"0x0"+Integer.toHexString(printType)+"0x0"+Integer.toHexString(outType)+testStd;
//        System.out.println(msg);
        Client.sendMsg(msg);
        return status;
    }

}
