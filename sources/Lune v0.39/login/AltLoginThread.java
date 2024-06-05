/*
 * Decompiled with CFR 0_132.
 */
package me.superskidder.lune.login;

import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;

import java.io.IOException;
import java.net.Proxy;
import java.net.URL;

import me.superskidder.lune.utils.client.HWIDUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.util.HttpUtil;
import net.minecraft.util.Session;

import javax.swing.*;

public class AltLoginThread
extends Thread {
    private final Minecraft mc = Minecraft.getMinecraft();
    private final String password;
    private String status;
    private final String username;
    private boolean test;

    public AltLoginThread(String username, String password, boolean test) {
        super("Alt Login Thread");
        this.username = username;
        this.password = password;
        this.status = "\u00a7eWaiting...";
        this.test = test;
    }

    private final Session createSession(String username, String password) {
        YggdrasilAuthenticationService service = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
        YggdrasilUserAuthentication auth = (YggdrasilUserAuthentication)service.createUserAuthentication(Agent.MINECRAFT);
        auth.setUsername(username);
        auth.setPassword(password);
        try {
            auth.logIn();
            return new Session(auth.getSelectedProfile().getName(), auth.getSelectedProfile().getId().toString(), auth.getAuthenticatedToken(), "mojang");
        }
        catch (AuthenticationException authenticationException) {
            return null;
        }
    }

    public String getStatus() {
        return this.status;
    }

    @Override
    public void run() {
        this.status = "\u00a7eLogging in...";


        if(test){
            try {
//                String usernameEncoded = MD5Utils.encode(this.username);
//                String passwordEncoded = MD5Utils.encode(this.password);
                String result = HttpUtil.get(new URL("http://gaoyusense.buzz/Login/login.php?UserName=" + username + "&PassWord=" + password+"&Hwid="+ HWIDUtil.getHWID()));
                if (result.replaceAll("\r","").equals("Successfully")) {
                    this.status = "\u00a7aLogged in.";
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    this.status = "\u00a7aOK";
                    mc.displayGuiScreen(new GuiMainMenu());
                    //AutoUpdate.startUpdate(username);
                } else {
                    JOptionPane.showInputDialog("这是你的HWID",HWIDUtil.getHWID());
                    this.status = "\u00a7cLogin failed!";
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }

        if (this.username.equals("")) {
            this.status = "\u00a7cName cannot be null!";
            return;
        }
        if (this.password.equals("")) {
            this.mc.session = new Session(this.username, "", "", "mojang");
            this.status = "\u00a7aLogged in. (" + this.username + " - offline name)";
            return;
        }
        Session auth = this.createSession(this.username, this.password);
        if (auth == null) {
            this.status = "\u00a7cLogin failed!";
        } else {
            this.status = "\u00a7aLogged in. (" + auth.getUsername() + ")";
            this.mc.session = auth;
        }
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

