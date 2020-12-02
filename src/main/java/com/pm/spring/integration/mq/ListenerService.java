package com.pm.spring.integration.mq;

import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Service;

@Service
public class ListenerService {

    @ServiceActivator(inputChannel = "consumingChannel", outputChannel = "routingChannel")
    public Object handleMessage(String message) {
        System.out.println(message);
        try{
            return Integer.valueOf(message);
        }catch(Exception e){}
        return message + "from consumer one";
    }
    
    @ServiceActivator(inputChannel = "stringChannel")
    public void handleReturnMessage(String message) {
        System.out.println("return message" + message); 
        throw new RuntimeException("test exception");       
    }

    @ServiceActivator(inputChannel = "integerChannel")
    public void handleReturnIntegerMessage(Integer message) {
        System.out.println("return message" + message);   
    }

    @ServiceActivator(inputChannel = "terrorChannel")
    public void handleError(String message) {
        System.out.println("error message" + message);   
    }
    
}
