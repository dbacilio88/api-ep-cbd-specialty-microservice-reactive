package pe.mil.microservices.components.mappers.contracts;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import pe.mil.microservices.dto.requests.RegisterSpecialtyRequest;
import pe.mil.microservices.repositories.entities.SpecialtyEntity;

import static pe.mil.microservices.components.mappers.contracts.ISpecialtyMapperByMapstruct.CustomerFields.*;

@Mapper
public interface ISpecialtyMapperByMapstruct {

    ISpecialtyMapperByMapstruct SPECIALTY_MAPPER = Mappers
        .getMapper(ISpecialtyMapperByMapstruct.class);

    @Condition
    default boolean isNotEmpty(String value) {
        return value != null && value.length() > 0;
    }

    @Mapping(source = FIELD_SPECIALTY_ID, target = FIELD_SPECIALTY_ID)
    @Mapping(source = FIELD_SPECIALTY_NAME, target = FIELD_SPECIALTY_NAME)
    @Mapping(source = FIELD_SPECIALTY_DESCRIPTION, target = FIELD_SPECIALTY_DESCRIPTION)
    SpecialtyEntity mapSpecialtyEntityByRegisterSpecialtyRequest(RegisterSpecialtyRequest source);


    class CustomerFields {
        public static final String FIELD_SPECIALTY_ID = "specialtyId";
        public static final String FIELD_SPECIALTY_NAME = "name";
        public static final String FIELD_SPECIALTY_DESCRIPTION = "description";
    }
}
