package pw.latematt.xiv.mod.mods.render;

import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import net.minecraft.block.material.Material;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import pw.latematt.timer.Timer;
import pw.latematt.xiv.XIV;
import pw.latematt.xiv.command.Command;
import pw.latematt.xiv.command.CommandHandler;
import pw.latematt.xiv.event.Listener;
import pw.latematt.xiv.event.events.IngameHUDRenderEvent;
import pw.latematt.xiv.event.events.ReadPacketEvent;
import pw.latematt.xiv.file.XIVFile;
import pw.latematt.xiv.mod.Mod;
import pw.latematt.xiv.mod.ModType;
import pw.latematt.xiv.utils.ChatLogger;
import pw.latematt.xiv.utils.RenderUtils;
import pw.latematt.xiv.value.Value;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * I HATE FUCKING NORMIES AND RUDY IS A FUCKING NORMIE REEEEEEEEEE
 *
 * @author Matthew
 */
public class HUD extends Mod implements Listener<IngameHUDRenderEvent>, CommandHandler {
    private final Value<Boolean> watermark = new Value<>("hud_watermark", false);
    private final Value<Boolean> organize = new Value<>("hud_organize", false);
    private final Value<Boolean> arraylist = new Value<>("hud_arraylist", true);
    private final Value<Boolean> coords = new Value<>("hud_coords", true);
    private final Value<Boolean> fps = new Value<>("hud_fps", true);
    private final Value<Boolean> ign = new Value<>("hud_ign", false);
    private final Value<Boolean> time = new Value<>("hud_time", true);
    private final Value<Boolean> lag = new Value<>("hud_lag", false);
    private final Value<Boolean> direction = new Value<>("hud_direction", false);
    private final Value<Boolean> potions = new Value<>("hud_potions", true);
    private final Value<Boolean> armor = new Value<>("hud_armor", true);
    private final Value<Boolean> rudysucks = new Value<>("hud_rudysucks", false);
    private final Value<Boolean> tabGui = new Value<>("hud_tabgui", true);
    private final Value<Boolean> notifications = new Value<>("hud_notifications", true);
    private final Timer timer = new Timer();
    private final Listener readPacketListener;

    public HUD() {
        super("HUD", ModType.RENDER);
        Command.newCommand().cmd("hud").description("Base command for the HUD mod.").arguments("<action>").handler(this).build();

        readPacketListener = new Listener<ReadPacketEvent>() {
            @Override
            public void onEventCalled(ReadPacketEvent event) {
                if (event.getPacket() instanceof S02PacketChat)
                    return;
                timer.reset();
            }
        };
        setEnabled(true);

        new XIVFile("hudconfig", "json") {
            @Override
            @SuppressWarnings("unchecked")
            public void load() throws IOException {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                BufferedReader reader = new BufferedReader(new FileReader(file));
                List<Value> values = gson.fromJson(reader, new TypeToken<List<Value>>() {
                }.getType());
                for (Value value : values) {
                    XIV.getInstance().getValueManager().getContents().stream().filter(value1 -> value.getName().equals(value1.getName())).forEach(value1 -> value1.setValue(value.getValue()));
                }
            }

            @Override
            public void save() throws IOException {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                List<Value> values = XIV.getInstance().getValueManager().getContents().stream().filter(value -> value.getName().startsWith("hud_")).collect(Collectors.toList());
                Files.write(gson.toJson(values).getBytes("UTF-8"), file);
            }
        };
    }

    @Override
    public void onEventCalled(IngameHUDRenderEvent event) {
        XIV.getInstance().getNotificationsHandler().tick();
        if (mc.gameSettings.showDebugInfo)
            return;

        ScaledResolution scaledResolution = RenderUtils.newScaledResolution();
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        if (watermark.getValue())
            mc.fontRendererObj.drawStringWithShadow("XIV", 2, 2, 0x80FFFFFF);
        if (rudysucks.getValue()) {
            int y = 2;
            if (watermark.getValue() || (watermark.getValue() && time.getValue()))
                y += 8;
            mc.fontRendererObj.drawStringWithShadow("\2471r\2472u\2473d\2474y \2475s\2476u\2477c\2478k\2479s", 2, y, 0xFFFFFFFF);
        }
        if (time.getValue()) {
            int x = 2;
            if (watermark.getValue())
                x += mc.fontRendererObj.getStringWidth("XIV ");
            else if (rudysucks.getValue())
                x += mc.fontRendererObj.getStringWidth("rudy sucks ");
            SimpleDateFormat format = new SimpleDateFormat("h:mm a");
            mc.fontRendererObj.drawStringWithShadow(format.format(new Date()), x, 2, 0xDDDDDDDD);
        }
        if (tabGui.getValue()) {
            int y = 2;
            if (watermark.getValue() || rudysucks.getValue() || time.getValue())
                y += 10;
            if (watermark.getValue() && rudysucks.getValue())
                y += 10;

            XIV.getInstance().getTabHandler().drawGui(2, y);
        }
        if (notifications.getValue()) {
            int x = 2;
            int y = 2;
            if (watermark.getValue() || rudysucks.getValue() || time.getValue())
                y += 10;
            if (watermark.getValue() && rudysucks.getValue())
                y += 10;
            if (tabGui.getValue())
                y += XIV.getInstance().getTabHandler().getTabHeight() * ModType.values().length + 2;
            XIV.getInstance().getNotificationsHandler().draw(x, y);
        }

        drawInfo(scaledResolution);
        if (arraylist.getValue())
            drawArraylist(scaledResolution);
        if (potions.getValue())
            drawPotions(scaledResolution);
        if (lag.getValue())
            drawLag(scaledResolution);
        if (armor.getValue())
            drawArmor(scaledResolution);
        if (direction.getValue())
            drawDirection(scaledResolution);

        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    private void drawArmor(ScaledResolution scaledResolution) {
        if (mc.playerController.isNotCreative()) {
            int x = 14;
            int width = scaledResolution.getScaledWidth() / 2;
            int height = scaledResolution.getScaledHeight() -
                    (mc.thePlayer.isInsideOfMaterial(Material.water) ?
                            mc.thePlayer.getActivePotionEffect(Potion.ABSORPTION) != null ? 55 : 65 : 55);

            GlStateManager.pushMatrix();
            GlStateManager.enableRescaleNormal();
            GlStateManager.disableBlend();
            for (int index = 3; index >= 0; index--) {
                ItemStack stack = mc.thePlayer.inventory.armorInventory[index];
                if (stack != null) {
                    RenderHelper.enableStandardItemLighting();
                    mc.getRenderItem().renderItemAndEffectIntoGUI(stack, width + x, height);
                    mc.getRenderItem().renderItemOverlayIntoGUI(mc.fontRendererObj, stack, width + x, height);
                    RenderHelper.disableStandardItemLighting();

                    GlStateManager.disableDepth();
                    GlStateManager.depthMask(false);
                    GlStateManager.scale(0.50F, 0.50F, 0.50F);
                    if (stack.getItem() == Items.golden_apple && stack.hasEffect()) {
                        mc.fontRendererObj.drawStringWithShadow("god", (width + x) * 2, height * 2, 0xFFFF0000);
                    } else {
                        NBTTagList enchants = stack.getEnchantmentTagList();
                        if (enchants != null) {
                            int encY = 0;
                            Enchantment[] important = new Enchantment[]{Enchantment.PROTECTION, Enchantment.UNBREAKING, Enchantment.SHARPNESS, Enchantment.FIRE_ASPECT, Enchantment.EFFICIENCY, Enchantment.FEATHER_FALLING, Enchantment.POWER, Enchantment.FLAME, Enchantment.PUNCH, Enchantment.FORTUNE, Enchantment.INFINITY, Enchantment.THORNS};
                            if (enchants.tagCount() >= 6) {
                                mc.fontRendererObj.drawStringWithShadow("god", (width + x) * 2, height * 2, 0xFFFF0000);
                            } else {
                                for (int index1 = 0; index1 < enchants.tagCount(); ++index1) {
                                    short id = enchants.getCompoundTagAt(index1).getShort("id");
                                    short level = enchants.getCompoundTagAt(index1).getShort("lvl");
                                    Enchantment enc = Enchantment.func_180306_c(id);
                                    if (enc != null) {
                                        for (Enchantment importantEnchantment : important) {
                                            if (enc == importantEnchantment) {
                                                String encName = enc.getTranslatedName(level).substring(0, 1).toLowerCase();
                                                if (level > 99)
                                                    encName = encName + "99+";
                                                else
                                                    encName = encName + level;
                                                mc.fontRendererObj.drawStringWithShadow(encName, (width + x) * 2, (height + encY) * 2, 0xFFAAAAAA);
                                                encY += 5;
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    GlStateManager.depthMask(true);
                    GlStateManager.enableDepth();
                    GlStateManager.scale(2.0F, 2.0F, 2.0F);

                    x += 18;
                }
            }
            GlStateManager.disableRescaleNormal();
            GlStateManager.popMatrix();
        }
    }

    private void drawPotions(ScaledResolution scaledResolution) {
        int x = scaledResolution.getScaledWidth() - 2;
        int y = scaledResolution.getScaledHeight() - 10;
        if (mc.ingameGUI.getChatGUI().getChatOpen())
            y -= 13;
        for (Object o : mc.thePlayer.getActivePotionEffects()) {
            PotionEffect effect = (PotionEffect) o;
            String name = I18n.format(effect.getEffectName());

            Fullbright fullbright = (Fullbright) XIV.getInstance().getModManager().find("fullbright");
            if (fullbright.isEnabled() && fullbright.potion.getValue() && effect.getPotionID() == Potion.NIGHT_VISION.getId())
                continue;

            if (effect.getAmplifier() == 1)
                name = name + " " + I18n.format("enchantment.level.2");
            else if (effect.getAmplifier() == 2)
                name = name + " " + I18n.format("enchantment.level.3");
            else if (effect.getAmplifier() == 3)
                name = name + " " + I18n.format("enchantment.level.4");
            else if (effect.getAmplifier() > 0)
                name = name + " " + (effect.getAmplifier() + 1);

            int var1 = effect.getDuration() / 20;
            int var2 = var1 / 60;
            var1 %= 60;
            char color = '7';
            if (var2 == 0)
                if (var1 <= 5)
                    color = '4';
                else if (var1 <= 10)
                    color = 'c';
                else if (var1 <= 15)
                    color = '6';
                else if (var1 <= 20)
                    color = 'e';

            name = String.format("%s \247%s%s", name, color, Potion.getDurationString(effect));
            mc.fontRendererObj.drawStringWithShadow(name, x - mc.fontRendererObj.getStringWidth(name), y, Potion.potionTypes[effect.getPotionID()].getLiquidColor());
            y -= 10;
        }
    }

    private void drawArraylist(ScaledResolution scaledResolution) {
        int x = scaledResolution.getScaledWidth() - 2;
        int y = 2;

        List<Mod> mods = XIV.getInstance().getModManager().getContents().stream().filter(mod -> mod.isVisible() && mod.isEnabled()).collect(Collectors.toList());
        if (organize.getValue())
            Collections.sort(mods, (mod1, mod2) -> {
                String mod1Name = mod1.getDisplayName();
                String mod2Name = mod2.getDisplayName();
                return mc.fontRendererObj.getStringWidth(mod1Name) > mc.fontRendererObj.getStringWidth(mod2Name) ? -1 : mc.fontRendererObj.getStringWidth(mod2Name) > mc.fontRendererObj.getStringWidth(mod1Name) ? 1 : 0;
            });

        for (Mod mod : mods) {
            String name = mod.getDisplayName();
            mc.fontRendererObj.drawStringWithShadow(name, x - mc.fontRendererObj.getStringWidth(name), y, mod.getColor());
            y += 10;
        }
    }

    private void drawInfo(ScaledResolution scaledResolution) {
        List<String> info = new ArrayList<>();

        if (coords.getValue())
            info.add(String.format("\247rXYZ\2477 %s %s %s", MathHelper.floor_double(mc.thePlayer.posX), MathHelper.floor_double(mc.thePlayer.posY), MathHelper.floor_double(mc.thePlayer.posZ)));

        if (ign.getValue())
            info.add(String.format("\247rIGN\2477 %s", mc.getSession().getUsername()));

        if (fps.getValue())
            info.add(String.format("\247rFPS\2477 %s", mc.debug.split(" fps")[0]));

        int y = scaledResolution.getScaledHeight() - 10;
        if (mc.ingameGUI.getChatGUI().getChatOpen())
            y -= 13;
        for (String infoString : info) {
            mc.fontRendererObj.drawStringWithShadow(infoString, 2, y, 0xFFFFFFFF);
            y -= 9;
        }
    }

    private void drawDirection(ScaledResolution scaledResolution) {
        EnumFacing yaw = EnumFacing.getHorizontal(MathHelper.floor_double((double) (mc.thePlayer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3);
        String displayString = yaw.getName().substring(0, 1).toUpperCase() + yaw.getName().substring(1);
        mc.fontRendererObj.drawStringWithShadow(displayString, (scaledResolution.getScaledWidth() - mc.fontRendererObj.getStringWidth(displayString)) / 2, (BossStatus.statusBarTime > 0 ? 20 : 2), 0xFFFFFFFF);
    }

    private void drawLag(ScaledResolution scaledResolution) {
        String lag;
        int color;

        if (timer.hasReached(20000L)) {
            lag = "|";
            color = 0xFF990000;
        } else {
            if (timer.hasReached(15000L)) {
                lag = "||";
                color = 0xFFFF0000;
            } else {
                if (timer.hasReached(10000L)) {
                    lag = "|||";
                    color = 0xFFCC3300;
                } else {
                    if (timer.hasReached(5000L)) {
                        lag = "||||";
                        color = 0xFF669900;
                    } else {
                        if (timer.hasReached(1000L)) {
                            lag = "||||";
                            color = 0xFF22DD00;
                        } else {
                            lag = "|||||";
                            color = 0xFF00FF00;
                        }
                    }
                }
            }
        }

        if (timer.hasReached(1000))
            lag += " \2477" + timer.getDifference() + "ms";
        int x = scaledResolution.getScaledWidth() / 2 + 93;
        int y = scaledResolution.getScaledHeight() - 10;
        if (mc.ingameGUI.getChatGUI().getChatOpen())
            y -= 13;
        mc.fontRendererObj.drawStringWithShadow(lag, x, y, color);
    }

    @Override
    public void onCommandRan(String message) {
        String[] arguments = message.split(" ");
        if (arguments.length >= 2) {
            String action = arguments[1];

            switch (action.toLowerCase()) {
                case "watermark":
                    if (arguments.length >= 3) {
                        if (arguments[2].equalsIgnoreCase("-d")) {
                            watermark.setValue(watermark.getDefault());
                        } else {
                            watermark.setValue(Boolean.parseBoolean(arguments[2]));
                        }
                    } else {
                        watermark.setValue(!watermark.getValue());
                    }
                    ChatLogger.print(String.format("HUD will %s display the watermark.", (watermark.getValue() ? "now" : "no longer")));
                    break;
                case "organize":
                    if (arguments.length >= 3) {
                        if (arguments[2].equalsIgnoreCase("-d")) {
                            organize.setValue(organize.getDefault());
                        } else {
                            organize.setValue(Boolean.parseBoolean(arguments[2]));
                        }
                    } else {
                        organize.setValue(!organize.getValue());
                    }
                    ChatLogger.print(String.format("HUD will %s display organize mods in the arraylist.", (organize.getValue() ? "now" : "no longer")));
                    break;
                case "arraylist":
                    if (arguments.length >= 3) {
                        if (arguments[2].equalsIgnoreCase("-d")) {
                            arraylist.setValue(arraylist.getDefault());
                        } else {
                            arraylist.setValue(Boolean.parseBoolean(arguments[2]));
                        }
                    } else {
                        arraylist.setValue(!arraylist.getValue());
                    }
                    ChatLogger.print(String.format("HUD will %s display the arraylist.", (arraylist.getValue() ? "now" : "no longer")));
                    break;
                case "coords":
                    if (arguments.length >= 3) {
                        if (arguments[2].equalsIgnoreCase("-d")) {
                            coords.setValue(coords.getDefault());
                        } else {
                            coords.setValue(Boolean.parseBoolean(arguments[2]));
                        }
                    } else {
                        coords.setValue(!coords.getValue());
                    }
                    ChatLogger.print(String.format("HUD will %s display your coords.", (coords.getValue() ? "now" : "no longer")));
                    break;
                case "fps":
                    if (arguments.length >= 3) {
                        if (arguments[2].equalsIgnoreCase("-d")) {
                            fps.setValue(fps.getDefault());
                        } else {
                            fps.setValue(Boolean.parseBoolean(arguments[2]));
                        }
                    } else {
                        fps.setValue(!fps.getValue());
                    }
                    ChatLogger.print(String.format("HUD will %s display your FPS.", (fps.getValue() ? "now" : "no longer")));
                    break;
                case "ign":
                    if (arguments.length >= 3) {
                        if (arguments[2].equalsIgnoreCase("-d")) {
                            ign.setValue(ign.getDefault());
                        } else {
                            ign.setValue(Boolean.parseBoolean(arguments[2]));
                        }
                    } else {
                        ign.setValue(!ign.getValue());
                    }
                    ChatLogger.print(String.format("HUD will %s display your in-game name.", (ign.getValue() ? "now" : "no longer")));
                    break;
                case "dir":
                case "direction":
                    if (arguments.length >= 3) {
                        if (arguments[2].equalsIgnoreCase("-d")) {
                            direction.setValue(direction.getDefault());
                        } else {
                            direction.setValue(Boolean.parseBoolean(arguments[2]));
                        }
                    } else {
                        direction.setValue(!direction.getValue());
                    }
                    ChatLogger.print(String.format("HUD will %s display your direction.", (direction.getValue() ? "now" : "no longer")));
                    break;
                case "time":
                    if (arguments.length >= 3) {
                        if (arguments[2].equalsIgnoreCase("-d")) {
                            time.setValue(time.getDefault());
                        } else {
                            time.setValue(Boolean.parseBoolean(arguments[2]));
                        }
                    } else {
                        time.setValue(!time.getValue());
                    }
                    ChatLogger.print(String.format("HUD will %s display the current time.", (time.getValue() ? "now" : "no longer")));
                    break;
                case "potions":
                    if (arguments.length >= 3) {
                        if (arguments[2].equalsIgnoreCase("-d")) {
                            potions.setValue(potions.getDefault());
                        } else {
                            potions.setValue(Boolean.parseBoolean(arguments[2]));
                        }
                    } else {
                        potions.setValue(!potions.getValue());
                    }
                    ChatLogger.print(String.format("HUD will %s display potion status.", (potions.getValue() ? "now" : "no longer")));
                    break;
                case "armor":
                    if (arguments.length >= 3) {
                        if (arguments[2].equalsIgnoreCase("-d")) {
                            armor.setValue(armor.getDefault());
                        } else {
                            armor.setValue(Boolean.parseBoolean(arguments[2]));
                        }
                    } else {
                        armor.setValue(!armor.getValue());
                    }
                    ChatLogger.print(String.format("HUD will %s display armor status.", (armor.getValue() ? "now" : "no longer")));
                    break;
                case "lag":
                    if (arguments.length >= 3) {
                        if (arguments[2].equalsIgnoreCase("-d")) {
                            lag.setValue(lag.getDefault());
                        } else {
                            lag.setValue(Boolean.parseBoolean(arguments[2]));
                        }
                    } else {
                        lag.setValue(!lag.getValue());
                    }
                    ChatLogger.print(String.format("HUD will %s display your lag.", (lag.getValue() ? "now" : "no longer")));
                    break;
                case "tabgui":
                    if (arguments.length >= 3) {
                        if (arguments[2].equalsIgnoreCase("-d")) {
                            tabGui.setValue(tabGui.getDefault());
                        } else {
                            tabGui.setValue(Boolean.parseBoolean(arguments[2]));
                        }
                    } else {
                        tabGui.setValue(!tabGui.getValue());
                    }

                    if (tabGui.getValue()) {
                        XIV.getInstance().getListenerManager().add(XIV.getInstance().getTabHandler());
                    } else {
                        XIV.getInstance().getListenerManager().remove(XIV.getInstance().getTabHandler());
                    }

                    ChatLogger.print(String.format("HUD will %s display a TabGUI.", (tabGui.getValue() ? "now" : "no longer")));
                    break;
                case "notifications":
                    if (arguments.length >= 3) {
                        if (arguments[2].equalsIgnoreCase("-d")) {
                            notifications.setValue(notifications.getDefault());
                        } else {
                            notifications.setValue(Boolean.parseBoolean(arguments[2]));
                        }
                    } else {
                        notifications.setValue(!notifications.getValue());
                    }

                    ChatLogger.print(String.format("HUD will %s display Notifications.", (notifications.getValue() ? "now" : "no longer")));
                    break;
                case "rudysucks":
                    if (arguments.length >= 3) {
                        if (arguments[2].equalsIgnoreCase("-d")) {
                            rudysucks.setValue(rudysucks.getDefault());
                        } else {
                            rudysucks.setValue(Boolean.parseBoolean(arguments[2]));
                        }
                    } else {
                        rudysucks.setValue(!rudysucks.getValue());
                    }
                    ChatLogger.print(String.format("HUD will %s display \"rudy sucks\".", (rudysucks.getValue() ? "now" : "no longer")));
                    break;
                default:
                    ChatLogger.print("Invalid action, valid: watermark, arraylist, organize, coords, fps, ign, lag, direction, time, potions, armor, tabgui, notifications");
                    break;
            }
        } else {
            ChatLogger.print("Invalid arguments, valid: hud <action>");
        }

        XIV.getInstance().getFileManager().saveFile("hudconfig");
    }

    @Override
    public void onEnabled() {
        XIV.getInstance().getListenerManager().add(this, readPacketListener);
        if (tabGui.getValue()) {
            XIV.getInstance().getListenerManager().add(XIV.getInstance().getTabHandler());
        }
    }

    @Override
    public void onDisabled() {
        XIV.getInstance().getListenerManager().remove(this, readPacketListener);
        if (!tabGui.getValue()) {
            XIV.getInstance().getListenerManager().remove(XIV.getInstance().getTabHandler());
        }
    }
}
