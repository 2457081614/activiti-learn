package com.tiduyun.activiti.service;

import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.Process;
import org.activiti.engine.ActivitiEngineAgenda;
import org.activiti.engine.impl.history.HistoryManager;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityManager;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntityManager;
import org.activiti.engine.impl.util.ProcessDefinitionUtil;

/**
 *
 * 实现自定义跳转到某个节点
 */
public class CustomJumpCmd implements Command<Void> {

    private String taskId;

    private String targetNodeId;

    public CustomJumpCmd(String taskId, String targetNodeId) {
        this.taskId = taskId;
        this.targetNodeId = targetNodeId;
    }

    @Override
    public Void execute(CommandContext commandContext) {

        ActivitiEngineAgenda agenda = commandContext.getAgenda();

        TaskEntityManager taskEntityManager = commandContext.getTaskEntityManager();

        TaskEntity taskEntity = taskEntityManager.findById(taskId);

        String processDefinitionId = taskEntity.getProcessDefinitionId();

        //执行实例ID
        String executionId = taskEntity.getExecutionId();


        ExecutionEntityManager executionEntityManager = commandContext.getExecutionEntityManager();

        ExecutionEntity executionEntity = executionEntityManager.findById(executionId);

        Process process = ProcessDefinitionUtil.getProcess(processDefinitionId);

        // 目标节点
        FlowElement flowElement = process.getFlowElement(targetNodeId);
        if (flowElement == null) {
            // throw exception
        }
        HistoryManager historyManager = commandContext.getHistoryManager();

        // 标记当前活动结束
        historyManager.recordActivityEnd(executionEntity,"jump");

        executionEntity.setCurrentFlowElement(flowElement);

        agenda.planContinueProcessInCompensation(executionEntity);

        //删除当前任务
        taskEntityManager.delete(taskId);

        // 当前任务结束
        historyManager.recordTaskEnd(taskId,"jump");


        return null;
    }
}
