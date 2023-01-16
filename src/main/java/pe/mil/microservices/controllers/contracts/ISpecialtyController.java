package pe.mil.microservices.controllers.contracts;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import pe.mil.microservices.constants.ProcessConstants;
import pe.mil.microservices.dto.requests.RegisterSpecialtyRequest;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@Validated
@RequestMapping(
    path = ProcessConstants.MICROSERVICE_SPECIALTY_PATH,
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE
)
public interface ISpecialtyController {

    Mono<ResponseEntity<Object>> findAll();

    Mono<ResponseEntity<Object>> getById(@Valid @PathVariable Long customerId);

    Mono<ResponseEntity<Object>> save(@Valid @RequestBody Mono<RegisterSpecialtyRequest> request);

    Mono<ResponseEntity<Object>> update(@Valid @RequestBody Mono<RegisterSpecialtyRequest> request);
}
