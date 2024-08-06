package com.shroomclient.shroomclientnextgen.listeners;

import com.shroomclient.shroomclientnextgen.annotations.RegisterListeners;
import com.shroomclient.shroomclientnextgen.events.SubscribeEvent;
import com.shroomclient.shroomclientnextgen.events.impl.RenderTickEvent;
import com.shroomclient.shroomclientnextgen.modules.impl.movement.Scaffold;

@RegisterListeners
public class MinMaxSettingsListener {

    @SubscribeEvent
    public void onGUIRender(RenderTickEvent e) {
        switch (Scaffold.scaffoldMode) {
            case Hypixel -> {
                Scaffold.keepY = false;
                Scaffold.rotationMode = Scaffold.RotationMode.Closest;
                Scaffold.DontClosest = false;
                Scaffold.constantPitch = false;
                Scaffold.constantYaw = false;
                Scaffold.headSnapFix = true;
                Scaffold.sprintMode = Scaffold.MovementMode.Hypixel;
                Scaffold.bridgeDown = true;
                Scaffold.besterYcheck = false;
                Scaffold.noSelfPlaceNormal = true;
                Scaffold.onlyNecPlace = true;
                Scaffold.placemindelay = 0;
                Scaffold.placemaxdelay = 0;
                Scaffold.tinyPitchCorrect = false;
                Scaffold.minYaw = 60;
                Scaffold.maxYaw = 90;
                Scaffold.maxYVelocity = true;
                Scaffold.maxYvelo = 0.12f;
                Scaffold.betterPitchClosest = false;
            }
            case Simple -> {
                Scaffold.rotationMode = Scaffold.RotationMode.Closest;
                Scaffold.DontClosest = false;
                Scaffold.constantPitch = false;
                Scaffold.constantYaw = false;
                Scaffold.headSnapFix = true;
                Scaffold.bridgeDown = true;
                Scaffold.besterYcheck = true;
                Scaffold.noSelfPlaceNormal = true;
                Scaffold.onlyNecPlace = false;
                Scaffold.placemindelay = 0;
                Scaffold.placemaxdelay = 0;
                Scaffold.tinyPitchCorrect = false;
                Scaffold.maxYVelocity = false;
                Scaffold.betterPitchClosest = false;
            }
            case Vulcan -> {
                Scaffold.rotationMode = Scaffold.RotationMode.Closest;
                Scaffold.DontClosest = true;
                Scaffold.constantPitch = true;
                Scaffold.constantYaw = true;
                Scaffold.headSnapFix = true;
                Scaffold.sprintMode = Scaffold.MovementMode.Vanilla;
                Scaffold.bridgeDown = true;
                Scaffold.besterYcheck = true;
                Scaffold.noSelfPlaceNormal = false;
                Scaffold.onlyNecPlace = true;
                Scaffold.placemindelay = 0;
                Scaffold.placemaxdelay = 0;
                Scaffold.tinyPitchCorrect = true;
                Scaffold.minYaw = 20;
                Scaffold.maxYaw = 90;
                Scaffold.maxYVelocity = true;
                Scaffold.maxYvelo = 0.12f;
                Scaffold.betterPitchClosest = false;
            }
        }
    }
}
