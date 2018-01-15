package org.exampledriven.eureka.customer.shared.server.server;

import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.security.cert.X509Certificate;

@Component
final class SerialNumberExtractor {

    String getSerialNumber(Principal principal) {
        X509Certificate certificate = (X509Certificate) ((PreAuthenticatedAuthenticationToken) principal).getCredentials();
        return certificate.getSerialNumber().toString();
    }

}