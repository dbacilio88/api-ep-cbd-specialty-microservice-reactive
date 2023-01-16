package pe.mil.microservices.services.implementations;

import com.google.common.collect.Lists;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.mil.microservices.components.enums.SpecialtyValidationResult;
import pe.mil.microservices.components.mappers.contracts.ISpecialtyMapperByMapstruct;
import pe.mil.microservices.components.validations.ISpecialtyRegisterValidation;
import pe.mil.microservices.dto.Specialty;
import pe.mil.microservices.dto.requests.RegisterSpecialtyRequest;
import pe.mil.microservices.dto.responses.RegisterSpecialtyResponse;
import pe.mil.microservices.repositories.contracts.ISpecialtyRepository;
import pe.mil.microservices.repositories.entities.SpecialtyEntity;
import pe.mil.microservices.services.contracts.ISpecialtyServices;
import pe.mil.microservices.utils.components.enums.ResponseCode;
import pe.mil.microservices.utils.components.exceptions.CommonBusinessProcessException;
import pe.mil.microservices.utils.components.helpers.ObjectMapperHelper;
import pe.mil.microservices.utils.dtos.base.GenericBusinessResponse;
import pe.mil.microservices.utils.dtos.process.BusinessProcessResponse;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@Service
public class SpecialtyService implements ISpecialtyServices {

    private final ISpecialtyRepository specialtyRepository;
    private final String specialtyServiceId;


    @Autowired
    public SpecialtyService(
        final ISpecialtyRepository specialtyRepository
    ) {
        this.specialtyRepository = specialtyRepository;
        specialtyServiceId = UUID.randomUUID().toString();
        log.debug("specialtyServiceId {}", specialtyServiceId);
        log.debug("SpecialtyService loaded successfully");
    }

    @Override
    public Mono<BusinessProcessResponse> getById(Long id) {

        log.info("this is in services getById method");
        log.debug("specialtyServiceId {}", specialtyServiceId);

        GenericBusinessResponse<Specialty> genericMessagesBusinessResponse = new GenericBusinessResponse<>();

        return Mono
            .just(genericMessagesBusinessResponse)
            .flatMap(generic -> {
                final Optional<SpecialtyEntity> entity = this.specialtyRepository.findById(id);
                if (entity.isEmpty()) {
                    return Mono.error(() -> new CommonBusinessProcessException(ResponseCode.ERROR_IN_REQUESTED_DATA));
                } else {
                    return Mono.just(entity.get());
                }
            })
            .flatMap(entity -> {
                final Specialty target = ObjectMapperHelper.map(entity, Specialty.class);
                log.info("specialty {} ", target.toString());
                GenericBusinessResponse<Specialty> data = new GenericBusinessResponse<>(target);
                return Mono.just(data);
            })
            .flatMap(response -> {
                return Mono.just(BusinessProcessResponse.setEntitySuccessfullyResponse(response));
            })
            .doOnSuccess(success ->
                log.info("finish process getById, success: {}", success)
            )
            .doOnError(throwable ->
                log.error("exception error in process getById, error: {}", throwable.getMessage())
            );
    }

    @Override
    public Mono<BusinessProcessResponse> getAll() {

        log.info("this is in services getAll method");
        log.debug("specialtyServiceId {}", specialtyServiceId);
        GenericBusinessResponse<List<Specialty>> genericMessagesBusinessResponse = new GenericBusinessResponse<>();

        return Mono.just(genericMessagesBusinessResponse)
            .flatMap(generic -> {
                generic.setData(ObjectMapperHelper.mapAll(Lists.newArrayList(this.specialtyRepository.findAll()), Specialty.class));
                return Mono.just(generic);
            })
            .flatMap(response -> {
                log.info("response {} ", response.getData().toString());
                return Mono.just(BusinessProcessResponse
                    .setEntitySuccessfullyResponse(response));
            }).flatMap(process -> Mono.just(BusinessProcessResponse.setEntitySuccessfullyResponse(process.getBusinessResponse())))
            .doOnSuccess(success ->
                log.info("finish process getById, success: {}", success.toString())
            )
            .doOnError(throwable ->
                log.error("exception error in process getById, error: {}", throwable.getMessage())
            );
    }

    @Override
    public Mono<BusinessProcessResponse> save(Mono<RegisterSpecialtyRequest> entity) {

        log.info("this is in services save method");
        log.debug("specialtyServiceId {}", specialtyServiceId);

        return entity
            .flatMap(create -> {
                log.debug("this is in services save demo method");
                final SpecialtyValidationResult result =
                    ISpecialtyRegisterValidation
                        .isSpecialtyIdValidation()
                        .and(ISpecialtyRegisterValidation.isSpecialtyNameValidation())
                        .and(ISpecialtyRegisterValidation.isSpecialtyDescriptionValidation())
                        .apply(create);
                log.info("result {} ", result);
                if (!SpecialtyValidationResult.SPECIALTY_VALID.equals(result)) {
                    return Mono.error(() -> new CommonBusinessProcessException(ResponseCode.ERROR_IN_REQUESTED_DATA));
                }
                return Mono.just(create);
            })
            .flatMap(request -> {
                log.debug("log in flatMap context #2");
                final SpecialtyEntity save =
                    ISpecialtyMapperByMapstruct.SPECIALTY_MAPPER.mapSpecialtyEntityByRegisterSpecialtyRequest(request);
                log.info("specialty entity {} ", save);

                boolean exists = this.specialtyRepository.existsBySpecialtyId(save.getSpecialtyId());
                log.info("specialty exists entity {} ", exists);

                if (exists) {
                    return Mono.error(() -> new CommonBusinessProcessException(ResponseCode.ERROR_IN_REQUESTED_DATA_EXISTS));
                }

                final SpecialtyEntity saved = this.specialtyRepository.save(save);
                log.info("specialty saved entity {} ", saved);

                if (Objects.isNull(saved.getSpecialtyId())) {
                    return Mono.error(() -> new CommonBusinessProcessException(ResponseCode.ERROR_IN_REQUESTED_DATA));
                }

                return Mono.just(saved);
            })
            .flatMap(specialty -> {
                log.info("specialty entity {} ", specialty);
                final RegisterSpecialtyResponse response = ObjectMapperHelper
                    .map(specialty, RegisterSpecialtyResponse.class);
                return Mono.just(BusinessProcessResponse
                    .setEntitySuccessfullyResponse(new GenericBusinessResponse<>(response)));
            })
            .doOnSuccess(success ->
                log.info("finish process save, success: {}", success)
            )
            .doOnError(throwable ->
                log.error("exception error in process save, error: {}", throwable.getMessage())
            );
    }

    @Override
    public Boolean delete(Specialty entity) {

        log.debug("this is in services delete method");
        log.debug("specialtyServiceId {}", specialtyServiceId);

        final Optional<SpecialtyEntity> result = this.specialtyRepository.findById(entity.getSpecialtyId());

        if (result.isEmpty()) {
            return false;
        }

        this.specialtyRepository.delete(result.get());

        return true;
    }

    @Override
    public Mono<BusinessProcessResponse> update(Mono<RegisterSpecialtyRequest> entity) {

        log.info("this is in services update method");
        log.debug("specialtyServiceId {}", specialtyServiceId);

        return entity.
            flatMap(update -> {
                final SpecialtyValidationResult result = ISpecialtyRegisterValidation
                    .isSpecialtyNameValidation().apply(update);
                if (!SpecialtyValidationResult.SPECIALTY_VALID.equals(result)) {
                    return Mono.error(() -> new CommonBusinessProcessException(ResponseCode.ERROR_IN_REQUESTED_DATA));
                }
                return Mono.just(update);
            })
            .flatMap(request -> {

                final SpecialtyEntity update = ISpecialtyMapperByMapstruct
                    .SPECIALTY_MAPPER
                    .mapSpecialtyEntityByRegisterSpecialtyRequest(request);

                final SpecialtyEntity updated = this.specialtyRepository.save(update);

                if (Objects.isNull(updated.getSpecialtyId())) {
                    return Mono.error(() -> new CommonBusinessProcessException(ResponseCode.ERROR_IN_REQUESTED_DATA));
                }
                return Mono.just(updated);

            })
            .flatMap(specialty -> {
                final RegisterSpecialtyResponse response = ObjectMapperHelper
                    .map(specialty, RegisterSpecialtyResponse.class);
                return Mono.just(BusinessProcessResponse
                    .setEntitySuccessfullyResponse(new GenericBusinessResponse<>(response)));
            }).doOnSuccess(success ->
                log.info("finish process save, success: {}", success)
            )
            .doOnError(throwable ->
                log.error("exception error in process save, error: {}", throwable.getMessage())
            );
    }
}
