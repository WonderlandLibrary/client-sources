#include "strings.h"
#include "../Main.h"
#include "../util/xxhash.hpp"
#include "../util/threadpool.h"
#include "../util/hwid.h"
#include "../includes.h"

std::mutex *results_mutex = new std::mutex();

ThreadPool thread_pool(max(std::thread::hardware_concurrency(), 1));

//static std::ostringstream o;
static std::string xhash(std::string s)
{
	uint64_t hash = xxh::xxhash<64>(s);
	std::ostringstream o;
	o << std::hex << hash;
	return o.str();
}

typedef enum _MEMORY_INFORMATION_CLASS {
	MemoryBasicInformation
} MEMORY_INFORMATION_CLASS;
#pragma comment(lib,"ntdll.lib")
extern "C" long __stdcall NtReadVirtualMemory(HANDLE, void*, void*, unsigned long, unsigned long*);
extern "C" long __stdcall NtQueryVirtualMemory(HANDLE, void*, MEMORY_INFORMATION_CLASS, void*, SIZE_T, PSIZE_T);

IWbemServices *pSvc = NULL;
IWbemLocator *pLoc = NULL;

COAUTHIDENTITY *userAcct = NULL;
COAUTHIDENTITY authIdent;

bool localconn = true;

//CREDENTIAL structure
//http://msdn.microsoft.com/en-us/library/windows/desktop/aa374788%28v=vs.85%29.aspx
#define CRED_MAX_USERNAME_LENGTH            513
#define CRED_MAX_CREDENTIAL_BLOB_SIZE       512
#define CREDUI_MAX_USERNAME_LENGTH CRED_MAX_USERNAME_LENGTH
#define CREDUI_MAX_PASSWORD_LENGTH (CRED_MAX_CREDENTIAL_BLOB_SIZE / 2)

HRESULT hres;

static std::string* string_key;

#ifndef NTSTATUS
typedef LONG NTSTATUS;
#endif
#ifndef NTAPI
typedef WINAPI NTAPI
#endif

#define PAGE_SIZE 512
#define DISPLAY_BUFFER_COUNT (PAGE_SIZE * 2 - 1)
#define PTR_ADD_OFFSET(Pointer, Offset) ((PVOID)((ULONG_PTR)(Pointer) + (ULONG_PTR)(Offset)))

bool printableChars[256] = {
	0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 1, 0, 0, /* 0 - 15 */ // TAB, LF and CR are printable
	0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, /* 16 - 31 */
	1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, /* ' ' - '/' */
	1, 1, 1, 1, 1, 1, 1, 1, 1, 1, /* '0' - '9' */
	1, 1, 1, 1, 1, 1, 1, /* ':' - '@' */
	1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, /* 'A' - 'Z' */
	1, 1, 1, 1, 1, 1, /* '[' - '`' */
	1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, /* 'a' - 'z' */
	1, 1, 1, 1, 0, /* '{' - 127 */ // DEL is not printable
	0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, /* 128 - 143 */
	0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, /* 144 - 159 */
	0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, /* 160 - 175 */
	0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, /* 176 - 191 */
	0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, /* 192 - 207 */
	0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, /* 208 - 223 */
	0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, /* 224 - 239 */
	0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 /* 240 - 255 */
};

int64_t counter;

struct checkdata {
	SIZE_T length;
	SIZE_T reallength;
	std::vector<std::tuple<std::string, std::string>> *strings;
	std::vector<std::tuple<std::string, std::string>> *results;
	int extra;
	PVOID address;
	PWSTR displayBuffer;
};

void check_string(checkdata *cd)
{
	std::string *s = new std::string();
	for (int i = 0; i < cd->length; i++)
		s->push_back((char)cd->displayBuffer[i]);

	if (cd->extra == 1932) // DPS
	{
		for (auto it = cd->strings->begin(); it != cd->strings->end(); ++it)
		{
			if (s->find(std::get<1>(*it)) != std::string::npos)
			{
				std::string clientname = xorr(hex2str(std::get<0>(*it)), string_key->c_str(), string_key->size());
				std::string string = xhash(xorr(std::get<1>(*it), string_key->c_str(), string_key->size()));
				results_mutex->lock();
				cd->results->emplace_back(std::tuple(clientname, string));
				results_mutex->unlock();
			}
		}

		delete s;
		return;
	}

	std::string enc = xhash(xorr(s->c_str(), string_key->c_str(), string_key->size()));

	for (auto it = cd->strings->begin(); it != cd->strings->end(); ++it)
	{
		if (strcmp(enc.c_str(), std::get<1>(*it).c_str()) == 0)
		{
			std::string clientname = xorr(hex2str(std::get<0>(*it)), string_key->c_str(), string_key->size());
			results_mutex->lock();
			cd->results->emplace_back(std::tuple(clientname, std::get<1>(*it)));
			results_mutex->unlock();
		}
	}

	delete s;
}

bool Scanner::run(HANDLE handle, std::vector<std::tuple<std::string, std::string>> *strings, std::vector<std::tuple<std::string, std::string>> *results, int extra)
{
	VMProtectBeginUltra("scanner.run");
	if (string_key == NULL)
		string_key = new std::string(xors("qCmxLz5ucqRUWA6jPxURKEnVsyr5xhc8z8We27DvN6rARjPFheRkpnj59r55qrNsP8wgK4pDSqueF8EPmQPFpDLJCjgDjCrytJ84"));

	counter = 0;
	if (h_token == NULL)
		return FALSE;

	int minlen = 4;
	int maxlen = 51;
	if (extra == 1)
		minlen = 3;
	if (extra == 5) { minlen = 4; maxlen = 21; }

	bool ok = search(strings, results, handle, minlen, maxlen, extra);
	CloseHandle(handle);
	return ok;
	VMProtectEnd();
}

struct WmiQueryResult2
{
	std::vector<std::wstring> ResultList;
	WmiQueryError Error = WmiQueryError::None;
	std::wstring ErrorDescription;
};

Scanner::Scanner()
{
	if (!OpenProcessToken(GetCurrentProcess(), TOKEN_ADJUST_PRIVILEGES, &h_token)) {
		std::cout << xors("OpenProcessToken() failed, error ") << GetLastError() << std::endl;
		h_token = NULL;
		return;
	}

	if (!SetPrivilege2(h_token, SE_DEBUG_NAME, TRUE)) {
		std::cout << xors("Failed to enable privilege, error ") << GetLastError() << std::endl;
		h_token = NULL;
		return;
	}

	WmiQueryResult2 ret_val;
	ret_val.Error = WmiQueryError::None;
	ret_val.ErrorDescription = L"";
	VARIANT vtProp;
	IEnumWbemClassObject* pEnumerator = NULL;

	if (pSvc == NULL)
	{
		wchar_t pszName[CREDUI_MAX_USERNAME_LENGTH + 1] = L"user";
		wchar_t pszPwd[CREDUI_MAX_PASSWORD_LENGTH + 1] = L"password";
		BSTR strNetworkResource;
		//To use a WMI remote connection set localconn to false and configure the values of the pszName, pszPwd and the name of the remote machine in strNetworkResource

		auto kkk = WindowsVersion();
		if (kkk.dwMajorVersion == 6 && kkk.dwMinorVersion == 1)
			strNetworkResource = L"root\\CIMV2";
		else
			strNetworkResource = (BSTR)L"\\\\.\\root\\CIMV2";

		hres = CoInitializeEx(0, COINIT_MULTITHREADED);
		if (FAILED(hres))
		{
			ret_val.Error = WmiQueryError::ComInitializationFailure;
			ret_val.ErrorDescription = L"Failed to initialize COM library. Error code : " + std::to_wstring(hres);
		}
		else
		{
			// Step 2: --------------------------------------------------
			// Set general COM security levels --------------------------

			hres = CoInitializeSecurity(
				NULL,
				-1,                          // COM authentication
				NULL,                        // Authentication services
				NULL,                        // Reserved
				RPC_C_AUTHN_LEVEL_DEFAULT,   // Default authentication 
				RPC_C_IMP_LEVEL_IMPERSONATE, // Default Impersonation  
				NULL,                        // Authentication info
				EOAC_NONE,                   // Additional capabilities 
				NULL                         // Reserved
			);


			if (FAILED(hres))
			{
				ret_val.Error = WmiQueryError::SecurityInitializationFailure;
				ret_val.ErrorDescription = L"Failed to initialize security. Error code : " + std::to_wstring(hres);
			}
			else
			{
				// Step 3: ---------------------------------------------------
				// Obtain the initial locator to WMI -------------------------
				pLoc = NULL;

				hres = CoCreateInstance(
					CLSID_WbemLocator,
					0,
					CLSCTX_INPROC_SERVER,
					IID_IWbemLocator, (LPVOID *)&pLoc);

				if (FAILED(hres))
				{
					ret_val.Error = WmiQueryError::IWbemLocatorFailure;
					ret_val.ErrorDescription = L"Failed to create IWbemLocator object. Error code : " + std::to_wstring(hres);
				}
				else
				{
					// Step 4: -----------------------------------------------------
					// Connect to WMI through the IWbemLocator::ConnectServer method

					pSvc = NULL;

					// Connect to the root\cimv2 namespace with
					// the current user and obtain pointer pSvc
					// to make IWbemServices calls.
					hres = pLoc->ConnectServer(
						strNetworkResource, // Object path of WMI namespace
						NULL,                    // User name. NULL = current user
						NULL,                    // User password. NULL = current
						0,                       // Locale. NULL indicates current
						NULL,                    // Security flags.
						0,                       // Authority (for example, Kerberos)
						0,                       // Context object 
						&pSvc                    // pointer to IWbemServices proxy
					);

					// Connected to ROOT\\CIMV2 WMI namespace

					if (FAILED(hres))
					{
						ret_val.Error = WmiQueryError::IWbemServiceConnectionFailure;
						ret_val.ErrorDescription = L"Could not connect to Wbem service.. Error code : " + std::to_wstring(hres);
					}
					else
					{
						// Step 5: --------------------------------------------------
						// Set security levels on the proxy -------------------------

						hres = CoSetProxyBlanket(
							pSvc,                        // Indicates the proxy to set
							RPC_C_AUTHN_WINNT,           // RPC_C_AUTHN_xxx
							RPC_C_AUTHZ_NONE,            // RPC_C_AUTHZ_xxx
							NULL,                        // Server principal name 
							RPC_C_AUTHN_LEVEL_CALL,      // RPC_C_AUTHN_LEVEL_xxx 
							RPC_C_IMP_LEVEL_IMPERSONATE, // RPC_C_IMP_LEVEL_xxx
							NULL,                        // client identity
							EOAC_NONE                    // proxy capabilities 
						);

						if (FAILED(hres))
						{
							ret_val.Error = WmiQueryError::BlanketProxySetFailure;
							ret_val.ErrorDescription = L"Could not set proxy blanket. Error code : " + std::to_wstring(hres);
							VariantClear(&vtProp);

							if (pSvc)
								pSvc->Release();

							if (pLoc)
								pLoc->Release();

							if (pEnumerator)
								pEnumerator->Release();

							CoUninitialize();
						}
					}
				}
			}
		}
	}
}
Scanner::~Scanner()
{
	delete string_key;
	CloseHandle(h_token);
}

std::vector<PAGETUPLE> Scanner::find_pages(HANDLE handle, int extra)
{
	VMProtectBeginMutation("scanner.find_pages");
	std::vector<PAGETUPLE> vec;
	MEMORY_BASIC_INFORMATION basic_info;
	PVOID baseAddress = (PVOID)0;
	
	while (NT_SUCCESS(NtQueryVirtualMemory(handle, baseAddress, MemoryBasicInformation, &basic_info, sizeof(basic_info), NULL)))
	{
		if (basic_info.State & MEM_COMMIT)
		{
			vec.push_back(PAGETUPLE(basic_info.BaseAddress, basic_info.AllocationBase, basic_info.RegionSize, basic_info.Protect, basic_info.Type, basic_info.State));
		}

		baseAddress = PTR_ADD_OFFSET(baseAddress, basic_info.RegionSize);
	}

	return vec;
	VMProtectEnd();
}

std::vector<std::tuple<std::string, std::string>> FuckMe(HANDLE proc, PAGETUPLE p, ULONG minLen, ULONG maxLen, int extra, std::vector<std::tuple<std::string, std::string>> *strings)
{
	std::vector<std::tuple<std::string, std::string>> *res = new std::vector<std::tuple<std::string, std::string>>();

	PVOID base_address = std::get<0>(p);
	PVOID alloc_base = std::get<1>(p);
	SIZE_T region_size = std::get<2>(p);
	DWORD protect = std::get<3>(p);
	DWORD type = std::get<4>(p);
	DWORD state = std::get<5>(p);

	SIZE_T buffer_size = PAGE_SIZE * 64;
	PUCHAR buffer = (PUCHAR)malloc(buffer_size);
	SIZE_T display_buffer_count = DISPLAY_BUFFER_COUNT;
	PWSTR display_buffer = (PWSTR)malloc((display_buffer_count + 1) * sizeof(WCHAR));
	ULONG length_in_bytes;

	std::vector<DWORD> allowed_types{ MEM_PRIVATE };
	if (extra == 9173)
		allowed_types.push_back(MEM_MAPPED);

	bool allowed = false;
	for (auto a : allowed_types) {
		if (a & type) { allowed = true; break; }
	}

	if (!allowed || protect == PAGE_NOACCESS || protect & PAGE_GUARD)
		goto Exit;

	if (extra == 2 && (base_address >= (PVOID)0x10000000000 || base_address == (PVOID)0x6C0000000 || (region_size >= 600000000 && base_address != (PVOID)0x700000000) || region_size == 33554432 || region_size == 16781312 || region_size == 8388608 || region_size == 8392704))
		goto Exit;
	else if (extra == 5 && region_size >= 8000000) // 8mb
		goto Exit;

	ULONG_PTR offset;
	SIZE_T readSize;

	readSize = region_size;

	if (region_size > buffer_size)
	{
		if (region_size <= 16 * 1024 * 1024)
		{
			free(buffer);
			buffer_size = region_size;
			buffer = (PUCHAR)malloc(buffer_size);

			if (!buffer)
				goto Exit;
		}
		else {
			readSize = buffer_size;
		}
	}

	for (offset = 0; offset < region_size; offset += readSize)
	{
		ULONG_PTR i;
		UCHAR byte; // current byte
		UCHAR byte1 = 0; // previous byte
		UCHAR byte2 = 0; // byte before previous byte
		BOOLEAN printable;
		BOOLEAN printable1 = FALSE;
		BOOLEAN printable2 = FALSE;
		ULONG length = 0;

		if (!NT_SUCCESS(NtReadVirtualMemory(proc, PTR_ADD_OFFSET(base_address, offset), buffer, (unsigned long)readSize, NULL)))
			continue;

		for (i = 0; i < readSize; i++)
		{
			byte = buffer[i];
			printable = printableChars[byte];

			if (printable2 && printable1 && printable)
			{
				if (length < display_buffer_count)
					display_buffer[length] = byte;

				length++;
			}
			else if (printable2 && printable1 && !printable)
			{
				if (length >= minLen)
				{
					goto CreateResult;
				}
				else if (byte == 0)
				{
					length = 1;
					display_buffer[0] = byte1;
				}
				else
				{
					length = 0;
				}
			}
			else if (printable2 && !printable1 && printable)
			{
				if (byte1 == 0)
				{
					if (length < display_buffer_count)
						display_buffer[length] = byte;

					length++;
				}
			}
			else if (printable2 && !printable1 && !printable)
			{
				if (length >= minLen)
				{
					goto CreateResult;
				}
				else
				{
					length = 0;
				}
			}
			else if (!printable2 && printable1 && printable)
			{
				if (length >= minLen + 1) // length - 1 >= minimumLength but avoiding underflow
				{
					length--; // exclude byte1
					goto CreateResult;
				}
				else
				{
					length = 2;
					display_buffer[0] = byte1;
					display_buffer[1] = byte;
				}
			}
			else if (!printable2 && printable1 && !printable)
			{
				// Nothing
			}
			else if (!printable2 && !printable1 && printable)
			{
				if (length < display_buffer_count)
					display_buffer[length] = byte;

				length++;
			}
			else if (!printable2 && !printable1 && !printable)
			{
				// Nothing
			}

			goto AfterCreateResult;

		CreateResult:
			{
				ULONG bias;
				length_in_bytes = length;
				bias = 0;
				int reallen = length;

				if (printable1 == printable) // determine if string was wide (refer to state table, 4 and 5)
				{
					length_in_bytes *= 2;
					reallen *= 2;
				}

				if (printable) // byte1 excluded (refer to state table, 5)
				{
					bias = 1;
				}

				if (length <= maxLen)
				{
					checkdata *cd = new checkdata();
					cd->length = length;
					cd->reallength = reallen;
					cd->strings = strings;
					cd->results = res;
					cd->extra = extra;
					cd->displayBuffer = display_buffer;
					//cd->address = (PVOID)((ULONG_PTR)BaseAddress + i - lengthInBytes);
					check_string(cd);
					delete cd;
				}

				length = 0;
			}

		AfterCreateResult:
			byte2 = byte1;
			byte1 = byte;
			printable2 = printable1;
			printable1 = printable;
		}
	}
	
	Exit:
	free(buffer);
	free(display_buffer);
	return *res;
}

bool Scanner::search(std::vector<std::tuple<std::string, std::string>> *strings, std::vector<std::tuple<std::string, std::string>> *results, HANDLE proc, ULONG minLen, ULONG maxLen, int extra)
{
	VMProtectBeginMutation("scanner.search");
	std::vector<PAGETUPLE> pages = find_pages(proc, extra);
	std::vector<std::future<std::vector<std::tuple<std::string, std::string>>>> results2;
	std::vector<std::tuple<std::string, std::string>> results3;

	for (auto p : pages)
	{
        results2.emplace_back(
			thread_pool.enqueue([proc, p, minLen, maxLen, extra, strings] {
				return FuckMe(proc, p, minLen, maxLen, extra, strings);
			})
		);
	}

	for (auto && k : results2)
		for (auto kk : k.get())
            results->emplace_back(kk); 

	return pages.size() != 0;
	VMProtectEnd();
}

DWORD get_svc_host(const wchar_t *type)
{
	// Use the IWbemServices pointer to make requests of WMI ----

	IEnumWbemClassObject* pEnumerator = NULL;
	hres = pSvc->ExecQuery(bstr_t(xors("WQL")), bstr_t(xors("SELECT CommandLine,ProcessId FROM Win32_Process")),
		WBEM_FLAG_FORWARD_ONLY | WBEM_FLAG_RETURN_IMMEDIATELY, NULL, &pEnumerator);

	if (FAILED(hres))
	{
		Paladin::gErrors.push_back({ 1, xors("ExecQuery, error code ") + hres });
		Paladin::gErrors.push_back({ 1, _com_error(hres).ErrorMessage() });
		pSvc->Release();
		pLoc->Release();
		CoUninitialize();
	}

	// Get the data from the WQL sentence
	IWbemClassObject *pclsObj = NULL;
	ULONG uReturn = 0;

	while (pEnumerator)
	{
		HRESULT hr = pEnumerator->Next(WBEM_INFINITE, 1, &pclsObj, &uReturn);

		if (0 == uReturn || FAILED(hr))
			break;

		VARIANT vtProp;

		bool hastype = false;

		hr = pclsObj->Get(L"CommandLine", 0, &vtProp, 0, 0);// String
		if (!FAILED(hr))
		{
			if (!(vtProp.vt == VT_NULL) || (vtProp.vt == VT_EMPTY)) {
				if ((vtProp.vt & VT_ARRAY)) {
					//wcout << "CommandLine : " << "Array types not supported (yet)" << endl;
				}
				else {
					hastype = (wcsstr(vtProp.bstrVal, type) != NULL);
				}
			}
		}
		VariantClear(&vtProp);

		hr = pclsObj->Get(L"ProcessId", 0, &vtProp, 0, 0);// Uint32
		if (hastype) {
			return vtProp.uintVal;
		}
		VariantClear(&vtProp);

		pclsObj->Release();
		pclsObj = NULL;
	}

	return 12345678;
}
