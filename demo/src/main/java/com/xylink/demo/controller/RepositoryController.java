package com.xylink.demo.controller;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author zhoupeng
 */
@RestController
@RequestMapping("/repository")
public class RepositoryController {

    @Autowired
    private RepositoryService repositoryService;

    /**
     * 部署（初始化）流程
     */
    @GetMapping("/deploy")
    public String deploy(String bpmnPath, String name, String category) {
        DeploymentBuilder builder = repositoryService.createDeployment();
//        builder.addClasspathResource("diagrams/leave.bpmn");
        builder.addClasspathResource(bpmnPath);
        builder.name(name);
        builder.category(category);
        Deployment deploy = builder.deploy();
        String result = String.format("部署器\nID:%s\n名称:%s", deploy.getId(), deploy.getName());
        System.out.println(result);
        return result;
    }


    /**
     * 删除部署
     */
    @GetMapping("/deleteDeployment")
    public String deleteDeployment(String deploymentId) throws IOException {
        repositoryService.deleteDeployment(deploymentId);
        return "SUCCESS";
    }


    /**
     * 查询.png文件
     */
    @GetMapping("/viewImage")
    public String viewImage(String deploymentId) throws IOException {
        String imageName = null;
        List<String> deploymentResourceNames = repositoryService.getDeploymentResourceNames(deploymentId);
        if (CollectionUtils.isEmpty(deploymentResourceNames)) {
            return "Empty";
        }
        for (String resourceName : deploymentResourceNames) {
            if (resourceName.indexOf(".png") > 0) {
                imageName = resourceName;
            }
        }

        InputStream inputStream = repositoryService.getResourceAsStream(deploymentId, imageName);
        File file = new File("/Users/zhoupeng/Downloads/" + imageName);
        FileUtils.copyInputStreamToFile(inputStream, file);
        return file.getAbsolutePath();
    }

    /**
     * 查看流程定义
     */
    @GetMapping("/queryDefinition")
    public String queryDefinition(String processDefKey) throws IOException {
        ProcessDefinitionQuery definitionQuery = repositoryService.createProcessDefinitionQuery();
        List<ProcessDefinition> list = definitionQuery
                //查询好比 where
                //.processDefinitionId()
                //.processDefinitionName()
                //.processDefinitionVersion()
                .processDefinitionKey(processDefKey)
                .latestVersion()
                //排序
                .orderByProcessDefinitionVersion().desc()
                //结果
                //.count()
                //.listPage()
                .list();
        if (CollectionUtils.isEmpty(list)) {
            return "Empty";
        }
        StringBuilder builder = new StringBuilder();
        for (ProcessDefinition definition : list) {
            String id = definition.getId();
            String key = definition.getKey();
            int version = definition.getVersion();
            String deploymentId = definition.getDeploymentId();
            String name = definition.getName();
            builder.append("\n\n流程定义id：").append(id)
                    .append("\n流程定义key：").append(key)
                    .append("\n流程定义版本：").append(version)
                    .append("\n流程定义部署id：").append(deploymentId)
                    .append("\n流程定义名称：").append(name);
        }
        String result = builder.toString();
        System.out.println(result);
        return result;
    }
}
