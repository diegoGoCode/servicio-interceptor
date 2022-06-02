package com.servicio.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.servicio.dominio.dto.ObjectResponseDTO;
import com.servicio.interceptor.dto.LoggerDto;
import com.servicio.interceptor.util.HeadersConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Component
public class GeneralInterceptor implements HandlerInterceptor {

    private static final Logger LOG = LoggerFactory.getLogger(GeneralInterceptor.class);
    private static final String NAME_COMPONENT = "servicio";
    private static final String ID_SERVICE = "1";

    LoggerDto loggerDto = new LoggerDto();
    Map<String, String> headers;
    Map<String, String> params;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        LOG.info("PreHandle -> GeneralInterceptor");
        if(request.getRequestURI().contains("swagger") || request.getRequestURI().contains("api-docs")){

            return true;
        }

        // Se validan los Headers requeridos
        getHeaders(request);
        if (!headers.containsKey(HeadersConstants.X_RQUID.toLowerCase()) || !headers.containsKey(HeadersConstants.X_CHANNEL.toLowerCase()) || !headers.containsKey(HeadersConstants.X_COMPANY_ID.toLowerCase())) {
            ObjectMapper mapper = new ObjectMapper();
            ObjectResponseDTO generalResponse = buildGeneralResponseErrorHeadersRequired();
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(mapper.writeValueAsString(generalResponse));
            return false;
        }

        // Se construye dto logger
        try {
            buildLoggerPreHandle(request);
        } catch (IOException e) {
            ObjectMapper mapper = new ObjectMapper();
            ObjectResponseDTO generalResponse = ObjectResponseDTO.builder().err("Error al obtener el body de la peticion").build();
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(mapper.writeValueAsString(generalResponse));
        }
        LOG.info("Logger PreHandle: " + loggerDto.toString());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        LOG.info("postHandle -> GeneralInterceptor");

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        LOG.info("afterCompletion -> GeneralInterceptor");
        loggerDto.setTimeResponseService(new Date().getTime());
        loggerDto.setCodeResponse(String.valueOf(response.getStatus()));
        LOG.info("Logger afterCompletion: " + loggerDto.toString());
        loggerDto = new LoggerDto();
    }

    public void getHeaders(HttpServletRequest request) {
        headers = new HashMap<>();
        Collections.list(request.getHeaderNames()).forEach(header -> headers.put(header, request.getHeader(header)));
    }

    public ObjectResponseDTO buildGeneralResponseErrorHeadersRequired() {
        return ObjectResponseDTO.builder().err("Los Headers: " + HeadersConstants.X_RQUID + ", " + HeadersConstants.X_CHANNEL + " y " + HeadersConstants.X_COMPANY_ID + " son requeridos en la petición.").build();
    }

    public void buildLoggerPreHandle(HttpServletRequest request) throws IOException {
        loggerDto.setTraceId(headers.get(HeadersConstants.X_RQUID.toLowerCase()));
        loggerDto.setChannel(headers.get(HeadersConstants.X_CHANNEL.toLowerCase()));
        loggerDto.setNameComponent(NAME_COMPONENT);
        loggerDto.setUrlServices(request.getMethod().concat(": ").concat(request.getRequestURI()));
        loggerDto.setIdServices(ID_SERVICE);
        loggerDto.setParametersIn(buildParametersInByHttpMethod(request));
        loggerDto.setTimeConsumeService(new Date().getTime());
        loggerDto.setIp(headers.containsKey(HeadersConstants.X_IP_ADDR.toLowerCase()) ? headers.get(HeadersConstants.X_IP_ADDR.toLowerCase()) : null);
    }

    public String buildParametersInByHttpMethod(HttpServletRequest request) throws IOException {
        String parametersIn = null;
        if (HttpMethod.GET.toString().equalsIgnoreCase(request.getMethod()) || HttpMethod.DELETE.toString().equalsIgnoreCase(request.getMethod()))
            parametersIn = getParams(request);
        if (HttpMethod.POST.toString().equalsIgnoreCase(request.getMethod()) || HttpMethod.PUT.toString().equalsIgnoreCase(request.getMethod()) || HttpMethod.PATCH.toString().equalsIgnoreCase(request.getMethod()))
            parametersIn = getBodyRequest(request);
        return parametersIn;
    }

    public String getParams(HttpServletRequest request) {
        params = new HashMap<>();
        Collections.list(request.getParameterNames()).forEach(param -> params.put(param, request.getParameter(param)));
        return params.toString();
    }

    public String getBodyRequest(HttpServletRequest request) throws IOException {
        InputStream inputStream = request.getInputStream();
        byte[] body = StreamUtils.copyToByteArray(inputStream);
        return new String(body);
    }
}