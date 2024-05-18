package mods.worldeditcui;

import de.labystudio.modapi.EventHandler;
import de.labystudio.modapi.Listener;
import de.labystudio.modapi.events.ChatReceivedEvent;
import de.labystudio.modapi.events.GameTickEvent;
import de.labystudio.modapi.events.JoinedServerEvent;
import de.labystudio.modapi.events.RenderWorldEvent;
import de.labystudio.modapi.events.SendChatMessageEvent;
import java.util.ArrayList;
import java.util.List;
import mods.worldeditcui.render.points.PointCube;
import mods.worldeditcui.render.region.CuboidRegion;
import mods.worldeditcui.util.RegionUtils;
import mods.worldeditcui.util.Vector;
import mods.worldeditcui.util.Vector3;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;

public class LabyModWorldEditCUIEvents implements Listener
{
    private LabyModWorldEditCUI main;
    private World prevWorld;
    private String lastArgs = null;
    private long lastExpandSent = -1L;
    private String lastShiftArgs = null;
    private long lastShiftSent = -1L;
    private String lastContractArgs = null;
    private long lastContractSent = -1L;

    public LabyModWorldEditCUIEvents(LabyModWorldEditCUI main)
    {
        this.main = main;
    }

    @EventHandler
    public void onRender(RenderWorldEvent e)
    {
        this.main.onPostRenderEntities((double)e.getPartialTicks());
    }

    @EventHandler
    public void onJoin(JoinedServerEvent e)
    {
        this.main.visible = true;
    }

    @EventHandler
    public void onTick(GameTickEvent e)
    {
        if (Minecraft.getMinecraft().theWorld != null)
        {
            if (Minecraft.getMinecraft().theWorld != this.prevWorld)
            {
                this.prevWorld = Minecraft.getMinecraft().theWorld;
                ((CuboidRegion)this.main.getController().getSelection()).clear();
            }
        }
    }

    @EventHandler
    public void onChat(ChatReceivedEvent e)
    {
        String s = e.getCleanMsg();

        if (s.equals("Selection cleared."))
        {
            ((CuboidRegion)this.main.getController().getSelection()).clear();
        }
        else if (e.getMsg().startsWith("\u00a7r\u00a7d") && s.startsWith("Region expanded") && this.lastExpandSent != -1L)
        {
            CuboidRegion cuboidregion = (CuboidRegion)this.main.getController().getSelection();

            if (cuboidregion.getFirstPoint() != null && cuboidregion.getSecondPoint() != null)
            {
                if (this.lastArgs.toLowerCase().equals("vert"))
                {
                    cuboidregion.expandVert();
                }
                else
                {
                    List<Vector> list2;
                    int l;
                    int j1;
                    String[] astring3 = this.lastArgs.split(" ");
                    list2 = new ArrayList();
                    l = 0;
                    j1 = Integer.parseInt(astring3[0]);
                    label124:

                    switch (astring3.length)
                    {
                        case 2:
                            try
                            {
                                l = Integer.parseInt(astring3[1]);
                                list2.add(RegionUtils.getDirection("me"));
                            }
                            catch (NumberFormatException var15)
                            {
                                if (astring3[1].contains(","))
                                {
                                    String[] astring9 = astring3[1].split(",");
                                    String[] astring11 = astring9;
                                    int l2 = astring9.length;
                                    int j3 = 0;

                                    while (true)
                                    {
                                        if (j3 >= l2)
                                        {
                                            break label124;
                                        }

                                        String s5 = astring11[j3];
                                        list2.add(RegionUtils.getDirection(s5.toLowerCase()));
                                        ++j3;
                                    }
                                }
                                else
                                {
                                    list2.add(RegionUtils.getDirection(astring3[1].toLowerCase()));
                                }
                            }

                            break;

                        case 3:
                            l = Integer.parseInt(astring3[1]);

                            if (astring3[2].contains(","))
                            {
                                String[] astring5 = astring3[2].split(",");
                                String[] astring8 = astring5;
                                int l1 = astring5.length;
                                int k2 = 0;

                                while (true)
                                {
                                    if (k2 >= l1)
                                    {
                                        break label124;
                                    }

                                    String s3 = astring8[k2];
                                    list2.add(RegionUtils.getDirection(s3.toLowerCase()));
                                    ++k2;
                                }
                            }
                            else
                            {
                                list2.add(RegionUtils.getDirection(astring3[2].toLowerCase()));
                                break;
                            }

                        default:
                            list2.add(RegionUtils.getDirection("me"));
                    }

                    Vector3 vector33 = cuboidregion.getFirstPoint().getPoint();
                    Vector3 vector35 = cuboidregion.getSecondPoint().getPoint();
                    Vector vector4 = new Vector(vector33.getX(), vector33.getY(), vector33.getZ());
                    Vector vector6 = new Vector(vector35.getX(), vector35.getY(), vector35.getZ());

                    if (l == 0)
                    {
                        for (Vector vector9 : list2)
                        {
                            RegionUtils.VectorSet regionutils$vectorset3 = RegionUtils.expand(vector4, vector6, new Vector[] {vector9.multiply(j1)});
                            vector4 = regionutils$vectorset3.getVector1();
                            vector6 = regionutils$vectorset3.getVector2();
                        }
                    }
                    else
                    {
                        for (Vector vector10 : list2)
                        {
                            RegionUtils.VectorSet regionutils$vectorset4 = RegionUtils.expand(vector4, vector6, new Vector[] {vector10.multiply(j1), vector10.multiply(-l)});
                            vector4 = regionutils$vectorset4.getVector1();
                            vector6 = regionutils$vectorset4.getVector2();
                        }
                    }

                    cuboidregion.setFirstSecond(new PointCube(vector4.getX(), vector4.getY(), vector4.getZ()), new PointCube(vector6.getX(), vector6.getY(), vector6.getZ()));
                    this.lastExpandSent = -1L;
                    this.lastArgs = null;
                }
            }
            else
            {
                this.lastExpandSent = -1L;
                this.lastArgs = null;
            }
        }
        else if (e.getMsg().startsWith("\u00a7r\u00a7d") && s.startsWith("Region contracted") && this.lastContractSent != -1L)
        {
            List<Vector> list1;
            CuboidRegion cuboidregion2;
            int k;
            int i1;
            String[] astring2 = this.lastContractArgs.split(" ");
            list1 = new ArrayList();
            cuboidregion2 = (CuboidRegion)this.main.getController().getSelection();
            k = Integer.parseInt(astring2[0]);
            i1 = 0;
            label743:

            switch (astring2.length)
            {
                case 2:
                    try
                    {
                        i1 = Integer.parseInt(astring2[1]);
                        list1.add(RegionUtils.getDirection("me"));
                    }
                    catch (NumberFormatException var16)
                    {
                        if (astring2[1].contains(","))
                        {
                            String[] astring7 = astring2[1].split(",");
                            String[] pos2 = astring7;
                            int j2 = astring7.length;
                            int dir = 0;

                            while (true)
                            {
                                if (dir >= j2)
                                {
                                    break label743;
                                }

                                String s4 = pos2[dir];
                                list1.add(RegionUtils.getDirection(s4.toLowerCase()));
                                ++dir;
                            }
                        }
                        else
                        {
                            list1.add(RegionUtils.getDirection(astring2[1].toLowerCase()));
                        }
                    }

                    break;

                case 3:
                    i1 = Integer.parseInt(astring2[1]);

                    if (astring2[2].contains(","))
                    {
                        String[] astring4 = astring2[2].split(",");
                        String[] split = astring4;
                        int s1 = astring4.length;
                        int pos2 = 0;

                        while (true)
                        {
                            if (pos2 >= s1)
                            {
                                break label743;
                            }

                            String s2 = split[pos2];
                            list1.add(RegionUtils.getDirection(s2.toLowerCase()));
                            ++pos2;
                        }
                    }
                    else
                    {
                        list1.add(RegionUtils.getDirection(astring2[2].toLowerCase()));
                        break;
                    }

                default:
                    list1.add(RegionUtils.getDirection("me"));
            }

            Vector3 vector32 = cuboidregion2.getFirstPoint().getPoint();
            Vector3 vector34 = cuboidregion2.getSecondPoint().getPoint();
            Vector vector3 = new Vector(vector32.getX(), vector32.getY(), vector32.getZ());
            Vector vector5 = new Vector(vector34.getX(), vector34.getY(), vector34.getZ());

            if (i1 == 0)
            {
                for (Vector vector7 : list1)
                {
                    RegionUtils.VectorSet regionutils$vectorset1 = RegionUtils.contract(vector3, vector5, new Vector[] {vector7.multiply(k)});
                    vector3 = regionutils$vectorset1.getVector1();
                    vector5 = regionutils$vectorset1.getVector2();
                }
            }
            else
            {
                for (Vector vector8 : list1)
                {
                    RegionUtils.VectorSet regionutils$vectorset2 = RegionUtils.contract(vector8.multiply(k), vector8.multiply(-i1), new Vector[0]);
                    vector3 = regionutils$vectorset2.getVector1();
                    vector5 = regionutils$vectorset2.getVector2();
                }
            }

            cuboidregion2.setFirstSecond(new PointCube(vector3.getX(), vector3.getY(), vector3.getZ()), new PointCube(vector5.getX(), vector5.getY(), vector5.getZ()));
            this.lastContractArgs = null;
            this.lastContractSent = -1L;
        }
        else if (e.getMsg().startsWith("\u00a7r\u00a7d") && s.equals("Region shifted.") && this.lastShiftSent != -1L)
        {
            String[] astring1 = this.lastShiftArgs.split(" ");
            CuboidRegion cuboidregion1 = (CuboidRegion)this.main.getController().getSelection();
            List<Vector> list = new ArrayList();
            int j = Integer.parseInt(astring1[0]);

            if (astring1.length == 2)
            {
                if (astring1[1].contains(","))
                {
                    for (String s1 : astring1[1].split(","))
                    {
                        list.add(RegionUtils.getDirection(s1.toLowerCase()));
                    }
                }
                else
                {
                    list.add(RegionUtils.getDirection(astring1[1].toLowerCase()));
                }
            }
            else
            {
                list.add(RegionUtils.getDirection("me"));
            }

            Vector3 vector3 = cuboidregion1.getFirstPoint().getPoint();
            Vector3 vector31 = cuboidregion1.getSecondPoint().getPoint();
            Vector vector1 = new Vector(vector3.getX(), vector3.getY(), vector3.getZ());
            Vector vector2 = new Vector(vector31.getX(), vector31.getY(), vector31.getZ());

            for (Vector vector : list)
            {
                RegionUtils.VectorSet regionutils$vectorset = RegionUtils.shift(vector1, vector2, new Vector[] {vector.multiply(j)});
                vector1 = regionutils$vectorset.getVector1();
                vector2 = regionutils$vectorset.getVector2();
            }

            cuboidregion1.setFirstSecond(new PointCube(vector1.getX(), vector1.getY(), vector1.getZ()), new PointCube(vector2.getX(), vector2.getY(), vector2.getZ()));
            this.lastShiftArgs = null;
            this.lastShiftSent = -1L;
        }
        else if (e.getMsg().startsWith("\u00a7r\u00a7d") && (s.startsWith("First") || s.startsWith("Second")))
        {
            int i = s.startsWith("First") ? 0 : 1;
            String[] astring = s.split("\\)")[0].split("\\(")[1].split(", ");
            ((CuboidRegion)this.main.getController().getSelection()).setCuboidPoint(i, Double.parseDouble(astring[0]), Double.parseDouble(astring[1]), Double.parseDouble(astring[2]));
        }
    }

    @EventHandler
    public void onCommandSent(SendChatMessageEvent e)
    {
        if (e.getMessage().toLowerCase().startsWith("//shift "))
        {
            RegionUtils.setLastYawAndPitch();
            this.lastShiftSent = System.currentTimeMillis();
            this.lastShiftArgs = e.getMessage().toLowerCase().replace("//shift ", "");
        }
        else if (e.getMessage().toLowerCase().startsWith("//contract "))
        {
            RegionUtils.setLastYawAndPitch();
            this.lastContractSent = System.currentTimeMillis();
            this.lastContractArgs = e.getMessage().toLowerCase().replace("//contract ", "");
        }
        else if (e.getMessage().toLowerCase().startsWith("//expand "))
        {
            if (!e.getMessage().toLowerCase().endsWith(" vert") && !e.getMessage().toLowerCase().endsWith(" vertical"))
            {
                RegionUtils.setLastYawAndPitch();
                this.lastArgs = e.getMessage().toLowerCase().replace("//expand ", "");
                this.lastExpandSent = System.currentTimeMillis();
            }
            else
            {
                this.lastExpandSent = System.currentTimeMillis();
                this.lastArgs = "vert";
            }
        }
    }
}
