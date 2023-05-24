package com.avertra.externalworker;

import org.camunda.bpm.client.spring.annotation.ExternalTaskSubscription;
import org.camunda.bpm.client.task.ExternalTaskHandler;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExternalWorker {
    protected static Logger LOGGER = LoggerFactory.getLogger(ExternalWorker.class);
//    private String workerId;
//
//    public ExternalWorker(ClientProperties properties) {
//        workerId = properties.getWorkerId();
//    }

    @ExternalTaskSubscription("sayMyName")
    @Bean
    public ExternalTaskHandler sayMyName() {
        return (externalTask, externalTaskService) -> {
            LOGGER.info("Printing name...");
            // retrieve a variable from the Process Engine
            int name = externalTask.getVariable("myName");
            // create an object typed variable
            ObjectValue newNameObject = Variables.objectValue("Alpha").create();

            // complete the external task
            externalTaskService.complete(externalTask, Variables.putValueTyped("newName", newNameObject));

            LOGGER.info("The External Task " + externalTask.getId() + " has been checked!");
        };
    }
}
