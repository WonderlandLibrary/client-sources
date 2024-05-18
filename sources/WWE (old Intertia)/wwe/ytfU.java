package wwe;

import wwe.utils.*;
import java.net.*;
import java.io.*;
import com.google.gson.*;
import net.minecraft.entity.passive.*;

public class ytfU extends HAxG
{
    public ytfU() {
        super("mobowner");
    }
    
    @Override
    public void lpzH(final String s, final String[] array) {
        try {
            hSNE.lyvR(this);
            if (ytfU.\u200b\u200e\u2003\u200f) {
                throw null;
            }
        }
        catch (Exception ex) {
            this.kVjF(ex);
        }
    }
    
    @Override
    public String UiIp() {
        return "Tells you the username who owns a mob";
    }
    
    @Override
    public String ZlRY() {
        return "mobowner";
    }
    
    @oqrr
    public void KnYY(final omqH omqH) {
        if (Wrapper.mc.objectMouseOver.entityHit != null) {
            if (Wrapper.mc.objectMouseOver.entityHit instanceof EntityTameable) {
                try {
                    final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new URL("https://api.mojang.com/user/profiles/" + ((EntityTameable)Wrapper.mc.objectMouseOver.entityHit).getOwnerId().toString().replaceAll("-", "") + "/names").openStream()));
                    final StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        sb.append(line + "\n");
                        if (ytfU.\u200b\u200e\u2003\u200f) {
                            throw null;
                        }
                    }
                    final String asString = new JsonParser().parse(sb.toString().trim()).getAsJsonArray().get(new JsonParser().parse(sb.toString().trim()).getAsJsonArray().size() - 1).getAsJsonObject().get("name").getAsString();
                    bufferedReader.close();
                    HAxG.TuzD("Thats " + asString + "'s " + ((EntityTameable)Wrapper.mc.objectMouseOver.entityHit).getName());
                    if (ytfU.\u200b\u200e\u2003\u200f) {
                        throw null;
                    }
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                    HAxG.TuzD("We have a problem...");
                }
            }
            if (Wrapper.mc.objectMouseOver.entityHit instanceof AbstractHorse) {
                try {
                    final BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(new URL("https://api.mojang.com/user/profiles/" + ((AbstractHorse)Wrapper.mc.objectMouseOver.entityHit).getOwnerUniqueId().toString().replaceAll("-", "") + "/names").openStream()));
                    final StringBuilder sb2 = new StringBuilder();
                    String line2;
                    while ((line2 = bufferedReader2.readLine()) != null) {
                        sb2.append(line2 + "\n");
                        if (ytfU.\u200b\u200e\u2003\u200f) {
                            throw null;
                        }
                    }
                    final String asString2 = new JsonParser().parse(sb2.toString().trim()).getAsJsonArray().get(new JsonParser().parse(sb2.toString().trim()).getAsJsonArray().size() - 1).getAsJsonObject().get("name").getAsString();
                    bufferedReader2.close();
                    HAxG.TuzD("Thats " + asString2 + "'s " + ((AbstractHorse)Wrapper.mc.objectMouseOver.entityHit).getName());
                    if (ytfU.\u200b\u200e\u2003\u200f) {
                        throw null;
                    }
                }
                catch (Exception ex2) {
                    ex2.printStackTrace();
                    HAxG.TuzD("We have a problem...");
                }
            }
        }
        hSNE.kduK(this);
    }
}
