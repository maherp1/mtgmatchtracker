package com.lumaserv.matchtracker.http.response;

import lombok.Getter;
import lombok.Setter;
import org.javawebstack.webutils.Resource;

import java.util.List;

@Getter
@Setter
public class Response {

    transient int status = 200;
    boolean success = true;
    String error;
    Object data;

    public <T> Response setData(Class<? extends Resource<T>> resourceType, T data) {

        return setData(resourceType, data, new Resource.Context());
    }

    public <T> Response setData(Class<? extends Resource<T>> resourceType, T data, Resource.Context context) {
        setData(Resource.make(resourceType, context, data));
        return this;
    }

    public <T> Response setData(Class<? extends Resource<T>> resourceType, List<T> data) {

        return setData(resourceType, data, new Resource.Context());
    }

    public <T> Response setData(Class<? extends Resource<T>> resourceType, List<T> data, Resource.Context context) {
        setData(Resource.make(resourceType, context, data));
        return this;
    }

    public static Response success() {
        return new Response();
    }

    public static <T> Response created(Class<? extends Resource<T>> resourceType, T data, Resource.Context context) {
        return new Response().setStatus(201).setData(resourceType, data, context);
    }

    public static <T> Response created(Class<? extends Resource<T>> resourceType, T data) {
        return new Response().setStatus(201).setData(resourceType, data);
    }

    public static Response error(int status, String message) {
        return new Response().setSuccess(false).setStatus(status).setError(message);
    }

}

