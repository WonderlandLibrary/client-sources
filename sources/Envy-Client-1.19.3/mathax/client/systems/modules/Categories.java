package mathax.client.systems.modules;

import mathax.client.MatHax;
import mathax.client.utils.render.color.Color;
import net.minecraft.item.Items;

public class Categories {
    public static final Category Combat = new Category("Combat", Items.DIAMOND_SWORD, Color.fromRGBA(225, 0, 0, 255));
    public static final Category Movement = new Category("Movement", Items.DIAMOND_BOOTS, Color.fromRGBA(0, 125, 255, 255));
    public static final Category Render = new Category("Render", Items.TINTED_GLASS, Color.fromRGBA(125, 255, 255, 255));
    public static final Category Player = new Category("Player", Items.ARMOR_STAND, Color.fromRGBA(245, 255, 100, 255));
    public static final Category World = new Category("World", Items.GRASS_BLOCK, Color.fromRGBA(0, 150, 0, 255));
    public static final Category Chat = new Category("Chat", Items.BEACON, Color.fromRGBA(255, 255, 255, 255));
    public static final Category Misc = new Category("Misc", Items.NETHER_STAR, Color.fromRGBA(0, 50, 175, 255));
    public static final Category Ghost = new Category("Ghost", Items.GOLD_INGOT, Color.fromRGBA(255, 0, 0, 255));
    public static final Category Fun = new Category("Fun", Items.TNT, Color.fromRGBA(255, 0, 0, 255));
    public static final Category Experimental = new Category("Experimental", Items.BEDROCK, Color.fromRGBA(255, 0, 0, 255));
    public static final Category Crash = new Category("Crash", Items.BARRIER, Color.fromRGBA(255, 100, 0, 255));


    public static final Category Minigame = new Category("Minigame", Items.IRON_SWORD, Color.fromRGBA(255, 0, 0, 255));
    public static final Category Client = new Category("Client", Items.COMMAND_BLOCK, Color.fromRGBA(MatHax.INSTANCE.MATHAX_COLOR.r, MatHax.INSTANCE.MATHAX_COLOR.g, MatHax.INSTANCE.MATHAX_COLOR.b, 255));

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
        Modules.registerCategory(Ghost);
        Modules.registerCategory(Minigame);
        Modules.registerCategory(Client);
        Modules.registerCategory(Fun);
        Modules.registerCategory(Experimental);
        Modules.registerCategory(Crash);

        REGISTERING = false;
    }
}
