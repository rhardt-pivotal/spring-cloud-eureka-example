package org.exampledriven.eureka.customer.shared.server.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.net.ssl.*;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    @PostConstruct
    public void sslContextConfiguration() {
        try {
            logger.info("*** PC SSL CONFIG ****");
            SSLContext sslContext = SSLContext.getInstance("TLS");
            TrustManagerFactory tmf = TrustManagerFactory.getInstance("PKIX", "SunJSSE");
            KeyStore ks = KeyStore.getInstance("JKS");
            InputStream is = new FileInputStream("/home/vcap/app/BOOT-INF/classes/server-nonprod.jks");
            ks.load(is, "changeme".toCharArray());
            tmf.init(ks);

            TrustManager[] tms = tmf.getTrustManagers();

            logger.info(" *** PC TMS: {}", tms);

//            sslContext.init(kms, tms, null);
//            SSLContext.setDefault(sslContext);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}


