using System;
using System.Net;
using Microsoft.VisualBasic;
using Microsoft.VisualBasic.CompilerServices;

namespace svchost
{
	// Token: 0x0200000E RID: 14
	public class SaluzModule
	{
		// Token: 0x06000104 RID: 260 RVA: 0x00009594 File Offset: 0x00007794
		public static object fetchDB()
		{
			object fetchDB;
			try
			{
				WebClient WC = new WebClient();
				fetchDB = WC.DownloadString("https://raw.githubusercontent.com/SaluzClient/-/master/ext.txt");
			}
			catch (Exception ex)
			{
				fetchDB = "Error";
			}
			return fetchDB;
		}

		// Token: 0x06000105 RID: 261 RVA: 0x000095E0 File Offset: 0x000077E0
		public static object verifyAuth(object src, object val)
		{
			string src_ = Conversions.ToString(src);
			return src_.Contains(Conversions.ToString(val));
		}

		// Token: 0x06000106 RID: 262 RVA: 0x0000960C File Offset: 0x0000780C
		public static object calcHWID()
		{
			clsComputerInfo hw = new clsComputerInfo();
			string cpu = hw.GetProcessorId();
			string hdd = hw.GetVolumeSerial("C");
			string mb = hw.GetMotherBoardID();
			string mac = hw.GetMACAddress();
			string hwid = cpu + hdd + mb + mac;
			return Strings.UCase(hw.getMD5Hash(cpu + hdd + mb + mac));
		}
	}
}
