package client.metaware.client;

import client.metaware.api.utils.MinecraftUtil;

public class ClientInfo implements MinecraftUtil {

    private final String clientName, clientDevelopers, clientBuild, commandPrefix, clientNamePrefix;

    public ClientInfo(String clientName, String clientBuild, String clientDevelopers, String commandPrefix, String clientNamePrefix){
        this.clientName = clientName;
        this.clientBuild = clientBuild;
        this.clientDevelopers = clientDevelopers;
        this.commandPrefix = commandPrefix;
        this.clientNamePrefix = clientNamePrefix;
    }


    public String getCommandPrefix() {
        return commandPrefix;
    }
    public String getClientBuild() {
        return clientBuild;
    }
    public String getClientDevelopers() {
        return clientDevelopers;
    }
    public String getClientName() {
        return clientName;
    }
    public String getClientTitle(){
        return clientName + " Build: " + clientBuild;
    }
    public String getClientNamePrefix() {
        return clientNamePrefix;
    }
}
