/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.functions.impl.misc;

import com.google.common.eventbus.Subscribe;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import mpp.venusfr.events.EventKey;
import mpp.venusfr.events.WorldEvent;
import mpp.venusfr.functions.api.Category;
import mpp.venusfr.functions.api.Function;
import mpp.venusfr.functions.api.FunctionRegister;
import mpp.venusfr.functions.settings.impl.BindSetting;
import mpp.venusfr.functions.settings.impl.BooleanSetting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.minecart.ChestMinecartEntity;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TextFormatting;
import net.optifine.render.RenderUtils;

@FunctionRegister(name="BaseFinder", type=Category.Misc)
public class BaseFinder
extends Function {
    private BindSetting active = new BindSetting("\u041a\u043d\u043e\u043f\u043a\u0430 \u043f\u043e\u0438\u0441\u043a\u0430", 0);
    public BooleanSetting notif = new BooleanSetting("\u0421\u043e\u043e\u0431\u0449\u0435\u043d\u0438\u0435 \u043e \u0441\u0443\u043d\u0434\u0443\u043a\u0435", true);
    public BooleanSetting gps = new BooleanSetting("GPS \u043d\u0430 \u0431\u043b\u0438\u0436\u0430\u0439\u0448\u0438\u0439 \u0441\u0443\u043d\u0434\u0443\u043a", true);
    private final Map<TileEntityType<?>, Integer> tiles = new HashMap(Map.of(new ChestTileEntity().getType(), (Object)new Color(0, 187, 8).getRGB()));

    public BaseFinder() {
        this.addSettings(this.active, this.notif, this.gps);
    }

    @Subscribe
    public void onKey(EventKey eventKey) {
        if (eventKey.getKey() == ((Integer)this.active.get()).intValue()) {
            boolean bl = false;
            for (TileEntity tileEntity : BaseFinder.mc.world.loadedTileEntityList) {
                int n;
                int n2;
                int n3;
                double d;
                if (!(tileEntity instanceof ChestTileEntity) || !((d = BaseFinder.mc.player.getDistanceSq(n3 = tileEntity.getPos().getX(), n2 = tileEntity.getPos().getY(), n = tileEntity.getPos().getZ())) < 20000.0)) continue;
                bl = true;
                if (((Boolean)this.notif.get()).booleanValue()) {
                    this.print(TextFormatting.GREEN + "\u0412\u043e\u0437\u043c\u043e\u0436\u043d\u043e \u0431\u044b\u043b\u0430 \u043d\u0430\u0439\u0434\u0435\u043d\u0430 \u0431\u0430\u0437\u0430");
                }
                BlockRayTraceResult blockRayTraceResult = new BlockRayTraceResult(new Vector3d(BaseFinder.mc.player.getPosX(), BaseFinder.mc.player.getPosY() - 1.0, BaseFinder.mc.player.getPosZ()), Direction.UP, new BlockPos(n3, n2, n), false);
                BaseFinder.mc.playerController.processRightClickBlock(BaseFinder.mc.player, BaseFinder.mc.world, Hand.MAIN_HAND, blockRayTraceResult);
                if (!((Boolean)this.gps.get()).booleanValue()) continue;
                BaseFinder.mc.player.sendChatMessage(".gps add Chest " + n3 + " " + n2 + " " + n);
            }
            if (!bl) {
                this.print(TextFormatting.RED + "\u0412 \u044d\u0442\u043e\u043c \u0440\u0435\u0433\u0438\u043e\u043d\u0435 \u043d\u0435 \u043d\u0430\u0448\u043b\u043e\u0441\u044c \u0431\u0430\u0437");
            }
        }
    }

    @Subscribe
    private void onRender(WorldEvent worldEvent) {
        for (TileEntity object : BaseFinder.mc.world.loadedTileEntityList) {
            if (!this.tiles.containsKey(object.getType())) continue;
            BlockPos blockPos = object.getPos();
            RenderUtils.drawBlockBox(blockPos, this.tiles.get(object.getType()));
        }
        for (Entity entity2 : BaseFinder.mc.world.getAllEntities()) {
            if (!(entity2 instanceof ChestMinecartEntity)) continue;
            RenderUtils.drawBlockBox(entity2.getPosition(), -1);
        }
    }
}

