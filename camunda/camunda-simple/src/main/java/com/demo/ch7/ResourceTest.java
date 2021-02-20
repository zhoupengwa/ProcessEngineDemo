package com.demo.ch7;

import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.authorization.*;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * @author zhoupeng
 */
public class ResourceTest {

    public IdentityService identityService;

    public AuthorizationService authorizationService;

    ProcessEngineConfiguration processEngineConfiguration;
    ManagementService managementService;

    @Before
    public void init() {
        processEngineConfiguration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("com.demo/ch7/camunda.cfg.xml");
        ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();
        identityService = processEngine.getIdentityService();
        authorizationService = processEngine.getAuthorizationService();
        managementService = processEngine.getManagementService();
    }


    /**
     * select distinct RES.* from ACT_RU_AUTHORIZATION RES WHERE RES.USER_ID_ in ( ? ) order by RES.ID_ asc LIMIT ? OFFSET ?
     * demo(String), 2147483647(Integer), 0(Integer)
     */
    @Test
    public void queryUserAuthorization() {

        AuthorizationQuery authorizationQuery = authorizationService.createAuthorizationQuery();
        List<Authorization> authorizationList = authorizationQuery
                .userIdIn("demo")
//                .groupIdIn("crm")
                .list();
        for (Authorization authorization : authorizationList) {
            System.out.println("##############");
            System.out.println(authorization.getId());
            System.out.println(authorization.getAuthorizationType());
            System.out.println(authorization.getGroupId());
            System.out.println(authorization.getResourceId());
            System.out.println(authorization.getResourceType());
            System.out.println("##############");
        }

    }


    public void createAuthorization(String userId, String groupId, Resource resource, Permissions[] permissions) {
        Authorization authorization = authorizationService.createNewAuthorization(Authorization.AUTH_TYPE_GRANT);
        authorization.setUserId(userId);
        authorization.setGroupId(groupId);
        authorization.setResource(resource);
        authorization.setResourceId(resource.resourceType() + "");
        authorization.setPermissions(permissions);
        authorizationService.saveAuthorization(authorization);
    }

    public static class TestResource implements Resource {
        private String resourceName;
        private int resourceType;

        public TestResource(String resourceName, int resourceType) {
            this.resourceName = resourceName;
            this.resourceType = resourceType;
        }

        @Override
        public String resourceName() {
            return this.resourceName;
        }

        @Override
        public int resourceType() {
            return this.resourceType;
        }
    }

    /**
     * insert into ACT_RU_AUTHORIZATION ( ID_, TYPE_, GROUP_ID_, USER_ID_, RESOURCE_TYPE_, RESOURCE_ID_, PERMS_, REV_ ) values ( ?, ?, ?, ?, ?, ?, ?, 1 )
     * 801(String), 1(Integer), null, peng(String), 100(Integer), 100(String), 2147483647(Integer)
     * <p>
     * insert into ACT_RU_AUTHORIZATION ( ID_, TYPE_, GROUP_ID_, USER_ID_, RESOURCE_TYPE_, RESOURCE_ID_, PERMS_, REV_ ) values ( ?, ?, ?, ?, ?, ?, ?, 1 )
     * 802(String), 1(Integer), null, peng2(String), 200(Integer), 200(String), 32(Integer)
     */
    @Test
    public void addUserAuthorization() {
        Resource resource1 = new TestResource("resource1", 100);
        Resource resource2 = new TestResource("resource2", 200);
        createAuthorization("peng", null, resource1, new Permissions[]{Permissions.ALL});
        createAuthorization("peng2", null, resource2, new Permissions[]{Permissions.ACCESS});
    }

    @Test
    public void addUserAuthorization2() {
        Authorization authorization = authorizationService.createNewAuthorization(Authorization.AUTH_TYPE_GRANT);
        authorization.setUserId("peng4");
        authorization.setGroupId(null);
        authorization.setResource(Resources.APPLICATION);
        authorization.setResourceId("*");
        authorization.setPermissions(new Permissions[]{Permissions.ALL});
        authorizationService.saveAuthorization(authorization);
    }

    @Test
    public void addUserAuthorization3() {
        Authorization authorization = authorizationService.createNewAuthorization(Authorization.AUTH_TYPE_GRANT);
        authorization.setUserId("peng4");
        authorization.setGroupId(null);
        authorization.setResource(Resources.USER);
        authorization.setResourceId("*");
        authorization.setPermissions(new Permissions[]{Permissions.READ, Permissions.CREATE, Permissions.UPDATE});
        authorizationService.saveAuthorization(authorization);
    }


    /**
     * 授权访问资源名称是组
     */
    @Test
    public void addUserAuthorization4() {
        Authorization authorization = authorizationService.createNewAuthorization(Authorization.AUTH_TYPE_GRANT);
        authorization.setUserId("peng4");
        authorization.setGroupId(null);
        authorization.setResource(Resources.GROUP);
        authorization.setResourceId("*");
        authorization.setPermissions(new Permissions[]{Permissions.READ, Permissions.CREATE, Permissions.UPDATE, Permissions.DELETE});
        authorizationService.saveAuthorization(authorization);
    }


    /**
     * 授权访问资源名称是租户
     */
    @Test
    public void addUserAuthorization5() {
        Authorization authorization = authorizationService.createNewAuthorization(Authorization.AUTH_TYPE_GRANT);
        authorization.setUserId("peng4");
        authorization.setGroupId(null);
        authorization.setResource(Resources.TENANT);
        authorization.setResourceId("*");
        authorization.setPermissions(new Permissions[]{Permissions.READ, Permissions.CREATE, Permissions.UPDATE, Permissions.DELETE});
        authorizationService.saveAuthorization(authorization);
    }

    /**
     * 授权访问资源名称是用户与组关系
     */
    @Test
    public void addUserAuthorization6() {
        Authorization authorization = authorizationService.createNewAuthorization(Authorization.AUTH_TYPE_GRANT);
        authorization.setUserId("peng4");
        authorization.setGroupId(null);
        authorization.setResource(Resources.GROUP_MEMBERSHIP);
        authorization.setResourceId("*");
        authorization.setPermissions(new Permissions[]{Permissions.READ, Permissions.CREATE, Permissions.UPDATE, Permissions.DELETE});
        authorizationService.saveAuthorization(authorization);
    }

    /**
     * 授权访问资源名称是租户添加用户、组权限
     */
    @Test
    public void addUserAuthorization7() {
        Authorization authorization = authorizationService.createNewAuthorization(Authorization.AUTH_TYPE_GRANT);
        authorization.setUserId("peng4");
        authorization.setGroupId(null);
        authorization.setResource(Resources.TENANT_MEMBERSHIP);
        authorization.setResourceId("*");
        authorization.setPermissions(new Permissions[]{Permissions.READ, Permissions.CREATE, Permissions.UPDATE, Permissions.DELETE});
        authorizationService.saveAuthorization(authorization);
    }

    /**
     * 授权用户的授权操作
     */
    @Test
    public void addUserAuthorization8() {
        Authorization authorization = authorizationService.createNewAuthorization(Authorization.AUTH_TYPE_GRANT);
        authorization.setUserId("peng4");
        authorization.setGroupId(null);
        authorization.setResource(Resources.AUTHORIZATION);
        authorization.setResourceId("*");
        authorization.setPermissions(new Permissions[]{Permissions.READ, Permissions.CREATE, Permissions.UPDATE, Permissions.DELETE});
        authorizationService.saveAuthorization(authorization);
    }


    /**
     * 授权cockpit模块-流程定义
     */
    @Test
    public void addUserAuthorization9() {
        Authorization authorization = authorizationService.createNewAuthorization(Authorization.AUTH_TYPE_GRANT);
        authorization.setUserId("peng4");
        authorization.setGroupId(null);
        authorization.setResource(Resources.PROCESS_DEFINITION);
        authorization.setResourceId("*");
        authorization.setPermissions(new Permissions[]{Permissions.ALL});
        authorizationService.saveAuthorization(authorization);
    }

    /**
     * 授权cockpit模块-决策表
     */
    @Test
    public void addUserAuthorization10() {
        Authorization authorization = authorizationService.createNewAuthorization(Authorization.AUTH_TYPE_GRANT);
        authorization.setUserId("peng4");
        authorization.setGroupId(null);
        authorization.setResource(Resources.DECISION_DEFINITION);
        authorization.setResourceId("*");
        authorization.setPermissions(new Permissions[]{Permissions.ALL});
        authorizationService.saveAuthorization(authorization);
    }

    /**
     * 授权cockpit模块-用户任务
     */
    @Test
    public void addUserAuthorization11() {
        Authorization authorization = authorizationService.createNewAuthorization(Authorization.AUTH_TYPE_GRANT);
        authorization.setUserId("peng4");
        authorization.setGroupId(null);
        authorization.setResource(Resources.TASK);
        authorization.setResourceId("*");
        authorization.setPermissions(new Permissions[]{Permissions.ALL});
        authorizationService.saveAuthorization(authorization);
    }


    /**
     * 授权cockpit模块-流程部署
     */
    @Test
    public void addUserAuthorization12() {
        Authorization authorization = authorizationService.createNewAuthorization(Authorization.AUTH_TYPE_GRANT);
        authorization.setUserId("peng4");
        authorization.setGroupId(null);
        authorization.setResource(Resources.DEPLOYMENT);
        authorization.setResourceId("*");
        authorization.setPermissions(new Permissions[]{Permissions.ALL});
        authorizationService.saveAuthorization(authorization);
    }


    @Test
    public void addUserAuthorization13() {
        Authorization authorization = authorizationService.createNewAuthorization(Authorization.AUTH_TYPE_GRANT);
        authorization.setUserId("peng4");
        authorization.setGroupId(null);
        authorization.setResource(Resources.BATCH);
        authorization.setResourceId("*");
        authorization.setPermissions(new Permissions[]{Permissions.ALL});
        authorizationService.saveAuthorization(authorization);
    }

    /**
     * 授权cockpit模块-授权流程实例操作
     */
    @Test
    public void addUserAuthorization14() {
        Authorization authorization = authorizationService.createNewAuthorization(Authorization.AUTH_TYPE_GRANT);
        authorization.setUserId("peng4");
        authorization.setGroupId(null);
        authorization.setResource(Resources.PROCESS_INSTANCE);
        authorization.setResourceId("*");
        authorization.setPermissions(new Permissions[]{Permissions.ALL});
        authorizationService.saveAuthorization(authorization);
    }


    /**
     * 授权tasklist模块-授权流程实例操作
     */
    @Test
    public void addUserAuthorization15() {
        Authorization authorization = authorizationService.createNewAuthorization(Authorization.AUTH_TYPE_GRANT);
        authorization.setUserId("peng4");
        authorization.setGroupId(null);
        authorization.setResource(Resources.FILTER);
        authorization.setResourceId("*");
        authorization.setPermissions(new Permissions[]{Permissions.ALL});
        authorizationService.saveAuthorization(authorization);
    }


    /**
     * 授权cmd命令类控制
     */
    @Test
    public void setAuthorizationEnabledFalse() {

        //关闭权限校验
        processEngineConfiguration.setAuthorizationEnabled(false);

        //当前用户身份
        identityService.setAuthenticatedUserId("peng");

        Authorization authorization = authorizationService.createNewAuthorization(Authorization.AUTH_TYPE_GRANT);
        authorization.setGroupId(null);
        authorization.setUserId("peng_c");
        authorization.setResource(Resources.USER);
        authorization.setResourceId("*");
        authorization.setPermissions(new Permissions[]{Permissions.READ});
        authorizationService.saveAuthorization(authorization);

        Map<String, String> properties = managementService.getProperties();
        System.out.println(properties);
    }


    /**
     * 授权cmd命令类控制
     */
    @Test
    public void setAuthorizationEnabledTrue() {

        //开启权限校验
        processEngineConfiguration.setAuthorizationEnabled(true);

        //当前用户身份
        identityService.setAuthenticatedUserId("peng");

        Authorization authorization = authorizationService.createNewAuthorization(Authorization.AUTH_TYPE_GRANT);
        authorization.setGroupId(null);
        authorization.setUserId("peng_d");
        authorization.setResource(Resources.USER);
        authorization.setResourceId("*");
        authorization.setPermissions(new Permissions[]{Permissions.READ});
        authorizationService.saveAuthorization(authorization);

        Map<String, String> properties = managementService.getProperties();
        System.out.println(properties);
    }

}
