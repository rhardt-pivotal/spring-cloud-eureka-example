package org.exampledriven.eureka.customer.shared.server.server.util;

import org.apache.coyote.http11.AbstractHttp11JsseProtocol;
import org.apache.tomcat.util.net.SSLHostConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.embedded.EmbeddedServletContainerException;
import org.springframework.boot.context.embedded.Ssl;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.util.Assert;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

import java.io.FileNotFoundException;

public class CFContainerTomcatEmbeddedServletContainerFactory extends TomcatEmbeddedServletContainerFactory{

    private static final Logger logger = LoggerFactory.getLogger(CFContainerTomcatEmbeddedServletContainerFactory.class);


    private void configureSslClientAuth(AbstractHttp11JsseProtocol<?> protocol, Ssl ssl) {
        if (ssl.getClientAuth() == Ssl.ClientAuth.NEED) {
            protocol.setClientAuth(Boolean.TRUE.toString());
        }
        else if (ssl.getClientAuth() == Ssl.ClientAuth.WANT) {
            protocol.setClientAuth("want");
        }
    }

    @Override
    protected void configureSsl(AbstractHttp11JsseProtocol<?> protocol, Ssl ssl) {

        logger.info("*** Configure SSL, protocol: {}, SSL: {}", protocol, ssl);

        protocol.setSSLEnabled(true);

        logger.info("SSL Protocol: {}", ssl.getProtocol());

        protocol.setSslProtocol(ssl.getProtocol());
        configureSslClientAuth(protocol, ssl);

        logger.info("keystore pass: {} ,keypass: {}, alias: {}", ssl.getKeyStorePassword(), ssl.getKeyPassword(), ssl.getKeyAlias());
        protocol.setKeystorePass(ssl.getKeyStorePassword());
        protocol.setKeyPass(ssl.getKeyPassword());
        protocol.setKeyAlias(ssl.getKeyAlias());
        String ciphers = StringUtils.arrayToCommaDelimitedString(ssl.getCiphers());

        logger.info("Ciphers: {}", ciphers);

        protocol.setCiphers(StringUtils.hasText(ciphers) ? ciphers : null);

        logger.info("Enabled protocols: {}", ssl.getEnabledProtocols());

        if (ssl.getEnabledProtocols() != null) {
            try {
                for (SSLHostConfig sslHostConfig : protocol.findSslHostConfigs()) {
                    sslHostConfig.setProtocols(StringUtils
                            .arrayToCommaDelimitedString(ssl.getEnabledProtocols()));
                }
            }
            catch (NoSuchMethodError ex) {
                // Tomcat 8.0.x or earlier
                Assert.isTrue(
                        protocol.setProperty("sslEnabledProtocols",
                                StringUtils.arrayToCommaDelimitedString(
                                        ssl.getEnabledProtocols())),
                        "Failed to set sslEnabledProtocols");
            }
        }



//        if (getSslStoreProvider() != null) {
//            TomcatURLStreamHandlerFactory instance = TomcatURLStreamHandlerFactory
//                    .getInstance();
//            instance.addUserFactory(
//                    new SslStoreProviderUrlStreamHandlerFactory(getSslStoreProvider()));
//            protocol.setKeystoreFile(
//                    SslStoreProviderUrlStreamHandlerFactory.KEY_STORE_URL);
//            protocol.setTruststoreFile(
//                    SslStoreProviderUrlStreamHandlerFactory.TRUST_STORE_URL);
//        }
//        else {
//            configureSslKeyStore(protocol, ssl);
//            configureSslTrustStore(protocol, ssl);
//        }
    }



}
