# spring-cloud-eureka-mtls-example

This is code shamelessly stolen from:

https://github.com/nebhale/mtls-sample
... and ...
https://github.com/ExampleDriven/spring-cloud-eureka-example

I've munged the two of them together to show how you can... 
- deploy a service to Cloud Foundry with no public route
- have that service listen for SSL connections on 8080 (to make Cloud Foundry happy)
- bind it to Eureka
- deploy a client to the same CF space, and bind it to the same Eureka service
- have the client call the service using CF c2c networking, presenting a certificate for mTLS
- have the client validate the server's certificate and vice versa
- map the client app's cf-assigned guid to roles in the server
- do the whole thing using the CF-provided 'container-identity' certificates.

        cf create-service p-service-registry standard demo-eureka    
        cf push -f ./customer-service/manifest.yml #notice the no-route: true in the manifest
        cf push -f ./customer-client-service/manifest.yml
        cf add-network-policy customer-client-service --destination-app customer-service
        
        
then you can try these uris:
- https://customer-client-service.your.cf.apps.domain/customer-client/1
- https://customer-client-service.your.cf.apps.domain/customer-client-user
- https://customer-client-service.your.cf.apps.domain/customer-client-admin

the last one should throw an error because the *customer-service* app doesn't recognize the *customer-client-service* app as having the _admin_ role.

        cf app customer-client-service --guid
        *copy the guid*
        cf set-env customer-service MTLS_ADMIN-CLIENT-IDS <guid>
        cf restage customer-service
        
now the 3rd URL should return successfully. 
        
