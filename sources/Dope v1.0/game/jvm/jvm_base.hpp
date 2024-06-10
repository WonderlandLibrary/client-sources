#pragma once
#include "../../common.hpp"

class c_jvm_base {
protected:
	MODULEENTRY32 _module;
	HANDLE _handle;
public:
	std::vector<void*> classes;

	uintptr_t _head;
	bool use_compressed_oops;
	bool use_compressed_class_pointers;
	NarrowPtrStruct _narrow_oop;
	NarrowPtrStruct _narrow_klass;

	c_jvm_base(MODULEENTRY32 module, HANDLE handle);

	~c_jvm_base()
	{
		if (this->_handle)
			CloseHandle(this->_handle);
	}

	inline HANDLE get_handle() {
		return this->_handle;
	}

	inline uintptr_t get_base() {
		return (uintptr_t)this->_module.modBaseAddr;
	}

	uintptr_t get_address(uintptr_t addr) {
		return this->read_memory<int>(addr) + addr + 4;
	}

	uintptr_t get_pattern(const char* pattern);

	template <typename T>
	T read_memory(uintptr_t src, ULONG size = sizeof(T)) {
		T ret;
		if (!ReadProcessMemory(this->_handle, (void*)src, &ret, size, NULL))
			return (T)0x0;
		//ZwReadVirtualMemory(this->_handle, (void*)src, &ret, size, NULL);
		return ret;
	}

	template <typename T>
	void write_memory(uintptr_t addy, T buffer) {
		/*temporary workaround, its kinda bad but seems like the way the JVM 16 free oop is DOGSHIT AND HELLA WEIRD*/

		T test_value;
		if (!ReadProcessMemory(this->_handle, (void*)addy, &test_value, sizeof(T), NULL)) {
			return;
		}

		if (!test_value) {
			return;
		}

		ZwWriteVirtualMemory(this->_handle, (LPVOID)addy, &buffer, sizeof(T), NULL);
		//ZwWriteVirtualMemory(this->_handle, (LPVOID)addy, &buffer, sizeof(T), NULL);
	}

	virtual void load_classes() = 0;

	virtual void* find_class(const char* name) = 0;

	virtual unsigned short get_field(void* klass, const char* name, const char* sig) = 0;

	virtual unsigned short get_interface_field(void* klass, const char* name, const char* sig) = 0;

	virtual unsigned short get_field_id(void* klass, const char* name, const char* sig) = 0;

	virtual bool is_instance_of(void* clazz, void* obj) = 0;

	virtual void* get_object_class(void* obj) = 0;

	virtual void* get_static_obj_field(void* klass, unsigned int fid) = 0;

	void* decodeoop(void* value);

	void* decodeklass(void* value);

	unsigned int encodeoop(unsigned int value);
	
	void* get_obj_field(void* obj, unsigned int fid);

	void set_obj_field(void* obj, unsigned int fid, unsigned int v);

	template <typename T>
	T get_static_data_field(void* klass, unsigned int fid) {
		if (!klass || !fid)
			return 0x0;

		return this->read_memory<T>((uintptr_t)klass + fid);
	}

	template <typename T>
	T get_data_field(void* obj, unsigned int fid) {
		if (!obj || !fid)
			return 0x0;

		return this->read_memory<T>((uintptr_t)obj + fid);
	}

	template <typename T>
	void set_data_field(void* obj, unsigned int fid, T v) {
		if (!obj || !fid)
			return;

		this->write_memory<T>((uintptr_t)obj + fid, v);
	}

	int get_array_length(void* obj);

	void* get_obj_array_elem(void* obj, int index);
};