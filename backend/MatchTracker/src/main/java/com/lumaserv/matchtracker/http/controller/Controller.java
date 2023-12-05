package com.lumaserv.matchtracker.http.controller;


import com.lumaserv.matchtracker.http.response.Response;
import com.lumaserv.matchtracker.model.Model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.javawebstack.abstractdata.AbstractObject;
import org.javawebstack.http.router.Exchange;
import org.javawebstack.orm.Repo;
import org.javawebstack.orm.query.Query;
import org.javawebstack.webutils.Resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * This is a convenience class that subclasses can call upon
 * to return property changes back to the models.
 * There's also a defaultList method implemented to return the objects in the database as a List
 * which can be paginated and filtered and where you can do your search in.
 */
public abstract class Controller {

    /**
     * @param exchange
     * @param model
     * @param filter
     * @param <T>
     * @return
     */

    public <T extends Model> DefaultListResult<T> defaultList(Exchange exchange, Class<T> model, Function<Query<T>, Query<T>> filter) {
        Query<T> query = Repo.get(model).query().accessible(exchange);

        if (filter != null) {
            query = filter.apply(query);
        }
        if (exchange.getQueryParameters().has("search")) {
            query.search(exchange.query("search"));
        }
        if (exchange.getQueryParameters().hasObject("filter")) {
            AbstractObject filterObject = exchange.getQueryParameters().object("filter");
            Map<String, String> filters = new HashMap<>();
            filterObject.forEach((k, v) -> filters.put(k, v.string()));
            query.filter(filters);
        }

        if (exchange.getQueryParameters().hasString("sort")) {
            String[] sortFields = exchange.getQueryParameters().string("sort").split(",");
            for (String f : sortFields) {
                boolean descending = f.startsWith("!");
                String name = f.startsWith("!") ? f.substring(1) : f;
                query.order(name, descending);
            }
        }

        int total = query.count();
        query.select("*");
        int page = 1;
        int pageSize = 10;
        if (exchange.getQueryParameters().has("page") || exchange.getQueryParameters().has("page_size")) {
            if (exchange.getQueryParameters().has("page")) {
                page = parseInt(exchange.query("page"), 1, Integer.MAX_VALUE);
                if (page == -1)
                    throw new RuntimeException("Invalid page");
            }
            if (exchange.getQueryParameters().has("page_size")) {
                pageSize = parseInt(exchange.query("page_size"), 0, Integer.MAX_VALUE);
                if (pageSize == -1)
                    throw new RuntimeException("Invalid page size");
            }
            query.offset((page - 1) * pageSize).limit(pageSize);
        }
        return new DefaultListResult<>(
                query.all(),
                page,
                pageSize,
                total
        );
    }

    private static int parseInt(String s, int min, int max) {
        try {
            int v = Integer.parseInt(s);
            if (v < min || v > max)
                return -1;
            return v;
        } catch (NumberFormatException ignored) {
            return -1;
        }
    }

    public Resource.Context defaultContext() {
        return new Resource.Context();
    }

    public <T extends Model> Response defaultListResponse(Exchange exchange, Class<T> model, Class<? extends Resource<T>> resourceType, Function<Query<T>, Query<T>> filter) {
        return defaultListResponse(exchange, model, resourceType, defaultContext(), filter);
    }

    public <T extends Model> Response defaultListResponse(Exchange exchange, Class<T> model, Class<? extends Resource<T>> resourceType, Resource.Context context, Function<Query<T>, Query<T>> filter) {
        DefaultListResult<T> result = defaultList(exchange, model, filter);
        return Response.success()
                .setData(resourceType, result.list, context)
                ;

    }

    @AllArgsConstructor
    @Getter
    public static class DefaultListResult<T extends Model> {
        List<T> list;
        int page;
        int pageSize;
        int total;
    }
}
