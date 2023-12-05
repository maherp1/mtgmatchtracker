package com.lumaserv.matchtracker.util.accessible;

import org.javawebstack.orm.Accessible;
import org.javawebstack.orm.Model;
import org.javawebstack.orm.query.Query;
import org.javawebstack.orm.query.QueryGroup;

public class DefaultAccessible implements Accessible {
    @Override
    public <T extends Model> QueryGroup<T> access(Query<T> query, QueryGroup<T> queryGroup, Object o) {
        return queryGroup;
    }
}
