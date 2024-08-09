/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.systems.modules.misc;

import meteordevelopment.meteorclient.events.packets.PacketEvent;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.EnumSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.entity.Entity;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntityS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityStatusEffectS2CPacket;

public final class AntiStaff extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<StaffMode> staffMode = sgGeneral.add(new EnumSetting.Builder<StaffMode>()
        .name("StaffMode")
        .description("The staffs that are there and not idfk.")
        .defaultValue(StaffMode.MCCI)
        .build()
    );

    String staffs = "";

    public AntiStaff() {
        super(Categories.Misc, "AntiStaff", "Detects if a staff is in the game :)");
    }

    @EventHandler
    private void onTick(final TickEvent.Post e) {
        if (mc.world == null) {
            this.onWorldChange();
        }
    }

    private void onWorldChange() {
        staffs = "";

        // TODO: add staff names & shit

        if (staffMode.get().equals(StaffMode.MCCI)) {
            staffs = "AFluffyGriffin:b3066f9c-fe2f-4bf1-a735-0f5b39ff26b3 Agne__:359cdbf4-f4a1-425b-9e39-bd036a7fba2f airborne343:c6ced72b-729b-42a3-8c0b-4b7f8e9cb165 aliyahu:0d36aac1-1f8f-4ea6-bdb3-985ad9a150af Beezeih:833b4611-7ef1-4483-95c0-291fef916cb3 CaptainFlamey:a312fbe0-09cb-4d15-ae48-895b7d91a835 Centranos:db6f2b90-762c-4ec9-82b1-9a2392cf409a Crazing:d428201d-8e31-46a6-b91d-230e6b367e12 CyberChamp:839abb02-75c9-4d2a-9b58-b0bbb13417a1 Dwittyy:6a02d9b9-d267-4aa3-a075-854de26c2691 Feferi:75f4dd93-f1d2-4e1a-88ab-736b6e6547ab Gameoholic:aa0de459-c794-4b37-a6b9-a7613719f7f1 ItsNitroTiger:37573b71-9fda-486d-9e37-b8f1f931193f ManWithRaft:04af1177-2242-4ef0-b0d3-cb675a05340a Melizsa:2482ebed-44c7-4a95-81f0-853ef3a55928 oGarfield:eb716400-fedb-49ba-8224-4800dcf9a042 picapi_:a6b5a2e6-f89d-410b-a803-a1d8673c9818 PonyRL:7f863afc-3115-458a-a8e2-e0bccdb38033 Pose1dan:809f08ce-55f3-410c-8c66-c1787f3125e2 ScottoAC:5252fa85-15a0-40f6-954b-078f58bd34b7 SirArchibald97:19f9fd28-558c-4959-98c2-fb1a18bed0a1 Spicv:5948803e-28f1-419e-8ed2-ee958278cb22 TheMysterys:4e832e0d-14b6-4f8f-ace2-280a9bf9dd98 Trualist:188ec7aa-29b1-469c-b690-dea155d0607d ufai:fbe34cb1-c85d-4a9f-8acf-4b5f49dd32df Valaeris:019ba906-12e5-4301-bbb6-6a68dc5d451e";
        }
    }


    @EventHandler
    private void onPacket(final PacketEvent.Receive e) {
        if (mc.player == null || mc.world == null) return;

        //lil bro forgot <?>
        Packet<?> p = e.packet;

        if (p instanceof EntityStatusEffectS2CPacket) {
            Entity entity = mc.world.getEntityById(((EntityStatusEffectS2CPacket) p).getEntityId());

            if (entity != null && (staffs.contains((CharSequence) entity.getName()) || staffs.contains(entity.getDisplayName().toString()))) {
                error("Staff detected (invis). Leave game.");
            }
        }

        if (p instanceof EntityS2CPacket) {
            Entity entity = ((EntityS2CPacket) p).getEntity(mc.world);

            if (entity != null && (staffs.contains(entity.getName().toString()) || staffs.contains(entity.getDisplayName().toString()))) {
                error("Staff detected (invis). Leave game.");
            }
        }
    }

    public enum StaffMode {
        MCCI,
    }
}
