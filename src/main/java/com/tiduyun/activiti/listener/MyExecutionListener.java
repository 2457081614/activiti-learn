package com.tiduyun.activiti.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

public class MyExecutionListener implements ExecutionListener {


    @Override
    public void notify(DelegateExecution execution) {

        String eventName = execution.getEventName();
        if("start".equals(eventName))
        {
            System.out.println("start----------");
        }
        if("end".equals(eventName))
        {
            System.out.println("end----------");
        }
    }
}
