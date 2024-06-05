package cc.novoline.modules.visual;

import cc.novoline.Novoline;
import cc.novoline.events.EventTarget;
import cc.novoline.events.events.Render2DEvent;
import cc.novoline.events.events.Render3DEvent;
import cc.novoline.events.events.RenderNameTagEvent;
import cc.novoline.gui.screen.setting.Manager;
import cc.novoline.gui.screen.setting.Setting;
import cc.novoline.gui.screen.setting.SettingType;
import cc.novoline.modules.AbstractModule;
import cc.novoline.modules.EnumModuleType;
import cc.novoline.modules.ModuleManager;
import cc.novoline.modules.PlayerManager$EnumPlayerType;
import cc.novoline.modules.configurations.annotation.Property;
import cc.novoline.modules.configurations.property.object.BooleanProperty;
import cc.novoline.modules.configurations.property.object.IntProperty;
import cc.novoline.modules.configurations.property.object.ListProperty;
import cc.novoline.modules.configurations.property.object.PropertyFactory;
import cc.novoline.modules.configurations.property.object.StringProperty;
import cc.novoline.modules.player.Freecam;
import cc.novoline.modules.visual.HUD;
import cc.novoline.modules.visual.Nametags$Player;
import cc.novoline.modules.visual.PlayerESP;
import cc.novoline.utils.Timer;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;

public final class Nametags extends AbstractModule {
   private final List validEntities = new CopyOnWriteArrayList();
   @Property("render-distance")
   private final IntProperty renderDistance = (IntProperty)((IntProperty)PropertyFactory.createInt(Integer.valueOf(192)).minimum(Integer.valueOf(4))).maximum(Integer.valueOf(256));
   @Property("tag-font")
   private final StringProperty tagFont = PropertyFactory.createString("Client").acceptableValues(new String[]{"Client", "Vanilla"});
   @Property("content")
   private final ListProperty content = PropertyFactory.createList((Object)"Distance").acceptableValues((Object[])(new String[]{"Distance", "Armor"}));
   @Property("additions")
   private final ListProperty additions = PropertyFactory.createList((Object)"Background").acceptableValues((Object[])(new String[]{"Background", "Health"}));
   @Property("health-type")
   private final StringProperty healthType = PropertyFactory.createString("Bar").acceptableValues(new String[]{"Bar", "Value"});
   @Property("background-alpha")
   private final IntProperty backgroundAlpha = (IntProperty)((IntProperty)PropertyFactory.createInt(Integer.valueOf(100)).minimum(Integer.valueOf(50))).maximum(Integer.valueOf(255));
   @Property("only-targets")
   private final BooleanProperty onlyTargets = PropertyFactory.booleanFalse();
   private Timer tpTimer = new Timer();

   public Nametags(ModuleManager var1) {
      super(var1, "Nametags", "Nametags", 0, EnumModuleType.VISUALS);
      Manager.put(new Setting("NAMETAGS_FONT", "Nametags Font", SettingType.COMBOBOX, this, this.tagFont));
      Manager.put(new Setting("NAMETAGS_RENDER_DIST", "Render distance", SettingType.SLIDER, this, this.renderDistance, 4.0D));
      Manager.put(new Setting("NAMETAGS_CONTENT", "Content", SettingType.SELECTBOX, this, this.content));
      Manager.put(new Setting("NAMETAGS_ADDITIONS", "Additions", SettingType.SELECTBOX, this, this.additions));
      Manager.put(new Setting("NAMETAGS_BG_ALPHA", "Background Alpha", SettingType.SLIDER, this, this.backgroundAlpha, 5.0D, this::lambda$new$0));
      Manager.put(new Setting("NAMETAGS_HEALTH", "Health Type", SettingType.COMBOBOX, this, this.healthType, this::lambda$new$1));
      Manager.put(new Setting("ONLY_TAR", "Only targets", SettingType.CHECKBOX, this, this.onlyTargets));
   }

   public void onDisable() {
      this.validEntities.clear();
   }

   private Nametags$Player getPlayerByEntity(EntityLivingBase var1) {
      return (Nametags$Player)this.validEntities.stream().filter(Nametags::lambda$getPlayerByEntity$2).findFirst().orElse((Object)null);
   }

   @EventTarget
   public void onNameTagRender(RenderNameTagEvent var1) {
      int var2 = HUD.e();
      if(var1.getEntity() != ((Freecam)this.getModule(Freecam.class)).getFreecamEntity()) {
         var1.setCancelled(this.novoline.getPlayerManager().getType(var1.getEntity().getName()) == PlayerManager$EnumPlayerType.TARGET || this.mc.player.getDistanceToEntity(var1.getEntity()) <= (float)((Integer)this.renderDistance.get()).intValue() && !((Boolean)this.onlyTargets.get()).booleanValue() && !(var1.getEntity() instanceof EntityArmorStand) || ((PlayerESP)this.getModule(PlayerESP.class)).isEnabled() && ((List)((PlayerESP)this.getModule(PlayerESP.class)).getFilter().get()).contains("Name"));
      }
   }

   @EventTarget
   public void onRender2D(Render2DEvent var1) {
      this.validEntities.forEach(Nametags$Player::render);
   }

   @EventTarget
   private void onRender(Render3DEvent var1) {
      Stream var10000 = this.mc.world.getLoadedEntityList().stream();
      EntityPlayer.class.getClass();
      var10000 = var10000.filter(EntityPlayer.class::isInstance).filter(Nametags::lambda$onRender$3).filter(Entity::isEntityAlive);
      EntityLivingBase.class.getClass();
      var10000.map(EntityLivingBase.class::cast).filter(this::lambda$onRender$4).forEach(this::lambda$onRender$5);
      this.validEntities.forEach(this::lambda$onRender$6);
   }

   public List getValidEntities() {
      return this.validEntities;
   }

   public IntProperty getRenderDistance() {
      return this.renderDistance;
   }

   public ListProperty getContent() {
      return this.content;
   }

   public ListProperty getAdditions() {
      return this.additions;
   }

   public StringProperty getHealthType() {
      return this.healthType;
   }

   public IntProperty getBackgroundAlpha() {
      return this.backgroundAlpha;
   }

   public BooleanProperty getOnlyTargets() {
      return this.onlyTargets;
   }

   private void lambda$onRender$6(Render3DEvent var1, Nametags$Player var2) {
      int var3 = HUD.h();
      if(!Nametags$Player.access$500(var2).isEntityAlive() || this.mc.player.getDistanceToEntity(Nametags$Player.access$500(var2)) > (float)((Integer)this.renderDistance.get()).intValue() && Novoline.getInstance().getPlayerManager().getType(Nametags$Player.access$500(var2).getName()) != PlayerManager$EnumPlayerType.TARGET) {
         this.validEntities.remove(var2);
      }

      if(!this.mc.world.getLoadedEntityList().contains(Nametags$Player.access$500(var2)) || ((Boolean)this.onlyTargets.get()).booleanValue() && Novoline.getInstance().getPlayerManager().getType(Nametags$Player.access$500(var2).getName()) != PlayerManager$EnumPlayerType.TARGET || Nametags$Player.access$500(var2).getEntityID() == this.mc.player.getEntityID() || Nametags$Player.access$500(var2).getDisplayName().getFormattedText().contains("NPC") || Nametags$Player.access$500(var2).getDisplayName().getUnformattedText().equalsIgnoreCase(Nametags$Player.access$500(var2).getName())) {
         this.validEntities.remove(var2);
      }

      float var4 = (float)(Nametags$Player.access$500(var2).lastTickPosX + (Nametags$Player.access$500(var2).posX - Nametags$Player.access$500(var2).lastTickPosX) * (double)var1.getPartialTicks() - this.mc.getRenderManager().renderPosX);
      float var5 = (float)(Nametags$Player.access$500(var2).lastTickPosY + 2.3D + (Nametags$Player.access$500(var2).posY + 2.3D - (Nametags$Player.access$500(var2).lastTickPosY + 2.3D)) * (double)var1.getPartialTicks() - this.mc.getRenderManager().renderPosY);
      float var6 = (float)(Nametags$Player.access$500(var2).lastTickPosZ + (Nametags$Player.access$500(var2).posZ - Nametags$Player.access$500(var2).lastTickPosZ) * (double)var1.getPartialTicks() - this.mc.getRenderManager().renderPosZ);
      Nametags$Player.access$602(var2, Nametags$Player.access$700(var2, (double)var4, (double)var5, (double)var6));
   }

   private void lambda$onRender$5(EntityLivingBase var1) {
      this.validEntities.add(new Nametags$Player(this, var1));
   }

   private boolean lambda$onRender$4(EntityLivingBase var1) {
      int var2 = HUD.h();
      return !this.validEntities.contains(this.getPlayerByEntity(var1));
   }

   private static boolean lambda$onRender$3(Entity var0) {
      int var1 = HUD.e();
      return !var0.isInvisible();
   }

   private static boolean lambda$getPlayerByEntity$2(EntityLivingBase var0, Nametags$Player var1) {
      return Nametags$Player.access$500(var1).equals(var0);
   }

   private Boolean lambda$new$1() {
      return Boolean.valueOf(((List)this.additions.get()).contains("Health"));
   }

   private Boolean lambda$new$0() {
      return Boolean.valueOf(((List)this.additions.get()).contains("Background"));
   }

   static Minecraft access$100(Nametags var0) {
      return var0.mc;
   }

   static StringProperty access$200(Nametags var0) {
      return var0.tagFont;
   }

   static Minecraft access$300(Nametags var0) {
      return var0.mc;
   }

   static Minecraft access$400(Nametags var0) {
      return var0.mc;
   }
}
