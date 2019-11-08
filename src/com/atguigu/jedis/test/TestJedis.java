package com.atguigu.jedis.test;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

public class TestJedis {
	//redis-cli -h xxx -p xxx
	//报错：can not connect to server
	//检查服务端配置文件bind绑定的地址，不能绑定127.0.0.1
	@SuppressWarnings("resource")
	@Test
	public void test() {
		//创建一个客户端对象
		Jedis jedis = new Jedis("192.168.8.3", 6379, 6000);
		
		//使用客户端对象，发送命令，调用对应的方法
		String pong = jedis.ping();
		
		System.out.println(pong);
		
		//使用完后及时关闭jedis
		jedis.close();
	}
	@Test
	public void testSentinel() throws Exception {
		Set<String> set = new HashSet<>();
		// set中放的是哨兵的Ip和端口
		set.add("192.168.8.3:26379");
		GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
		JedisSentinelPool jedisSentinelPool = new JedisSentinelPool("mymaster", set, poolConfig, 60000);
		Jedis jedis = jedisSentinelPool.getResource();
		String value = jedis.get("k3");
		jedis.set("Jedis", "Jedis1");
		System.out.println(value);
	}


}
