package pe.mil.microservices.components.validations;

import pe.mil.microservices.components.enums.SpecialtyValidationResult;
import pe.mil.microservices.dto.requests.RegisterSpecialtyRequest;
import java.util.function.Function;
import java.util.function.Predicate;

@FunctionalInterface
public interface ISpecialtyRegisterValidation
    extends Function<RegisterSpecialtyRequest, SpecialtyValidationResult> {

    static ISpecialtyRegisterValidation isSpecialtyNameValidation() {
        return request ->
            request.getName() != null
                && !request.getName().isEmpty()
                && !request.getName().isBlank()

                ? SpecialtyValidationResult.SPECIALTY_VALID
                : SpecialtyValidationResult.INVALID_SPECIALTY_NAME;
    }

    static ISpecialtyRegisterValidation isSpecialtyIdValidation() {
        return request ->
            request.getSpecialtyId() != null
                ? SpecialtyValidationResult.SPECIALTY_VALID
                : SpecialtyValidationResult.INVALID_SPECIALTY_ID;
    }

    static ISpecialtyRegisterValidation isSpecialtyDescriptionValidation() {
        return request ->
            request.getDescription() != null
                && !request.getDescription().isBlank()
                && !request.getDescription().isEmpty()
                ? SpecialtyValidationResult.SPECIALTY_VALID
                : SpecialtyValidationResult.INVALID_SPECIALTY_DESCRIPTION;
    }

    static ISpecialtyRegisterValidation customValidation(Predicate<RegisterSpecialtyRequest> validate) {
        return request -> validate.test(request)
            ? SpecialtyValidationResult.SPECIALTY_VALID
            : SpecialtyValidationResult.INVALID_SPECIALTY_NAME;
    }

    default ISpecialtyRegisterValidation and(ISpecialtyRegisterValidation andValidation) {
        return request -> {
            SpecialtyValidationResult validation = this.apply(request);
            return validation.equals(SpecialtyValidationResult.SPECIALTY_VALID)
                ? andValidation.apply(request)
                : validation;
        };
    }

    default ISpecialtyRegisterValidation or(ISpecialtyRegisterValidation orValidation) {
        return request -> {
            SpecialtyValidationResult validation = this.apply(request);
            return validation.equals(SpecialtyValidationResult.SPECIALTY_VALID)
                ? orValidation.apply(request)
                : validation;
        };
    }
}
