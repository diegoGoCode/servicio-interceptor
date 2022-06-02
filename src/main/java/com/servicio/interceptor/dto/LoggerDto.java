package com.servicio.interceptor.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoggerDto {
    private static final long serialVersionUID = 1L;
    /*Consecutivo de la traza*/
    private String traceId;
    /*canal*/
    private String channel;
    /*Name-Componete de la aplicacion cliente*/
    private String idClientAplication;
    /*id del tipo de identificacion de la persona o entidad que realiza la transacion*/
    private String typeIdPerson;
    /*Identificacion de la persona*/
    private String idPerson;
    /*Nombre del componente que registra la traza*/
    private String nameComponent;
    /*Url del servicio consumido*/
    private String urlServices;
    /*Identificador del servicio consumido*/
    private String idServices;
    /*Parametros de entrada*/
    private String parametersIn; // header - body - parametros
    /*Parametros de salida*/
    private String parametersOut;
    /*Codigo de respuesta del servicio.*/
    private String codeResponse;
    /*Mensaje de respuesta*/
    private String messageResponse;
    /*Detalle de la traza*/
    private String detailTrace;
    /*Tiempo en milisegundos cuando fue consumido*/
    private long  timeConsumeService;
    /*Tiempo en milisegundos cuando respondio*/
    private long timeResponseService;
    /*Ip de consumo*/
    private String ip;
}
