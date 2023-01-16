package pe.mil.microservices.services.contracts;

import pe.mil.microservices.dto.Specialty;
import pe.mil.microservices.dto.requests.RegisterSpecialtyRequest;
import pe.mil.microservices.utils.dtos.process.BusinessProcessResponse;
import pe.mil.microservices.utils.service.interfaces.*;
import reactor.core.publisher.Mono;

public interface ISpecialtyServices
    extends
    IGetDomainEntityById<Mono<BusinessProcessResponse>, Long>,
    IGetAllDomainEntity<Mono<BusinessProcessResponse>>,
    ISaveDomainEntity<Mono<BusinessProcessResponse>, Mono<RegisterSpecialtyRequest>>,
    IUpdateDomainEntity<Mono<BusinessProcessResponse>, Mono<RegisterSpecialtyRequest>>,
    IDeleteDomainEntity<Specialty>{
}
