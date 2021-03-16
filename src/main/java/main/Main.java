package main;

import configuration.UnpackingStringConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Scanner;

public class Main {

    public static void main(String[] args){

        ApplicationContext context = new AnnotationConfigApplicationContext(UnpackingStringConfiguration.class);
        Scanner scan = context.getBean("scanner", Scanner.class);
        InputHandler inputHandler = context.getBean("inputHandler", InputHandler.class);
        inputHandler.handle(scan);
    }
}
