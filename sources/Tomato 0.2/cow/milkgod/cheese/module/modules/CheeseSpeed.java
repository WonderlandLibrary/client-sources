/*
 * Decompiled with CFR 0_115.
 */
package cow.milkgod.cheese.module.modules;

import com.darkmagician6.eventapi.EventTarget;
import cow.milkgod.cheese.Cheese;
import cow.milkgod.cheese.commands.Command;
import cow.milkgod.cheese.commands.CommandManager;
import cow.milkgod.cheese.events.EventChatSend;
import cow.milkgod.cheese.events.EventPacketSent;
import cow.milkgod.cheese.events.EventPostMotionUpdates;
import cow.milkgod.cheese.events.EventPreMotionUpdates;
import cow.milkgod.cheese.events.EventTick;
import cow.milkgod.cheese.module.Category;
import cow.milkgod.cheese.module.Module;
import cow.milkgod.cheese.module.ModuleManager;
import cow.milkgod.cheese.utils.Logger;
import cow.milkgod.cheese.utils.Wrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Timer;

public class CheeseSpeed
extends Module {
    public static float modifier = 2.5f;
    public static float mode = 3.0f;
    private int movement;
    private int packets;
    private int HybridMode;
    private boolean ticked;
    private double sprintSpeed = 2.5;
    private cow.milkgod.cheese.utils.Timer timer = new cow.milkgod.cheese.utils.Timer();
    private int kekticks;
    private float height;
    private boolean strafe;

    public CheeseSpeed() {
        super("Speed", 0, Category.MOVEMENT, 10027110, true, "", new String[]{"sped"});
    }

    @Override
    public void onEnable() {
        super.onEnable();
        if (mode == 0.0f) {
            this.setDisplayName("Speed\u00a77 - TP");
            modifier = 2.48f;
        } else if (mode == 1.0f) {
            this.setDisplayName("Speed\u00a77 - Wrapper");
            modifier = 1.925f;
        } else if (mode == 2.0f) {
            this.setDisplayName("Speed\u00a77 - Jitter");
            modifier = 4.3f;
        } else if (mode == 3.0f) {
            this.setDisplayName("Speed\u00a77 - Tick");
        } else if (mode == 4.0f) {
            this.setDisplayName("Speed\u00a77 - Hybrid");
        } else if (mode == 5.0f) {
            this.setDisplayName("Speed\u00a77 - Debug");
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
        Timer.timerSpeed = 1.0f;
    }

    @EventTarget
    public void onTick(EventTick event) {
        if (!Wrapper.isMoving(Wrapper.getPlayer()) || !Wrapper.getPlayer().isCollidedVertically && mode != 6.0f) {
            Timer.timerSpeed = 1.0f;
        }
    }

    @EventTarget
    public void onMotion(EventPreMotionUpdates event) {
        if (mode == 0.0f) {
            ++this.movement;
            if (Wrapper.getPlayer().isSprinting() && Wrapper.getPlayer().isCollidedVertically && !Wrapper.isOnLiquid(Wrapper.getPlayer().getEntityBoundingBox()) && !Wrapper.getPlayer().isCollidedHorizontally && this.movement > 3) {
                if (this.strafe) {
                    Wrapper.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(Wrapper.getPlayer().posX + 0.2, Wrapper.getPlayer().posY + 0.5, Wrapper.getPlayer().posZ - 0.2, Wrapper.getPlayer().onGround));
                    this.strafe = !this.strafe;
                } else if (!this.strafe) {
                    Wrapper.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(Wrapper.getPlayer().posX - 0.2, Wrapper.getPlayer().posY + 0.5, Wrapper.getPlayer().posZ + 0.2, Wrapper.getPlayer().onGround));
                    this.strafe = !this.strafe;
                }
                Wrapper.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(Wrapper.getPlayer().posX, Wrapper.getPlayer().posY + 0.05, Wrapper.getPlayer().posZ, Wrapper.getPlayer().onGround));
                Wrapper.getPlayer().setPosition(Wrapper.getPlayer().posX + Wrapper.getPlayer().motionX * (double)modifier, Wrapper.getPlayer().posY, Wrapper.getPlayer().posZ + Wrapper.getPlayer().motionZ * (double)modifier);
                this.movement = 0;
            }
        } else if (mode == 1.0f) {
            ++this.movement;
            if (!Wrapper.isOnLiquid(Wrapper.getPlayer().getEntityBoundingBox()) && !Wrapper.isInLiquid(Wrapper.getPlayer().getEntityBoundingBox()) && (Wrapper.getGameSettings().keyBindForward.getIsKeyPressed() || Wrapper.getGameSettings().keyBindBack.getIsKeyPressed()) && Wrapper.getPlayer().isCollidedVertically && this.movement > 3) {
                Wrapper.getPlayer().motionX *= (double)modifier;
                Wrapper.getPlayer().motionZ *= (double)modifier;
                this.movement = 0;
            }
        } else if (mode == 2.0f) {
            ++this.movement;
            if ((Wrapper.getGameSettings().keyBindForward.getIsKeyPressed() || Wrapper.getGameSettings().keyBindBack.getIsKeyPressed()) && Wrapper.getPlayer().isCollidedVertically && !Wrapper.isOnLiquid(Wrapper.getPlayer().getEntityBoundingBox()) && !Wrapper.isInLiquid(Wrapper.getPlayer().getEntityBoundingBox())) {
                if (Wrapper.getPlayer().getHealth() >= 20.0f) {
                    Timer.timerSpeed = 1.05f;
                }
                if (this.movement > 3) {
                    Wrapper.getPlayer().motionX *= (double)modifier;
                    Wrapper.getPlayer().motionZ *= (double)modifier;
                    this.movement = 0;
                } else {
                    Wrapper.getPlayer().motionX /= 1.5499999523162842;
                    Wrapper.getPlayer().motionZ /= 1.5499999523162842;
                }
            }
        } else if (mode == 3.0f) {
            if (Wrapper.isMoving(Wrapper.getPlayer())) {
                Timer.timerSpeed = 32767.0f;
            }
            if (Wrapper.isMoving(Wrapper.getPlayer())) {
                Timer.timerSpeed = 1.3f;
                Wrapper.getPlayer().motionX *= 1.0199999809265137;
                Wrapper.getPlayer().motionZ *= 1.0199999809265137;
            }
        } else if (mode == 4.0f) {
            ++this.HybridMode;
            ++this.movement;
            if (this.movement > 3) {
                this.movement = 0;
            }
            if (this.HybridMode <= 20 && Wrapper.getPlayer().isSprinting() && Wrapper.getPlayer().isCollidedVertically && !Wrapper.isOnLiquid(Wrapper.getPlayer().getEntityBoundingBox()) && !Wrapper.getPlayer().isCollidedHorizontally && this.movement == 0) {
                Wrapper.sendPacketDirect(new C03PacketPlayer.C04PacketPlayerPosition(Wrapper.getPlayer().posX, Wrapper.getPlayer().posY, Wrapper.getPlayer().posZ, Wrapper.getPlayer().onGround));
                Wrapper.getPlayer().setPosition(Wrapper.getPlayer().posX + Wrapper.getPlayer().motionX * 4.32, Wrapper.getPlayer().posY, Wrapper.getPlayer().posZ + Wrapper.getPlayer().motionZ * 4.32);
            } else if (this.HybridMode == 22) {
                Timer.timerSpeed = 1.2f;
                Wrapper.getPlayer().motionX *= 0.8500000238418579;
                Wrapper.getPlayer().motionZ *= 0.8500000238418579;
                Wrapper.sendPacketDirect(new C03PacketPlayer.C04PacketPlayerPosition(Wrapper.getPlayer().posX, Wrapper.getPlayer().posY, Wrapper.getPlayer().posZ, Wrapper.getPlayer().onGround));
            } else if (this.HybridMode >= 23) {
                Timer.timerSpeed = 1.0f;
                if ((Wrapper.getGameSettings().keyBindForward.getIsKeyPressed() || Wrapper.getGameSettings().keyBindBack.getIsKeyPressed()) && Wrapper.getPlayer().isCollidedVertically && !Wrapper.isOnLiquid(Wrapper.getPlayer().getEntityBoundingBox()) && !Wrapper.isInLiquid(Wrapper.getPlayer().getEntityBoundingBox())) {
                    if (this.movement == 0) {
                        Wrapper.getPlayer().motionX *= 4.4;
                        Wrapper.getPlayer().motionZ *= 4.4;
                    } else {
                        Wrapper.getPlayer().motionX /= 1.5;
                        Wrapper.getPlayer().motionZ /= 1.5;
                    }
                }
            }
            if (this.HybridMode > 70) {
                this.HybridMode = 0;
                Timer.timerSpeed = 1.2f;
                Wrapper.getPlayer().motionX *= 0.8500000238418579;
                Wrapper.getPlayer().motionZ *= 0.8500000238418579;
                Wrapper.sendPacketDirect(new C03PacketPlayer.C04PacketPlayerPosition(Wrapper.getPlayer().posX, Wrapper.getPlayer().posY, Wrapper.getPlayer().posZ, Wrapper.getPlayer().onGround));
            }
        } else if (mode == 6.0f && Wrapper.isMoving(Wrapper.getPlayer())) {
            ++this.movement;
            Wrapper.getPlayer().motionY -= 2.596692E-4;
            event.y -= 2.596692E-4;
            Wrapper.getPlayer().motionX *= 1.4;
            Wrapper.getPlayer().motionZ *= 1.4;
        }
    }

    @EventTarget
    public void onMotion(EventPostMotionUpdates event) {
        if (mode == 5.0f) {
            if (Wrapper.isMoving(Wrapper.getPlayer())) {
                if (Wrapper.getPlayer().onGround && !Wrapper.isOnLiquid(Wrapper.getPlayer().getEntityBoundingBox())) {
                    float v3 = 0.0f;
                    float v4 = 0.1732f;
                    float v5 = 0.0f;
                    float v6 = 0.0f;
                    float v7 = Wrapper.getPlayer().rotationYaw;
                    if (Wrapper.getPlayer().moveForward < 0.0f) {
                        v7 += 180.0f;
                    }
                    if (Wrapper.getPlayer().moveStrafing > 0.0f) {
                        v7 -= 90.0f * (Wrapper.getPlayer().moveForward > 0.0f ? 0.5f : (Wrapper.getPlayer().moveForward < 0.0f ? -0.5f : 1.0f));
                    }
                    if (Wrapper.getPlayer().moveStrafing < 0.0f) {
                        v7 += 90.0f * (Wrapper.getPlayer().moveForward > 0.0f ? 0.5f : (Wrapper.getPlayer().moveForward < 0.0f ? -0.5f : 1.0f));
                    }
                    float v8 = v7 * 0.017453292f;
                    if (!Wrapper.getMinecraft().gameSettings.keyBindJump.getIsKeyPressed()) {
                        v5 = - MathHelper.sin(v8) * v4;
                        v6 = MathHelper.cos(v8) * v4;
                    }
                    ++this.kekticks;
                    if (this.kekticks >= 0 && this.kekticks < 2) {
                        this.height = 0.0f;
                        Timer.timerSpeed = 3.8f;
                    } else if (this.kekticks >= 2 && this.kekticks < 4) {
                        this.height = 0.1f;
                        Timer.timerSpeed = 1.02f;
                    } else if (this.kekticks >= 4 && this.kekticks < 6) {
                        Timer.timerSpeed = 1.0f;
                        this.height = 0.17f;
                    } else {
                        this.kekticks = 0;
                    }
                    Wrapper.getPlayer().motionY = 0.0;
                    Wrapper.getPlayer().motionX += (double)v5;
                    Wrapper.getPlayer().motionZ += (double)v6;
                } else {
                    Timer.timerSpeed = 1.0f;
                }
            } else {
                Timer.timerSpeed = 1.0f;
            }
        }
    }

    @EventTarget
    public void onSendPacket(EventPacketSent event) {
        if (event.getPacket() instanceof C03PacketPlayer && Wrapper.getPlayer().lastTickPosY == Wrapper.getPlayer().posY) {
            ++this.packets;
            if (this.packets >= 5 && mode == 3.0f) {
                event.setCancelled(true);
                this.packets = 0;
            } else if (!(mode != 0.0f && mode != 3.0f && mode != 4.0f || Wrapper.isMoving(Wrapper.getPlayer()))) {
                Cheese.getInstance();
                if (!Cheese.moduleManager.getModbyName("Blink").getState()) {
                    event.setCancelled(true);
                }
            }
        }
        if (mode == 6.0f && Wrapper.isMoving(Wrapper.getPlayer()) && Wrapper.getPlayer().isCollidedVertically && !Wrapper.isOnLiquid(Wrapper.getPlayer().getEntityBoundingBox()) && !Wrapper.isInLiquid(Wrapper.getPlayer().getEntityBoundingBox())) {
            event.setCancelled(false);
        }
    }

    @Override
    protected void addCommand() {
        Cheese.getInstance();
        CommandManager commandManager = Cheese.commandManager;
        CommandManager.addCommand(new Command("Speed", "Unknown Option! ", null, "<0 - TP | 1 - Wrapper | 2 - Jitter | 3 - Tick | 4 - Hybrid | 5 - Debug |>", "Fucklord shines down cheese walking shoes unto you"){

            @EventTarget
            public void onTick(EventTick ev2) {
                try {
                    String nigger = EventChatSend.getMessage().split(" ")[1];
                    CheeseSpeed.mode = Float.parseFloat(nigger);
                    if (CheeseSpeed.mode == 0.0f) {
                        CheeseSpeed.this.setDisplayName("Speed\u00a77 - TP");
                        CheeseSpeed.modifier = 2.48f;
                    } else if (CheeseSpeed.mode == 1.0f) {
                        CheeseSpeed.this.setDisplayName("Speed\u00a77 - Wrapper");
                        CheeseSpeed.modifier = 1.925f;
                    } else if (CheeseSpeed.mode == 2.0f) {
                        CheeseSpeed.this.setDisplayName("Speed\u00a77 - Jitter");
                        CheeseSpeed.modifier = 4.3f;
                    } else if (CheeseSpeed.mode == 3.0f) {
                        CheeseSpeed.this.setDisplayName("Speed\u00a77 - Tick");
                    } else if (CheeseSpeed.mode == 4.0f) {
                        CheeseSpeed.this.setDisplayName("Speed\u00a77 - Hybrid");
                    } else if (CheeseSpeed.mode == 5.0f) {
                        CheeseSpeed.this.setDisplayName("Speed\u00a77 - Debug");
                    }
                    Logger.logChat("Mode set to " + CheeseSpeed.mode);
                }
                catch (Exception e) {
                    Logger.logChat(String.valueOf(String.valueOf(this.getErrorMessage())) + this.getArguments());
                }
                this.Toggle();
            }
        });
    }

}

