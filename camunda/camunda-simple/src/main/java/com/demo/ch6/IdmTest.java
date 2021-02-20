package com.demo.ch6;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngineConfiguration;
import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.identity.GroupQuery;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.identity.UserQuery;
import org.camunda.bpm.engine.impl.persistence.entity.GroupEntity;
import org.camunda.bpm.engine.impl.persistence.entity.TenantEntity;
import org.camunda.bpm.engine.impl.persistence.entity.UserEntity;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhoupeng
 */
public class IdmTest {


    public IdentityService identityService;

    @Before
    public void init() {
        ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("com.demo/ch6/camunda.cfg.xml");
        ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();
        identityService = processEngine.getIdentityService();
        System.out.println(identityService);
    }


    @Test
    public void getIdentityService() {

    }

    /**
     * insert into ACT_ID_USER (ID_, FIRST_, LAST_, EMAIL_, PWD_, SALT_, REV_) values ( ?, ?, ?, ?, ?, ?, 1 )
     * <p>
     * peng(String), 周(String), 鹏(String), 123@qq.com(String), 1(String), 1(String)
     */
    @Test
    public void saveUser() {
        UserEntity user = new UserEntity();
        user.setId("peng");
        user.setEmail("123@qq.com");
        user.setFirstName("周");
        user.setLastName("鹏");
        user.setDbPassword("1");
        user.setSalt("1");
        identityService.saveUser(user);
    }

    /**
     * insert into ACT_ID_USER (ID_, FIRST_, LAST_, EMAIL_, PWD_, SALT_, REV_) values ( ?, ?, ?, ?, ?, ?, 1 )
     * peng2(String), 周2(String), 鹏2(String), 123@qq.com(String), {SHA-512}3+H52awaeQkouH/J94W6314xfD9wHCERyUfVRBovtemLsIAco29UXXKg902ISWMs7EgLov4WB0oRc8vwk5REQA==(String), iIKLPiLUIcIN1rfz9gEC3A==(String)
     */
    @Test
    public void saveUser2() {
        UserEntity user = new UserEntity();
        user.setId("peng4");
        user.setEmail("123@qq.com");
        user.setFirstName("周2");
        user.setLastName("鹏2");
        user.setPassword("123");
        user.setSalt("1");
        identityService.saveUser(user);
    }


    /**
     * select distinct RES.* from ACT_ID_USER RES order by RES.ID_ asc LIMIT ? OFFSET ?
     * 2147483647(Integer), 0(Integer)
     */
    @Test
    public void createUserQuery() {
        UserQuery userQuery = identityService.createUserQuery();

        List<User> userList = userQuery
//                .userIdIn("peng1", "peng2")
                .userEmailLike("%qq%")
                .list();
        for (User user : userList) {
            System.out.println("##################");
            System.out.println(user.toString());
            System.out.println("##################");
        }

    }


    /**
     * select distinct RES.* from ACT_ID_USER RES order by RES.ID_ asc LIMIT ? OFFSET ?
     * <p>
     * 0(Integer), 3(Integer)
     */
    @Test
    public void createUserQueryPage() {
        UserQuery userQuery = identityService.createUserQuery();
        int max = 2;
        int firstResult = max * (2 - 1);
        List<User> userList = userQuery
                .listPage(3, firstResult);
        for (User user : userList) {
            System.out.println("##################");
            System.out.println(user.toString());
            System.out.println("##################");
        }
    }


    /**
     * delete from ACT_ID_MEMBERSHIP where USER_ID_ = ?
     * peng33(String)
     * delete from ACT_ID_TENANT_MEMBER where USER_ID_ = ?
     * peng33(String)
     * delete from ACT_ID_USER where ID_ = ? and REV_ = ?
     * peng33(String), 1(Integer)
     */
    @Test
    public void deleteUser() {
        String userId = "peng33";
        identityService.deleteUser(userId);
    }


    /**
     * insert into ACT_ID_GROUP (ID_, NAME_, TYPE_, REV_) values ( ?, ?, ?, 1 )
     * <p>
     * dep(String), 项目经理(String), workflow(String)
     */
    @Test
    public void saveGroup() {
        GroupEntity groupEntity = new GroupEntity();
        groupEntity.setId("dep");
        groupEntity.setName("项目经理");
        groupEntity.setType("workflow");
        identityService.saveGroup(groupEntity);
    }


    /**
     * select distinct RES.* from ACT_ID_GROUP RES order by RES.ID_ asc LIMIT ? OFFSET ?
     * 2147483647(Integer), 0(Integer)
     */
    @Test
    public void createGroupQuery() {

        GroupQuery groupQuery = identityService.createGroupQuery();
        List<Group> groupList = groupQuery.list();

        for (Group group : groupList) {
            System.out.println("###############");
            System.out.println(group.toString());
            System.out.println("###############");
        }
    }


    /**
     * insert into ACT_ID_MEMBERSHIP (USER_ID_, GROUP_ID_) values ( ?, ? )
     * peng(String), dep(String)
     */
    @Test
    public void createMemberShip() {
        String userId = "peng2";
        String groupId = "dep";
        identityService.createMembership(userId, groupId);
    }


    /**
     * delete from ACT_ID_MEMBERSHIP where GROUP_ID_ = ?
     * dep(String)
     * delete from ACT_ID_TENANT_MEMBER where GROUP_ID_ = ?
     * dep(String)
     * delete from ACT_ID_GROUP where ID_ = ? and REV_ = ?
     * dep(String), 1(Integer)
     */
    @Test
    public void deleteGroup() {
        String groupId = "dep";
        identityService.deleteGroup(groupId);
    }


    /**
     * insert into ACT_ID_TENANT (ID_, NAME_, REV_) values ( ?, ?, 1 )
     * crm(String), 客户关系管理系统(String)
     */
    @Test
    public void saveTenant() {
        TenantEntity tenantEntity = new TenantEntity();
        tenantEntity.setId("crm");
        tenantEntity.setName("客户关系管理系统");
        identityService.saveTenant(tenantEntity);
    }


    /***
     *
     *  insert into ACT_ID_TENANT_MEMBER (ID_, TENANT_ID_, USER_ID_, GROUP_ID_) values ( ?, ?, ?, ? )
     *  8401(String), crm(String), peng(String), null
     *
     */
    @Test
    public void createTenantUserMemberShip() {
        String tenantId = "crm";
        String userId = "peng";
        identityService.createTenantUserMembership(tenantId, userId);
    }

    /***
     *
     *   insert into ACT_ID_TENANT_MEMBER (ID_, TENANT_ID_, USER_ID_, GROUP_ID_) values ( ?, ?, ?, ? )
     *   8501(String), crm(String), null, dep(String)
     *
     */
    @Test
    public void createTenantGroupMemberShip() {
        String tenantId = "crm";
        String groupId = "dep";
        identityService.createTenantGroupMembership(tenantId, groupId);
    }


    /**
     * select distinct RES.* from ACT_ID_USER RES inner join ACT_ID_TENANT_MEMBER TM on RES.ID_ = TM.USER_ID_ WHERE TM.TENANT_ID_ = ? order by RES.ID_ asc LIMIT ? OFFSET ?
     * crm(String), 2147483647(Integer), 0(Integer)
     */
    @Test
    public void createUserQueryMemberOfTenant() {
        String tenantId = "crm";
        UserQuery userQuery = identityService.createUserQuery();
        List<User> userList = userQuery.memberOfTenant(tenantId).list();
        for (User user : userList) {
            System.out.println("##################");
            System.out.println(user.toString());
            System.out.println("##################");
        }
    }

    /**
     * select distinct RES.* from ACT_ID_GROUP RES inner join ACT_ID_TENANT_MEMBER TM on RES.ID_ = TM.GROUP_ID_ WHERE TM.TENANT_ID_ = ? order by RES.ID_ asc LIMIT ? OFFSET ?
     * crm(String), 2147483647(Integer), 0(Integer)
     */
    @Test
    public void createGroupQueryMemberOfTenant() {
        String tenantId = "crm";
        GroupQuery groupQuery = identityService.createGroupQuery();
        List<Group> groupList = groupQuery.memberOfTenant(tenantId).list();
        for (Group group : groupList) {
            System.out.println("##################");
            System.out.println(group.toString());
            System.out.println("##################");
        }
    }

    /**
     * insert into ACT_ID_INFO (ID_, USER_ID_, TYPE_, KEY_, VALUE_, PASSWORD_, PARENT_ID_, REV_) values ( ?, ?, ?, ?, ?, ?, ?, 1 )
     * 8601(String), peng2(String), account(String), pengaccountName(String), pengaccountUserName(String), java.io.ByteArrayInputStream@5f2f577(ByteArrayInputStream), null
     * 8602(String), null, null, a(String), a(String), null, 8601(String)
     * 8603(String), null, null, b(String), b(String), null, 8601(String)
     */
    @Test
    public void setUserAccount() {
        String userId = "peng2";
        String userPassword = "1";
        String accountName = "pengaccountName";
        String accountUserName = "pengaccountUserName";
        String accountPassword = "pengaccountPassword";
        Map<String, String> accountDetails = new HashMap<String, String>();
        accountDetails.put("a", "a");
        accountDetails.put("b", "b");
        identityService.setUserAccount(userId, userPassword, accountName, accountUserName, accountPassword, accountDetails);
    }


    /**
     * select KEY_ from ACT_ID_INFO where USER_ID_ = ? and TYPE_ = ? and PARENT_ID_ is null
     * peng2(String), account(String)
     */
    @Test
    public void getUserAccountNames() {
        String userId = "peng2";
        List<String> userAccountNames = identityService.getUserAccountNames(userId);
        System.out.println(userAccountNames);
    }


    /**
     * insert into ACT_ID_INFO (ID_, USER_ID_, TYPE_, KEY_, VALUE_, PASSWORD_, PARENT_ID_, REV_) values ( ?, ?, ?, ?, ?, ?, ?, 1 )
     * 8701(String), peng2(String), userinfo(String), c(String), c(String), null, null
     */
    @Test
    public void setUserInfo() {
        String userId = "peng2";
        identityService.setUserInfo(userId, "c", "c");
    }


    /**
     * delete from ACT_ID_INFO where ID_ = ? and REV_ = ?
     * 8701(String), 1(Integer)
     */
    @Test
    public void deleteUserInfo() {
        String userId = "peng2";
        identityService.deleteUserInfo(userId, "c");
    }


}
