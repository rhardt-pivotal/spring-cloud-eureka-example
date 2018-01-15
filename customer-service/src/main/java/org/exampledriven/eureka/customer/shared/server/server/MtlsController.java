package org.exampledriven.eureka.customer.shared.server.server;

import org.exampledriven.eureka.customer.shared.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
public class MtlsController {

    @Autowired
    private SerialNumberExtractor serialNumberExtractor;

    private static final Logger logger = LoggerFactory.getLogger(MtlsController.class.getName());

    @GetMapping(path = "/admin", produces = "application/json")
    public ResponseEntity<Customer> admin(Principal principal) {
        return handleMtlsRequest(principal, "/admin" );
    }

    @GetMapping(path = "/user", produces = "application/json")
    public ResponseEntity<Customer> user(Principal principal) {
        return handleMtlsRequest(principal, "/user" );
    }

    private ResponseEntity<Customer> handleMtlsRequest(Principal principal, String endpoint) {
        String applicationId = principal.getName();
        String certificateSerialNumber = this.serialNumberExtractor.getSerialNumber(principal);

        this.logger.info("Received request for {} with certificate for {} with SN {}", endpoint, applicationId, certificateSerialNumber);
        return ResponseEntity.ok(new Customer(1, principal.getName(), String.format("You authenticated to %s using x509 certificate for %s with SN %s", endpoint, applicationId, certificateSerialNumber)));
        //return String.format("You authenticated to %s using x509 certificate for %s with SN %s", endpoint, applicationId, certificateSerialNumber);
    }


}
