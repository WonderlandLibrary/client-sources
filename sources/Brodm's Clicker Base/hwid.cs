using System;
using System.Collections.Generic;
using System.Linq;
using System.Management;
using System.Security.Cryptography;
using System.Text;
using System.Threading.Tasks;

namespace skidderino
{
    public class hwid
    {
		public class clsComputerInfo
		{
			public string GetProcessorId()
			{
				string result = string.Empty;
				foreach (ManagementBaseObject managementBaseObject in new ManagementObjectSearcher(new SelectQuery("Win32_processor")).Get())
				{
					result = managementBaseObject.GetPropertyValue("processorId").ToString();
				}
				return result;
			}

			public string GetMACAddress()
			{
				ManagementObjectCollection instances = new ManagementClass("Win32_NetworkAdapterConfiguration").GetInstances();
				string text = string.Empty;
				foreach (ManagementBaseObject managementBaseObject in instances)
				{
					ManagementObject managementObject = (ManagementObject)managementBaseObject;
					if (text.Equals(string.Empty))
					{
						if (Convert.ToBoolean(managementObject.GetPropertyValue("IPEnabled")))
						{
							text = managementObject.GetPropertyValue("MacAddress").ToString();
						}
						managementObject.Dispose();
					}
					text = text.Replace(":", string.Empty);
				}
				return text;
			}

			public string GetVolumeSerial(string strDriveLetter = "C")
			{
				ManagementObject managementObject = new ManagementObject(string.Format("win32_logicaldisk.deviceid=\"{0}:\"", strDriveLetter));
				managementObject.Get();
				return managementObject.GetPropertyValue("VolumeSerialNumber").ToString();
			}

			public string GetMotherBoardID()
			{
				string result = string.Empty;
				foreach (ManagementBaseObject managementBaseObject in new ManagementObjectSearcher(new SelectQuery("Win32_BaseBoard")).Get())
				{
					result = managementBaseObject.GetPropertyValue("product").ToString();
				}
				return result;
			}

			public string getMD5Hash(string strToHash)
			{
				HashAlgorithm hashAlgorithm = new MD5CryptoServiceProvider();
				byte[] array = Encoding.ASCII.GetBytes(strToHash);
				array = hashAlgorithm.ComputeHash(array);
				string text = "";
				foreach (byte b in array)
				{
					text += b.ToString("x2");
				}
				return text;
			}
		}
	}
}