package com.tiduyun.activiti;

import com.tiduyun.activiti.service.CustomJumpCmd;
import org.activiti.engine.*;
import org.activiti.engine.identity.User;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentQuery;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@SpringBootTest()
class ActivitiApplicationTests {

    @Autowired
    RepositoryService repositoryService;

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    ManagementService managementService;

    @Autowired
    IdentityService identityService;


    @Test
    public void deploy() {
        repositoryService.createDeployment().addClasspathResource("processes/parallel-1.bpmn").
                name("parallel-1").key("parallel-1").tenantId("1").deploy();

    }

    @Test
    public void queryDeploy() {
        List<Deployment> deployments = repositoryService.createDeploymentQuery().deploymentTenantId("dsdf").list();
        System.out.println(deployments.toString());
    }


    /**
     * 开启一个流程
     */
    @Test
    public void start() throws InterruptedException {
        Map<String, Object> variables = new HashMap<>();
        variables.put("a", "test1");
        variables.put("b", "test2");


        runtimeService.startProcessInstanceByKey("news", variables);
        TimeUnit.SECONDS.sleep(50);
    }


    @Test
    public void testJump() {

        String taskId = "5005";
        String targetNodeId = "_3";
        managementService.executeCommand(new CustomJumpCmd(taskId, targetNodeId));
    }


}
