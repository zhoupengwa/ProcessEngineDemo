package com.demo.controller;

import com.demo.domain.dto.ZzDepartment;
import com.demo.service.ZzDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author zhoupeng
 */
@Controller
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    private ZzDepartmentService zzDepartmentService;


    @GetMapping(value = "/add")
    public ModelAndView add() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("department_add");
        return mv;
    }


    @PostMapping("/save")
    public String save(String departmentId, String departmentName) {
        ZzDepartment department = new ZzDepartment();
        department.setDepartmentId(departmentId);
        department.setDepartmentName(departmentName);
        zzDepartmentService.insert(department);
        return "redirect:/department/list";
    }


    @GetMapping("/list")
    public String list(Model model) {
        List<ZzDepartment> departmentList = zzDepartmentService.listAll(null);
        model.addAttribute("departmentList", departmentList);
        return "department_list";
    }

    @RequestMapping("/delete/{departmentId}")
    public String delete(@PathVariable String departmentId) {
        zzDepartmentService.deleteByPrimaryKey(departmentId);
        return "redirect:/department/list";
    }
}
