package com.example.admin.bubblemaths;

public class BackgroundThread {
    static void run(Runnable runnable) {
        Thread thread = new Thread(runnable);
        thread.start();
    }
}
