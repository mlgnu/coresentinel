package com.districtcore.coresentinel.annotation;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.HandlerMapping;
import java.util.Map;

public class PrefixedIdArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(PrefixedId.class)
                && parameter.getParameterType().equals(Long.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {
        PrefixedId annotation = parameter.getParameterAnnotation(PrefixedId.class);
        Map<String, String> uriVars = (Map<String, String>) webRequest.getAttribute(
                HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE, NativeWebRequest.SCOPE_REQUEST);
        String pathVarName = annotation.value();
        String value = uriVars.get(pathVarName);
        String prefix = annotation.prefix();
        if (value == null) {
            throw new IllegalArgumentException("Missing path variable: " + pathVarName);
        }
        if (value.startsWith(prefix)) {
            value = value.substring(1);
        }
        return Long.valueOf(value);
    }
}

