package antifraud;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Anti-Fraud System API", version = "1.0", description = "The set of REST endpoints responsible for interacting with users, and " +
        "an internal transaction validation logic based on a set of heuristic rules.The data returns in JSON format."))
public class AntiFraudApplication {
    public static void main(String[] args) {
        SpringApplication.run(AntiFraudApplication.class, args);
    }
}