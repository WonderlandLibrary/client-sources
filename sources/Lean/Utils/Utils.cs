using System;
using System.Linq;
using System.Management;
using System.Security.Cryptography;
using System.Text;

namespace oxybitch.Utils
{
	// Token: 0x02000008 RID: 8
	internal class Utils
	{
		// Token: 0x06000022 RID: 34 RVA: 0x00004EE8 File Offset: 0x000030E8
		public string PegarHWID()
		{
			ManagementObjectSearcher managementObjectSearcher = new ManagementObjectSearcher("SELECT * FROM win32_logicaldisk");
			foreach (ManagementBaseObject managementBaseObject in managementObjectSearcher.Get())
			{
				ManagementObject managementObject = (ManagementObject)managementBaseObject;
				bool flag = managementObject["VolumeSerialNumber"] != null;
				if (flag)
				{
					return managementObject["VolumeSerialNumber"].ToString();
				}
			}
			return string.Empty;
		}

		// Token: 0x06000023 RID: 35 RVA: 0x00004F74 File Offset: 0x00003174
		public string SHA1(string input)
		{
			byte[] source = new SHA1Managed().ComputeHash(Encoding.UTF8.GetBytes(input));
			return string.Join("", (from b in source
			select b.ToString("x2")).ToArray<string>());
		}

		// Token: 0x06000024 RID: 36 RVA: 0x00004FD0 File Offset: 0x000031D0
		public string RandomString(int length)
		{
			return new string((from s in Enumerable.Repeat<string>("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789", length)
			select s[Utils.random.Next(s.Length)]).ToArray<char>());
		}

		// Token: 0x04000031 RID: 49
		private static Random random = new Random();
	}
}
