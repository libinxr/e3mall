package cn.e3mall.activemq;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class ActiveMqSpring {

	@Test
	public void activeMqProducer() throws Exception{
		//从spring容器中获取factory
		ApplicationContext context=new ClassPathXmlApplicationContext("classpath:spring/applicationContext-activemq.xml");
		//从容器中获得JmsTemplate对象
		JmsTemplate template = context.getBean(JmsTemplate.class);
		//从容器中获得Destination对象
		Destination  queueDestination = (Destination) context.getBean("queueDestination");
		//发送消息
		template.send(queueDestination, new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				
				return session.createTextMessage("hello activemq2");
			}
		});
		
	}
	
}
