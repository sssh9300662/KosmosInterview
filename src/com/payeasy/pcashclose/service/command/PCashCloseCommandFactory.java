package com.payeasy.pcashclose.service.command;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

import com.payeasy.pcashclose.service.exception.PCashCloseException;
import com.payeasy.pcashclose.service.util.ActType;

public class PCashCloseCommandFactory implements BeanFactoryAware {

    private static final Logger logger = Logger.getLogger(PCashCloseCommandFactory.class);
    
    private BeanFactory beanFactory;
    
    private Map<String, String> commandMappings;
    
    public PCashCloseCommand createCommand(ActType actType, Long pplNum) throws PCashCloseException {
        
        String beanName = this.commandMappings.get(actType.getActType());
        
        logger.info("Get pcashCloseCommand [" + beanName + "] " +
        		    "by pplNum [" + pplNum + "] and actType [" + actType + "]");
      
        if (beanName == null) {
            throw new PCashCloseException("Create pcashCloseCommand by actType [" + actType + "] fail !! " +
            		                      "Because it doesn't exist!!");
        }
        
        PCashCloseCommand pcashCloseCommand = (PCashCloseCommand) this.beanFactory.getBean(beanName);
        
        return pcashCloseCommand;
    }
    
    //=== Setter & getter =============================================================
    
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    public void setCommandMappings(Map<String, String> commandMappings) {
        this.commandMappings = commandMappings;
    }

    
}
