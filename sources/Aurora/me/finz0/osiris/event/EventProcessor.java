package me.finz0.osiris.event;

import com.mojang.realmsclient.gui.ChatFormatting;
import de.Hero.clickgui.ClickGUI;
import de.Hero.clickgui.Panel;
import me.finz0.osiris.gui.GuiManager;
import me.finz0.osiris.gui.clickgui.ClickGui;
import me.finz0.osiris.gui.clickgui.ClickGuiScreen;
import me.finz0.osiris.gui.clickgui.base.Component;
import me.finz0.osiris.gui.clickgui.base.ComponentType;
import me.finz0.osiris.gui.clickgui.elements.Frame;
import me.finz0.osiris.gui.clickgui.elements.Text;
import me.finz0.osiris.hud.HudComponentManager;
import me.finz0.osiris.AuroraMod;
import me.finz0.osiris.command.Command;
import me.finz0.osiris.command.CommandManager;
import me.finz0.osiris.event.events.PacketEvent;
import me.finz0.osiris.event.events.PlayerJoinEvent;
import me.finz0.osiris.event.events.PlayerLeaveEvent;
import me.finz0.osiris.module.ModuleManager;
import me.finz0.osiris.module.modules.gui.ClickGuiModule;
import me.finz0.osiris.module.modules.render.ShulkerPreview;
import me.finz0.osiris.module.modules.render.TabGui;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketPlayerListItem;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.awt.*;

import com.google.common.collect.Maps;
import org.apache.commons.io.IOUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

public class EventProcessor {
    public static EventProcessor INSTANCE;
    Minecraft mc = Minecraft.getMinecraft();
    CommandManager commandManager = new CommandManager();
    float hue = 0;
    Color c;
    int rgb;
    int speed = 2;

    private boolean isInit = false;

    public EventProcessor() {
        INSTANCE = this;
    }

    public int getRgb() {
        return rgb;
    }

    public Color getC() {
        return c;
    }

    public void setRainbowSpeed(int s) {
        speed = s;
    }

    public int getRainbowSpeed() {
        return speed;
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        //rainbow stuff
        c = Color.getHSBColor(hue, 1f, 1f);
        rgb = Color.HSBtoRGB(hue, 1f, 1f);
        hue += speed / 2000f;
        //Module onUpdate
        if (mc.player != null)

            if (!isInit) {
                ClickGuiModule.setColor();
                isInit = true;
            }
        if (!(mc.currentScreen instanceof ClickGuiScreen)) {
            ModuleManager.getModuleByName("ClickGUI").setEnabled(false);
        }

        ModuleManager.onUpdate();
    }

    @SubscribeEvent
    public void onWorldRender(RenderWorldLastEvent event) {
        if (event.isCanceled()) return;
        ModuleManager.onWorldRender(event);
    }

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent.Post event) {
        AuroraMod.EVENT_BUS.post(event);
        if (event.getType() == RenderGameOverlayEvent.ElementType.HOTBAR) {
            //module onRender
            ModuleManager.onRender();

        }
    }

    @SubscribeEvent
    public void onGuiRenderScreen(GuiScreenEvent event) {
        //shulker preview
        if (ShulkerPreview.active || ShulkerPreview.pinned) {
            NonNullList<ItemStack> nonnulllist = NonNullList.withSize(27, ItemStack.EMPTY);
            ItemStackHelper.loadAllItems(ShulkerPreview.nbt, nonnulllist);

            GlStateManager.enableBlend();
            GlStateManager.disableRescaleNormal();
            RenderHelper.disableStandardItemLighting();
            GlStateManager.disableLighting();
            GlStateManager.disableDepth();

            mc.getRenderItem().zLevel = 300.0F;

            int oldX = ShulkerPreview.drawX;
            int oldY = ShulkerPreview.drawY;

            Gui.drawRect(oldX + 9, oldY - 14, oldX + 173, oldY + 52, 0xaa111111);
            mc.renderEngine.bindTexture(new ResourceLocation("textures/gui/container/shulker_box.png"));
            GlStateManager.color(1, 1, 1, 1);
            mc.ingameGUI.drawTexturedModalRect(oldX + 10, oldY - 4, 7, 17, 162, 54);

            mc.fontRenderer.drawString(ShulkerPreview.itemStack.getDisplayName(), oldX + 12, oldY - 12, 0xffffff);

            GlStateManager.enableBlend();
            //GlStateManager.enableAlpha();
            GlStateManager.enableTexture2D();
            GlStateManager.enableLighting();
            GlStateManager.enableDepth();
            RenderHelper.enableGUIStandardItemLighting();
            for (int i = 0; i < nonnulllist.size(); i++) {
                int iX = oldX + (i % 9) * 18 + 11;
                int iY = oldY + (i / 9) * 18 - 11 + 8;
                ItemStack itemStack = nonnulllist.get(i);
                mc.getRenderItem().renderItemAndEffectIntoGUI(itemStack, iX, iY);
                mc.getRenderItem().renderItemOverlayIntoGUI(mc.fontRenderer, itemStack, iX, iY, null);
                if (ShulkerPreview.pinned) {
                    if (isPointInRegion(iX, iY, 18, 18, ShulkerPreview.mouseX, ShulkerPreview.mouseY)) {
                        FontRenderer font = itemStack.getItem().getFontRenderer(itemStack);
                        net.minecraftforge.fml.client.config.GuiUtils.preItemToolTip(itemStack);
                        event.getGui().drawHoveringText(event.getGui().getItemToolTip(itemStack), iX, iY);
                        net.minecraftforge.fml.client.config.GuiUtils.postItemToolTip();
                    }
                }
            }
            RenderHelper.disableStandardItemLighting();
            mc.getRenderItem().zLevel = 0.0F;

            GlStateManager.enableLighting();
            GlStateManager.enableDepth();
            RenderHelper.enableStandardItemLighting();
            GlStateManager.enableRescaleNormal();

            ShulkerPreview.active = false;
        }
    }

    @SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (Keyboard.getEventKeyState()) {
            if (Keyboard.getEventKey() == 0 || Keyboard.getEventKey() == Keyboard.KEY_NONE) return;
            //Module binds
            ModuleManager.onBind(Keyboard.getEventKey());
            //Macro
            AuroraMod.getInstance().macroManager.getMacros().forEach(m -> {
                if (m.getKey() == Keyboard.getEventKey())
                    m.onMacro();
            });
            //TabGUI
            if (Keyboard.getEventKey() == Keyboard.KEY_UP) {
                if (!TabGui.extended) TabGui.selected--;
                else TabGui.selectedMod--;
            }
            if (Keyboard.getEventKey() == Keyboard.KEY_DOWN) {
                if (!TabGui.extended) TabGui.selected++;
                else TabGui.selectedMod++;
            }
            if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT) {
                if (!TabGui.extended) TabGui.extended = true;
                else if (TabGui.currentMod != null) TabGui.currentMod.toggle();
            }
            if (Keyboard.getEventKey() == Keyboard.KEY_LEFT) TabGui.extended = false;
        }
    }

    @SubscribeEvent
    public void onMouseInput(InputEvent.MouseInputEvent event) {
        if (Mouse.getEventButtonState())
            AuroraMod.EVENT_BUS.post(event);
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onChatSent(ClientChatEvent event) {

        if (event.getMessage().startsWith(Command.getPrefix())) {
            event.setCanceled(true);
            try {
                mc.ingameGUI.getChatGUI().addToSentMessages(event.getMessage());
                commandManager.callCommand(event.getMessage().substring(1));
            } catch (Exception e) {
                e.printStackTrace();
                Command.sendClientMessage(ChatFormatting.DARK_RED + "Error: " + e.getMessage());
            }
            //event.setMessage("");
        }
    }

    @SubscribeEvent
    public void onRenderScreen(RenderGameOverlayEvent.Text event) {
        AuroraMod.EVENT_BUS.post(event);
    }

    @SubscribeEvent
    public void onChatReceived(ClientChatReceivedEvent event) {
        AuroraMod.EVENT_BUS.post(event);
    }

    @SubscribeEvent
    public void onAttackEntity(AttackEntityEvent event) {
        AuroraMod.EVENT_BUS.post(event);
    }

    @SubscribeEvent
    public void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        AuroraMod.EVENT_BUS.post(event);
    }

    @SubscribeEvent
    public void onDrawBlockHighlight(DrawBlockHighlightEvent event) {
        AuroraMod.EVENT_BUS.post(event);
    }

    @SubscribeEvent
    public void onRenderBlockOverlay(RenderBlockOverlayEvent event) {
        AuroraMod.EVENT_BUS.post(event);
    }

    @SubscribeEvent
    public void onLivingDamage(LivingDamageEvent event) {
        AuroraMod.EVENT_BUS.post(event);
    }

    @SubscribeEvent
    public void onLivingEntityUseItemFinish(LivingEntityUseItemEvent.Finish event) {
        AuroraMod.EVENT_BUS.post(event);
    }

    @SubscribeEvent
    public void onInputUpdate(InputUpdateEvent event) {
        AuroraMod.EVENT_BUS.post(event);
    }

    @SubscribeEvent
    public void onLivingDeath(LivingDeathEvent event) {
        AuroraMod.EVENT_BUS.post(event);
    }

    @SubscribeEvent
    public void onWorldUnload(WorldEvent.Unload event) {
        AuroraMod.EVENT_BUS.post(event);
    }

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event) {
        AuroraMod.EVENT_BUS.post(event);
    }

    @EventHandler
    private Listener<PacketEvent.Receive> receiveListener = new Listener<>(event -> {
        if (event.getPacket() instanceof SPacketPlayerListItem) {
            SPacketPlayerListItem packet = (SPacketPlayerListItem) event.getPacket();
            if (packet.getAction() == SPacketPlayerListItem.Action.ADD_PLAYER) {
                for (SPacketPlayerListItem.AddPlayerData playerData : packet.getEntries()) {
                    if (playerData.getProfile().getId() != mc.session.getProfile().getId()) {
                        new Thread(() -> {
                            String name = resolveName(playerData.getProfile().getId().toString());
                            if (name != null) {
                                if (mc.player != null && mc.player.ticksExisted >= 1000)
                                    AuroraMod.EVENT_BUS.post(new PlayerJoinEvent(name));
                            }
                        }).start();
                    }
                }
            }
            if (packet.getAction() == SPacketPlayerListItem.Action.REMOVE_PLAYER) {
                for (SPacketPlayerListItem.AddPlayerData playerData : packet.getEntries()) {
                    if (playerData.getProfile().getId() != mc.session.getProfile().getId()) {
                        new Thread(() -> {
                            final String name = resolveName(playerData.getProfile().getId().toString());
                            if (name != null) {
                                if (mc.player != null && mc.player.ticksExisted >= 1000)
                                    AuroraMod.EVENT_BUS.post(new PlayerLeaveEvent(name));
                            }
                        }).start();
                    }
                }
            }
        }
    });

    private final Map<String, String> uuidNameCache = Maps.newConcurrentMap();

    public String resolveName(String uuid) {
        uuid = uuid.replace("-", "");
        if (uuidNameCache.containsKey(uuid)) {
            return uuidNameCache.get(uuid);
        }

        final String url = "https://api.mojang.com/user/profiles/" + uuid + "/names";
        try {
            final String nameJson = IOUtils.toString(new URL(url));
            if (nameJson != null && nameJson.length() > 0) {
                final JSONArray jsonArray = (JSONArray) JSONValue.parseWithException(nameJson);
                if (jsonArray != null) {
                    final JSONObject latestName = (JSONObject) jsonArray.get(jsonArray.size() - 1);
                    if (latestName != null) {
                        return latestName.get("name").toString();
                    }
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void init() {
        AuroraMod.EVENT_BUS.subscribe(this);
        MinecraftForge.EVENT_BUS.register(this);
    }

    //from GuiContainer
    private boolean isMouseOverSlot(Slot slotIn, int mouseX, int mouseY) {
        return this.isPointInRegion(slotIn.xPos, slotIn.yPos, 16, 16, mouseX, mouseY);
    }

    protected boolean isPointInRegion(int rectX, int rectY, int rectWidth, int rectHeight, int pointX, int pointY) {
        int i = ShulkerPreview.guiLeft;
        int j = ShulkerPreview.guiTop;
        pointX = pointX - i;
        pointY = pointY - j;
        return pointX >= rectX - 1 && pointX < rectX + rectWidth + 1 && pointY >= rectY - 1 && pointY < rectY + rectHeight + 1;
    }

}
