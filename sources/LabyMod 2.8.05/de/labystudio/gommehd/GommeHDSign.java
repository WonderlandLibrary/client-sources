package de.labystudio.gommehd;

import de.labystudio.listener.GommeHD;
import de.labystudio.utils.Color;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiUtilRenderComponents;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ResourceLocation;

public class GommeHDSign
{
    public static String search = "";
    public static boolean allowed = false;
    public static boolean sound = false;
    public static int partySize = 0;
    public static String blacklist = "";
    public static boolean autoJoin = false;
    public static boolean nightMode = false;
    public static long noSpamClick = 0L;
    public static long noSpamLook = 0L;
    public static TileEntitySign found = null;
    private static float focusYaw = 0.0F;
    private static float focusPitch = 0.0F;
    private static ResourceLocation soundLocation = new ResourceLocation("mob.creeper.death");

    public static boolean isGommeSign(ArrayList<String> text)
    {
        return !GommeHD.isGommeHD() ? false : (text.size() == 0 ? false : (((String)text.get(0)).contains("- ") && ((String)text.get(0)).contains(" -") ? !((String)text.get(0)).contains("---") : false));
    }

    public static ArrayList<String> getText(TileEntitySign sign)
    {
        ArrayList<String> arraylist = new ArrayList();
        FontRenderer fontrenderer = Minecraft.getMinecraft().fontRendererObj;
        byte b0 = 0;

        for (int i = 0; i < sign.signText.length; ++i)
        {
            if (sign.signText[i] != null)
            {
                IChatComponent ichatcomponent = sign.signText[i];
                List list = GuiUtilRenderComponents.func_178908_a(ichatcomponent, 90, fontrenderer, false, true);
                String s = list != null && list.size() > 0 ? ((IChatComponent)list.get(0)).getFormattedText() : "";
                arraylist.add(s);
            }
        }

        if (!isGommeSign(arraylist))
        {
            return new ArrayList();
        }
        else
        {
            return arraylist;
        }
    }

    public static boolean isAvailable(ArrayList<String> text)
    {
        ArrayList<String> arraylist = new ArrayList();
        arraylist.addAll(text);

        if (arraylist.size() > 2 && !arraylist.isEmpty())
        {
            try
            {
                String s = (String)arraylist.get(1);

                if (s != null && s.contains("aLobby"))
                {
                    return true;
                }
            }
            catch (Exception var3)
            {
                ;
            }
        }

        return false;
    }

    public static boolean isFull(ArrayList<String> text)
    {
        ArrayList<String> arraylist = new ArrayList();
        arraylist.addAll(text);
        return !arraylist.isEmpty() && arraylist.size() > 2 && arraylist.get(1) != null && ((String)arraylist.get(1)).contains("6Lobby");
    }

    public static boolean isEmpty(ArrayList<String> text)
    {
        ArrayList<String> arraylist = new ArrayList();
        arraylist.addAll(text);
        return !arraylist.isEmpty() && arraylist.size() > 2 && arraylist.get(3) != null && Color.removeColor((String)arraylist.get(3)).startsWith("0");
    }

    public static String getMap(ArrayList<String> text)
    {
        ArrayList<String> arraylist = new ArrayList();
        arraylist.addAll(text);
        return arraylist.size() > 3 && !arraylist.isEmpty() && arraylist.get(2) != null ? (String)arraylist.get(2) : "";
    }

    public static boolean size(ArrayList<String> text)
    {
        if (partySize == 0)
        {
            return true;
        }
        else
        {
            ArrayList<String> arraylist = new ArrayList();
            arraylist.addAll(text);

            try
            {
                if (arraylist.size() > 3 && !arraylist.isEmpty() && arraylist.get(3) != null)
                {
                    String s = Color.removeColor((String)arraylist.get(3));

                    if (s.contains("/"))
                    {
                        String[] astring = s.split("/");

                        if (Integer.parseInt(astring[1]) - Integer.parseInt(astring[0]) >= partySize && arraylist.get(2) != null)
                        {
                            String s1 = Color.removeColor((String)arraylist.get(2));

                            if (!s1.contains(" ") || !s1.contains("x"))
                            {
                                return true;
                            }

                            String[] astring1 = s1.split(" ");
                            String[] astring2 = astring1[1].split("x");

                            if (Integer.parseInt(astring2[1]) >= partySize)
                            {
                                return true;
                            }
                        }
                    }
                }
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
            }

            return false;
        }
    }

    public static boolean search(ArrayList<String> text)
    {
        try
        {
            String s = getMap(text).toLowerCase();

            if (!blacklist.isEmpty() && search.isEmpty())
            {
                for (String s3 : blacklist.toLowerCase().split(","))
                {
                    if (s.contains(s3))
                    {
                        return false;
                    }
                }

                return true;
            }

            if (search.isEmpty())
            {
                return true;
            }

            if (!blacklist.isEmpty())
            {
                boolean flag = false;

                for (String s2 : search.toLowerCase().split(","))
                {
                    if (s.contains(s2))
                    {
                        flag = true;
                    }
                }

                for (String s4 : blacklist.toLowerCase().split(","))
                {
                    if (s.contains(s4))
                    {
                        flag = false;
                    }
                }

                return flag;
            }

            for (String s1 : search.toLowerCase().split(","))
            {
                if (s.contains(s1))
                {
                    return true;
                }
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }

        return false;
    }

    public static void green()
    {
        GlStateManager.color(0.6F, 23.6F, 0.6F, 0.6F);
    }

    public static void red()
    {
        GlStateManager.color(10.0F, 0.6F, 0.6F, 0.6F);
    }

    public static void orange()
    {
        GlStateManager.color(10.0F, 1.6F, 0.6F, 0.6F);
    }

    public static void blue()
    {
        GlStateManager.color(0.6F, 0.6F, 0.6F, 0.7F);
    }

    public static void sendJoinPacket(BlockPos b)
    {
        if (noSpamClick <= System.currentTimeMillis())
        {
            noSpamClick = System.currentTimeMillis() + 100L;
            EnumFacing enumfacing = EnumFacing.UP;
            C07PacketPlayerDigging c07packetplayerdigging = new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, b, enumfacing);
            Minecraft.getMinecraft().getNetHandler().addToSendQueue(c07packetplayerdigging);
        }
    }

    public static void render(TileEntitySign sign)
    {
        if (sign.updateSign > 50)
        {
            sign.updateSign = 0;
        }

        if (sign.updateSign == 0 && allowed && GommeHD.isGommeHD())
        {
            sign.setText(new ArrayList());
            sign.setAvailable(sign.getText());
            sign.setFull(sign.getText());
            sign.setSearch(sign.getText());
            sign.setSize(sign.getText());
            sign.setEmpty(sign.getText());
        }

        ++sign.updateSign;

        if (allowed && GommeHD.isGommeHD() && !sign.getText().isEmpty())
        {
            ArrayList<String> arraylist = sign.getText();

            if (!arraylist.isEmpty())
            {
                if (sign.getAvailable())
                {
                    if (sign.getSearch())
                    {
                        if (sign.getSize())
                        {
                            if (sign.isEmpty() && nightMode)
                            {
                                blue();
                            }
                            else
                            {
                                green();

                                if (autoJoin)
                                {
                                    sendJoinPacket(sign.getPos());
                                }
                            }

                            if (sound && Minecraft.getSystemTime() / 2L % 30L == 0L)
                            {
                                Minecraft.getMinecraft().getSoundHandler().playSound(new PositionedSoundRecord(soundLocation, 12.0F, 2.0F, (float)sign.getPos().getX(), (float)sign.getPos().getY(), (float)sign.getPos().getZ()));
                            }
                        }
                        else
                        {
                            orange();
                        }
                    }
                    else
                    {
                        red();
                    }
                }
                else if (sign.isFull())
                {
                    if (sign.getSearch())
                    {
                        orange();
                    }
                    else
                    {
                        red();
                    }
                }
                else
                {
                    red();
                }
            }
        }
    }
}
