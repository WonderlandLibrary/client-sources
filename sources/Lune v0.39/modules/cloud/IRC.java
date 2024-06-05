package me.superskidder.lune.modules.cloud;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.superskidder.lune.Lune;
import me.superskidder.lune.guis.clickgui.IRCgui;
import me.superskidder.lune.modules.world.IRCThread;
import me.superskidder.lune.utils.client.DevUtils;
import me.superskidder.lune.utils.client.HWIDUtil;
import me.superskidder.lune.utils.player.PlayerUtil;
import me.superskidder.lune.utils.irc.IRCMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class IRC {
    public static BufferedReader reader;
    public static Socket socket;
    public static PrintWriter pw;

    public IRC() {
    }

    public static void handleInput() {
        try {
            String ircMessage = reader.readLine();

            if (ircMessage == null) {
                return;
            }

            switch (ircMessage) {
                case "CLOSE":
                    if (Minecraft.getMinecraft().theWorld != null) {
                        PlayerUtil.sendMessage("§4IRC服务器关闭");
                    }
                    return;
                case "你被踢出了IRC":
                    System.exit(99);
                case "HeartPacketNeeded":
                    sendMessage("MSG@" + (Minecraft.getMinecraft().thePlayer == null ? "Null" : Minecraft.getMinecraft().thePlayer.getName()) + "@" + ChatFormatting.BLUE + Lune.CLIENT_NAME + ChatFormatting.WHITE + "@" + "Heart!Give you,my dear");

                    return;
                case "\247d[IRC]\2477You do not send enough heart packet":
                    return;
                default:
                    break;
            }
            if (Minecraft.getMinecraft().thePlayer != null) {
                Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(ircMessage));
            }
            if (ircMessage.contains(":")) {
                IRCgui.msgs.add(new IRCMessage(ircMessage.split(":")[0], ircMessage.split(":")[1]));
            } else {
                IRCgui.msgs.add(new IRCMessage("System", ircMessage));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean connect(String username, String password) {
        try {
//            socket = new Socket("127.0.0.1", 41344);
            socket = new Socket("43.249.192.204", 41344);
            reader = new MyBufferedReader(new InputStreamReader(socket.getInputStream()));
            pw = new MyPrintWriter(socket.getOutputStream(), true);
            if (Minecraft.getMinecraft().theWorld != null) {
                PlayerUtil.sendMessage("Connected");
            } else {
                System.out.println("Connect successfully");
            }
            pw.println(username + "@" + password + "@" + HWIDUtil.getHWID() + "@" + ChatFormatting.BLUE + "Lune@" + (Lune.mc.thePlayer == null ? "Null" : Lune.mc.thePlayer.getName()));
//            pw.println(username + "@" + password + "@" + HWIDUtil.getHWID() + "@" + ChatFormatting.BLUE + "Lune@" + Lune.mc.thePlayer.getName());
            new IRCThread().start();
            return true;
        } catch (Exception e) {
        	if(DevUtils.isDev()) {
                e.printStackTrace();
                return false;
        	}
            System.exit(0);
        }
        return false;
    }


    public static void sendMessage(String msg) {
        System.out.println(msg);
        pw.println(msg);
    }

    static class MyPrintWriter extends PrintWriter {
        public MyPrintWriter(Writer out) {
            super(out);
        }

        public MyPrintWriter(Writer out, boolean autoFlush) {
            super(out, autoFlush);
        }

        public MyPrintWriter(OutputStream out) {
            super(out);
        }

        public MyPrintWriter(OutputStream out, boolean autoFlush) {
            super(out, autoFlush);
        }

        public MyPrintWriter(String fileName) throws FileNotFoundException {
            super(fileName);
        }

        public MyPrintWriter(String fileName, String csn) throws FileNotFoundException, UnsupportedEncodingException {
            super(fileName, csn);
        }

        public MyPrintWriter(File file) throws FileNotFoundException {
            super(file);
        }

        public MyPrintWriter(File file, String csn) throws FileNotFoundException, UnsupportedEncodingException {
            super(file, csn);
        }

        @Override
        public void println(String msg) {
            Base64.Encoder encoder = Base64.getEncoder();
            byte[] msgByte = msg.getBytes(StandardCharsets.UTF_8);

            msg = encoder.encodeToString(msgByte);
            super.println(msg);
        }
    }

    static class MyBufferedReader extends BufferedReader {
        public MyBufferedReader(Reader in, int sz) {
            super(in, sz);
        }

        public MyBufferedReader(Reader in) {
            super(in);
        }

        @Override
        public String readLine() throws IOException {
            try {
                String msg = super.readLine();
                msg = this.cleanStr(msg);

                Base64.Decoder decoder = Base64.getDecoder();
                msg = new String(decoder.decode(msg), StandardCharsets.UTF_8);

                msg = this.cleanStr(msg);
                return msg;
            } catch (Exception e) {
                return null;
            }
        }

        public String cleanStr(String str) {
            str = str.replaceAll("\n", "");
            str = str.replaceAll("\r", "");
            str = str.replaceAll("\t", "");
            return str;
        }
    }
}
