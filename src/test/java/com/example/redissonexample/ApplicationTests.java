package com.example.redissonexample;

import org.junit.jupiter.api.Test;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApplicationTests {

    @Autowired
    RedissonClient redisson;

    @Test
    void bloomFilterTest() {
        RBloomFilter<Integer> bloomFilter = redisson.getBloomFilter("sample");
        // 预期插入数量，误判率 正确率：1 - p2
        bloomFilter.tryInit(100000, 0.03);

        for (int i = 0; i < 1000; i++) {
            bloomFilter.add(i);
        }

        // 返回此实例所需的 Redis 内存中的 bits
        System.out.println("占用内存：" + bloomFilter.getSize() / 8 / 1024 + "MB");
        // 计算已添加到布隆过滤器的元素的概率数量
        System.out.println("已添加到布隆过滤器的元素的概率数量：" + bloomFilter.count());
        // 检查元素是否存在
        System.out.println(bloomFilter.contains(666));
    }

}
