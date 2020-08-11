package com.tiduyun.activiti.serviceTask;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.Map;


public class CustomerServiceTask implements JavaDelegate {


    @Override
    public void execute(DelegateExecution execution) {

        Map<String, Object> variables = execution.getVariables();


        System.out.println("customer task execute....");
        System.out.println(execution.toString());
    }
}
