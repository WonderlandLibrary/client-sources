package net.minecraft.src;

import java.lang.reflect.Method;

public class ReflectorMethod {
	private ReflectorClass reflectorClass;
	private String targetMethodName;
	private Class[] targetMethodParameterTypes;
	private boolean checked;
	private Method targetMethod;

	public ReflectorMethod(ReflectorClass p_i70_1_, String p_i70_2_) {
		this(p_i70_1_, p_i70_2_, (Class[]) null, false);
	}

	public ReflectorMethod(ReflectorClass p_i71_1_, String p_i71_2_, Class[] p_i71_3_) {
		this(p_i71_1_, p_i71_2_, p_i71_3_, false);
	}

	public ReflectorMethod(ReflectorClass p_i72_1_, String p_i72_2_, Class[] p_i72_3_, boolean p_i72_4_) {
		this.reflectorClass = null;
		this.targetMethodName = null;
		this.targetMethodParameterTypes = null;
		this.checked = false;
		this.targetMethod = null;
		this.reflectorClass = p_i72_1_;
		this.targetMethodName = p_i72_2_;
		this.targetMethodParameterTypes = p_i72_3_;

		if (!p_i72_4_) {
			Method method = this.getTargetMethod();
		}
	}

	public Method getTargetMethod() {
		if (this.checked) {
			return this.targetMethod;
		} else {
			this.checked = true;
			Class oclass = this.reflectorClass.getTargetClass();

			if (oclass == null) {
				return null;
			} else {
				try {
					if (this.targetMethodParameterTypes == null) {
						Method[] amethod = Reflector.getMethods(oclass, this.targetMethodName);

						if (amethod.length <= 0) {
							Config.log("(Reflector) Method not present: " + oclass.getName() + "." + this.targetMethodName);
							return null;
						}

						if (amethod.length > 1) {
							Config.warn("(Reflector) More than one method found: " + oclass.getName() + "." + this.targetMethodName);

							for (Method method : amethod) {
								Config.warn("(Reflector)  - " + method);
							}

							return null;
						}

						this.targetMethod = amethod[0];
					} else {
						this.targetMethod = Reflector.getMethod(oclass, this.targetMethodName, this.targetMethodParameterTypes);
					}

					if (this.targetMethod == null) {
						Config.log("(Reflector) Method not present: " + oclass.getName() + "." + this.targetMethodName);
						return null;
					} else {
						this.targetMethod.setAccessible(true);
						return this.targetMethod;
					}
				} catch (Throwable throwable) {
					throwable.printStackTrace();
					return null;
				}
			}
		}
	}

	public boolean exists() {
		return this.checked ? this.targetMethod != null : this.getTargetMethod() != null;
	}

	public Class getReturnType() {
		Method method = this.getTargetMethod();
		return method == null ? null : method.getReturnType();
	}

	public void deactivate() {
		this.checked = true;
		this.targetMethod = null;
	}
}
