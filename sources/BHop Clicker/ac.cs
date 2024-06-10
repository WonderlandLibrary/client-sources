using System;
using System.ComponentModel;
using System.Diagnostics;
using System.Drawing;
using System.Runtime.CompilerServices;
using System.Runtime.InteropServices;
using System.Windows.Forms;
using Microsoft.VisualBasic;
using Microsoft.VisualBasic.CompilerServices;

namespace shit_temple
{
	// Token: 0x0200000B RID: 11
	[DesignerGenerated]
	public partial class ac : Form
	{
		// Token: 0x06000025 RID: 37 RVA: 0x0000225A File Offset: 0x0000045A
		public ac()
		{
			base.Load += this.ac_Load;
			base.Closed += this.ac_Closed;
			this.InitializeComponent();
		}

		// Token: 0x1700000C RID: 12
		// (get) Token: 0x06000028 RID: 40 RVA: 0x0000228C File Offset: 0x0000048C
		// (set) Token: 0x06000029 RID: 41 RVA: 0x00002294 File Offset: 0x00000494
		internal virtual HexTheme HexTheme1 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700000D RID: 13
		// (get) Token: 0x0600002A RID: 42 RVA: 0x0000229D File Offset: 0x0000049D
		// (set) Token: 0x0600002B RID: 43 RVA: 0x00002E64 File Offset: 0x00001064
		internal virtual HexButton HexButton1
		{
			[CompilerGenerated]
			get
			{
				return this._HexButton1;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.HexButton1_Click);
				HexButton hexButton = this._HexButton1;
				if (hexButton != null)
				{
					hexButton.Click -= value2;
				}
				this._HexButton1 = value;
				hexButton = this._HexButton1;
				if (hexButton != null)
				{
					hexButton.Click += value2;
				}
			}
		}

		// Token: 0x1700000E RID: 14
		// (get) Token: 0x0600002C RID: 44 RVA: 0x000022A5 File Offset: 0x000004A5
		// (set) Token: 0x0600002D RID: 45 RVA: 0x00002EA8 File Offset: 0x000010A8
		internal virtual Timer Timer1
		{
			[CompilerGenerated]
			get
			{
				return this._Timer1;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.Timer1_Tick);
				Timer timer = this._Timer1;
				if (timer != null)
				{
					timer.Tick -= value2;
				}
				this._Timer1 = value;
				timer = this._Timer1;
				if (timer != null)
				{
					timer.Tick += value2;
				}
			}
		}

		// Token: 0x1700000F RID: 15
		// (get) Token: 0x0600002E RID: 46 RVA: 0x000022AD File Offset: 0x000004AD
		// (set) Token: 0x0600002F RID: 47 RVA: 0x00002EEC File Offset: 0x000010EC
		internal virtual Label Label1
		{
			[CompilerGenerated]
			get
			{
				return this._Label1;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.Label1_Click);
				Label label = this._Label1;
				if (label != null)
				{
					label.Click -= value2;
				}
				this._Label1 = value;
				label = this._Label1;
				if (label != null)
				{
					label.Click += value2;
				}
			}
		}

		// Token: 0x17000010 RID: 16
		// (get) Token: 0x06000030 RID: 48 RVA: 0x000022B5 File Offset: 0x000004B5
		// (set) Token: 0x06000031 RID: 49 RVA: 0x000022BD File Offset: 0x000004BD
		internal virtual HexClose HexClose1 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000011 RID: 17
		// (get) Token: 0x06000032 RID: 50 RVA: 0x000022C6 File Offset: 0x000004C6
		// (set) Token: 0x06000033 RID: 51 RVA: 0x000022CE File Offset: 0x000004CE
		internal virtual HexMini HexMini1 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x06000034 RID: 52
		[DllImport("user32", CharSet = CharSet.Ansi, EntryPoint = "mouse_event", ExactSpelling = true, SetLastError = true)]
		private static extern bool apimouse_event(int dwFlags, int dX, int dY, int cButtons, int dwExtraInfo);

		// Token: 0x06000035 RID: 53 RVA: 0x000022D7 File Offset: 0x000004D7
		private void Timer1_Tick(object sender, EventArgs e)
		{
			if (Control.MouseButtons == MouseButtons.Left)
			{
				ac.apimouse_event(4, 0, 0, 0, 0);
				ac.apimouse_event(2, 0, 0, 0, 0);
			}
		}

		// Token: 0x06000036 RID: 54 RVA: 0x000022FB File Offset: 0x000004FB
		private void HexButton3_Click(object sender, EventArgs e)
		{
			Interaction.MsgBox("If ur Getting ss`ed Remove the file!", MsgBoxStyle.OkOnly, null);
			base.Close();
		}

		// Token: 0x06000037 RID: 55 RVA: 0x00002F30 File Offset: 0x00001130
		private void HexButton1_Click(object sender, EventArgs e)
		{
			if (Operators.CompareString(this.HexButton1.Text, "Stop", false) == 0)
			{
				this.HexButton1.Text = "Start";
				this.Timer1.Stop();
				return;
			}
			if (Operators.CompareString(this.HexButton1.Text, "Start", false) == 0)
			{
				this.HexButton1.Text = "Stop";
				this.Timer1.Start();
			}
		}

		// Token: 0x06000038 RID: 56 RVA: 0x00002310 File Offset: 0x00000510
		private void Label1_Click(object sender, EventArgs e)
		{
			Process.Start("https://youtube.com/bhopminecraft");
		}

		// Token: 0x06000039 RID: 57 RVA: 0x0000231D File Offset: 0x0000051D
		private void ac_Load(object sender, EventArgs e)
		{
			this.Timer1.Interval = 75;
		}

		// Token: 0x0600003A RID: 58 RVA: 0x0000232C File Offset: 0x0000052C
		private void ac_Closed(object sender, EventArgs e)
		{
			base.Close();
			this.HexButton1.Dispose();
			this.HexTheme1.Dispose();
			this.HexClose1.Dispose();
		}

		// Token: 0x04000015 RID: 21
		public const int MOUSEEVENTF_LEFTDOWN = 2;

		// Token: 0x04000016 RID: 22
		public const int MOUSEEVENTF_LEFTUP = 4;

		// Token: 0x04000017 RID: 23
		public const int MOUSEEVENTF_MIDDLEDOWN = 32;

		// Token: 0x04000018 RID: 24
		public const int MOUSEEVENTF_MIDDLEUP = 64;

		// Token: 0x04000019 RID: 25
		public const int MOUSEEVENTF_RIGHTDOWN = 8;

		// Token: 0x0400001A RID: 26
		public const int MOUSEEVENTF_RIGHTUP = 16;

		// Token: 0x0400001B RID: 27
		public const int MOUSEEVENTF_MOVE = 1;
	}
}
