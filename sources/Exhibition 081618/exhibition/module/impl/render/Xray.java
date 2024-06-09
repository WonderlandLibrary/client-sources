package exhibition.module.impl.render;

import com.google.common.collect.Lists;
import exhibition.event.Event;
import exhibition.event.RegisterEvent;
import exhibition.event.impl.EventTick;
import exhibition.module.Module;
import exhibition.module.data.ModuleData;
import exhibition.module.data.Setting;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class Xray extends Module {
   private static final String KEY_OPACITY = "OPACITY";
   public static final String CAVEFINDER = "CAVE";
   private static final HashSet blockIDs = new HashSet();
   private int opacity = 160;
   List KEY_IDS = Lists.newArrayList(new Integer[]{Integer.valueOf(10), Integer.valueOf(11), Integer.valueOf(8), Integer.valueOf(9), Integer.valueOf(14), Integer.valueOf(15), Integer.valueOf(16), Integer.valueOf(21), Integer.valueOf(41), Integer.valueOf(42), Integer.valueOf(46), Integer.valueOf(48), Integer.valueOf(52), Integer.valueOf(56), Integer.valueOf(57), Integer.valueOf(61), Integer.valueOf(62), Integer.valueOf(73), Integer.valueOf(74), Integer.valueOf(84), Integer.valueOf(89), Integer.valueOf(103), Integer.valueOf(116), Integer.valueOf(117), Integer.valueOf(118), Integer.valueOf(120), Integer.valueOf(129), Integer.valueOf(133), Integer.valueOf(137), Integer.valueOf(145), Integer.valueOf(152), Integer.valueOf(153), Integer.valueOf(154)});

   public Xray(ModuleData data) {
      super(data);
      this.settings.put("OPACITY", new Setting("OPACITY", Integer.valueOf(160), "Opacity for blocks you want to ignore.", 5.0D, 0.0D, 255.0D));
      this.settings.put("CAVE", new Setting("CAVE", false, "Only show blocks touching air."));
   }

   public void onEnable() {
      blockIDs.clear();
      this.opacity = ((Number)((Setting)this.settings.get("OPACITY")).getValue()).intValue();

      try {
         Iterator var1 = this.KEY_IDS.iterator();

         while(var1.hasNext()) {
            Integer o = (Integer)var1.next();
            blockIDs.add(o);
         }
      } catch (Exception var3) {
         var3.printStackTrace();
      }

      mc.renderGlobal.loadRenderers();
   }

   public void onDisable() {
      mc.renderGlobal.loadRenderers();
   }

   @RegisterEvent(
      events = {EventTick.class}
   )
   public void onEvent(Event event) {
   }

   public boolean containsID(int id) {
      return blockIDs.contains(id);
   }

   public int getOpacity() {
      return this.opacity;
   }
}
