package pe.mil.microservices.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ProcessConstants {

    public static final String PROCESS_TYPE_STRING = "String";
    public static final String PARAM_COMPONENT_UUID = "String";
    public static final String PARAMETER_EMPTY_VALUE = "";
    public static final String PARAMETER_ACTUATOR_PATH_CONTAIN_VALUE = "actuator";
    public static final String MICROSERVICE_PATH_CONTEXT = "";
    public static final String MICROSERVICE_SPECIALTY_PATH = MICROSERVICE_PATH_CONTEXT + "/specialties";
    public static final String GET_SPECIALTY_PATH = "";
    public static final String SAVE_SPECIALTY_PATH = "";
    public static final String GET_SPECIALTY_ID_PATH = "/{specialtyId}";
    public static final String FIND_ALL_SPECIALTY_LOG_METHOD = "find.specialty.method";
    public static final String FIND_BY_ID_SPECIALTY_LOG_METHOD = "findById.specialty.method";
    public static final String SAVE_SPECIALTY_LOG_METHOD = "save.specialty.method";
    public static final String UPDATE_SPECIALTY_LOG_METHOD = "update.specialty.method";


    public static final String MAPSTRUCT_COMPONENT_MODEL_CONFIGURATION = "spring";

}
