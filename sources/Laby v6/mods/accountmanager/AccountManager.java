package mods.accountmanager;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.labystudio.modapi.EventHandler;
import de.labystudio.modapi.Listener;
import de.labystudio.modapi.ModAPI;
import de.labystudio.modapi.Module;
import de.labystudio.modapi.events.GameTickEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;
import mods.accountmanager.gui.AccountManagerGUI;
import mods.accountmanager.gui.LoginGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ImageBufferDownload;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;

public class AccountManager extends Module implements Listener
{
    public static List<Account> accounts = new ArrayList();
    public static final String VERSION = "1.0";
    public static final Map<String, ResourceLocation> playerHeads = new HashMap();

    @EventHandler
    public void onTick(GameTickEvent e)
    {
        if (LoginGui.openNewAccountManagerGui != null)
        {
            Minecraft.getMinecraft().displayGuiScreen(new AccountManagerGUI(LoginGui.openNewAccountManagerGui));
            LoginGui.openNewAccountManagerGui = null;
        }
    }

    public void onEnable()
    {
        ModAPI.registerListener(this);
        ModAPI.addSettingsButton("AccountManager", new AccountManagerGUI());
        JsonParser jsonparser = new JsonParser();
        String s = null;

        try
        {
            s = IOUtils.toString((InputStream)(new FileInputStream("launcher_profiles.json")));
        }
        catch (FileNotFoundException filenotfoundexception)
        {
            filenotfoundexception.printStackTrace();
        }
        catch (IOException ioexception)
        {
            ioexception.printStackTrace();
        }

        if (s != null)
        {
            JsonObject jsonobject = jsonparser.parse(s).getAsJsonObject();
            JsonObject jsonobject1 = jsonobject.get("authenticationDatabase").getAsJsonObject();

            for (Entry<String, JsonElement> entry : jsonobject1.entrySet())
            {
                JsonObject jsonobject2 = ((JsonElement)entry.getValue()).getAsJsonObject();
                accounts.add(new Account(jsonobject2.get("displayName").getAsString(), jsonobject2.get("accessToken").getAsString(), UUID.fromString(jsonobject2.get("uuid").getAsString())));
                String s1 = jsonobject2.get("displayName").getAsString();
                loadPlayerHead(s1);
                System.out.println("[AccountManager] Account " + jsonobject2.get("displayName").getAsString() + " has been load!");
            }
        }
    }

    public static void loadPlayerHead(String playerName)
    {
        ResourceLocation resourcelocation = new ResourceLocation("images/" + playerName);
        ThreadDownloadImageData threaddownloadimagedata = new ThreadDownloadImageData((File)null, String.format("https://minotar.net/skin/%s.png", new Object[] {StringUtils.stripControlCodes(playerName)}), DefaultPlayerSkin.getDefaultSkin(UUID.nameUUIDFromBytes(("OfflinePlayer:" + playerName).getBytes(Charsets.UTF_8))), new ImageBufferDownload());
        Minecraft.getMinecraft().getTextureManager().loadTexture(resourcelocation, threaddownloadimagedata);
        playerHeads.put(playerName, resourcelocation);
    }

    public static boolean containsAccount(String name)
    {
        for (Account account : accounts)
        {
            if (name.equals(account.getUserName()))
            {
                return true;
            }
        }

        return false;
    }

    public static void drawTexturedModalRect(double xCoord, double yCoord, double minU, double minV, double maxU, double maxV)
    {
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos(xCoord + 0.0D, yCoord + (double)((float)maxV), 0.0D).tex((double)((float)(minU + 0.0D) * f), (double)((float)(minV + maxV) * f1)).endVertex();
        worldrenderer.pos(xCoord + (double)((float)maxU), yCoord + (double)((float)maxV), 0.0D).tex((double)((float)(minU + maxU) * f), (double)((float)(minV + maxV) * f1)).endVertex();
        worldrenderer.pos(xCoord + (double)((float)maxU), yCoord + 0.0D, 0.0D).tex((double)((float)(minU + maxU) * f), (double)((float)(minV + 0.0D) * f1)).endVertex();
        worldrenderer.pos(xCoord + 0.0D, yCoord + 0.0D, 0.0D).tex((double)((float)(minU + 0.0D) * f), (double)((float)(minV + 0.0D) * f1)).endVertex();
        tessellator.draw();
    }
}
