/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.eventbus.Subscribe
 */
package cc.diablo.command.impl;

import cc.diablo.command.Command;
import cc.diablo.event.impl.ChatEvent;
import cc.diablo.helpers.render.ChatHelper;
import com.google.common.eventbus.Subscribe;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

public class SkinCommand
extends Command {
    public static final String uuidURL = "https://sessionserver.mojang.com/session/minecraft/profile/";
    public static final String nameURL = "https://api.mojang.com/users/profiles/minecraft/";
    public static String skin = null;

    public SkinCommand() {
        super("Skin", "Changes the player skin to any player's skin");
    }

    @Subscribe
    public void onCommand(ChatEvent e) {
        String[] message = e.message.split(" ");
        if (message[0].equals("skin")) {
            try {
                URL url = new URL("http://www.yahoo.com/image_to_read.jpg");
                BufferedInputStream in = new BufferedInputStream(url.openStream());
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                byte[] buf = new byte[1024];
                int n = 0;
                while (-1 != (n = ((InputStream)in).read(buf))) {
                    out.write(buf, 0, n);
                }
                out.close();
                ((InputStream)in).close();
                byte[] response = out.toByteArray();
                FileOutputStream fos = new FileOutputStream("C://borrowed_image.jpg");
                fos.write(response);
                fos.close();
            }
            catch (Exception exception) {
                ChatHelper.addChat("A error occurred");
                exception.printStackTrace();
            }
        }
    }
}

