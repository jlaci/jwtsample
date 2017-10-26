package hu.bme.jwtsampleapp.gateway.configuration.remoting;

import hu.bme.jwtsampleapp.entities.sample.service.SampleServiceIF;
import hu.bme.jwtsampleapp.security.spring.remoting.SecureRemoteInvocationFactory;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;

@Configuration
@CommonsLog
public class ImportServicesConfiguration {

    @Bean
    public SecureRemoteInvocationFactory secureRemoteInvocationFactory() {
        return new SecureRemoteInvocationFactory();
    }

    @Bean
    public HttpInvokerProxyFactoryBean remoteAuthService(@Value("${network.SampleServiceUrl}") String sampleServiceUrl, SecureRemoteInvocationFactory secureRemoteInvocationFactory) {
        HttpInvokerProxyFactoryBean invokerProxyFactoryBean = new HttpInvokerProxyFactoryBean();
        invokerProxyFactoryBean.setServiceInterface(SampleServiceIF.class);
        invokerProxyFactoryBean.setServiceUrl(sampleServiceUrl + SampleServiceIF.REMOTE_ENDPOINT);
        invokerProxyFactoryBean.setRemoteInvocationFactory(secureRemoteInvocationFactory);
        return invokerProxyFactoryBean;
    }

}
