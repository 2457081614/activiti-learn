package com.tiduyun.activiti;

import com.tiduyun.activiti.service.CustomJumpCmd;
import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest()
class ActivitiApplicationTests {

    @Autowired
    RepositoryService repositoryService;

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    ManagementService managementService;


    @Test
    public void deploy() {
        repositoryService.createDeployment().addClasspathResource("processes/process-1.bpmn").deploy();

    }


    /**
     * 开启一个流程
     */
    @Test
    public void start()
    {
        runtimeService.startProcessInstanceByKey("myProcess_1");
    }



    @Test
    public void testJump()
    {

        String taskId = "5005";
        String targetNodeId = "_3";
        managementService.executeCommand(new CustomJumpCmd(taskId,targetNodeId));
    }

}
