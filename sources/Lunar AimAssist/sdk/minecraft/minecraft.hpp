#pragma once

#include <memory>
#include <string>
#include <unordered_map>

#include <jni.h>

namespace sdk
{
	class c_minecraft
	{
	private:
		JNIEnv* env;
		std::unordered_map<std::string, jclass> klasses;

	public:
		c_minecraft(JNIEnv* env, std::unordered_map<std::string, jclass> klasses);
		~c_minecraft();

		jclass findClass(std::string klassName) { return klasses[klassName]; }

		jclass klass();
		jobject getMinecraft();
		jobject getPlayer();
		jobject getWorld();
	};

	extern std::unique_ptr<c_minecraft> instance;
}