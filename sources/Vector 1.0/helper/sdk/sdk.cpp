
#include "sdk.hpp"

auto sdk::memory::get_p_env() -> _PEB*
{
	return ((_TEB*)__readgsqword(offsetof(_NT_TIB, Self)))->ProcessEnvironmentBlock /*current process enviroment block pointer*/;
}

auto sdk::memory::get_ntdll() -> unsigned __int64
{
	static const auto get_ntdll_dll = []() -> unsigned __int64
	{
		auto p = sdk::memory::get_p_env();

		auto base = (void*)nullptr;

		for (auto entry = p->Ldr->InMemoryOrderModuleList; entry.Flink != &p->Ldr->InMemoryOrderModuleList; entry = *entry.Flink)
		{
			auto this_record = (_LDR_DATA_TABLE_ENTRY*)CONTAINING_RECORD(entry.Flink, _LDR_DATA_TABLE_ENTRY, InMemoryOrderLinks);

			if (this_record->DllBase > base)
				base = this_record->DllBase;
		}

		return (unsigned __int64)base /*the last loaded module in memory is ntdll -> we get biggest base address from the list*/;
	};

	static const auto ntdll_dll = get_ntdll_dll();

	return ntdll_dll;
}

auto sdk::memory::get_library_base(const char* mod_name) -> unsigned __int64
{
	auto p = sdk::memory::get_p_env();

	if (mod_name != nullptr)
	{
		unsigned __int64 name_length = 1;

		for (auto base = (unsigned __int64)&mod_name[0]; *(char*)base != 0; ++name_length, ++base /*calculate the name length*/);

		for (auto entry = p->Ldr->InMemoryOrderModuleList; entry.Flink != &p->Ldr->InMemoryOrderModuleList; entry = *entry.Flink)
		{
			auto this_record = (_LDR_DATA_TABLE_ENTRY*)CONTAINING_RECORD(entry.Flink, _LDR_DATA_TABLE_ENTRY, InMemoryOrderLinks);

			unsigned __int64 of = 0;

			for (unsigned __int64 base =
				(unsigned __int64)&this_record->FullDllName.Buffer[0] + 2, x = 2; *(char*)base != 0; base += 2, x += 2 /*paths*/)
			{
				if (*(char*)base == '\\' /*find the offset of the last separator in order to get the mod name without the path*/)
					of = x;
			}

			for (unsigned __int64 base =
				(unsigned __int64)&this_record->FullDllName.Buffer[0] + of + 2, x = 0; x < name_length; ++x, base += 2 /*names*/)
			{
				static const auto to_upper = [](char x) -> char
				{
					char upper = x;

					if (x >= 'a' && x <= 'z')
						upper -= 32;

					return upper;
				};

				if (to_upper(*(char*)(mod_name + x)) != to_upper(*(char*)base) /*verify if the current name matches the passed*/)
					break;

				if (x == name_length - 1 && *(char*)base == 0)
				{
					return (unsigned __int64)this_record->DllBase;
				}
			}
		}
	}

	return 0;
}

auto sdk::memory::get_proc_address(unsigned __int64 mod, const char* exp_name) -> unsigned __int64
{
	if (mod != 0)
	{
		unsigned __int64 name_length = 1;

		for (auto base = (unsigned __int64)&exp_name[0]; *(char*)base != 0; ++name_length, ++base /*calculate the name length*/);

		auto dos_header = (_IMAGE_DOS_HEADER*)mod;

		auto nt_headers = (_IMAGE_NT_HEADERS64*)((unsigned char*)dos_header + dos_header->e_lfanew);

		if (nt_headers->Signature != IMAGE_NT_SIGNATURE)
			return 0;

		auto export_directory = (_IMAGE_EXPORT_DIRECTORY*)((unsigned char*)
			dos_header + nt_headers->OptionalHeader.DataDirectory[IMAGE_DIRECTORY_ENTRY_EXPORT].VirtualAddress /*ex directory*/);

		auto offsets = (unsigned __int32*)((unsigned char*)dos_header + export_directory->AddressOfFunctions);

		auto names = (unsigned __int32*)((unsigned char*)dos_header + export_directory->AddressOfNames);

		auto ordinals = (unsigned __int16*)((unsigned char*)dos_header + export_directory->AddressOfNameOrdinals);

		for (unsigned __int32 x = 0; x < export_directory->NumberOfFunctions; ++x)
		{
			for (unsigned __int64 y = 0; y < name_length; ++y)
			{
				if (*(char*)(exp_name + y) != *(char*)((unsigned char*)dos_header + names[x] + y))
					break;

				if (y == name_length - 1 && *(char*)((unsigned char*)dos_header + names[x] + y) == 0)
				{
					return mod + offsets[ordinals[x]] /*return the function address using the module base address and ordinals*/;
				}
			}
		}
	}

	return 0;
}

auto sdk::memory::verify_integrity(unsigned __int64 address, unsigned __int64 size) -> __int64
{
    static unsigned char black_list[]
    {
        0xEB, 0xE9, 0xFF, 0xEA, 0xFF, 0x90
    };

    for (auto x = address; x < address + size; ++x)
    {
        for (const auto& item : black_list)
        {
            if (item == *(unsigned char*)x)
                return -1;
        }
    }

    return 0;
}

auto sdk::memory::nt_create_thread_ex(/*NtCreateThreadEx can be used on the current process to generate debugger hidden threads*/
    void** thread,
    unsigned long access,
    void* attribs,
    void* process,
    void* routine,
    void* args,
    unsigned long create_flags,
    unsigned __int64 zero_bits,
    unsigned __int64 stack_size,
    unsigned __int64 stack,
    void* attribs_list
) -> long
{
    static const auto proc_address = sdk::memory::get_proc_address(sdk::memory::get_ntdll(/*safe syscall, unregistered import*/),
        "NtCreateThreadEx");

    if (sdk::memory::verify_integrity(proc_address, 24) != 0)
    {
        FORCE_CRASH;
    }

    return ((long(_stdcall*)(void**, unsigned long,
        void*, void*, void*, void*, unsigned long, unsigned __int64, unsigned __int64, unsigned __int64, void*))
        proc_address)(thread, access, attribs, process, routine, args, create_flags, zero_bits, stack_size, stack, attribs_list);
}

auto sdk::memory::nt_allocate_virtual_memory(
    void* handle,
    void** address,
    unsigned __int64* zero_bits,
    unsigned __int64* size,
    unsigned __int64 type,
    unsigned __int64 protection
) -> long
{
    static const auto proc_address = sdk::memory::get_proc_address(sdk::memory::get_ntdll(/*safe syscall, unregistered import*/),
        "NtAllocateVirtualMemory");

    if (sdk::memory::verify_integrity(proc_address, 24) != 0)
    {
        FORCE_CRASH;
    }

    return ((long(_stdcall*)(void*, void**, unsigned __int64*, unsigned __int64*, unsigned __int64, unsigned __int64))
        proc_address)(handle, address, zero_bits, size, type, protection);
}

auto sdk::memory::nt_free_virtual_memory(
    void* handle,
    void** address,
    unsigned __int64* size,
    unsigned __int64 type
) -> long
{
    static const auto proc_address = sdk::memory::get_proc_address(sdk::memory::get_ntdll(/*safe syscall, unregistered import*/),
        "NtFreeVirtualMemory");

    if (sdk::memory::verify_integrity(proc_address, 24) != 0)
    {
        FORCE_CRASH;
    }

    return ((long(_stdcall*)(void*, void**, unsigned __int64*, unsigned __int64))
        proc_address)(handle, address, size, type);
}

auto sdk::memory::nt_protect_virtual_memory(
    void* handle,
    void* address,
    unsigned __int64 size,
    unsigned __int64 protection,
    unsigned __int64* old_protection
) -> long
{
    static const auto proc_address = sdk::memory::get_proc_address(sdk::memory::get_ntdll(/*safe syscall, unregistered import*/),
        "NtProtectVirtualMemory");

    if (sdk::memory::verify_integrity(proc_address, 24) != 0)
    {
        FORCE_CRASH;
    }

    return ((long(_stdcall*)(void*, void**, unsigned __int64*, unsigned __int64, unsigned __int64*))
        proc_address)(handle, &address, &size, protection, old_protection);
}

auto sdk::memory::nt_query_virtual_memory(
    void* handle,
    void* address,
    unsigned char mode,
    void* mbi,
    unsigned __int64 size,
    unsigned __int64* bytes_read
) -> long
{
    static const auto proc_address = sdk::memory::get_proc_address(sdk::memory::get_ntdll(/*safe syscall, unregistered import*/),
        "NtQueryVirtualMemory");

    if (sdk::memory::verify_integrity(proc_address, 24) != 0)
    {
        FORCE_CRASH;
    }

    return ((long(_stdcall*)(void*, void*, unsigned char, void*, unsigned __int64, unsigned __int64*))
        proc_address)(handle, address, mode, mbi, size, bytes_read);
}

auto sdk::memory::nt_read_virtual_memory(
    void* handle,
    void* address,
    void* buffer,
    unsigned __int64 size,
    unsigned __int64* bytes_read
) -> long
{
    static const auto proc_address = sdk::memory::get_proc_address(sdk::memory::get_ntdll(/*safe syscall, unregistered import*/),
        "NtReadVirtualMemory");

    if (sdk::memory::verify_integrity(proc_address, 24) != 0)
    {
        FORCE_CRASH;
    }

    return ((long(_stdcall*)(void*, void*, void*, unsigned __int64, unsigned __int64*))
        proc_address)(handle, address, buffer, size, bytes_read);
}

auto sdk::memory::nt_write_virtual_memory(
    void* handle,
    void* address,
    void* buffer,
    unsigned __int64 size,
    unsigned __int64* bytes_written
) -> long
{
    static const auto proc_address = sdk::memory::get_proc_address(sdk::memory::get_ntdll(/*safe syscall, unregistered import*/),
        "NtWriteVirtualMemory");

    if (sdk::memory::verify_integrity(proc_address, 24) != 0)
    {
        FORCE_CRASH;
    }

    return ((long(_stdcall*)(void*, void*, void*, unsigned __int64, unsigned __int64*))
        proc_address)(handle, address, buffer, size, bytes_written);
}

auto sdk::memory::nt_open_process(
    void** handle,
    unsigned long access,
    unsigned long pid
) -> long
{
    static const auto proc_address = sdk::memory::get_proc_address(sdk::memory::get_ntdll(/*safe syscall, unregistered import*/),
        "NtOpenProcess");

    if (sdk::memory::verify_integrity(proc_address, 24) != 0)
    {
        FORCE_CRASH;
    }

    _OBJECT_ATTRIBUTES object_attributes
    {
        sizeof(_OBJECT_ATTRIBUTES)
    };

    _CLIENT_ID client_id
    {
        (void*)pid
    };

    return ((long(_stdcall*)(void**, unsigned long, _OBJECT_ATTRIBUTES*, _CLIENT_ID*))
        proc_address)(handle, access, &object_attributes, &client_id);
}

auto sdk::memory::load_pe_file_ex(void* handle, std::vector<unsigned char>& buffer /*map a passed dll buffer into a process*/) -> sdk::memory::module_ex
{
    *(unsigned __int64*)&buffer[0x0] = sdk::memory::get_proc_address(sdk::memory::get_library_base("KERNEL32.dll"), "GetModuleHandleA");
    *(unsigned __int64*)&buffer[0x8] = sdk::memory::get_proc_address(sdk::memory::get_library_base("KERNEL32.dll"), "GetProcAddress");

    auto nt_headers = (_IMAGE_NT_HEADERS64*)((unsigned char*)&buffer[0] + ((_IMAGE_DOS_HEADER*)&buffer[0])->e_lfanew /*get the passed dll nt headers*/);

    unsigned __int64 file_size_of_image = nt_headers->OptionalHeader.SizeOfImage;

    void* allocation = 0;

    if (sdk::memory::nt_allocate_virtual_memory(handle, &allocation, 0, &file_size_of_image, MEM_COMMIT, PAGE_EXECUTE_READWRITE) != 0 /*create region*/)
    {
        buffer.clear();

        return sdk::memory::module_ex{ 0, nullptr, 0 };
    }

    if (sdk::memory::nt_write_virtual_memory(handle, (void*)allocation, (void*)&buffer[0], 4096, nullptr) != 0 /*write the pe header in the page base*/)
    {
        sdk::memory::nt_free_virtual_memory(handle, &allocation, &file_size_of_image, MEM_RELEASE);

        buffer.clear();

        return sdk::memory::module_ex{ 0, nullptr, 1 };
    }

    auto sec = IMAGE_FIRST_SECTION(nt_headers);

    for (unsigned char x = 0; x < nt_headers->FileHeader.NumberOfSections && sec->SizeOfRawData != 0; ++x, ++sec)
    {
        if (sdk::memory::nt_write_virtual_memory(handle,
            (void*)((unsigned __int64)allocation + sec->VirtualAddress), &buffer[0] + sec->PointerToRawData, sec->SizeOfRawData, 0) != 0 /*write secs*/)
        {
            sdk::memory::nt_free_virtual_memory(handle, &allocation, &file_size_of_image, MEM_RELEASE);

            buffer.clear();

            return sdk::memory::module_ex{ 0, nullptr, 2 };
        }
    }

    buffer.clear();

    void* shell_code = 0;

    if (sdk::memory::nt_allocate_virtual_memory(handle, &shell_code, 0, &file_size_of_image, MEM_COMMIT, PAGE_EXECUTE_READWRITE) != 0 /*create region*/)
    {
        sdk::memory::nt_free_virtual_memory(handle, &allocation, &file_size_of_image, MEM_RELEASE);

        return sdk::memory::module_ex{ 0, nullptr, 3 };
    }

    if (sdk::memory::nt_write_virtual_memory(handle, (void*)shell_code, sdk::memory::load_pe_file_shell, 4096, nullptr) != 0 /*write loader shellcode*/)
    {
        sdk::memory::nt_free_virtual_memory(handle, &allocation, &file_size_of_image, MEM_RELEASE);
        sdk::memory::nt_free_virtual_memory(handle, &shell_code, &file_size_of_image, MEM_RELEASE);

        return sdk::memory::module_ex{ 0, nullptr, 4 };
    }

    void* thread = nullptr /*this thread will be hidden*/;

    if (sdk::memory::nt_create_thread_ex(
        &thread,
        (STANDARD_RIGHTS_REQUIRED | SYNCHRONIZE | 0xffff),
        nullptr,
        handle /*handle to other process*/,
        (LPTHREAD_START_ROUTINE)shell_code,
        (void*)allocation /*pointer to the image buffer*/,
        4 /*4 -> THREAD_CREATE_FLAGS_HIDE_FROM_DEBUGGER*/,
        0,
        0,
        0,
        nullptr) != 0)
    {
        sdk::memory::nt_free_virtual_memory(handle, &allocation, &file_size_of_image, MEM_RELEASE);
        sdk::memory::nt_free_virtual_memory(handle, &shell_code, &file_size_of_image, MEM_RELEASE);

        return sdk::memory::module_ex{ 0, nullptr, 5 };
    }

    Sleep(1000);

    sdk::memory::nt_free_virtual_memory(handle, &shell_code, &file_size_of_image, MEM_RELEASE /*wait a second and release the shell code memory page*/);

    return sdk::memory::module_ex{ (unsigned __int64)allocation, thread, 0 };
}

auto sdk::memory::unload_pe_file_ex(void* handle, sdk::memory::module_ex& module_ex) -> bool
{
    unsigned __int64 file_size_of_image = 0;

    _MEMORY_BASIC_INFORMATION mbi;

    if (sdk::memory::nt_query_virtual_memory(handle, (void*)module_ex.base, 0, &mbi, sizeof(mbi), nullptr) != 0 /*the passed dll was already unloaded*/)
    {
        return false;
    }

    file_size_of_image = mbi.RegionSize;

    void* shell_code = 0;

    if (sdk::memory::nt_allocate_virtual_memory(handle, &shell_code, 0, &file_size_of_image, MEM_COMMIT, PAGE_EXECUTE_READWRITE) != 0 /*create region*/)
    {
        return false;
    }

    if (sdk::memory::nt_write_virtual_memory(handle, (void*)shell_code, sdk::memory::unload_pe_file_shell, 4096, nullptr) != 0 /*write unloader shell*/)
    {
        sdk::memory::nt_free_virtual_memory(handle, &shell_code, &file_size_of_image, MEM_RELEASE);

        return false;
    }

    void* thread = nullptr /*this thread will be hidden*/;

    if (sdk::memory::nt_create_thread_ex(
        &thread,
        (STANDARD_RIGHTS_REQUIRED | SYNCHRONIZE | 0xffff),
        nullptr,
        handle /*handle to other process*/,
        (LPTHREAD_START_ROUTINE)shell_code,
        (void*)module_ex.base /*pointer to the mod base*/,
        4 /*4 -> THREAD_CREATE_FLAGS_HIDE_FROM_DEBUGGER*/,
        0,
        0,
        0,
        nullptr) != 0)
    {
        sdk::memory::nt_free_virtual_memory(handle, &shell_code, &file_size_of_image, MEM_RELEASE);

        return false;
    }

    Sleep(1000);

    auto allocation = (void*)module_ex.base;

    sdk::memory::nt_free_virtual_memory(handle, &allocation, &file_size_of_image, MEM_RELEASE /*wait a second and release the shell code memory page*/);
    sdk::memory::nt_free_virtual_memory(handle, &shell_code, &file_size_of_image, MEM_RELEASE /*wait a second and release the shell code memory page*/);

    return true;
}

auto sdk::memory::load_pe_file_shell(unsigned __int64 base) -> void
{
    auto* opt_header = &((_IMAGE_NT_HEADERS64*)(base + ((_IMAGE_DOS_HEADER*)base)->e_lfanew))->OptionalHeader;

    for (auto reloc = (_IMAGE_BASE_RELOCATION*)(base + opt_header->DataDirectory
        [IMAGE_DIRECTORY_ENTRY_BASERELOC].VirtualAddress); reloc->VirtualAddress != 0; reloc = (_IMAGE_BASE_RELOCATION*)((unsigned __int64)reloc + reloc->SizeOfBlock))
    {
        auto entry_count = (reloc->SizeOfBlock - sizeof(_IMAGE_BASE_RELOCATION)) / sizeof(unsigned short);
        auto entry_flags = (unsigned short*)(reloc + 1);

        for (unsigned int x = 0; x != entry_count; ++x, ++entry_flags)
        {
            if (*entry_flags >> 0x0C == IMAGE_REL_BASED_DIR64)
            {
                auto patch = (unsigned __int64*)(base + reloc->VirtualAddress + ((*entry_flags) & 0xFFF));

                *patch += (unsigned __int64)(base - opt_header->ImageBase);
            }
        }
    }

    auto import_address = (unsigned __int64)&opt_header->DataDirectory[IMAGE_DIRECTORY_ENTRY_IMPORT];

    for (auto dir = base + ((_IMAGE_DATA_DIRECTORY*)import_address)->VirtualAddress; ((_IMAGE_IMPORT_DESCRIPTOR*)(dir))->Name; dir += sizeof(_IMAGE_IMPORT_DESCRIPTOR))
    {
        auto library_address = (unsigned __int64)
            ((HINSTANCE__ * (_stdcall*)(const char*)) * (unsigned __int64*)(base))((const char*)(base + ((_IMAGE_IMPORT_DESCRIPTOR*)(dir))->Name));

        auto original_first_thunk = base + ((_IMAGE_IMPORT_DESCRIPTOR*)(dir))->OriginalFirstThunk;
        auto first_thunk = base + ((_IMAGE_IMPORT_DESCRIPTOR*)(dir))->FirstThunk;

        while (*(unsigned __int64*)(first_thunk))
        {
            if (original_first_thunk && (((_IMAGE_THUNK_DATA64*)(original_first_thunk))->u1.Ordinal & IMAGE_ORDINAL_FLAG))
            {
                auto name_array = (unsigned __int64)&opt_header->DataDirectory[IMAGE_DIRECTORY_ENTRY_IMPORT];
                auto export_directory = base + ((_IMAGE_DATA_DIRECTORY*)(name_array))->VirtualAddress;

                auto address_array = base + ((_IMAGE_EXPORT_DIRECTORY*)(export_directory))->AddressOfFunctions;

                address_array += IMAGE_ORDINAL(((_IMAGE_THUNK_DATA64*)
                    (original_first_thunk))->u1.Ordinal - ((_IMAGE_EXPORT_DIRECTORY*)(export_directory))->Base * sizeof(unsigned long));

                *(unsigned __int64*)(first_thunk) = base + *(unsigned __int64*)address_array;
            }
            else
            {
                import_address = (unsigned __int64)(base + *(unsigned __int64*)(first_thunk));

                *(unsigned __int64*)(first_thunk) = (((unsigned __int64(_stdcall*)(HINSTANCE__*, const char*))
                    * (unsigned __int64*)(base + 0x8))((HINSTANCE__*)(library_address), (const char*)(((_IMAGE_IMPORT_BY_NAME*)(import_address))->Name)));
            }

            first_thunk += sizeof(unsigned __int64);

            if (original_first_thunk != 0)
            {
                original_first_thunk += sizeof(unsigned __int64);
            }
        }
    }

    if (opt_header->DataDirectory[IMAGE_DIRECTORY_ENTRY_TLS].Size != 0)
    {
        auto* cb = (PIMAGE_TLS_CALLBACK*)(((_IMAGE_TLS_DIRECTORY64*)(base + opt_header->DataDirectory[IMAGE_DIRECTORY_ENTRY_TLS].VirtualAddress))->AddressOfCallBacks);

        for (; cb != nullptr && *cb != nullptr; ++cb)
        {
            (*cb)((void*)base, DLL_PROCESS_ATTACH, nullptr);
        }
    }

    if (opt_header->AddressOfEntryPoint != 0)
    {
        ((bool(_stdcall*)(unsigned __int64, long))(base + opt_header->AddressOfEntryPoint))(base, DLL_PROCESS_ATTACH);
    }
}

auto sdk::memory::unload_pe_file_shell(unsigned __int64 base) -> void
{
    auto* opt_header = &((_IMAGE_NT_HEADERS64*)(base + ((_IMAGE_DOS_HEADER*)base)->e_lfanew))->OptionalHeader;

    if (opt_header->AddressOfEntryPoint != 0)
    {
        ((bool(_stdcall*)(unsigned __int64, long))(base + opt_header->AddressOfEntryPoint))(base, DLL_PROCESS_DETACH);
    }
}