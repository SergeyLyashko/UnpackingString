package configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.Scanner;

@ComponentScan(basePackages = {"handlers", "main", "out"})
@Configuration
public class UnpackingStringConfiguration {

    @Bean
    public Scanner scanner(){
        return new Scanner(System.in);
    }

    @Bean
    @Scope("prototype")
    public StringBuilder stringBuilder(){
        return new StringBuilder();
    }
}
