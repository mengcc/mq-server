
package com.mzs.mqserver.jms;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.jms.MapMessage;
import javax.jms.MessageListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * 
 * @ClassName: MessageListenerThread
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author panyunfu
 * @date 2017年9月18日 下午4:09:52
 *
 */
public class MessageListenerThread implements MessageListener {
	private static final Logger logger = LoggerFactory.getLogger(MessageListenerThread.class);

	private ExecutorService threadPool = Executors.newFixedThreadPool(2);

	@Override
	public void onMessage(final javax.jms.Message message) {
		this.threadPool.execute(new Runnable() {
			public void run() {
				try {
					System.out.println("消息已接收");
					if (message instanceof MapMessage) {
						MapMessage mapMsg = (MapMessage) message;
						String startCity = mapMsg.getString("startCity");
						String flightNo = mapMsg.getString("flightNo");
						String key = mapMsg.getString("key");
						logger.info("flightNo:" + flightNo + ",startCity=" + startCity+",key="+key);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	

}
