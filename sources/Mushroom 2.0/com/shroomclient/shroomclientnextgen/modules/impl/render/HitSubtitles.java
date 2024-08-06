package com.shroomclient.shroomclientnextgen.modules.impl.render;

import com.shroomclient.shroomclientnextgen.config.ConfigOption;
import com.shroomclient.shroomclientnextgen.events.SubscribeEvent;
import com.shroomclient.shroomclientnextgen.events.impl.PacketEvent;
import com.shroomclient.shroomclientnextgen.events.impl.Render3dEvent;
import com.shroomclient.shroomclientnextgen.mixin.PlayerInteractEntityC2SPacketAccessor;
import com.shroomclient.shroomclientnextgen.modules.Module;
import com.shroomclient.shroomclientnextgen.modules.ModuleCategory;
import com.shroomclient.shroomclientnextgen.modules.RegisterModule;
import com.shroomclient.shroomclientnextgen.util.C;
import com.shroomclient.shroomclientnextgen.util.RenderUtil;
import com.shroomclient.shroomclientnextgen.util.TargetUtil;
import java.util.ArrayList;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

@RegisterModule(
    name = "Hit Subtitles",
    uniqueId = "hitsubs",
    description = "Superhero Hit Effects",
    category = ModuleCategory.Render
)
public class HitSubtitles extends Module {

    @Override
    protected void onEnable() {}

    @Override
    protected void onDisable() {}

    @ConfigOption(name = "Delay On Hit Effect", description = "", order = 1)
    public static Boolean delay = true;

    Identifier[] boomChickaWowWow = new Identifier[] {
        new Identifier("shroomclientnextgen", "superheroword1.png"),
        new Identifier("shroomclientnextgen", "superheroword2.png"),
        new Identifier("shroomclientnextgen", "superheroword3.png"),
        new Identifier("shroomclientnextgen", "superheroword4.png"),
        new Identifier("shroomclientnextgen", "superheroword5.png"),
        new Identifier("shroomclientnextgen", "superheroword6.png"),
        new Identifier("shroomclientnextgen", "superheroword7.png"),
    };

    public record hitSubTitlePoses(Vec3d pos, int texture, long time) {}

    private static ArrayList<hitSubTitlePoses> hitPoses = new ArrayList<>();
    private static ArrayList<hitSubTitlePoses> toRemove = new ArrayList<>();

    @SubscribeEvent
    public void draw3dEvent(Render3dEvent e) {
        // insane
        for (hitSubTitlePoses pos : hitPoses) {
            RenderUtil.drawHitSubtitle(
                boomChickaWowWow[pos.texture],
                pos.pos,
                0.8f,
                0.3f,
                pos.time,
                e.partialTicks,
                e.matrixStack
            );

            if (System.currentTimeMillis() - pos.time > 2000) toRemove.add(pos);
        }

        for (hitSubTitlePoses pos : toRemove) {
            hitPoses.remove(pos);
        }
        toRemove.clear();
    }

    long delayTime = 0;

    @SubscribeEvent
    public void packetEvent(PacketEvent.Send.Post e) {
        if (e.getPacket() instanceof PlayerInteractEntityC2SPacket p) {
            int eId = ((PlayerInteractEntityC2SPacketAccessor) p).getEntityId();
            if (
                C.w().getEntityById(eId) instanceof PlayerEntity ent &&
                !TargetUtil.isBot(ent)
            ) {
                if (!delay || System.currentTimeMillis() - delayTime > 300) {
                    Vec3d pos = ent.getPos();
                    pos = new Vec3d(
                        pos.x + Math.random() - 0.5f,
                        pos.y + (Math.random() * 2f),
                        pos.z + Math.random() - 0.5f
                    );

                    delayTime = System.currentTimeMillis();

                    hitPoses.add(
                        new hitSubTitlePoses(
                            pos,
                            (int) (Math.random() * boomChickaWowWow.length),
                            System.currentTimeMillis()
                        )
                    );
                }
            }
        }
    }
}
