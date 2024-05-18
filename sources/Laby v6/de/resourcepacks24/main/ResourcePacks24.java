package de.resourcepacks24.main;

import com.google.gson.Gson;
import de.resourcepacks24.utils.DrawUtils;
import de.resourcepacks24.utils.EnumPackSorting;
import de.resourcepacks24.utils.IconDownloader;
import de.resourcepacks24.utils.PackInfoCallback;
import de.resourcepacks24.utils.PackInfoDownloader;
import de.resourcepacks24.utils.RPTag;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class ResourcePacks24
{
    public static String api_packs = "https://resourcepacks24.de/api/api?public_key=4cb863fb3cf988de9d2bcec48b9c22f3669fc7bf&username=" + Minecraft.getMinecraft().getSession().getUsername() + "&uuid=" + Minecraft.getMinecraft().getSession().getPlayerID();
    public static String pack_page = "https://resourcepacks24.de/resourcepack/";
    public static String creator_home = "https://resourcepacks24.de/c/";
    public static String download_count = "https://resourcepacks24.de/api/count_download?rp_id=";
    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
    public static final ResourceLocation RESOURCE_FOLDER = new ResourceLocation("folder.png");
    private DrawUtils draw;
    public static Random random = new Random();
    private int progress = 0;
    private int animatedProgress = 0;
    private EnumPackSorting sorting = EnumPackSorting.NONE;
    private String searchText = "";
    private boolean sortModeVotes = false;
    private Pack selectedPack;
    private String path = "";
    private ArrayList<String> deletedPacks = new ArrayList();
    private IconDownloader iconDownloader;
    private ArrayList<Pack> packs = new ArrayList();
    ArrayList<RPTag> rpTags = new ArrayList();
    private static ResourcePacks24 instance;

    public ArrayList<Pack> getPacks()
    {
        if (this.sorting != EnumPackSorting.TOP && this.sorting != EnumPackSorting.LATEST)
        {
            if (this.sorting == EnumPackSorting.RANDOM)
            {
                ArrayList<Pack> arraylist2 = new ArrayList();

                for (Pack pack2 : this.packs)
                {
                    if (!this.isHiddenPack(pack2))
                    {
                        arraylist2.add(pack2);
                    }
                }

                return arraylist2;
            }
            else if (this.sorting == EnumPackSorting.NONE)
            {
                if (this.searchText.replace(" ", "").isEmpty())
                {
                    return new ArrayList();
                }
                else
                {
                    ArrayList<Pack> arraylist1 = new ArrayList();

                    for (Pack pack1 : this.packs)
                    {
                        if (pack1.getCreator().toLowerCase().contains(this.searchText.toLowerCase()) && !arraylist1.contains(pack1) && !this.isHiddenPack(pack1))
                        {
                            arraylist1.add(pack1);
                        }

                        if (pack1.getName().toLowerCase().contains(this.searchText.toLowerCase()) && !arraylist1.contains(pack1) && !this.isHiddenPack(pack1))
                        {
                            arraylist1.add(pack1);
                        }

                        if (pack1.getDesc().toLowerCase().contains(this.searchText.toLowerCase()) && !arraylist1.contains(pack1) && !this.isHiddenPack(pack1))
                        {
                            arraylist1.add(pack1);
                        }
                    }

                    return arraylist1;
                }
            }
            else
            {
                return this.packs;
            }
        }
        else
        {
            ArrayList<Pack> arraylist = new ArrayList();
            int i = 0;

            for (Pack pack : this.packs)
            {
                if (i >= 20)
                {
                    break;
                }

                if (!this.isHiddenPack(pack))
                {
                    arraylist.add(pack);
                }

                ++i;
            }

            return arraylist;
        }
    }

    public boolean isHiddenPack(Pack pack)
    {
        boolean flag = true;

        for (RPTag rptag : this.getRpTags())
        {
            if (rptag.isEnabled())
            {
                if (rptag.hasTag("PvP", pack.getTag_pvp()))
                {
                    flag = false;
                }

                if (rptag.hasTag("Hardcore Games", pack.getTag_hg()))
                {
                    flag = false;
                }

                if (rptag.hasTag("Survival Games", pack.getTag_sg()))
                {
                    flag = false;
                }

                if (rptag.hasTag("UHC", pack.getTag_uhc()))
                {
                    flag = false;
                }

                if (rptag.hasTag("Potion PvP", pack.getTag_pot()))
                {
                    flag = false;
                }

                if (rptag.hasTag("Bedwars", pack.getTag_bedwars()))
                {
                    flag = false;
                }

                if (rptag.hasTag("SkyWars", pack.getTag_skywars()))
                {
                    flag = false;
                }

                if (rptag.hasTag("SMYP", pack.getTag_smyp()))
                {
                    flag = false;
                }

                if (rptag.hasTag("YouTube", pack.getTag_yt()))
                {
                    flag = false;
                }

                if (rptag.getTagName().equals("Premium") && pack.getPremiumId() != 0)
                {
                    flag = false;
                }
            }
        }

        return flag;
    }

    public String getPath()
    {
        return this.path;
    }

    public ArrayList<String> getDeletedPacks()
    {
        return this.deletedPacks;
    }

    public int getAnimatedProgress()
    {
        return this.animatedProgress;
    }

    public IconDownloader getIconDownloader()
    {
        return this.iconDownloader;
    }

    public void resetProgress()
    {
        this.progress = 0;
        this.animatedProgress = 0;
    }

    public int getProgress()
    {
        return this.progress;
    }

    public DrawUtils getDraw()
    {
        return this.draw;
    }

    public boolean isSortModeVotes()
    {
        return this.sortModeVotes;
    }

    public void setSortModeVotes(boolean sortModeVotes)
    {
        this.sortModeVotes = sortModeVotes;
    }

    public void setSelectedPack(Pack pack)
    {
        this.selectedPack = pack;
    }

    public Pack getSelectedPack()
    {
        return this.selectedPack;
    }

    public void setSearchText(String searchText)
    {
        this.searchText = searchText;
    }

    public String getSearchText()
    {
        return this.searchText;
    }

    public ArrayList<RPTag> getRpTags()
    {
        return this.rpTags;
    }

    public EnumPackSorting getSorting()
    {
        return this.sorting;
    }

    public void setPath(String path)
    {
        this.path = path;
    }

    public void updateAnimatedProgress()
    {
        if (this.progress > this.animatedProgress)
        {
            if (this.progress - 6 > this.animatedProgress)
            {
                this.animatedProgress += 5;
            }
            else
            {
                ++this.animatedProgress;
            }
        }

        if (this.progress < this.animatedProgress)
        {
            if (this.progress + 6 < this.animatedProgress)
            {
                this.animatedProgress -= 5;
            }
            else
            {
                --this.animatedProgress;
            }
        }
    }

    public void setProgress(int progress)
    {
        if (progress == 0)
        {
            this.animatedProgress = progress;
        }

        this.progress = progress;
    }

    public static ResourcePacks24 getInstance()
    {
        if (instance == null)
        {
            instance = new ResourcePacks24();
            instance.onLoad();
            return instance;
        }
        else
        {
            return instance;
        }
    }

    public void onLoad()
    {
        this.draw = new DrawUtils();
        this.iconDownloader = new IconDownloader();
        new PackInfoDownloader(new PackInfoCallback()
        {
            public void result(ArrayList<Pack> list)
            {
                ResourcePacks24.this.packs = list;
            }
            public void progress(int i)
            {
                ResourcePacks24.this.progress = i;
            }
        });
        this.rpTags.clear();
        this.rpTags.add(new RPTag("PvP", true));
        this.rpTags.add(new RPTag("Hardcore Games", true));
        this.rpTags.add(new RPTag("Survival Games", true));
        this.rpTags.add(new RPTag("UHC", true));
        this.rpTags.add(new RPTag("Potion PvP", true));
        this.rpTags.add(new RPTag("Bedwars", true));
        this.rpTags.add(new RPTag("Skywars", true));
        this.rpTags.add(new RPTag("SMYP", true));
        this.rpTags.add(new RPTag("YouTube", true));
        this.rpTags.add(new RPTag("Premium", true));
        this.rpConfig();
        System.out.println("[Resourcepack24] Loaded modification!");
    }

    public void sort(EnumPackSorting sorting)
    {
        this.sorting = sorting;

        if (sorting == EnumPackSorting.LATEST)
        {
            Collections.sort(this.packs, new Comparator<Pack>()
            {
                public int compare(Pack pack1, Pack pack2)
                {
                    return (int)(pack2.getUpTime() - pack1.getUpTime());
                }
            });
        }
        else if (sorting == EnumPackSorting.TOP)
        {
            Collections.sort(this.packs, new Comparator<Pack>()
            {
                public int compare(Pack pack1, Pack pack2)
                {
                    return ResourcePacks24.this.sortModeVotes ? pack2.getVotes() - pack1.getVotes() : pack2.getDownloads() - pack1.getDownloads();
                }
            });
        }
        else
        {
            Collections.sort(this.packs, new Comparator<Pack>()
            {
                public int compare(Pack pack1, Pack pack2)
                {
                    return pack1.getId() - pack2.getId();
                }
            });
        }
    }

    private void rpConfig()
    {
        final File file1 = new File(Minecraft.getMinecraft().mcDataDir, "rp24.txt");
        final Gson gson = new Gson();

        if (file1.exists())
        {
            try
            {
                BufferedReader bufferedreader = new BufferedReader(new FileReader(file1));
                String s;
                String s1;

                for (s = ""; (s1 = bufferedreader.readLine()) != null; s = s + s1)
                {
                    ;
                }

                bufferedreader.close();
                ArrayList<String> arraylist = (ArrayList)gson.fromJson(s, ArrayList.class);
                this.deletedPacks.addAll(arraylist);
                System.out.println("[Resourcepack24] Loaded config file!");

                for (String s2 : arraylist)
                {
                    File file2 = Minecraft.getMinecraft().getResourcePackRepository().getDirResourcepacks();
                    File file3 = new File(file2.getAbsolutePath(), s2);

                    if (file3.delete())
                    {
                        getInstance().getDeletedPacks().remove(s2);
                        System.out.println("[Resourcepack24] Deleted " + s2 + " successfully!");
                    }
                    else
                    {
                        System.out.println("[Resourcepack24] Can\'t delete " + s2 + ".");
                    }
                }
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
            }

            file1.delete();
        }

        Runtime.getRuntime().addShutdownHook(new Thread("Resourcepack24Mod shutdown hook")
        {
            public void run()
            {
                if (!ResourcePacks24.this.getDeletedPacks().isEmpty())
                {
                    try
                    {
                        if (!file1.exists())
                        {
                            file1.createNewFile();
                        }

                        String s3 = gson.toJson((Object)ResourcePacks24.this.getDeletedPacks());
                        FileWriter filewriter = new FileWriter(file1);
                        filewriter.write(s3);
                        filewriter.flush();
                        filewriter.close();
                        System.out.println("[Resourcepack24] Saved config file!");
                    }
                    catch (Exception exception1)
                    {
                        exception1.printStackTrace();
                    }
                }
            }
        });
    }
}
