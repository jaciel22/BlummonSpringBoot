package com.blummon.users.vo;

import com.blummon.users.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.UUID;


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VoUserResponse {
    private String mensaje;

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    private String folio;

    public void generarFolioAutomatico() {
        this.folio = UUID.randomUUID().toString(); // Genera un UUID y lo convierte a String
    }


    public VoResultado getResultado() {
        return resultado;
    }

    public void setResultado(VoResultado resultado) {
        this.resultado = resultado;
    }

    public VoResultado resultado;


}





