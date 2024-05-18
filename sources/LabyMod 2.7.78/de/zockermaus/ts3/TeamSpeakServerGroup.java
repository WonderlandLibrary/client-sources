package de.zockermaus.ts3;

import java.util.ArrayList;
import java.util.List;

public class TeamSpeakServerGroup
{
    private static final List<TeamSpeakServerGroup> groups = new ArrayList();
    int sgid;
    String groupName;
    int type;
    int iconId;
    int savebd;

    public TeamSpeakServerGroup(int sgid)
    {
        this.sgid = sgid;
    }

    public void setSgid(int sgid)
    {
        this.sgid = sgid;
    }

    public void setGroupName(String groupName)
    {
        this.groupName = groupName;
    }

    public void setType(int type)
    {
        this.type = type;
    }

    public void setIconId(int iconId)
    {
        this.iconId = iconId;
    }

    public void setSavebd(int savebd)
    {
        this.savebd = savebd;
    }

    public int getSgid()
    {
        return this.sgid;
    }

    public String getGroupName()
    {
        return this.groupName;
    }

    public int getType()
    {
        return this.type;
    }

    public int getIconId()
    {
        return this.iconId;
    }

    public int getSavebd()
    {
        return this.savebd;
    }

    public static void addGroup(TeamSpeakServerGroup group)
    {
        groups.add(group);
    }

    public static List<TeamSpeakServerGroup> getGroups()
    {
        return groups;
    }
}
