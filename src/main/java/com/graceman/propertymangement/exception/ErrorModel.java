package com.graceman.propertymangement.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@AllArgsConstructor
@NoArgsConstructor
public class ErrorModel {

    private String code;
    private String message;

}
