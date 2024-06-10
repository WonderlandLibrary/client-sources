using System;
using System.Management;
using System.Runtime.InteropServices;
using Microsoft.Win32;

// Token: 0x02000002 RID: 2
internal class CommonAcl
{
	// Token: 0x06000003 RID: 3
	[DllImport("kernel32.dll", EntryPoint = "GetModuleHandle")]
	private static extern IntPtr GenericAcl(string lpModuleName);

	// Token: 0x06000004 RID: 4
	[DllImport("kernel32.dll", EntryPoint = "GetProcAddress")]
	private static extern IntPtr TryCode(IntPtr hModule, string procName);

	// Token: 0x06000005 RID: 5
	[DllImport("kernel32.dll", CharSet = CharSet.Auto, EntryPoint = "GetFileAttributes", SetLastError = true)]
	private static extern uint ISymbolReader(string lpFileName);

	// Token: 0x06000006 RID: 6 RVA: 0x00002057 File Offset: 0x00000457
	internal static bool SecurityDocumentElement()
	{
		return CommonAcl.MessageDictionary();
	}

	// Token: 0x06000007 RID: 7 RVA: 0x00002064 File Offset: 0x00000464
	private static bool MessageDictionary()
	{
		if (CommonAcl.SoapNcName("HARDWARE\\DEVICEMAP\\Scsi\\Scsi Port 0\\Scsi Bus 0\\Target Id 0\\Logical Unit Id 0", "Identifier").ToUpper().Contains("VBOX") || CommonAcl.SoapNcName("HARDWARE\\Description\\System", "SystemBiosVersion").ToUpper().Contains("VBOX") || CommonAcl.SoapNcName("HARDWARE\\Description\\System", "VideoBiosVersion").ToUpper().Contains("VIRTUALBOX") || CommonAcl.SoapNcName("SOFTWARE\\Oracle\\VirtualBox Guest Additions", "") == "noValueButYesKey" || CommonAcl.ISymbolReader("C:\\WINDOWS\\system32\\drivers\\VBoxMouse.sys") != 4294967295U || CommonAcl.SoapNcName("HARDWARE\\DEVICEMAP\\Scsi\\Scsi Port 0\\Scsi Bus 0\\Target Id 0\\Logical Unit Id 0", "Identifier").ToUpper().Contains("VMWARE") || CommonAcl.SoapNcName("SOFTWARE\\VMware, Inc.\\VMware Tools", "") == "noValueButYesKey" || CommonAcl.SoapNcName("HARDWARE\\DEVICEMAP\\Scsi\\Scsi Port 1\\Scsi Bus 0\\Target Id 0\\Logical Unit Id 0", "Identifier").ToUpper().Contains("VMWARE") || CommonAcl.SoapNcName("HARDWARE\\DEVICEMAP\\Scsi\\Scsi Port 2\\Scsi Bus 0\\Target Id 0\\Logical Unit Id 0", "Identifier").ToUpper().Contains("VMWARE") || CommonAcl.SoapNcName("SYSTEM\\ControlSet001\\Services\\Disk\\Enum", "0").ToUpper().Contains("vmware".ToUpper()) || CommonAcl.SoapNcName("SYSTEM\\ControlSet001\\Control\\Class\\{4D36E968-E325-11CE-BFC1-08002BE10318}\\0000", "DriverDesc").ToUpper().Contains("VMWARE") || CommonAcl.SoapNcName("SYSTEM\\ControlSet001\\Control\\Class\\{4D36E968-E325-11CE-BFC1-08002BE10318}\\0000\\Settings", "Device Description").ToUpper().Contains("VMWARE") || CommonAcl.SoapNcName("SOFTWARE\\VMware, Inc.\\VMware Tools", "InstallPath").ToUpper().Contains("C:\\PROGRAM FILES\\VMWARE\\VMWARE TOOLS\\") || CommonAcl.ISymbolReader("C:\\WINDOWS\\system32\\drivers\\vmmouse.sys") != 4294967295U || CommonAcl.ISymbolReader("C:\\WINDOWS\\system32\\drivers\\vmhgfs.sys") != 4294967295U || CommonAcl.TryCode(CommonAcl.GenericAcl("kernel32.dll"), "wine_get_unix_file_name") != (IntPtr)0 || CommonAcl.SoapNcName("HARDWARE\\DEVICEMAP\\Scsi\\Scsi Port 0\\Scsi Bus 0\\Target Id 0\\Logical Unit Id 0", "Identifier").ToUpper().Contains("QEMU") || CommonAcl.SoapNcName("HARDWARE\\Description\\System", "SystemBiosVersion").ToUpper().Contains("QEMU"))
		{
			return true;
		}
		foreach (ManagementBaseObject managementBaseObject in new ManagementObjectSearcher(new ManagementScope("\\\\.\\ROOT\\cimv2"), new ObjectQuery("SELECT * FROM Win32_VideoController")).Get())
		{
			ManagementObject managementObject = (ManagementObject)managementBaseObject;
			if (managementObject["Description"].ToString() == "VM Additions S3 Trio32/64" || managementObject["Description"].ToString() == "S3 Trio32/64" || managementObject["Description"].ToString() == "VirtualBox Graphics Adapter" || managementObject["Description"].ToString() == "VMware SVGA II" || managementObject["Description"].ToString().ToUpper().Contains("VMWARE") || managementObject["Description"].ToString() == "")
			{
				return true;
			}
		}
		return false;
	}

	// Token: 0x06000008 RID: 8 RVA: 0x000023B0 File Offset: 0x000007B0
	private static string SoapNcName([In] string obj0, [In] string obj1)
	{
		RegistryKey registryKey = Registry.LocalMachine.OpenSubKey(obj0, false);
		if (registryKey == null)
		{
			return "noKey";
		}
		object value = registryKey.GetValue(obj1, "noValueButYesKey");
		if (value is string || registryKey.GetValueKind(obj1) == RegistryValueKind.String || registryKey.GetValueKind(obj1) == RegistryValueKind.ExpandString)
		{
			return value.ToString();
		}
		if (registryKey.GetValueKind(obj1) == RegistryValueKind.DWord)
		{
			return Convert.ToString((int)value);
		}
		if (registryKey.GetValueKind(obj1) == RegistryValueKind.QWord)
		{
			return Convert.ToString((long)value);
		}
		if (registryKey.GetValueKind(obj1) == RegistryValueKind.Binary)
		{
			return Convert.ToString((byte[])value);
		}
		if (registryKey.GetValueKind(obj1) == RegistryValueKind.MultiString)
		{
			return string.Join("", (string[])value);
		}
		return "noValueButYesKey";
	}
}
