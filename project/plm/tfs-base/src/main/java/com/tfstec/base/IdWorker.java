/**   
 * @Project: tfs-base 
 * @Title: IdWorker.java 
 * @Package com.tfstec.base 
 * @Description: 生成唯一编号 
 * @author lx 
 * @date 2016年6月29日 下午11:14:58 
 * @Copyright: 2016 年 前海阿拉海钜科技. All rights reserved  
 * @version V1.0   
 */
package com.tfstec.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
 * @ClassName IdWorker  
 * @Description 出自于twitter 开放的一个id生成器，采用时间+机器号+内存值 组装的id，每秒大概产生26万个不同的id 
 *  64位ID (42(毫秒)+5(机器ID)+5(业务编码)+12(重复累加))  
 * @author lx 
 * @date 2016年6月29日  
 *   
 */
public class IdWorker {
	protected static final Logger LOG = LoggerFactory.getLogger(IdWorker.class);

	private long workerId;
	private long datacenterId;
	private long sequence = 0L;

	private long twepoch = 1288834974657L;

	// 机器标识位数
	private long workerIdBits = 5L;
	// 数据中心标识位数
	private long datacenterIdBits = 5L;
	// 机器ID最大值
	private long maxWorkerId = -1L ^ (-1L << workerIdBits);
	// 数据中心ID最大值
	private long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);
	// 毫秒内自增位
	private long sequenceBits = 12L;
	// 机器ID偏左移12位
	private long workerIdShift = sequenceBits;
	// 数据中心ID左移17位
	private long datacenterIdShift = sequenceBits + workerIdBits;
	// 时间毫秒左移22位
	private long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;
	private long sequenceMask = -1L ^ (-1L << sequenceBits);

	private long lastTimestamp = -1L;

	public IdWorker(long workerId, long datacenterId) {
		// sanity check for workerId
		if (workerId > maxWorkerId || workerId < 0) {
			throw new IllegalArgumentException(String.format(
					"worker Id can't be greater than %d or less than 0", maxWorkerId));
		}
		if (datacenterId > maxDatacenterId || datacenterId < 0) {
			throw new IllegalArgumentException(String.format(
					"datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
		}
		this.workerId = workerId;
		this.datacenterId = datacenterId;
		LOG.info(String
				.format("worker starting. timestamp left shift %d, datacenter id bits %d, worker id bits %d, sequence bits %d, workerid %d",
						timestampLeftShift, datacenterIdBits, workerIdBits, sequenceBits, workerId));
	}

	public synchronized long nextId() {
		// 拿到当前的毫秒数
		long timestamp = timeGen();

		if (timestamp < lastTimestamp) {
			LOG.error(String.format("clock is moving backwards.  Rejecting requests until %d.",
					lastTimestamp));
			throw new RuntimeException(String.format(
					"Clock moved backwards.  Refusing to generate id for %d milliseconds",
					lastTimestamp - timestamp));
		}

		if (lastTimestamp == timestamp) {
			// 最后时间和当前时间相等，那么时间自增
			sequence = (sequence + 1) & sequenceMask;
			if (sequence == 0) {
				timestamp = tilNextMillis(lastTimestamp);
			}
		} else {
			sequence = 0L;
		}

		lastTimestamp = timestamp;
		// 执行ID偏移
		return ((timestamp - twepoch) << timestampLeftShift) | (datacenterId << datacenterIdShift)
				| (workerId << workerIdShift) | sequence;
	}

	protected long tilNextMillis(long lastTimestamp) {
		long timestamp = timeGen();
		while (timestamp <= lastTimestamp) {
			timestamp = timeGen();
		}
		return timestamp;
	}

	protected long timeGen() {
		return System.currentTimeMillis();
	}

	// 使用
	public static void main(String[] args) {
		IdWorker idWorker = new IdWorker(1, 0);
		System.out.println(idWorker.nextId());
	}
}
