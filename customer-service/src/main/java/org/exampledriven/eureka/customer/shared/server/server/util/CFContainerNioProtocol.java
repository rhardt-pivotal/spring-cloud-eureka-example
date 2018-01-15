package org.exampledriven.eureka.customer.shared.server.server.util;

import org.apache.coyote.http11.Http11NioProtocol;

public class CFContainerNioProtocol extends Http11NioProtocol {

    public CFContainerNioProtocol() {
        super();
        setSslImplementationName(CFContainerJsseImplementation.class.getCanonicalName());
    }



}
