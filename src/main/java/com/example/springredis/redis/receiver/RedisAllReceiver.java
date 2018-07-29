package com.example.springredis.redis.receiver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author carzy
 * @date 2018/07/23
 */
@Component
public class RedisAllReceiver implements MessageListener {

    private final Logger logger = LoggerFactory.getLogger(RedisAllReceiver.class);

    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void onMessage(Message message, byte[] bytes) {
        byte[] body = message.getBody();
        byte[] channel = message.getChannel();
        Object itemValue = redisTemplate.getValueSerializer().deserialize(body);
        String topic = redisTemplate.getStringSerializer().deserialize(channel);
        logger.info("topic_*: {}, value: {}", topic, itemValue);
    }

}
