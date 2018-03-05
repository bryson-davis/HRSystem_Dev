package com.kevin.HRSystem.controller;

import com.kevin.HRSystem.constant.ConstantManager;
import com.kevin.HRSystem.constant.WebConstant;
import com.kevin.HRSystem.model.Employee;
import com.kevin.HRSystem.service.EmpManagerService;
import com.kevin.HRSystem.service.MgrManagerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.kevin.HRSystem.constant.ConstantManager.*;

@Controller
public class EmployeeController {

    @Resource
    private EmpManagerService empManagerService;

    @Resource
    private MgrManagerService mgrManagerService;

    @RequestMapping(value = "/loginPage", method = RequestMethod.GET)
    public ModelAndView showLoginPage() {
        return new ModelAndView("login", "employee", new Employee());
    }

    @RequestMapping(value = "/processLogin", method = RequestMethod.POST)
    public ModelAndView processLogin(@ModelAttribute("employee") Employee employee, HttpServletRequest request) {
        System.out.println(employee);
        System.out.println(request.getProtocol());
        ModelAndView modelAndView;
        int result = empManagerService.validLogin(employee);
        String message;
        //登录结果为普通员工
        if(result == LOGIN_EMP) {
            //设置Session
            request.getSession().setAttribute(WebConstant.USER, employee.getName());
            request.getSession().setAttribute(WebConstant.LEVEL, WebConstant.EMP_LEVEL);

            message = "您已成功登录系统,您的身份是普通员工";
            modelAndView = new ModelAndView("success");
            modelAndView.addObject("message", message);
            return  modelAndView;
        } else if(result == ConstantManager.LOGIN_MGR){
            request.getSession().setAttribute(WebConstant.USER, employee.getName());
            request.getSession().setAttribute(WebConstant.LEVEL, WebConstant.MGR_LEVEL);

            message = "您已成功登录系统,您的身份是普通员工";
            modelAndView = new ModelAndView("success");
            modelAndView.addObject("message", message);
            return modelAndView;
        } else {
            modelAndView = new ModelAndView("error");
            return modelAndView;
        }
    }


}
