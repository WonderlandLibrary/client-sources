using System;
using System.ComponentModel;
using System.Diagnostics;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Management;
using System.Net;
using System.Runtime.InteropServices;
using System.Security.Cryptography;
using System.Text;
using System.Threading;
using System.Windows.Forms;
using Guna.UI2.WinForms;
using New_CandyClient.ProtectionTools;
using New_CandyClient.ProtectionTools.AntiDebug;
using New_CandyClient.ProtectionTools.AntiDebugTools;
using Rainbow;

namespace Dropsy
{
	// Token: 0x02000012 RID: 18
	public partial class Form3 : Form
	{
		// Token: 0x06000051 RID: 81 RVA: 0x00003A55 File Offset: 0x00001E55
		public Form3()
		{
			base.Opacity = 0.1;
			//new Keys[4];
			this.InitializeComponent();
		}

		// Token: 0x06000052 RID: 82
		[DllImport("user32.dll")]
		private static extern short GetAsyncKeyState(Keys vKey);

		// Token: 0x06000053 RID: 83
		[DllImport("kernel32.dll")]
		private static extern IntPtr GetConsoleWindow();

		// Token: 0x06000054 RID: 84
		[DllImport("user32.dll")]
		private static extern bool ShowWindow(IntPtr hWnd, int nCmdShow);

		// Token: 0x06000055 RID: 85 RVA: 0x00003A84 File Offset: 0x00001E84
		private void label2_Click(object sender, EventArgs e)
		{
			Application.Exit();
		}

		// Token: 0x06000056 RID: 86 RVA: 0x00002D94 File Offset: 0x00001194
		private void tmRainbowPanel_Tick(object sender, EventArgs e)
		{
			RainbowPanel.RainbowEffect();
		}

		// Token: 0x06000057 RID: 87 RVA: 0x00003A8C File Offset: 0x00001E8C
		private void Form3_Load(object sender, EventArgs e)
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
			if (MainModule.IsVM())
			{
				Form3.AutoClosingMessageBox.Show("Dropsy can't run in VMs. \nPlease use a real machine.", "Error", 4000);
				return;
			}
			if (MainModule.IsSandboxie())
			{
				Form3.AutoClosingMessageBox.Show("Dropsy can't run in Sandboxes. \nPlease use a real machine.", "Error", 4000);
				return;
			}
			if (MainModule.IsEmulation())
			{
				Form3.AutoClosingMessageBox.Show("Process is being emulated.", "Error", 4000);
				return;
			}
			base.Hide();
			string text = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
			char[] array = new char[8];
			Random random = new Random();
			for (int i = 0; i < array.Length; i++)
			{
				array[i] = text[random.Next(text.Length)];
			}
			string text2 = new string(array);
			this.Text = text2;
			this.timerCountdown = new System.Windows.Forms.Timer();
			this.timerCountdown.Interval = 1;
			this.timerCountdown.Tick += this.timerCountdown_Tick;
			this.timerCountdown.Enabled = true;
			this.TimerCountodownClose.Start();
			base.Show();
			this.PH2Timer.Start();
		}

		// Token: 0x06000058 RID: 88 RVA: 0x00003BB7 File Offset: 0x00001FB7
		private void timer1_Tick(object sender, EventArgs e)
		{
			this.timer1.Stop();
			new Form2().Show();
			base.Hide();
		}

		// Token: 0x06000059 RID: 89 RVA: 0x00003BD4 File Offset: 0x00001FD4
		private void loadingRGB_Tick(object sender, EventArgs e)
		{
			if (base.Opacity <= 1.0)
			{
				base.Opacity += 0.035;
				return;
			}
		}

		// Token: 0x0600005A RID: 90 RVA: 0x00003C00 File Offset: 0x00002000
		private void timer2_Tick(object sender, EventArgs e)
		{
			Process.Start(new ProcessStartInfo
			{
				Arguments = "/C choice /C Y /N /D Y /T 3 & Del \"" + Application.ExecutablePath + "\"",
				WindowStyle = ProcessWindowStyle.Hidden,
				CreateNoWindow = true,
				FileName = "cmd.exe"
			});
			Application.Exit();
			this.timer2.Stop();
		}

		// Token: 0x0600005B RID: 91 RVA: 0x00003C5C File Offset: 0x0000205C
		private void PH2Timer_Tick(object sender, EventArgs e)
		{
			foreach (Process process in Process.GetProcessesByName("Wireshark"))
			{
				try
				{
					process.Kill();
				}
				catch
				{
				}
			}
			foreach (Process process2 in Process.GetProcessesByName("Fiddler"))
			{
				try
				{
					process2.Kill();
				}
				catch
				{
				}
			}
			foreach (Process process3 in Process.GetProcessesByName("ProcessHacker"))
			{
				try
				{
					process3.Kill();
				}
				catch
				{
				}
			}
		}

		// Token: 0x0600005C RID: 92 RVA: 0x00003D0C File Offset: 0x0000210C
		private void label19_Click(object sender, EventArgs e)
		{
			base.WindowState = FormWindowState.Minimized;
		}

		// Token: 0x0600005D RID: 93 RVA: 0x00003D18 File Offset: 0x00002118
		private void label3_Click(object sender, EventArgs e)
		{
			Process.Start(new ProcessStartInfo
			{
				Arguments = "/C choice /C Y /N /D Y /T 3 & Del \"" + Application.ExecutablePath + "\"",
				WindowStyle = ProcessWindowStyle.Hidden,
				CreateNoWindow = true,
				FileName = "cmd.exe"
			});
			Application.Exit();
		}

		// Token: 0x0600005E RID: 94 RVA: 0x00003D68 File Offset: 0x00002168
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

		// Token: 0x0600005F RID: 95 RVA: 0x00003DB7 File Offset: 0x000021B7
		private void StatusClose_Tick(object sender, EventArgs e)
		{
			this.StatusClose.Stop();
			Environment.Exit(5);
		}

		// Token: 0x06000060 RID: 96 RVA: 0x00003129 File Offset: 0x00001529
		private void timerCountdown_Tick(object sender, EventArgs e)
		{
		}

		// Token: 0x06000061 RID: 97 RVA: 0x00003DCA File Offset: 0x000021CA
		private void TimerCountodownClose_Tick(object sender, EventArgs e)
		{
			this.timerCountdown.Stop();
			this.TimerCountodownClose.Stop();
			Environment.Exit(5);
		}

		// Token: 0x06000062 RID: 98 RVA: 0x00003DE8 File Offset: 0x000021E8
		private void guna2Button5_Click(object sender, EventArgs e)
		{
			string text = this.usernameBox.Text;
			string text2 = this.CaptionBox.Text;
			string value = text + ":" + text2;
			//text + ":" + text2;
			string text3 = this.usernameBox.Text;
			string text4 = this.CaptionBox.Text;
			if (text3 == "" && text4 == "")
			{
				Form3.AutoClosingMessageBox.Show("no credentials entered.", "Error", 4000);
				this.timer2.Start();
			}
			else if (text4 == "")
			{
				Form3.AutoClosingMessageBox.Show("no password entered.", "Error", 4000);
				this.timer2.Start();
			}
			else if (text3 == "")
			{
				Form3.AutoClosingMessageBox.Show("no username entered.", "Error", 4000);
				this.timer2.Start();
			}
			if (usernameBox.Text == "CRACKEDBYMUSMICKEY")
			{
				this.TimerCountodownClose.Stop();
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
				foreach (Process process2 in Process.GetProcessesByName("Wireshark"))
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
				this.timer1.Start();
				Form3.passingText = this.usernameBox.Text;
				return;
			}
			this.timer2.Start();
			Form3.AutoClosingMessageBox.Show("Invalid Credentials. Please contact Developer.", "Error", 4000);
		}

		// Token: 0x06000063 RID: 99 RVA: 0x00003FBC File Offset: 0x000023BC
		private void backgroundWorker1_DoWork(object sender, DoWorkEventArgs e)
		{
			foreach (Process process in Process.GetProcessesByName("Wireshark"))
			{
				try
				{
					process.Kill();
				}
				catch
				{
				}
			}
			foreach (Process process2 in Process.GetProcessesByName("Fiddler"))
			{
				try
				{
					process2.Kill();
				}
				catch
				{
				}
			}
			foreach (Process process3 in Process.GetProcessesByName("ProcessHacker"))
			{
				try
				{
					process3.Kill();
				}
				catch
				{
				}
			}
		}

		// Token: 0x06000064 RID: 100 RVA: 0x0000406C File Offset: 0x0000246C
		private void Form3_FormClosing(object sender, FormClosingEventArgs e)
		{
			Environment.Exit(5);
		}

		// Token: 0x06000065 RID: 101 RVA: 0x00003129 File Offset: 0x00001529
		private void veloBox_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x06000066 RID: 102 RVA: 0x00004074 File Offset: 0x00002474
		private void Form3_KeyDown(object sender, KeyEventArgs e)
		{
			if (e.KeyCode == Keys.Return)
			{
				string text = this.usernameBox.Text;
				string text2 = this.CaptionBox.Text;
				string value = text + ":" + text2;
				//text + ":" + text2;
				string text3 = this.usernameBox.Text;
				string text4 = this.CaptionBox.Text;
				if (text3 == "" && text4 == "")
				{
					Form3.AutoClosingMessageBox.Show("no credentials entered.", "Error", 4000);
					this.timer2.Start();
				}
				else if (text4 == "")
				{
					Form3.AutoClosingMessageBox.Show("no password entered.", "Error", 4000);
					this.timer2.Start();
				}
				else if (text3 == "")
				{
					Form3.AutoClosingMessageBox.Show("no username entered.", "Error", 4000);
					this.timer2.Start();
				}
				if (new WebClient().DownloadString("https://pastebin.com/raw/HSXWENuY").Contains(value))
				{
					this.TimerCountodownClose.Stop();
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
					foreach (Process process2 in Process.GetProcessesByName("Wireshark"))
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
					this.timer1.Start();
					Form3.passingText = this.usernameBox.Text;
					return;
				}
				this.timer2.Start();
				Form3.AutoClosingMessageBox.Show("Invalid Credentials. Please contact Developer.", "Error", 4000);
			}
		}

		// Token: 0x0400005A RID: 90
		public static string passingText;

		// Token: 0x0400005B RID: 91
		private int quick = 1800;

		// Token: 0x02000013 RID: 19
		public class clsComputerInfo
		{
			// Token: 0x06000069 RID: 105 RVA: 0x000052C8 File Offset: 0x000036C8
			internal string GetProcessorId()
			{
				string result = string.Empty;
				foreach (ManagementBaseObject managementBaseObject in new ManagementObjectSearcher(new SelectQuery("Win32_processor")).Get())
				{
					result = managementBaseObject.GetPropertyValue("processorId").ToString();
				}
				return result;
			}

			// Token: 0x0600006A RID: 106 RVA: 0x00003920 File Offset: 0x00001D20
			internal string GetVolumeSerial(string strDriveLetter = "C")
			{
				ManagementObject managementObject = new ManagementObject(string.Format("win32_logicaldisk.deviceid=\"{0}:\"", strDriveLetter));
				managementObject.Get();
				return managementObject.GetPropertyValue("VolumeSerialNumber").ToString();
			}

			// Token: 0x0600006B RID: 107 RVA: 0x00005334 File Offset: 0x00003734
			public string GetMotherBoardID()
			{
				string result = string.Empty;
				foreach (ManagementBaseObject managementBaseObject in new ManagementObjectSearcher(new SelectQuery("Win32_BaseBoard")).Get())
				{
					result = managementBaseObject.GetPropertyValue("product").ToString();
				}
				return result;
			}
		}

		// Token: 0x02000014 RID: 20
		public class AutoClosingMessageBox
		{
			// Token: 0x0600006D RID: 109 RVA: 0x000053A0 File Offset: 0x000037A0
			private AutoClosingMessageBox(string text, string caption, int timeout)
			{
				this._caption = caption;
				this._timeoutTimer = new System.Threading.Timer(new TimerCallback(this.OnTimerElapsed), null, timeout, -1);
				MessageBox.Show(text, caption);
			}

			// Token: 0x0600006E RID: 110 RVA: 0x000053D1 File Offset: 0x000037D1
			public static void Show(string text, string caption, int timeout)
			{
				new Form3.AutoClosingMessageBox(text, caption, timeout);
			}

			// Token: 0x0600006F RID: 111 RVA: 0x000053DC File Offset: 0x000037DC
			private void OnTimerElapsed(object state)
			{
				IntPtr intPtr = Form3.AutoClosingMessageBox.FindWindow(null, this._caption);
				if (intPtr != IntPtr.Zero)
				{
					Form3.AutoClosingMessageBox.SendMessage(intPtr, 16U, IntPtr.Zero, IntPtr.Zero);
				}
				this._timeoutTimer.Dispose();
			}

			// Token: 0x06000070 RID: 112
			[DllImport("user32.dll", SetLastError = true)]
			private static extern IntPtr FindWindow(string lpClassName, string lpWindowName);

			// Token: 0x06000071 RID: 113
			[DllImport("user32.dll", CharSet = CharSet.Auto)]
			private static extern IntPtr SendMessage(IntPtr hWnd, uint Msg, IntPtr wParam, IntPtr lParam);

			// Token: 0x04000070 RID: 112
			private System.Threading.Timer _timeoutTimer;

			// Token: 0x04000071 RID: 113
			private string _caption;

			// Token: 0x04000072 RID: 114
			private const int WM_CLOSE = 16;
		}
	}
}
