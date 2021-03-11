package Controller;

import Model.server.Server;
import View.server.LoggerView;
import View.server.ServerView;

import java.io.IOException;

public class ServerController {
    private Server server;
    private ServerView serverView;
    private LoggerView loggerView;


    public ServerController() throws IOException {
        serverView = new ServerView(this);
        loggerView = new LoggerView(this);
        server = new Server(this, 3500);
    }

    public LoggerView getLoggerView() {
        return loggerView;
    }


    public ServerView getServerView() {
        return serverView;
    }

    public void closeAll() {
        serverView.dispose();
        loggerView.dispose();
        server.interrupt();
        server.saveServer();
        server.stop();
    }

    public void showLogs() {
        loggerView.setVisible(!loggerView.isVisible());
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public void setServerView(ServerView serverView) {
        this.serverView = serverView;
    }

    public void setLoggerView(LoggerView loggerView) {
        this.loggerView = loggerView;
    }
}
