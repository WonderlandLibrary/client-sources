#pragma once
#include "aimassist.hpp"

// for my next update 

/*class c_gamesettings {
private:
	jobject gamesettings_obj;
	JNIEnv* env;

public:
	c_gamesettings(jobject obj)
	{
		gamesettings_obj = obj;
		
	}

	float get_sens()
	{

		jclass this_class = env->GetObjectClass(gamesettings_obj);
		jfieldID fid;

		fid = env->GetFieldID(this_class,"MouseSensitivity", "F");

		env->DeleteLocalRef(this_class);

		return env->GetFloatField(gamesettings_obj, fid);
	}

	jobject get_object()
	{
		return gamesettings_obj;
	}
};*/