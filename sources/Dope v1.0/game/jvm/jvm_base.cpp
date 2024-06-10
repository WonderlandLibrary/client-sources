#include "jvm_base.hpp"
#include "../../modules/settings.hpp"
#include "../../utilities/memory.hpp"

c_jvm_base::c_jvm_base(MODULEENTRY32 module, HANDLE handle)
{
	this->_module = module;
	this->_handle = handle;

	//this->_head = this->read_memory<uintptr_t>(this->get_address(this->get_pattern(settings->user.ptrn.data()) + settings->user.ptrnOff));
	this->_head = this->read_memory<uintptr_t>(this->get_address(this->get_pattern("48 C7 01 ? ? ? ? 48 8B 05 ? ? ? ?") + 0xA));

}

uintptr_t c_jvm_base::get_pattern(const char* pattern)
{
	return MemoryHelper::get().find_pattern(this->get_handle(), this->_module, pattern);
}

void* c_jvm_base::decodeoop(void* value)
{
	if (this->use_compressed_oops)
		return (void*)((uintptr_t)this->_narrow_oop._base + ((uintptr_t)value << this->_narrow_oop._shift));

	return (void*)value;
}

void* c_jvm_base::decodeklass(void* value)
{
	return (void*)((uintptr_t)this->_narrow_klass._base + ((uintptr_t)value << this->_narrow_klass._shift));
}

unsigned int c_jvm_base::encodeoop(unsigned int value)
{
	if (this->use_compressed_oops)
		return (unsigned int)(((uintptr_t)value - (uintptr_t)this->_narrow_klass._base) >> this->_narrow_klass._shift);

	return (unsigned int)value;
}

void* c_jvm_base::get_obj_field(void* obj, unsigned int fid)
{
	if (!obj || !fid)
		return 0x0;

	unsigned int val = this->read_memory<unsigned int>((uintptr_t)obj + fid);
	return this->decodeoop((void*)val);
}

void c_jvm_base::set_obj_field(void* obj, unsigned int fid, unsigned int v)
{
	if (!obj || !fid)
		return;

	v = encodeoop(v);
	this->write_memory<unsigned int>((uintptr_t)((uintptr_t)obj + fid), v);
}

int c_jvm_base::get_array_length(void* obj)
{
	if (!obj)
		return 0x0;

	/*
	  v41 = 0x10i64;
	  if ( UseCompressedClassPointers )
		v41 = 0xCi64;
	*/

	auto offset = this->use_compressed_class_pointers ? 0xC : 0x10; // even tho UseCompressedClassPointers is 1, 0x10 seems to be working (while 0xC has weird results)
	return this->read_memory<int>((uintptr_t)obj + offset);
}

void* c_jvm_base::get_obj_array_elem(void* obj, int index)
{
	/*
		v29 = 0x18;
		if ( UseCompressedClassPointers )
			v29 = 0x10;
	*/
	if (!obj)
		return 0x0;

	auto offset = this->use_compressed_class_pointers ? 0x10 : 0x18;
	return this->get_obj_field(obj, offset + 4 * index);  // return base_offset_in_bytes() + sizeof(T) * index;
}
