package me.xatzdevelopments.xatz.client.modules;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import io.netty.util.internal.ThreadLocalRandom;
import me.xatzdevelopments.xatz.client.events.UpdateEvent;
import me.xatzdevelopments.xatz.client.main.Xatz;
import me.xatzdevelopments.xatz.client.module.state.Category;
import me.xatzdevelopments.xatz.client.settings.ClientSettings;
import me.xatzdevelopments.xatz.client.tools.Helper;
import me.xatzdevelopments.xatz.client.tools.TimeTracker;
import me.xatzdevelopments.xatz.client.tools.Utils;
import me.xatzdevelopments.xatz.gui.UnicodeFontRenderer;
import me.xatzdevelopments.xatz.gui.custom.clickgui.CheckBtnSetting;
import me.xatzdevelopments.xatz.gui.custom.clickgui.ModSetting;
import me.xatzdevelopments.xatz.gui.custom.clickgui.SliderSetting;
import me.xatzdevelopments.xatz.gui.custom.clickgui.ValueFormat;
import me.xatzdevelopments.xatz.module.Module;
import net.java.games.input.Mouse;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.src.MathUtils;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;


public class Killaura69420 extends Module {
	  private double[] offsets = new double[]{0.0625, 0.0, 1.1E-5, 0.0};
	    private Entity target;
	    private List<Entity> toAttack = new ArrayList<Entity>(0);
	    private int index;
	    private boolean swapping;
	    private TimeTracker timer = new TimeTracker();
	    private TimeTracker swapTimer = new TimeTracker();
	    int lol = 1;
	    private Comparator<Entity> angleComparator = Comparator.comparingDouble(e2 -> this.getAngles(e2)[0]);
	    UnicodeFontRenderer ufr;
	    UnicodeFontRenderer ufr2;
	    private float lastHealth = 0.0F;
		EntityLivingBase idk = null;


	    

	@Override
	public ModSetting[] getModSettings() {
/*		EnumAttribute("Hit Location", "location", (Enum[])HitLocation.values());
	    private EnumAttribute<ReachCheck> reachCheck = new EnumAttribute("Reach Check", "reachCheck", (Enum[])ReachCheck.values());
	    private EnumAttribute<Mode> mode = new EnumAttribute("Mode", "mode", (Enum[])Mode.values());
      */
		
		
		
		

		CheckBtnSetting players = new CheckBtnSetting("Players", "players");
		CheckBtnSetting animals = new CheckBtnSetting("Animals", "animals");
		CheckBtnSetting AAC = new CheckBtnSetting("AAC", "AACBETA");
		CheckBtnSetting mobs = new CheckBtnSetting("Mobs", "mobs");
		CheckBtnSetting invisibles = new CheckBtnSetting("Invisibles", "invisibles");
	     CheckBtnSetting block = new CheckBtnSetting("AutoBlock", "block");
	     CheckBtnSetting dura = new CheckBtnSetting("Dura", "dura");
	     CheckBtnSetting teams = new CheckBtnSetting("Teams", "teams");
	     CheckBtnSetting autoDisable = new CheckBtnSetting("Auto Disable", "autoDisable");
	     CheckBtnSetting GWEN = new CheckBtnSetting("Anti GWEN", "GWEN");
	    
	     SliderSetting<Number> aps = new SliderSetting<Number>("APS", ClientSettings.apsBeta, 1.0, 20.0, 0.1, ValueFormat.DECIMAL);
	     SliderSetting<Number> range = new SliderSetting<Number>("Range", ClientSettings.rangeBeta, 2, 6.0, 0.25, ValueFormat.DECIMAL);
	     SliderSetting<Number> sps = new SliderSetting<Number>("Swap Interval", ClientSettings.sps, 0.1, 20.0, 0.1, ValueFormat.DECIMAL);
	     SliderSetting<Number> random = new SliderSetting<Number>("Random", ClientSettings.rnd, 0.1, 3.0, 1, ValueFormat.DECIMAL);
		
		
		
		
		return new ModSetting[] { players,animals,AAC, mobs,invisibles,block,dura,teams,autoDisable,GWEN, aps,range,sps,random};
	}

	public Killaura69420() {
		super("BetaKillAura", Keyboard.KEY_NONE, Category.COMBAT2, "Automatically attacks entities in range.");
		

	
	}
	@Override
    public void onEnable() {

        this.target = null;
        this.swapping = false;
        this.index = 0;

        super.onEnable();
    }

    @Override
    public void onDisable() {
        
        super.onDisable();
    }

    private static final Random generator = new Random();
    public static double random(double min, double max) {
        return min + (generator.nextDouble() * (max - min));
     }

    @Override
    public void onUpdate(UpdateEvent e) {
if (mc.thePlayer.isDead) {
	Xatz.chatMessage("The Killaura has automatically been Disabled!");
	super.onDisable();
}

if (!(mc.currentScreen instanceof GuiChest)) {
                this.toAttack = this.getNear(ClientSettings.rangeBeta);
                this.toAttack.sort(this.angleComparator);
                if (!this.toAttack.isEmpty() && ClientSettings.block && mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemSword && !mc.thePlayer.isBlocking()) {
                    mc.thePlayer.setItemInUse(mc.thePlayer.getHeldItem(), 1);
                   
                }
                if (this.target != null && this.target instanceof EntityPlayer && !AntiBot.isReal((EntityPlayer)this.target) && Xatz.getModuleByName("AntiBot").isToggled()) {
                    this.target = null;
                }
               
                    if(currentMode.equals("Tick")) {
                        for (Entity ent : mc.theWorld.loadedEntityList) {
                            if (ent.ticksExisted <= 0) continue;
                            --ent.ticksExisted;
                        }
                    }
                    if(currentMode.equals("Single")) {
                    	
                    	 if (this.target != null && (this.target == null || this.getDistanceSq(this.target) <= MathUtils.square((Double)ClientSettings.rangeBeta) && this.qualifies(this.target))) {
                    		 
                    	 }
                    	 else {
                         this.target = this.toAttack.isEmpty() ? null : this.toAttack.get(0);
                    	 }
                     }

                    if(currentMode.equals("Switch")) {
                    
                        this.target = null;
                        this.swapping = false;
                        
                        if (this.swapTimer.hasPassed(1000.0 / ClientSettings.apsBeta) && this.toAttack.size() > 1) {
                            this.swapTimer.updateLastTime();
                            this.swapping = true;
                            
                            ++this.index;
                          
                        }
                        
                    /*    if(Xatz.getModuleByName("SuperHeroFX").isToggled()) {
                        	if(this.target != null && idk.hurtTime > 0) {
                        		Xatz.fxManager.addTextFx(idk, "test");
                        	}
                        	if(this.target == null) {
                        		Xatz.fxManager.resetFxList();
                        	}
                        } */
                    }
    
 if (!this.toAttack.isEmpty()) {
                        	
                        
                        if (this.index >= this.toAttack.size()) {
                            this.index = 0;
                        }
                       
                        this.target = this.toAttack.get(this.index);
                    }
 else {
                    }
                    
                if (this.target != null) {
                	
                float[] angles = this.getAngles(this.target);
                target.hurtResistantTime = 0;
                if(!ClientSettings.AACBETA) {
                	target.ticksExisted = 0;
                	target.ticksExisted = 0;
                	target.ticksExisted = 0;
                	target.ticksExisted = 0;
                	e.yaw = angles[0];
                	target.ticksExisted = 0;
                	target.ticksExisted = 0;
                	target.ticksExisted = 0;
                	target.ticksExisted = 0;
                    e.pitch = angles[1]; 
                    target.ticksExisted = 0;
                    target.ticksExisted = 0;
                    target.ticksExisted = 0;
                    target.ticksExisted = 0;
                }
                else {
                	e.yaw = angles[0] + (float)this.getRandomIntInRange(-3, 3);

                    e.pitch = Math.max(Math.min(angles[1] + (float)this.getRandomIntInRange(-5, 22), 90.0f), -90.0f);
                }
                }
                else {
                	
                }
            

                boolean blocking = mc.thePlayer.isBlocking();

                if (this.target != null) {
                boolean crits = Xatz.getModuleByName("Criticals2").isToggled();
                
                
                if (currentMode.equals("Tick")) {
                        if (timer.hasPassed(499)) {
                      
                        this.timer.updateLastTime();
                        if (ClientSettings.dura) {
                            mc.thePlayer.moveToHotbar(9, mc.thePlayer.inventory.currentItem);
                            Helper.sendPacket(new C03PacketPlayer(true), true);
                            boolean reblock = false;
                            if (mc.thePlayer.getHeldItem() != null && (reblock = blocking && mc.thePlayer.getHeldItem().getItem() instanceof ItemSword)) {
                                mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem());
                            }
                            
                            this.attack(false, reblock);
                            //Xatz.chatMessage("1");
                            
                            this.attack(crits, reblock);
                            //Xatz.chatMessage("3");
                            mc.thePlayer.moveToHotbar(9, mc.thePlayer.inventory.currentItem);
                            Helper.sendPacket(new C03PacketPlayer(true), true);
                            if (blocking) {
                                mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem());
                            }
                            
                            this.attack(crits, blocking);
                           // Xatz.chatMessage("5");
                        }
                        this.attack(false, blocking);
                        //Xatz.chatMessage("6");
                        this.attack(false, blocking);
                       // Xatz.chatMessage("7");
                       
                    }
                }
                    if (currentMode.equals("Single")) {
                    	
                    	 
                    	 if (this.timer.hasPassed(1000.0 / (ClientSettings.apsBeta + random(-ClientSettings.rnd,ClientSettings.rnd)))) {
                         	
                         	
                             this.attack(crits, blocking);
                             
                             this.timer.updateLastTime();
                         }
                         this.swapping = false;
                    }
                    
                    if (currentMode.equals("Switch")) {
                    	
                    
                    	if (this.timer.hasPassed(1000.0 / (ClientSettings.apsBeta + random(-ClientSettings.rnd,ClientSettings.rnd)))) {
                    		
                            this.attack(crits, blocking);
                            
                            this.timer.updateLastTime();
                        }
                        this.swapping = false;
                    }
                
                
                }
                else {
                	
                }
    }


    }
    
    
    
    
    
    @Override
    public void onTick() {
    	
    }
    @Override
	public String[] getModes() {

		return new String[] { "Switch", "Single" , "Tick"};
	}
    
	public String getModeName() {
		return "Mode: ";
	}
    
    public static boolean IsntMineplexBot(Entity e){
    	if (e instanceof EntityPlayer) {
    	
    	
			if (!((EntityPlayer) e).didSwingItem) {
				if (e.onGround) {
					if (e.isSprinting()) {
						return true;
					}
				} else {
					if (e.hurtResistantTime == 0) {
						return false;
					}
				}
			}
			return true;
		
    }
    	else {
    		return true;
    	}
    }


    private List<Entity> getNear(double range) {
        return mc.theWorld.loadedEntityList.stream().filter(e2 -> this.getDistanceSq(e2) <= MathUtils.square(range) && this.qualifies(e2)).collect(Collectors.toList());
    }

    private boolean qualifies(Entity e2) {
    	if (e2 instanceof EntityVillager) {
    		return false;
    	}
        if (!(e2 instanceof EntityLivingBase)  || e2.isInvisible() && !ClientSettings.invisibles || e2.isDead || e2 == mc.thePlayer  || (e2 instanceof EntityPlayer && Xatz.friendsMananger.isFriend((EntityPlayer)e2)) || (!IsntMineplexBot(e2) && ClientSettings.GWEN) || (e2 instanceof EntityPlayer && Xatz.friendsMananger.isFriend((EntityPlayer)e2)) || (ClientSettings.teams && Utils.isOnTeam((EntityLivingBase)e2))) {
            return false;
        }
        if (e2 instanceof EntityPlayer && ClientSettings.players) {
            return true;
        }
        return ((e2 instanceof EntityMob || e2 instanceof EntitySlime || e2 instanceof EntityGhast) && ClientSettings.mobs) || ((e2 instanceof EntityAnimal || e2 instanceof EntitySquid || e2 instanceof EntityBat) && ClientSettings.animals != false);
    }

    private void attack(boolean crit, boolean unblockAndReblock) {
        if (unblockAndReblock) {
            Helper.sendPacket(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN), true);
        }
        for (int i = 0; i < 1; i++) {
			mc.effectRenderer.emitParticleAtEntity(target, EnumParticleTypes.CRIT);
			mc.effectRenderer.emitParticleAtEntity(target, EnumParticleTypes.CRIT_MAGIC);
		}

        mc.thePlayer.swingItem();
        if (mc.thePlayer.onGround) {
            if (Xatz.getModuleByName("Speed").isToggled() && mc.thePlayer.isMoving()) {
                crit = false;
            }
            if (crit) {
                Criticals.crit();
            }
        }
        Helper.sendPacket(new C02PacketUseEntity(this.target, C02PacketUseEntity.Action.ATTACK), true);
        if (unblockAndReblock) {
            mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem());
        }
    }

    private float[] getAngles(Entity target) {
        double xDiff = target.posX - mc.thePlayer.posX;
        double yDiff = target.posY - mc.thePlayer.posY;
        double zDiff = target.posZ - mc.thePlayer.posZ;
        float yaw = (float)(Math.atan2(zDiff, xDiff) * 180.0 / 3.141592653589793) - 90.0f;
        float pitch = (float)((- Math.atan2(target.posY + (double)target.getEyeHeight() / 0.0 - (mc.thePlayer.posY + (double)mc.thePlayer.getEyeHeight()), Math.hypot(xDiff, zDiff))) * 180.0 / 3.141592653589793);
       
            if (yDiff > -0.2 && yDiff < 0.2) {
                pitch = (float)((- Math.atan2(target.posY + (double)target.getEyeHeight() / HitLocation.CHEST.getOffset() - (mc.thePlayer.posY + (double)mc.thePlayer.getEyeHeight()), Math.hypot(xDiff, zDiff))) * 180.0 / 3.141592653589793);
            } else if (yDiff > -0.2) {
                pitch = (float)((- Math.atan2(target.posY + (double)target.getEyeHeight() / HitLocation.FEET.getOffset() - (mc.thePlayer.posY + (double)mc.thePlayer.getEyeHeight()), Math.hypot(xDiff, zDiff))) * 180.0 / 3.141592653589793);
            } else if (yDiff < 0.3) {
                pitch = (float)((- Math.atan2(target.posY + (double)target.getEyeHeight() / HitLocation.HEAD.getOffset() - (mc.thePlayer.posY + (double)mc.thePlayer.getEyeHeight()), Math.hypot(xDiff, zDiff))) * 180.0 / 3.141592653589793);
            }
        
        return new float[]{yaw, pitch};
    }

    private double getDistanceSq(Entity target) {
       
                double xDiff = target.posX - mc.thePlayer.posX;
                double yDiff = target.posY - mc.thePlayer.posY;
                double zDiff = target.posZ - mc.thePlayer.posZ;
                if (yDiff > -0.2) {
                    yDiff = target.posY - (mc.thePlayer.posY + 1.0);
                } else if (yDiff < 0.3) {
                    yDiff = target.posY + 1.0 - mc.thePlayer.posY;
                }
                return xDiff * xDiff + yDiff * yDiff + zDiff * zDiff;
            

        
    }
/* @Override
	
	public void onGui() {
        if (Xatz.getModuleByName("FPS").isToggled()) {
           if (this.ufr == null) {
              this.ufr = UnicodeFontRenderer.getFontFromAssets("Roboto-Light", 20, 0, 2.0F, 2.0F);
           }

           if (this.ufr2 == null) {
              this.ufr2 = UnicodeFontRenderer.getFontFromAssets("Roboto-Light", 15, 0, 2.0F, 2.0F);
           }

           if (target instanceof EntityPlayer || target instanceof EntityOtherPlayerMP) {
              float width = (float)(event.getWidth() / 2.0D + 100.0D);
              float height = (float)(event.getHeight() / 2.0D);
              EntityPlayer player = (EntityPlayer)target;
              Gui.drawRect((double)(width - 70.0F), (double)(height + 30.0F), (double)(width + 80.0F), (double)(height + 105.0F), (new Color(0, 0, 0, 180)).getRGB());
              ufr.drawStringWithShadow(player.getName(), width - 65.0F, height + 35.0F, 16777215);
              ufr2.drawStringWithShadow(player.onGround ? "On Ground" : "Off Ground", width - 65.0F, height + 50.0F, 16777215);
              ufr2.drawStringWithShadow("Health: " + player.getHealth(), width - 65.0F + (float)ufr2.getStringWidth("off Ground") + 13.0F, height + 50.0F, 16777215);
              ufr2.drawStringWithShadow("Distance: " + mc.thePlayer.getDistanceToEntity(player), width - 65.0F, height + 60.0F, -1);
              ufr2.drawStringWithShadow("Fall Distance: " + player.fallDistance, width - 65.0F, height + 70.0F, -1);
              ufr.drawStringWithShadow(player.getHealth() > mc.thePlayer.getHealth() ? "You Might Lose" : "You Might Win", width - 65.0F, height + 80.0F, player.getHealth() > mc.thePlayer.getHealth() ? Color.RED.getRGB() : Color.GREEN.getRGB());
              GL11.glPushMatrix();
              GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
              mc.getRenderItem().renderItemAndEffectIntoGUI(player.getCurrentEquippedItem(), (int)width + 50, (int)height + 80);
              GL11.glPopMatrix();
              float health = player.getHealth();
              float healthPercentage = health / player.getMaxHealth();
              float targetHealthPercentage = 0.0F;
              if (healthPercentage != this.lastHealth) {
                 float diff = healthPercentage - this.lastHealth;
                 targetHealthPercentage = this.lastHealth;
                 this.lastHealth += diff / 8.0F;
              }

              Color healthcolor = Color.WHITE;
              if (healthPercentage * 100.0F > 75.0F) {
                 healthcolor = Color.GREEN;
              } else if (healthPercentage * 100.0F > 50.0F && healthPercentage * 100.0F < 75.0F) {
                 healthcolor = Color.YELLOW;
              } else if (healthPercentage * 100.0F < 50.0F && healthPercentage * 100.0F > 25.0F) {
                 healthcolor = Color.ORANGE;
              } else if (healthPercentage * 100.0F < 25.0F) {
                 healthcolor = Color.RED;
              }

              Gui.drawRect((double)(width - 70.0F), (double)(height + 104.0F), (double)(width - 70.0F + 149.0F * targetHealthPercentage), (double)(height + 106.0F), healthcolor.getRGB());
              GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
              GuiInventory.drawEntityOnScreen((int)width + 60, (int)height + 75, 20, height + 12, width + 30, player);
           }
        } 
        
		super.onRender();
    } */
        
    


   

    private static enum HitLocation {
        AUTO(0.0),
        HEAD(1.0),
        CHEST(1.5),
        FEET(3.5);
        
        private double offset;

        private HitLocation(double offset) {
            this.offset = offset;
        }

        public double getOffset() {
            return this.offset;
        }
        
    }
    
    static int getRandomIntInRange(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }



}

