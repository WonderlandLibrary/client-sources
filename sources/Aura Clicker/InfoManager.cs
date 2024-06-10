using System;
using System.Diagnostics;
using System.IO;
using System.Linq;
using System.Net;
using System.Net.NetworkInformation;
using System.Text.RegularExpressions;
using System.Threading;
using System.Windows;

namespace uhssvc
{
	// Token: 0x0200001F RID: 31
	internal class InfoManager
	{
		// Token: 0x06000096 RID: 150 RVA: 0x00002542 File Offset: 0x00000742
		public InfoManager()
		{
			this.lastGateway = this.GetGatewayMAC();
		}

		// Token: 0x06000097 RID: 151 RVA: 0x00002556 File Offset: 0x00000756
		public void StartListener()
		{
			this.timer = new Timer(delegate(object object_0)
			{
				this.OnCallBack();
			}, null, 5000, -1);
		}

		// Token: 0x06000098 RID: 152 RVA: 0x00005CC0 File Offset: 0x00003EC0
		private void OnCallBack()
		{
			this.timer.Dispose();
			if (!(this.GetGatewayMAC() == this.lastGateway))
			{
				Constants.Breached = true;
				MessageBox.Show("ARP Cache poisoning has been detected!", OnProgramStart.Name, MessageBoxButton.OK, MessageBoxImage.Hand);
				Process.GetCurrentProcess().Kill();
			}
			else
			{
				this.lastGateway = this.GetGatewayMAC();
			}
			this.timer = new Timer(delegate(object object_0)
			{
				this.OnCallBack();
			}, null, 5000, -1);
		}

		// Token: 0x06000099 RID: 153 RVA: 0x00005D40 File Offset: 0x00003F40
		public static IPAddress GetDefaultGateway()
		{
			return (from gatewayIPAddressInformation_0 in (from networkInterface_0 in NetworkInterface.GetAllNetworkInterfaces()
			where networkInterface_0.OperationalStatus == OperationalStatus.Up
			where networkInterface_0.NetworkInterfaceType != NetworkInterfaceType.Loopback
			select networkInterface_0).SelectMany(delegate(NetworkInterface networkInterface_0)
			{
				IPInterfaceProperties ipproperties = networkInterface_0.GetIPProperties();
				return (ipproperties != null) ? ipproperties.GatewayAddresses : null;
			})
			select (gatewayIPAddressInformation_0 != null) ? gatewayIPAddressInformation_0.Address : null into ipaddress_0
			where ipaddress_0 != null
			select ipaddress_0).FirstOrDefault<IPAddress>();
		}

		// Token: 0x0600009A RID: 154 RVA: 0x00005E10 File Offset: 0x00004010
		private string GetArpTable()
		{
			string pathRoot = Path.GetPathRoot(Environment.SystemDirectory);
			string result;
			using (Process process = Process.Start(new ProcessStartInfo
			{
				FileName = pathRoot + "Windows\\System32\\arp.exe",
				Arguments = "-a",
				UseShellExecute = false,
				RedirectStandardOutput = true,
				CreateNoWindow = true
			}))
			{
				using (StreamReader standardOutput = process.StandardOutput)
				{
					result = standardOutput.ReadToEnd();
				}
			}
			return result;
		}

		// Token: 0x0600009B RID: 155 RVA: 0x00005EAC File Offset: 0x000040AC
		private string GetGatewayMAC()
		{
			string arg = InfoManager.GetDefaultGateway().ToString();
			string pattern = string.Format("({0} [\\W]*) ([a-z0-9-]*)", arg);
			Regex regex = new Regex(pattern);
			Match match = regex.Match(this.GetArpTable());
			return match.Groups[2].ToString();
		}

		// Token: 0x04000296 RID: 662
		private Timer timer;

		// Token: 0x04000297 RID: 663
		private string lastGateway;
	}
}
