package Starters;

import Controller.ClientController;
import Model.Shared.User;

import java.io.IOException;

public class StartClient2 {
    public static void main(String[] args) throws IOException {


        User u = User.getUserFromFile();
        if (u == null) {
            new ClientController();
        } else {
            new ClientController(u);
        }
    }

}
