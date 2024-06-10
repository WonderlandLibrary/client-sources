using System;
using System.Diagnostics;
using System.Net;
using System.Windows.Forms;

namespace name
{
	// Token: 0x02000004 RID: 4
	internal static class Program
	{
		// Token: 0x06000012 RID: 18 RVA: 0x00003DFC File Offset: 0x00001FFC
		[STAThread]
		private static void Main()
		{
			WebClient webClient = new WebClient();
			string a = webClient.DownloadString("https://pastebin.com/raw/pzsk1skU");
			bool flag = a == "0.1";
			if (flag)
			{
				bool flag2 = Process.GetProcessesByName("javaw").Length != 0;
				if (flag2)
				{
					bool flag3 = Program.IsApplicationAlreadyRunning();
					if (!flag3)
					{
						Application.EnableVisualStyles();
						Application.SetCompatibleTextRenderingDefault(false);
						Application.Run(new m());
					}
				}
				else
				{
					MessageBox.Show("Minecraft not found.", "", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
					Application.Exit();
				}
			}
			else
			{
				MessageBox.Show("Bad version");
				Process.Start(new ProcessStartInfo
				{
					Arguments = "/C choice /C Y /N /D Y /T 3 & Del \"" + Application.ExecutablePath + "\"",
					WindowStyle = ProcessWindowStyle.Hidden,
					CreateNoWindow = true,
					FileName = "cmd.exe"
				});
			}
		}

		// Token: 0x06000013 RID: 19 RVA: 0x00003ED8 File Offset: 0x000020D8
		private static bool IsApplicationAlreadyRunning()
		{
			string processName = Process.GetCurrentProcess().ProcessName;
			Process[] processesByName = Process.GetProcessesByName(processName);
			return processesByName.Length > 1;
		}
	}
}
