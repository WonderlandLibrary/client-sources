using System;
using System.ComponentModel;
using System.Diagnostics;
using System.Drawing;
using System.Runtime.CompilerServices;
using System.Runtime.InteropServices;
using System.Windows.Forms;
using Microsoft.VisualBasic.CompilerServices;
using Muphy.My;
using Siticone.UI.WinForms;

namespace Muphy
{
	// Token: 0x02000015 RID: 21
	[DesignerGenerated]
	public partial class soundform : Form
	{
		// Token: 0x060001B6 RID: 438 RVA: 0x00002E3A File Offset: 0x0000103A
		public soundform()
		{
			base.Load += this.soundform_Load;
			this.InitializeComponent();
		}

		// Token: 0x170000AF RID: 175
		// (get) Token: 0x060001B9 RID: 441 RVA: 0x00002E5A File Offset: 0x0000105A
		// (set) Token: 0x060001BA RID: 442 RVA: 0x0000B898 File Offset: 0x00009A98
		internal virtual Timer clicksound
		{
			[CompilerGenerated]
			get
			{
				return this._clicksound;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.clicksound_Tick);
				Timer clicksound = this._clicksound;
				if (clicksound != null)
				{
					clicksound.Tick -= value2;
				}
				this._clicksound = value;
				clicksound = this._clicksound;
				if (clicksound != null)
				{
					clicksound.Tick += value2;
				}
			}
		}

		// Token: 0x170000B0 RID: 176
		// (get) Token: 0x060001BB RID: 443 RVA: 0x00002E62 File Offset: 0x00001062
		// (set) Token: 0x060001BC RID: 444 RVA: 0x0000B8DC File Offset: 0x00009ADC
		internal virtual SiticoneCheckBox SiticoneCheckBox1
		{
			[CompilerGenerated]
			get
			{
				return this._SiticoneCheckBox1;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.SiticoneCheckBox1_CheckedChanged);
				SiticoneCheckBox siticoneCheckBox = this._SiticoneCheckBox1;
				if (siticoneCheckBox != null)
				{
					siticoneCheckBox.CheckedChanged -= value2;
				}
				this._SiticoneCheckBox1 = value;
				siticoneCheckBox = this._SiticoneCheckBox1;
				if (siticoneCheckBox != null)
				{
					siticoneCheckBox.CheckedChanged += value2;
				}
			}
		}

		// Token: 0x170000B1 RID: 177
		// (get) Token: 0x060001BD RID: 445 RVA: 0x00002E6A File Offset: 0x0000106A
		// (set) Token: 0x060001BE RID: 446 RVA: 0x00002E72 File Offset: 0x00001072
		internal virtual Panel Panel1 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x170000B2 RID: 178
		// (get) Token: 0x060001BF RID: 447 RVA: 0x00002E7B File Offset: 0x0000107B
		// (set) Token: 0x060001C0 RID: 448 RVA: 0x00002E83 File Offset: 0x00001083
		internal virtual SiticoneControlBox SiticoneControlBox1 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x170000B3 RID: 179
		// (get) Token: 0x060001C1 RID: 449 RVA: 0x00002E8C File Offset: 0x0000108C
		// (set) Token: 0x060001C2 RID: 450 RVA: 0x00002E94 File Offset: 0x00001094
		internal virtual SiticoneDragControl SiticoneDragControl1 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x170000B4 RID: 180
		// (get) Token: 0x060001C3 RID: 451 RVA: 0x00002E9D File Offset: 0x0000109D
		// (set) Token: 0x060001C4 RID: 452 RVA: 0x00002EA5 File Offset: 0x000010A5
		internal virtual Label Label1 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x170000B5 RID: 181
		// (get) Token: 0x060001C5 RID: 453 RVA: 0x00002EAE File Offset: 0x000010AE
		// (set) Token: 0x060001C6 RID: 454 RVA: 0x00002EB6 File Offset: 0x000010B6
		internal virtual Label Label2 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x170000B6 RID: 182
		// (get) Token: 0x060001C7 RID: 455 RVA: 0x00002EBF File Offset: 0x000010BF
		// (set) Token: 0x060001C8 RID: 456 RVA: 0x00002EC7 File Offset: 0x000010C7
		internal virtual Label Label3 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x170000B7 RID: 183
		// (get) Token: 0x060001C9 RID: 457 RVA: 0x00002ED0 File Offset: 0x000010D0
		// (set) Token: 0x060001CA RID: 458 RVA: 0x00002ED8 File Offset: 0x000010D8
		internal virtual Label Label4 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x170000B8 RID: 184
		// (get) Token: 0x060001CB RID: 459 RVA: 0x00002EE1 File Offset: 0x000010E1
		// (set) Token: 0x060001CC RID: 460 RVA: 0x00002EE9 File Offset: 0x000010E9
		internal virtual Label Label5 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x170000B9 RID: 185
		// (get) Token: 0x060001CD RID: 461 RVA: 0x00002EF2 File Offset: 0x000010F2
		// (set) Token: 0x060001CE RID: 462 RVA: 0x0000B920 File Offset: 0x00009B20
		internal virtual SiticoneCheckBox SiticoneCheckBox2
		{
			[CompilerGenerated]
			get
			{
				return this._SiticoneCheckBox2;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.SiticoneCheckBox2_CheckedChanged);
				SiticoneCheckBox siticoneCheckBox = this._SiticoneCheckBox2;
				if (siticoneCheckBox != null)
				{
					siticoneCheckBox.CheckedChanged -= value2;
				}
				this._SiticoneCheckBox2 = value;
				siticoneCheckBox = this._SiticoneCheckBox2;
				if (siticoneCheckBox != null)
				{
					siticoneCheckBox.CheckedChanged += value2;
				}
			}
		}

		// Token: 0x170000BA RID: 186
		// (get) Token: 0x060001CF RID: 463 RVA: 0x00002EFA File Offset: 0x000010FA
		// (set) Token: 0x060001D0 RID: 464 RVA: 0x0000B964 File Offset: 0x00009B64
		internal virtual Timer butterclick
		{
			[CompilerGenerated]
			get
			{
				return this._butterclick;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.butterclick_Tick);
				Timer butterclick = this._butterclick;
				if (butterclick != null)
				{
					butterclick.Tick -= value2;
				}
				this._butterclick = value;
				butterclick = this._butterclick;
				if (butterclick != null)
				{
					butterclick.Tick += value2;
				}
			}
		}

		// Token: 0x170000BB RID: 187
		// (get) Token: 0x060001D1 RID: 465 RVA: 0x00002F02 File Offset: 0x00001102
		// (set) Token: 0x060001D2 RID: 466 RVA: 0x00002F0A File Offset: 0x0000110A
		internal virtual Label Label6 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x060001D3 RID: 467
		[DllImport("user32.dll")]
		private static extern short GetAsyncKeyState(int vKey);

		// Token: 0x060001D4 RID: 468 RVA: 0x0000B9A8 File Offset: 0x00009BA8
		private void clicksound_Tick(object sender, EventArgs e)
		{
			if (Control.MouseButtons == MouseButtons.Left)
			{
				MyProject.Computer.Audio.Play("C:\\Windows\\Temp\\mouseclick.wav");
			}
		}

		// Token: 0x060001D5 RID: 469 RVA: 0x00002521 File Offset: 0x00000721
		private void soundform_Load(object sender, EventArgs e)
		{
		}

		// Token: 0x060001D6 RID: 470 RVA: 0x0000B9D8 File Offset: 0x00009BD8
		private void SiticoneCheckBox1_CheckedChanged(object sender, EventArgs e)
		{
			if (this.SiticoneCheckBox1.Checked)
			{
				this.clicksound.Start();
			}
			else
			{
				this.clicksound.Stop();
			}
		}

		// Token: 0x060001D7 RID: 471 RVA: 0x0000BA0C File Offset: 0x00009C0C
		private void SiticoneCheckBox2_CheckedChanged(object sender, EventArgs e)
		{
			if (this.SiticoneCheckBox2.Checked)
			{
				this.SiticoneCheckBox1.Checked = false;
				this.butterclick.Start();
			}
			else
			{
				this.butterclick.Stop();
			}
		}

		// Token: 0x060001D8 RID: 472 RVA: 0x0000BA4C File Offset: 0x00009C4C
		private void butterclick_Tick(object sender, EventArgs e)
		{
			if (Control.MouseButtons == MouseButtons.Left)
			{
				MyProject.Computer.Audio.Play("C:\\Windows\\Temp\\butterclick.wav");
			}
		}
	}
}
