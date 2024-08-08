// 
// Decompiled by Procyon v0.5.36
// 

package me.perry.mcdonalds.features.modules.render;

import net.minecraft.init.SoundEvents;
import net.minecraftforge.event.entity.PlaySoundAtEntityEvent;
import net.minecraft.entity.passive.EntityBat;
import net.minecraftforge.client.event.RenderLivingEvent;
import java.util.Iterator;
import java.util.HashMap;
import net.minecraft.world.BossInfo;
import net.minecraft.client.gui.GuiBossOverlay;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.gui.BossInfoClient;
import java.util.UUID;
import java.util.Map;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.item.Item;
import net.minecraft.init.Blocks;
import net.minecraft.world.GameType;
import java.util.Random;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.network.play.server.SPacketTimeUpdate;
import me.perry.mcdonalds.event.events.PacketEvent;
import java.util.stream.Stream;
import java.util.function.Consumer;
import net.minecraft.entity.Entity;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.Objects;
import net.minecraft.entity.item.EntityItem;
import me.perry.mcdonalds.features.setting.Setting;
import me.perry.mcdonalds.features.modules.Module;

public class NoRender extends Module
{
    private static NoRender INSTANCE;
    public Setting<Boolean> fire;
    public Setting<Boolean> portal;
    public Setting<Boolean> pumpkin;
    public Setting<Boolean> totemPops;
    public Setting<Boolean> items;
    public Setting<Boolean> nausea;
    public Setting<Boolean> hurtcam;
    public Setting<Fog> fog;
    public Setting<Boolean> noWeather;
    public Setting<Boss> boss;
    public Setting<Float> scale;
    public Setting<Boolean> bats;
    public Setting<NoArmor> noArmor;
    public Setting<Boolean> glint;
    public Setting<Skylight> skylight;
    public Setting<Boolean> barriers;
    public Setting<Boolean> blocks;
    public Setting<Boolean> advancements;
    public Setting<Boolean> pigmen;
    public Setting<Boolean> timeChange;
    public Setting<Integer> time;
    
    public NoRender() {
        super("NoRender", "Allows you to stop rendering stuff", Category.RENDER, true, false, false);
        this.fire = (Setting<Boolean>)this.register(new Setting("Fire", (T)false, "Removes the portal overlay."));
        this.portal = (Setting<Boolean>)this.register(new Setting("Portal", (T)false, "Removes the portal overlay."));
        this.pumpkin = (Setting<Boolean>)this.register(new Setting("Pumpkin", (T)false, "Removes the pumpkin overlay."));
        this.totemPops = (Setting<Boolean>)this.register(new Setting("TotemPop", (T)false, "Removes the Totem overlay."));
        this.items = (Setting<Boolean>)this.register(new Setting("Items", (T)false, "Removes items on the ground."));
        this.nausea = (Setting<Boolean>)this.register(new Setting("Nausea", (T)false, "Removes Portal Nausea."));
        this.hurtcam = (Setting<Boolean>)this.register(new Setting("HurtCam", (T)false, "Removes shaking after taking damage."));
        this.fog = (Setting<Fog>)this.register(new Setting("Fog", (T)Fog.NONE, "Removes Fog."));
        this.noWeather = (Setting<Boolean>)this.register(new Setting("Weather", (T)false, "AntiWeather"));
        this.boss = (Setting<Boss>)this.register(new Setting("BossBars", (T)Boss.NONE, "Modifies the bossbars."));
        this.scale = (Setting<Float>)this.register(new Setting("Scale", (T)0.0f, (T)0.5f, (T)1.0f, v -> this.boss.getValue() == Boss.MINIMIZE || this.boss.getValue() != Boss.STACK, "Scale of the bars."));
        this.bats = (Setting<Boolean>)this.register(new Setting("Bats", (T)false, "Removes bats."));
        this.noArmor = (Setting<NoArmor>)this.register(new Setting("NoArmor", (T)NoArmor.NONE, "Doesnt Render Armor on players."));
        this.glint = (Setting<Boolean>)this.register(new Setting("Glint", (T)false, v -> this.noArmor.getValue() != NoArmor.NONE));
        this.skylight = (Setting<Skylight>)this.register(new Setting("Skylight", (T)Skylight.NONE));
        this.barriers = (Setting<Boolean>)this.register(new Setting("Barriers", (T)false, "Barriers"));
        this.blocks = (Setting<Boolean>)this.register(new Setting("Blocks", (T)false, "Blocks"));
        this.advancements = (Setting<Boolean>)this.register(new Setting("Advancements", (T)false));
        this.pigmen = (Setting<Boolean>)this.register(new Setting("Pigmen", (T)false));
        this.timeChange = (Setting<Boolean>)this.register(new Setting("TimeChange", (T)false));
        this.time = (Setting<Integer>)this.register(new Setting("Time", (T)0, (T)0, (T)23000, v -> this.timeChange.getValue()));
        this.setInstance();
    }
    
    public static NoRender getInstance() {
        if (NoRender.INSTANCE == null) {
            NoRender.INSTANCE = new NoRender();
        }
        return NoRender.INSTANCE;
    }
    
    private void setInstance() {
        NoRender.INSTANCE = this;
    }
    
    @Override
    public void onUpdate() {
        if (this.items.getValue()) {
            final Stream stream = NoRender.mc.world.loadedEntityList.stream();
            final Class<EntityItem> obj = EntityItem.class;
            Objects.requireNonNull(obj);
            final Stream filter = stream.filter(obj::isInstance);
            final Class<EntityItem> obj2 = EntityItem.class;
            Objects.requireNonNull(obj2);
            filter.map(obj2::cast).forEach(Entity::func_70106_y);
        }
        if (this.noWeather.getValue() && NoRender.mc.world.isRaining()) {
            NoRender.mc.world.setRainStrength(0.0f);
        }
        if (this.timeChange.getValue()) {
            NoRender.mc.world.setWorldTime((long)this.time.getValue());
        }
    }
    
    @SubscribeEvent
    public void onPacketReceive(final PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketTimeUpdate & this.timeChange.getValue()) {
            event.setCanceled(true);
        }
    }
    
    public void doVoidFogParticles(final int posX, final int posY, final int posZ) {
        final int i = 32;
        final Random random = new Random();
        final ItemStack itemstack = NoRender.mc.player.getHeldItemMainhand();
        final boolean flag = !this.barriers.getValue() || (NoRender.mc.playerController.getCurrentGameType() == GameType.CREATIVE && !itemstack.isEmpty() && itemstack.getItem() == Item.getItemFromBlock(Blocks.BARRIER));
        final BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
        for (int j = 0; j < 667; ++j) {
            this.showBarrierParticles(posX, posY, posZ, 16, random, flag, blockpos$mutableblockpos);
            this.showBarrierParticles(posX, posY, posZ, 32, random, flag, blockpos$mutableblockpos);
        }
    }
    
    public void showBarrierParticles(final int x, final int y, final int z, final int offset, final Random random, final boolean holdingBarrier, final BlockPos.MutableBlockPos pos) {
        final int i = x + NoRender.mc.world.rand.nextInt(offset) - NoRender.mc.world.rand.nextInt(offset);
        final int j = y + NoRender.mc.world.rand.nextInt(offset) - NoRender.mc.world.rand.nextInt(offset);
        final int k = z + NoRender.mc.world.rand.nextInt(offset) - NoRender.mc.world.rand.nextInt(offset);
        pos.setPos(i, j, k);
        final IBlockState iblockstate = NoRender.mc.world.getBlockState((BlockPos)pos);
        iblockstate.getBlock().randomDisplayTick(iblockstate, (World)NoRender.mc.world, (BlockPos)pos, random);
        if (!holdingBarrier && iblockstate.getBlock() == Blocks.BARRIER) {
            NoRender.mc.world.spawnParticle(EnumParticleTypes.BARRIER, (double)(i + 0.5f), (double)(j + 0.5f), (double)(k + 0.5f), 0.0, 0.0, 0.0, new int[0]);
        }
    }
    
    @SubscribeEvent
    public void onRenderPre(final RenderGameOverlayEvent.Pre event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.BOSSINFO && this.boss.getValue() != Boss.NONE) {
            event.setCanceled(true);
        }
    }
    
    @SubscribeEvent
    public void onRenderPost(final RenderGameOverlayEvent.Post event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.BOSSINFO && this.boss.getValue() != Boss.NONE) {
            if (this.boss.getValue() == Boss.MINIMIZE) {
                final Map<UUID, BossInfoClient> map = (Map<UUID, BossInfoClient>)NoRender.mc.ingameGUI.getBossOverlay().mapBossInfos;
                if (map == null) {
                    return;
                }
                final ScaledResolution scaledresolution = new ScaledResolution(NoRender.mc);
                final int i = scaledresolution.getScaledWidth();
                int j = 12;
                for (final Map.Entry<UUID, BossInfoClient> entry : map.entrySet()) {
                    final BossInfoClient info = entry.getValue();
                    final String text = info.getName().getFormattedText();
                    final int k = (int)(i / this.scale.getValue() / 2.0f - 91.0f);
                    GL11.glScaled((double)this.scale.getValue(), (double)this.scale.getValue(), 1.0);
                    if (!event.isCanceled()) {
                        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                        NoRender.mc.getTextureManager().bindTexture(GuiBossOverlay.GUI_BARS_TEXTURES);
                        NoRender.mc.ingameGUI.getBossOverlay().render(k, j, (BossInfo)info);
                        NoRender.mc.fontRenderer.drawStringWithShadow(text, i / this.scale.getValue() / 2.0f - NoRender.mc.fontRenderer.getStringWidth(text) / 2, (float)(j - 9), 16777215);
                    }
                    GL11.glScaled(1.0 / this.scale.getValue(), 1.0 / this.scale.getValue(), 1.0);
                    j += 10 + NoRender.mc.fontRenderer.FONT_HEIGHT;
                }
            }
            else if (this.boss.getValue() == Boss.STACK) {
                final Map<UUID, BossInfoClient> map = (Map<UUID, BossInfoClient>)NoRender.mc.ingameGUI.getBossOverlay().mapBossInfos;
                final HashMap<String, Pair<BossInfoClient, Integer>> to = new HashMap<String, Pair<BossInfoClient, Integer>>();
                for (final Map.Entry<UUID, BossInfoClient> entry2 : map.entrySet()) {
                    final String s = entry2.getValue().getName().getFormattedText();
                    if (to.containsKey(s)) {
                        Pair<BossInfoClient, Integer> p = to.get(s);
                        p = new Pair<BossInfoClient, Integer>(p.getKey(), p.getValue() + 1);
                        to.put(s, p);
                    }
                    else {
                        final Pair<BossInfoClient, Integer> p = new Pair<BossInfoClient, Integer>(entry2.getValue(), 1);
                        to.put(s, p);
                    }
                }
                final ScaledResolution scaledresolution2 = new ScaledResolution(NoRender.mc);
                final int l = scaledresolution2.getScaledWidth();
                int m = 12;
                for (final Map.Entry<String, Pair<BossInfoClient, Integer>> entry3 : to.entrySet()) {
                    String text = entry3.getKey();
                    final BossInfoClient info2 = entry3.getValue().getKey();
                    final int a = entry3.getValue().getValue();
                    text = text + " x" + a;
                    final int k2 = (int)(l / this.scale.getValue() / 2.0f - 91.0f);
                    GL11.glScaled((double)this.scale.getValue(), (double)this.scale.getValue(), 1.0);
                    if (!event.isCanceled()) {
                        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                        NoRender.mc.getTextureManager().bindTexture(GuiBossOverlay.GUI_BARS_TEXTURES);
                        NoRender.mc.ingameGUI.getBossOverlay().render(k2, m, (BossInfo)info2);
                        NoRender.mc.fontRenderer.drawStringWithShadow(text, l / this.scale.getValue() / 2.0f - NoRender.mc.fontRenderer.getStringWidth(text) / 2, (float)(m - 9), 16777215);
                    }
                    GL11.glScaled(1.0 / this.scale.getValue(), 1.0 / this.scale.getValue(), 1.0);
                    m += 10 + NoRender.mc.fontRenderer.FONT_HEIGHT;
                }
            }
        }
    }
    
    @SubscribeEvent
    public void onRenderLiving(final RenderLivingEvent.Pre<?> event) {
        if (this.bats.getValue() && event.getEntity() instanceof EntityBat) {
            event.setCanceled(true);
        }
    }
    
    @SubscribeEvent
    public void onPlaySound(final PlaySoundAtEntityEvent event) {
        if ((this.bats.getValue() && event.getSound().equals(SoundEvents.ENTITY_BAT_AMBIENT)) || event.getSound().equals(SoundEvents.ENTITY_BAT_DEATH) || event.getSound().equals(SoundEvents.ENTITY_BAT_HURT) || event.getSound().equals(SoundEvents.ENTITY_BAT_LOOP) || event.getSound().equals(SoundEvents.ENTITY_BAT_TAKEOFF)) {
            event.setVolume(0.0f);
            event.setPitch(0.0f);
            event.setCanceled(true);
        }
    }
    
    static {
        NoRender.INSTANCE = new NoRender();
        NoRender.INSTANCE = new NoRender();
    }
    
    public enum Skylight
    {
        NONE, 
        WORLD, 
        ENTITY, 
        ALL;
    }
    
    public enum Fog
    {
        NONE, 
        AIR, 
        NOFOG;
    }
    
    public enum Boss
    {
        NONE, 
        REMOVE, 
        STACK, 
        MINIMIZE;
    }
    
    public enum NoArmor
    {
        NONE, 
        ALL, 
        HELMET;
    }
    
    public static class Pair<T, S>
    {
        private T key;
        private S value;
        
        public Pair(final T key, final S value) {
            this.key = key;
            this.value = value;
        }
        
        public T getKey() {
            return this.key;
        }
        
        public void setKey(final T key) {
            this.key = key;
        }
        
        public S getValue() {
            return this.value;
        }
        
        public void setValue(final S value) {
            this.value = value;
        }
    }
}
