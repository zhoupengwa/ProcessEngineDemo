package com.demo.controller;

import com.demo.domain.dto.ZPaiche;
import com.demo.domain.dto.ZzUser;
import com.demo.service.ZPaicheService;
import com.demo.service.ZzUserService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author zhoupeng
 */
@Controller
@RequestMapping("/paiche")
@Slf4j
public class PaicheController {


    @Autowired
    private ZzUserService zzUserService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private IdentityService identityService;

    @Autowired
    private RuntimeService runtimeService;


    @Autowired
    private ZPaicheService zPaicheService;


    private final String processDefinitionKey = "paiche";

    @RequestMapping("/start/save")
    public String saveStartForm(HttpServletRequest request, HttpSession session) {
        String userId = session.getAttribute("userId") == null ? null : session.getAttribute("userId").toString();
        if (userId == null) {
            return "paiche_login";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map<String, Object> map = new HashMap<>();
        UUID uuid = UUID.randomUUID();
        String businessKey = uuid.toString().replace("-", "");

        ZPaiche record = new ZPaiche();
        String startDateTime = request.getParameter("startDateTime");
        String startPosition = request.getParameter("startPosition");
        String endPosition = request.getParameter("endPosition");
        String persons = request.getParameter("persons");
        String phone = request.getParameter("phone");
        String bzhu = request.getParameter("bzhu");


        ZzUser currentUser = zzUserService.selectByPrimaryKey(userId);

        try {

            //流程变量，职务，分支使用
            map.put("role", currentUser.getRoleId());
            if ("002".equals(currentUser.getRoleId())) {
                //如果是职员，则领导审批为 该职员所在部门的领导
                map.put("leader_department", currentUser.getDepartmentId() + ":001");
            } else {
                //如果是部门领导，直接设置为车管部门领导
                map.put("leader_department", "003:001");
            }
            ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                    .processDefinitionKey(processDefinitionKey).latestVersion().singleResult();

            String processDefinitionId = processDefinition.getId();
            identityService.setAuthenticatedUserId(userId);

            //以businessKey传参方式：启动流程
            ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinitionId, businessKey, map);


            record.setId(businessKey);
            record.setStartDateTime(sdf.parse(startDateTime));
            record.setStartPosition(startPosition);
            record.setEndPosition(endPosition);
            record.setPersons(Integer.parseInt(persons));
            record.setPhone(phone);
            record.setBzhu(bzhu);
            record.setCreateTime(new Date());
            record.setUserId(userId);
            //每个流程相关业务 需要回填记录对应的流程实例ID
            record.setPid(processInstance.getId());
            int rowCount = zPaicheService.insert(record);
            if (rowCount != 1) {
                throw new RuntimeException("insert paiche record error");
            }

        } catch (Exception ex) {
            System.out.println("error : " + ex.toString());
        } finally {
            identityService.setAuthenticatedUserId(null);
        }
        return "redirect:/paiche/list";
    }


    @GetMapping("/index")
    public String index() {
        return "paiche_login";
    }

    @PostMapping("/login")
    public String login(String userId, String psd, HttpSession session, Model model) {
        ZzUser zzUser = zzUserService.selectByPrimaryKey(userId);
        if (zzUser == null) {
            model.addAttribute("msg", "账号有误");
            return "common_back_msg";
        }
        if (zzUser.getPsd().equals(psd)) {
            log.info("user login success: {}", userId);
            session.setAttribute("userId", userId);
            return "paiche_add";
        }
        model.addAttribute("msg", "密码错误");
        return "common_back_msg";
    }
}
