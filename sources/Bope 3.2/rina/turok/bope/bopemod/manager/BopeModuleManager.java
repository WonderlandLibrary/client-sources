package rina.turok.bope.bopemod.manager;

import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import rina.turok.bope.bopemod.BopeModule;
import rina.turok.bope.bopemod.events.BopeEventRender;
import rina.turok.bope.bopemod.hacks.BopeCategory;
import rina.turok.bope.bopemod.hacks.BopeClickGUI;
import rina.turok.bope.bopemod.hacks.BopeClickHUD;
import rina.turok.bope.bopemod.hacks.chat.BopeAutoGG;
import rina.turok.bope.bopemod.hacks.chat.BopeChatStyle;
import rina.turok.bope.bopemod.hacks.chat.BopeChatSuffix;
import rina.turok.bope.bopemod.hacks.chat.BopeVisualRange;
import rina.turok.bope.bopemod.hacks.combat.BopeAutoCrystal;
import rina.turok.bope.bopemod.hacks.combat.BopeAutoGapple;
import rina.turok.bope.bopemod.hacks.combat.BopeAutoOffHandCrystal;
import rina.turok.bope.bopemod.hacks.combat.BopeAutoTotem;
import rina.turok.bope.bopemod.hacks.combat.BopeCriticals;
import rina.turok.bope.bopemod.hacks.combat.BopeFastUtil;
import rina.turok.bope.bopemod.hacks.combat.BopeKillAura;
import rina.turok.bope.bopemod.hacks.combat.BopeSurround;
import rina.turok.bope.bopemod.hacks.combat.BopeVelocity;
import rina.turok.bope.bopemod.hacks.exploit.BopeSpeedMine;
import rina.turok.bope.bopemod.hacks.exploit.BopeTimer;
import rina.turok.bope.bopemod.hacks.exploit.BopeXCarry;
import rina.turok.bope.bopemod.hacks.misc.BopeNoEntityTrace;
import rina.turok.bope.bopemod.hacks.misc.BopeNoHurtCam;
import rina.turok.bope.bopemod.hacks.misc.BopeRPC;
import rina.turok.bope.bopemod.hacks.misc.BopeSmallHand;
import rina.turok.bope.bopemod.hacks.misc.BopeSwing;
import rina.turok.bope.bopemod.hacks.movement.BopeInventoryWalk;
import rina.turok.bope.bopemod.hacks.movement.BopeNoSlowDown;
import rina.turok.bope.bopemod.hacks.movement.BopeStrafe;
import rina.turok.bope.bopemod.hacks.player.BopeFreecam;
import rina.turok.bope.bopemod.hacks.render.BopeBrightness;
import rina.turok.bope.bopemod.hacks.render.BopeEntityESP;
import rina.turok.bope.bopemod.hacks.render.BopeFreeCameraOrient;
import rina.turok.bope.bopemod.hacks.render.BopeHighlight;
import rina.turok.bope.bopemod.hacks.render.BopeHoleColor;
import rina.turok.bope.bopemod.hacks.render.BopeNameTag;
import rina.turok.bope.bopemod.hacks.render.BopePlayerESP;
import rina.turok.bope.bopemod.hacks.render.BopeStorageESP;
import rina.turok.bope.bopemod.system.BopeSystem;
import rina.turok.bope.bopemod.system.event.BopeEventRender0;
import rina.turok.turok.draw.TurokRenderHelp;

public class BopeModuleManager {
   private String tag;
   public static ArrayList array_module = new ArrayList();
   public static Minecraft mc = Minecraft.getMinecraft();

   public BopeModuleManager(String tag) {
      this.tag = tag;
      this.add_module(new BopeClickGUI());
      this.add_module(new BopeClickHUD());
      this.add_module(new BopeVisualRange());
      this.add_module(new BopeChatSuffix());
      this.add_module(new BopeChatStyle());
      this.add_module(new BopeAutoGG());
      this.add_module(new BopeAutoOffHandCrystal());
      this.add_module(new BopeAutoCrystal());
      this.add_module(new BopeAutoGapple());
      this.add_module(new BopeAutoTotem());
      this.add_module(new BopeCriticals());
      this.add_module(new BopeFastUtil());
      this.add_module(new BopeKillAura());
      this.add_module(new BopeSurround());
      this.add_module(new BopeVelocity());
      this.add_module(new BopeSpeedMine());
      this.add_module(new BopeXCarry());
      this.add_module(new BopeTimer());
      this.add_module(new BopeNoEntityTrace());
      this.add_module(new BopeNoHurtCam());
      this.add_module(new BopeSmallHand());
      this.add_module(new BopeSwing());
      this.add_module(new BopeRPC());
      this.add_module(new BopeInventoryWalk());
      this.add_module(new BopeNoSlowDown());
      this.add_module(new BopeStrafe());
      this.add_module(new BopeFreecam());
      this.add_module(new BopeFreeCameraOrient());
      this.add_module(new BopeBrightness());
      this.add_module(new BopeStorageESP());
      this.add_module(new BopeHighlight());
      this.add_module(new BopeHoleColor());
      this.add_module(new BopePlayerESP());
      this.add_module(new BopeEntityESP());
      this.add_module(new BopeNameTag());
      this.add_module(new BopeEventRender0());
      this.add_module(new BopeSystem());
   }

   public void add_module(BopeModule module) {
      array_module.add(module);
   }

   public ArrayList get_array_modules() {
      return array_module;
   }

   public ArrayList get_array_active_modules() {
      ArrayList actived_modules = new ArrayList();
      Iterator var2 = this.get_array_modules().iterator();

      while(var2.hasNext()) {
         BopeModule modules = (BopeModule)var2.next();
         if (modules.is_active()) {
            actived_modules.add(modules);
         }
      }

      return actived_modules;
   }

   public Vec3d process(Entity entity, double x, double y, double z) {
      return new Vec3d((entity.posX - entity.lastTickPosX) * x, (entity.posY - entity.lastTickPosY) * y, (entity.posZ - entity.lastTickPosZ) * z);
   }

   public Vec3d get_interpolated_pos(Entity entity, double ticks) {
      return (new Vec3d(entity.lastTickPosX, entity.lastTickPosY, entity.lastTickPosZ)).add(this.process(entity, ticks, ticks, ticks));
   }

   public void render(RenderWorldLastEvent event) {
      mc.profiler.startSection("bope");
      mc.profiler.startSection("setup");
      GlStateManager.disableTexture2D();
      GlStateManager.enableBlend();
      GlStateManager.disableAlpha();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      GlStateManager.shadeModel(7425);
      GlStateManager.disableDepth();
      GlStateManager.glLineWidth(1.0F);
      Vec3d pos = this.get_interpolated_pos(mc.player, (double)event.getPartialTicks());
      BopeEventRender event_render = new BopeEventRender(TurokRenderHelp.INSTANCE, pos);
      event_render.reset_translation();
      mc.profiler.endSection();
      Iterator var4 = this.get_array_modules().iterator();

      while(var4.hasNext()) {
         BopeModule modules = (BopeModule)var4.next();
         if (modules.is_active()) {
            mc.profiler.startSection(modules.get_tag());
            modules.render(event_render);
            mc.profiler.endSection();
         }
      }

      mc.profiler.startSection("release");
      GlStateManager.glLineWidth(1.0F);
      GlStateManager.shadeModel(7424);
      GlStateManager.disableBlend();
      GlStateManager.enableAlpha();
      GlStateManager.enableTexture2D();
      GlStateManager.enableDepth();
      GlStateManager.enableCull();
      TurokRenderHelp.release_gl();
      mc.profiler.endSection();
      mc.profiler.endSection();
   }

   public void update() {
      Iterator var1 = this.get_array_modules().iterator();

      while(var1.hasNext()) {
         BopeModule modules = (BopeModule)var1.next();
         if (modules.is_active()) {
            modules.update();
         }
      }

   }

   public void render() {
      Iterator var1 = this.get_array_modules().iterator();

      while(var1.hasNext()) {
         BopeModule modules = (BopeModule)var1.next();
         if (modules.is_active()) {
            modules.render();
         }
      }

   }

   public void bind(int event_key) {
      if (event_key != 0) {
         Iterator var2 = this.get_array_modules().iterator();

         while(var2.hasNext()) {
            BopeModule modules = (BopeModule)var2.next();
            if (modules.get_bind(0) == event_key) {
               modules.toggle();
            }
         }

      }
   }

   public BopeModule get_module_with_tag(String tag) {
      BopeModule module_requested = null;
      Iterator var3 = this.get_array_modules().iterator();

      while(var3.hasNext()) {
         BopeModule module = (BopeModule)var3.next();
         if (module.get_tag().equalsIgnoreCase(tag)) {
            module_requested = module;
            break;
         }
      }

      return module_requested;
   }

   public ArrayList get_modules_with_category(BopeCategory category) {
      ArrayList module_requesteds = new ArrayList();
      Iterator var3 = this.get_array_modules().iterator();

      while(var3.hasNext()) {
         BopeModule modules = (BopeModule)var3.next();
         if (modules.get_category().equals(category)) {
            module_requesteds.add(modules);
         }
      }

      return module_requesteds;
   }

   public String get_tag() {
      return this.tag;
   }
}
