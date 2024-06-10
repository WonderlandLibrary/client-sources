using System;
using System.ComponentModel;
using System.Diagnostics;
using System.Drawing;
using System.Management;
using System.Net;
using System.Net.NetworkInformation;
using System.Runtime.InteropServices;
using System.Security.Cryptography;
using System.Text;
using System.Threading;
using System.Windows.Forms;
using New_CandyClient.ProtectionTools.AntiDebug;
using New_CandyClient.ProtectionTools.AntiDebugTools;
using Rainbow;

namespace Dropsy
{
	// Token: 0x0200000E RID: 14
	public partial class Form1 : Form
	{
		// Token: 0x06000030 RID: 48 RVA: 0x00002D3E File Offset: 0x0000113E
		public Form1()
		{
			this.InitializeComponent();
		}

		// Token: 0x06000031 RID: 49
		[DllImport("kernel32.dll")]
		public static extern IntPtr ZeroMemory(IntPtr addr, IntPtr size);

		// Token: 0x06000032 RID: 50
		[DllImport("kernel32.dll")]
		public static extern IntPtr VirtualProtect(IntPtr lpAddress, IntPtr dwSize, IntPtr flNewProtect, ref IntPtr lpflOldProtect);

		// Token: 0x06000033 RID: 51 RVA: 0x00002D4C File Offset: 0x0000114C
		public static void EraseSection(IntPtr address, int size)
		{
			IntPtr intPtr = (IntPtr)size;
			IntPtr flNewProtect = (IntPtr)0;
			Form1.VirtualProtect(address, intPtr, (IntPtr)64, ref flNewProtect);
			Form1.ZeroMemory(address, intPtr);
			IntPtr intPtr2 = (IntPtr)0;
			Form1.VirtualProtect(address, intPtr, flNewProtect, ref intPtr2);
		}

		// Token: 0x06000034 RID: 52 RVA: 0x00002D94 File Offset: 0x00001194
		private void tmRainbowPanel_Tick(object sender, EventArgs e)
		{
			RainbowPanel.RainbowEffect();
		}

		// Token: 0x06000035 RID: 53 RVA: 0x00002D94 File Offset: 0x00001194
		private void loadingRGB_Tick(object sender, EventArgs e)
		{
			RainbowPanel.RainbowEffect();
		}

		// Token: 0x06000036 RID: 54 RVA: 0x00002D9C File Offset: 0x0000119C
		private void Form1_Load(object sender, EventArgs e)
		{
			new Thread(delegate()
			{
				for (;;)
				{
					Scanner.ScanAndKill();
					if (DebugProtect1.PerformChecks() == 1)
					{
						Environment.FailFast("");
					}
					if (DebugProtect2.PerformChecks() == 1)
					{
						Environment.FailFast("");
					}
					Thread.Sleep(1000);
				}
			});
			foreach (Process process in Process.GetProcessesByName("Fiddler"))
			{
				try
				{
					process.Kill();
					Application.Exit();
				}
				catch
				{
				}
			}
			foreach (Process process2 in Process.GetProcessesByName("ProcessHacker"))
			{
				try
				{
					process2.Kill();
					Application.Exit();
				}
				catch
				{
				}
			}
			string text = new WebClient().DownloadString("https://pastebin.com/Vbe7X2RH");
			string value = "0.4.1";
			if (text.Contains(value))
			{
				this.Status.Start();
				return;
			}
			Form1.AutoClosingMessageBox.Show("Outdated Version \nPlease contact developer. \n \n mich#0653", "Unhandled Exeption", 1000);
			Environment.Exit(0);
		}

		// Token: 0x06000037 RID: 55 RVA: 0x00002E8C File Offset: 0x0000128C
		private void version2_Tick(object sender, EventArgs e)
		{
			this.count.Stop();
			this.version.Stop();
			this.version2.Stop();
		}

		// Token: 0x06000038 RID: 56 RVA: 0x00002EAF File Offset: 0x000012AF
		private void count_Tick(object sender, EventArgs e)
		{
			this.count.Stop();
		}

		// Token: 0x06000039 RID: 57 RVA: 0x00002EBC File Offset: 0x000012BC
		private void checkersTimer_Tick(object sender, EventArgs e)
		{
			this.VersionCheck.Start();
		}

		// Token: 0x0600003A RID: 58 RVA: 0x00002EC9 File Offset: 0x000012C9
		private void VersionCheck_Tick(object sender, EventArgs e)
		{
			this.HWIDCheck.Start();
			this.VersionCheck.Stop();
			this.checkersTimer.Stop();
		}

		// Token: 0x0600003B RID: 59 RVA: 0x00002EEC File Offset: 0x000012EC
		private static string sha256(string randomString)
		{
			HashAlgorithm hashAlgorithm = new SHA256Managed();
			string text = string.Empty;
			foreach (byte b in hashAlgorithm.ComputeHash(Encoding.ASCII.GetBytes(randomString)))
			{
				text += b.ToString("x2");
			}
			return text;
		}

		// Token: 0x0600003C RID: 60 RVA: 0x00002F3C File Offset: 0x0000133C
		private void HWIDCheck_Tick(object sender, EventArgs e)
		{
			this.Status.Stop();
			WebClient webClient = new WebClient();
			Form1.clsComputerInfo clsComputerInfo = new Form1.clsComputerInfo();
			string processorId = clsComputerInfo.GetProcessorId();
			string volumeSerial = clsComputerInfo.GetVolumeSerial("C");
			string motherBoardID = clsComputerInfo.GetMotherBoardID();
			string value = Form1.sha256(processorId + volumeSerial + motherBoardID);
			if (webClient.DownloadString("https://pastebin.com/raw/nRuLHA8s").Contains(value))
			{
				this.label2.Text = "clearing strings.";
				Thread.Sleep(600);
				this.label2.Text = "authenticated!";
				Thread.Sleep(600);
				Form2 form = new Form2();
				Form form2 = new Form1();
				form.Show();
				form2.Close();
			}
			else
			{
				this.HWIDCheck.Stop();
				Form1.AutoClosingMessageBox.Show("Hardware Mismatch", "Unhandled Exeption", 1000);
				Application.Exit();
			}
			this.HWIDCheck.Stop();
		}

		// Token: 0x0600003D RID: 61 RVA: 0x00003014 File Offset: 0x00001414
		private void Status_Tick(object sender, EventArgs e)
		{
			IPGlobalProperties ipglobalProperties = IPGlobalProperties.GetIPGlobalProperties();
			ipglobalProperties.GetActiveTcpListeners();
			TcpConnectionInformation[] activeTcpConnections = ipglobalProperties.GetActiveTcpConnections();
			WebClient webClient = new WebClient();
			PingReply pingReply = new Ping().Send(IPAddress.Parse("104.23.98.190"));
			webClient.DownloadString("https://pastebin.com/raw/HSXWENuY");
			string text = webClient.DownloadString("http://icanhazip.com/");
			if (pingReply.Status == IPStatus.Success)
			{
				foreach (TcpConnectionInformation tcpConnectionInformation in activeTcpConnections)
				{
					this.label2.Text = string.Concat(new string[]
					{
						"connection: ",
						tcpConnectionInformation.State.ToString().ToLower(),
						"\nlocal : ",
						text.ToLower(),
						"\nenumerating query."
					});
				}
				this.HWIDCheck.Start();
			}
			else
			{
				Form1.AutoClosingMessageBox.Show("Server Unavailable, please try again later.", "Connection Error", 1000);
				Environment.Exit(0);
			}
			this.Status.Stop();
		}

		// Token: 0x0600003E RID: 62 RVA: 0x0000310D File Offset: 0x0000150D
		private void StatusClose_Tick(object sender, EventArgs e)
		{
			this.StatusClose.Stop();
			Thread.Sleep(1500);
			Application.Exit();
		}

		// Token: 0x0600003F RID: 63 RVA: 0x00003129 File Offset: 0x00001529
		private void shadowLabel1_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x06000040 RID: 64 RVA: 0x00003129 File Offset: 0x00001529
		private void version_Tick(object sender, EventArgs e)
		{
		}

		// Token: 0x0200000F RID: 15
		public class AutoClosingMessageBox
		{
			// Token: 0x06000043 RID: 67 RVA: 0x0000378B File Offset: 0x00001B8B
			private AutoClosingMessageBox(string text, string caption, int timeout)
			{
				this._caption = caption;
				this._timeoutTimer = new System.Threading.Timer(new TimerCallback(this.OnTimerElapsed), null, timeout, -1);
				MessageBox.Show(text, caption);
			}

			// Token: 0x06000044 RID: 68 RVA: 0x000037BC File Offset: 0x00001BBC
			public static void Show(string text, string caption, int timeout)
			{
				new Form1.AutoClosingMessageBox(text, caption, timeout);
			}

			// Token: 0x06000045 RID: 69 RVA: 0x000037C8 File Offset: 0x00001BC8
			private void OnTimerElapsed(object state)
			{
				IntPtr intPtr = Form1.AutoClosingMessageBox.FindWindow(null, this._caption);
				if (intPtr != IntPtr.Zero)
				{
					Form1.AutoClosingMessageBox.SendMessage(intPtr, 16U, IntPtr.Zero, IntPtr.Zero);
				}
				this._timeoutTimer.Dispose();
			}

			// Token: 0x06000046 RID: 70
			[DllImport("user32.dll", SetLastError = true)]
			private static extern IntPtr FindWindow(string lpClassName, string lpWindowName);

			// Token: 0x06000047 RID: 71
			[DllImport("user32.dll", CharSet = CharSet.Auto)]
			private static extern IntPtr SendMessage(IntPtr hWnd, uint Msg, IntPtr wParam, IntPtr lParam);

			// Token: 0x04000055 RID: 85
			private System.Threading.Timer _timeoutTimer;

			// Token: 0x04000056 RID: 86
			private string _caption;

			// Token: 0x04000057 RID: 87
			private const int WM_CLOSE = 16;
		}

		// Token: 0x02000010 RID: 16
		public class clsComputerInfo
		{
			// Token: 0x06000048 RID: 72 RVA: 0x00003810 File Offset: 0x00001C10
			internal string GetProcessorId()
			{
				string result = string.Empty;
				foreach (ManagementBaseObject managementBaseObject in new ManagementObjectSearcher(new SelectQuery("Win32_processor")).Get())
				{
					result = managementBaseObject.GetPropertyValue("processorId").ToString();
				}
				return result;
			}

			// Token: 0x06000049 RID: 73 RVA: 0x0000387C File Offset: 0x00001C7C
			internal string GetMACAddress()
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

			// Token: 0x0600004A RID: 74 RVA: 0x00003920 File Offset: 0x00001D20
			internal string GetVolumeSerial(string strDriveLetter = "C")
			{
				ManagementObject managementObject = new ManagementObject(string.Format("win32_logicaldisk.deviceid=\"{0}:\"", strDriveLetter));
				managementObject.Get();
				return managementObject.GetPropertyValue("VolumeSerialNumber").ToString();
			}

			// Token: 0x0600004B RID: 75 RVA: 0x00003948 File Offset: 0x00001D48
			public string GetMotherBoardID()
			{
				string result = string.Empty;
				foreach (ManagementBaseObject managementBaseObject in new ManagementObjectSearcher(new SelectQuery("Win32_BaseBoard")).Get())
				{
					result = managementBaseObject.GetPropertyValue("product").ToString();
				}
				return result;
			}

			// Token: 0x0600004C RID: 76 RVA: 0x000039B4 File Offset: 0x00001DB4
			internal string getMD5Hash(string strToHash)
			{
				HashAlgorithm hashAlgorithm = new MD5CryptoServiceProvider();
				byte[] array = Encoding.ASCII.GetBytes(strToHash);
				array = hashAlgorithm.ComputeHash(array);
				string result = "";
				string str = string.Empty;
				foreach (byte b in array)
				{
					str += b.ToString("x2");
				}
				return result;
			}
		}
	}
}
