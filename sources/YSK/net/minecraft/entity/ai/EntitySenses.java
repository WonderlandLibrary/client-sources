package net.minecraft.entity.ai;

import java.util.*;
import net.minecraft.entity.*;
import com.google.common.collect.*;

public class EntitySenses
{
    private static final String[] I;
    List<Entity> seenEntities;
    List<Entity> unseenEntities;
    EntityLiving entityObj;
    
    static {
        I();
    }
    
    public EntitySenses(final EntityLiving entityObj) {
        this.seenEntities = (List<Entity>)Lists.newArrayList();
        this.unseenEntities = (List<Entity>)Lists.newArrayList();
        this.entityObj = entityObj;
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("$(9\u0006\u0012\"", "GIWUw");
    }
    
    public boolean canSee(final Entity entity) {
        if (this.seenEntities.contains(entity)) {
            return " ".length() != 0;
        }
        if (this.unseenEntities.contains(entity)) {
            return "".length() != 0;
        }
        this.entityObj.worldObj.theProfiler.startSection(EntitySenses.I["".length()]);
        final boolean canEntityBeSeen = this.entityObj.canEntityBeSeen(entity);
        this.entityObj.worldObj.theProfiler.endSection();
        if (canEntityBeSeen) {
            this.seenEntities.add(entity);
            "".length();
            if (3 != 3) {
                throw null;
            }
        }
        else {
            this.unseenEntities.add(entity);
        }
        return canEntityBeSeen;
    }
    
    public void clearSensingCache() {
        this.seenEntities.clear();
        this.unseenEntities.clear();
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (2 < -1) {
                throw null;
            }
        }
        return sb.toString();
    }
}
