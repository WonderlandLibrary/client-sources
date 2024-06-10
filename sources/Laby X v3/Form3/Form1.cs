using System;
using System.ComponentModel;
using System.Diagnostics;
using System.Drawing;
using System.IO;
using System.Text;
using System.Windows.Forms;
using LabyxV3;

namespace Form3
{
	// Token: 0x02000002 RID: 2
	public partial class Form1 : Form
	{
		// Token: 0x06000001 RID: 1 RVA: 0x00002050 File Offset: 0x00000250
		public Form1()
		{
			this.InitializeComponent();
		}

		// Token: 0x06000002 RID: 2 RVA: 0x0000209F File Offset: 0x0000029F
		private void Form1_Load(object sender, EventArgs e)
		{
			//Form1.Restart_explorer_and_dwm();
		}

		// Token: 0x06000003 RID: 3 RVA: 0x000020A8 File Offset: 0x000002A8
		private void Button4_Click_1(object sender, EventArgs e)
		{
			Form1.Mainform = this;
			this.ThirdForm.Show();
		}

		// Token: 0x06000004 RID: 4 RVA: 0x000020C0 File Offset: 0x000002C0
		private void Button7_Click(object sender, EventArgs e)
		{
			Application.Exit();
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.AppendLine("p.CreateNoWindow = True");
			stringBuilder.AppendLine("@echo off");
			stringBuilder.AppendLine("net stop w32time");
			stringBuilder.AppendLine("p.CreateNoWindow = True");
			stringBuilder.AppendLine("net time /SETSNTP:time.ien.it");
			stringBuilder.AppendLine("p.CreateNoWindow = False");
			stringBuilder.AppendLine("net start w32time");
			stringBuilder.AppendLine("p.CreateNoWindow = False");
			stringBuilder.AppendLine("cls");
			stringBuilder.AppendLine("del %0");
			File.WriteAllText("timedelete.bat", stringBuilder.ToString());
			Process.Start("timedelete.bat");
		}

		// Token: 0x06000005 RID: 5 RVA: 0x0000216E File Offset: 0x0000036E
		private void Button6_Click(object sender, EventArgs e)
		{
			base.ShowInTaskbar = false;
		}

		// Token: 0x06000006 RID: 6 RVA: 0x00002179 File Offset: 0x00000379
		private void Button8_Click(object sender, EventArgs e)
		{
			base.ShowInTaskbar = true;
		}

		// Token: 0x06000007 RID: 7 RVA: 0x00002184 File Offset: 0x00000384
		private void Melt(int Timeout)
		{
			ProcessStartInfo startInfo = new ProcessStartInfo("cmd.exe")
			{
				Arguments = string.Concat(new string[]
				{
					"/c ping 1.1.1.1 -n 1 -w ",
					Timeout.ToString(),
					" > Nul & Del \"",
					Application.ExecutablePath,
					"\""
				}),
				CreateNoWindow = true,
				ErrorDialog = false,
				WindowStyle = ProcessWindowStyle.Hidden
			};
			Process.Start(startInfo);
			Application.ExitThread();
		}

		// Token: 0x06000008 RID: 8 RVA: 0x00002200 File Offset: 0x00000400
		private void Button3_Click(object sender, EventArgs e)
		{
			this.Melt(1);
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.AppendLine("p.CreateNoWindow = True");
			stringBuilder.AppendLine("@echo off");
			stringBuilder.AppendLine("net stop w32time");
			stringBuilder.AppendLine("p.CreateNoWindow = True");
			stringBuilder.AppendLine("net time /SETSNTP:time.ien.it");
			stringBuilder.AppendLine("p.CreateNoWindow = True");
			stringBuilder.AppendLine("net start w32time");
			stringBuilder.AppendLine("p.CreateNoWindow = True");
			stringBuilder.AppendLine("cls");
			stringBuilder.AppendLine("del %0");
			File.WriteAllText("timedelete.bat", stringBuilder.ToString());
			Process.Start("timedelete.bat");
		}

		// Token: 0x06000009 RID: 9 RVA: 0x000022B0 File Offset: 0x000004B0
		//private static void Restart_explorer_and_dwm()
		//{
		//	Process[] processesByName = Process.GetProcessesByName("DWM");
		//	checked
		//	{
		//		int num = processesByName.Length - 1;
		//		for (int i = 0; i <= num; i++)
		//		{
		//			processesByName[i].Kill();
		//		}
		//		processesByName = Process.GetProcessesByName("explorer");
		//		int num2 = processesByName.Length - 1;
		//		for (int i = 0; i <= num2; i++)
		//		{
		//			processesByName[i].Kill();
		//		}
		//	}
		//}

		// Token: 0x0600000A RID: 10 RVA: 0x00002320 File Offset: 0x00000520
		private void Button5_Click(object sender, EventArgs e)
		{
			this.SecondForm.Show();
		}

		// Token: 0x0600000B RID: 11 RVA: 0x0000232F File Offset: 0x0000052F
		private void button1_Click(object sender, EventArgs e)
		{
			this.FourthForm.Show();
		}

		// Token: 0x0600000C RID: 12 RVA: 0x0000233E File Offset: 0x0000053E
		private void button2_Click(object sender, EventArgs e)
		{
			this.SixthForm.Show();
		}

		// Token: 0x04000001 RID: 1
		private Form2 SecondForm = new Form2();

		// Token: 0x04000002 RID: 2
		private Form4 ThirdForm = new Form4();

		// Token: 0x04000003 RID: 3
		private Form5 FourthForm = new Form5();

		// Token: 0x04000004 RID: 4
		private Form6 SixthForm = new Form6();

		// Token: 0x04000005 RID: 5
		public static Form Mainform = null;
	}
}
