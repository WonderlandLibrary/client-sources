package in.momin5.cookieclient.client.modules.combat;

import in.momin5.cookieclient.CookieClient;
import in.momin5.cookieclient.api.event.events.EventRender;
import in.momin5.cookieclient.api.event.events.OnUpdateWalkingPlayerEvent;
import in.momin5.cookieclient.api.event.events.PacketEvent;
import in.momin5.cookieclient.api.module.Category;
import in.momin5.cookieclient.api.module.Module;
import in.momin5.cookieclient.api.setting.settings.SettingBoolean;
import in.momin5.cookieclient.api.setting.settings.SettingColor;
import in.momin5.cookieclient.api.setting.settings.SettingMode;
import in.momin5.cookieclient.api.setting.settings.SettingNumber;
import in.momin5.cookieclient.api.util.utils.misc.TimerUtil;
import in.momin5.cookieclient.api.util.utils.player.CATestUtils;
import in.momin5.cookieclient.api.util.utils.player.CrystalUtils;
import in.momin5.cookieclient.api.util.utils.render.CustomColor;
import in.momin5.cookieclient.api.util.utils.render.DRenderUtils;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.MinecraftForge;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class CATest extends Module {
    public CATest() {
        super("AutoCrystal", Category.COMBAT);
    }

    public SettingMode breakType = register(new SettingMode("Break",this,"Packet","Swing","Packet"));
    public SettingMode breakHand = register(new SettingMode("Hand",this,"Main","Offhand","Main"));
    public SettingMode breakMode = register(new SettingMode("BreakMode",this,"Smart","Smart","All"));
    public SettingNumber breakRange = register(new SettingNumber("BreakRange",this,4.8,1.0,6.0,0.1));
    public SettingNumber placeRange = register(new SettingNumber("PlaceRange",this,4.8,0.1,6.0,0.1));
    public SettingNumber breakDelay = register(new SettingNumber("BreakDelay",this,1,0,10,1));
    public SettingNumber placeDelay = register(new SettingNumber("PlaceDelay",this,1,0,10,1));
    public SettingNumber minDamage = register(new SettingNumber("MinDamage",this,5.0,0.1,20,0.1));
    public SettingNumber maxSelfDamage = register(new SettingNumber("MaxSelfDamage",this,8,1,10,1));
    public SettingNumber facePlaceHp = register(new SettingNumber("FaceplaceHP",this,10,1,36,1));
    public SettingNumber enemyRange = register(new SettingNumber("EnemyRange",this,10,1,12,1));
    public SettingNumber wallRange = register(new SettingNumber("WallRange",this,3.5,0.5,6.0,0.1));
    public SettingBoolean antiSuicide = register(new SettingBoolean("Anti-Suicide",this,true));
    public SettingBoolean ghostMode = register(new SettingBoolean("Insta break",this,false));
    public SettingBoolean soundSync = register(new SettingBoolean("SoundSync",this,true));
    public SettingBoolean rotate = register(new SettingBoolean("Rotate",this,false));
    public SettingBoolean raytrace = register(new SettingBoolean("Raytrace",this,false));
    public SettingBoolean oneDot16 = register(new SettingBoolean("1.16",this,false));
    public SettingBoolean autoSwitch = register(new SettingBoolean("AutoSwitch",this,false));
    public SettingBoolean weakness = register(new SettingBoolean("AntiWeakness",this,false));
    public SettingBoolean blowArmor = register(new SettingBoolean("BreakArmor",this,true));
    public SettingNumber armorPercent = register(new SettingNumber("Armor%",this,20,1,100,1));
    public SettingBoolean damageRender = register(new SettingBoolean("DamageRender",this,true));
    public SettingMode logic = register(new SettingMode("Logic",this,"PlaceBreak","PlaceBreak","BreakPlace"));
    public SettingColor caRender = register(new SettingColor("Color",this, new CustomColor(117,0,188, 100)));
    public SettingBoolean dev = register(new SettingBoolean("DevSetting",this,false));

    private final ConcurrentHashMap<EntityEnderCrystal, Integer> attacked_crystals = new ConcurrentHashMap<>();
    private final List<BlockPos> placePosList = new CopyOnWriteArrayList<>();
    private final TimerUtil remove_visual_timer = new TimerUtil();
    private boolean rotating = false;
    //private BlockPos crystalPosRotate;
    public EntityPlayer autoez_target = null;
    private double place_timeout;
    private double break_timeout;
    private int break_delay_counter;
    private int place_delay_counter;
    private boolean already_attacking = false;
    private BlockPos renderPos = null;


    @Override
    public void onEnable(){
        MinecraftForge.EVENT_BUS.register(this);
        CookieClient.EVENT_BUS.subscribe(this);

        rotating = rotate.isEnabled();
        remove_visual_timer.reset();

        place_timeout = placeDelay.getValue();
        break_timeout = breakDelay.getValue();

    }

    public void onDisable(){
        MinecraftForge.EVENT_BUS.unregister(this);
        CookieClient.EVENT_BUS.unsubscribe(this);

        attacked_crystals.clear();
        renderPos = null;
    }

    @Override
    public void onUpdate(){
        doCristalAura();
    }

    public void doCristalAura(){
        if(nullCheck())
            return;

        if(remove_visual_timer.hasDelayRun(1000)){
            remove_visual_timer.reset();
            attacked_crystals.clear();
        }

        if(logic.is("BreakPlace")){
            if(break_delay_counter > break_timeout) {
                breakAC();
            }

            if(place_delay_counter > place_timeout) {
                placeAC();
            }
        }else {
            if (place_delay_counter > place_timeout) {
                placeAC();
            }

            if (break_delay_counter > break_timeout) {
                breakAC();
            }
        }

        break_delay_counter++;
        place_delay_counter++;

    }

    public void breakAC(){
        EntityEnderCrystal crystal = get_best_crystal();
        if (crystal == null) {
            return;
        }

        if (weakness.isEnabled() && mc.player.isPotionActive(MobEffects.WEAKNESS)) {

            boolean should_weakness = true;

            if (mc.player.isPotionActive(MobEffects.STRENGTH))
            {
                if (Objects.requireNonNull(mc.player.getActivePotionEffect(MobEffects.STRENGTH)).getAmplifier() == 2) {
                    should_weakness = false;
                }
            }

            if (should_weakness) {

                if (!already_attacking) {
                    already_attacking = true;
                }

                int new_slot = -1;

                for (int j = 0; j < 9; j++) {

                    ItemStack stack = mc.player.inventory.getStackInSlot(j);

                    if (stack.getItem() instanceof net.minecraft.item.ItemSword || stack.getItem() instanceof net.minecraft.item.ItemTool) {
                        new_slot = j;
                        mc.playerController.updateController();

                        break;
                    }
                }

                if (new_slot != -1) {
                    mc.player.inventory.currentItem = new_slot;
                }
            }
        }


        //crystalPosRotate = crystal.getPosition();
        rotating = rotate.isEnabled();


        for (int i = 0; i < 1; i++) {
            swingArm();
            if(breakType.is("Swing")){
                mc.playerController.attackEntity(mc.player, crystal);
            }
            else {
                mc.player.connection.sendPacket(new CPacketUseEntity(crystal));
            }
        }

        add_attacked_crystal(crystal);

        if (ghostMode.isEnabled()) {
            mc.world.removeEntityFromWorld(crystal.getEntityId());
            crystal.setDead();
        }

        break_delay_counter = 0;

    }

    public void placeAC(){
        BlockPos target_block = get_best_block();

        if (target_block == null) {
            return;
        }

        place_delay_counter = 0;
        already_attacking = false;

        boolean offhand_check = false;
        if (mc.player.getHeldItemOffhand().getItem() != Items.END_CRYSTAL) {
            if (mc.player.getHeldItemMainhand().getItem() != Items.END_CRYSTAL && autoSwitch.isEnabled()) {
                if (find_crystals_hotbar() == -1) return;
                mc.player.inventory.currentItem = find_crystals_hotbar();
                return;
            }
        } else {
            offhand_check = true;
        }

        //crystalPosRotate = target_block;
        rotating = rotate.isEnabled();

        if(mc.player.getHeldItemMainhand().getItem() == Items.END_CRYSTAL || mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL) {
            CrystalUtils.placeCrystalOnBlock(target_block, offhand_check ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND);
        }
        placePosList.add(target_block);

    }

    @EventHandler
    private final Listener<OnUpdateWalkingPlayerEvent> onUpdateWalkingPlayerEventListener = new Listener<>(event -> {
        if (!rotating)
            return;

        //i will add it later
        //RotationUtils.faceBlockPacket(crystalPosRotate);

        rotating = false;
    });

    @EventHandler
    private final Listener<PacketEvent.Receive> soundSyncListener = new Listener<>(event -> {
        if(soundSync.isEnabled()) {
            if (event.getPacket() instanceof SPacketSoundEffect) {
                SPacketSoundEffect packet = (SPacketSoundEffect) event.getPacket();
                if (packet.getCategory() == SoundCategory.BLOCKS && packet.getSound() == SoundEvents.ENTITY_GENERIC_EXPLODE)
                    for (Entity e : mc.world.loadedEntityList) {
                        if (e instanceof EntityEnderCrystal && e.getDistance(packet.getX(), packet.getY(), packet.getZ()) <= 6.0D)
                            mc.world.removeEntityFromWorld(e.getEntityId());
                        if(dev.isEnabled()) {
                            e.setDead();
                        }
                    }
            }

        }
    });

    public void swingArm(){
        if(breakHand.is("Offhand") && mc.player.getHeldItemOffhand() != null){
            mc.player.swingArm(EnumHand.OFF_HAND);
        } else {
            mc.player.swingArm(EnumHand.MAIN_HAND);
        }
    }

    public BlockPos get_best_block() {
        double best_damage = 0f;
        renderPos = null;

        double maximum_damage_self = maxSelfDamage.getValue();

        BlockPos best_block = null;

        List<BlockPos> blocks = CrystalUtils.possiblePlacePositions((float) placeRange.getValue(), oneDot16.isEnabled(), true);

        for (EntityPlayer target : mc.world.playerEntities) {

            for (BlockPos block : blocks) {
                double minimum_damage;
                if (target == mc.player)
                    continue;
                //CookieClient.LOGGER.info("Player not null");
                if (target.getDistance(mc.player) >= enemyRange.getValue())
                    continue;
                //CookieClient.LOGGER.info("player in range");
                //CookieClient.LOGGER.info(target.getDistanceSq(mc.player));
                if (!CrystalUtils.rayTracePlaceCheck(block, raytrace.isEnabled())) {
                    continue;
                }
                if (mc.player.getDistance(block.getX(), block.getY(), block.getZ()) > placeRange.getValue())
                    continue;
                //CookieClient.LOGGER.info("In placement value");
                if (target.isDead || target.getHealth() <= 0.0F)
                    continue;
                //CookieClient.LOGGER.info("Target not dead");
                boolean no_place = ( mc.player.getHeldItemMainhand().getItem() == Items.DIAMOND_SWORD);
                if ((target.getHealth() < facePlaceHp.getValue() && !no_place) || (get_armor_fucker(target) && !no_place)) {
                    minimum_damage = 2.0D;
                } else {
                    minimum_damage = minDamage.getValue();
                }
                double target_damage = CATestUtils.calculateDamageThreaded(block.getX() + 0.5d,block.getY() + 1.0d, block.getZ() + 0.5d,target);
                if (target_damage < minimum_damage)
                    continue;
                //CookieClient.LOGGER.info("target can be damaged");
                //CookieClient.LOGGER.info(target_damage);
                double self_damage = CATestUtils.calculateDamageThreaded(block.getX() + 0.5d,block.getY() + 1.0d, block.getZ() + 0.5d,mc.player);
                if (self_damage > maximum_damage_self || (antiSuicide.isEnabled() && (mc.player.getHealth() + mc.player.getAbsorptionAmount()) - self_damage <= 0.5D))
                    continue;
                //CookieClient.LOGGER.info("passed the anti suicide check");
                /*if (target_damage > best_damage) {
                    best_damage = target_damage;
                    best_block = block;
                    autoez_target = target;
                }*/

                if(target_damage == best_damage) {
                    if(best_block == null || block.distanceSq(mc.player.getPositionVector().x,mc.player.getPositionVector().y,mc.player.getPositionVector().z) > best_block.distanceSq(mc.player.getPositionVector().x,mc.player.getPositionVector().y,mc.player.getPositionVector().z)) {
                        best_damage = target_damage;
                        best_block = block;
                        renderPos = best_block;
                    }
                }else if(target_damage > best_damage) {
                    best_damage = target_damage;
                    best_block = block;
                    autoez_target = target;
                    renderPos = best_block;
                }
            }
        }

        blocks.clear();

        //CookieClient.LOGGER.info(best_block);
        return best_block;
    }

    public boolean get_armor_fucker(EntityPlayer p) {
        for (ItemStack stack : p.getArmorInventoryList()) {

            if (stack == null || stack.getItem() == Items.AIR) return true;

            float armor_percent = ((float) (stack.getMaxDamage() - stack.getItemDamage()) / (float) stack.getMaxDamage()) * 100.0f;

            if (blowArmor.isEnabled() && armorPercent.getValue() >= armor_percent) return true;

        }

        return false;
    }

    private int find_crystals_hotbar() {
        for (int i = 0; i < 9; i++) {
            if (mc.player.inventory.getStackInSlot(i).getItem() == Items.END_CRYSTAL) {
                return i;
            }
        }
        return -1;
    }

    public EntityEnderCrystal get_best_crystal() {
        double best_damage = 0.0D;

        double maximum_damage_self = maxSelfDamage.getValue();

        double best_distance = 0.0D;

        EntityEnderCrystal best_crystal = null;

        for (Entity c : mc.world.loadedEntityList) {

            if (!(c instanceof EntityEnderCrystal))
                continue;
            EntityEnderCrystal crystal = (EntityEnderCrystal)c;
            if (mc.player.getDistance(crystal) > (!mc.player.canEntityBeSeen(crystal) ? wallRange.getValue() : breakRange.getValue())) {
                continue;
            }
            if (!mc.player.canEntityBeSeen(crystal) && raytrace.isEnabled()) {
                continue;
            }

            if (attacked_crystals.containsKey(crystal) && attacked_crystals.get(crystal) > 5)
                continue;
            for (EntityPlayer player : mc.world.playerEntities) {
                double minimum_damage;
                if (player == mc.player)
                    continue;

                if (player.getDistance(mc.player) >= enemyRange.getValue())
                    continue;
                if (player.isDead || player.getHealth() <= 0.0F)
                    continue;
                boolean no_place = (mc.player.getHeldItemMainhand().getItem() == Items.DIAMOND_SWORD);
                if ((player.getHealth() < facePlaceHp.getValue() && !no_place) || (get_armor_fucker(player) && !no_place)) {
                    minimum_damage = 2.0D;
                } else {
                    minimum_damage = minDamage.getValue();
                }

                double target_damage = CrystalUtils.calculateDamage(crystal, player);

                if (target_damage < minimum_damage)
                    continue;
                double self_damage = CrystalUtils.calculateDamage(crystal, mc.player);

                if (self_damage > maximum_damage_self || (antiSuicide.isEnabled() && (mc.player.getHealth() + mc.player.getAbsorptionAmount()) - self_damage <= 0.5D))
                    continue;
                if (target_damage > best_damage && breakMode.is("Smart")) {
                    best_damage = target_damage;
                    best_crystal = crystal;
                }
            }

            if (breakMode.is("All") && mc.player.getDistanceSq(crystal) > best_distance) {
                best_distance = mc.player.getDistanceSq(crystal);
                best_crystal = crystal;
            }
        }

        return best_crystal;
    }

    private void add_attacked_crystal(EntityEnderCrystal crystal) {
        if (attacked_crystals.containsKey(crystal)) {
            int value = attacked_crystals.get(crystal);
            attacked_crystals.put(crystal, value + 1);
        } else {
            attacked_crystals.put(crystal, 1);
        }

    }


    public void onWorldRender(EventRender event){
        if(this.renderPos != null && find_crystals_hotbar() != -1 || this.renderPos != null && mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL){
            DRenderUtils.drawBox(this.renderPos,1,new CustomColor(caRender.getValue()),255);
            DRenderUtils.drawBoundingBox(this.renderPos,1,1.00f,new CustomColor(caRender.getValue(),255));
        }

        if(damageRender.isEnabled() && find_crystals_hotbar() != -1 || damageRender.isEnabled() && mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL) {
            if (this.renderPos != null && this.autoez_target != null ) {
                double d = CrystalUtils.calculateDamage(renderPos.getX() + .5, renderPos.getY() + 1, renderPos.getZ() + .5, autoez_target);
                String[] damageText = new String[1];
                damageText[0] = (Math.floor(d) == d ? (int) d : String.format("%.1f", d)) + "";
                DRenderUtils.drawNametag(renderPos.getX() + 0.5, renderPos.getY() + 0.5, renderPos.getZ() + 0.5, damageText, new CustomColor(255, 255, 255), 1);
            }
        }
    }
}
