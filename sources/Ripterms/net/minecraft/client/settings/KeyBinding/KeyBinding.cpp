#include "KeyBinding.h"

void KeyBinding::setPressed(bool pressed)
{
	if (!instance) return;
	env->SetBooleanField(instance, KeyBindingClass.getFieldID("pressed"), (pressed ? JNI_TRUE : JNI_FALSE));
}

void KeyBinding::setPressTime(int pressTime)
{
	if (!instance) return;
	env->SetIntField(instance, KeyBindingClass.getFieldID("pressTime"), pressTime);
}
