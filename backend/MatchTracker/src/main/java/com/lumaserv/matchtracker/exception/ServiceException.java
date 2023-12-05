package com.lumaserv.matchtracker.exception;

import com.lumaserv.matchtracker.http.response.Response;
import lombok.Getter;

public class ServiceException extends Exception {

    @Getter
    final int httpStatus;
    @Getter
    String error;

    public ServiceException(String message, String error) {
        this(500, message);
        this.error = error;
    }

    public ServiceException(int httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public ServiceException(int status, String error, String message) {
        super(message);
        this.httpStatus = status;
        this.error = error;
    }


    public Response toResponse() {
        return Response.error(httpStatus, getMessage());
    }

}
