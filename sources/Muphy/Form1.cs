using System;
using System.ComponentModel;
using System.Diagnostics;
using System.Drawing;
using System.Runtime.CompilerServices;
using System.Windows.Forms;
using Microsoft.VisualBasic;
using Microsoft.VisualBasic.CompilerServices;
using Muphy.My;
using Muphy.My.Resources;
using Siticone.UI.WinForms;

namespace Muphy
{
	// Token: 0x0200000F RID: 15
	[DesignerGenerated]
	public partial class Form1 : Form
	{
		// Token: 0x06000120 RID: 288 RVA: 0x000029C2 File Offset: 0x00000BC2
		public Form1()
		{
			base.Load += this.Form1_Load;
			this.InitializeComponent();
		}

		// Token: 0x06000121 RID: 289 RVA: 0x00007F84 File Offset: 0x00006184
		private void Form1_Load(object sender, EventArgs e)
		{
			if (MyProject.Computer.Network.IsAvailable)
			{
				Interaction.MsgBox("Conected", MsgBoxStyle.OkOnly, null);
			}
			else
			{
				Interaction.MsgBox("You not online, connect and try again", MsgBoxStyle.OkOnly, null);
				Application.Exit();
			}
		}

		// Token: 0x06000122 RID: 290 RVA: 0x000029E2 File Offset: 0x00000BE2
		private void SiticoneButton1_Click(object sender, EventArgs e)
		{
			MyProject.Forms.hwidacess.Show();
			base.Hide();
		}

		// Token: 0x06000123 RID: 291 RVA: 0x00002900 File Offset: 0x00000B00
		private void SiticoneControlBox1_Click(object sender, EventArgs e)
		{
			Application.Exit();
		}

		// Token: 0x06000124 RID: 292 RVA: 0x00002900 File Offset: 0x00000B00
		private void Timer1_Tick(object sender, EventArgs e)
		{
			Application.Exit();
		}

		// Token: 0x1700007A RID: 122
		// (get) Token: 0x06000127 RID: 295 RVA: 0x000029F9 File Offset: 0x00000BF9
		// (set) Token: 0x06000128 RID: 296 RVA: 0x00002A01 File Offset: 0x00000C01
		internal virtual PictureBox PictureBox2 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700007B RID: 123
		// (get) Token: 0x06000129 RID: 297 RVA: 0x00002A0A File Offset: 0x00000C0A
		// (set) Token: 0x0600012A RID: 298 RVA: 0x00002A12 File Offset: 0x00000C12
		internal virtual SiticoneMaterialTextBox User1 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700007C RID: 124
		// (get) Token: 0x0600012B RID: 299 RVA: 0x00002A1B File Offset: 0x00000C1B
		// (set) Token: 0x0600012C RID: 300 RVA: 0x00002A23 File Offset: 0x00000C23
		internal virtual SiticoneMaterialTextBox Pass1 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700007D RID: 125
		// (get) Token: 0x0600012D RID: 301 RVA: 0x00002A2C File Offset: 0x00000C2C
		// (set) Token: 0x0600012E RID: 302 RVA: 0x00008934 File Offset: 0x00006B34
		internal virtual SiticoneButton SiticoneButton1
		{
			[CompilerGenerated]
			get
			{
				return this._SiticoneButton1;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.SiticoneButton1_Click);
				SiticoneButton siticoneButton = this._SiticoneButton1;
				if (siticoneButton != null)
				{
					siticoneButton.Click -= value2;
				}
				this._SiticoneButton1 = value;
				siticoneButton = this._SiticoneButton1;
				if (siticoneButton != null)
				{
					siticoneButton.Click += value2;
				}
			}
		}

		// Token: 0x1700007E RID: 126
		// (get) Token: 0x0600012F RID: 303 RVA: 0x00002A34 File Offset: 0x00000C34
		// (set) Token: 0x06000130 RID: 304 RVA: 0x00002A3C File Offset: 0x00000C3C
		internal virtual SiticoneDragControl SiticoneDragControl1 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700007F RID: 127
		// (get) Token: 0x06000131 RID: 305 RVA: 0x00002A45 File Offset: 0x00000C45
		// (set) Token: 0x06000132 RID: 306 RVA: 0x00002A4D File Offset: 0x00000C4D
		internal virtual SiticoneDragControl SiticoneDragControl2 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000080 RID: 128
		// (get) Token: 0x06000133 RID: 307 RVA: 0x00002A56 File Offset: 0x00000C56
		// (set) Token: 0x06000134 RID: 308 RVA: 0x00008978 File Offset: 0x00006B78
		internal virtual SiticoneControlBox SiticoneControlBox1
		{
			[CompilerGenerated]
			get
			{
				return this._SiticoneControlBox1;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.SiticoneControlBox1_Click);
				SiticoneControlBox siticoneControlBox = this._SiticoneControlBox1;
				if (siticoneControlBox != null)
				{
					siticoneControlBox.Click -= value2;
				}
				this._SiticoneControlBox1 = value;
				siticoneControlBox = this._SiticoneControlBox1;
				if (siticoneControlBox != null)
				{
					siticoneControlBox.Click += value2;
				}
			}
		}

		// Token: 0x17000081 RID: 129
		// (get) Token: 0x06000135 RID: 309 RVA: 0x00002A5E File Offset: 0x00000C5E
		// (set) Token: 0x06000136 RID: 310 RVA: 0x00002A66 File Offset: 0x00000C66
		internal virtual SiticoneControlBox SiticoneControlBox2 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000082 RID: 130
		// (get) Token: 0x06000137 RID: 311 RVA: 0x00002A6F File Offset: 0x00000C6F
		// (set) Token: 0x06000138 RID: 312 RVA: 0x00002A77 File Offset: 0x00000C77
		internal virtual SiticoneDragControl SiticoneDragControl3 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000083 RID: 131
		// (get) Token: 0x06000139 RID: 313 RVA: 0x00002A80 File Offset: 0x00000C80
		// (set) Token: 0x0600013A RID: 314 RVA: 0x000089BC File Offset: 0x00006BBC
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
	}
}
