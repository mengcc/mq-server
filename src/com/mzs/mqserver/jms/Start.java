/**
 * All rights Reserved, Designed By tydic.com
 * @Author mengzs
 * @Date 2017年11月13日 下午4:06:34
 */
package com.mzs.mqserver.jms;

/**
 * @Author mengzs 
 * @Date 2017年11月13日
 */
public class Start {

	public static void main(String[] args) {
		MessageHandler handler=new MessageHandler();
		handler.init();
		handler.handlerMsg();
	}
}
