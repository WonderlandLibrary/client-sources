package club.bluezenith.core.user;

import net.superblaubeere27.masxinlingvonta.annotation.Outsource;

public class ClientUser {
    private String username, alert, privateApiKey;
    private int uid;

    private ClientRank rank;

    public ClientUser(String username, int uid, ClientRank rank) {
        startup(username, uid, rank);
    }

    @Outsource
    private void startup(String username, int uid, ClientRank rank) {
        if(uid <= 0) throw new IllegalStateException("Client connection failed"); //to confuse whoever might try cracking lol

        this.username = username;
        this.uid = uid;
        this.rank = rank;
    }

    public String getUsername() {
        return this.username;
    }

    public int getUID() {
        return this.uid;
    }

    public ClientRank getRank() {
        return this.rank == null ? ClientRank.USER : this.rank;
    }

    public void displayAlert(String alert) {
        if(this.alert == null) {
            this.alert = alert;
        }
    }

    public void setApiKey(String apiKey) {
        this.privateApiKey = apiKey;
    }

    public String getApiKey() {
        return this.privateApiKey;
    }

    @Override
    public String toString() {
        return username;
    }
}
