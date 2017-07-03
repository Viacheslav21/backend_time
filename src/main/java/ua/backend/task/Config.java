package ua.backend.task;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by Slavik on 30.06.17.
 */
@ComponentScan
@EnableAutoConfiguration
@ConfigurationProperties
public class Config {
}
