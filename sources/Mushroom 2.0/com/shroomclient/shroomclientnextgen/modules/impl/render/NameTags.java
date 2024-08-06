package com.shroomclient.shroomclientnextgen.modules.impl.render;

import com.shroomclient.shroomclientnextgen.config.ConfigMode;
import com.shroomclient.shroomclientnextgen.config.ConfigOption;
import com.shroomclient.shroomclientnextgen.modules.Module;
import com.shroomclient.shroomclientnextgen.modules.ModuleCategory;
import com.shroomclient.shroomclientnextgen.modules.RegisterModule;
import com.shroomclient.shroomclientnextgen.util.C;
import com.shroomclient.shroomclientnextgen.util.TargetUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;

@RegisterModule(
    name = "Nametags",
    uniqueId = "nametags",
    description = "",
    category = ModuleCategory.Render
)
public class NameTags extends Module {

    @ConfigMode
    @ConfigOption(name = "Mode", description = "", order = 1)
    public static Mode mode = Mode.Name;

    public static boolean shouldNameTag(Entity entity) {
        return (
            !TargetUtil.isBot(entity) &&
            entity != C.p() &&
            entity instanceof PlayerEntity
        );
    }

    @Override
    protected void onEnable() {}

    @Override
    protected void onDisable() {}

    public enum Mode {
        Name,
        Box,
    }
}
