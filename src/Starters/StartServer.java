package Starters;

import Controller.ServerController;

import java.awt.*;
import java.io.IOException;

public class StartServer {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                new ServerController();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
