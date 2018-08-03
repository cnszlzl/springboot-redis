package com.example.springredis;

import com.example.springredis.redis.receiver.RedisAllReceiver;
import com.example.springredis.redis.receiver.RedisUser2Receiver;
import com.example.springredis.redis.receiver.RedisUserReceiver;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;


/**
 * @author carzy
 * @date 2018/07/18
 */
@Configuration
@EnableRedisRepositories
public class RedisConfig {

    @Bean
    @ConditionalOnMissingBean(name = {"redisTemplate"})
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnection) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnection);
        redisTemplate.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        return redisTemplate;
    }


    @Bean
    public RedisMessageListenerContainer container(RedisConnectionFactory redisConnection,
                                            RedisUserReceiver redisUserReceiver, RedisUser2Receiver redisUser2Receiver, RedisAllReceiver allReceiver) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnection);
        //  订阅了一个通道
        container.addMessageListener(redisUserReceiver, new PatternTopic(RedisChannel.USER_CHANNEL));
        container.addMessageListener(redisUser2Receiver, new PatternTopic(RedisChannel.USER2_CHANNEL));

        // 匹配多个  channel
        container.addMessageListener(allReceiver, new ChannelTopic(RedisChannel.USER_CHANNEL));
        return container;
    }

    public static class RedisChannel {
        public static final String USER_CHANNEL = "topic_user";
        public static final String USER2_CHANNEL = "topic_user2";
    }

}






