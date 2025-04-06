package com.districtcore.coresentinel.config;

import com.districtcore.coresentinel.annotation.PrefixedId;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

@Component
public class SwaggerConfig implements OperationCustomizer {

    @Override
    public Operation customize(Operation operation, HandlerMethod handlerMethod) {
        for (MethodParameter methodParameter : handlerMethod.getMethodParameters()) {
            if (methodParameter.hasParameterAnnotation(PrefixedId.class)) {
                PrefixedId userIdAnnotation = methodParameter.getParameterAnnotation(PrefixedId.class);
                String pathVarName = userIdAnnotation.value();
                if (operation.getParameters() != null) {
                    for (Parameter param : operation.getParameters()) {
                        if (param.getName().equals(pathVarName)) {
                            param.setIn("path");
                            param.setSchema(new StringSchema());
                            param.setDescription("ID with prefix (e.g., 'A123', 'R456')");
                                    param.setRequired(true);
                        }
                    }
                }
            }
        }
        return operation;
    }
}
