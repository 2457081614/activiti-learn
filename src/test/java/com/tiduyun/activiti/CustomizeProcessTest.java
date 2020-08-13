package com.tiduyun.activiti;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.*;
import org.activiti.bpmn.model.Process;
import org.activiti.engine.*;
import org.activiti.engine.form.FormType;
import org.activiti.engine.impl.form.StringFormType;
import org.activiti.engine.repository.Deployment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    FormService formService;


    @Test
    public void deployTest() {
        BpmnModel bpmnModel = new BpmnModel();

        Process process = new Process();
        process.setId("news");
        process.setName("2222");
        StartEvent startEvent = new StartEvent();
        startEvent.setId("startEvent");
        process.addFlowElement(startEvent);

      /*  ServiceTask serviceTask = new ServiceTask();
        serviceTask.setImplementationType("class");
        serviceTask.setImplementation("com.tiduyun.activiti.serviceTask.CustomerServiceTask");
        serviceTask.setId("serviceTask");*/

        UserTask userTask = new UserTask();
        userTask.setId("userTask");
        userTask.setFormKey("formKey");
        List<FormProperty> formProperties = new ArrayList<>();
        FormProperty formProperty = new FormProperty();
        formProperty.setId("1");
        formProperty.setType("string");
        formProperty.setWriteable(true);
        formProperty.setReadable(true);
        formProperty.setName("name");
        formProperties.add(formProperty);
        userTask.setFormProperties(formProperties);



        process.addFlowElement(userTask);


        EndEvent endEvent = new EndEvent();
        endEvent.setId("endEvent");
        process.addFlowElement(endEvent);

        SequenceFlow sequenceFlow = new SequenceFlow();
        sequenceFlow.setId("s1");
        sequenceFlow.setSourceFlowElement(startEvent);
        sequenceFlow.setTargetFlowElement(userTask);
        sequenceFlow.setSourceRef("startEvent");
        sequenceFlow.setTargetRef("userTask");
        process.addFlowElement(sequenceFlow);

        SequenceFlow sequenceFlow2 = new SequenceFlow();
        sequenceFlow.setId("s2");
        sequenceFlow.setSourceFlowElement(userTask);
        sequenceFlow.setTargetFlowElement(endEvent);
        sequenceFlow2.setSourceRef("userTask");
        sequenceFlow2.setTargetRef("endEvent");
        process.addFlowElement(sequenceFlow2);

        bpmnModel.addProcess(process);

        Deployment deploy = repositoryService.createDeployment()
                .addBpmnModel("test1.bpmn", bpmnModel)
                .tenantId("1")
                .deploy();

        BpmnXMLConverter bpmnXMLConverter  = new BpmnXMLConverter();
        byte[] bytes = bpmnXMLConverter.convertToXML(bpmnModel, "UTF-8");
        System.out.println(bpmnXMLConverter.toString());

    }


}
