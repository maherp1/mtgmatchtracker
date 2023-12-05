package com.lumaserv.matchtracker;

import com.lumaserv.matchtracker.http.controller.Controller;
import com.lumaserv.matchtracker.http.middleware.AuthMiddleware;
import com.lumaserv.matchtracker.http.middleware.TokenMiddleware;
import com.lumaserv.matchtracker.model.Model;
import com.lumaserv.matchtracker.util.accessible.DefaultAccessible;
import lombok.Getter;
import org.javawebstack.http.router.HTTPRouter;
import org.javawebstack.http.router.transformer.response.SerializedResponseTransformer;
import org.javawebstack.http.router.undertow.UndertowHTTPSocketServer;
import org.javawebstack.orm.ORM;
import org.javawebstack.orm.ORMConfig;
import org.javawebstack.orm.connection.MySQL;
import org.javawebstack.orm.connection.pool.MinMaxScaler;
import org.javawebstack.orm.connection.pool.SQLPool;
import org.javawebstack.orm.exception.ORMConfigurationException;
import org.javawebstack.orm.mapper.AbstractDataTypeMapper;
import org.javawebstack.webutils.config.Config;
import org.javawebstack.webutils.config.EnvFile;
import org.javawebstack.webutils.middleware.CORSPolicy;
import org.javawebstack.webutils.middleware.ModelBindCheckMiddleware;
import org.javawebstack.webutils.modelbind.ModelBindParamTransformer;

import java.io.File;

@Getter
public class MatchTracker {

    public static MatchTracker INSTANCE;

    final HTTPRouter server;
    final Config config;

    public MatchTracker() throws ORMConfigurationException {

        config = new Config().add(new EnvFile(new File(".env")).withVariables(), Config::basicEnvMapping);
        setupDatabase();
        server = new HTTPRouter(new UndertowHTTPSocketServer())
                .port(config.getInt("http.port", 80))
                .routeParamTransformer(new ModelBindParamTransformer().setAccessorAttribName("player"))
                .beforeInterceptor(new TokenMiddleware())
                .beforeInterceptor(new CORSPolicy("*"))
                .routeParamTransformer(new ModelBindParamTransformer())
                .responseTransformer(new SerializedResponseTransformer().ignoreStrings())
                .middleware("auth", new AuthMiddleware())
                .middleware("bind",new ModelBindCheckMiddleware())
                .controller(Controller.class, Controller.class.getPackage());
    }


    public void start() {
        server.start().join();
    }


    public void setupDatabase() throws ORMConfigurationException {
        SQLPool pool = new SQLPool(new MinMaxScaler(2, 5), () -> new MySQL(
                config.get("database.host"),
                config.getInt("database.port", 3306),
                config.get("database.name"),
                config.get("database.username"),
                config.get("database.password")
        ));
        ORMConfig ormConfig = new ORMConfig()
                .setDefaultSize(50)
                .addTypeMapper(new AbstractDataTypeMapper());
        ORM.register(Model.class.getPackage(), pool, ormConfig);
        ORM.getRepos().forEach(r -> r.setAccessible(new DefaultAccessible()));
        ORM.autoMigrate();
    }

    public static MatchTracker getInstance(){
        return INSTANCE;
    }

    public static void main(String[] args) throws ORMConfigurationException {
        MatchTracker instance = new MatchTracker();
        instance.start();
    }
}
