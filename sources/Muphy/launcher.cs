using System;
using System.ComponentModel;
using System.Diagnostics;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Runtime.CompilerServices;
using System.Threading;
using System.Windows.Forms;
using Microsoft.VisualBasic.CompilerServices;
using Muphy.My;
using Muphy.My.Resources;
using Siticone.UI.WinForms;

namespace Muphy
{
	// Token: 0x02000011 RID: 17
	[DesignerGenerated]
	public partial class launcher : Form
	{
		// Token: 0x0600014B RID: 331 RVA: 0x00002B36 File Offset: 0x00000D36
		public launcher()
		{
			base.Load += this.launcher_Load;
			this.InitializeComponent();
		}

		// Token: 0x17000089 RID: 137
		// (get) Token: 0x0600014E RID: 334 RVA: 0x00002B56 File Offset: 0x00000D56
		// (set) Token: 0x0600014F RID: 335 RVA: 0x00002B5E File Offset: 0x00000D5E
		internal virtual PictureBox PictureBox1 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700008A RID: 138
		// (get) Token: 0x06000150 RID: 336 RVA: 0x00002B67 File Offset: 0x00000D67
		// (set) Token: 0x06000151 RID: 337 RVA: 0x00009234 File Offset: 0x00007434
		internal virtual System.Windows.Forms.Timer Timer1
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
				System.Windows.Forms.Timer timer = this._Timer1;
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

		// Token: 0x1700008B RID: 139
		// (get) Token: 0x06000152 RID: 338 RVA: 0x00002B6F File Offset: 0x00000D6F
		// (set) Token: 0x06000153 RID: 339 RVA: 0x00002B77 File Offset: 0x00000D77
		internal virtual SiticoneProgressBar SiticoneProgressBar1 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700008C RID: 140
		// (get) Token: 0x06000154 RID: 340 RVA: 0x00002B80 File Offset: 0x00000D80
		// (set) Token: 0x06000155 RID: 341 RVA: 0x00002B88 File Offset: 0x00000D88
		internal virtual SiticoneElipse SiticoneElipse1 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700008D RID: 141
		// (get) Token: 0x06000156 RID: 342 RVA: 0x00002B91 File Offset: 0x00000D91
		// (set) Token: 0x06000157 RID: 343 RVA: 0x00002B99 File Offset: 0x00000D99
		internal virtual SiticoneElipse SiticoneElipse2 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700008E RID: 142
		// (get) Token: 0x06000158 RID: 344 RVA: 0x00002BA2 File Offset: 0x00000DA2
		// (set) Token: 0x06000159 RID: 345 RVA: 0x00002BAA File Offset: 0x00000DAA
		internal virtual SiticoneDragControl SiticoneDragControl1 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700008F RID: 143
		// (get) Token: 0x0600015A RID: 346 RVA: 0x00002BB3 File Offset: 0x00000DB3
		// (set) Token: 0x0600015B RID: 347 RVA: 0x00002BBB File Offset: 0x00000DBB
		internal virtual SiticoneDragControl SiticoneDragControl2 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000090 RID: 144
		// (get) Token: 0x0600015C RID: 348 RVA: 0x00002BC4 File Offset: 0x00000DC4
		// (set) Token: 0x0600015D RID: 349 RVA: 0x00002BCC File Offset: 0x00000DCC
		internal virtual Label Label3 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x0600015E RID: 350 RVA: 0x00009278 File Offset: 0x00007478
		private void launcher_Load(object sender, EventArgs e)
		{
			float num = 0f;
			do
			{
				base.Opacity = (double)num;
				this.Refresh();
				Thread.Sleep(45);
				num += 0.1f;
			}
			while (num <= 1.1f);
		}

		// Token: 0x0600015F RID: 351 RVA: 0x000092B0 File Offset: 0x000074B0
		private void Timer1_Tick(object sender, EventArgs e)
		{
			this.SiticoneProgressBar1.Increment(2);
			if (this.SiticoneProgressBar1.Value == 100)
			{
				base.Hide();
				MyProject.Forms.Form1.Show();
				this.Timer1.Stop();
			}
		}
	}
}
