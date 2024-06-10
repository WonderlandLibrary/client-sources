#define WIN32_LEAN_AND_MEAN
#include "../windows.h"
#include <iostream>
#include <string.h>
#include <TlHelp32.h>
#include <intrin.h>
#include <sstream>
#include <wchar.h>
#include <iomanip>
#include <comdef.h>
#include <Wbemidl.h>
#include <vector>
#include <atlstr.h>
#include <locale>
#include <codecvt>
#include <regex>
#include <iterator>
#pragma comment(lib, "wbemuuid.lib")
#pragma once

// Necessary fuctions to query HWID info.
enum class WmiQueryError {
	None,
	BadQueryFailure,
	PropertyExtractionFailure,
	ComInitializationFailure,
	SecurityInitializationFailure,
	IWbemLocatorFailure,
	IWbemServiceConnectionFailure,
	BlanketProxySetFailure,
};
struct WmiQueryResult
{
	std::vector<std::wstring> ResultList;
	WmiQueryError Error = WmiQueryError::None;
	std::wstring ErrorDescription;
};

static WmiQueryResult GetWMIQueryResult(std::string wmiQuery, std::string propNameOfResultObject, bool allowEmptyItems = false) {

	WmiQueryResult retVal;
	retVal.Error = WmiQueryError::None;
	retVal.ErrorDescription = L"";

	HRESULT hres;

	IWbemLocator *pLoc = NULL;
	IWbemServices *pSvc = NULL;
	IEnumWbemClassObject* pEnumerator = NULL;
	IWbemClassObject *pclsObj = NULL;
	VARIANT vtProp;

	// Step 1: --------------------------------------------------
	// Initialize COM. ------------------------------------------

	hres = CoInitializeEx(0, COINIT_MULTITHREADED);
	if (FAILED(hres))
	{
		retVal.Error = WmiQueryError::ComInitializationFailure;
		retVal.ErrorDescription = L"Failed to initialize COM library. Error code : " + std::to_wstring(hres);
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
			retVal.Error = WmiQueryError::SecurityInitializationFailure;
			retVal.ErrorDescription = L"Failed to initialize security. Error code : " + std::to_wstring(hres);
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
				retVal.Error = WmiQueryError::IWbemLocatorFailure;
				retVal.ErrorDescription = L"Failed to create IWbemLocator object. Error code : " + std::to_wstring(hres);
			}
			else
			{
				// Step 4: -----------------------------------------------------
				// Connect to WMI through the IWbemLocator::ConnectServer method

				pSvc = NULL;

				BSTR strNetworkResource;
				auto kkk = WindowsVersion();
				if (kkk.dwMajorVersion == 6 && kkk.dwMinorVersion == 1)
					strNetworkResource = L"root\\CIMV2";
				else
					strNetworkResource = (BSTR)L"\\\\.\\root\\CIMV2";

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
					retVal.Error = WmiQueryError::IWbemServiceConnectionFailure;
					retVal.ErrorDescription = L"Could not connect to Wbem service.. Error code : " + std::to_wstring(hres);
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
						retVal.Error = WmiQueryError::BlanketProxySetFailure;
						retVal.ErrorDescription = L"Could not set proxy blanket. Error code : " + std::to_wstring(hres);
					}
					else
					{
						// Step 6: --------------------------------------------------
						// Use the IWbemServices pointer to make requests of WMI ----

						// For example, get the name of the operating system
						pEnumerator = NULL;
						hres = pSvc->ExecQuery(
							bstr_t("WQL"),
							bstr_t(wmiQuery.c_str()),
							WBEM_FLAG_FORWARD_ONLY | WBEM_FLAG_RETURN_IMMEDIATELY,
							NULL,
							&pEnumerator);

						if (FAILED(hres))
						{
							retVal.Error = WmiQueryError::BadQueryFailure;
							retVal.ErrorDescription = L"Bad query. Error code : " + std::to_wstring(hres);
						}
						else
						{
							// Step 7: -------------------------------------------------
							// Get the data from the query in step 6 -------------------

							pclsObj = NULL;
							ULONG uReturn = 0;

							while (pEnumerator)
							{
								HRESULT hr = pEnumerator->Next(WBEM_INFINITE, 1,
									&pclsObj, &uReturn);

								if (0 == uReturn)
								{
									break;
								}

								// VARIANT vtProp;

								// Get the value of desired property
								hr = pclsObj->Get(bstr_t(propNameOfResultObject.c_str()), 0, &vtProp, 0, 0);
								if (S_OK != hr) {
									retVal.Error = WmiQueryError::PropertyExtractionFailure;
									retVal.ErrorDescription = L"Couldn't extract property: " + s2ws(propNameOfResultObject) + L" from result of query. Error code : " + std::to_wstring(hr);
								}
								else {
									BSTR val = vtProp.bstrVal;

									// Sometimes val might be NULL even when result is S_OK
									// Convert NULL to empty string (otherwise "std::wstring(val)" would throw exception)
									if (NULL == val) {
										if (allowEmptyItems) {
											retVal.ResultList.push_back(std::wstring(L""));
										}
									}
									else {
										retVal.ResultList.push_back(std::wstring(val));
									}
								}
							}
						}
					}
				}
			}
		}
	}

	// Cleanup
	// ========

	VariantClear(&vtProp);
	if (pclsObj)
		pclsObj->Release();

	if (pSvc)
		pSvc->Release();

	if (pLoc)
		pLoc->Release();

	if (pEnumerator)
		pEnumerator->Release();

	CoUninitialize();

	return retVal;
}
static std::wstring QueryWMI(std::string query, std::string propNameOfResultObject)
{
	WmiQueryResult res;
	res = GetWMIQueryResult(query, propNameOfResultObject);

	if (res.Error != WmiQueryError::None) {
		std::cout << "Got this error while executing query: " << std::endl;
		std::wcout << res.ErrorDescription << std::endl;
		return L""; // Exitting function
	}
	std::wstring str;
	for (const auto& item : res.ResultList) {
		str += item;
	}
	return str;
}

static void replaceAll(std::string& str, const std::string& from, const std::string& to) {
	if (from.empty())
		return;
	size_t start_pos = 0;
	while ((start_pos = str.find(from, start_pos)) != std::string::npos) {
		str.replace(start_pos, from.length(), to);
		start_pos += to.length(); // In case 'to' contains 'from', like replacing 'x' with 'yx'
	}
}
static void replaceAll(std::wstring& str, const std::wstring& from, const std::wstring& to) {
	if (from.empty())
		return;
	size_t start_pos = 0;
	while ((start_pos = str.find(from, start_pos)) != std::string::npos) {
		str.replace(start_pos, from.length(), to);
		start_pos += to.length(); // In case 'to' contains 'from', like replacing 'x' with 'yx'
	}
}

static std::string finalize(std::string str)
{
	std::string newstr;
	for (int i = 0; i < 40; i++)
	{
		if (i != 0 && i % 8 == 0)
		{
			newstr += "-";
			newstr += str[i];
		}
		else
		{
			newstr += str[i];
		}
	}
	std::transform(newstr.begin(), newstr.end(), newstr.begin(), ::toupper);
	return newstr;
}

class HWID {
public:
	static std::wstring GetProcessorID()
	{
		return QueryWMI(xors("SELECT processorID FROM Win32_Processor"), xors("processorID"));
	}

	static std::wstring get_hdd_serial()
	{
		char *d = getenv(xors("SystemDrive"));
		std::string k = xors("SELECT VolumeSerialNumber FROM Win32_LogicalDisk WHERE DeviceId=\"DRIVEHERE\"");
		replaceAll(k, xors("DRIVEHERE"), d);
		return QueryWMI(k.c_str(), xors("VolumeSerialNumber"));
	}

	static std::wstring GetBoardMaker()
	{
		return QueryWMI(xors("SELECT Manufacturer FROM Win32_BaseBoard"), xors("Manufacturer"));
	}

	static std::wstring GetBoardID()
	{
		return QueryWMI(xors("SELECT Product FROM Win32_BaseBoard"), xors("Product"));
	}

	static std::wstring GetBIOSSerialNo()
	{
		return QueryWMI(xors("SELECT SerialNumber FROM Win32_BIOS"), xors("SerialNumber"));
	}

	static std::wstring GetBIOSMaker()
	{
		return QueryWMI(xors("SELECT Manufacturer FROM Win32_BIOS"), xors("Manufacturer"));
	}

	static std::wstring GetBIOSVersion()
	{
		return QueryWMI(xors("SELECT Version FROM Win32_BIOS"), xors("Version"));
	}

	static std::wstring GetBIOSCaption()
	{
		return QueryWMI(xors("SELECT Caption FROM Win32_BIOS"), xors("Caption"));
	}

	static std::wstring GetPhysicalMemory()
	{
		return QueryWMI(xors("SELECT Capacity FROM Win32_PhysicalMemory"), xors("Capacity"));
	}

	static std::wstring get_board_serial()
	{
		return QueryWMI(xors("SELECT SerialNumber FROM Win32_BaseBoard"), xors("SerialNumber"));
	}

	static std::wstring GetCPUManufacturer()
	{
		return QueryWMI(xors("SELECT Manufacturer FROM Win32_Processor"), xors("Manufacturer"));
	}

	static std::wstring GetCPUInfo()
	{
		std::wstring query = QueryWMI(xors("SELECT Name FROM Win32_Processor"), xors("Name"));

		replaceAll(query, L"(TM)", L"™");
		replaceAll(query, L"(tm)", L"™");
		replaceAll(query, L"(r)", L"®");
		replaceAll(query, L"(R)", L"®");
		replaceAll(query, L"(C)", L"©");
		replaceAll(query, L"(c)", L"©");
		replaceAll(query, L"    ", L" ");
		replaceAll(query, L"  ", L" ");

		std::wstring query1 = QueryWMI(xors("SELECT Caption FROM Win32_Processor"), xors("Caption"));
		std::wstring query2 = QueryWMI(xors("SELECT SocketDesignation FROM Win32_Processor"), xors("SocketDesignation"));

		return query + L", " + query1 + L", " + query2;
	}
};