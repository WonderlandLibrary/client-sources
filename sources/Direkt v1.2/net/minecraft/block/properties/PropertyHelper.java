package net.minecraft.block.properties;

import com.google.common.base.Objects;

public abstract class PropertyHelper<T extends Comparable<T>> implements IProperty<T> {
	private final Class<T> valueClass;
	private final String name;

	protected PropertyHelper(String name, Class<T> valueClass) {
		this.valueClass = valueClass;
		this.name = name;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Class<T> getValueClass() {
		return this.valueClass;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("name", this.name).add("clazz", this.valueClass).add("values", this.getAllowedValues()).toString();
	}

	@Override
	public boolean equals(Object p_equals_1_) {
		if (this == p_equals_1_) {
			return true;
		} else if (!(p_equals_1_ instanceof PropertyHelper)) {
			return false;
		} else {
			PropertyHelper<?> propertyhelper = (PropertyHelper) p_equals_1_;
			return this.valueClass.equals(propertyhelper.valueClass) && this.name.equals(propertyhelper.name);
		}
	}

	@Override
	public int hashCode() {
		return (31 * this.valueClass.hashCode()) + this.name.hashCode();
	}
}
