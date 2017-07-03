package ua.backend.task;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ImportResource;
import ua.backend.task.DB.DbService;

/**
 * Created by Slavik on 30.06.17.
 */
@ImportResource("classpath:/spring-config/spring-rest-config.xml")
public class Application {

    public static void main(String[] args) {
        ApplicationContext app = SpringApplication.run(new Object[]{Application.class, Config.class}, args);
        DbService dbs = app.getBean(DbService.class);
        dbs.create();
    }
}
