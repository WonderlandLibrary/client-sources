package studio.dreamys.module.mines;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import studio.dreamys.module.Category;
import studio.dreamys.module.Module;
import studio.dreamys.util.PlayerUtils;
import studio.dreamys.util.RenderUtils;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CoordsGrabber extends Module {
    public static final HashMap<String, BlockPos> coords = new HashMap<>();
    public static final Pattern xyzPattern = Pattern.compile(".*?(?<user>[a-zA-Z0-9_]{3,16}):.*?(?<x>[0-9]{1,3}),? (?:y: )?(?<y>[0-9]{1,3}),? (?:z: )?(?<z>[0-9]{1,3}).*?");
    public static final Pattern xzPattern = Pattern.compile(".*(?<user>[a-zA-Z0-9_]{3,16}):.* (?<x>[0-9]{1,3}),? (?<z>[0-9]{1,3}).*");

    public CoordsGrabber() {
        super("CoordsGrabber", Category.MINES);
    }

    @SubscribeEvent
    public void onClientChatReceived(ClientChatReceivedEvent e) {
        String msg = ChatFormatting.stripFormatting(e.message.getUnformattedText());
        Matcher xyzMatcher = xyzPattern.matcher(msg);
        Matcher xzMatcher = xzPattern.matcher(msg);
        if (xyzMatcher.matches()) waypointChatMessage(xyzMatcher.group("x"), xyzMatcher.group("y"), xyzMatcher.group("z"));
        else if (xzMatcher.matches()) waypointChatMessage(xzMatcher.group("x"), "100", xzMatcher.group("z"));
    }

    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent e) {
        if (coords.size() > 0) {
            coords.forEach((name, bp) -> RenderUtils.renderWaypointText(name, bp, e.partialTicks));
        }
    }

    @SubscribeEvent
    public void onChangeWorld(WorldEvent.Load e) {
        coords.clear();
    }


    public static void waypointChatMessage(String x, String y, String z) {
        ChatComponentText component = new ChatComponentText(
                PlayerUtils.prefix + " > §eFound coordinates in a chat message, click a button to set a waypoint.\n"
        );
        ChatComponentText city = (ChatComponentText) new ChatComponentText("§f[Lost Precursor City] ").setChatStyle(
                new ChatStyle()
                        .setChatClickEvent(
                                new ClickEvent(
                                        ClickEvent.Action.RUN_COMMAND,
                                        "/chw set §fCity " + x + " " + y + " " + z
                                )
                        ).setChatHoverEvent(
                                new HoverEvent(
                                        HoverEvent.Action.SHOW_TEXT,
                                        new ChatComponentText("§eSet waypoint for Lost Precursor City")
                                )
                        )
        );
        ChatComponentText temple = (ChatComponentText) new ChatComponentText("§a[Jungle Temple] ").setChatStyle(
                new ChatStyle()
                        .setChatClickEvent(
                                new ClickEvent(
                                        ClickEvent.Action.RUN_COMMAND,
                                        "/chw set §aTemple " + x + " " + y + " " + z
                                )
                        ).setChatHoverEvent(
                                new HoverEvent(
                                        HoverEvent.Action.SHOW_TEXT,
                                        new ChatComponentText("§eSet waypoint for Jungle Temple")
                                )
                        )
        );
        ChatComponentText den = (ChatComponentText) new ChatComponentText("§e[Goblin Queen's Den] ").setChatStyle(
                new ChatStyle()
                        .setChatClickEvent(
                                new ClickEvent(
                                        ClickEvent.Action.RUN_COMMAND,
                                        "/chw set §eQueen " + x + " " + y + " " + z
                                )
                        ).setChatHoverEvent(
                                new HoverEvent(
                                        HoverEvent.Action.SHOW_TEXT,
                                        new ChatComponentText("§eSet waypoint for Goblin Queen's Den")
                                )
                        )
        );
        ChatComponentText king = (ChatComponentText) new ChatComponentText("§e[Goblin King] ").setChatStyle(
                new ChatStyle()
                        .setChatClickEvent(
                                new ClickEvent(
                                        ClickEvent.Action.RUN_COMMAND,
                                        "/chw set §eKing " + x + " " + y + " " + z
                                )
                        ).setChatHoverEvent(
                                new HoverEvent(
                                        HoverEvent.Action.SHOW_TEXT,
                                        new ChatComponentText("§eSet waypoint for Goblin King")
                                )
                        )
        );
        ChatComponentText mines = (ChatComponentText) new ChatComponentText("§9[Mines of Divan] ").setChatStyle(
                new ChatStyle()
                        .setChatClickEvent(
                                new ClickEvent(
                                        ClickEvent.Action.RUN_COMMAND,
                                        "/chw set §9Mines " + x + " " + y + " " + z
                                )
                        ).setChatHoverEvent(
                                new HoverEvent(
                                        HoverEvent.Action.SHOW_TEXT,
                                        new ChatComponentText("§eSet waypoint for Mines of Divan")
                                )
                        )
        );
        ChatComponentText bal = (ChatComponentText) new ChatComponentText("§c[Khazad-dûm] ").setChatStyle(
                new ChatStyle()
                        .setChatClickEvent(
                                new ClickEvent(
                                        ClickEvent.Action.RUN_COMMAND,
                                        "/chw set §cBal " + x + " " + y + " " + z
                                )
                        ).setChatHoverEvent(
                                new HoverEvent(
                                        HoverEvent.Action.SHOW_TEXT,
                                        new ChatComponentText("§Sset waypoint for Khazad-dûm")
                                )
                        )
        );
        ChatComponentText fairy = (ChatComponentText) new ChatComponentText(EnumChatFormatting.LIGHT_PURPLE + "[Fairy Grotto] ").setChatStyle(
                new ChatStyle()
                        .setChatClickEvent(
                                new ClickEvent(
                                        ClickEvent.Action.RUN_COMMAND,
                                        "/chw set " + EnumChatFormatting.LIGHT_PURPLE + "Fairy " + x + " " + y + " " + z
                                )
                        ).setChatHoverEvent(
                                new HoverEvent(
                                        HoverEvent.Action.SHOW_TEXT,
                                        new ChatComponentText("§eSet waypoint for Fairy Grotto")
                                )
                        )
        );
        ChatComponentText custom = (ChatComponentText) new ChatComponentText("§e[Custom]").setChatStyle(
                new ChatStyle()
                        .setChatClickEvent(
                                new ClickEvent(
                                        ClickEvent.Action.SUGGEST_COMMAND,
                                        "/chw set §ename  " + x + " " + y + " " + z
                                )
                        ).setChatHoverEvent(
                                new HoverEvent(
                                        HoverEvent.Action.SHOW_TEXT,
                                        new ChatComponentText("§eSet custom waypoint")
                                )
                        )
        );
        if (!coords.containsKey("§fCity")) component.appendSibling(city);
        if (!coords.containsKey("§aTemple")) component.appendSibling(temple);
        if (!coords.containsKey("§eQueen")) component.appendSibling(den);
        if (!coords.containsKey("§eKing")) component.appendSibling(king);
        if (!coords.containsKey("§9Mines")) component.appendSibling(mines);
        if (!coords.containsKey("§cBal")) component.appendSibling(bal);
        if (!coords.containsKey(EnumChatFormatting.LIGHT_PURPLE + "Fairy")) component.appendSibling(fairy);
        component.appendSibling(custom);
        Minecraft.getMinecraft().thePlayer.addChatMessage(component);
    }
}
