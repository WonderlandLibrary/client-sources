using System;
using System.ComponentModel;
using System.Diagnostics;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Runtime.CompilerServices;
using System.Windows.Forms;

// Token: 0x0200008D RID: 141
[DefaultEvent("TextChanged")]
public class GControl21 : Control
{
	// Token: 0x170001A9 RID: 425
	// (get) Token: 0x06000627 RID: 1575 RVA: 0x0001E458 File Offset: 0x0001C658
	// (set) Token: 0x06000628 RID: 1576 RVA: 0x0001E460 File Offset: 0x0001C660
	private virtual TextBox TextBox_0 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x170001AA RID: 426
	// (get) Token: 0x06000629 RID: 1577 RVA: 0x0001E46C File Offset: 0x0001C66C
	// (set) Token: 0x0600062A RID: 1578 RVA: 0x0001E484 File Offset: 0x0001C684
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

	// Token: 0x170001AB RID: 427
	// (get) Token: 0x0600062B RID: 1579 RVA: 0x0001E4A4 File Offset: 0x0001C6A4
	// (set) Token: 0x0600062C RID: 1580 RVA: 0x0001E4BC File Offset: 0x0001C6BC
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

	// Token: 0x170001AC RID: 428
	// (get) Token: 0x0600062D RID: 1581 RVA: 0x0001E4DC File Offset: 0x0001C6DC
	// (set) Token: 0x0600062E RID: 1582 RVA: 0x0001E4E4 File Offset: 0x0001C6E4
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

	// Token: 0x170001AD RID: 429
	// (get) Token: 0x0600062F RID: 1583 RVA: 0x0001E504 File Offset: 0x0001C704
	// (set) Token: 0x06000630 RID: 1584 RVA: 0x0001E50C File Offset: 0x0001C70C
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

	// Token: 0x170001AE RID: 430
	// (get) Token: 0x06000631 RID: 1585 RVA: 0x0001E52C File Offset: 0x0001C72C
	// (set) Token: 0x06000632 RID: 1586 RVA: 0x0001E534 File Offset: 0x0001C734
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

	// Token: 0x170001AF RID: 431
	// (get) Token: 0x06000633 RID: 1587 RVA: 0x0001E58C File Offset: 0x0001C78C
	// (set) Token: 0x06000634 RID: 1588 RVA: 0x0001E5A4 File Offset: 0x0001C7A4
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

	// Token: 0x170001B0 RID: 432
	// (get) Token: 0x06000635 RID: 1589 RVA: 0x0001E5C4 File Offset: 0x0001C7C4
	// (set) Token: 0x06000636 RID: 1590 RVA: 0x0001E5DC File Offset: 0x0001C7DC
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

	// Token: 0x06000637 RID: 1591 RVA: 0x0001E64C File Offset: 0x0001C84C
	protected virtual void OnCreateControl()
	{
		base.OnCreateControl();
		if (!base.Controls.Contains(this.TextBox_0))
		{
			base.Controls.Add(this.TextBox_0);
		}
	}

	// Token: 0x06000638 RID: 1592 RVA: 0x0001E67C File Offset: 0x0001C87C
	private void method_0(object sender, EventArgs e)
	{
		this.System.Windows.Forms.Control.Text = this.TextBox_0.Text;
	}

	// Token: 0x06000639 RID: 1593 RVA: 0x0001E690 File Offset: 0x0001C890
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

	// Token: 0x0600063A RID: 1594 RVA: 0x0001E6F0 File Offset: 0x0001C8F0
	protected virtual void OnResize(EventArgs e)
	{
		this.TextBox_0.Location = new Point(5, 5);
		checked
		{
			this.TextBox_0.Width = base.Width - 35;
			if (!this.bool_2)
			{
				base.Height = this.TextBox_0.Height + 11;
			}
			else
			{
				this.TextBox_0.Height = base.Height - 11;
			}
			base.OnResize(e);
		}
	}

	// Token: 0x170001B1 RID: 433
	// (get) Token: 0x0600063B RID: 1595 RVA: 0x0001E75C File Offset: 0x0001C95C
	// (set) Token: 0x0600063C RID: 1596 RVA: 0x0001E774 File Offset: 0x0001C974
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

	// Token: 0x170001B2 RID: 434
	// (get) Token: 0x0600063D RID: 1597 RVA: 0x0001E780 File Offset: 0x0001C980
	// (set) Token: 0x0600063E RID: 1598 RVA: 0x0001E798 File Offset: 0x0001C998
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

	// Token: 0x170001B3 RID: 435
	// (get) Token: 0x0600063F RID: 1599 RVA: 0x0001E7A4 File Offset: 0x0001C9A4
	// (set) Token: 0x06000640 RID: 1600 RVA: 0x0001E7BC File Offset: 0x0001C9BC
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

	// Token: 0x06000641 RID: 1601 RVA: 0x0001E7C8 File Offset: 0x0001C9C8
	protected virtual void OnMouseDown(MouseEventArgs e)
	{
		base.OnMouseDown(e);
		this.enum0_0 = Enum0.Down;
		base.Invalidate();
	}

	// Token: 0x06000642 RID: 1602 RVA: 0x0001E7E0 File Offset: 0x0001C9E0
	protected virtual void OnMouseUp(MouseEventArgs e)
	{
		base.OnMouseUp(e);
		this.enum0_0 = Enum0.Over;
		this.TextBox_0.Focus();
		base.Invalidate();
	}

	// Token: 0x06000643 RID: 1603 RVA: 0x0001E804 File Offset: 0x0001CA04
	protected virtual void OnMouseLeave(EventArgs e)
	{
		base.OnMouseLeave(e);
		this.enum0_0 = Enum0.None;
		base.Invalidate();
	}

	// Token: 0x06000644 RID: 1604 RVA: 0x0001E81C File Offset: 0x0001CA1C
	public GControl21()
	{
		this.enum0_0 = Enum0.None;
		this.color_0 = Color.FromArgb(255, 255, 255);
		this.color_1 = Color.FromArgb(50, 50, 50);
		this.color_2 = Color.FromArgb(180, 187, 205);
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

	// Token: 0x06000645 RID: 1605 RVA: 0x0001E9C8 File Offset: 0x0001CBC8
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
			graphics2.FillPie(new SolidBrush(base.FindForm().BackColor), new Rectangle(base.Width - 25, base.Height - 60, base.Height + 25, base.Height + 25), 90f, 90f);
			graphics2.DrawPie(new Pen(Color.FromArgb(35, 35, 35), 2f), new Rectangle(base.Width - 25, base.Height - 60, base.Height + 25, base.Height + 25), 90f, 90f);
			base.OnPaint(e);
			graphics.Dispose();
			e.Graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
			e.Graphics.DrawImageUnscaled(bitmap, 0, 0);
			bitmap.Dispose();
		}
	}

	// Token: 0x06000646 RID: 1606 RVA: 0x0001EB64 File Offset: 0x0001CD64
	string method_2()
	{
		return base.Text;
	}

	// Token: 0x06000647 RID: 1607 RVA: 0x0001EB6C File Offset: 0x0001CD6C
	void method_3(string string_0)
	{
		base.Text = string_0;
	}

	// Token: 0x06000648 RID: 1608 RVA: 0x0001EB78 File Offset: 0x0001CD78
	Font method_4()
	{
		return base.Font;
	}

	// Token: 0x06000649 RID: 1609 RVA: 0x0001EB80 File Offset: 0x0001CD80
	void method_5(Font font_0)
	{
		base.Font = font_0;
	}

	// Token: 0x0400030B RID: 779
	private Enum0 enum0_0;

	// Token: 0x0400030C RID: 780
	[CompilerGenerated]
	[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	[AccessedThroughProperty("TB")]
	private TextBox textBox_0;

	// Token: 0x0400030D RID: 781
	private Color color_0;

	// Token: 0x0400030E RID: 782
	private Color color_1;

	// Token: 0x0400030F RID: 783
	private Color color_2;

	// Token: 0x04000310 RID: 784
	private HorizontalAlignment horizontalAlignment_0;

	// Token: 0x04000311 RID: 785
	private int int_0;

	// Token: 0x04000312 RID: 786
	private bool bool_0;

	// Token: 0x04000313 RID: 787
	private bool bool_1;

	// Token: 0x04000314 RID: 788
	private bool bool_2;
}
