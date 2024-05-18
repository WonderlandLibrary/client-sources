package de.labystudio.modapi.events;

import de.labystudio.modapi.Event;
import de.labystudio.modapi.Listener;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.scoreboard.ScorePlayerTeam;

public class RenderTabOverlayEvent extends Event
{
    public static final Map<Listener, List<Method>> listenerMethods = new HashMap();
    private ScorePlayerTeam scorePlayerTeam;
    private String result = null;

    public Map<Listener, List<Method>> getListenerMethods()
    {
        return listenerMethods;
    }

    public RenderTabOverlayEvent(ScorePlayerTeam scorePlayerTeam)
    {
        this.scorePlayerTeam = scorePlayerTeam;
    }

    public ScorePlayerTeam getScorePlayerTeam()
    {
        return this.scorePlayerTeam;
    }

    public String getResult()
    {
        return this.result;
    }

    public void setResult(String result)
    {
        this.result = result;
    }
}
