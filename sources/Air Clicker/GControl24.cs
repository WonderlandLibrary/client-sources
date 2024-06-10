using System;
using System.ComponentModel;
using System.Diagnostics;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Runtime.CompilerServices;
using System.Windows.Forms;

// Token: 0x02000091 RID: 145
public class GControl24 : Control
{
	// Token: 0x170001C0 RID: 448
	// (get) Token: 0x06000678 RID: 1656 RVA: 0x0001F568 File Offset: 0x0001D768
	// (set) Token: 0x06000679 RID: 1657 RVA: 0x0001F570 File Offset: 0x0001D770
	private virtual TextBox TextBox_0 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x0600067A RID: 1658 RVA: 0x0001F57C File Offset: 0x0001D77C
	public void method_0()
	{
		this.TextBox_0.Focus();
		this.TextBox_0.SelectAll();
	}

	// Token: 0x170001C1 RID: 449
	// (get) Token: 0x0600067B RID: 1659 RVA: 0x0001F598 File Offset: 0x0001D798
	// (set) Token: 0x0600067C RID: 1660 RVA: 0x0001F5B0 File Offset: 0x0001D7B0
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

	// Token: 0x170001C2 RID: 450
	// (get) Token: 0x0600067D RID: 1661 RVA: 0x0001F5D0 File Offset: 0x0001D7D0
	// (set) Token: 0x0600067E RID: 1662 RVA: 0x0001F5E8 File Offset: 0x0001D7E8
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

	// Token: 0x170001C3 RID: 451
	// (get) Token: 0x0600067F RID: 1663 RVA: 0x0001F608 File Offset: 0x0001D808
	// (set) Token: 0x06000680 RID: 1664 RVA: 0x0001F610 File Offset: 0x0001D810
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

	// Token: 0x170001C4 RID: 452
	// (get) Token: 0x06000681 RID: 1665 RVA: 0x0001F630 File Offset: 0x0001D830
	// (set) Token: 0x06000682 RID: 1666 RVA: 0x0001F638 File Offset: 0x0001D838
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

	// Token: 0x170001C5 RID: 453
	// (get) Token: 0x06000683 RID: 1667 RVA: 0x0001F658 File Offset: 0x0001D858
	// (set) Token: 0x06000684 RID: 1668 RVA: 0x0001F660 File Offset: 0x0001D860
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

	// Token: 0x170001C6 RID: 454
	// (get) Token: 0x06000685 RID: 1669 RVA: 0x0001F6B8 File Offset: 0x0001D8B8
	// (set) Token: 0x06000686 RID: 1670 RVA: 0x0001F6D0 File Offset: 0x0001D8D0
	[Category("Options")]
	public virtual string Text
	{
		get
		{
			return this.method_3();
		}
		set
		{
			this.method_4(value);
			if (this.TextBox_0 != null)
			{
				this.TextBox_0.Text = value;
			}
		}
	}

	// Token: 0x170001C7 RID: 455
	// (get) Token: 0x06000687 RID: 1671 RVA: 0x0001F6F0 File Offset: 0x0001D8F0
	// (set) Token: 0x06000688 RID: 1672 RVA: 0x0001F708 File Offset: 0x0001D908
	[Category("Options")]
	public virtual Font Font
	{
		get
		{
			return this.method_5();
		}
		set
		{
			this.method_6(value);
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

	// Token: 0x06000689 RID: 1673 RVA: 0x0001F778 File Offset: 0x0001D978
	protected virtual void OnCreateControl()
	{
		base.OnCreateControl();
		if (!base.Controls.Contains(this.TextBox_0))
		{
			base.Controls.Add(this.TextBox_0);
		}
	}

	// Token: 0x0600068A RID: 1674 RVA: 0x0001F7A8 File Offset: 0x0001D9A8
	private void method_1(object sender, EventArgs e)
	{
		this.System.Windows.Forms.Control.Text = this.TextBox_0.Text;
	}

	// Token: 0x0600068B RID: 1675 RVA: 0x0001F7BC File Offset: 0x0001D9BC
	private void method_2(object sender, KeyEventArgs e)
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

	// Token: 0x0600068C RID: 1676 RVA: 0x0001F81C File Offset: 0x0001DA1C
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

	// Token: 0x170001C8 RID: 456
	// (get) Token: 0x0600068D RID: 1677 RVA: 0x0001F888 File Offset: 0x0001DA88
	// (set) Token: 0x0600068E RID: 1678 RVA: 0x0001F8A0 File Offset: 0x0001DAA0
	public GControl24.GEnum3 GEnum3_0
	{
		get
		{
			return this.genum3_0;
		}
		set
		{
			this.genum3_0 = value;
		}
	}

	// Token: 0x170001C9 RID: 457
	// (get) Token: 0x0600068F RID: 1679 RVA: 0x0001F8AC File Offset: 0x0001DAAC
	// (set) Token: 0x06000690 RID: 1680 RVA: 0x0001F8C4 File Offset: 0x0001DAC4
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

	// Token: 0x170001CA RID: 458
	// (get) Token: 0x06000691 RID: 1681 RVA: 0x0001F8D0 File Offset: 0x0001DAD0
	// (set) Token: 0x06000692 RID: 1682 RVA: 0x0001F8E8 File Offset: 0x0001DAE8
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

	// Token: 0x170001CB RID: 459
	// (get) Token: 0x06000693 RID: 1683 RVA: 0x0001F8F4 File Offset: 0x0001DAF4
	// (set) Token: 0x06000694 RID: 1684 RVA: 0x0001F90C File Offset: 0x0001DB0C
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

	// Token: 0x06000695 RID: 1685 RVA: 0x0001F918 File Offset: 0x0001DB18
	protected virtual void OnMouseDown(MouseEventArgs e)
	{
		base.OnMouseDown(e);
		this.enum0_0 = Enum0.Down;
		base.Invalidate();
	}

	// Token: 0x06000696 RID: 1686 RVA: 0x0001F930 File Offset: 0x0001DB30
	protected virtual void OnMouseUp(MouseEventArgs e)
	{
		base.OnMouseUp(e);
		this.enum0_0 = Enum0.Over;
		this.TextBox_0.Focus();
		base.Invalidate();
	}

	// Token: 0x06000697 RID: 1687 RVA: 0x0001F954 File Offset: 0x0001DB54
	protected virtual void OnMouseLeave(EventArgs e)
	{
		base.OnMouseLeave(e);
		this.enum0_0 = Enum0.None;
		base.Invalidate();
	}

	// Token: 0x06000698 RID: 1688 RVA: 0x0001F96C File Offset: 0x0001DB6C
	public GControl24()
	{
		this.enum0_0 = Enum0.None;
		this.color_0 = Color.FromArgb(42, 42, 42);
		this.color_1 = Color.FromArgb(255, 255, 255);
		this.color_2 = Color.FromArgb(35, 35, 35);
		this.genum3_0 = GControl24.GEnum3.NotRounded;
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
		this.TextBox_0.TextChanged += this.method_1;
		this.TextBox_0.KeyDown += this.method_2;
	}

	// Token: 0x06000699 RID: 1689 RVA: 0x0001FB14 File Offset: 0x0001DD14
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
		GControl24.GEnum3 genum = this.genum3_0;
		if (genum != GControl24.GEnum3.Rounded)
		{
			if (genum == GControl24.GEnum3.NotRounded)
			{
				graphics2.FillRectangle(new SolidBrush(Color.FromArgb(42, 42, 42)), checked(new Rectangle(0, 0, base.Width - 1, base.Height - 1)));
				graphics2.DrawRectangle(new Pen(new SolidBrush(Color.FromArgb(35, 35, 35)), 2f), new Rectangle(0, 0, base.Width, base.Height));
			}
		}
		else
		{
			GraphicsPath path = Class23.smethod_0(rectangle_, 6);
			graphics2.FillPath(new SolidBrush(Color.FromArgb(42, 42, 42)), path);
			graphics2.DrawPath(new Pen(new SolidBrush(Color.FromArgb(35, 35, 35)), 2f), path);
		}
		base.OnPaint(e);
		graphics.Dispose();
		e.Graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
		e.Graphics.DrawImageUnscaled(bitmap, 0, 0);
		bitmap.Dispose();
	}

	// Token: 0x0600069A RID: 1690 RVA: 0x0001FC94 File Offset: 0x0001DE94
	string method_3()
	{
		return base.Text;
	}

	// Token: 0x0600069B RID: 1691 RVA: 0x0001FC9C File Offset: 0x0001DE9C
	void method_4(string string_0)
	{
		base.Text = string_0;
	}

	// Token: 0x0600069C RID: 1692 RVA: 0x0001FCA8 File Offset: 0x0001DEA8
	Font method_5()
	{
		return base.Font;
	}

	// Token: 0x0600069D RID: 1693 RVA: 0x0001FCB0 File Offset: 0x0001DEB0
	void method_6(Font font_0)
	{
		base.Font = font_0;
	}

	// Token: 0x04000324 RID: 804
	private Enum0 enum0_0;

	// Token: 0x04000325 RID: 805
	[AccessedThroughProperty("TB")]
	[CompilerGenerated]
	[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	private TextBox textBox_0;

	// Token: 0x04000326 RID: 806
	private Color color_0;

	// Token: 0x04000327 RID: 807
	private Color color_1;

	// Token: 0x04000328 RID: 808
	private Color color_2;

	// Token: 0x04000329 RID: 809
	private GControl24.GEnum3 genum3_0;

	// Token: 0x0400032A RID: 810
	private HorizontalAlignment horizontalAlignment_0;

	// Token: 0x0400032B RID: 811
	private int int_0;

	// Token: 0x0400032C RID: 812
	private bool bool_0;

	// Token: 0x0400032D RID: 813
	private bool bool_1;

	// Token: 0x0400032E RID: 814
	private bool bool_2;

	// Token: 0x02000092 RID: 146
	public enum GEnum3
	{
		// Token: 0x04000330 RID: 816
		Rounded,
		// Token: 0x04000331 RID: 817
		NotRounded
	}
}
