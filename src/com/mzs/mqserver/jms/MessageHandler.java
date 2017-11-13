
package com.mzs.mqserver.jms;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.pool.PooledConnection;
import org.apache.activemq.pool.PooledConnectionFactory;

/**
 * 
 * @ClassName: MessageHandler
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author panyunfu
 * @date 2017年9月18日 下午4:10:01
 *
 */
public class MessageHandler {

	private ActiveMQConnectionFactory connectionFactroy = null; // 连接工厂
	private PooledConnection connection = null;
	private Session session = null;
	private Destination destination = null; // 消息的目的地
	private static final String url = "failover:tcp://127.0.0.1:61616";
	private PooledConnectionFactory pooledConnectionFactory;
	private static final String QUEUE = "demo";

	public void init() {
		try {
			// 实例化连接工厂
			connectionFactroy = new ActiveMQConnectionFactory();
			connectionFactroy.setUserName(ActiveMQConnection.DEFAULT_USER);
			connectionFactroy.setPassword(ActiveMQConnection.DEFAULT_PASSWORD);
			connectionFactroy.setBrokerURL(url);
			connectionFactroy.setDispatchAsync(true);// 异步发送消息
			pooledConnectionFactory = new PooledConnectionFactory();
			pooledConnectionFactory.setConnectionFactory(connectionFactroy);
			pooledConnectionFactory.setMaximumActiveSessionPerConnection(200);
			pooledConnectionFactory.setIdleTimeout(120);
			pooledConnectionFactory.setMaxConnections(5);
			pooledConnectionFactory.setTimeBetweenExpirationCheckMillis(3000);
			pooledConnectionFactory.setBlockIfSessionPoolIsFull(true);
			connection = (PooledConnection) pooledConnectionFactory.createConnection();
			connection.start();
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	public void handlerMsg() {
		MessageConsumer messageConsumer; // 消息的消费者
		try {
			session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
			destination = session.createQueue(QUEUE);
			messageConsumer = session.createConsumer(destination); // 创建消息消费者
			messageConsumer.setMessageListener(new MessageListenerThread());// 注册消息监听
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
