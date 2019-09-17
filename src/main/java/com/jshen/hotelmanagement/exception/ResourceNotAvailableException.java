package com.jshen.hotelmanagement.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
@Getter
@Setter
public class ResourceNotAvailableException extends RuntimeException {
    private String resourceName;
    private String fieldName;
    private Object fieldValue;

//TODO: give out clearer info
    public ResourceNotAvailableException(String resourceName, String fieldName, Object fieldValue){
        super (String.format(" %s not available with %s : '%s'", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}

