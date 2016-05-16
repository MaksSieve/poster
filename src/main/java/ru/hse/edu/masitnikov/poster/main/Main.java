package ru.hse.edu.masitnikov.poster.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import ru.hse.edu.masitnikov.poster.core.Poster;

@Component
public class Main {

    @Autowired
    private Poster poster;

    public static void main(final String[] args) throws Exception {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");

        Main main = ctx.getBean(Main.class);
        main.start();
    }

    public void start(){
        try {
            poster.posting();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}