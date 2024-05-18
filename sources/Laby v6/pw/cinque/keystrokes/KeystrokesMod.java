package pw.cinque.keystrokes;

import de.labystudio.modapi.EventHandler;
import de.labystudio.modapi.Listener;
import de.labystudio.modapi.ModAPI;
import de.labystudio.modapi.Module;
import de.labystudio.modapi.events.GameTickEvent;
import de.labystudio.modapi.events.SendChatMessageEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import pw.cinque.keystrokes.render.Key;
import pw.cinque.keystrokes.render.KeystrokesRenderer;
import pw.cinque.keystrokes.settings.Location;

public class KeystrokesMod extends Module implements Listener
{
    private static Minecraft mc = Minecraft.getMinecraft();
    public static int location = 0;
    public static List<Key> keys = new ArrayList();
    public static int width = 0;
    public static int height = 0;
    private static File locationFile;

    public static void reloadKeys()
    {
        keys.clear();
        int i = -1;

        try
        {
            File file1 = new File(mc.mcDataDir + File.separator + "KeystrokesMod", "keys.cfg");

            if (!file1.exists())
            {
                file1.getParentFile().mkdirs();
                file1.createNewFile();

                if (mc.thePlayer != null)
                {
                    mc.thePlayer.addChatMessage(new ChatComponentText("Created a new empty Key configuration file."));
                }

                PrintWriter printwriter = new PrintWriter(new FileOutputStream(file1));
                printwriter.println("# Format: KEY:X-OFFSET:Y-OFFSET");
                printwriter.println("# Auf Deusch: TASTE:NACH RECHTS:NACH UNTEN");
                printwriter.println("");
                printwriter.println("W:18:0");
                printwriter.println("A:0:18");
                printwriter.println("S:18:18");
                printwriter.println("D:36:18");
                printwriter.println("LMB:0:38");
                printwriter.println("RMB:28:38");
                printwriter.flush();
                printwriter.close();
            }

            BufferedReader bufferedreader = new BufferedReader(new FileReader(file1));
            i = 0;
            String s;

            while ((s = bufferedreader.readLine()) != null)
            {
                ++i;

                if (!s.isEmpty() && !s.replace(" ", "").isEmpty() && !s.startsWith("#"))
                {
                    String[] astring = s.split(":");
                    String s1 = astring[0];
                    int j = Integer.valueOf(astring[1]).intValue();
                    int k = Integer.valueOf(astring[2]).intValue();
                    keys.add(new Key(s1, j, k));
                }
            }

            i = -1;
            bufferedreader.close();

            for (Key key : keys)
            {
                width = Math.max(width, key.getX() + key.getWidth() + 10);
                height = Math.max(height, key.getY() + key.getHeight() + 10);
            }

            if (mc.thePlayer != null)
            {
                mc.thePlayer.addChatMessage(new ChatComponentText("Key configuration has been reloaded."));
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            keys.clear();

            if (mc.thePlayer != null)
            {
                mc.thePlayer.addChatMessage((new ChatComponentText("An error occured while loading the Key configuration file:")).setChatStyle((new ChatStyle()).setColor(EnumChatFormatting.RED)));
                mc.thePlayer.addChatMessage((new ChatComponentText(" - " + exception.toString())).setChatStyle((new ChatStyle()).setColor(EnumChatFormatting.RED)));

                if (i != -1)
                {
                    mc.thePlayer.addChatMessage((new ChatComponentText(" - At line #" + i)).setChatStyle((new ChatStyle()).setColor(EnumChatFormatting.RED)));
                }
            }
        }
    }

    public static void reloadLocation()
    {
        locationFile = new File(mc.mcDataDir + File.separator + "KeystrokesMod", "location.txt");

        try
        {
            if (!locationFile.exists())
            {
                locationFile.createNewFile();
                FileWriter filewriter = new FileWriter(locationFile);
                filewriter.append(location + "\n");
                filewriter.flush();
                filewriter.close();
            }

            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(new FileInputStream(locationFile)));
            String s = null;

            if ((s = bufferedreader.readLine()) != null)
            {
                try
                {
                    location = Integer.parseInt(s);
                }
                catch (NumberFormatException var3)
                {
                    if (mc.thePlayer != null)
                    {
                        mc.thePlayer.addChatMessage(new ChatComponentText("[KeystrokesMod] The location in the location.txt is not an integer!"));
                    }
                }

                bufferedreader.close();
                return;
            }

            if (mc.thePlayer != null)
            {
                mc.thePlayer.addChatMessage((new ChatComponentText("[KeystrokesMod] Couldn\'t load location from location.txt!")).setChatStyle((new ChatStyle()).setColor(EnumChatFormatting.RED)));
            }

            bufferedreader.close();
        }
        catch (IOException ioexception)
        {
            ioexception.printStackTrace();
        }
    }

    public void onEnable()
    {
        reloadKeys();
        reloadLocation();
        ModAPI.registerListener(new KeystrokesRenderer());
        ModAPI.registerListener(this);
    }

    @EventHandler
    public void onTick(GameTickEvent event)
    {
        for (Key key : keys)
        {
            if (!key.isMouseKey())
            {
                key.setPressed(Keyboard.isKeyDown(key.getKey()));
            }
        }

        for (Key key1 : keys)
        {
            if (key1.isMouseKey())
            {
                key1.setPressed(Mouse.isButtonDown(key1.getKey()));
            }
        }
    }

    @EventHandler
    public void onCommand(SendChatMessageEvent event)
    {
        String s = event.getMessage();

        if (s.toLowerCase().startsWith("/keystrokes"))
        {
            event.setCancelled(true);
            String[] astring = s.split(" ");

            if (astring.length == 1)
            {
                Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText("/keystrokes <setlocation|reload>"));
            }
            else
            {
                String[] astring1 = new String[astring.length - 1];
                System.arraycopy(astring, 1, astring1, 0, astring.length - 1);

                if (astring1[0].equalsIgnoreCase("setlocation"))
                {
                    String s1 = "";

                    for (int i = 0; i < Location.values().length; ++i)
                    {
                        s1 = s1 + (!s1.equals("") ? ", " : "") + Location.values()[i].name();
                    }

                    if (astring1.length < 2)
                    {
                        Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText("Invalid location. Possible locations: " + s1.toString()));
                    }
                    else
                    {
                        for (int j = 0; j < Location.values().length; ++j)
                        {
                            Location location = Location.values()[j];

                            if (location.name().equalsIgnoreCase(astring1[1]))
                            {
                                this.location = j;

                                try
                                {
                                    PrintWriter printwriter = new PrintWriter(new FileWriter(locationFile));
                                    printwriter.println(j);
                                    printwriter.flush();
                                    printwriter.close();
                                }
                                catch (IOException ioexception)
                                {
                                    ioexception.printStackTrace();
                                }

                                Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText("Location set to " + location + "."));
                                return;
                            }
                        }

                        Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText("Invalid location. Possible locations: " + s1.toString()));
                    }
                }
                else if (astring1[0].equalsIgnoreCase("reload"))
                {
                    reloadKeys();
                    reloadLocation();
                }
                else
                {
                    Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText("/keystrokes <setlocation|reload>"));
                }
            }
        }
    }
}
