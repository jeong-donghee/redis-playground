package io.github.redisplayground;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/redis")
@RequiredArgsConstructor
public class RedisController {

    private final StringRedisTemplate redisTemplate;

    @GetMapping
    public List<RedisKeyValueDto> getRedisKeyValue() {
        Set<String> keys = redisTemplate.keys("*");
        return keys.stream()
                .map(key -> RedisKeyValueDto.builder()
                        .key(key)
                        .value(redisTemplate.opsForValue().get(key))
                        .build()
                ).toList();
    }

    @PostMapping
    public void insertRedisKeyValue(@RequestBody RedisKeyValueDto redisKeyValueDto) {
        redisTemplate.opsForValue().set(redisKeyValueDto.getKey(), redisKeyValueDto.getValue());
    }
}
