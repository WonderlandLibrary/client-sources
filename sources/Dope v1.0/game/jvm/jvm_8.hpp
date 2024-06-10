#pragma once
#include "jvm_base.hpp"

class c_jvm8 : public c_jvm_base {
public:
	c_jvm8(MODULEENTRY32 module, HANDLE handle);

	virtual void load_classes() override;

	virtual void* find_class(const char* name) override;
	
	virtual unsigned short get_field(void* klass, const char* name, const char* sig) override;
	
	virtual unsigned short get_interface_field(void* klass, const char* name, const char* sig) override;
	
	virtual unsigned short get_field_id(void* klass, const char* name, const char* sig) override;

	virtual bool is_instance_of(void* clazz, void* obj) override;

	virtual void* get_object_class(void* obj) override;

	virtual void* get_static_obj_field(void* klass, unsigned int fid) override;
};