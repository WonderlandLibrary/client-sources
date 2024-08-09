/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data;

import java.util.HashMap;
import java.util.Map;

public class EntityNameRewriter {
    private static final Map<String, String> entityNames = new HashMap<String, String>();

    private static void reg(String string, String string2) {
        entityNames.put("minecraft:" + string, "minecraft:" + string2);
    }

    public static String rewrite(String string) {
        String string2 = entityNames.get(string);
        if (string2 != null) {
            return string2;
        }
        string2 = entityNames.get("minecraft:" + string);
        if (string2 != null) {
            return string2;
        }
        return string;
    }

    static {
        EntityNameRewriter.reg("commandblock_minecart", "command_block_minecart");
        EntityNameRewriter.reg("ender_crystal", "end_crystal");
        EntityNameRewriter.reg("evocation_fangs", "evoker_fangs");
        EntityNameRewriter.reg("evocation_illager", "evoker");
        EntityNameRewriter.reg("eye_of_ender_signal", "eye_of_ender");
        EntityNameRewriter.reg("fireworks_rocket", "firework_rocket");
        EntityNameRewriter.reg("illusion_illager", "illusioner");
        EntityNameRewriter.reg("snowman", "snow_golem");
        EntityNameRewriter.reg("villager_golem", "iron_golem");
        EntityNameRewriter.reg("vindication_illager", "vindicator");
        EntityNameRewriter.reg("xp_bottle", "experience_bottle");
        EntityNameRewriter.reg("xp_orb", "experience_orb");
    }
}

