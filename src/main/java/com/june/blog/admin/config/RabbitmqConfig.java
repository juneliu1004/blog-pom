package com.june.blog.admin.config;



import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfig {

    @Bean
    public Queue queue(){
        return new Queue("msg1");
    }

    @Bean
    public Queue myQueue() {
        return new Queue("myQueue");
    }
    @Bean
    public Queue myQueue2() {
        return new Queue("myQueue2");
    }


    @Bean
    public TopicExchange exchange(){
        return new TopicExchange("topicExchange");
    }

    @Bean
    public Queue queue2(){
        return new Queue("topic.a");
    }


    @Bean
    public Queue queue3(){

        return new Queue("topic.b");
    }

    @Bean
    Binding bind2(Queue queue2,TopicExchange exchange){
        return BindingBuilder.bind(queue2).to(exchange).with("topic.a");
    }

    @Bean
    Binding bind3(Queue queue3,TopicExchange exchange){
        return BindingBuilder.bind(queue3).to(exchange).with("topic.#");
    }

    @Bean
    TopicExchange myTopicExchange(){
        return new TopicExchange("myTopicExchange");
    }

    @Bean
    Binding binding(Queue myQueue,TopicExchange myTopicExchange){
        return BindingBuilder.bind(myQueue).to(myTopicExchange).with("articleInfo");
    }
    @Bean
    Binding binding2(Queue myQueue2,TopicExchange myTopicExchange){
        return BindingBuilder.bind(myQueue2).to(myTopicExchange).with("articleInfo");
    }



}
