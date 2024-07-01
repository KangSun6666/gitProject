package org.fkit.springbootmybatistest.controller;

import org.fkit.springbootmybatistest.entity.Machine;
import org.fkit.springbootmybatistest.service.MachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 设备控制器，查看，管理设备等操作
 * @sk
 */
@Controller
@RequestMapping("/machine")
public class MachineController {
    @Autowired
    MachineService machineService;

    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request) {

        //转换日期
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));// CustomDateEditor为自定义日期编辑器
    }

    //查询设备信息
    @RequestMapping("/selMachine")
    public String selM(Model model, HttpServletRequest request) {
        String m_usage = request.getParameter("m_usage");
        if (m_usage == null) {
            List<Machine> machines = machineService.guestSel();
            model.addAttribute("machines", machines);
        } else {
            List<Machine> machines = machineService.guestSelByUsage(m_usage);
            model.addAttribute("machines", machines);
        }
        return "mdata";
    }

    //预预约
    @RequestMapping("/toAppointment")
    public String toAppoint(int machine_id, Model model, HttpServletRequest request) {
        Machine machine = machineService.selByMachineId(machine_id);
        model.addAttribute("machine", machine);
//        System.out.println(machine_id);
//        System.out.println(request.getSession().getAttribute("user"));
        return "appointment";
    }

    //查看设备详情
    @RequestMapping("/detail")
    public String Detail(int machine_id) {
        System.out.println(machine_id);
        return "detail";
    }

    //管理员查看所有设备
    @RequestMapping("/selAll")
    public String selAll(Model model, HttpServletRequest request) {
        String m_usage = request.getParameter("m_usage");
        if (m_usage != null) {
            List<Machine> machines = machineService.selLikeUsage(m_usage);
            model.addAttribute("machines", machines);
        } else {
            List<Machine> machines = machineService.sel();
            model.addAttribute("machines", machines);
        }
        return "/machinemanager/machineinfo";
    }

    //删除设备
    @RequestMapping("/delMachine")
    public String delMachine(int id) {
        machineService.delMachine(id);
        return "forward:/machine/selAll";
    }

    //预更新设备
    @RequestMapping("/toUpdate")
    public String toUpdate(int id, Model model) {
        Machine machine = machineService.selById(id);
        model.addAttribute("machine", machine);
        return "/machinemanager/updatemachine";
    }

    //更新设备信息
    @RequestMapping("/update")
    public String Update(Machine machine) {
        machineService.updateMachine(machine);
        return "forward:/machine/selAll";
    }

    //预添加
    @RequestMapping("/toAdd")
    public String toAdd() {
        return "/machinemanager/addmachine";
    }

    //添加设备
    @RequestMapping("/addMachine")
    public String addMachine(Machine machine) {
        machineService.addMachine(machine);
        return "forward:/machine/selAll";
    }

    @RequestMapping("/viewMachine")
    public String viewMachine(int machine_id, Model model) {
        Machine m = machineService.selByMachineId(machine_id);
        model.addAttribute("m", m);
        return "viewmachine";
    }

}
