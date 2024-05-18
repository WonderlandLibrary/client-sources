package com.darkcart.xdolf.altmanager;

import net.minecraft.util.Session;

/**
 * Author: Opim10
 * Version: 1
 * Use: Login to Minecraft with Yggdrasil;
 */
public class YggdrasilAuthenticator {
    public YggdrasilPayload Payload = new YggdrasilPayload();
    public String Username;
    public String Password;
    private Session session;

    public YggdrasilAuthenticator(String Username, String Password) {
        this.Username = Username;
        this.Password = Password;
    }

    public boolean login() {
        if (Password != "" && Password != null) {
            Session AuthResponse = Payload.loginPassword(this.Username, this.Password);
            if (AuthResponse != null) {
                session = (AuthResponse);
                return true;
            }

        } else {
            Session AuthResponseCrack = Payload.loginCrack(this.Username);
            session = (AuthResponseCrack);
            return true;
        }

        return false;
    }

    public Session getSession() {
        return session;
    }
}