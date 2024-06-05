package net.minecraft.src;

import java.util.*;

public interface ScoreObjectiveCriteria
{
    public static final Map field_96643_a = new HashMap();
    public static final ScoreObjectiveCriteria field_96641_b = new ScoreDummyCriteria("dummy");
    public static final ScoreObjectiveCriteria field_96642_c = new ScoreDummyCriteria("deathCount");
    public static final ScoreObjectiveCriteria field_96639_d = new ScoreDummyCriteria("playerKillCount");
    public static final ScoreObjectiveCriteria field_96640_e = new ScoreDummyCriteria("totalKillCount");
    public static final ScoreObjectiveCriteria field_96638_f = new ScoreHealthCriteria("health");
    
    String func_96636_a();
    
    int func_96635_a(final List p0);
    
    boolean isReadOnly();
}
