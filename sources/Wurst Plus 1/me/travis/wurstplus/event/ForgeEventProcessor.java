package me.travis.wurstplus.event;

import me.travis.wurstplus.wurstplusMod;
import me.travis.wurstplus.command.Command;
import me.travis.wurstplus.command.commands.PeekCommand;
import me.travis.wurstplus.event.events.DisplaySizeChangedEvent;
import me.travis.wurstplus.gui.UIRenderer;
import me.travis.wurstplus.gui.wurstplus.wurstplusGUI;
import me.travis.wurstplus.gui.rgui.component.container.use.Frame;
import me.travis.wurstplus.module.ModuleManager;
import me.travis.wurstplus.util.wurstplusTessellator;
import me.travis.wurstplus.util.Wrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiShulkerBox;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraftforge.client.event.*;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

public class ForgeEventProcessor {

    private int displayWidth;
    private int displayHeight;

    @SubscribeEvent
    public void onUpdate(LivingEvent.LivingUpdateEvent event) {
        if (event.isCanceled()) return;
        // wurstplusMod.EVENT_BUS.post(new UpdateEvent());

        if (Minecraft.getMinecraft().displayWidth != displayWidth || Minecraft.getMinecraft().displayHeight != displayHeight) {
            wurstplusMod.EVENT_BUS.post(new DisplaySizeChangedEvent());
            displayWidth = Minecraft.getMinecraft().displayWidth;
            displayHeight = Minecraft.getMinecraft().displayHeight;

            wurstplusMod.getInstance().getGuiManager().getChildren().stream()
                    .filter(component -> component instanceof Frame)
                    .forEach(component -> wurstplusGUI.dock((Frame) component));
        }

        if (PeekCommand.sb != null) {
            ScaledResolution scaledresolution = new ScaledResolution(Minecraft.getMinecraft());
            int i = scaledresolution.getScaledWidth();
            int j = scaledresolution.getScaledHeight();
            GuiShulkerBox gui = new GuiShulkerBox(Wrapper.getPlayer().inventory, PeekCommand.sb);
            gui.setWorldAndResolution(Wrapper.getMinecraft(), i, j);
            Minecraft.getMinecraft().displayGuiScreen(gui);
            PeekCommand.sb = null;
        }
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (Wrapper.getPlayer() == null) return;
        ModuleManager.onUpdate();
        wurstplusMod.getInstance().getGuiManager().callTick(wurstplusMod.getInstance().getGuiManager());
    }

    @SubscribeEvent
    public void onWorldRender(RenderWorldLastEvent event) {
        if (event.isCanceled()) return;
        ModuleManager.onWorldRender(event);
    }

    @SubscribeEvent
    public void onRenderPre(RenderGameOverlayEvent.Pre event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.BOSSINFO && ModuleManager.isModuleEnabled("BossStack")) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent.Post event) {
        if (event.isCanceled()) return;

        RenderGameOverlayEvent.ElementType target = RenderGameOverlayEvent.ElementType.EXPERIENCE;
        if (!Wrapper.getPlayer().isCreative() && Wrapper.getPlayer().getRidingEntity() instanceof AbstractHorse)
            target = RenderGameOverlayEvent.ElementType.HEALTHMOUNT;

        if (event.getType() == target) {
            ModuleManager.onRender();
            GL11.glPushMatrix();
            UIRenderer.renderAndUpdateFrames();
            GL11.glPopMatrix();
            wurstplusTessellator.releaseGL();
        } else if (event.getType() == RenderGameOverlayEvent.ElementType.BOSSINFO && ModuleManager.isModuleEnabled("BossStack")) {
            // BossStack.render(event);
        }
    }

    @SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (Keyboard.getEventKeyState())
            ModuleManager.onBind(Keyboard.getEventKey());
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public ClientChatEvent onChatSent(ClientChatEvent event) {
        if (event.getMessage().startsWith(Command.getCommandPrefix())) {
            event.setCanceled(true);
            try {
                Wrapper.getMinecraft().ingameGUI.getChatGUI().addToSentMessages(event.getMessage());

                if (event.getMessage().length() > 1)
                    wurstplusMod.getInstance().commandManager.callCommand(event.getMessage().substring(Command.getCommandPrefix().length() - 1));
                else
                    Command.sendChatMessage("Please enter a command.");
            } catch (Exception e) {
                e.printStackTrace();
                Command.sendChatMessage("Error occured while running command! (" + e.getMessage() + ")");
            }
            event.setMessage("");
        }
        return event;
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public RenderPlayerEvent.Pre onPlayerDrawn(RenderPlayerEvent.Pre event) {
        wurstplusMod.EVENT_BUS.post(event);
        return event;
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public RenderPlayerEvent.Post onPlayerDrawn(RenderPlayerEvent.Post event) {
        wurstplusMod.EVENT_BUS.post(event);
        return event;
    }

    @SubscribeEvent()
    public void onChunkLoaded(ChunkEvent.Load event) {
        wurstplusMod.EVENT_BUS.post(event);
    }

    @SubscribeEvent()
    public ChunkEvent.Unload onChunkLoaded(ChunkEvent.Unload event) {
        wurstplusMod.EVENT_BUS.post(event);
        return event;
    }

    @SubscribeEvent
    public InputUpdateEvent onInputUpdate(InputUpdateEvent event) {
        wurstplusMod.EVENT_BUS.post(event);
        return event;
    }

    @SubscribeEvent
    public LivingEntityUseItemEvent.Start onLivingEntityUseItemEventTick(LivingEntityUseItemEvent.Start entityUseItemEvent) {
        wurstplusMod.EVENT_BUS.post(entityUseItemEvent);
        return entityUseItemEvent;
    }

    @SubscribeEvent
    public LivingDamageEvent onLivingDamageEvent(LivingDamageEvent event) {
        wurstplusMod.EVENT_BUS.post(event);
        return event;
    }

    @SubscribeEvent
    public EntityJoinWorldEvent onEntityJoinWorldEvent(EntityJoinWorldEvent entityJoinWorldEvent) {
        wurstplusMod.EVENT_BUS.post(entityJoinWorldEvent);
        return entityJoinWorldEvent;
    }

    @SubscribeEvent
    public PlayerSPPushOutOfBlocksEvent onPlayerPush(PlayerSPPushOutOfBlocksEvent event) {
        wurstplusMod.EVENT_BUS.post(event);
        return event;
    }

    @SubscribeEvent
    public PlayerInteractEvent.LeftClickBlock onLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
        wurstplusMod.EVENT_BUS.post(event);
        return event;
    }

    @SubscribeEvent
    public AttackEntityEvent onAttackEntity(AttackEntityEvent entityEvent) {
        // Command.sendChatMessage("Hit");
        wurstplusMod.EVENT_BUS.post(entityEvent);
        return entityEvent;
    }

    @SubscribeEvent
    public RenderBlockOverlayEvent onRenderBlockOverlay(RenderBlockOverlayEvent event) {
        wurstplusMod.EVENT_BUS.post(event);
        return event;
    }

    @SubscribeEvent
    public LivingDeathEvent kill(LivingDeathEvent event) {
        return event;
    }

}
