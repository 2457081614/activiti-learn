package com.tiduyun.activiti.serviceTask;

import com.tiduyun.activiti.service.AutoInjectService;
import com.tiduyun.activiti.utils.SpringUtil;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;



public class CustomerServiceTask implements JavaDelegate {


    private AutoInjectService autoInjectService;


    @Override
    public void execute(DelegateExecution execution) {
        autoInjectService = SpringUtil.getBean(AutoInjectService.class);

        autoInjectService.say();

        Map<String, Object> variables = execution.getVariables();




        System.out.println("customer task execute....");
        System.out.println(execution.toString());
    }
}
