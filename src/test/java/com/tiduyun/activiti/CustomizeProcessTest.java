package com.tiduyun.activiti;

import org.activiti.bpmn.model.*;
import org.activiti.bpmn.model.Process;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.Deployment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CustomizeProcessTest {


    @Autowired
    RepositoryService repositoryService;

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    ManagementService managementService;

    @Autowired
    IdentityService identityService;


    @Test
    public void deployTest() {
        BpmnModel bpmnModel = new BpmnModel();

        Process process = new Process();
        process.setId("news");
        process.setName("2222");
        StartEvent startEvent = new StartEvent();
        startEvent.setId("startEvent");
        process.addFlowElement(startEvent);

        ServiceTask serviceTask = new ServiceTask();
        serviceTask.setImplementationType("class");
        serviceTask.setImplementation("com.tiduyun.activiti.serviceTask.CustomerServiceTask");
        serviceTask.setId("serviceTask");
        process.addFlowElement(serviceTask);


        EndEvent endEvent = new EndEvent();
        endEvent.setId("endEvent");
        process.addFlowElement(endEvent);

        SequenceFlow sequenceFlow = new SequenceFlow();
        sequenceFlow.setId("s1");
        sequenceFlow.setSourceFlowElement(startEvent);
        sequenceFlow.setTargetFlowElement(serviceTask);
        sequenceFlow.setSourceRef("startEvent");
        sequenceFlow.setTargetRef("serviceTask");
        process.addFlowElement(sequenceFlow);

        SequenceFlow sequenceFlow2 = new SequenceFlow();
        sequenceFlow.setId("s2");
        sequenceFlow.setSourceFlowElement(serviceTask);
        sequenceFlow.setTargetFlowElement(endEvent);
        sequenceFlow2.setSourceRef("serviceTask");
        sequenceFlow2.setTargetRef("endEvent");
        process.addFlowElement(sequenceFlow2);

        bpmnModel.addProcess(process);

        Deployment deploy = repositoryService.createDeployment()
                .addBpmnModel("test1.bpmn", bpmnModel)
                .name("custome bpmn")
                .key("custome bpmn")
                .deploy();


    }


}
