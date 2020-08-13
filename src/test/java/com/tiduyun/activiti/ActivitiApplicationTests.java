package com.tiduyun.activiti;

import com.tiduyun.activiti.listener.MyEventListener;
import com.tiduyun.activiti.listener.MyExecutionListener;
import com.tiduyun.activiti.service.CustomJumpCmd;
import org.activiti.engine.*;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.identity.User;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentQuery;
import org.activiti.engine.task.Task;
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

    @Autowired
    TaskService taskService;

    @Autowired
    FormService formService;


    @Test
    public void deploy() {
        repositoryService.createDeployment().category("categoty").addClasspathResource("processes/process-1.bpmn").
                name("myProcess_1").key("myProcess_1").tenantId("1").deploy();

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
        variables.put("a", "6");
        variables.put("b", "test2");

        runtimeService.addEventListener(new MyEventListener(), ActivitiEventType.ACTIVITY_STARTED);
        runtimeService.startProcessInstanceByKeyAndTenantId("news", variables,"1");
        TimeUnit.SECONDS.sleep(50);
    }


    @Test
    public void testJump() {

        String taskId = "5005";

        String targetNodeId = "_3";
        managementService.executeCommand(new CustomJumpCmd(taskId, targetNodeId));
    }

    /**
     * 完成任务
     */
    @Test
    public void completeTask()
    {
        List<Task> list = taskService.createTaskQuery().list();
        Task task = list.get(0);

        //申领任务 将任务指定给某个人处理
        taskService.claim(task.getId(),"you");

        taskService.complete(task.getId());
    }

    /**
     * 挂起
     */
    @Test
    public void suspendProcess()
    {
        repositoryService.suspendProcessDefinitionByKey("exclusive-1","1");
    }




    @Test
    public void  getFormData()
    {
        String taskId ="2507";
        TaskFormData taskFormData = formService.getTaskFormData(taskId);


        Map<String, String> properties  = new HashMap<>();
        properties.put("name","xw");
        formService.submitTaskFormData(taskId,properties);

        List<FormProperty> formProperties = taskFormData.getFormProperties();
        System.out.println(formProperties.toString());

       /* Map<String, Object> variables = taskService.getVariables(taskId);
        System.out.println(variables.toString());*/
    }

}
