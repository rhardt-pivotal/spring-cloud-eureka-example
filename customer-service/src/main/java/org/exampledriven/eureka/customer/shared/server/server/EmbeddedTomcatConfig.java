package org.exampledriven.eureka.customer.shared.server.server;

import org.exampledriven.eureka.customer.shared.server.server.util.CFContainerNioProtocol;
import org.exampledriven.eureka.customer.shared.server.server.util.CFContainerTomcatEmbeddedServletContainerFactory;
import org.springframework.boot.autoconfigure.web.EmbeddedServletContainerAutoConfiguration;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;


//@Configuration
//@Import(EmbeddedServletContainerAutoConfiguration.BeanPostProcessorsRegistrar.class)
public class EmbeddedTomcatConfig {

    @Bean
    public TomcatEmbeddedServletContainerFactory tescf() throws Exception{
        CFContainerTomcatEmbeddedServletContainerFactory ret =
                new CFContainerTomcatEmbeddedServletContainerFactory();
        ret.setProtocol(CFContainerNioProtocol.class.getCanonicalName());
        return ret;
    }

}
