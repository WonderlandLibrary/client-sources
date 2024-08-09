package mathax.client.systems.modules.player;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import mathax.client.eventbus.EventHandler;
import mathax.client.events.game.GameLeftEvent;
import mathax.client.events.world.TickEvent;
import mathax.client.settings.*;
import mathax.client.systems.commands.arguments.CompoundNbtTagArgumentType;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.c2s.play.CreativeInventoryActionC2SPacket;

import java.awt.*;
import java.util.*;
import java.util.List;

public class RainbowArmor extends Module {

    private final SettingGroup sgGeneral = settings.getDefaultGroup();
    private final SettingGroup sgHead = settings.createGroup("Head Settings");
    private final SettingGroup sgArmor = settings.createGroup("Armor Settings");
    private final Setting<RainbowMode> rainbowMODE = sgGeneral.add(new EnumSetting.Builder<RainbowMode>()
        .name("rainbow-mode")
        .description("RGB Method.")
        .defaultValue(RainbowMode.Math)
        .build()
    );
    private final Setting<Integer> speed = sgGeneral.add(new IntSetting.Builder()
        .name("speed")
        .description("WARNING: High speeds will cause a ton of lag and can easily crash the game!")
        .defaultValue(5)
        .min(1)
        .sliderMax(30)
        .visible(() -> rainbowMODE.get() == RainbowMode.Math || rainbowMODE.get() == RainbowMode.Index)
        .build()
    );
    private final Setting<Double> SPEED = sgGeneral.add(new DoubleSetting.Builder()
        .name("speed")
        .description("WARNING: using speed on random is currently very expermential and glitchy.")
        .defaultValue(.5)
        .min(0.001)
        .max(2)
        .visible(() -> rainbowMODE.get() == RainbowMode.Random)
        .build()
    );
    private final Setting<RandomMode> randomMODE = sgGeneral.add(new EnumSetting.Builder<RandomMode>()
        .name("random-mode")
        .description("random mode.")
        .defaultValue(RandomMode.Simple)
        .visible(() -> rainbowMODE.get() == RainbowMode.Random)
        .build()
    );
    private final Setting<Boolean> toggleOnLog = sgGeneral.add(new BoolSetting.Builder()
        .name("toggle-on-log")
        .description("Disables RGB armor when you disconnect from a server.")
        .defaultValue(true)
        .build()
    );
    private final Setting<HeadMode> headMode = sgHead.add(new EnumSetting.Builder<HeadMode>()
        .name("head-mode")
        .description("Head item mode.")
        .defaultValue(HeadMode.Normal)
        .build()
    );
    private final Setting<Integer> headDelay = sgHead.add(new IntSetting.Builder()
        .name("head-item-delay")
        .description("Delay for head item.")
        .defaultValue(2)
        .min(1)
        .sliderMax(20)
        .visible(() -> headMode.get() == HeadMode.Custom)
        .build()
    );
    private final Setting<java.util.List<Item>> items = sgHead.add(new ItemListSetting.Builder()
        .name("items")
        .description("Select items to be shown on head.")
        .defaultValue(List.of())
        .visible(() -> headMode.get() == HeadMode.Custom)
        .build()
    );
    private final Setting<Boolean> isCustomNbt = sgHead.add(new BoolSetting.Builder()
        .name("enable-custom-nbt")
        .description("Enable custom NBT.")
        .defaultValue(true)
        .visible(() -> headMode.get() == HeadMode.Custom)
        .build()
    );
    private final Setting<List<String>> customNbt = sgHead.add(new StringListSetting.Builder()
        .name("custom-nbt")
        .description("Custom NBT to set to the items.")
        .defaultValue(List.of("{Enchantments:[{id:\"minecraft:aqua_affinity\",lvl:0s}]}"))
        .visible(() -> headMode.get() == HeadMode.Custom && isCustomNbt.get())
        .build()
    );

    private final Setting<Boolean> excludeChest = sgArmor.add(new BoolSetting.Builder()
        .name("chestplate")
        .description("Disables Chestplate.")
        .defaultValue(true)
        .onChanged(onChanged -> {
            if(this.isActive()) {
                mc.interactionManager.clickCreativeStack(Items.AIR.getDefaultStack(), 6);
            }
        })
        .build()
    );
    private final Setting<Boolean> excludeLeggings = sgArmor.add(new BoolSetting.Builder()
        .name("leggings")
        .description("Disables Leggings.")
        .defaultValue(true)
        .onChanged(onChanged -> {
            if(this.isActive()) {
                mc.interactionManager.clickCreativeStack(Items.AIR.getDefaultStack(), 7);
            }
        })
        .build()
    );
    private final Setting<Boolean> excludeBoots = sgArmor.add(new BoolSetting.Builder()
        .name("boots")
        .description("Disables Boots.")
        .defaultValue(true)
        .onChanged(onChanged -> {
            if(this.isActive()) {
                mc.interactionManager.clickCreativeStack(Items.AIR.getDefaultStack(), 8);
            }
        })
        .build()
    );
    public RainbowArmor() {
        super(Categories.Player, Items.LEATHER_HELMET, "rainbow-armor", "Gives you Rainbow Leather Armor with various modes.");
    }

    private int ticks;
    boolean isIndex = false;
    boolean isHSB = false;
    private int nbtI;

    @Override
    public boolean onActivate() {
        if (!mc.player.getAbilities().creativeMode) {
            error("Creative mode only.");
            this.toggle();
        }
        nbtI = 0;
        return false;
    }
    @EventHandler
    private void onGameLeft(GameLeftEvent event) {
        if (!toggleOnLog.get()) return;
        toggle();
    }
    @EventHandler
    private void onTick(TickEvent.Post event) {
        ticks++;
        if (headMode.get() == HeadMode.Normal) {
            ItemStack armor = new ItemStack(Items.LEATHER_HELMET);
            setArmorColors(5, armor);
        }
        if (headMode.get() == HeadMode.Custom) {
            if (ticks % headDelay.get() == 0) {
                setCustomHead();
            }
        }
        if (excludeChest.get()) {
            ItemStack armor = new ItemStack(Items.LEATHER_CHESTPLATE);
            setArmorColors(6, armor);
        }
        if (excludeLeggings.get()) {
            ItemStack armor = new ItemStack(Items.LEATHER_LEGGINGS);
            setArmorColors(7, armor);
        }
        if (excludeBoots.get()) {
            ItemStack armor = new ItemStack(Items.LEATHER_BOOTS);
            setArmorColors(8, armor);
        }
    }

    private void setCustomHead() {
        List<Item> selectedItems = new ArrayList<>(items.get());
        Collections.shuffle(selectedItems, new Random());
        if (selectedItems.isEmpty()) return;
        ItemStack itemStack = new ItemStack(selectedItems.get(0 % selectedItems.size()));

        // Apply enchantments
        if (!customNbt.get().isEmpty() && isCustomNbt.get()) {
            if (nbtI >= customNbt.get().size()) nbtI = 0;
            String nbt = customNbt.get().get(nbtI);
            try {
                if(!Objects.equals(nbt, "")) {
                    itemStack.setNbt(new CompoundNbtTagArgumentType().parse(new StringReader(nbt)));
                }
            } catch (CommandSyntaxException e) {
                throw new RuntimeException(e);
            }
            nbtI++;
        }
        CreativeInventoryActionC2SPacket packet = new CreativeInventoryActionC2SPacket(5, itemStack);
        mc.player.networkHandler.sendPacket(packet);

    }
    private void setArmorColors(int slot, ItemStack armor) {
        NbtCompound nbt = new NbtCompound();
        NbtCompound tag = nbt.getCompound("display");
        if (rainbowMODE.get() == RainbowMode.Math) {
            tag.putInt("color", generateRGBMATH());
        } else if (rainbowMODE.get() == RainbowMode.HSB) {
            tag.putInt("color", generateHSB(100, 3000));
        } else if (rainbowMODE.get() == RainbowMode.Random) {
            tag.putInt("color", generateRGB());
        }  else if (rainbowMODE.get() == RainbowMode.Index) {
            tag.putInt("color", generateRGBINDEX());
        }
        nbt.put("display", tag);
        armor.setNbt(nbt);
        CreativeInventoryActionC2SPacket packet = new CreativeInventoryActionC2SPacket(slot, armor);
        mc.player.networkHandler.sendPacket(packet);
    }

    // rgb

    private int generateRGBMATH() {
        if (rainbowMODE.get() == RainbowMode.Math) {

            long time = System.currentTimeMillis();
            int red = (int) ((Math.sin(time / (2000.0 / speed.get())) + 1) * 127.5);
            int green = (int) ((Math.sin(time / (2000.0 / speed.get()) + 2 * Math.PI / 3) + 1) * 127.5);
            int blue = (int) ((Math.sin(time / (2000.0 / speed.get()) + 4 * Math.PI / 3) + 1) * 127.5);

            return (red << 16) | (green << 8) | blue;
        } else {
            return 0;
        }
    }

    public int generateHSB(float offset, float speed) {
        // thanks xlzxq for the rgb method lmao
        if (rainbowMODE.get() == RainbowMode.HSB) {
            float hue = (float) (System.currentTimeMillis() % 3000) + (offset);
            while (hue > speed) {
                hue -= speed;
            }
            hue /= speed;
            if (hue > 0.5) {
                hue = 0.5F - (hue - 0.5f);
            }
            hue += 0.5F;
            int rgbColor = Color.HSBtoRGB(hue, 0.5F, 1F);

            // Convert RGB color to hex
            String hexColor = String.format("#%06x", rgbColor & 0xFFFFFF);

            // Return hex color as an RGB integer value
            return parseHexColor(hexColor);
        } else {
            return 0;
        }
    }

    private int generateRGBINDEX() {
        if (rainbowMODE.get() == RainbowMode.Index) {
            long time = System.currentTimeMillis();

            int colorIndex = (int) ((time / (2000.0 / speed.get())) % 7);

            return getColorByIndex(colorIndex);
        } else {
            return 0;
        }
    }

    private int generateRGB() {
        if (rainbowMODE.get() == RainbowMode.Random) {
            if (randomMODE.get() == RandomMode.Simple) {
                int offset = (int) (Math.random() * 7);

                long time = System.currentTimeMillis();
                int colorIndex = (offset + (int) (time / (2000.0 / SPEED.get()))) % 7;

                return getColorByIndex(colorIndex);
            } else if (randomMODE.get() == RandomMode.Abstract) {
                int offset = (int) (Math.random() * 101);

                long time = System.currentTimeMillis();
                int colorIndex = (offset + (int) (time / (2000.0 / SPEED.get()))) % 101;

                return getColorByIndex(colorIndex);
            } else if (randomMODE.get() == RandomMode.Unique) {
                int offset = (int) (Math.random() * 50);

                long time = System.currentTimeMillis();
                int colorIndex = (offset + (int) (time / (2000.0 / SPEED.get()))) % 50;

                return getColorByIndex(colorIndex);
            } else if (randomMODE.get() == RandomMode.Experimental) {
                int offset = (int) (Math.random() * 25);

                long time = System.currentTimeMillis();
                int colorIndex = (offset + (int) (time / (2000.0 / SPEED.get()))) % 25;

                return getColorByIndex(colorIndex);
            }
        } else {
            return 0;
        }
        return 0;
    }

    private int getColorByIndex(int index) {
        isIndex = true;

        if (randomMODE.get() == RandomMode.Simple) {


            switch (index) {
                case 0: return parseHexColor("#FF0000");
                case 1: return parseHexColor("#FFA500");
                case 2: return parseHexColor("#FFFF00");
                case 3: return parseHexColor("#00FF00");
                case 4: return parseHexColor("#0000FF");
                case 5: return parseHexColor("#4B0082");
                case 6: return parseHexColor("#EE82EE");
                default:
                    return 0;
            }
        }
        if (randomMODE.get() == RandomMode.Abstract) {
            switch (index) {
                case 0: return parseHexColor("#FF0000"); case 1: return parseHexColor("#FF3300"); case 2: return parseHexColor("#FF6600");
                case 3: return parseHexColor("#FF9900"); case 4: return parseHexColor("#FFCC00"); case 5: return parseHexColor("#FFFF00");
                case 6: return parseHexColor("#CCFF00"); case 7: return parseHexColor("#99FF00"); case 8: return parseHexColor("#66FF00");
                case 9: return parseHexColor("#33FF00"); case 10: return parseHexColor("#00FF00"); case 11: return parseHexColor("#00FF33");
                case 12: return parseHexColor("#00FF66"); case 13: return parseHexColor("#00FF99"); case 14: return parseHexColor("#00FFCC");
                case 15: return parseHexColor("#00FFFF"); case 16: return parseHexColor("#00CCFF"); case 17: return parseHexColor("#0099FF");
                case 18: return parseHexColor("#0066FF"); case 19: return parseHexColor("#0033FF"); case 20: return parseHexColor("#0000FF");
                case 21: return parseHexColor("#3300FF"); case 22: return parseHexColor("#6600FF"); case 23: return parseHexColor("#9900FF");
                case 24: return parseHexColor("#CC00FF"); case 25: return parseHexColor("#FF00FF"); case 26: return parseHexColor("#FF00CC");
                case 27: return parseHexColor("#FF0099"); case 28: return parseHexColor("#FF0066"); case 29: return parseHexColor("#FF0033");
                case 30: return parseHexColor("#FF6666"); case 31: return parseHexColor("#FF6633"); case 32: return parseHexColor("#FF6600");
                case 33: return parseHexColor("#FF9966"); case 34: return parseHexColor("#FF9933"); case 35: return parseHexColor("#FF9900");
                case 36: return parseHexColor("#FFCC66"); case 37: return parseHexColor("#FFCC33"); case 38: return parseHexColor("#FFCC00");
                case 39: return parseHexColor("#FFFF66"); case 40: return parseHexColor("#FFFF33"); case 41: return parseHexColor("#FFFF00");
                case 42: return parseHexColor("#CCFF66"); case 43: return parseHexColor("#CCFF33"); case 44: return parseHexColor("#CCFF00");
                case 45: return parseHexColor("#99FF66"); case 46: return parseHexColor("#99FF33"); case 47: return parseHexColor("#99FF00");
                case 48: return parseHexColor("#66FF66"); case 49: return parseHexColor("#66FF33"); case 50: return parseHexColor("#66FF00");
                case 51: return parseHexColor("#33FF66"); case 52: return parseHexColor("#33FF33"); case 53: return parseHexColor("#33FF00");
                case 54: return parseHexColor("#00FF66"); case 55: return parseHexColor("#00FF33"); case 56: return parseHexColor("#00FF00");
                case 57: return parseHexColor("#00FF33"); case 58: return parseHexColor("#00FF33"); case 59: return parseHexColor("#00FF00");
                case 60: return parseHexColor("#00CC33"); case 61: return parseHexColor("#00CC00"); case 62: return parseHexColor("#00CC33");
                case 63: return parseHexColor("#009933"); case 64: return parseHexColor("#009900"); case 65: return parseHexColor("#009933");
                case 66: return parseHexColor("#006633"); case 67: return parseHexColor("#006600"); case 68: return parseHexColor("#006633");
                case 69: return parseHexColor("#003333"); case 70: return parseHexColor("#003300"); case 71: return parseHexColor("#003333");
                case 72: return parseHexColor("#333300"); case 73: return parseHexColor("#333333"); case 74: return parseHexColor("#333300");
                case 75: return parseHexColor("#663300"); case 76: return parseHexColor("#663333"); case 77: return parseHexColor("#663300");
                case 78: return parseHexColor("#996600"); case 79: return parseHexColor("#996633"); case 80: return parseHexColor("#996600");
                case 81: return parseHexColor("#CC6600"); case 82: return parseHexColor("#CC6633"); case 83: return parseHexColor("#CC6600");
                case 84: return parseHexColor("#FF6600"); case 85: return parseHexColor("#FF6633"); case 86: return parseHexColor("#FF6600");
                case 87: return parseHexColor("#FF9900"); case 88: return parseHexColor("#FF9933"); case 89: return parseHexColor("#FF9900");
                case 90: return parseHexColor("#FFCC00"); case 91: return parseHexColor("#FFCC33"); case 92: return parseHexColor("#FFCC00");
                case 93: return parseHexColor("#FFFF00"); case 94: return parseHexColor("#FFFF33"); case 95: return parseHexColor("#FFFF00");
                case 96: return parseHexColor("#CCFF00"); case 97: return parseHexColor("#CCFF33"); case 98: return parseHexColor("#CCFF00");
                case 99: return parseHexColor("#99FF00"); case 100: return parseHexColor("#808080"); default: return parseHexColor("#FF0000");
            }
        }
        if (randomMODE.get() == RandomMode.Unique) {
            switch (index) {
                case 0: return parseHexColor("#FF3399"); case 1: return parseHexColor("#CC66FF"); case 2: return parseHexColor("#99CCFF");
                case 3: return parseHexColor("#33FFCC"); case 4: return parseHexColor("#00FFFF"); case 5: return parseHexColor("#66CCFF");
                case 6: return parseHexColor("#9966FF"); case 7: return parseHexColor("#FF99FF"); case 8: return parseHexColor("#FF9966");
                case 9: return parseHexColor("#FFCC99"); case 10: return parseHexColor("#CCFF99"); case 11: return parseHexColor("#99FF99");
                case 12: return parseHexColor("#99FFFF"); case 13: return parseHexColor("#CCFFFF"); case 14: return parseHexColor("#CCCCFF");
                case 15: return parseHexColor("#FFCCFF"); case 16: return parseHexColor("#FF99CC"); case 17: return parseHexColor("#FF6699");
                case 18: return parseHexColor("#FFCC66"); case 19: return parseHexColor("#FFFF99"); case 20: return parseHexColor("#CCFFCC");
                case 21: return parseHexColor("#99FFCC"); case 22: return parseHexColor("#66FFCC"); case 23: return parseHexColor("#33FF99");
                case 24: return parseHexColor("#33FFFF"); case 25: return parseHexColor("#66FFFF"); case 27: return parseHexColor("#FFCCFF");
                case 28: return parseHexColor("#FFCCCC"); case 29: return parseHexColor("#FF99FF"); case 30: return parseHexColor("#FF6666");
                case 31: return parseHexColor("#FF9999"); case 32: return parseHexColor("#FFCCCC"); case 33: return parseHexColor("#FFFFCC");
                case 34: return parseHexColor("#CCFF66"); case 35: return parseHexColor("#99FF66"); case 36: return parseHexColor("#66FF99");
                case 37: return parseHexColor("#33FF66"); case 38: return parseHexColor("#33FF33"); case 39: return parseHexColor("#66FF33");
                case 40: return parseHexColor("#99FF33"); case 41: return parseHexColor("#CCFF33"); case 42: return parseHexColor("#FFFF33");
                case 43: return parseHexColor("#FFCC33"); case 44: return parseHexColor("#FF9933"); case 45: return parseHexColor("#FF6633");
                case 46: return parseHexColor("#FF3333"); case 47: return parseHexColor("#FF3366"); case 48: return parseHexColor("#FF3399");
                case 49: return parseHexColor("#FF33CC"); default: return parseHexColor("#FF3399");
            }
        }

        if (randomMODE.get() == RandomMode.Experimental) {
            switch (index) {
                case 0: return parseHexColor("#9933CC"); case 1: return parseHexColor("#66FFFF"); case 2: return parseHexColor("#FF9966");
                case 3: return parseHexColor("#CCFF33"); case 4: return parseHexColor("#FFCCFF"); case 5: return parseHexColor("#FFCC66");
                case 6: return parseHexColor("#FF3333"); case 7: return parseHexColor("#FFFF00"); case 8: return parseHexColor("#66CCFF");
                case 9: return parseHexColor("#FF6600"); case 10: return parseHexColor("#00FF66"); case 11: return parseHexColor("#CCFFFF");
                case 12: return parseHexColor("#FF99CC"); case 13: return parseHexColor("#33FFCC"); case 14: return parseHexColor("#FF9933");
                case 15: return parseHexColor("#99FF99"); case 16: return parseHexColor("#FF6699"); case 17: return parseHexColor("#CCFFCC");
                case 18: return parseHexColor("#FF3333"); case 19: return parseHexColor("#FFCC99"); case 20: return parseHexColor("#99CCFF");
                case 21: return parseHexColor("#FFFF33"); case 22: return parseHexColor("#FF3399"); case 23: return parseHexColor("#CC66FF");
                case 24: return parseHexColor("#33FF99"); default: return parseHexColor("#FFFFFF"); // Default color, if needed
            }
        }
        return parseHexColor("#FFFFFF"); // Default color, if neede
    }

    private int parseHexColor(String hexColor) {
        if (isIndex) {
            return (int) Long.parseLong(hexColor.substring(1), 16);
        } else if (!isHSB) {
            hexColor = hexColor.substring(1);
            int rgbColor = Integer.parseInt(hexColor, 16);
            return rgbColor;
        }
        return 0;
    }

    public enum RandomMode {
        Simple,
        Abstract,
        Unique,
        Experimental,
    }
    public enum HeadMode {
        None,
        Normal,
        Custom,
    }
    public enum RainbowMode {
        Math,
        Index,
        HSB,
        Random,
    }
}
