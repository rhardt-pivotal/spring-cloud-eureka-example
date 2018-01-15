package org.exampledriven.eureka.customer.shared.server.server.util;

import org.apache.tomcat.util.net.SSLHostConfigCertificate;
import org.apache.tomcat.util.net.SSLUtil;
import org.apache.tomcat.util.net.jsse.JSSEImplementation;

public class CFContainerJsseImplementation extends JSSEImplementation{

    @Override
    public SSLUtil getSSLUtil(SSLHostConfigCertificate certificate) {
        return new CFContainerJSSEUtil(certificate);
    }
}
