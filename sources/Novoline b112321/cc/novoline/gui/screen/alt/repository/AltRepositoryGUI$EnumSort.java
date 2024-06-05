package cc.novoline.gui.screen.alt.repository;

import cc.novoline.gui.screen.alt.repository.Alt;
import java.util.Comparator;
import net.CI;

enum AltRepositoryGUI$EnumSort {
   DATE("Date", AltRepositoryGUI$EnumSort::lambda$static$0),
   LEVEL("Level", AltRepositoryGUI$EnumSort::lambda$static$1),
   NAME("Name", AltRepositoryGUI$EnumSort::lambda$static$2),
   EMAIL("Email", AltRepositoryGUI$EnumSort::lambda$static$3);

   private final String criteria;
   private final Comparator comparator;
   private static final AltRepositoryGUI$EnumSort[] $VALUES = new AltRepositoryGUI$EnumSort[]{DATE, LEVEL, NAME, EMAIL};

   private AltRepositoryGUI$EnumSort(String var3, Comparator var4) {
      this.criteria = var3;
      this.comparator = var4;
   }

   public String getCriteria() {
      return "By " + this.criteria;
   }

   public Comparator getComparator() {
      return this.comparator;
   }

   private static int lambda$static$3(Alt var0, Alt var1) {
      return var0.getCredential().getLogin().compareTo(var1.getCredential().getLogin());
   }

   private static int lambda$static$2(Alt var0, Alt var1) {
      return var0.getPlayer().getGameProfile().getName().compareTo(var1.getPlayer().getGameProfile().getName());
   }

   private static int lambda$static$1(Alt var0, Alt var1) {
      int[] var2 = CI.b();
      return var0.getHypixelProfile() == null?(var1.getHypixelProfile() == null?0:1):(var1.getHypixelProfile() == null?-1:Integer.compare(var1.getHypixelProfile().getLevel(), var0.getHypixelProfile().getLevel()));
   }

   private static int lambda$static$0(Alt var0, Alt var1) {
      return 0;
   }
}
