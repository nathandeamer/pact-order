# Pact Bi-Directional with OAS

https://docs.pactflow.io/docs/bi-directional-contract-testing/

### Generate the openapi specification
This project uses `springdoc-openapi-ui` to generate openapi spec.  
Using the `org.springdoc.openapi-gradle-plugin` gradle plugin we can generate a copy of the spec:  
```./gradlew generateOpenApiDocs```

### Publish the contract to pact:
```./publish.sh true```
