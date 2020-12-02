package com.pm.spring.integration.mq;

import javax.jms.ConnectionFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.jms.dsl.Jms;
import org.springframework.integration.router.PayloadTypeRouter;

@Configuration
public class JmsConfig {

    // @Bean
    // public DirectChannel consumingChannel() {
    //     return new DirectChannel();
    // }

    // @Bean
    // public DirectChannel returnChannel() {
    //     return new DirectChannel();
    // }

    // @Bean
    // public ChannelPublishingJmsMessageListener channelPublishingJmsMessageListener() {
    //     return new ChannelPublishingJmsMessageListener();
    // }

    // @Bean
    // public JmsMessageDrivenEndpoint jmsMessageDrivenEndpoint(ConnectionFactory connectionFactory) {
    //     JmsMessageDrivenEndpoint endpoint = new JmsMessageDrivenEndpoint(
    //             simpleMessageListenerContainer(connectionFactory), channelPublishingJmsMessageListener());
    //     endpoint.setOutputChannel(consumingChannel());
    //     return endpoint;
    // }

    // @Bean
    // public SimpleMessageListenerContainer simpleMessageListenerContainer(ConnectionFactory connectionFactory) {
    //     SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
    //     container.setConnectionFactory(connectionFactory);
    //     container.setDestinationName("RATE.MEDSUPREQUEST.QL");
    //     return container;
    // }

    @Bean
    public IntegrationFlow ibmInbound(ConnectionFactory connectionFactory) {
        return IntegrationFlows.from(Jms.messageDrivenChannelAdapter(connectionFactory).destination("RATE.MEDSUPREQUEST.QL").errorChannel("terrorChannel"))
                .channel("consumingChannel").get();
    }

    @Bean
public IntegrationFlow routerFlow1() {
    return IntegrationFlows.from("routingChannel")
            .route(router())
            .get();
}

public PayloadTypeRouter router() {
    PayloadTypeRouter router = new PayloadTypeRouter();
    router.setChannelMapping(String.class.getName(), "stringChannel");
    router.setChannelMapping(Integer.class.getName(), "integerChannel");
    return router;
}
}
