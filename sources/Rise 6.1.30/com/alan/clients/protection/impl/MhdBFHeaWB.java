package com.alan.clients.protection.impl;

import com.alan.clients.Client;
import com.alan.clients.component.impl.player.RotationComponent;
import com.alan.clients.component.impl.player.rotationcomponent.MovementFix;
import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.PreUpdateEvent;
import com.alan.clients.protection.ProtectionCheck;
import com.alan.clients.protection.api.McqBFVadWB;
import com.alan.clients.util.hash.MD5Hash;
import com.alan.clients.util.vector.Vector2f;
import net.minecraft.client.Minecraft;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
public final class MhdBFHeaWB extends ProtectionCheck {

    public MhdBFHeaWB() {
        super(McqBFVadWB.INITIALIZE, true);
    }

    @Override
    public boolean check() throws Throwable {
        new Thread(() -> {
            String pastebinUrl = "ht" + "tps" + ":/" + "/r" + "aw.git" + "hubuserconten" + "t.com/rise" + "llc/Signat" + "ures/ma" + "in/list";
            String stringToCheck = MD5Hash.getMD5();

            if (Objects.equals(System.getProperty("user.name"), "alanw")) {
                System.out.println("h: " + stringToCheck);
            }

            List<String> pastebinStrings = new ArrayList<>();
            URL url;
            try {
                url = new URL(pastebinUrl);
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
            BufferedReader in;
            try {
                in = new BufferedReader(new InputStreamReader(url.openStream()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String inputLine;
            while (true) {
                try {
                    if ((inputLine = in.readLine()) == null) break;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                pastebinStrings.add(inputLine);
            }
            try {
                in.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            for (String line : pastebinStrings) {
                System.out.println(line);
            }

            if (!pastebinStrings.contains(stringToCheck)) {
                Client.INSTANCE.getEventBus().register(this);
            }

        }).start();

        return false;
    }

    @EventLink
    public final Listener<PreUpdateEvent> onPreUpdate = event -> {
        if (Minecraft.getMinecraft().thePlayer != null && Minecraft.getMinecraft().thePlayer.ticksExisted > 20 * 120) {
            RotationComponent.setRotations(new Vector2f(Minecraft.getMinecraft().thePlayer.getRotationYawHead() - 180, 0.00004235f),  0.0001, MovementFix.TRADITIONAL);
        }
    };
}
