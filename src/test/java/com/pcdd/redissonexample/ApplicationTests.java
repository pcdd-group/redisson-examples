package com.pcdd.redissonexample;

import org.junit.jupiter.api.Test;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class ApplicationTests {

    @Autowired
    RedissonClient redisson;

    /**
     * https://www.javadoc.io/doc/org.redisson/redisson/latest/org/redisson/api/RBloomFilter.html
     */
    @Test
    void bloomFilterTest() {
        // 预期插入数量
        int expectedInsertions  = 100000;
        // 误判率
        double falseProbability = 0.03;

        RBloomFilter<String> bloomFilter = redisson.getBloomFilter("sample");
        bloomFilter.tryInit(expectedInsertions , falseProbability);

        // 插入样本数据
        for (int i = 1; i <= expectedInsertions ; i++) {
            bloomFilter.add("sample" + i);
        }

        // 返回此实例所需的 Redis 内存中的 bits，误判率越小占用越大
        System.out.println("占用内存：" + bloomFilter.getSize() / 8 / 1024 + "KB");
        // 计算已添加到布隆过滤器的元素的概率数量
        System.out.println("已添加到布隆过滤器的元素的概率数量：" + bloomFilter.count());
        // 检查元素是否存在，存在实际可能不存在，不存在则一定不存在
        System.out.println(bloomFilter.contains("sample100000"));
    }

}
