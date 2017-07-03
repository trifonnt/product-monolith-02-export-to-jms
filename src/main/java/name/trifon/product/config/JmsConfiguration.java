package name.trifon.product.config;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;

import io.github.jhipster.config.JHipsterProperties;

//   Using JMS in Spring Boot
// - https://dzone.com/articles/using-jms-in-spring-boot-1?edition=305129&utm_source=Daily%20Digest&utm_medium=email&utm_campaign=dd%202017-06-22
// Below Java annotation enables @JmsListener(destination = "OrderTransactionQueue", containerFactory = "myFactory")
@EnableJms
@Configuration
public class JmsConfiguration {

	// TODO - @Trifon - Read it from Properties
	// TODO - @Trifon - Username and password?
	private static final String JMS_BROKER_URL = "tcp://localhost:61616"; // embedded: "vm://embedded?broker.persistent=false,useShutdownHook=false";
// failover://(tcp://10.144.4.28:61616,tcp://10.144.4.28:61616)?initialReconnectDelay=100&randomize=false

	private final Logger log = LoggerFactory.getLogger(JmsConfiguration.class);

	private final JHipsterProperties jHipsterProperties;


	public JmsConfiguration(JHipsterProperties jHipsterProperties) {
        this.jHipsterProperties = jHipsterProperties;
	}

	@Bean
	public ConnectionFactory connectionFactory() {
		return new ActiveMQConnectionFactory(JMS_BROKER_URL);
	}

}
