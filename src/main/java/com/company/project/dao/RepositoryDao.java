package com.company.project.dao;

import com.company.project.bean.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.UUID;

@Component
public class RepositoryDao {

    private final ReactiveRedisConnectionFactory redisFactory;

    private final ReactiveRedisOperations<String, Repository> redisOperations;

    @Autowired
    public RepositoryDao(ReactiveRedisConnectionFactory redisFactory) {
        this.redisFactory = redisFactory;
        this.redisOperations = buildRedisOperations(this.redisFactory);
    }

    public Repository insert(Repository repository) {
        // generate ID
        String id = UUID.randomUUID().toString();
        repository.setId(id);

        return save(repository);
    }

    public Repository update(Repository repository) {
        return save(repository);
    }

    public void delete(String id) {
        String key = buildKey(Repository.class, id);
        redisOperations.opsForValue().delete(key);
    }

    public Repository findById(String id) {
        Flux<Repository> repositoryFlux = findOnRedis(Repository.class, id);

        return null;
    }

    public List<Repository> findAll() {
        Flux<Repository> repositoryFlux = findOnRedis(Repository.class, "*");

        return null;
    }


    private ReactiveRedisOperations<String, Repository> buildRedisOperations(ReactiveRedisConnectionFactory factory) {
        Jackson2JsonRedisSerializer<Repository> serializer = new Jackson2JsonRedisSerializer<>(Repository.class);

        RedisSerializationContext.RedisSerializationContextBuilder<String, Repository> builder =
                RedisSerializationContext.newSerializationContext(new StringRedisSerializer());

        RedisSerializationContext<String, Repository> context = builder.value(serializer).build();

        return new ReactiveRedisTemplate<>(factory, context);
    }

    private Repository save(Repository repository) {
        String id = repository.getId();

        String key = buildKey(Repository.class, id);

        redisOperations.opsForValue().set(key, repository);

        return repository;
    }

    private <T> String buildKey(Class<T> clazz, String id) {
        StringBuilder key = new StringBuilder();
        key.append(clazz.getSimpleName())
                .append(":")
                .append(id);

        return key.toString();
    }

    //private <T> Flux<T> findOnRedis(Class<T> clazz, String id) {
    private Flux<Repository> findOnRedis(Class<Repository> clazz, String id) {
        String key = buildKey(clazz, id);

        return redisOperations.keys(key)
            .flatMap(redisOperations.opsForValue()::get);

    }
}
