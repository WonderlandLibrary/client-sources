package com.client.glowclient.modules.movement;

import net.minecraft.entity.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.client.glowclient.events.*;
import net.minecraft.entity.item.*;
import com.client.glowclient.sponge.mixinutils.*;
import org.lwjgl.input.*;
import com.client.glowclient.*;
import com.client.glowclient.modules.*;
import com.client.glowclient.utils.*;

public class BoatTravel extends ModuleContainer
{
    public static nB mode;
    public static nB speed;
    public static NumberValue phaseSpeed;
    public Timer b;
    
    @SubscribeEvent
    public void M(final EventBoat eventBoat) {
        if (EntityUtils.m((Entity)eventBoat.getBoat())) {
            eventBoat.setYaw(eventBoat.getBoat().rotationYaw = Wrapper.mc.player.rotationYaw);
        }
    }
    
    @SubscribeEvent
    public void A(final EventUpdate eventUpdate) {
        double n = 0.0;
        if (Wrapper.mc.player.getRidingEntity() instanceof EntityBoat) {
            if (BoatTravel.mode.e().equals("Top") && Wrapper.mc.player != null && Wrapper.mc.player.getRidingEntity() != null) {
                Wrapper.mc.player.getRidingEntity().noClip = true;
                HookTranslator.v2 = true;
                double n2;
                if (Wrapper.mc.player.posY < 124.5 && Wrapper.mc.player.getRidingEntity().posY < 124.5) {
                    n2 = (n = BoatTravel.phaseSpeed.k());
                }
                else if (BoatTravel.speed.e().equals("400m/s")) {
                    n2 = (Wrapper.mc.world.getChunk(Wrapper.mc.player.getPosition()).isLoaded() ? (n = 100.0) : ((Wrapper.mc.player.posY < 125.5 && Wrapper.mc.player.getRidingEntity().posY < 125.5) ? (n = BoatTravel.phaseSpeed.k()) : (n = 22.177769)));
                }
                else {
                    if (BoatTravel.speed.e().equals("1600m/s")) {
                        n = 88.844439;
                    }
                    n2 = n;
                }
                u.M(n2, Wrapper.mc.player.getRidingEntity());
                HookTranslator.v1 = true;
                if (Wrapper.mc.gameSettings.keyBindJump.isKeyDown()) {
                    Wrapper.mc.player.getRidingEntity().onGround = false;
                    if (Wrapper.mc.player.posY < 125.5 && Wrapper.mc.player.getRidingEntity().posY < 125.5) {
                        if (!this.b.hasBeenSet()) {
                            this.b.reset();
                        }
                        if (this.b.delay(0.0)) {
                            Wrapper.mc.player.getRidingEntity().motionY = 1.0;
                        }
                        if (this.b.delay(50.0)) {
                            Wrapper.mc.player.getRidingEntity().motionY = 0.0;
                        }
                        if (this.b.delay(800.0)) {
                            this.b.reset();
                        }
                    }
                    else {
                        Wrapper.mc.player.getRidingEntity().motionY = 0.0;
                    }
                }
                else if (Wrapper.mc.player.posY > 1.0 && Wrapper.mc.player.getRidingEntity().posY > 1.0) {
                    if (Keyboard.isKeyDown(208)) {
                        Wrapper.mc.player.getRidingEntity().motionY = -1.0;
                    }
                    else if (Keyboard.isKeyDown(200)) {
                        Wrapper.mc.player.getRidingEntity().motionY = 1.0;
                    }
                    else {
                        Wrapper.mc.player.getRidingEntity().motionY = 0.0;
                    }
                }
                else {
                    Wrapper.mc.player.getRidingEntity().motionY = 0.0;
                }
            }
            if (BoatTravel.mode.e().equals("Bottom") && Wrapper.mc.player != null && Wrapper.mc.player.getRidingEntity() != null) {
                Wrapper.mc.player.getRidingEntity().noClip = true;
                HookTranslator.v2 = true;
                double k = 0.0;
                if (Wrapper.mc.player.posY > 1.5 && Wrapper.mc.player.getRidingEntity().posY > 1.5) {
                    k = BoatTravel.phaseSpeed.k();
                }
                else if (BoatTravel.speed.e().equals("400m/s")) {
                    if (Wrapper.mc.world.getChunk(Wrapper.mc.player.getPosition()).isLoaded()) {}
                }
                else if (BoatTravel.speed.e().equals("1600m/s")) {
                    n = 88.844439;
                }
                u.M(k, Wrapper.mc.player.getRidingEntity());
                HookTranslator.v1 = true;
                if (Keyboard.isKeyDown(208)) {
                    Wrapper.mc.player.getRidingEntity().onGround = false;
                    if (Wrapper.mc.player.posY > 1.5 && Wrapper.mc.player.getRidingEntity().posY > 1.5) {
                        if (!this.b.hasBeenSet()) {
                            this.b.reset();
                        }
                        if (this.b.delay(0.0)) {
                            Wrapper.mc.player.getRidingEntity().motionY = -1.0;
                        }
                        if (this.b.delay(50.0)) {
                            Wrapper.mc.player.getRidingEntity().motionY = 0.0;
                        }
                        if (this.b.delay(550.0)) {
                            this.b.reset();
                        }
                    }
                    else {
                        Wrapper.mc.player.getRidingEntity().motionY = 0.0;
                    }
                }
                else if (Wrapper.mc.player.posY > 0.5 && Wrapper.mc.player.getRidingEntity().posY > 0.5) {
                    Wrapper.mc.player.getRidingEntity().motionY = (Wrapper.mc.gameSettings.keyBindJump.isKeyDown() ? 0.5 : 0.0);
                }
                else {
                    Wrapper.mc.player.getRidingEntity().motionY = 0.0;
                }
            }
            if (BoatTravel.mode.e().equals("GlideBounce") && Wrapper.mc.player != null && Wrapper.mc.player.getRidingEntity() != null) {
                Wrapper.mc.player.getRidingEntity().noClip = true;
                HookTranslator.v2 = true;
                if (!Wrapper.mc.world.getChunk(Wrapper.mc.player.getPosition()).isLoaded()) {
                    double motionY;
                    if (Keyboard.isKeyDown(200)) {
                        motionY = 20.0;
                    }
                    else {
                        motionY = 0.8;
                    }
                    if (!Wrapper.mc.gameSettings.keyBindSprint.isKeyDown()) {
                        if (!Keyboard.isKeyDown(208)) {
                            if (!this.b.hasBeenSet()) {
                                this.b.reset();
                            }
                            if (this.b.delay(0.0)) {
                                Wrapper.mc.player.getRidingEntity().motionY = motionY;
                            }
                            if (this.b.delay(50.0)) {
                                Wrapper.mc.player.getRidingEntity().motionY = -0.033;
                            }
                            if (this.b.delay(1000.0)) {
                                this.b.reset();
                            }
                        }
                        else {
                            Wrapper.mc.player.getRidingEntity().motionY = -2.0;
                        }
                    }
                }
                if (Wrapper.mc.world.getChunk(Wrapper.mc.player.getPosition()).isLoaded()) {
                    if (!Wrapper.mc.gameSettings.keyBindSprint.isKeyDown()) {
                        u.M(BoatTravel.phaseSpeed.k(), Wrapper.mc.player.getRidingEntity());
                    }
                    else {
                        u.M(100.0, Wrapper.mc.player.getRidingEntity());
                    }
                }
                else {
                    if (BoatTravel.speed.e().equals("400m/s")) {
                        if (Keyboard.isKeyDown(203)) {
                            u.M(2.0, Wrapper.mc.player.getRidingEntity());
                        }
                        else {
                            u.M(22.077769, Wrapper.mc.player.getRidingEntity());
                        }
                    }
                    if (BoatTravel.speed.e().equals("1600m/s")) {
                        if (Keyboard.isKeyDown(203)) {
                            u.M(2.0, Wrapper.mc.player.getRidingEntity());
                        }
                        else {
                            u.M(88.844439, Wrapper.mc.player.getRidingEntity());
                        }
                    }
                }
            }
            if (BoatTravel.mode.e().equals("Phase") && Wrapper.mc.player != null && Wrapper.mc.player.getRidingEntity() != null) {
                Wrapper.mc.player.getRidingEntity().noClip = true;
                HookTranslator.v2 = true;
                double i = 0.0;
                if (Wrapper.mc.world.getChunk(Wrapper.mc.player.getPosition()).isLoaded()) {
                    i = BoatTravel.phaseSpeed.k();
                }
                u.M(i, Wrapper.mc.player.getRidingEntity());
                HookTranslator.v1 = true;
                if (Wrapper.mc.gameSettings.keyBindJump.isKeyDown()) {
                    Wrapper.mc.player.getRidingEntity().onGround = false;
                    if (Wrapper.mc.player.posY >= 125.5 || Wrapper.mc.player.getRidingEntity().posY >= 125.5) {
                        Wrapper.mc.player.getRidingEntity().motionY = 0.0;
                        return;
                    }
                    if (!this.b.hasBeenSet()) {
                        this.b.reset();
                    }
                    BoatTravel boatTravel = null;
                    Label_1655: {
                        if (this.b.delay(0.0)) {
                            if (Wrapper.mc.player.posY < 124.5 && Wrapper.mc.player.getRidingEntity().posY < 124.5) {
                                Wrapper.mc.player.getRidingEntity().motionY = 1.0;
                                boatTravel = this;
                                break Label_1655;
                            }
                            Wrapper.mc.player.getRidingEntity().motionY = 0.75;
                        }
                        boatTravel = this;
                    }
                    if (boatTravel.b.delay(50.0)) {
                        Wrapper.mc.player.getRidingEntity().motionY = 0.0;
                    }
                    if (this.b.delay(800.0)) {
                        this.b.reset();
                    }
                }
                else if (Wrapper.mc.gameSettings.keyBindSprint.isKeyDown()) {
                    Wrapper.mc.player.getRidingEntity().onGround = false;
                    if (Wrapper.mc.player.posY <= 2.0 || Wrapper.mc.player.getRidingEntity().posY <= 2.0) {
                        Wrapper.mc.player.getRidingEntity().motionY = 0.0;
                        return;
                    }
                    if (!this.b.hasBeenSet()) {
                        this.b.reset();
                    }
                    if (this.b.delay(0.0)) {
                        Wrapper.mc.player.getRidingEntity().motionY = -1.0;
                    }
                    if (this.b.delay(50.0)) {
                        Wrapper.mc.player.getRidingEntity().motionY = 0.0;
                    }
                    if (this.b.delay(800.0)) {
                        this.b.reset();
                    }
                }
                else {
                    Wrapper.mc.player.getRidingEntity().motionY = 0.0;
                }
            }
        }
    }
    
    @Override
    public String M() {
        return BoatTravel.mode.e();
    }
    
    @SubscribeEvent
    public void D(final EventUpdate eventUpdate) {
        if (Wrapper.mc.player.getRidingEntity() != null) {
            HookTranslator.v1 = (Ob.M() instanceof EntityBoat);
        }
    }
    
    @Override
    public void E() {
        if (Wrapper.mc.player != null) {
            if (Wrapper.mc.player.getRidingEntity() != null) {
                Wrapper.mc.player.getRidingEntity().noClip = false;
            }
            HookTranslator.v2 = false;
            HookTranslator.v1 = false;
        }
    }
    
    public BoatTravel() {
        super(Category.MOVEMENT, "BoatTravel", false, -1, "Go faster than chunks in boats! Down Arrow Key to go down");
        this.b = new Timer();
    }
    
    static {
        BoatTravel.mode = ValueFactory.M("BoatTravel", "Mode", "Mode of BoatTravel", "Phase", "Top", "Bottom", "GlideBounce", "Phase");
        BoatTravel.speed = ValueFactory.M("BoatTravel", "Speed", "Speed source of BoatTravel", "400m/s", "400m/s", "1600m/s");
        BoatTravel.phaseSpeed = ValueFactory.M("BoatTravel", "PhaseSpeed", "Speed of Phasing", 1.59, 0.01, 0.1, 20.0);
    }
}
