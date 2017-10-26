package hu.bme.jwtsampleapp.sampleservice.configuration.remoting;


import hu.bme.jwtsampleapp.entities.sample.service.SampleServiceIF;
import hu.bme.jwtsampleapp.security.spring.remoting.SecureHttpInvokerServiceExporter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;

import java.util.Properties;

@Configuration
@Slf4j
@EnableWebMvc   //This has to be enabled for spring remoting over http to work
public class ExportServicesConfiguration {

    @Bean
    public SecureHttpInvokerServiceExporter sampleServiceExporter(SampleServiceIF sampleService) {
        log.info("Exporting SampleService");
        SecureHttpInvokerServiceExporter serviceExporter = new SecureHttpInvokerServiceExporter();
        serviceExporter.setServiceInterface(SampleServiceIF.class);
        serviceExporter.setService(sampleService);
        return serviceExporter;
    }

    @Bean
    public SimpleUrlHandlerMapping serviceMappings(){
        SimpleUrlHandlerMapping urlMappings = new SimpleUrlHandlerMapping();
        Properties mappings = new Properties();
        mappings.setProperty(SampleServiceIF.REMOTE_ENDPOINT, "sampleServiceExporter");
        urlMappings.setMappings(mappings);
        return urlMappings;
    }
}
