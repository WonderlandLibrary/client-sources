using System;
using System.ComponentModel;
using System.Diagnostics;
using System.Drawing;
using System.Runtime.CompilerServices;
using System.Runtime.InteropServices;
using System.Windows.Forms;
using Microsoft.VisualBasic.CompilerServices;
using Siticone.UI.WinForms;

namespace Muphy
{
	// Token: 0x02000012 RID: 18
	[DesignerGenerated]
	public partial class qtapy : Form
	{
		// Token: 0x06000160 RID: 352 RVA: 0x00002BD5 File Offset: 0x00000DD5
		public qtapy()
		{
			this.InitializeComponent();
		}

		// Token: 0x17000091 RID: 145
		// (get) Token: 0x06000163 RID: 355 RVA: 0x00002BE3 File Offset: 0x00000DE3
		// (set) Token: 0x06000164 RID: 356 RVA: 0x0000A3C8 File Offset: 0x000085C8
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

		// Token: 0x17000092 RID: 146
		// (get) Token: 0x06000165 RID: 357 RVA: 0x00002BEB File Offset: 0x00000DEB
		// (set) Token: 0x06000166 RID: 358 RVA: 0x00002BF3 File Offset: 0x00000DF3
		internal virtual TextBox TextBox1 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000093 RID: 147
		// (get) Token: 0x06000167 RID: 359 RVA: 0x00002BFC File Offset: 0x00000DFC
		// (set) Token: 0x06000168 RID: 360 RVA: 0x00002C04 File Offset: 0x00000E04
		internal virtual Label Label1 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000094 RID: 148
		// (get) Token: 0x06000169 RID: 361 RVA: 0x00002C0D File Offset: 0x00000E0D
		// (set) Token: 0x0600016A RID: 362 RVA: 0x0000A40C File Offset: 0x0000860C
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

		// Token: 0x17000095 RID: 149
		// (get) Token: 0x0600016B RID: 363 RVA: 0x00002C15 File Offset: 0x00000E15
		// (set) Token: 0x0600016C RID: 364 RVA: 0x0000A450 File Offset: 0x00008650
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

		// Token: 0x17000096 RID: 150
		// (get) Token: 0x0600016D RID: 365 RVA: 0x00002C1D File Offset: 0x00000E1D
		// (set) Token: 0x0600016E RID: 366 RVA: 0x00002C25 File Offset: 0x00000E25
		internal virtual TextBox TextBox5 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000097 RID: 151
		// (get) Token: 0x0600016F RID: 367 RVA: 0x00002C2E File Offset: 0x00000E2E
		// (set) Token: 0x06000170 RID: 368 RVA: 0x00002C36 File Offset: 0x00000E36
		internal virtual TextBox TextBox3 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000098 RID: 152
		// (get) Token: 0x06000171 RID: 369 RVA: 0x00002C3F File Offset: 0x00000E3F
		// (set) Token: 0x06000172 RID: 370 RVA: 0x00002C47 File Offset: 0x00000E47
		internal virtual TextBox TextBox2 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000099 RID: 153
		// (get) Token: 0x06000173 RID: 371 RVA: 0x00002C50 File Offset: 0x00000E50
		// (set) Token: 0x06000174 RID: 372 RVA: 0x00002C58 File Offset: 0x00000E58
		internal virtual TrackBar TBBlue { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700009A RID: 154
		// (get) Token: 0x06000175 RID: 373 RVA: 0x00002C61 File Offset: 0x00000E61
		// (set) Token: 0x06000176 RID: 374 RVA: 0x00002C69 File Offset: 0x00000E69
		internal virtual TrackBar TBGreen { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700009B RID: 155
		// (get) Token: 0x06000177 RID: 375 RVA: 0x00002C72 File Offset: 0x00000E72
		// (set) Token: 0x06000178 RID: 376 RVA: 0x00002C7A File Offset: 0x00000E7A
		internal virtual TrackBar TBRed { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700009C RID: 156
		// (get) Token: 0x06000179 RID: 377 RVA: 0x00002C83 File Offset: 0x00000E83
		// (set) Token: 0x0600017A RID: 378 RVA: 0x0000A494 File Offset: 0x00008694
		internal virtual Timer TMRGen
		{
			[CompilerGenerated]
			get
			{
				return this._TMRGen;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.TMRGen_Tick);
				Timer tmrgen = this._TMRGen;
				if (tmrgen != null)
				{
					tmrgen.Tick -= value2;
				}
				this._TMRGen = value;
				tmrgen = this._TMRGen;
				if (tmrgen != null)
				{
					tmrgen.Tick += value2;
				}
			}
		}

		// Token: 0x1700009D RID: 157
		// (get) Token: 0x0600017B RID: 379 RVA: 0x00002C8B File Offset: 0x00000E8B
		// (set) Token: 0x0600017C RID: 380 RVA: 0x00002C93 File Offset: 0x00000E93
		internal virtual SiticoneGroupBox SiticoneGroupBox1 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700009E RID: 158
		// (get) Token: 0x0600017D RID: 381 RVA: 0x00002C9C File Offset: 0x00000E9C
		// (set) Token: 0x0600017E RID: 382 RVA: 0x00002CA4 File Offset: 0x00000EA4
		internal virtual Panel Panel1 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700009F RID: 159
		// (get) Token: 0x0600017F RID: 383 RVA: 0x00002CAD File Offset: 0x00000EAD
		// (set) Token: 0x06000180 RID: 384 RVA: 0x00002CB5 File Offset: 0x00000EB5
		internal virtual SiticoneDragControl SiticoneDragControl1 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x170000A0 RID: 160
		// (get) Token: 0x06000181 RID: 385 RVA: 0x00002CBE File Offset: 0x00000EBE
		// (set) Token: 0x06000182 RID: 386 RVA: 0x00002CC6 File Offset: 0x00000EC6
		internal virtual Label BLUCLR { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x170000A1 RID: 161
		// (get) Token: 0x06000183 RID: 387 RVA: 0x00002CCF File Offset: 0x00000ECF
		// (set) Token: 0x06000184 RID: 388 RVA: 0x00002CD7 File Offset: 0x00000ED7
		internal virtual Label GRNCLR { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x170000A2 RID: 162
		// (get) Token: 0x06000185 RID: 389 RVA: 0x00002CE0 File Offset: 0x00000EE0
		// (set) Token: 0x06000186 RID: 390 RVA: 0x00002CE8 File Offset: 0x00000EE8
		internal virtual Label RDCLR { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x170000A3 RID: 163
		// (get) Token: 0x06000187 RID: 391 RVA: 0x00002CF1 File Offset: 0x00000EF1
		// (set) Token: 0x06000188 RID: 392 RVA: 0x0000A4D8 File Offset: 0x000086D8
		internal virtual Timer bind
		{
			[CompilerGenerated]
			get
			{
				return this._bind;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.bind_Tick);
				Timer bind = this._bind;
				if (bind != null)
				{
					bind.Tick -= value2;
				}
				this._bind = value;
				bind = this._bind;
				if (bind != null)
				{
					bind.Tick += value2;
				}
			}
		}

		// Token: 0x170000A4 RID: 164
		// (get) Token: 0x06000189 RID: 393 RVA: 0x00002CF9 File Offset: 0x00000EF9
		// (set) Token: 0x0600018A RID: 394 RVA: 0x0000A51C File Offset: 0x0000871C
		internal virtual SiticoneButton SiticoneButton2
		{
			[CompilerGenerated]
			get
			{
				return this._SiticoneButton2;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.SiticoneButton2_Click);
				SiticoneButton siticoneButton = this._SiticoneButton2;
				if (siticoneButton != null)
				{
					siticoneButton.Click -= value2;
				}
				this._SiticoneButton2 = value;
				siticoneButton = this._SiticoneButton2;
				if (siticoneButton != null)
				{
					siticoneButton.Click += value2;
				}
			}
		}

		// Token: 0x170000A5 RID: 165
		// (get) Token: 0x0600018B RID: 395 RVA: 0x00002D01 File Offset: 0x00000F01
		// (set) Token: 0x0600018C RID: 396 RVA: 0x0000A560 File Offset: 0x00008760
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

		// Token: 0x170000A6 RID: 166
		// (get) Token: 0x0600018D RID: 397 RVA: 0x00002D09 File Offset: 0x00000F09
		// (set) Token: 0x0600018E RID: 398 RVA: 0x0000A5A4 File Offset: 0x000087A4
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

		// Token: 0x170000A7 RID: 167
		// (get) Token: 0x0600018F RID: 399 RVA: 0x00002D11 File Offset: 0x00000F11
		// (set) Token: 0x06000190 RID: 400 RVA: 0x0000A5E8 File Offset: 0x000087E8
		internal virtual SiticoneCheckBox SiticoneCheckBox3
		{
			[CompilerGenerated]
			get
			{
				return this._SiticoneCheckBox3;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.SiticoneCheckBox3_CheckedChanged);
				SiticoneCheckBox siticoneCheckBox = this._SiticoneCheckBox3;
				if (siticoneCheckBox != null)
				{
					siticoneCheckBox.CheckedChanged -= value2;
				}
				this._SiticoneCheckBox3 = value;
				siticoneCheckBox = this._SiticoneCheckBox3;
				if (siticoneCheckBox != null)
				{
					siticoneCheckBox.CheckedChanged += value2;
				}
			}
		}

		// Token: 0x06000191 RID: 401
		[DllImport("user32.dll")]
		private static extern short GetAsyncKeyState(int vKey);

		// Token: 0x06000192 RID: 402 RVA: 0x0000A62C File Offset: 0x0000882C
		private void TMRGen_Tick(object sender, EventArgs e)
		{
			this.RDCLR.Text = Conversions.ToString(this.TBRed.Value);
			this.GRNCLR.Text = Conversions.ToString(this.TBGreen.Value);
			this.BLUCLR.Text = Conversions.ToString(this.TBBlue.Value);
			int red = Conversions.ToInteger(this.RDCLR.Text);
			int green = Conversions.ToInteger(this.GRNCLR.Text);
			int blue = Conversions.ToInteger(this.BLUCLR.Text);
			this.SiticoneCheckBox1.CheckMarkColor = Color.FromArgb(red, green, blue);
			this.SiticoneCheckBox2.CheckMarkColor = Color.FromArgb(red, green, blue);
			this.SiticoneCheckBox3.CheckMarkColor = Color.FromArgb(red, green, blue);
			this.SiticoneButton1.FillColor = Color.FromArgb(red, green, blue);
			this.SiticoneButton2.FillColor = Color.FromArgb(red, green, blue);
			this.Label1.ForeColor = Color.FromArgb(red, green, blue);
			this.SiticoneCheckBox1.ForeColor = Color.FromArgb(red, green, blue);
		}

		// Token: 0x06000193 RID: 403 RVA: 0x00002D19 File Offset: 0x00000F19
		private void SiticoneButton1_Click(object sender, EventArgs e)
		{
			this.Timer1.Start();
		}

		// Token: 0x06000194 RID: 404 RVA: 0x00002D26 File Offset: 0x00000F26
		private void Timer1_Tick(object sender, EventArgs e)
		{
			this.Timer1.Interval = Conversions.ToInteger(this.TextBox1.Text);
			SendKeys.Send("Q");
		}

		// Token: 0x06000195 RID: 405 RVA: 0x0000A744 File Offset: 0x00008944
		private void SiticoneCheckBox1_CheckedChanged(object sender, EventArgs e)
		{
			if (this.SiticoneCheckBox1.Checked)
			{
				this.bind.Start();
			}
		}

		// Token: 0x06000196 RID: 406 RVA: 0x0000A76C File Offset: 0x0000896C
		private void bind_Tick(object sender, EventArgs e)
		{
			try
			{
				if (qtapy.GetAsyncKeyState(86) != 0)
				{
					this.Timer1.Start();
				}
				else if (qtapy.GetAsyncKeyState(67) != 0)
				{
					this.Timer1.Stop();
				}
			}
			catch (Exception ex)
			{
			}
		}

		// Token: 0x06000197 RID: 407 RVA: 0x00002D4D File Offset: 0x00000F4D
		private void SiticoneButton2_Click(object sender, EventArgs e)
		{
			this.Timer1.Stop();
		}

		// Token: 0x06000198 RID: 408 RVA: 0x00002D5A File Offset: 0x00000F5A
		private void SiticoneControlBox1_Click(object sender, EventArgs e)
		{
			base.Hide();
			this.Timer1.Stop();
			this.bind.Stop();
		}

		// Token: 0x06000199 RID: 409 RVA: 0x0000A7CC File Offset: 0x000089CC
		private void SiticoneCheckBox3_CheckedChanged(object sender, EventArgs e)
		{
			if (this.SiticoneCheckBox3.Checked)
			{
				base.ShowInTaskbar = false;
			}
			else
			{
				base.ShowInTaskbar = true;
			}
		}

		// Token: 0x0600019A RID: 410 RVA: 0x0000A7F8 File Offset: 0x000089F8
		private void SiticoneCheckBox2_CheckedChanged(object sender, EventArgs e)
		{
			if (this.SiticoneCheckBox2.Checked)
			{
				base.Hide();
			}
		}
	}
}
