package org.fkit.springbootmybatistest.interceptor;

import org.fkit.springbootmybatistest.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 进入controller层之前拦截请求
 * 返回值表示是否将当前的请求拦截下来，false:拦截请求；true:不拦截，继续执行
 * Object obj:表示当前被拦截的目标对象（controller中的方法）
 * @sk
 */
@Component
public class UserInterceptor implements HandlerInterceptor {

     @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handler)throws IOException{
         System.out.println("执行到了preHandler方法");
         System.out.println(handler);
         User user = (User) request.getSession().getAttribute("user");
         if (user == null){
             response.sendRedirect(request.getContextPath()+"/user/toIndex");//拦截后跳转的方法
             System.out.println("已成功拦截并转发跳转");
             return false;
         }
         System.out.println("合格不需要拦截，放行");
         return true;
     }

     /*
     * 处理请求完成后视图渲染之前的处理操作
     * 通过ModelAndView参数改变显示的视图，或发往视图的方法
     * */
     @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView){
         System.out.println("执行了postHandle方法");
     }

    /**
     * 视图渲染之后的操作
     */
    @Override
    public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)throws Exception{
        System.out.println("执行到了afterCompletion方法");
    }
}
