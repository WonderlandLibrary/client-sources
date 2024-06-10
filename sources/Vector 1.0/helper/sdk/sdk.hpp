
#include "../hdr.hpp"
#include "picosha/picosha.hpp"
#include "xor/xor.h"

namespace sdk::memory
{
	extern __forceinline auto get_p_env() -> _PEB*;

    extern __forceinline auto get_ntdll() -> unsigned __int64;

	extern __forceinline auto get_library_base(const char* mod_name) -> unsigned __int64;

	extern __forceinline auto get_proc_address(unsigned __int64 mod, const char* exp_name) -> unsigned __int64;

	extern __forceinline auto verify_integrity(unsigned __int64 address, unsigned __int64 size) -> __int64;

	extern __forceinline auto nt_create_thread_ex(
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
	) -> long;

	extern __forceinline auto nt_allocate_virtual_memory(
		void* handle,
		void** address,
		unsigned __int64* zero_bits,
		unsigned __int64* size,
		unsigned __int64 type,
		unsigned __int64 protection
	) -> long;

	extern __forceinline auto nt_free_virtual_memory(
		void* handle,
		void** address,
		unsigned __int64* size,
		unsigned __int64 type
	) -> long;

	extern __forceinline auto nt_protect_virtual_memory(
		void* handle,
		void* address,
		unsigned __int64 size,
		unsigned __int64 protection,
		unsigned __int64* old_protection
	) -> long;

	extern __forceinline auto nt_query_virtual_memory(
		void* handle,
		void* address,
		unsigned char mode,
		void* mbi,
		unsigned __int64 size,
		unsigned __int64* bytes_read
	) -> long;

	extern __forceinline auto nt_read_virtual_memory(
		void* handle,
		void* address,
		void* buffer,
		unsigned __int64 size,
		unsigned __int64* bytes_read
	) -> long;

	extern __forceinline auto nt_write_virtual_memory(
		void* handle,
		void* address,
		void* buffer,
		unsigned __int64 size,
		unsigned __int64* bytes_read
	) -> long;

	extern __forceinline auto nt_open_process(
		void** handle,
		unsigned long access,
		unsigned long pid
	) -> long;

	struct module_ex
	{
		unsigned __int64 base = 0;
		void* thread_ex = nullptr;
		unsigned __int64 code = 0;
	};

	extern __forceinline auto load_pe_file_ex(void* handle, std::vector<unsigned char>& buffer) -> sdk::memory::module_ex;

	extern __forceinline auto unload_pe_file_ex(void* handle, sdk::memory::module_ex& module_ex) -> bool;

	extern __forceinline auto load_pe_file_shell(unsigned __int64 base) -> void;

	extern __forceinline auto unload_pe_file_shell(unsigned __int64 base) -> void;

	//misc
	extern __forceinline auto is_active(void* handle, unsigned char mode) -> bool
	{
		if (mode == 0)
		{
			unsigned long exit_code = 0;

			GetExitCodeProcess(handle, &exit_code);

			return exit_code == STILL_ACTIVE;
		}

		if (mode == 1)
		{
			unsigned long exit_code = 0;

			GetExitCodeThread(handle, &exit_code);

			return exit_code == STILL_ACTIVE;
		}

		return false;
	}

	extern __forceinline auto file_to_vector(const char* path) -> std::vector<unsigned char>
	{
		std::vector<unsigned char> buffer;

		std::ifstream file(path, std::ios::binary | std::ios::ate);

		if (!file.is_open())
		{
			return std::vector<unsigned char>{};
		}

		buffer.resize(file.tellg());

		file.seekg(0, std::ios::beg);
		file.read((char*)&buffer[0], buffer.size());
		file.close();

		return buffer;
	}

	extern __forceinline auto get_process_id(const char* exe_name, const char* cls_name, const char* win_name, const char* ser_name) -> unsigned long
	{
		unsigned long process_id = 0;

		if (exe_name != nullptr)
		{
			auto snap = CreateToolhelp32Snapshot(TH32CS_SNAPPROCESS, 0);

			if (snap != INVALID_HANDLE_VALUE)
			{
				tagPROCESSENTRY32 entry
				{
					sizeof(tagPROCESSENTRY32)
				};

				for (auto init = Process32First(snap, &entry); init && Process32Next(snap, &entry);)
				{
					if (std::string(entry.szExeFile).find(exe_name) != std::string::npos)
					{
						process_id = entry.th32ProcessID;
					}
				}

				CloseHandle(snap);
			}
		}

		if (cls_name != nullptr)
		{
			GetWindowThreadProcessId(FindWindowA(cls_name, nullptr), &process_id);
		}

		if (win_name != nullptr)
		{
			GetWindowThreadProcessId(FindWindowA(nullptr, win_name), &process_id);
		}

		if (ser_name != nullptr)
		{
			auto manager = OpenSCManagerA(nullptr, nullptr, 0);

			if (manager == nullptr)
				return 0;

			auto service = OpenServiceA(manager, ser_name, SERVICE_QUERY_STATUS);

			if (service == nullptr)
			{
				CloseServiceHandle(manager);
				return 0;
			}

			_SERVICE_STATUS_PROCESS service_status;

			unsigned long bytes_read = 0;

			auto res = QueryServiceStatusEx(
				service, SC_STATUS_PROCESS_INFO, (unsigned char*)&service_status, sizeof(service_status), &bytes_read);

			CloseServiceHandle(service);
			CloseServiceHandle(manager);

			if (service_status.dwProcessId != 0)
			{
				process_id = service_status.dwProcessId;
			}
		}

		return process_id;
	}

	extern __forceinline auto get_hardware_id() -> std::string
	{
		char16_t hold = 0;

		__int32 cpuid[4];

		__cpuid(cpuid, 0);

		auto ptr = (char16_t*)cpuid;

		for (char32_t x = 0; x < 8; ++x)
		{
			hold += ptr[x];
		}

		unsigned long vol_serial_number = 0;

		char vol_serial[9];

		std::string directory(MAX_PATH, 0);
		GetWindowsDirectoryA(&directory[0], MAX_PATH);

		directory = directory.substr(0, directory.find("\\") + 1);

		GetVolumeInformationA(&directory[0], nullptr, 0, &vol_serial_number, nullptr, nullptr, nullptr, 0);

		sprintf_s(vol_serial, 9, "%08lX", vol_serial_number);

		auto hashed = picosha2::hash256_hex_string(std::to_string(hold) + vol_serial);

		auto tmp_id = hashed.substr(0, 31);

		for (auto x = 0; x < 3; ++x)
		{
			tmp_id = picosha2::hash256_hex_string(tmp_id);
		}

		return tmp_id.substr(0, 30) /*return the hwid hashed 3 times with hash256 and substracting first 30 chars*/;
	}
}

namespace sdk
{
	static struct vec2
	{
		double x = 0., y = 0.;
	};

	static struct vec3
	{
		double x = 0., y = 0., z = 0.;
	};

	static struct vec4
	{
		double x = 0., y = 0., z = 0., w = 0.;
	};
}

namespace sdk
{
	struct high_resolution_timer
	{
		high_resolution_timer()
		{
			this->str = std::chrono::high_resolution_clock::now();
		}

		~high_resolution_timer()
		{

		}

		std::chrono::steady_clock::time_point str = std::chrono::high_resolution_clock::now();
		std::chrono::steady_clock::time_point end = std::chrono::high_resolution_clock::now();

		double elapsed = 0.;

		auto has_passed(double milliseconds) -> bool
		{
			this->end = std::chrono::high_resolution_clock::now();

			this->elapsed = std::chrono::duration_cast<std::chrono::microseconds>(this->end - this->str).count() / 1000.;

			if (this->elapsed > milliseconds)
			{
				this->str = std::chrono::high_resolution_clock::now();
				return true;
			}

			return false;
		}
	};
}