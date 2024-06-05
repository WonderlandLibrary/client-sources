package gq.vapu.czfclient.IRC;


import gq.vapu.czfclient.API.Value.Mode;
import gq.vapu.czfclient.Manager.ModuleManager;
import gq.vapu.czfclient.Module.Module;
import gq.vapu.czfclient.Module.ModuleType;
import gq.vapu.czfclient.Module.Modules.Blatant.Criticals;
import gq.vapu.czfclient.Util.Helper;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;

import static gq.vapu.czfclient.IRC.ServerList.*;

public class IRC extends Module {
    public static Mode mode = new Mode("Channel", "Channel", ServerList.values(), Master);
    public BufferedReader reader;
    public static Socket socket;
    public static PrintWriter pw;
    static InputStream in;
    public IRC() {
        super("IRC", new String[]{"irc"},ModuleType.Player);
        this.addValues(this.mode);
    }


    @Override
    public void onDisable(){
        sendMessage("CLOSE");
        Helper.sendMessage("You exited IRC");
    }
    @Override
    public void onEnable(){
        new IRCThread().start();
    }


    public static void handleInput() {
        byte[] data=new byte[1024];
        try {
            int len=in.read(data);
            String ircmessage = new String(data,0,len);
            ircmessage = ircmessage.replaceAll("\n","");
            ircmessage = ircmessage.replaceAll("\r","");
            ircmessage = ircmessage.replaceAll("\t","");

            if(ircmessage.equals("CLOSE")){
                Helper.sendMessage("¡ì4IRC Server Closed");
                ModuleManager.getModuleByClass(IRC.class).setEnabled(false);
                return;
            }else if(ircmessage.equals("You has been Kicked form this server!")){
                System.exit(99);
            }
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(ircmessage));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void connect(){
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Connecting..."));
        try {
            int Port = 114514;
            if(mode.getValue() == Master)
            {
                Port = 11852;
            } else if(mode.getValue() == Server1) {
                Port = 11853;
            } else if(mode.getValue() == Server2) {
                Port = 11854;
            }
            socket = new Socket("zn.lankodata.com", Port);
            in=socket.getInputStream();
            pw = new PrintWriter(socket.getOutputStream(), true);
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("succeed"));
            Helper.sendMessage("Enter .irc or .i Send Message");
            pw.println(mc.thePlayer.getName() +"@"+ " " +"@Client@"+Minecraft.getMinecraft().thePlayer.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return;
    }


    public static void sendMessage(String msg){
        pw.println(msg);
    }

}
enum ServerList {
    Master,
    Server1,
    Server2
}