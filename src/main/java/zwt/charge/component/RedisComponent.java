package zwt.charge.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.hash.HashMapper;
import org.springframework.data.redis.hash.ObjectHashMapper;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

/**
 * @Author: lilongzhou
 * @Description:
 * @Date: Created in 下午2:43 2018/12/25
 */
@Component
@Slf4j
public class RedisComponent {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * key存储数据string（hash-string结构）
     * @param key 客户端id即设备alias
     * @param keyName 字段名
     * @param value 通过数据对象计算出来的唯一值
     */
    public void writeHash(String key, String keyName, String value) {
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new StringRedisSerializer());
        hashOperations.put(key, keyName, String.valueOf(value));
    }

    /**
     * 读取key中的数据（hash-string结构）
     * @param key 客户端id即设备alias
     * @param keyName 字段名
     * @return 通过数据对象计算出来的唯一值
     */
    public String loadHash(String key, String keyName) {
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new StringRedisSerializer());
        String value = null;
        if (hashOperations.hasKey(key, keyName)) {
            value = hashOperations.get(key, keyName);
        }
        return value;
    }

    /**
     * key存储数据string（hash-string结构）
     * @param key key
     * @param keyName hashKey
     * @param value value
     */
    public void writeHashLong(String key, String keyName, long value) {
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new StringRedisSerializer());
        hashOperations.put(key, keyName, String.valueOf(value));
    }

    /**
     * 读取key中的数据（hash-string结构）
     * @param key key
     * @param keyName hashKey
     * @return value
     */
    public Long loadHashLong(String key, String keyName) {
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new StringRedisSerializer());
        Long value = null;
        if (hashOperations.hasKey(key, keyName)) {
            value = Long.valueOf(hashOperations.get(key, keyName));
        }
        return value;
    }

    /**
     * 根据key获取数据量
     * @param key 客户端id即设备alias
     * @return 数量大小
     */
    public long getAmountByKey(String key) {
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new StringRedisSerializer());
        return hashOperations.size(key);
    }

    public Set<String> hashHKs(String key) {
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new StringRedisSerializer());
        return hashOperations.keys(key);
    }

    public List<String> hashValues(String key) {
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new StringRedisSerializer());
        return hashOperations.values(key);
    }

    /**
     * 为key中数据自加数据
     * @param key key
     * @param keyName hashKey
     * @param value value
     * @return
     */
    public Long hashIncrBy(String key, String keyName, long value) {
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new StringRedisSerializer());
        Long incrByValue = hashOperations.increment(key, keyName, value);
        return incrByValue;
    }

    /**
     * 删除key数据
     * @param key
     * @param hkey
     * @return
     */
    public Long clearByKey(String key, String hkey) {
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new StringRedisSerializer());
        return hashOperations.delete(key, hkey);
    }



    /**
     * 在key中存储map（hash-map机构）
     * @param key 通过数据对象计算出来的唯一值
     * @param cacheDataBean 数据对象
     */
    /*public void writeHashMap(String key, CacheDataBean cacheDataBean) {
        Map<byte[], byte[]> mapperedHash = mapper.toHash(cacheDataBean);
        HashOperations<String, byte[], byte[]> hashOperations = redisTemplate.opsForHash();
        hashOperations.putAll(key, mapperedHash);
    }*/

    /**
     * 读取key中的value值（hash-map机构）
     * @param key 通过数据对象计算出来的唯一值
     * @return 读取到的数据对象
     */
    /*public CacheDataBean loadHashMap(String key) {
        HashOperations<String, byte[], byte[]> hashOperations = redisTemplate.opsForHash();
        Map<byte[], byte[]> mapperedHash = hashOperations.entries(key);
        return (CacheDataBean) mapper.fromHash(mapperedHash);
    }*/




}
