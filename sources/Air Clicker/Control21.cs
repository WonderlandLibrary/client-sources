using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Diagnostics;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Runtime.CompilerServices;
using System.Windows.Forms;

// Token: 0x02000034 RID: 52
internal class Control21 : Control
{
	// Token: 0x17000105 RID: 261
	// (get) Token: 0x06000310 RID: 784 RVA: 0x0000F044 File Offset: 0x0000D244
	// (set) Token: 0x06000311 RID: 785 RVA: 0x0000F060 File Offset: 0x0000D260
	[DesignerSerializationVisibility(DesignerSerializationVisibility.Content)]
	public Control21.Class13[] Class13_0
	{
		get
		{
			return this.list_0.ToArray();
		}
		set
		{
			this.list_0 = new List<Control21.Class13>(value);
			this.method_5();
		}
	}

	// Token: 0x17000106 RID: 262
	// (get) Token: 0x06000312 RID: 786 RVA: 0x0000F074 File Offset: 0x0000D274
	public Control21.Class13[] Class13_1
	{
		get
		{
			return this.list_1.ToArray();
		}
	}

	// Token: 0x17000107 RID: 263
	// (get) Token: 0x06000313 RID: 787 RVA: 0x0000F090 File Offset: 0x0000D290
	// (set) Token: 0x06000314 RID: 788 RVA: 0x0000F0AC File Offset: 0x0000D2AC
	[DesignerSerializationVisibility(DesignerSerializationVisibility.Content)]
	public Control21.Class15[] Class15_0
	{
		get
		{
			return this.list_2.ToArray();
		}
		set
		{
			this.list_2 = new List<Control21.Class15>(value);
			this.method_7();
		}
	}

	// Token: 0x17000108 RID: 264
	// (get) Token: 0x06000315 RID: 789 RVA: 0x0000F0C0 File Offset: 0x0000D2C0
	// (set) Token: 0x06000316 RID: 790 RVA: 0x0000F0C8 File Offset: 0x0000D2C8
	public bool Boolean_0
	{
		get
		{
			return this.bool_0;
		}
		set
		{
			this.bool_0 = value;
			if (this.list_1.Count > 1)
			{
				this.list_1.RemoveRange(1, checked(this.list_1.Count - 1));
			}
			base.Invalidate();
		}
	}

	// Token: 0x17000109 RID: 265
	// (get) Token: 0x06000317 RID: 791 RVA: 0x0000F100 File Offset: 0x0000D300
	// (set) Token: 0x06000318 RID: 792 RVA: 0x0000F118 File Offset: 0x0000D318
	public virtual Font Font
	{
		get
		{
			return this.method_8();
		}
		set
		{
			this.int_0 = checked((int)Math.Round((double)Graphics.FromHwnd(this.method_9()).MeasureString("@", this.System.Windows.Forms.Control.Font).Height) + 6);
			if (this.control19_0 != null)
			{
				this.control19_0.Int32_3 = this.int_0;
				this.control19_0.Int32_4 = this.int_0;
			}
			base.Font = value;
			this.method_6();
		}
	}

	// Token: 0x06000319 RID: 793 RVA: 0x0000F194 File Offset: 0x0000D394
	public void method_0(string string_0, params string[] string_1)
	{
		List<Control21.Class14> list = new List<Control21.Class14>();
		foreach (string string_2 in string_1)
		{
			list.Add(new Control21.Class14
			{
				String_0 = string_2
			});
		}
		Control21.Class13 @class = new Control21.Class13();
		@class.String_0 = string_0;
		@class.List_0 = list;
		this.list_0.Add(@class);
		this.method_5();
	}

	// Token: 0x0600031A RID: 794 RVA: 0x0000F200 File Offset: 0x0000D400
	public void method_1(int int_2)
	{
		this.list_0.RemoveAt(int_2);
		this.method_5();
	}

	// Token: 0x0600031B RID: 795 RVA: 0x0000F214 File Offset: 0x0000D414
	public void method_2(Control21.Class13 class13_0)
	{
		this.list_0.Remove(class13_0);
		this.method_5();
	}

	// Token: 0x0600031C RID: 796 RVA: 0x0000F22C File Offset: 0x0000D42C
	public void method_3(Control21.Class13[] class13_0)
	{
		foreach (Control21.Class13 item in class13_0)
		{
			this.list_0.Remove(item);
		}
		this.method_5();
	}

	// Token: 0x0600031D RID: 797 RVA: 0x0000F264 File Offset: 0x0000D464
	public Control21()
	{
		this.list_0 = new List<Control21.Class13>();
		this.list_1 = new List<Control21.Class13>();
		this.list_2 = new List<Control21.Class15>();
		this.bool_0 = true;
		this.int_0 = 24;
		base.SetStyle(ControlStyles.UserPaint | ControlStyles.Opaque | ControlStyles.ResizeRedraw | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
		base.SetStyle(ControlStyles.Selectable, true);
		this.pen_0 = new Pen(Color.FromArgb(55, 55, 55));
		this.pen_1 = new Pen(Color.FromArgb(24, 24, 24));
		this.pen_2 = new Pen(Color.FromArgb(65, 65, 65));
		this.solidBrush_0 = new SolidBrush(Color.FromArgb(62, 62, 62));
		this.solidBrush_1 = new SolidBrush(Color.FromArgb(65, 65, 65));
		this.solidBrush_2 = new SolidBrush(Color.FromArgb(47, 47, 47));
		this.solidBrush_3 = new SolidBrush(Color.FromArgb(50, 50, 50));
		this.control19_0 = new Control19();
		this.control19_0.Int32_3 = this.int_0;
		this.control19_0.Int32_4 = this.int_0;
		this.control19_0.Event_0 += this.method_4;
		this.control19_0.MouseDown += this.control19_0_MouseDown;
		base.Controls.Add(this.control19_0);
		this.method_6();
	}

	// Token: 0x0600031E RID: 798 RVA: 0x0000F3CC File Offset: 0x0000D5CC
	protected virtual void OnSizeChanged(EventArgs e)
	{
		this.method_6();
		base.OnSizeChanged(e);
	}

	// Token: 0x0600031F RID: 799 RVA: 0x0000F3DC File Offset: 0x0000D5DC
	private void method_4(object object_0)
	{
		base.Invalidate();
	}

	// Token: 0x06000320 RID: 800 RVA: 0x0000F3E4 File Offset: 0x0000D5E4
	private void method_5()
	{
		this.control19_0.Int32_1 = checked(this.list_0.Count * this.int_0);
		base.Invalidate();
	}

	// Token: 0x06000321 RID: 801 RVA: 0x0000F40C File Offset: 0x0000D60C
	private void method_6()
	{
		checked
		{
			this.control19_0.Location = new Point(base.Width - this.control19_0.Width - 1, 1);
			this.control19_0.Size = new Size(18, base.Height - 2);
			base.Invalidate();
		}
	}

	// Token: 0x06000322 RID: 802 RVA: 0x0000F460 File Offset: 0x0000D660
	private void method_7()
	{
		int num = 3;
		checked
		{
			this.int_1 = new int[this.list_2.Count - 1 + 1];
			int num2 = this.list_2.Count - 1;
			for (int i = 0; i <= num2; i++)
			{
				this.int_1[i] = num;
				num += this.Class15_0[i].Int32_0;
			}
			base.Invalidate();
		}
	}

	// Token: 0x06000323 RID: 803 RVA: 0x0000F4C4 File Offset: 0x0000D6C4
	private void control19_0_MouseDown(object sender, MouseEventArgs e)
	{
		base.Focus();
	}

	// Token: 0x06000324 RID: 804 RVA: 0x0000F4D0 File Offset: 0x0000D6D0
	protected virtual void OnMouseDown(MouseEventArgs e)
	{
		base.Focus();
		checked
		{
			if (e.Button == MouseButtons.Left)
			{
				int num = (int)Math.Round(unchecked(this.control19_0.Double_1 * (double)(checked(this.control19_0.Int32_1 - (base.Height - this.int_0 * 2)))));
				int num2 = (e.Y + num - this.int_0) / this.int_0;
				if (num2 > this.list_0.Count - 1)
				{
					num2 = -1;
				}
				if (num2 != -1)
				{
					if (Control.ModifierKeys != Keys.Control || !this.bool_0)
					{
						this.list_1.Clear();
						this.list_1.Add(this.list_0[num2]);
					}
					else if (!this.list_1.Contains(this.list_0[num2]))
					{
						this.list_1.Add(this.list_0[num2]);
					}
					else
					{
						this.list_1.Remove(this.list_0[num2]);
					}
				}
				base.Invalidate();
			}
			base.OnMouseDown(e);
		}
	}

	// Token: 0x06000325 RID: 805 RVA: 0x0000F5F0 File Offset: 0x0000D7F0
	protected virtual void OnPaint(PaintEventArgs e)
	{
		Class9.graphics_0 = e.Graphics;
		Class9.graphics_0.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
		Class9.graphics_0.Clear(this.BackColor);
		checked
		{
			Class9.graphics_0.DrawRectangle(this.pen_0, 1, 1, base.Width - 3, base.Height - 3);
			int num = (int)Math.Round(unchecked(this.control19_0.Double_1 * (double)(checked(this.control19_0.Int32_1 - (base.Height - this.int_0 * 2)))));
			int num2;
			if (num != 0)
			{
				num2 = num / this.int_0;
			}
			else
			{
				num2 = 0;
			}
			int num3 = Math.Min(num2 + base.Height / this.int_0, this.list_0.Count - 1);
			int num4 = num2;
			int num5 = num3;
			Rectangle rectangle;
			for (int i = num4; i <= num5; i++)
			{
				Control21.Class13 @class = this.Class13_0[i];
				rectangle = new Rectangle(0, this.int_0 + i * this.int_0 + 1 - num, base.Width, this.int_0 - 1);
				float height = Class9.graphics_0.MeasureString(@class.String_0, this.System.Windows.Forms.Control.Font).Height;
				int num6 = rectangle.Y + (int)Math.Round(unchecked((double)this.int_0 / 2.0 - (double)(height / 2f)));
				if (!this.list_1.Contains(@class))
				{
					if (i % 2 != 0)
					{
						Class9.graphics_0.FillRectangle(this.solidBrush_3, rectangle);
					}
					else
					{
						Class9.graphics_0.FillRectangle(this.solidBrush_2, rectangle);
					}
				}
				else if (i % 2 != 0)
				{
					Class9.graphics_0.FillRectangle(this.solidBrush_1, rectangle);
				}
				else
				{
					Class9.graphics_0.FillRectangle(this.solidBrush_0, rectangle);
				}
				Class9.graphics_0.DrawLine(this.pen_1, 0, rectangle.Bottom, base.Width, rectangle.Bottom);
				if (this.Class15_0.Length > 0)
				{
					rectangle.Width = this.Class15_0[0].Int32_0;
					Class9.graphics_0.SetClip(rectangle);
				}
				Class9.graphics_0.DrawString(@class.String_0, this.System.Windows.Forms.Control.Font, Brushes.Black, 10f, (float)(num6 + 1));
				Class9.graphics_0.DrawString(@class.String_0, this.System.Windows.Forms.Control.Font, Brushes.WhiteSmoke, 9f, (float)num6);
				if (@class.List_0 != null)
				{
					int num7 = Math.Min(@class.List_0.Count, this.list_2.Count) - 1;
					for (int j = 0; j <= num7; j++)
					{
						int num8 = this.int_1[j + 1] + 4;
						rectangle.X = num8;
						rectangle.Width = this.Class15_0[j].Int32_0;
						Class9.graphics_0.SetClip(rectangle);
						Class9.graphics_0.DrawString(@class.List_0[j].String_0, this.System.Windows.Forms.Control.Font, Brushes.Black, (float)(num8 + 1), (float)(num6 + 1));
						Class9.graphics_0.DrawString(@class.List_0[j].String_0, this.System.Windows.Forms.Control.Font, Brushes.WhiteSmoke, (float)num8, (float)num6);
					}
				}
				Class9.graphics_0.ResetClip();
			}
			rectangle = new Rectangle(0, 0, base.Width, this.int_0);
			this.linearGradientBrush_0 = new LinearGradientBrush(rectangle, Color.FromArgb(60, 60, 60), Color.FromArgb(55, 55, 55), 90f);
			Class9.graphics_0.FillRectangle(this.linearGradientBrush_0, rectangle);
			Class9.graphics_0.DrawRectangle(this.pen_2, 1, 1, base.Width - 22, this.int_0 - 2);
			int y = Math.Min(this.control19_0.Int32_1 + this.int_0 - num, base.Height);
			int num9 = this.list_2.Count - 1;
			for (int k = 0; k <= num9; k++)
			{
				Control21.Class15 class2 = this.Class15_0[k];
				float height = Class9.graphics_0.MeasureString(class2.String_0, this.System.Windows.Forms.Control.Font).Height;
				int num6 = (int)Math.Round(unchecked((double)this.int_0 / 2.0 - (double)(height / 2f)));
				int num8 = this.int_1[k];
				Class9.graphics_0.DrawString(class2.String_0, this.System.Windows.Forms.Control.Font, Brushes.Black, (float)(num8 + 1), (float)(num6 + 1));
				Class9.graphics_0.DrawString(class2.String_0, this.System.Windows.Forms.Control.Font, Brushes.WhiteSmoke, (float)num8, (float)num6);
				Class9.graphics_0.DrawLine(this.pen_1, num8 - 3, 0, num8 - 3, y);
				Class9.graphics_0.DrawLine(this.pen_2, num8 - 2, 0, num8 - 2, this.int_0);
			}
			Class9.graphics_0.DrawRectangle(this.pen_1, 0, 0, base.Width - 1, base.Height - 1);
			Class9.graphics_0.DrawLine(this.pen_1, 0, this.int_0, base.Width, this.int_0);
			Class9.graphics_0.DrawLine(this.pen_1, this.control19_0.Location.X - 1, 0, this.control19_0.Location.X - 1, base.Height);
		}
	}

	// Token: 0x06000326 RID: 806 RVA: 0x0000FB5C File Offset: 0x0000DD5C
	protected virtual void OnMouseWheel(MouseEventArgs e)
	{
		checked
		{
			int num = 0 - e.Delta * SystemInformation.MouseWheelScrollLines / 120 * (this.int_0 / 2);
			int int32_ = Math.Max(Math.Min(this.control19_0.Int32_2 + num, this.control19_0.Int32_1), this.control19_0.Int32_0);
			this.control19_0.Int32_2 = int32_;
			base.OnMouseWheel(e);
		}
	}

	// Token: 0x06000327 RID: 807 RVA: 0x0000FBC8 File Offset: 0x0000DDC8
	Font method_8()
	{
		return base.Font;
	}

	// Token: 0x06000328 RID: 808 RVA: 0x0000FBD0 File Offset: 0x0000DDD0
	IntPtr method_9()
	{
		return base.Handle;
	}

	// Token: 0x0400019A RID: 410
	private List<Control21.Class13> list_0;

	// Token: 0x0400019B RID: 411
	private List<Control21.Class13> list_1;

	// Token: 0x0400019C RID: 412
	private List<Control21.Class15> list_2;

	// Token: 0x0400019D RID: 413
	private bool bool_0;

	// Token: 0x0400019E RID: 414
	private int int_0;

	// Token: 0x0400019F RID: 415
	private Control19 control19_0;

	// Token: 0x040001A0 RID: 416
	private int[] int_1;

	// Token: 0x040001A1 RID: 417
	private Pen pen_0;

	// Token: 0x040001A2 RID: 418
	private Pen pen_1;

	// Token: 0x040001A3 RID: 419
	private Pen pen_2;

	// Token: 0x040001A4 RID: 420
	private SolidBrush solidBrush_0;

	// Token: 0x040001A5 RID: 421
	private SolidBrush solidBrush_1;

	// Token: 0x040001A6 RID: 422
	private SolidBrush solidBrush_2;

	// Token: 0x040001A7 RID: 423
	private SolidBrush solidBrush_3;

	// Token: 0x040001A8 RID: 424
	private LinearGradientBrush linearGradientBrush_0;

	// Token: 0x02000035 RID: 53
	public class Class13
	{
		// Token: 0x1700010A RID: 266
		// (get) Token: 0x06000329 RID: 809 RVA: 0x0000FBD8 File Offset: 0x0000DDD8
		// (set) Token: 0x0600032A RID: 810 RVA: 0x0000FBE0 File Offset: 0x0000DDE0
		public string String_0 { get; set; }

		// Token: 0x1700010B RID: 267
		// (get) Token: 0x0600032B RID: 811 RVA: 0x0000FBEC File Offset: 0x0000DDEC
		// (set) Token: 0x0600032C RID: 812 RVA: 0x0000FBF4 File Offset: 0x0000DDF4
		[DesignerSerializationVisibility(DesignerSerializationVisibility.Content)]
		public List<Control21.Class14> List_0 { get; set; }

		// Token: 0x0600032D RID: 813 RVA: 0x0000FC00 File Offset: 0x0000DE00
		public Class13()
		{
			this.List_0 = new List<Control21.Class14>();
			this.guid_0 = Guid.NewGuid();
		}

		// Token: 0x0600032E RID: 814 RVA: 0x0000FC20 File Offset: 0x0000DE20
		public virtual string ToString()
		{
			return this.String_0;
		}

		// Token: 0x0600032F RID: 815 RVA: 0x0000FC38 File Offset: 0x0000DE38
		public virtual bool Equals(object obj)
		{
			return obj is Control21.Class13 && ((Control21.Class13)obj).guid_0 == this.guid_0;
		}

		// Token: 0x040001A9 RID: 425
		[CompilerGenerated]
		[DebuggerBrowsable(DebuggerBrowsableState.Never)]
		private string string_0;

		// Token: 0x040001AA RID: 426
		[CompilerGenerated]
		[DebuggerBrowsable(DebuggerBrowsableState.Never)]
		private List<Control21.Class14> list_0;

		// Token: 0x040001AB RID: 427
		protected Guid guid_0;
	}

	// Token: 0x02000036 RID: 54
	public class Class14
	{
		// Token: 0x1700010C RID: 268
		// (get) Token: 0x06000331 RID: 817 RVA: 0x0000FC74 File Offset: 0x0000DE74
		// (set) Token: 0x06000332 RID: 818 RVA: 0x0000FC7C File Offset: 0x0000DE7C
		public string String_0 { get; set; }

		// Token: 0x06000333 RID: 819 RVA: 0x0000FC88 File Offset: 0x0000DE88
		public virtual string ToString()
		{
			return this.String_0;
		}

		// Token: 0x040001AC RID: 428
		[DebuggerBrowsable(DebuggerBrowsableState.Never)]
		[CompilerGenerated]
		private string string_0;
	}

	// Token: 0x02000037 RID: 55
	public class Class15
	{
		// Token: 0x06000334 RID: 820 RVA: 0x0000FCA0 File Offset: 0x0000DEA0
		public Class15()
		{
			this.Int32_0 = 60;
		}

		// Token: 0x1700010D RID: 269
		// (get) Token: 0x06000335 RID: 821 RVA: 0x0000FCB0 File Offset: 0x0000DEB0
		// (set) Token: 0x06000336 RID: 822 RVA: 0x0000FCB8 File Offset: 0x0000DEB8
		public string String_0 { get; set; }

		// Token: 0x1700010E RID: 270
		// (get) Token: 0x06000337 RID: 823 RVA: 0x0000FCC4 File Offset: 0x0000DEC4
		// (set) Token: 0x06000338 RID: 824 RVA: 0x0000FCCC File Offset: 0x0000DECC
		public int Int32_0 { get; set; }

		// Token: 0x06000339 RID: 825 RVA: 0x0000FCD8 File Offset: 0x0000DED8
		public virtual string ToString()
		{
			return this.String_0;
		}

		// Token: 0x040001AD RID: 429
		[CompilerGenerated]
		[DebuggerBrowsable(DebuggerBrowsableState.Never)]
		private string string_0;

		// Token: 0x040001AE RID: 430
		[CompilerGenerated]
		[DebuggerBrowsable(DebuggerBrowsableState.Never)]
		private int int_0;
	}
}
