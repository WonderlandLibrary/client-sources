package de.labystudio.gommehd;

import de.labystudio.hologram.Hologram;
import de.labystudio.hologram.Manager;
import de.labystudio.hologram.SetColor;
import de.labystudio.labymod.ConfigManager;
import de.labystudio.listener.GommeHD;
import de.labystudio.utils.Color;
import de.labystudio.utils.Utils;
import java.util.ArrayList;
import java.util.UUID;
import net.minecraft.entity.EntityLivingBase;

public class GommeHDBed
{
    public static ArrayList<UUID> respawn = new ArrayList();
    public static ArrayList<BedLocation> locations = new ArrayList();
    public static ArrayList<EnumBWTeam> noBeds = new ArrayList();
    public static EnumBWMap playingMap = EnumBWMap.NONE;
    public static int mapHeight = 0;

    private static boolean contains(int x, int y, int z)
    {
        for (BedLocation bedlocation : getLocations())
        {
            if (bedlocation.x == x && bedlocation.y == y && bedlocation.z == z)
            {
                return true;
            }
        }

        return false;
    }

    public static SetColor bindColor(EnumBWTeam team)
    {
        return team == EnumBWTeam.BLAU ? new SetColor(0.0F, 0.0F, 1.0F, 0.45F) : (team == EnumBWTeam.GRÜN ? new SetColor(0.0F, 1.0F, 0.0F, 0.55F) : (team == EnumBWTeam.ROT ? new SetColor(1.0F, 0.0F, 0.0F, 0.55F) : (team == EnumBWTeam.GELB ? new SetColor(1.0F, 1.0F, 0.0F, 0.45F) : (team == EnumBWTeam.SCHWARZ ? new SetColor(0.0F, 0.0F, 0.0F, 0.45F) : (team == EnumBWTeam.PINK ? new SetColor(1.5F, 0.5F, 0.6F, 0.45F) : (team == EnumBWTeam.TÜRKIS ? new SetColor(0.2F, 1.6F, 3.8F, 0.45F) : (team == EnumBWTeam.ORANGE ? new SetColor(1.5F, 0.5F, 0.0F, 0.45F) : new SetColor(0.0F, 0.0F, 0.0F, 0.15F))))))));
    }

    public static void setPreset(EnumBWMap map)
    {
        reset();
        playingMap = map;
        setBeds();
    }

    public static void setBeds()
    {
        if (playingMap == EnumBWMap.FARMLAND)
        {
            add(EnumBWTeam.BLAU, 148, 146, -428);
            add(EnumBWTeam.ROT, 104, 146, -464);
            add(EnumBWTeam.GRÜN, 67, 146, -420);
            add(EnumBWTeam.GELB, 112, 146, -385);
            setMapHeight(25);
        }

        if (playingMap == EnumBWMap.EMPIRE)
        {
            add(EnumBWTeam.BLAU, -12, 120, 6);
            add(EnumBWTeam.ROT, 46, 120, -61);
            add(EnumBWTeam.GRÜN, -24, 120, -120);
            add(EnumBWTeam.GELB, -80, 120, -52);
            setMapHeight(30);
        }

        if (playingMap == EnumBWMap.ANTIC)
        {
            add(EnumBWTeam.BLAU, -57, 126, 0);
            add(EnumBWTeam.ROT, 0, 126, 57);
            add(EnumBWTeam.GRÜN, 57, 126, 0);
            add(EnumBWTeam.GELB, 0, 126, -57);
            setMapHeight(30);
        }

        if (playingMap == EnumBWMap.VILLAGE)
        {
            add(EnumBWTeam.BLAU, 27, 121, -14);
            add(EnumBWTeam.ROT, -90, 121, -7);
            add(EnumBWTeam.GRÜN, -29, 121, 45);
            add(EnumBWTeam.GELB, -34, 121, -69);
            setMapHeight(30);
        }

        if (playingMap == EnumBWMap.FACTORY)
        {
            add(EnumBWTeam.BLAU, 69, 143, 0);
            add(EnumBWTeam.ROT, -69, 143, 0);
            add(EnumBWTeam.GRÜN, 0, 143, -69);
            add(EnumBWTeam.GELB, 0, 143, 69);
            setMapHeight(15);
        }

        if (playingMap == EnumBWMap.WILDWEST)
        {
            add(EnumBWTeam.BLAU, -7, 80, -70);
            add(EnumBWTeam.ROT, -7, 80, 123);
            add(EnumBWTeam.GRÜN, -104, 80, 26);
            add(EnumBWTeam.GELB, 89, 80, 26);
            setMapHeight(35);
        }

        if (playingMap == EnumBWMap.TIPPIS)
        {
            add(EnumBWTeam.BLAU, 60, 59, -28);
            add(EnumBWTeam.ROT, -64, 59, 24);
            add(EnumBWTeam.GRÜN, -64, 59, -27);
            add(EnumBWTeam.GELB, 60, 59, 23);
            add(EnumBWTeam.SCHWARZ, -27, 59, 60);
            add(EnumBWTeam.PINK, 33, 59, -64);
            add(EnumBWTeam.TÜRKIS, -28, 59, -64);
            add(EnumBWTeam.ORANGE, 24, 59, 60);
            setMapHeight(35);
        }

        if (playingMap == EnumBWMap.SHROOM)
        {
            add(EnumBWTeam.BLAU, 0, 90, -85);
            add(EnumBWTeam.ROT, 0, 90, 81);
            add(EnumBWTeam.GRÜN, 83, 90, -2);
            add(EnumBWTeam.GELB, -83, 90, -2);
            setMapHeight(35);
        }
    }

    public static int getMapHeight()
    {
        return mapHeight;
    }

    public static void setMapHeight(int i)
    {
        mapHeight = i;
    }

    public static void add(EnumBWTeam team, int x, int y, int z)
    {
        if (!contains(x, y, z) && playingMap != EnumBWMap.NONE)
        {
            locations.add(new BedLocation(team, x, y, z));
        }
    }

    public static void reset()
    {
        locations.clear();
        playingMap = EnumBWMap.NONE;
        mapHeight = 20;
        ArrayList<Hologram> arraylist = new ArrayList();
        ArrayList<Hologram> arraylist1 = new ArrayList();
        arraylist.addAll(Manager.holograms);

        for (Hologram hologram : arraylist)
        {
            if (hologram.memory instanceof EnumBWTeam)
            {
                arraylist1.add(hologram);
            }
        }

        for (Hologram hologram1 : arraylist1)
        {
            Manager.holograms.remove(hologram1);
        }

        respawn.clear();
    }

    public static ArrayList<BedLocation> getLocations()
    {
        ArrayList<BedLocation> arraylist = new ArrayList();
        arraylist.addAll(locations);
        return arraylist;
    }

    public static String getSymbol(boolean b)
    {
        return b ? Color.c + "a\u2714" : Color.c + "4\u2716";
    }

    public static String getBedStatus(EnumBWTeam team)
    {
        return GommeHD.gommeHDServer_BW_Team.isEmpty() ? "" : " " + getSymbol(!noBeds.contains(team));
    }

    public static void renderPlayerTag(EntityLivingBase entity, double x, double y, double z)
    {
        if (GommeHD.gommeHDServer_BW && ConfigManager.settings.showBWTeams.booleanValue() && GommeHD.isGommeHD())
        {
            if (respawn.contains(entity.getUniqueID()) && !entity.isDead)
            {
                try
                {
                    respawn.remove(entity.getUniqueID());
                    String s = entity.getDisplayName().getFormattedText().replace(entity.getName(), "");

                    if (s.contains(" "))
                    {
                        s = s.split(" ")[0];
                    }

                    EnumBWTeam enumbwteam = null;

                    if (s.contains(Color.cl("c")))
                    {
                        enumbwteam = EnumBWTeam.ROT;
                    }

                    if (s.contains(Color.cl("e")))
                    {
                        enumbwteam = EnumBWTeam.GELB;
                    }

                    if (s.contains(Color.cl("9")))
                    {
                        enumbwteam = EnumBWTeam.BLAU;
                    }

                    if (s.contains(Color.cl("2")))
                    {
                        enumbwteam = EnumBWTeam.GRÜN;
                    }

                    if (s.contains(Color.cl("0")))
                    {
                        enumbwteam = EnumBWTeam.SCHWARZ;
                    }

                    if (s.contains(Color.cl("3")))
                    {
                        enumbwteam = EnumBWTeam.TÜRKIS;
                    }

                    if (s.contains(Color.cl("6")))
                    {
                        enumbwteam = EnumBWTeam.ORANGE;
                    }

                    if (s.contains(Color.cl("d")))
                    {
                        enumbwteam = EnumBWTeam.PINK;
                    }

                    if (enumbwteam != null)
                    {
                        boolean flag = false;

                        for (Hologram hologram1 : Manager.getHolograms())
                        {
                            if (hologram1.memory instanceof EnumBWTeam && (EnumBWTeam)hologram1.memory == enumbwteam)
                            {
                                flag = true;
                            }
                        }

                        if (!flag)
                        {
                            x = (double)entity.getPosition().getX();
                            y = (double)entity.getPosition().getY();
                            z = (double)entity.getPosition().getZ();
                            Hologram hologram3 = new Hologram(Utils.normalizeString(enumbwteam.name()) + getBedStatus(enumbwteam), (int)x, (int)y + 20, (int)z, bindColor(enumbwteam));
                            hologram3.memory = enumbwteam;
                            Manager.holograms.add(hologram3);
                        }
                    }
                }
                catch (Exception var12)
                {
                    ;
                }
            }
            else if (entity.isDead)
            {
                respawn.add(entity.getUniqueID());
            }
        }
        else
        {
            if (!Manager.holograms.isEmpty() || !respawn.isEmpty())
            {
                ArrayList<Hologram> arraylist = new ArrayList();
                ArrayList<Hologram> arraylist1 = new ArrayList();
                arraylist.addAll(Manager.holograms);

                for (Hologram hologram : arraylist)
                {
                    if (hologram.memory instanceof EnumBWTeam)
                    {
                        arraylist1.add(hologram);
                    }
                }

                for (Hologram hologram2 : arraylist1)
                {
                    Manager.holograms.remove(hologram2);
                }

                respawn.clear();
            }
        }
    }

    public static void updateHolograms()
    {
        for (Hologram hologram : Manager.holograms)
        {
            if (hologram.memory instanceof EnumBWTeam)
            {
                EnumBWTeam enumbwteam = (EnumBWTeam)hologram.memory;

                if (hologram != null)
                {
                    hologram.text = Utils.normalizeString(enumbwteam.name()) + getBedStatus(enumbwteam);
                }
            }
        }
    }
}
