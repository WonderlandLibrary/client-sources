package rina.turok.bope.bopemod.hacks.render;

import java.util.Iterator;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBrewingStand;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.tileentity.TileEntityDropper;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.tileentity.TileEntityShulkerBox;
import rina.turok.bope.Bope;
import rina.turok.bope.bopemod.BopeModule;
import rina.turok.bope.bopemod.events.BopeEventRender;
import rina.turok.bope.bopemod.guiscreen.settings.BopeSetting;
import rina.turok.bope.bopemod.hacks.BopeCategory;
import rina.turok.turok.draw.TurokRenderHelp;

public class BopeStorageESP extends BopeModule {
   BopeSetting shu_ = this.create("Shulker Color", "StorageESPShulker", "HUD", this.combobox(new String[]{"HUD", "Client"}));
   BopeSetting enc_ = this.create("Enchest Color", "StorageESPEnchest", "Client", this.combobox(new String[]{"HUD", "Client"}));
   BopeSetting che_ = this.create("Chest Color", "StorageESPChest", "Client", this.combobox(new String[]{"HUD", "Client"}));
   BopeSetting oth_ = this.create("Others Color", "StorageESPOthers", "Client", this.combobox(new String[]{"HUD", "Client"}));
   BopeSetting ot_a = this.create("Outline A", "StorageESPOutlineA", 150, 0, 255);
   BopeSetting a = this.create("Solid A", "StorageESPSolidA", 150, 0, 255);
   private int color_alpha;

   public BopeStorageESP() {
      super(BopeCategory.BOPE_RENDER);
      this.name = "Storage ESP";
      this.tag = "StorageESP";
      this.description = "Is able to see storages in world";
   }

   public void render(BopeEventRender event) {
      int nl_r = Bope.client_r;
      int nl_g = Bope.client_g;
      int nl_b = Bope.client_b;
      this.color_alpha = this.a.get_value(1);
      Iterator var5 = this.mc.world.loadedTileEntityList.iterator();

      while(true) {
         TileEntity tiles;
         do {
            if (!var5.hasNext()) {
               return;
            }

            tiles = (TileEntity)var5.next();
            if (tiles instanceof TileEntityShulkerBox) {
               TileEntityShulkerBox shulker = (TileEntityShulkerBox)tiles;
               int hex = -16777216 | shulker.getColor().getColorValue() & -1;
               if (this.shu_.in("HUD")) {
                  this.draw(tiles, nl_r, nl_g, nl_b);
               } else {
                  this.draw(tiles, (hex & 16711680) >> 16, (hex & '\uff00') >> 8, hex & 255);
               }
            }

            if (tiles instanceof TileEntityEnderChest) {
               if (this.enc_.in("HUD")) {
                  this.draw(tiles, nl_r, nl_g, nl_b);
               } else {
                  this.draw(tiles, 204, 0, 255);
               }
            }

            if (tiles instanceof TileEntityChest) {
               if (this.che_.in("HUD")) {
                  this.draw(tiles, nl_r, nl_g, nl_b);
               } else {
                  this.draw(tiles, 153, 102, 0);
               }
            }
         } while(!(tiles instanceof TileEntityDispenser) && !(tiles instanceof TileEntityDropper) && !(tiles instanceof TileEntityHopper) && !(tiles instanceof TileEntityFurnace) && !(tiles instanceof TileEntityBrewingStand));

         if (this.oth_.in("HUD")) {
            this.draw(tiles, nl_r, nl_g, nl_b);
         } else {
            this.draw(tiles, 190, 190, 190);
         }
      }
   }

   public void draw(TileEntity tile_entity, int r, int g, int b) {
      TurokRenderHelp.prepare("quads");
      TurokRenderHelp.draw_cube(tile_entity.getPos(), r, g, b, this.a.get_value(1), "all");
      TurokRenderHelp.release();
      TurokRenderHelp.prepare("lines");
      TurokRenderHelp.draw_cube_line(tile_entity.getPos(), r, g, b, this.ot_a.get_value(1), "all");
      TurokRenderHelp.release();
   }
}
