package com.nl.gerimedica.app.handler;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class GenericErrorMessage {
    private int status;
    private String message;
}
