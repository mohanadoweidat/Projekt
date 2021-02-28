import Controller.ClientController;

import javax.swing.*;
import java.io.FileNotFoundException;

public class Main
{
    public static void main(String[] args) throws FileNotFoundException
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    ClientController clientController = new ClientController();
                }
                catch (FileNotFoundException e)
                {
                    e.printStackTrace();
                }
            }
        });


    }
}

