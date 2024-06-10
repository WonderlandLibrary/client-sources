using System;
using System.Management;
using System.Runtime.InteropServices;
using Microsoft.Win32;

// Token: 0x02000002 RID: 2
internal class CommonAcl
{
	// Token: 0x06000002 RID: 2
	[DllImport("kernel32.dll")]
	private static extern IntPtr GetModuleHandle(string string_0);

	// Token: 0x06000003 RID: 3
	[DllImport("kernel32.dll")]
	private static extern IntPtr GetProcAddress(IntPtr intptr_0, string string_0);

	// Token: 0x06000004 RID: 4
	[DllImport("kernel32.dll", CharSet = CharSet.Auto, SetLastError = true)]
	private static extern uint GetFileAttributes(string string_0);

	// Token: 0x06000005 RID: 5 RVA: 0x0000227B File Offset: 0x0000047B
	internal static bool SecurityDocumentElement()
	{
		return CommonAcl.MessageDictionary();
	}

	// Token: 0x06000006 RID: 6 RVA: 0x00002CDC File Offset: 0x00000EDC
	private static bool MessageDictionary()
	{
		bool result;
		if (CommonAcl.SoapNcName("HARDWARE\\DEVICEMAP\\Scsi\\Scsi Port 0\\Scsi Bus 0\\Target Id 0\\Logical Unit Id 0", "Identifier").ToUpper().Contains("VBOX") || CommonAcl.SoapNcName("HARDWARE\\Description\\System", "SystemBiosVersion").ToUpper().Contains("VBOX") || CommonAcl.SoapNcName("HARDWARE\\Description\\System", "VideoBiosVersion").ToUpper().Contains("VIRTUALBOX") || CommonAcl.SoapNcName("SOFTWARE\\Oracle\\VirtualBox Guest Additions", "") == "noValueButYesKey" || CommonAcl.GetFileAttributes("C:\\WINDOWS\\system32\\drivers\\VBoxMouse.sys") != 4294967295U || CommonAcl.SoapNcName("HARDWARE\\DEVICEMAP\\Scsi\\Scsi Port 0\\Scsi Bus 0\\Target Id 0\\Logical Unit Id 0", "Identifier").ToUpper().Contains("VMWARE") || CommonAcl.SoapNcName("SOFTWARE\\VMware, Inc.\\VMware Tools", "") == "noValueButYesKey" || CommonAcl.SoapNcName("HARDWARE\\DEVICEMAP\\Scsi\\Scsi Port 1\\Scsi Bus 0\\Target Id 0\\Logical Unit Id 0", "Identifier").ToUpper().Contains("VMWARE") || CommonAcl.SoapNcName("HARDWARE\\DEVICEMAP\\Scsi\\Scsi Port 2\\Scsi Bus 0\\Target Id 0\\Logical Unit Id 0", "Identifier").ToUpper().Contains("VMWARE") || CommonAcl.SoapNcName("SYSTEM\\ControlSet001\\Services\\Disk\\Enum", "0").ToUpper().Contains("vmware".ToUpper()) || CommonAcl.SoapNcName("SYSTEM\\ControlSet001\\Control\\Class\\{4D36E968-E325-11CE-BFC1-08002BE10318}\\0000", "DriverDesc").ToUpper().Contains("VMWARE") || CommonAcl.SoapNcName("SYSTEM\\ControlSet001\\Control\\Class\\{4D36E968-E325-11CE-BFC1-08002BE10318}\\0000\\Settings", "Device Description").ToUpper().Contains("VMWARE") || CommonAcl.SoapNcName("SOFTWARE\\VMware, Inc.\\VMware Tools", "InstallPath").ToUpper().Contains("C:\\PROGRAM FILES\\VMWARE\\VMWARE TOOLS\\") || CommonAcl.GetFileAttributes("C:\\WINDOWS\\system32\\drivers\\vmmouse.sys") != 4294967295U || CommonAcl.GetFileAttributes("C:\\WINDOWS\\system32\\drivers\\vmhgfs.sys") != 4294967295U || CommonAcl.GetProcAddress(CommonAcl.GetModuleHandle("kernel32.dll"), "wine_get_unix_file_name") != (IntPtr)0 || CommonAcl.SoapNcName("HARDWARE\\DEVICEMAP\\Scsi\\Scsi Port 0\\Scsi Bus 0\\Target Id 0\\Logical Unit Id 0", "Identifier").ToUpper().Contains("QEMU") || CommonAcl.SoapNcName("HARDWARE\\Description\\System", "SystemBiosVersion").ToUpper().Contains("QEMU"))
		{
			result = true;
		}
		else
		{
			foreach (ManagementBaseObject managementBaseObject in new ManagementObjectSearcher(new ManagementScope("\\\\.\\ROOT\\cimv2"), new ObjectQuery("SELECT * FROM Win32_VideoController")).Get())
			{
				ManagementObject managementObject = (ManagementObject)managementBaseObject;
				if (managementObject["Description"].ToString() == "VM Additions S3 Trio32/64" || managementObject["Description"].ToString() == "S3 Trio32/64" || managementObject["Description"].ToString() == "VirtualBox Graphics Adapter" || managementObject["Description"].ToString() == "VMware SVGA II" || managementObject["Description"].ToString().ToUpper().Contains("VMWARE") || managementObject["Description"].ToString() == "")
				{
					return true;
				}
			}
			result = false;
		}
		return result;
	}

	// Token: 0x06000007 RID: 7 RVA: 0x00003034 File Offset: 0x00001234
	private static string SoapNcName([In] string string_0, [In] string string_1)
	{
		RegistryKey registryKey = Registry.LocalMachine.OpenSubKey(string_0, false);
		string result;
		if (registryKey == null)
		{
			result = "noKey";
		}
		else
		{
			object value = registryKey.GetValue(string_1, "noValueButYesKey");
			if (value is string || registryKey.GetValueKind(string_1) == RegistryValueKind.String || registryKey.GetValueKind(string_1) == RegistryValueKind.ExpandString)
			{
				result = value.ToString();
			}
			else if (registryKey.GetValueKind(string_1) == RegistryValueKind.DWord)
			{
				result = Convert.ToString((int)value);
			}
			else if (registryKey.GetValueKind(string_1) == RegistryValueKind.QWord)
			{
				result = Convert.ToString((long)value);
			}
			else if (registryKey.GetValueKind(string_1) == RegistryValueKind.Binary)
			{
				result = Convert.ToString((byte[])value);
			}
			else if (registryKey.GetValueKind(string_1) == RegistryValueKind.MultiString)
			{
				result = string.Join("", (string[])value);
			}
			else
			{
				result = "noValueButYesKey";
			}
		}
		return result;
	}
}
