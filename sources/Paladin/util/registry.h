#pragma once
#include <Windows.h>
#include <vector>

#define MAX_KEY_LENGTH 255
#define MAX_VALUE_NAME 16383

const auto filetime_to_time_t = [](FILETIME const& ft) -> time_t
{
	ULARGE_INTEGER ull;
	ull.LowPart = ft.dwLowDateTime;
	ull.HighPart = ft.dwHighDateTime;
	return max(0, ull.QuadPart / 10000000ULL - 11644473600ULL);
};

static DWORD GetRegValue(HKEY hkey, DWORD dwType, LPCSTR subKey, LPCSTR keyname)
{
	HKEY key;
	DWORD value;
	DWORD size = sizeof(DWORD);
	if (RegOpenKeyEx(hkey, subKey, 0, KEY_QUERY_VALUE, &key) == ERROR_SUCCESS)
	{
		if (RegQueryValueEx(key, keyname, 0, &dwType, reinterpret_cast<LPBYTE>(&value), &size) == ERROR_SUCCESS)
			return value;
		else
			return -69;
	}
	else
		return -69;
}

std::vector<std::pair<std::string, time_t>> QuerySubKeys(HKEY hKey)
{
	std::vector<std::pair<std::string, time_t>> subkeys;

	CHAR    achKey[MAX_KEY_LENGTH];   // buffer for subkey name
	DWORD    cbName;                   // size of name string 
	TCHAR    achClass[MAX_PATH] = TEXT("");  // buffer for class name 
	DWORD    cchClassName = MAX_PATH;  // size of class string 
	DWORD    cSubKeys = 0;               // number of subkeys 
	DWORD    cbMaxSubKey;              // longest subkey size 
	DWORD    cchMaxClass;              // longest class string 
	DWORD    cValues;              // number of values for key 
	DWORD    cchMaxValue;          // longest value name 
	DWORD    cbMaxValueData;       // longest value data 
	DWORD    cbSecurityDescriptor; // size of security descriptor 
	FILETIME ftLastWriteTime;      // last write time 

	DWORD i, retCode;

	//TCHAR  achValue[MAX_VALUE_NAME];
	DWORD cchValue = MAX_VALUE_NAME;

	// Get the class name and the value count. 
	retCode = RegQueryInfoKey(
		hKey,                    // key handle 
		achClass,                // buffer for class name 
		&cchClassName,           // size of class string 
		NULL,                    // reserved 
		&cSubKeys,               // number of subkeys 
		&cbMaxSubKey,            // longest subkey size 
		&cchMaxClass,            // longest class string 
		&cValues,                // number of values for this key 
		&cchMaxValue,            // longest value name 
		&cbMaxValueData,         // longest value data 
		&cbSecurityDescriptor,   // security descriptor 
		&ftLastWriteTime);       // last write time 

	// Enumerate the subkeys, until RegEnumKeyEx fails.

	if (cSubKeys)
	{
		for (i = 0; i < cSubKeys; i++)
		{
			cbName = MAX_KEY_LENGTH;
			retCode = RegEnumKeyExA(hKey, i,
				achKey,
				&cbName,
				NULL,
				NULL,
				NULL,
				&ftLastWriteTime);
			if (retCode == ERROR_SUCCESS)
			{
				subkeys.push_back({ achKey, filetime_to_time_t(ftLastWriteTime) });
			}
		}
	}

	return subkeys;
}
