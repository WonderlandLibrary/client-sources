using System;
using System.ComponentModel;
using System.Diagnostics;
using System.Drawing;
using System.Runtime.CompilerServices;
using System.Windows.Forms;
using Microsoft.VisualBasic.CompilerServices;
using Siticone.UI.WinForms;

namespace Muphy
{
	// Token: 0x02000013 RID: 19
	[DesignerGenerated]
	public partial class self : Form
	{
		// Token: 0x0600019B RID: 411 RVA: 0x00002D78 File Offset: 0x00000F78
		public self()
		{
			base.Load += this.self_Load;
			this.InitializeComponent();
		}

		// Token: 0x170000A8 RID: 168
		// (get) Token: 0x0600019E RID: 414 RVA: 0x00002D98 File Offset: 0x00000F98
		// (set) Token: 0x0600019F RID: 415 RVA: 0x00002DA0 File Offset: 0x00000FA0
		internal virtual SiticoneCircleProgressBar progres { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x170000A9 RID: 169
		// (get) Token: 0x060001A0 RID: 416 RVA: 0x00002DA9 File Offset: 0x00000FA9
		// (set) Token: 0x060001A1 RID: 417 RVA: 0x0000ABCC File Offset: 0x00008DCC
		internal virtual SiticoneCircleButton Button
		{
			[CompilerGenerated]
			get
			{
				return this._Button;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.Button_Click);
				SiticoneCircleButton button = this._Button;
				if (button != null)
				{
					button.Click -= value2;
				}
				this._Button = value;
				button = this._Button;
				if (button != null)
				{
					button.Click += value2;
				}
			}
		}

		// Token: 0x170000AA RID: 170
		// (get) Token: 0x060001A2 RID: 418 RVA: 0x00002DB1 File Offset: 0x00000FB1
		// (set) Token: 0x060001A3 RID: 419 RVA: 0x00002DB9 File Offset: 0x00000FB9
		internal virtual Label logs { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x170000AB RID: 171
		// (get) Token: 0x060001A4 RID: 420 RVA: 0x00002DC2 File Offset: 0x00000FC2
		// (set) Token: 0x060001A5 RID: 421 RVA: 0x0000AC10 File Offset: 0x00008E10
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

		// Token: 0x060001A6 RID: 422 RVA: 0x0000AC54 File Offset: 0x00008E54
		private void Timer1_Tick(object sender, EventArgs e)
		{
			this.progres.Increment(1);
			if (this.progres.Value == 5)
			{
				this.logs.Text = "Logs: Clearing Temp";
			}
		}

		// Token: 0x060001A7 RID: 423 RVA: 0x00002DCA File Offset: 0x00000FCA
		private void Button_Click(object sender, EventArgs e)
		{
			this.Timer1.Start();
		}

		// Token: 0x060001A8 RID: 424 RVA: 0x00002521 File Offset: 0x00000721
		private void self_Load(object sender, EventArgs e)
		{
		}
	}
}
