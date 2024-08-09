package xyz.mathax.mathaxclient.systems.modules;

import net.minecraft.item.Items;
import net.minecraft.util.Pair;
import xyz.mathax.mathaxclient.utils.misc.MatHaxIdentifier;
import xyz.mathax.mathaxclient.utils.render.color.Color;

public class Categories {
    public static final Category Combat = new Category("Combat", new Pair<>(new MatHaxIdentifier("textures/icons/gui/category/combat.png"), Items.DIAMOND_SWORD), new Color(225, 0, 0, 255));
    public static final Category Movement = new Category("Movement", new Pair<>(new MatHaxIdentifier("textures/icons/gui/category/movement.png"), Items.CHAINMAIL_BOOTS), new Color(0, 125, 255, 255));
    public static final Category Render = new Category("Render", new Pair<>(new MatHaxIdentifier("textures/icons/gui/category/render.png"), Items.SPYGLASS), new Color(125, 255, 255, 255));
    public static final Category Player = new Category("Player", new Pair<>(new MatHaxIdentifier("textures/icons/gui/category/player.png"), Items.PLAYER_HEAD), new Color(245, 255, 100, 255));
    public static final Category World = new Category("World", new Pair<>(new MatHaxIdentifier("textures/icons/gui/category/world.png"), Items.GRASS_BLOCK), new Color(0, 150, 0, 255));
    public static final Category Chat = new Category("Chat", new Pair<>(new MatHaxIdentifier("textures/icons/gui/category/chat.png"), Items.OAK_SIGN), new Color(255, 255, 255, 255));
    public static final Category Misc = new Category("Misc", new Pair<>(new MatHaxIdentifier("textures/icons/gui/category/misc.png"), Items.BEACON), new Color(0, 50, 175, 255));

    public static boolean REGISTERING;

    public static void init() {
        REGISTERING = true;

        Modules.registerCategory(Combat);
        Modules.registerCategory(Movement);
        Modules.registerCategory(Render);
        Modules.registerCategory(Player);
        Modules.registerCategory(World);
        Modules.registerCategory(Chat);
        Modules.registerCategory(Misc);

        REGISTERING = false;
    }
}
