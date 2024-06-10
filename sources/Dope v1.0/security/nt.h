#include <winternl.h>

using NtCloseTypedef = NTSTATUS(*)(HANDLE);
using NtQueryInformationProcessTypedef = NTSTATUS(*)(HANDLE, UINT, PVOID, ULONG, PULONG);
using NtQuerySystemInformationTypedef = NTSTATUS(*)(ULONG, PVOID, ULONG, PULONG);

extern "C" NTSTATUS NtFlushInstructionCache(HANDLE, PVOID, SIZE_T);
extern "C" NTSTATUS NtOpenThread(PHANDLE, ACCESS_MASK, POBJECT_ATTRIBUTES, CLIENT_ID*);
extern "C" NTSTATUS NtSuspendThread(HANDLE, PULONG);
extern "C" NTSTATUS NtResumeThread(HANDLE, PULONG);
extern "C" NTSTATUS NtAllocateVirtualMemory(HANDLE, PVOID*, ULONG, PSIZE_T, ULONG, ULONG);
extern "C" NTSTATUS NtFreeVirtualMemory(HANDLE, PVOID*, PSIZE_T, ULONG);

typedef NTSTATUS(NTAPI* _NtQuerySystemInformation)(
	ULONG SystemInformationClass,
	PVOID SystemInformation,
	ULONG SystemInformationLength,
	PULONG ReturnLength
	);
typedef NTSTATUS(NTAPI* _NtDuplicateObject)(
	HANDLE SourceProcessHandle,
	HANDLE SourceHandle,
	HANDLE TargetProcessHandle,
	PHANDLE TargetHandle,
	ACCESS_MASK DesiredAccess,
	ULONG Attributes,
	ULONG Options
	);
typedef NTSTATUS(NTAPI* _NtQueryObject)(
	HANDLE ObjectHandle,
	ULONG ObjectInformationClass,
	PVOID ObjectInformation,
	ULONG ObjectInformationLength,
	PULONG ReturnLength
	);


typedef struct _SYSTEM_HANDLE
{
	ULONG ProcessId;
	BYTE ObjectTypeNumber;
	BYTE Flags;
	USHORT Handle;
	PVOID Object;
	ACCESS_MASK GrantedAccess;
} SYSTEM_HANDLE, * PSYSTEM_HANDLE;

typedef struct _CODE_CRC32
{
	PVOID         m_va;
	DWORD         m_size;
	DWORD         m_crc32;
}CODE_CRC32;

typedef struct _SYSTEM_HANDLE_INFORMATION
{
	ULONG HandleCount;
	SYSTEM_HANDLE Handles[1];
} SYSTEM_HANDLE_INFORMATION, * PSYSTEM_HANDLE_INFORMATION;

typedef enum err_code
{
	err_success,
	err_enum_process_modules_failed,
	err_size_too_small,
	err_mod_name_not_found,
	err_mod_query_failed,
	err_create_file_failed,
	err_create_file_mapping_failed,
	err_create_file_mapping_already_exists,
	err_map_file_failed,
	err_mem_deprotect_failed,
	err_mem_reprotect_failed,
	err_text_section_not_found,
	err_file_path_query_failed
} err_code;

typedef enum suspend_resume_type
{
	srt_suspend,
	srt_resume
} suspend_resume_type, * psuspend_resume_type;

typedef struct suspend_resume_info
{
	ULONG current_pid;
	ULONG current_tid;
	suspend_resume_type type;
} suspend_resume_info, * psuspend_resume_info;

typedef struct wrk_system_process_information
{
	ULONG next_entry_offset;
	ULONG number_of_threads;
	LARGE_INTEGER spare_li1;
	LARGE_INTEGER spare_li2;
	LARGE_INTEGER spare_li3;
	LARGE_INTEGER create_time;
	LARGE_INTEGER user_time;
	LARGE_INTEGER kernel_time;
	UNICODE_STRING image_name;
	KPRIORITY base_priority;
	HANDLE unique_process_id;
	HANDLE inherited_from_unique_process_id;
	ULONG handle_count;
	ULONG session_id;
	ULONG_PTR page_directory_base;
	SIZE_T peak_virtual_size;
	SIZE_T virtual_size;
	ULONG page_fault_count;
	SIZE_T peak_working_set_size;
	SIZE_T working_set_size;
	SIZE_T quota_peak_paged_pool_usage;
	SIZE_T quota_paged_pool_usage;
	SIZE_T quota_peak_non_paged_pool_usage;
	SIZE_T quota_non_paged_pool_usage;
	SIZE_T pagefile_usage;
	SIZE_T peak_pagefile_usage;
	SIZE_T private_page_count;
	LARGE_INTEGER read_operation_count;
	LARGE_INTEGER write_operation_count;
	LARGE_INTEGER other_operation_count;
	LARGE_INTEGER read_transfer_count;
	LARGE_INTEGER write_transfer_count;
	LARGE_INTEGER other_transfer_count;
	SYSTEM_THREAD_INFORMATION threads[1];
} wrk_system_process_information, * pwrk_system_process_information;

typedef enum wrk_memory_information_class
{
	memory_basic_information
} wrk_memory_information_class, * pwrk_memory_information_class;

typedef struct object_type_information
{
	UNICODE_STRING type_name;
	ULONG total_number_of_handles;
	ULONG total_number_of_objects;
} object_type_information, * pobject_type_information;

typedef struct object_all_information
{
	ULONG number_of_objects;
	object_type_information object_type_information[1];
} object_all_information, * pobject_all_information;

#pragma pack(push, 1)
struct DbgUiRemoteBreakinPatch
{
	WORD  push_0;
	BYTE  push;
	DWORD CurrentPorcessHandle;
	BYTE  mov_eax;
	DWORD TerminateProcess;
	WORD  call_eax;
};
#pragma pack(pop)