package de.resourcepacks24.main;

public class Pack
{
    private int id;
    private String name = "?";
    private String ingameName;
    private String newName;
    private long upTime;
    private String creator = "?";
    private String size = "?";
    private int status;
    private int downloads;
    private String hashName = "?";
    private int premiumId;
    private int votes;
    private int tag_hg;
    private int tag_sg;
    private int tag_uhc;
    private int tag_pot;
    private int tag_pvp;
    private int tag_bedwars;
    private int tag_skywars;
    private int tag_smyp;
    private int tag_yt;
    private String desc = "?";

    public Pack(int id, String name, String ingameName, String newName, long upTime, String creator, String size, int status, int downloads, String hashName, int premiumId, int votes, int tag_hg, int tag_sg, int tag_uhc, int tag_pot, int tag_pvp, int tag_bedwars, int tag_skywars, int tag_smyp, int tag_yt)
    {
        this.id = id;
        this.name = name;
        this.ingameName = ingameName;
        this.newName = newName;
        this.upTime = upTime;
        this.creator = creator;
        this.size = size;
        this.status = status;
        this.downloads = downloads;
        this.hashName = hashName;
        this.premiumId = premiumId;
        this.votes = votes;
        this.tag_hg = tag_hg;
        this.tag_sg = tag_sg;
        this.tag_uhc = tag_uhc;
        this.tag_pot = tag_pot;
        this.tag_pvp = tag_pvp;
        this.tag_bedwars = tag_bedwars;
        this.tag_skywars = tag_skywars;
        this.tag_smyp = tag_sg;
        this.tag_yt = tag_yt;
    }

    public int getId()
    {
        return this.id;
    }

    public String getName()
    {
        return this.name;
    }

    public String getIngameName()
    {
        return this.ingameName;
    }

    public String getNewName()
    {
        return this.newName;
    }

    public long getUpTime()
    {
        return this.upTime;
    }

    public String getCreator()
    {
        return this.creator;
    }

    public String getSize()
    {
        return this.size;
    }

    public int getStatus()
    {
        return this.status;
    }

    public int getDownloads()
    {
        return this.downloads;
    }

    public String getHashName()
    {
        return this.hashName;
    }

    public int getPremiumId()
    {
        return this.premiumId;
    }

    public int getVotes()
    {
        return this.votes;
    }

    public int getTag_bedwars()
    {
        return this.tag_bedwars;
    }

    public int getTag_hg()
    {
        return this.tag_hg;
    }

    public int getTag_pot()
    {
        return this.tag_pot;
    }

    public int getTag_pvp()
    {
        return this.tag_pvp;
    }

    public int getTag_sg()
    {
        return this.tag_sg;
    }

    public int getTag_skywars()
    {
        return this.tag_skywars;
    }

    public int getTag_smyp()
    {
        return this.tag_smyp;
    }

    public int getTag_uhc()
    {
        return this.tag_uhc;
    }

    public int getTag_yt()
    {
        return this.tag_yt;
    }

    public String getDesc()
    {
        return this.desc;
    }

    public void setDesc(String desc)
    {
        this.desc = desc;
    }

    public String getBestPossibleName()
    {
        return this.ingameName != null ? this.ingameName : (this.newName != null ? this.newName : (this.name != null ? this.name : "?"));
    }

    public void drawImage(int x, int y, int width, int height, double scale)
    {
        ResourcePacks24.getInstance().getIconDownloader().drawUrlImage(this.getHashName(), ResourcePacks24.creator_home + this.getCreator() + "/thumbnail/" + this.getName() + ".jpg", (double)x, (double)y, (double)width, (double)height, scale);
    }
}
