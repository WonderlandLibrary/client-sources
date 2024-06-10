using System;
using System.ComponentModel;
using System.Diagnostics;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Runtime.CompilerServices;
using System.Windows.Forms;

// Token: 0x0200008C RID: 140
[DefaultEvent("TextChanged")]
public class GControl20 : Control
{
	// Token: 0x1700019E RID: 414
	// (get) Token: 0x06000604 RID: 1540 RVA: 0x0001DD30 File Offset: 0x0001BF30
	// (set) Token: 0x06000605 RID: 1541 RVA: 0x0001DD38 File Offset: 0x0001BF38
	private virtual TextBox TextBox_0 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x1700019F RID: 415
	// (get) Token: 0x06000606 RID: 1542 RVA: 0x0001DD44 File Offset: 0x0001BF44
	// (set) Token: 0x06000607 RID: 1543 RVA: 0x0001DD5C File Offset: 0x0001BF5C
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

	// Token: 0x170001A0 RID: 416
	// (get) Token: 0x06000608 RID: 1544 RVA: 0x0001DD7C File Offset: 0x0001BF7C
	// (set) Token: 0x06000609 RID: 1545 RVA: 0x0001DD94 File Offset: 0x0001BF94
	[Category("Options")]
	public int Int32_0
	{
		get
		{
			return this.int_0;
		}
		set
		{
			this.int_0 = value;
			if (this.TextBox_0 != null)
			{
				this.TextBox_0.MaxLength = value;
			}
		}
	}

	// Token: 0x170001A1 RID: 417
	// (get) Token: 0x0600060A RID: 1546 RVA: 0x0001DDB4 File Offset: 0x0001BFB4
	// (set) Token: 0x0600060B RID: 1547 RVA: 0x0001DDBC File Offset: 0x0001BFBC
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

	// Token: 0x170001A2 RID: 418
	// (get) Token: 0x0600060C RID: 1548 RVA: 0x0001DDDC File Offset: 0x0001BFDC
	// (set) Token: 0x0600060D RID: 1549 RVA: 0x0001DDE4 File Offset: 0x0001BFE4
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

	// Token: 0x170001A3 RID: 419
	// (get) Token: 0x0600060E RID: 1550 RVA: 0x0001DE04 File Offset: 0x0001C004
	// (set) Token: 0x0600060F RID: 1551 RVA: 0x0001DE0C File Offset: 0x0001C00C
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
					if (value)
					{
						this.TextBox_0.Height = base.Height - 11;
					}
					else
					{
						base.Height = this.TextBox_0.Height + 11;
					}
				}
			}
		}
	}

	// Token: 0x170001A4 RID: 420
	// (get) Token: 0x06000610 RID: 1552 RVA: 0x0001DE64 File Offset: 0x0001C064
	// (set) Token: 0x06000611 RID: 1553 RVA: 0x0001DE7C File Offset: 0x0001C07C
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

	// Token: 0x170001A5 RID: 421
	// (get) Token: 0x06000612 RID: 1554 RVA: 0x0001DE9C File Offset: 0x0001C09C
	// (set) Token: 0x06000613 RID: 1555 RVA: 0x0001DEB4 File Offset: 0x0001C0B4
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
					this.TextBox_0.Width = base.Width - 35;
					if (!this.bool_2)
					{
						base.Height = this.TextBox_0.Height + 11;
					}
				}
			}
		}
	}

	// Token: 0x06000614 RID: 1556 RVA: 0x0001DF24 File Offset: 0x0001C124
	protected virtual void OnCreateControl()
	{
		base.OnCreateControl();
		if (!base.Controls.Contains(this.TextBox_0))
		{
			base.Controls.Add(this.TextBox_0);
		}
	}

	// Token: 0x06000615 RID: 1557 RVA: 0x0001DF54 File Offset: 0x0001C154
	private void method_0(object sender, EventArgs e)
	{
		this.System.Windows.Forms.Control.Text = this.TextBox_0.Text;
	}

	// Token: 0x06000616 RID: 1558 RVA: 0x0001DF68 File Offset: 0x0001C168
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

	// Token: 0x06000617 RID: 1559 RVA: 0x0001DFC8 File Offset: 0x0001C1C8
	protected virtual void OnResize(EventArgs e)
	{
		this.TextBox_0.Location = new Point(5, 5);
		checked
		{
			this.TextBox_0.Width = base.Width - 35;
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

	// Token: 0x170001A6 RID: 422
	// (get) Token: 0x06000618 RID: 1560 RVA: 0x0001E034 File Offset: 0x0001C234
	// (set) Token: 0x06000619 RID: 1561 RVA: 0x0001E04C File Offset: 0x0001C24C
	[Category("Colours")]
	public Color Color_0
	{
		get
		{
			return this.color_0;
		}
		set
		{
			this.color_0 = value;
		}
	}

	// Token: 0x170001A7 RID: 423
	// (get) Token: 0x0600061A RID: 1562 RVA: 0x0001E058 File Offset: 0x0001C258
	// (set) Token: 0x0600061B RID: 1563 RVA: 0x0001E070 File Offset: 0x0001C270
	[Category("Colours")]
	public Color Color_1
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

	// Token: 0x170001A8 RID: 424
	// (get) Token: 0x0600061C RID: 1564 RVA: 0x0001E07C File Offset: 0x0001C27C
	// (set) Token: 0x0600061D RID: 1565 RVA: 0x0001E094 File Offset: 0x0001C294
	[Category("Colours")]
	public Color Color_2
	{
		get
		{
			return this.color_2;
		}
		set
		{
			this.color_2 = value;
		}
	}

	// Token: 0x0600061E RID: 1566 RVA: 0x0001E0A0 File Offset: 0x0001C2A0
	protected virtual void OnMouseDown(MouseEventArgs e)
	{
		base.OnMouseDown(e);
		this.enum0_0 = Enum0.Down;
		base.Invalidate();
	}

	// Token: 0x0600061F RID: 1567 RVA: 0x0001E0B8 File Offset: 0x0001C2B8
	protected virtual void OnMouseUp(MouseEventArgs e)
	{
		base.OnMouseUp(e);
		this.enum0_0 = Enum0.Over;
		this.TextBox_0.Focus();
		base.Invalidate();
	}

	// Token: 0x06000620 RID: 1568 RVA: 0x0001E0DC File Offset: 0x0001C2DC
	protected virtual void OnMouseLeave(EventArgs e)
	{
		base.OnMouseLeave(e);
		this.enum0_0 = Enum0.None;
		base.Invalidate();
	}

	// Token: 0x06000621 RID: 1569 RVA: 0x0001E0F4 File Offset: 0x0001C2F4
	public GControl20()
	{
		this.enum0_0 = Enum0.None;
		this.color_0 = Color.FromArgb(42, 42, 42);
		this.color_1 = Color.FromArgb(255, 255, 255);
		this.color_2 = Color.FromArgb(35, 35, 35);
		this.horizontalAlignment_0 = HorizontalAlignment.Left;
		this.int_0 = 32767;
		base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.SupportsTransparentBackColor | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
		this.DoubleBuffered = true;
		this.BackColor = Color.Transparent;
		this.TextBox_0 = new TextBox();
		this.TextBox_0.Height = 190;
		this.TextBox_0.Font = new Font("Segoe UI", 10f);
		this.TextBox_0.Text = this.System.Windows.Forms.Control.Text;
		this.TextBox_0.BackColor = Color.FromArgb(42, 42, 42);
		this.TextBox_0.ForeColor = Color.FromArgb(255, 255, 255);
		this.TextBox_0.MaxLength = this.int_0;
		this.TextBox_0.Multiline = false;
		this.TextBox_0.ReadOnly = this.bool_0;
		this.TextBox_0.UseSystemPasswordChar = this.bool_1;
		this.TextBox_0.BorderStyle = BorderStyle.None;
		this.TextBox_0.Location = new Point(5, 5);
		this.TextBox_0.Width = checked(base.Width - 35);
		this.TextBox_0.TextChanged += this.method_0;
		this.TextBox_0.KeyDown += this.method_1;
	}

	// Token: 0x06000622 RID: 1570 RVA: 0x0001E294 File Offset: 0x0001C494
	protected virtual void OnPaint(PaintEventArgs e)
	{
		Bitmap bitmap = new Bitmap(base.Width, base.Height);
		Graphics graphics = Graphics.FromImage(bitmap);
		Rectangle rectangle_ = new Rectangle(0, 0, base.Width, base.Height);
		Graphics graphics2 = graphics;
		graphics2.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
		graphics2.SmoothingMode = SmoothingMode.HighQuality;
		graphics2.PixelOffsetMode = PixelOffsetMode.HighQuality;
		graphics2.Clear(this.BackColor);
		this.TextBox_0.BackColor = Color.FromArgb(42, 42, 42);
		this.TextBox_0.ForeColor = Color.FromArgb(255, 255, 255);
		GraphicsPath path = Class23.smethod_0(rectangle_, 6);
		graphics2.FillPath(new SolidBrush(Color.FromArgb(42, 42, 42)), path);
		graphics2.DrawPath(new Pen(new SolidBrush(Color.FromArgb(35, 35, 35)), 2f), path);
		checked
		{
			graphics2.FillPie(new SolidBrush(base.FindForm().BackColor), new Rectangle(base.Width - 25, base.Height - 23, base.Height + 25, base.Height + 25), 180f, 90f);
			graphics2.DrawPie(new Pen(Color.FromArgb(35, 35, 35), 2f), new Rectangle(base.Width - 25, base.Height - 23, base.Height + 25, base.Height + 25), 180f, 90f);
			base.OnPaint(e);
			graphics.Dispose();
			e.Graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
			e.Graphics.DrawImageUnscaled(bitmap, 0, 0);
			bitmap.Dispose();
		}
	}

	// Token: 0x06000623 RID: 1571 RVA: 0x0001E430 File Offset: 0x0001C630
	string method_2()
	{
		return base.Text;
	}

	// Token: 0x06000624 RID: 1572 RVA: 0x0001E438 File Offset: 0x0001C638
	void method_3(string string_0)
	{
		base.Text = string_0;
	}

	// Token: 0x06000625 RID: 1573 RVA: 0x0001E444 File Offset: 0x0001C644
	Font method_4()
	{
		return base.Font;
	}

	// Token: 0x06000626 RID: 1574 RVA: 0x0001E44C File Offset: 0x0001C64C
	void method_5(Font font_0)
	{
		base.Font = font_0;
	}

	// Token: 0x04000301 RID: 769
	private Enum0 enum0_0;

	// Token: 0x04000302 RID: 770
	[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	[CompilerGenerated]
	[AccessedThroughProperty("TB")]
	private TextBox textBox_0;

	// Token: 0x04000303 RID: 771
	private Color color_0;

	// Token: 0x04000304 RID: 772
	private Color color_1;

	// Token: 0x04000305 RID: 773
	private Color color_2;

	// Token: 0x04000306 RID: 774
	private HorizontalAlignment horizontalAlignment_0;

	// Token: 0x04000307 RID: 775
	private int int_0;

	// Token: 0x04000308 RID: 776
	private bool bool_0;

	// Token: 0x04000309 RID: 777
	private bool bool_1;

	// Token: 0x0400030A RID: 778
	private bool bool_2;
}
