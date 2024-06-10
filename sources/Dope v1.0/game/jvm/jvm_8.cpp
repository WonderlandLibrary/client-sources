#include "jvm_8.hpp"

c_jvm8::c_jvm8(MODULEENTRY32 module, HANDLE handle)
	:  c_jvm_base(module, handle)
{
	this->use_compressed_oops = this->read_memory<bool>(this->get_address(this->get_pattern("40 38 3D ? ? ? ? 74 20") + 0x3));
	this->use_compressed_class_pointers = this->read_memory<bool>(this->get_address(this->get_pattern("0F 85 ? ? ? ? 80 3D ? ? ? ? ? 74 16") + 0x2));
	this->_narrow_oop = this->read_memory<NarrowPtrStruct>(this->get_address(this->get_pattern("48 03 05 ? ? ? ? 49 3B 40 18") + 0x3));
	this->_narrow_klass = this->read_memory<NarrowPtrStruct>(this->get_address(this->get_pattern("48 03 15 ? ? ? ? EB 04 48 8B 56 08 48 8B 07 48 8B CF") + 0x3));
}

void c_jvm8::load_classes()
{
	std::vector<void*> ret;

	void* cld = (void*)this->_head;
	while (cld != NULL)
	{
		void* k = this->read_memory<void*>((uintptr_t)cld + 0x28);
		while (k != NULL) {
			ret.push_back(k);
			k = this->read_memory<void*>((uintptr_t)k + 0x88);
		}

		cld = this->read_memory<void*>((uintptr_t)cld + 0x48);
	}

	this->classes = ret;
}

void* c_jvm8::find_class(const char* name)
{
	for (const auto& k : this->classes)
	{
		void* symbol = this->read_memory<void*>(((uintptr_t)k + 0x10));
		unsigned short symbol_length = this->read_memory<unsigned short>(((uintptr_t)symbol));

		char clazz_name[256] = { 0 };
		ZwReadVirtualMemory(this->_handle, (void*)((uintptr_t)symbol + 0x8), (PVOID)clazz_name, symbol_length, NULL);

		if (strcmp(name, clazz_name) == 0)
			return k;
	}

	return NULL;
}

unsigned short c_jvm8::get_field(void* klass, const char* name, const char* sig)
{
	if (!klass)
		return NULL;

	void* fields = this->read_memory<void*>((uintptr_t)klass + 0x1B0);
	if (!fields)
		return NULL;

	void* klass_constants = this->read_memory<void*>((uintptr_t)klass + 0xD8);
	if (!klass_constants)
		return NULL;

	unsigned short _java_fields_count = this->read_memory<unsigned short>((uintptr_t)klass + 0x106);
	fields = (void*)((uintptr_t)fields + 0x4); // 0x1C0 + 4 (first bytes are the rray length
	for (int i = 0; i < _java_fields_count; i++)
	{
		void* field = (void*)((uintptr_t)fields + i * 6 * 2);
		if (!field)
			continue;

		unsigned short name_index = this->read_memory<unsigned short>((uintptr_t)field + 0x2);
		unsigned short sig_index = this->read_memory<unsigned short>((uintptr_t)field + 0x4);

		void* name_sym = this->read_memory<void*>((uintptr_t)klass_constants + 0x50 + name_index * 8);
		void* sig_sym = this->read_memory<void*>((uintptr_t)klass_constants + 0x50 + sig_index * 8);
		if (!name_sym || !sig_sym)
			continue;

		unsigned short name_length = this->read_memory<unsigned short>(((uintptr_t)name_sym));
		if (name_length < 0 || name_length > 256)
			continue;

		char field_name[256] = { 0 };
		ZwReadVirtualMemory(this->_handle, (void*)((uintptr_t)name_sym + 0x8), (PVOID)field_name, name_length, NULL);
		if (strcmp(field_name, name) != 0)
			continue;

		unsigned short sig_length = this->read_memory<unsigned short>(((uintptr_t)sig_sym));
		if (sig_length < 0 || sig_length > 256)
			continue;

		char field_sig[256] = { 0 };
		ZwReadVirtualMemory(this->_handle, (void*)((uintptr_t)sig_sym + 0x8), (PVOID)field_sig, sig_length, NULL);
		if (strcmp(field_sig, sig))
			continue;

		unsigned short low_packed_offset = this->read_memory<unsigned short>(((uintptr_t)field + 0x8));
		unsigned short high_packed_offset = this->read_memory<unsigned short>(((uintptr_t)field + 0xA));

		return build_int_from_shorts(low_packed_offset, high_packed_offset) >> 2;
	}

	return NULL;
}

unsigned short c_jvm8::get_interface_field(void* klass, const char* name, const char* sig)
{
	void* _local_interfaces = this->read_memory<void*>((uintptr_t)klass + 0x190);
	if (!_local_interfaces)
		return NULL;

	unsigned short fid = NULL;

	int length = this->read_memory<int>((uintptr_t)_local_interfaces);
	_local_interfaces = (void*)((uintptr_t)_local_interfaces + 0x8);
	for (int i = 0; i < length; i++) {
		void* k = this->read_memory<void*>((uintptr_t)_local_interfaces + i * 8);

		if (fid = get_field(k, name, sig))
			return fid;

		if (fid = get_interface_field(k, name, sig))
			return fid;
	}

	return NULL;
}

unsigned short c_jvm8::get_field_id(void* klass, const char* name, const char* sig)
{
	unsigned short fid;

	if (!(fid = get_field(klass, name, sig))) {
		if (!(fid = get_interface_field(klass, name, sig))) {
			auto super = this->read_memory<void*>((uintptr_t)klass + 0x70);
			if (super) {
				fid = get_field(super, name, sig);
			}
		}
	}

	return fid;
}

bool c_jvm8::is_instance_of(void* clazz, void* obj)
{
	if (clazz == NULL || obj == NULL)
		return false;

	void* obj_clazz = this->get_object_class(obj);
	unsigned int _super_check_offset = this->read_memory<unsigned int>((uintptr_t)obj_clazz + 0xC);
	void* super = this->read_memory<void*>((uintptr_t)obj_clazz + _super_check_offset);

	if (super == clazz || obj_clazz == clazz)
		return true;

	void* _secondary_supers = this->read_memory<void*>((uintptr_t)obj_clazz + 0x20);
	int supers_count = this->read_memory<int>((uintptr_t)_secondary_supers);
	_secondary_supers = (void*)((uintptr_t)_secondary_supers + 0x8);
	for (int i = 0; i < supers_count; i++) {
		super = this->read_memory<void*>((uintptr_t)_secondary_supers + i * 8);
		if (super == clazz)
			return true;
	}

	return false;
}

void* c_jvm8::get_object_class(void* obj)
{
	void* klass = NULL;
	if (!obj)
		return klass;

	if (!this->use_compressed_class_pointers)
	{
		klass = this->read_memory<void*>((uintptr_t)klass + 0x8);
	}
	else {
		unsigned int val = this->read_memory<unsigned int>((uintptr_t)obj + 0x8);
		klass = this->decodeklass((void*)val);
	}

	return klass;
}

void* c_jvm8::get_static_obj_field(void* klass, unsigned int fid)
{
	if (!klass || !fid)
		return 0x0;

	void* _java_mirror = this->read_memory<void*>((uintptr_t)klass + 0x68);
	unsigned int val = this->read_memory<unsigned int>((uintptr_t)_java_mirror + fid);
	return this->decodeoop((void*)val);
}
