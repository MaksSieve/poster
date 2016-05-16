package ru.hse.edu.masitnikov.poster.main;

import org.jboss.jandex.*;

import java.util.Scanner;
import java.util.Timer;

public class ConsoleThread implements Runnable {

    public ConsoleThread(Timer timer) {
        setTimer(timer);

        thread = new Thread(this, "Console thread");

        thread.run();
    }

    private Timer timer;

    private Thread thread;

    public void run() {
        Scanner scanner = new Scanner(System.in);
        String cmd;
        do{
            cmd = scanner.nextLine();
        }while(cmd.equals("exit"));

        timer.cancel();
        Main.exit(0);
    }

    public Timer getTimer() {
        return timer;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }
}
