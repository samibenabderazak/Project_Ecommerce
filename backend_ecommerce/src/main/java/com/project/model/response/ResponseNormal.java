package com.project.model.response;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseNormal {
    private String msg;
    private HttpStatus httpStatus;
}
