using System;
using System.ComponentModel;
using System.Diagnostics;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Runtime.CompilerServices;
using System.Windows.Forms;

// Token: 0x0200004A RID: 74
[DefaultEvent("TextChanged")]
internal class Control32 : Control
{
	// Token: 0x17000130 RID: 304
	// (get) Token: 0x060003DB RID: 987 RVA: 0x00012A50 File Offset: 0x00010C50
	// (set) Token: 0x060003DC RID: 988 RVA: 0x00012A58 File Offset: 0x00010C58
	private virtual TextBox TextBox_0 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x17000131 RID: 305
	// (get) Token: 0x060003DD RID: 989 RVA: 0x00012A64 File Offset: 0x00010C64
	// (set) Token: 0x060003DE RID: 990 RVA: 0x00012A7C File Offset: 0x00010C7C
	[Category("Options")]
	public HorizontalAlignment HorizontalAlignment_0
	{
		get
		{
			return this.horizontalAlignment_0;
		}
		set
		{
			this.horizontalAlignment_0 = value;
			if (this.TextBox_0 != null)
			{
				this.TextBox_0.TextAlign = value;
			}
		}
	}

	// Token: 0x17000132 RID: 306
	// (get) Token: 0x060003DF RID: 991 RVA: 0x00012A9C File Offset: 0x00010C9C
	// (set) Token: 0x060003E0 RID: 992 RVA: 0x00012AB4 File Offset: 0x00010CB4
	[Category("Options")]
	public int Int32_0
	{
		get
		{
			return this.int_2;
		}
		set
		{
			this.int_2 = value;
			if (this.TextBox_0 != null)
			{
				this.TextBox_0.MaxLength = value;
			}
		}
	}

	// Token: 0x17000133 RID: 307
	// (get) Token: 0x060003E1 RID: 993 RVA: 0x00012AD4 File Offset: 0x00010CD4
	// (set) Token: 0x060003E2 RID: 994 RVA: 0x00012ADC File Offset: 0x00010CDC
	[Category("Options")]
	public bool Boolean_0
	{
		get
		{
			return this.bool_0;
		}
		set
		{
			this.bool_0 = value;
			if (this.TextBox_0 != null)
			{
				this.TextBox_0.ReadOnly = value;
			}
		}
	}

	// Token: 0x17000134 RID: 308
	// (get) Token: 0x060003E3 RID: 995 RVA: 0x00012AFC File Offset: 0x00010CFC
	// (set) Token: 0x060003E4 RID: 996 RVA: 0x00012B04 File Offset: 0x00010D04
	[Category("Options")]
	public bool Boolean_1
	{
		get
		{
			return this.bool_1;
		}
		set
		{
			this.bool_1 = value;
			if (this.TextBox_0 != null)
			{
				this.TextBox_0.UseSystemPasswordChar = value;
			}
		}
	}

	// Token: 0x17000135 RID: 309
	// (get) Token: 0x060003E5 RID: 997 RVA: 0x00012B24 File Offset: 0x00010D24
	// (set) Token: 0x060003E6 RID: 998 RVA: 0x00012B2C File Offset: 0x00010D2C
	[Category("Options")]
	public bool Boolean_2
	{
		get
		{
			return this.bool_2;
		}
		set
		{
			this.bool_2 = value;
			checked
			{
				if (this.TextBox_0 != null)
				{
					this.TextBox_0.Multiline = value;
					if (!value)
					{
						base.Height = this.TextBox_0.Height + 11;
					}
					else
					{
						this.TextBox_0.Height = base.Height - 11;
					}
				}
			}
		}
	}

	// Token: 0x17000136 RID: 310
	// (get) Token: 0x060003E7 RID: 999 RVA: 0x00012B84 File Offset: 0x00010D84
	// (set) Token: 0x060003E8 RID: 1000 RVA: 0x00012B9C File Offset: 0x00010D9C
	[Category("Options")]
	public virtual string Text
	{
		get
		{
			return this.method_2();
		}
		set
		{
			this.method_3(value);
			if (this.TextBox_0 != null)
			{
				this.TextBox_0.Text = value;
			}
		}
	}

	// Token: 0x17000137 RID: 311
	// (get) Token: 0x060003E9 RID: 1001 RVA: 0x00012BBC File Offset: 0x00010DBC
	// (set) Token: 0x060003EA RID: 1002 RVA: 0x00012BD4 File Offset: 0x00010DD4
	[Category("Options")]
	public virtual Font Font
	{
		get
		{
			return this.method_4();
		}
		set
		{
			this.method_5(value);
			checked
			{
				if (this.TextBox_0 != null)
				{
					this.TextBox_0.Font = value;
					this.TextBox_0.Location = new Point(3, 5);
					this.TextBox_0.Width = base.Width - 6;
					if (!this.bool_2)
					{
						base.Height = this.TextBox_0.Height + 11;
					}
				}
			}
		}
	}

	// Token: 0x060003EB RID: 1003 RVA: 0x00012C44 File Offset: 0x00010E44
	protected virtual void OnCreateControl()
	{
		base.OnCreateControl();
		if (!base.Controls.Contains(this.TextBox_0))
		{
			base.Controls.Add(this.TextBox_0);
		}
	}

	// Token: 0x060003EC RID: 1004 RVA: 0x00012C74 File Offset: 0x00010E74
	private void method_0(object sender, EventArgs e)
	{
		this.System.Windows.Forms.Control.Text = this.TextBox_0.Text;
	}

	// Token: 0x060003ED RID: 1005 RVA: 0x00012C88 File Offset: 0x00010E88
	private void method_1(object sender, KeyEventArgs e)
	{
		if (e.Control && e.KeyCode == Keys.A)
		{
			this.TextBox_0.SelectAll();
			e.SuppressKeyPress = true;
		}
		if (e.Control && e.KeyCode == Keys.C)
		{
			this.TextBox_0.Copy();
			e.SuppressKeyPress = true;
		}
	}

	// Token: 0x060003EE RID: 1006 RVA: 0x00012CE8 File Offset: 0x00010EE8
	protected virtual void OnResize(EventArgs e)
	{
		this.TextBox_0.Location = new Point(5, 5);
		checked
		{
			this.TextBox_0.Width = base.Width - 10;
			if (this.bool_2)
			{
				this.TextBox_0.Height = base.Height - 11;
			}
			else
			{
				base.Height = this.TextBox_0.Height + 11;
			}
			base.OnResize(e);
		}
	}

	// Token: 0x17000138 RID: 312
	// (get) Token: 0x060003EF RID: 1007 RVA: 0x00012D54 File Offset: 0x00010F54
	// (set) Token: 0x060003F0 RID: 1008 RVA: 0x00012D6C File Offset: 0x00010F6C
	[Category("Colors")]
	public Color Color_0
	{
		get
		{
			return this.color_1;
		}
		set
		{
			this.color_1 = value;
		}
	}

	// Token: 0x17000139 RID: 313
	// (get) Token: 0x060003F1 RID: 1009 RVA: 0x00012D78 File Offset: 0x00010F78
	// (set) Token: 0x060003F2 RID: 1010 RVA: 0x00012D90 File Offset: 0x00010F90
	public virtual Color ForeColor
	{
		get
		{
			return this.color_1;
		}
		set
		{
			this.color_1 = value;
		}
	}

	// Token: 0x060003F3 RID: 1011 RVA: 0x00012D9C File Offset: 0x00010F9C
	protected virtual void OnMouseDown(MouseEventArgs e)
	{
		base.OnMouseDown(e);
		this.enum2_0 = Enum2.Down;
		base.Invalidate();
	}

	// Token: 0x060003F4 RID: 1012 RVA: 0x00012DB4 File Offset: 0x00010FB4
	protected virtual void OnMouseUp(MouseEventArgs e)
	{
		base.OnMouseUp(e);
		this.enum2_0 = Enum2.Over;
		this.TextBox_0.Focus();
		base.Invalidate();
	}

	// Token: 0x060003F5 RID: 1013 RVA: 0x00012DD8 File Offset: 0x00010FD8
	protected virtual void OnMouseEnter(EventArgs e)
	{
		base.OnMouseEnter(e);
		this.enum2_0 = Enum2.Over;
		this.TextBox_0.Focus();
		base.Invalidate();
	}

	// Token: 0x060003F6 RID: 1014 RVA: 0x00012DFC File Offset: 0x00010FFC
	protected virtual void OnMouseLeave(EventArgs e)
	{
		base.OnMouseLeave(e);
		this.enum2_0 = Enum2.None;
		base.Invalidate();
	}

	// Token: 0x060003F7 RID: 1015 RVA: 0x00012E14 File Offset: 0x00011014
	public Control32()
	{
		this.enum2_0 = Enum2.None;
		this.horizontalAlignment_0 = HorizontalAlignment.Left;
		this.int_2 = 32767;
		this.color_0 = Color.FromArgb(45, 47, 49);
		this.color_1 = Color.FromArgb(192, 192, 192);
		this.color_2 = Class16.color_0;
		base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.SupportsTransparentBackColor | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
		this.DoubleBuffered = true;
		this.BackColor = Color.Transparent;
		this.TextBox_0 = new TextBox();
		this.TextBox_0.Font = new Font("Segoe UI", 10f);
		this.TextBox_0.Text = this.System.Windows.Forms.Control.Text;
		this.TextBox_0.BackColor = this.color_0;
		this.TextBox_0.ForeColor = this.color_1;
		this.TextBox_0.MaxLength = this.int_2;
		this.TextBox_0.Multiline = this.bool_2;
		this.TextBox_0.ReadOnly = this.bool_0;
		this.TextBox_0.UseSystemPasswordChar = this.bool_1;
		this.TextBox_0.BorderStyle = BorderStyle.None;
		this.TextBox_0.Location = new Point(5, 5);
		checked
		{
			this.TextBox_0.Width = base.Width - 10;
			this.TextBox_0.Cursor = Cursors.IBeam;
			if (!this.bool_2)
			{
				base.Height = this.TextBox_0.Height + 11;
			}
			else
			{
				this.TextBox_0.Height = base.Height - 11;
			}
			this.TextBox_0.TextChanged += this.method_0;
			this.TextBox_0.KeyDown += this.method_1;
		}
	}

	// Token: 0x060003F8 RID: 1016 RVA: 0x00012FD4 File Offset: 0x000111D4
	protected virtual void OnPaint(PaintEventArgs e)
	{
		Class16.bitmap_0 = new Bitmap(base.Width, base.Height);
		Class16.graphics_0 = Graphics.FromImage(Class16.bitmap_0);
		checked
		{
			this.int_0 = base.Width - 1;
			this.int_1 = base.Height - 1;
			Rectangle rect = new Rectangle(0, 0, this.int_0, this.int_1);
			Graphics graphics_ = Class16.graphics_0;
			graphics_.SmoothingMode = SmoothingMode.HighQuality;
			graphics_.PixelOffsetMode = PixelOffsetMode.HighQuality;
			graphics_.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
			graphics_.Clear(this.BackColor);
			this.TextBox_0.BackColor = this.color_0;
			this.TextBox_0.ForeColor = this.color_1;
			graphics_.FillRectangle(new SolidBrush(this.color_0), rect);
			base.OnPaint(e);
			Class16.graphics_0.Dispose();
			e.Graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
			e.Graphics.DrawImageUnscaled(Class16.bitmap_0, 0, 0);
			Class16.bitmap_0.Dispose();
		}
	}

	// Token: 0x060003F9 RID: 1017 RVA: 0x000130D0 File Offset: 0x000112D0
	string method_2()
	{
		return base.Text;
	}

	// Token: 0x060003FA RID: 1018 RVA: 0x000130D8 File Offset: 0x000112D8
	void method_3(string string_0)
	{
		base.Text = string_0;
	}

	// Token: 0x060003FB RID: 1019 RVA: 0x000130E4 File Offset: 0x000112E4
	Font method_4()
	{
		return base.Font;
	}

	// Token: 0x060003FC RID: 1020 RVA: 0x000130EC File Offset: 0x000112EC
	void method_5(Font font_0)
	{
		base.Font = font_0;
	}

	// Token: 0x04000210 RID: 528
	private int int_0;

	// Token: 0x04000211 RID: 529
	private int int_1;

	// Token: 0x04000212 RID: 530
	private Enum2 enum2_0;

	// Token: 0x04000213 RID: 531
	[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	[CompilerGenerated]
	[AccessedThroughProperty("TB")]
	private TextBox textBox_0;

	// Token: 0x04000214 RID: 532
	private HorizontalAlignment horizontalAlignment_0;

	// Token: 0x04000215 RID: 533
	private int int_2;

	// Token: 0x04000216 RID: 534
	private bool bool_0;

	// Token: 0x04000217 RID: 535
	private bool bool_1;

	// Token: 0x04000218 RID: 536
	private bool bool_2;

	// Token: 0x04000219 RID: 537
	private Color color_0;

	// Token: 0x0400021A RID: 538
	private Color color_1;

	// Token: 0x0400021B RID: 539
	private Color color_2;
}
