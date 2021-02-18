package com.demo.controller;

import com.demo.domain.dto.ZzUser;
import com.demo.domain.vo.ZzUserVO;
import com.demo.service.ZzUserService;
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
@RequestMapping("/user")
public class UserController {


    @Autowired
    private ZzUserService zzUserService;

    @Autowired
    private IdentityService identityService;


    @GetMapping(value = "/add")
    public ModelAndView add() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("user_add");
        return mv;
    }


    @PostMapping("/save")
    public String save(String userId, String username, String psd, String roleId, String departmentId) {
        ZzUser user = new ZzUser();
        user.setUserId(userId);
        user.setUsername(username);
        user.setPsd(psd);
        user.setDepartmentId(departmentId);
        user.setRoleId(roleId);
        zzUserService.insert(user);
        return "redirect:/user/list";
    }


    @GetMapping("/list")
    public String list(Model model) {
        List<ZzUserVO> userList = zzUserService.listAll(null);
        model.addAttribute("userList", userList);
        return "user_list";
    }

    @RequestMapping("/delete/{userId}")
    public String delete(@PathVariable String userId) {
        zzUserService.deleteByPrimaryKey(userId);
        return "redirect:/user/list";
    }


}
