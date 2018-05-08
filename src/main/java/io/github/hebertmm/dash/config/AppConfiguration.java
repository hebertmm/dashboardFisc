package io.github.***REMOVED***mm.dash.config;

import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jxmpp.stringprep.XmppStringprepException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.integration.xmpp.config.XmppConnectionFactoryBean;

import javax.net.ssl.SSLSocketFactory;

@Configuration
@ImportResource("classpath:integration.xml")
public class AppConfiguration {

	@Bean("xmppConnection")
	public XmppConnectionFactoryBean xmppConnectionFactoryBean() throws XmppStringprepException {

		XMPPTCPConnectionConfiguration connectionConfiguration = XMPPTCPConnectionConfiguration.builder()
				.setXmppDomain("io.github.***REMOVED***mm")
				.setHost("fcm-xmpp.googleapis.com").setPort(5235)
				.setConnectTimeout(10000)
				.setUsernameAndPassword("***REMOVED***@gcm.googleapis.com","***REMOVED***")
				.setSecurityMode(SecurityMode.ifpossible)
				.setSendPresence(false)
				.setSocketFactory(SSLSocketFactory.getDefault())
				.build();
		
		XmppConnectionFactoryBean connectionFactoryBean = new XmppConnectionFactoryBean();
		connectionFactoryBean.setConnectionConfiguration(connectionConfiguration);
		connectionFactoryBean.setSubscriptionMode(null);
		connectionFactoryBean.setAutoStartup(false); //retirar em produção
		Roster.setRosterLoadedAtLoginDefault(false);

		return connectionFactoryBean;
	}
}
