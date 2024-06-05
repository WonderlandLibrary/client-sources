package net.minecraft.util;

import com.google.common.base.Predicate;
import net.minecraft.util.EntitySelectors$1;
import net.minecraft.util.EntitySelectors$2;
import net.minecraft.util.EntitySelectors$3;
import net.minecraft.util.EntitySelectors$4;

public final class EntitySelectors {
   public static final Predicate selectAnything = new EntitySelectors$1();
   public static final Predicate IS_STANDALONE = new EntitySelectors$2();
   public static final Predicate selectInventories = new EntitySelectors$3();
   public static final Predicate NOT_SPECTATING = new EntitySelectors$4();
}
