package org.exampledriven.eureka.customer.shared.server.server.util;

import org.apache.tomcat.util.net.SSLHostConfigCertificate;
import org.apache.tomcat.util.net.jsse.JSSEUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.security.KeyStore;
import java.security.Provider;
import java.security.cert.X509Certificate;

public class CFContainerJSSEUtil extends JSSEUtil {

    private static final Logger logger = LoggerFactory.getLogger(CFContainerJSSEUtil.class);

    public CFContainerJSSEUtil(SSLHostConfigCertificate certificate) {
        super(certificate);
    }

    @Override
    public KeyManager[] getKeyManagers() throws Exception {
        KeyManagerFactory kmf =
                KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
        ks.load(null, null);
        kmf.init(ks, new char[]{});
        KeyManager[] ret = kmf.getKeyManagers();
        return ret;
        //return super.getKeyManagers();
    }

    @Override
    public TrustManager[] getTrustManagers() throws Exception {
        TrustManagerFactory tmf =
                TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
        ks.load(null, null);
        tmf.init(ks);
        TrustManager[] ret = tmf.getTrustManagers();

        logger.info("***** MY TMS: {} ", ret.length);
        if (ret.length > 0) {
            for (int i = 0 ; i < ret.length ; i++) {
                logger.info("TM - {}", i);
                if (ret[i] instanceof X509ExtendedTrustManager) {
                    logger.info("Accepted Issuers");
                    int counter = 0;
                    for (X509Certificate cert : ((X509ExtendedTrustManager)ret[i]).getAcceptedIssuers()){
                        logger.info(""+counter++);
                        logger.info(cert.getIssuerDN().getName());
                    }
                }
            }
        }

        logger.info("PARENT TMS: "+super.getTrustManagers());


        return ret;
    }
}
