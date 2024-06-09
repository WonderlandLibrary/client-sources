package us.dev.direkt.module.internal.combat;

import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.network.play.server.SPacketEntityTeleport;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import us.dev.api.property.BoundedProperty;
import us.dev.api.property.Property;
import us.dev.api.property.multi.MultiProperty;
import us.dev.api.timing.Timer;
import us.dev.direkt.Direkt;
import us.dev.direkt.Wrapper;
import us.dev.direkt.event.internal.events.game.network.EventPostReceivePacket;
import us.dev.direkt.event.internal.events.game.network.EventSendPacket;
import us.dev.direkt.event.internal.events.game.player.update.EventPreMotionUpdate;
import us.dev.direkt.event.internal.events.game.server.EventServerDisconnect;
import us.dev.direkt.event.internal.filters.PacketFilter;
import us.dev.direkt.module.ModCategory;
import us.dev.direkt.module.ToggleableModule;
import us.dev.direkt.module.annotations.ModData;
import us.dev.direkt.module.internal.movement.Step;
import us.dev.direkt.module.property.annotations.Exposed;
import us.dev.direkt.util.client.EntityUtils;
import us.dev.direkt.util.client.MovementUtils;
import us.dev.dvent.Link;
import us.dev.dvent.Listener;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@ModData(label = "Kill Aura", aliases = {"aimbot", "aura", "forcefield", "ka"}, category = ModCategory.COMBAT)
public class KillAura extends ToggleableModule {

    @Exposed(description = "The types of entities that should be valid targets")
    private MultiProperty<Boolean> targetTypes = new MultiProperty.Builder<Boolean>("Targets")
            .put(new Property<>("Players", true))
            .put(new Property<>("Hostiles", true))
            .put(new Property<>("Passives", true))
            .put(new Property<>("Neutrals", false))
            .put(new Property<>("Bosses", false))
            .put(new Property<>("Vehicles", false))
            .put(new Property<>("Others", false))
            .build();

    @Exposed(description = "Target invisible entities")
    private Property<Boolean> invisibles = new Property<>("Invisibles", true);

    @Exposed(description = "Ignore players on your own team")
    private Property<Boolean> teams = new Property<>("Teams", false);

    @Exposed(description = "Aim at targets silently")
    private Property<Boolean> silent = new Property<>("Silent", true);

    @Exposed(description = "Delay hits if the target has been hurt recently")
    private Property<Boolean> hitDelay = new Property<>("Hit Delay", true);
    
    @Exposed(description = "Make the aura switch between entities.")
    private Property<Boolean> doSwitch = new Property<>("Switch", false);
    
    @Exposed(description = "Don't attack bots.")
    private Property<Boolean> AntiBot = new Property<>("Anti Bot", true);
    
    @Exposed(description = "The most cancerous way to bypass fight angle.")
    private Property<Boolean> angle = new Property<>("Angle", false);
    
    @Exposed(description = "Hit multiple entities.")
    private Property<Boolean> multi = new Property<>("Multi", false);
    
    @Exposed(description = "Range in which targets will be attacked")
    private Property<Double> reach = new BoundedProperty<>("Reach", 1.0, 3.7, 5.0);

    @Exposed(description = "Number of attacks per second that will be done")
    private Property<Integer> attackSpeed = new BoundedProperty<>("APS", 1, 7, 20);
    

    private Entity targetEntity;
    private Entity lastAttacked;
    private Timer timer = new Timer();
    private Timer packettimer = new Timer();
    private float virtualYaw,
    			  virtualPitch;
    private int switchTimes = 0;
    
    private HashMap<Integer, Long> EntityTeleportTimes = new HashMap<Integer, Long>();
    
    @Listener
    protected Link<EventSendPacket> onSendPacket = new Link<>(event -> {
        Entity closestEntity = null;
        float closestDistance = 5F;

        /*
         * Add the qualifying entities to an arraylist.
	     */
        final Set<Entity> entities = Wrapper.getLoadedEntities().stream()
                .filter(this::doesQualify)
                .collect(Collectors.toSet());

        /*
         * Remove last attacked entity from the array, given theres plenty of other people to attack
         */
        for (Iterator<Entity> it = entities.iterator(); it.hasNext(); ) {
            final Entity e = it.next();
            if(this.doSwitch.getValue()) { 
            if (e == lastAttacked ) {
                if (entities.size() > 2) {
                    it.remove();
                    continue;
                } else {
                    lastAttacked = null;
                }
            }
        }
            
            /*
    		 * Loop through that arraylist and determine the closest entity.
    		 */
            if (e.getDistanceToEntity(Wrapper.getPlayer()) < closestDistance) {
                closestDistance = e.getDistanceToEntity(Wrapper.getPlayer());
                closestEntity = e;
            }
        }
        if (this.doesQualify(closestEntity)) {
            /*
             * Cancerous code to essentially just look at the player your hitting
             */

        	float newYaw = this.getRotationsToward(closestEntity)[0];
        	float newPitch = this.getRotationsToward(closestEntity)[1];
            /*
             * If silent mode is turned on
             * check if your stepping, if not, check if your placing a block, if you are then cancel it, look where you need to, do it again, then look one more time
             * if not, look at the entities
             * however if silent mode isn't turned on
             * just look at the entitiy client side
             */
            if(silent.getValue()) {
            if (!Step.cancelSomePackets) {
            	if(event.getPacket() instanceof CPacketPlayerTryUseItem) {
            		event.setCancelled(true);
            		Wrapper.sendPacketDirect(new CPacketPlayer.PositionRotation(Wrapper.getPlayer().posX, Wrapper.getPlayer().posY, Wrapper.getPlayer().posZ, Wrapper.getPlayer().rotationYaw, Wrapper.getPlayer().rotationPitch, Wrapper.getPlayer().onGround));
            		Wrapper.sendPacketDirect(event.getPacket());
            		Wrapper.sendPacketDirect(new CPacketPlayer.PositionRotation(Wrapper.getPlayer().posX, Wrapper.getPlayer().posY, Wrapper.getPlayer().posZ, Wrapper.getPlayer().rotationYaw, Wrapper.getPlayer().rotationPitch, Wrapper.getPlayer().onGround));
            		} else {
            				event.setPacket(new CPacketPlayer.PositionRotation(Wrapper.getPlayer().posX, Wrapper.getPlayer().posY, Wrapper.getPlayer().posZ, newYaw, newPitch, Wrapper.getPlayer().onGround));
            		}
            	}
            } else {
            		Wrapper.getPlayer().rotationPitch = newPitch;
            		Wrapper.getPlayer().rotationYaw = newYaw;
            }
            
            
            /*
             * Normally you could hit the person right here, but 1.9 has a different packet flow
             * That causes it not to get called enough to hit them properly
             * To solve this we just hit them in a GameTick
             */
            targetEntity = closestEntity;

        } 
    }, Link.LOW_PRIORITY, new PacketFilter<>(CPacketPlayer.class, CPacketPlayerTryUseItem.class));

	public boolean isVisibleFOV(Entity e, Entity e2, Float fov) {
		return (((Math.abs(this.getRotationsToward(e)[0] - e2.rotationYaw) % 360F) > 180F ? (360F - (Math.abs(this.getRotationsToward(e)[0] - e2.rotationYaw) % 360F)) : (Math.abs(this.getRotationsToward(e)[0] - e2.rotationYaw) % 360F))) <= fov;
	}
    
    @Listener
    protected Link<EventPreMotionUpdate> onPreMotionUpdate = new Link<>(event -> {
    	if(packettimer.hasReach(56) && !MovementUtils.isMoving(Wrapper.getPlayer())) {
    		Wrapper.updatePosition(Wrapper.getPlayer().posX, Wrapper.getPlayer().posY, Wrapper.getPlayer().posZ);
    	}
        /*
    	 * Disable KillAura if the player is dead
    	 */
        if (Wrapper.getPlayer().getHealth() <= 0) {
            this.setRunning(false);
            Wrapper.addChatMessage("Killaura was disabled becuase you died!");
            return;
        }

         /*
         * if the item that the player is holding's cooldown is finished,
         * and the entity qualifys,
         * and it has been at least 180 milliseconds since the last attack
         * Hit them,
         * then swing
         * UNLESS the player is blocking, in that case do the following:
         * Unblock
         * Attack
         * Swing
         * re-block
         * then reset timers and set lastAttacked
         */

        
        if ((!this.hitDelay.getValue() || Wrapper.getPlayer().getCooledAttackStrength(0) == 1 || (targetEntity instanceof EntityLivingBase && canExecute((EntityLivingBase) targetEntity))) && this.doesQualify(targetEntity) && this.timer.hasReach((1000 / this.attackSpeed.getValue()) + (int) (Math.random() * 30)) && (!hitDelay.getValue() || targetEntity.hurtResistantTime < 20)) {
        	if (Wrapper.getPlayer().isActiveItemStackBlocking())
                Wrapper.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(0, 0, 0), EnumFacing.DOWN));
        	if(this.angle.getValue()) {
                Wrapper.getPlayerController().attackEntity(Wrapper.getPlayer(), targetEntity);
            	Wrapper.getPlayer().swingArm(EnumHand.MAIN_HAND);
        	}
            Wrapper.getPlayerController().attackEntity(Wrapper.getPlayer(), targetEntity);
        	Wrapper.getPlayer().swingArm(EnumHand.MAIN_HAND);
            if (Wrapper.getPlayer().isActiveItemStackBlocking())
                Wrapper.sendPacket(new CPacketPlayerTryUseItem(EnumHand.OFF_HAND));
            this.timer.reset();
            this.lastAttacked = this.targetEntity;
            
            /*
             * Spawn some particles
             */
            
            if(Direkt.getInstance().getModuleManager().getModule(Criticals.class).isRunning()) {
            	Wrapper.getMinecraft().effectRenderer.emitParticleAtEntity(targetEntity, EnumParticleTypes.CRIT_MAGIC);
            	Wrapper.getMinecraft().effectRenderer.emitParticleAtEntity(targetEntity, EnumParticleTypes.CRIT);
            }
            
            /*
             * If multi is turned on,
             *  and theres entities very close to the one you just hit,
             *   then hit those too.
             */
            if(this.multi.getValue()) {
            	Wrapper.getLoadedEntities().stream()
            	.filter(e -> e != lastAttacked && this.doesQualify(e) && e.getDistanceToEntity(lastAttacked) <= 1 )
            	.forEach(e -> {
                    if(Direkt.getInstance().getModuleManager().getModule(Criticals.class).isRunning()) {
                    	Wrapper.getMinecraft().effectRenderer.emitParticleAtEntity(targetEntity, EnumParticleTypes.CRIT_MAGIC);
                    	Wrapper.getMinecraft().effectRenderer.emitParticleAtEntity(targetEntity, EnumParticleTypes.CRIT);
                    }
            		if(this.angle.getValue()) {
                        Wrapper.getPlayerController().attackEntity(Wrapper.getPlayer(), e);
                    	Wrapper.getPlayer().swingArm(EnumHand.MAIN_HAND);	
            		}
                    Wrapper.getPlayerController().attackEntity(Wrapper.getPlayer(), e);
                	Wrapper.getPlayer().swingArm(EnumHand.MAIN_HAND);
            	});
            }
        }
    });

    @Override
    public void onEnable() {
    	virtualYaw = Wrapper.getPlayer().rotationYaw;
    }
    
    @Listener
    protected Link<EventServerDisconnect> onServerDisconnect = new Link<>(event -> {
        /*
    	 * If you leave with killaura turned on, fucking turn it off
    	 */
        this.setRunning(false);
    });

    @Listener
    protected Link<EventPostReceivePacket> onPreMotion = new Link<>(event -> {
    	if(event.getPacket() instanceof SPacketEntityTeleport) {
    		SPacketEntityTeleport packet = (SPacketEntityTeleport) event.getPacket();
    		EntityTeleportTimes.put(packet.getEntityId(), System.currentTimeMillis());
    	}
    });
    
    public boolean isBot(Entity entity)
	{		
		try {
			entity.getName();
		} catch (ClassCastException e) {
			return true;
		}

		if(entity.posY - Wrapper.getPlayer().posY > 1.5D) {
			return true;
		}

		if (entity == null 
				|| entity.ticksExisted < 200 
				|| entity.getName() == null) {
			return true;
		}

		return Wrapper.getSendQueue().getPlayerInfo(entity.getName()) == null;
	}
    
    private boolean doesQualify(Entity e) {
    	if (e != null && e != Wrapper.getPlayer() && (!AntiBot.getValue() || !isBot(e) || !(e instanceof EntityPlayer)) && Wrapper.getPlayer().getDistanceToEntity(e) <= this.reach.getValue() && (!this.invisibles.getValue() || !e.isInvisible()) && e.isEntityAlive() && (Wrapper.getSendQueue().getPlayerInfo(e.getUniqueID()) != null || !(e instanceof EntityPlayer))) {
            if (e instanceof EntityPlayer && (Direkt.getInstance().getFriendManager().isFriend((EntityPlayer) e) || (this.teams.getValue() && e.getTeam().isSameTeam(Wrapper.getPlayer().getTeam()))))
                return false;
            else if (targetTypes.getValue("Players").getValue() && e instanceof EntityPlayer)
                return true;
            else if (targetTypes.getValue("Hostiles").getValue() && EntityUtils.isHostileMob(e))
                return true;
            else if (targetTypes.getValue("Neutrals").getValue() && EntityUtils.isNeutralMob(e))
                return true;
            else if (targetTypes.getValue("Passives").getValue() && EntityUtils.isPassiveMob(e))
                return true;
            else if (targetTypes.getValue("Bosses").getValue() && EntityUtils.isBossMob(e))
                return true;
            else if (targetTypes.getValue("Vehicles").getValue() && EntityUtils.isVehicleEntity(e))
                return true;
            else if (targetTypes.getValue("Others").getValue() && EntityUtils.isMiscellaneousEntity(e))
                return true;
        }
        return false;
    }

    private static boolean canExecute(EntityLivingBase e) {
        return (getItemDamage(Wrapper.getPlayer().getHeldItemMainhand()) * Wrapper.getPlayer().getCooledAttackStrength(0)) * (1.0 - (e instanceof EntityPlayer ? getDamageReduced((EntityPlayer) e) : 0.0)) >= e.getHealth();
    }

    private static float getItemDamage(ItemStack itemStack) {
        if (itemStack == null) return 1.0F;

        final Multimap<String, AttributeModifier> multimap = itemStack.getAttributeModifiers(EntityEquipmentSlot.MAINHAND);
        if (!multimap.isEmpty()) {
            for (AttributeModifier attributeModifier : multimap.get(SharedMonsterAttributes.ATTACK_DAMAGE.getAttributeUnlocalizedName())) {
                final double damage = attributeModifier.getAmount();
                return damage > 1.0D ? 1.0F + (float) damage : 1.0F;
            }
        }
        return 1.0F;
    }

	public float[] getRotationsToward(Entity closestEntity, float yawIn, float pitchIn) {
		double xDist = closestEntity.posX - Wrapper.getPlayer().posX;
		double yDist = closestEntity.posY + (double)closestEntity.getEyeHeight() - (Wrapper.getPlayer().posY + Wrapper.getPlayer().getEyeHeight());
		double zDist = closestEntity.posZ - Wrapper.getPlayer().posZ;
		double fDist = (double)MathHelper.sqrt_double(xDist * xDist + zDist * zDist);
		float yaw = this.fixRotation(yawIn, (float)(Math.atan2(zDist, xDist) * 180.0D / Math.PI) - 90.0F, 360F);
		float pitch = this.fixRotation(pitchIn, (float)(-(Math.atan2(yDist, fDist) * 180.0D / Math.PI)), 360F);
        return new float[]{yaw, pitch};
	}

	public float[] getRotationsToward(Entity closestEntity) {
		double xDist = closestEntity.posX - Wrapper.getPlayer().posX;
		double yDist = closestEntity.posY + (double)closestEntity.getEyeHeight() - (Wrapper.getPlayer().posY + Wrapper.getPlayer().getEyeHeight());
		double zDist = closestEntity.posZ - Wrapper.getPlayer().posZ;
		double fDist = (double)MathHelper.sqrt_double(xDist * xDist + zDist * zDist);
		float yaw = this.fixRotation(Wrapper.getPlayer().rotationYaw, (float)(Math.atan2(zDist, xDist) * 180.0D / Math.PI) - 90.0F, 360F);
		float pitch = this.fixRotation(Wrapper.getPlayer().rotationPitch, (float)(-(Math.atan2(yDist, fDist) * 180.0D / Math.PI)), 360F);
        return new float[]{yaw, pitch};
	}

	private float fixRotation(float p_70663_1_, float p_70663_2_, float p_70663_3_)
	{
		float var4 = MathHelper.wrapDegrees(p_70663_2_ - p_70663_1_);

		if (var4 > p_70663_3_)
		{
			var4 = p_70663_3_;
		}

		if (var4 < -p_70663_3_)
		{
			var4 = -p_70663_3_;
		}

		return p_70663_1_ + var4;
	}
    
    private static double getDamageReduced(EntityPlayer player) {
        final EnumMap<EntityEquipmentSlot, ItemStack> armorItems = Maps.newEnumMap(EntityEquipmentSlot.class);
        armorItems.put(EntityEquipmentSlot.HEAD, player.getItemStackFromSlot(EntityEquipmentSlot.HEAD));
        armorItems.put(EntityEquipmentSlot.CHEST, player.getItemStackFromSlot(EntityEquipmentSlot.CHEST));
        armorItems.put(EntityEquipmentSlot.LEGS, player.getItemStackFromSlot(EntityEquipmentSlot.LEGS));
        armorItems.put(EntityEquipmentSlot.FEET, player.getItemStackFromSlot(EntityEquipmentSlot.FEET));

        double reduction = 0.0;
        for (Map.Entry<EntityEquipmentSlot, ItemStack> item : armorItems.entrySet()) {
            if (item.getValue() != null) {
                final Multimap<String, AttributeModifier> multimap = item.getValue().getAttributeModifiers(item.getKey());
                if (!multimap.isEmpty()) {
                    for (AttributeModifier attributeModifier : multimap.get(SharedMonsterAttributes.ARMOR.getAttributeUnlocalizedName())) {
                        reduction += attributeModifier.getAmount() + EnchantmentHelper.getEnchantmentModifierDamage(armorItems.values(), DamageSource.causePlayerDamage(player));
                    }
                }
            }
        }
        return (reduction * 4) / 100.0;
    }
}