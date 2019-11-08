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
	//����can not connect to server
	//������������ļ�bind�󶨵ĵ�ַ�����ܰ�127.0.0.1
	@SuppressWarnings("resource")
	@Test
	public void test() {
		//����һ���ͻ��˶���
		Jedis jedis = new Jedis("192.168.8.3", 6379, 6000);
		
		//ʹ�ÿͻ��˶��󣬷���������ö�Ӧ�ķ���
		String pong = jedis.ping();
		
		System.out.println(pong);
		
		//ʹ�����ʱ�ر�jedis
		jedis.close();
	}
	@Test
	public void testSentinel() throws Exception {
		Set<String> set = new HashSet<>();
		// set�зŵ����ڱ���Ip�Ͷ˿�
		set.add("192.168.8.3:26379");
		GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
		JedisSentinelPool jedisSentinelPool = new JedisSentinelPool("mymaster", set, poolConfig, 60000);
		Jedis jedis = jedisSentinelPool.getResource();
		String value = jedis.get("k3");
		jedis.set("Jedis", "Jedis1");
		System.out.println(value);
	}


}
