package com.demo.controller;

import com.demo.domain.dto.ZzRole;
import com.demo.service.ZzRoleService;
import org.camunda.bpm.engine.IdentityService;
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
@RequestMapping("/role")
public class RoleController {


    @Autowired
    private ZzRoleService zzRoleService;

    @GetMapping(value = "/add")
    public ModelAndView add() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("role_add");
        return mv;
    }

    @PostMapping("/save")
    public String save(String roleId, String roleName) {
        ZzRole role = new ZzRole();
        role.setRoleId(roleId);
        role.setRoleName(roleName);
        zzRoleService.insert(role);
        return "redirect:/role/list";
    }


    @GetMapping("/list")
    public String list(Model model) {
        List<ZzRole> roleList = zzRoleService.listAll(null);
        model.addAttribute("roleList", roleList);
        return "role_list";
    }

    @RequestMapping("/delete/{roleId}")
    public String delete(@PathVariable String roleId) {
        zzRoleService.deleteByPrimaryKey(roleId);
        return "redirect:/role/list";
    }

}
