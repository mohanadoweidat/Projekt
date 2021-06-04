package Controller;

import Model.server.Server;
import View.server.LoggerView;
import View.server.ServerView;

import java.io.IOException;

public class ServerController {
    private Server server;
    private ServerView serverView;
    private LoggerView loggerView;

    /**
     * Constructor.
     * @throws IOException exception.
     */
    public ServerController() throws IOException {
        serverView = new ServerView(this);
        loggerView = new LoggerView(this);
        server = new Server(this, 3500);
    }


    /**
     * This function will disconnect the server and dispose the view(GUI).
     */
    public void closeAll() {
        serverView.dispose();
        loggerView.dispose();
        server.interrupt();
        server.saveServer();
        server.stop();
    }

    /**
     * This function will show the logs.
     */
    public void showLogs() {
        loggerView.setVisible(!loggerView.isVisible());
    }

    /**
     * Get Server object.
     * @return Server object.
     */
    public Server getServer() {
        return server;
    }

    /**
     * Get LoggerView object.
     * @return LoggerView object.
     */
    public LoggerView getLoggerView() {
        return loggerView;
    }


    /**
     * Get ServerView object.
     * @return ServerView object.
     */
    public ServerView getServerView() {
        return serverView;
    }
}
