using System;
using System.Management;
using System.Security.Cryptography;
using System.Text;
using Microsoft.VisualBasic.CompilerServices;

namespace svchost
{
	// Token: 0x0200000D RID: 13
	public class clsComputerInfo
	{
		// Token: 0x060000FE RID: 254 RVA: 0x00009314 File Offset: 0x00007514
		internal string GetProcessorId()
		{
			string strProcessorId = string.Empty;
			SelectQuery query = new SelectQuery("Win32_processor");
			ManagementObjectSearcher search = new ManagementObjectSearcher(query);
			try
			{
				foreach (ManagementBaseObject managementBaseObject in search.Get())
				{
					ManagementObject info = (ManagementObject)managementBaseObject;
					strProcessorId = info["processorId"].ToString();
				}
			}
			finally
			{
				ManagementObjectCollection.ManagementObjectEnumerator enumerator;
				if (enumerator != null)
				{
					((IDisposable)enumerator).Dispose();
				}
			}
			return strProcessorId;
		}

		// Token: 0x060000FF RID: 255 RVA: 0x0000939C File Offset: 0x0000759C
		internal string GetMACAddress()
		{
			ManagementClass mc = new ManagementClass("Win32_NetworkAdapterConfiguration");
			ManagementObjectCollection moc = mc.GetInstances();
			string MACAddress = string.Empty;
			try
			{
				foreach (ManagementBaseObject managementBaseObject in moc)
				{
					ManagementObject mo = (ManagementObject)managementBaseObject;
					bool flag = MACAddress.Equals(string.Empty);
					if (flag)
					{
						bool flag2 = Conversions.ToBoolean(mo["IPEnabled"]);
						if (flag2)
						{
							MACAddress = mo["MacAddress"].ToString();
						}
						mo.Dispose();
					}
					MACAddress = MACAddress.Replace(":", string.Empty);
				}
			}
			finally
			{
				ManagementObjectCollection.ManagementObjectEnumerator enumerator;
				if (enumerator != null)
				{
					((IDisposable)enumerator).Dispose();
				}
			}
			return MACAddress;
		}

		// Token: 0x06000100 RID: 256 RVA: 0x00009464 File Offset: 0x00007664
		internal string GetVolumeSerial(string strDriveLetter = "C")
		{
			ManagementObject disk = new ManagementObject(string.Format("win32_logicaldisk.deviceid=\"{0}:\"", strDriveLetter));
			disk.Get();
			return disk["VolumeSerialNumber"].ToString();
		}

		// Token: 0x06000101 RID: 257 RVA: 0x000094A0 File Offset: 0x000076A0
		internal string GetMotherBoardID()
		{
			string strMotherBoardID = string.Empty;
			SelectQuery query = new SelectQuery("Win32_BaseBoard");
			ManagementObjectSearcher search = new ManagementObjectSearcher(query);
			try
			{
				foreach (ManagementBaseObject managementBaseObject in search.Get())
				{
					ManagementObject info = (ManagementObject)managementBaseObject;
					strMotherBoardID = info["product"].ToString();
				}
			}
			finally
			{
				ManagementObjectCollection.ManagementObjectEnumerator enumerator;
				if (enumerator != null)
				{
					((IDisposable)enumerator).Dispose();
				}
			}
			return strMotherBoardID;
		}

		// Token: 0x06000102 RID: 258 RVA: 0x00009528 File Offset: 0x00007728
		internal string getMD5Hash(string strToHash)
		{
			MD5CryptoServiceProvider md5Obj = new MD5CryptoServiceProvider();
			byte[] bytesToHash = Encoding.ASCII.GetBytes(strToHash);
			bytesToHash = md5Obj.ComputeHash(bytesToHash);
			string strResult = "";
			foreach (byte b in bytesToHash)
			{
				strResult += b.ToString("x2");
			}
			return strResult;
		}
	}
}
