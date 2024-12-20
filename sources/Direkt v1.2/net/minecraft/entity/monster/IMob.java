package net.minecraft.entity.monster;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.IAnimals;

public interface IMob extends IAnimals {
	Predicate<Entity> MOB_SELECTOR = new Predicate<Entity>() {
		@Override
		public boolean apply(@Nullable Entity p_apply_1_) {
			return p_apply_1_ instanceof IMob;
		}
	};
	Predicate<Entity> VISIBLE_MOB_SELECTOR = new Predicate<Entity>() {
		@Override
		public boolean apply(@Nullable Entity p_apply_1_) {
			return (p_apply_1_ instanceof IMob) && !p_apply_1_.isInvisible();
		}
	};
}
