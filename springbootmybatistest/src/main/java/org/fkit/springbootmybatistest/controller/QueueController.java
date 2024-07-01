package org.fkit.springbootmybatistest.controller;

import org.fkit.springbootmybatistest.entity.FastAppointment;
import org.fkit.springbootmybatistest.entity.Machine;
import org.fkit.springbootmybatistest.service.FastAppointmentService;
import org.fkit.springbootmybatistest.service.MachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

/**
 * 即时排队控制器，即时预约排队
 * 设置每次只能有一个检测和一个其他设备提供即时服务，即更改设备的status 为 on
 *
 * @sk
 */
@Controller
@RequestMapping("/que")
public class QueueController {

    @Autowired
    private MachineService machineService;

    @Autowired
    private FastAppointmentService fastAppointmentService;

    private LinkedList<FastAppointment> queue1 = new LinkedList<FastAppointment>(); //建立检测队列

    private LinkedList<FastAppointment> queue2 = new LinkedList<FastAppointment>(); //建立除菌队列

    @RequestMapping("/selM")
    public String selM(Model model, HttpServletRequest request) {
        String m_usage = request.getParameter("m_usage");
        System.out.println(m_usage);
        if (m_usage == null) {
            List<Machine> machines = machineService.fastSel();
            model.addAttribute("machines", machines);
        } else {
            List<Machine> machines = machineService.fastSelByUsage(m_usage);
            model.addAttribute("machines", machines);
        }
        return "/queue/machinedata";
    }

    @RequestMapping("/toAppointment")
    public String toAppo(int machine_id, Model model) {
        System.out.println(machine_id);
        Machine machine = machineService.selByMachineId(machine_id);
        model.addAttribute("machine", machine);
        return "/queue/fastappoint";
    }

    @RequestMapping("/appoint")
    public String StartUse(FastAppointment appo, Model model) {
        String machine_usage = appo.getMachine_usage();
        String use_name = appo.getUse_name();
        int machine_id = appo.getMachine_id();
//        System.out.println(machine_usage);
//        System.out.println(use_name);
        if ((fastAppointmentService.selByUse_name(use_name)) == null) {  //判断用户是否已经在队列中（已预约）
            if (machine_usage.equals("检测")) {
                fastAppointmentService.addFastAppointment(appo);    //添加记录
                FastAppointment appo1 = fastAppointmentService.selByUse_name(use_name);
                queue1.add(appo1);   //加入队列
                System.out.println(queue1.toString());
                System.out.println(queue1.size());
            } else if (machine_usage.equals("除菌")) {
                fastAppointmentService.addFastAppointment(appo);    //添加记录
                FastAppointment appo1 = fastAppointmentService.selByUse_name(use_name);
                queue2.add(appo1);   //加入队列
                System.out.println(queue1.size());
//                fastAppointmentService.addFastAppointment(appo);
            }
            return "/queue/success";
        } else {
            Machine machine = machineService.selByMachineId(machine_id);
            model.addAttribute("machine", machine);
            model.addAttribute("err", "您已在队伍中,请勿重复预约！");
            return "/queue/fastappoint";
        }
    }

    @RequestMapping("/viewAppo")
    public String ViewAppointment(Model model, String use_name) {
        FastAppointment appo1 = fastAppointmentService.selByUse_name(use_name);
        model.addAttribute("appo1", appo1);
        return "/queue/myfastappointment";
    }

    @RequestMapping("seeIndex")
    public void seeIndex(int id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("UTF-8");  //请求中文乱码处理
        response.setHeader("Content-Type", "text/html;charset=utf-8");  //响应中文乱码处理
        PrintWriter out = response.getWriter();
        String msg; //定义返回信息
        FastAppointment appo = fastAppointmentService.selById(id);
        String machine_usage = appo.getMachine_usage();
        int id1 = appo.getId();
        if (machine_usage.equals("检测")) {
            for (FastAppointment t : queue1) {
                if (t.getId() == id1) {
                    int pos = queue1.indexOf(t);
                    msg = "您前面还有" + pos + "个人";
                    out.write(msg);
                    out.flush();
                    out.close();
                }
            }
        } else {
            for (FastAppointment t : queue2) {
                if (t.getId() == id1) {
                    int pos = queue2.indexOf(t);
                    msg = "您前面还有" + pos + "个人";
                    out.write(msg);
                    out.flush();
                    out.close();
                }
            }
        }
    }

    @RequestMapping("/useMachine")
    public void useMachine(int id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("UTF-8");  //请求中文乱码处理
        response.setHeader("Content-Type", "text/html;charset=utf-8");  //响应中文乱码处理
        PrintWriter out = response.getWriter();
        String msg; //定义返回信息
        FastAppointment appo = fastAppointmentService.selById(id);
        String machine_usage = appo.getMachine_usage();
        int id1 = appo.getId();
        if (machine_usage.equals("检测")) {
            for (FastAppointment t : queue1) {
                if (t.getId() == id1) {
                    int pos = queue1.indexOf(t);
//                System.out.println(pos);
                    int n = pos;
                    if (pos == 0) {
                        request.getSession().setAttribute("appointment1", appo);
                        msg = "时间正确，允许使用";
                        out.write(msg);
                        out.flush();
                        out.close();
                    } else {
                        msg = "别急，你前面还有" + n + "个人";
                        out.write(msg);
                        out.flush();
                        out.close();
                    }
                }
            }
        } else {
            for (FastAppointment t : queue2) {
                if (t.getId() == id1) {
                    int pos = queue2.indexOf(t);
//                System.out.println(pos);
                    int n = pos;
                    if (pos == 0) {
                        request.getSession().setAttribute("appointment1", appo);
                        msg = "时间正确，允许使用";
                        out.write(msg);
                        out.flush();
                        out.close();
                    } else {
                        msg = "别急，你前面还有" + n + "个人";
                        out.write(msg);
                        out.flush();
                        out.close();
                    }
                }
            }
        }
//        for(FastAppointment t:queue1){
//            if (t.getId() == id1){
//                int pos = queue1.indexOf(t);
//                System.out.println(pos);
//                int n = pos;
//                if (pos == 0){
//                    request.getSession().setAttribute("appointment1",appo);
//                    msg ="时间正确，允许使用";
//                    out.write(msg);
//                    out.flush();
//                    out.close();
//                }else {
//                    msg ="别急，你前面还有"+n+"个人";
//                    out.write(msg);
//                    out.flush();
//                    out.close();
//                }
//            }
//        }
    }

    @RequestMapping("/using")
    public String Using(HttpServletRequest request, Model model) {
        FastAppointment appo = (FastAppointment) request.getSession().getAttribute("appointment1");
        model.addAttribute("app", appo);
        return "/queue/usemachine";
    }

    @RequestMapping("/end")
    public String End(FastAppointment fastAppointment) {
        int id = fastAppointment.getId();
        FastAppointment fa = fastAppointmentService.selById(id);
        String machine_usage = fa.getMachine_usage();
        if (machine_usage.equals("检测")) {
            for(int i=0; i<queue1.size(); i++) {
                FastAppointment faa = queue1.get(i);
                if(faa.getId() == id) {
                    queue1.remove(i);
                    i--;// 元素位置发生变化，修改i
                }
            }
        } else {
            for(int i=0; i<queue2.size(); i++) {
                FastAppointment faa = queue2.get(i);
                if(faa.getId() == id) {
                    queue2.remove(i);
                    i--;// 元素位置发生变化，修改i
                }
            }
        }
        fastAppointmentService.delFastAppointment(id);
        return "forward:/user/welcome";
    }

    @RequestMapping("/cancelAppointment")
    public void cancelAppointment(int id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("UTF-8");  // 请求中文乱码处理
        response.setHeader("Content-Type", "text/html;charset=utf-8");  // 响应中文乱码处理
        PrintWriter out = response.getWriter();
        String msg; //定义错误信息
        FastAppointment fa = fastAppointmentService.selById(id);
        String machine_usage = fa.getMachine_usage();
        if (machine_usage.equals("检测")) {
            for (FastAppointment t : queue1) {
                if (t.getId() == id) {
                    if (queue1.indexOf(t) == 0) {
                        msg = "您已排在第一位，不可取消";
                        out.write(msg);
                        out.flush();
                        out.close();
                    } else {
                        for(int i=0; i<queue1.size(); i++) {
                            FastAppointment faa = queue1.get(i);
                            if(faa.getId() == id) {
                                queue1.remove(i);
                                i--;// 元素位置发生变化，修改i
                            }
                        }
                        fastAppointmentService.delFastAppointment(id);
                        msg = "取消成功";
                        out.write(msg);
                        out.flush();
                        out.close();
                    }
                }
            }
        } else {
            for (FastAppointment t : queue2) {
                if (t.getId() == id) {
                    if (queue2.indexOf(t) == 0) {
                        msg = "您已排在第一位，不可取消";
                        out.write(msg);
                        out.flush();
                        out.close();
                    } else {
                        for(int i=0; i<queue2.size(); i++) {
                            FastAppointment faa = queue2.get(i);
                            if(faa.getId() == id) {
                                queue2.remove(i);
                                i--;// 元素位置发生变化，修改i
                            }
                        }
                        fastAppointmentService.delFastAppointment(id);
                        msg = "取消成功";
                        out.write(msg);
                        out.flush();
                        out.close();
                    }
                }
            }
        }
    }

    @RequestMapping("/selAll")
    public String selAll(Model model) {
        List<FastAppointment> fas = fastAppointmentService.sel();
        model.addAttribute("fas", fas);
        return "/queue/allappo";
    }

    @RequestMapping("/removeAppo")
    public String removeAppo(int id, Model model) {
        FastAppointment fa = fastAppointmentService.selById(id);
        String machine_usage = fa.getMachine_usage();
        if (machine_usage.equals("检测")) {
            for (FastAppointment t : queue1) {
                if (t.getId() == id) {
                    int pos = queue1.indexOf(t);
                    if (pos == 0) {
                        model.addAttribute("err", "当前用户已在第一位或使用中，不可移除！");
                    } else {
                        for(int i=0; i<queue1.size(); i++) {
                            FastAppointment faa = queue1.get(i);
                            if(faa.getId() == id) {
                                queue1.remove(i);
                                i--;// 元素位置发生变化，修改i
                            }
                        }
                        fastAppointmentService.delFastAppointment(id);
                    }
                }
            }
        } else {
            for (FastAppointment t : queue2) {
                if (t.getId() == id) {
                    int pos = queue2.indexOf(t);
                    if (pos == 0) {
                        model.addAttribute("err", "当前用户已在第一位或使用中，不可移除！");
                    } else {
                        for(int i=0; i<queue2.size(); i++) {
                            FastAppointment faa = queue2.get(i);
                            if(faa.getId() == id) {
                                queue2.remove(i);
                                i--;// 元素位置发生变化，修改i
                            }
                        }
                        fastAppointmentService.delFastAppointment(id);
                    }
                }
            }
        }
        return "forward:/que/selAll";
    }

    @RequestMapping("/clear")
    public String Clear(){
        queue1.clear();
        queue2.clear();
        fastAppointmentService.delAll();
        return "forward:/que/selAll";
    }
}
