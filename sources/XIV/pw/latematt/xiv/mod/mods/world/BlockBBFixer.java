package pw.latematt.xiv.mod.mods.world;

import net.minecraft.block.BlockCactus;
import net.minecraft.block.BlockFire;
import net.minecraft.block.BlockWeb;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import pw.latematt.xiv.XIV;
import pw.latematt.xiv.command.Command;
import pw.latematt.xiv.command.CommandHandler;
import pw.latematt.xiv.event.Listener;
import pw.latematt.xiv.event.events.BlockAddBBEvent;
import pw.latematt.xiv.mod.Mod;
import pw.latematt.xiv.mod.ModType;
import pw.latematt.xiv.utils.ChatLogger;
import pw.latematt.xiv.value.Value;

/**
 * @author Matthew
 */
public class BlockBBFixer extends Mod implements Listener<BlockAddBBEvent>, CommandHandler {
    private final Value<Boolean> cactus = new Value<>("blockbbfixer_cactus", true);
    private final Value<Boolean> cobweb = new Value<>("blockbbfixer_cobweb", true);
    private final Value<Boolean> fire = new Value<>("blockbbfixer_fire", true);

    public BlockBBFixer() {
        super("BlockBBFixer", ModType.WORLD);
        Command.newCommand().cmd("blockbbfixer").description("Base command for BlockBBFixer mod.").arguments("<action>").aliases("bbfixer", "blockfixer", "bbf").handler(this).build();
    }

    @Override
    public void onEventCalled(BlockAddBBEvent event) {
        if (event.getBlock() instanceof BlockCactus && cactus.getValue() || event.getBlock() instanceof BlockWeb && cobweb.getValue() || event.getBlock() instanceof BlockFire && fire.getValue()) {
            BlockPos pos = event.getPos();
            event.setAxisAlignedBB(new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1.0D, pos.getY() + 1.0D, pos.getZ() + 1.0D));
        }
    }

    @Override
    public void onCommandRan(String message) {
        String[] arguments = message.split(" ");
        if (arguments.length >= 2) {
            String action = arguments[1];
            switch (action.toLowerCase()) {
                case "cactus":
                    if (arguments.length >= 3) {
                        if (arguments[2].equalsIgnoreCase("-d")) {
                            cactus.setValue(cactus.getDefault());
                        } else {
                            cactus.setValue(Boolean.parseBoolean(arguments[2]));
                        }
                    } else {
                        cactus.setValue(!cactus.getValue());
                    }
                    ChatLogger.print(String.format("BlockBBFixer will %s fix the bounding box of Cacti.", cactus.getValue() ? "now" : "no longer"));
                    break;
                case "cobweb":
                    if (arguments.length >= 3) {
                        if (arguments[2].equalsIgnoreCase("-d")) {
                            cobweb.setValue(cobweb.getDefault());
                        } else {
                            cobweb.setValue(Boolean.parseBoolean(arguments[2]));
                        }
                    } else {
                        cobweb.setValue(!cobweb.getValue());
                    }
                    ChatLogger.print(String.format("BlockBBFixer will %s fix the bounding box of Cobwebs.", cobweb.getValue() ? "now" : "no longer"));
                    break;
                case "fire": {
                    if (arguments.length >= 3) {
                        if (arguments[2].equalsIgnoreCase("-d")) {
                            fire.setValue(fire.getDefault());
                        } else {
                            fire.setValue(Boolean.parseBoolean(arguments[2]));
                        }
                    } else {
                        fire.setValue(!fire.getValue());
                    }
                    ChatLogger.print(String.format("BlockBBFixer will %s fix the bounding box of Fire.", fire.getValue() ? "now" : "no longer"));
                    break;
                }
                default:
                    ChatLogger.print("Invalid action, valid: cactus, cobweb, fire");
                    break;
            }
        } else {
            ChatLogger.print("Invalid arguments, valid: blockbbfixer <action>");
        }
    }

    @Override
    public void onEnabled() {
        XIV.getInstance().getListenerManager().add(this);
    }

    @Override
    public void onDisabled() {
        XIV.getInstance().getListenerManager().remove(this);
    }
}
