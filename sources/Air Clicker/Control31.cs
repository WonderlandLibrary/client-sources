using System;
using System.ComponentModel;
using System.Diagnostics;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Runtime.CompilerServices;
using System.Threading;
using System.Windows.Forms;

// Token: 0x02000047 RID: 71
[DefaultEvent("CheckedChanged")]
internal class Control31 : Control
{
	// Token: 0x060003C4 RID: 964 RVA: 0x0001246C File Offset: 0x0001066C
	protected virtual void OnTextChanged(EventArgs e)
	{
		base.OnTextChanged(e);
		base.Invalidate();
	}

	// Token: 0x1700012C RID: 300
	// (get) Token: 0x060003C5 RID: 965 RVA: 0x0001247C File Offset: 0x0001067C
	// (set) Token: 0x060003C6 RID: 966 RVA: 0x00012484 File Offset: 0x00010684
	public bool Boolean_0
	{
		get
		{
			return this.bool_0;
		}
		set
		{
			this.bool_0 = value;
			base.Invalidate();
		}
	}

	// Token: 0x1400000B RID: 11
	// (add) Token: 0x060003C7 RID: 967 RVA: 0x00012494 File Offset: 0x00010694
	// (remove) Token: 0x060003C8 RID: 968 RVA: 0x000124D0 File Offset: 0x000106D0
	public event Control31.Delegate12 Event_0
	{
		[CompilerGenerated]
		add
		{
			Control31.Delegate12 @delegate = this.delegate12_0;
			Control31.Delegate12 delegate2;
			do
			{
				delegate2 = @delegate;
				Control31.Delegate12 value2 = (Control31.Delegate12)Delegate.Combine(delegate2, value);
				@delegate = Interlocked.CompareExchange<Control31.Delegate12>(ref this.delegate12_0, value2, delegate2);
			}
			while (@delegate != delegate2);
		}
		[CompilerGenerated]
		remove
		{
			Control31.Delegate12 @delegate = this.delegate12_0;
			Control31.Delegate12 delegate2;
			do
			{
				delegate2 = @delegate;
				Control31.Delegate12 value2 = (Control31.Delegate12)Delegate.Remove(delegate2, value);
				@delegate = Interlocked.CompareExchange<Control31.Delegate12>(ref this.delegate12_0, value2, delegate2);
			}
			while (@delegate != delegate2);
		}
	}

	// Token: 0x060003C9 RID: 969 RVA: 0x0001250C File Offset: 0x0001070C
	protected virtual void OnClick(EventArgs e)
	{
		this.bool_0 = !this.bool_0;
		Control31.Delegate12 @delegate = this.delegate12_0;
		if (@delegate != null)
		{
			@delegate(this);
		}
		base.OnClick(e);
	}

	// Token: 0x1700012D RID: 301
	// (get) Token: 0x060003CA RID: 970 RVA: 0x00012540 File Offset: 0x00010740
	// (set) Token: 0x060003CB RID: 971 RVA: 0x00012558 File Offset: 0x00010758
	[Category("Options")]
	public Control31.Enum5 Enum5_0
	{
		get
		{
			return this.enum5_0;
		}
		set
		{
			this.enum5_0 = value;
		}
	}

	// Token: 0x060003CC RID: 972 RVA: 0x00012564 File Offset: 0x00010764
	protected virtual void OnResize(EventArgs e)
	{
		base.OnResize(e);
		base.Height = 22;
	}

	// Token: 0x1700012E RID: 302
	// (get) Token: 0x060003CD RID: 973 RVA: 0x00012578 File Offset: 0x00010778
	// (set) Token: 0x060003CE RID: 974 RVA: 0x00012590 File Offset: 0x00010790
	[Category("Colors")]
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

	// Token: 0x1700012F RID: 303
	// (get) Token: 0x060003CF RID: 975 RVA: 0x0001259C File Offset: 0x0001079C
	// (set) Token: 0x060003D0 RID: 976 RVA: 0x000125B4 File Offset: 0x000107B4
	[Category("Colors")]
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

	// Token: 0x060003D1 RID: 977 RVA: 0x000125C0 File Offset: 0x000107C0
	protected virtual void OnMouseDown(MouseEventArgs e)
	{
		base.OnMouseDown(e);
		this.enum2_0 = Enum2.Down;
		base.Invalidate();
	}

	// Token: 0x060003D2 RID: 978 RVA: 0x000125D8 File Offset: 0x000107D8
	protected virtual void OnMouseUp(MouseEventArgs e)
	{
		base.OnMouseUp(e);
		this.enum2_0 = Enum2.Over;
		base.Invalidate();
	}

	// Token: 0x060003D3 RID: 979 RVA: 0x000125F0 File Offset: 0x000107F0
	protected virtual void OnMouseEnter(EventArgs e)
	{
		base.OnMouseEnter(e);
		this.enum2_0 = Enum2.Over;
		base.Invalidate();
	}

	// Token: 0x060003D4 RID: 980 RVA: 0x00012608 File Offset: 0x00010808
	protected virtual void OnMouseLeave(EventArgs e)
	{
		base.OnMouseLeave(e);
		this.enum2_0 = Enum2.None;
		base.Invalidate();
	}

	// Token: 0x060003D5 RID: 981 RVA: 0x00012620 File Offset: 0x00010820
	public Control31()
	{
		this.enum2_0 = Enum2.None;
		this.color_0 = Color.FromArgb(45, 47, 49);
		this.color_1 = Class16.color_0;
		this.color_2 = Color.FromArgb(243, 243, 243);
		base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
		this.DoubleBuffered = true;
		this.BackColor = Color.FromArgb(60, 70, 73);
		this.Cursor = Cursors.Hand;
		this.Font = new Font("Segoe UI", 10f);
		base.Size = new Size(112, 22);
	}

	// Token: 0x060003D6 RID: 982 RVA: 0x000126C4 File Offset: 0x000108C4
	protected virtual void OnPaint(PaintEventArgs e)
	{
		Class16.bitmap_0 = new Bitmap(base.Width, base.Height);
		Class16.graphics_0 = Graphics.FromImage(Class16.bitmap_0);
		checked
		{
			this.int_0 = base.Width - 1;
			this.int_1 = base.Height - 1;
			Rectangle rect = new Rectangle(0, 2, base.Height - 5, base.Height - 5);
			Graphics graphics_ = Class16.graphics_0;
			graphics_.SmoothingMode = SmoothingMode.HighQuality;
			graphics_.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
			graphics_.Clear(this.BackColor);
			Control31.Enum5 @enum = this.enum5_0;
			if (@enum != Control31.Enum5.Style1)
			{
				if (@enum == Control31.Enum5.Style2)
				{
					graphics_.FillRectangle(new SolidBrush(this.color_0), rect);
					Enum2 enum2 = this.enum2_0;
					if (enum2 == Enum2.Over)
					{
						graphics_.DrawRectangle(new Pen(this.color_1), rect);
						graphics_.FillRectangle(new SolidBrush(Color.FromArgb(118, 213, 170)), rect);
					}
					else if (enum2 == Enum2.Down)
					{
						graphics_.DrawRectangle(new Pen(this.color_1), rect);
						graphics_.FillRectangle(new SolidBrush(Color.FromArgb(118, 213, 170)), rect);
					}
					if (this.Boolean_0)
					{
						graphics_.DrawString("ü", new Font("Wingdings", 18f), new SolidBrush(this.color_1), new Rectangle(5, 7, this.int_1 - 9, this.int_1 - 9), Class16.stringFormat_1);
					}
					if (!base.Enabled)
					{
						graphics_.FillRectangle(new SolidBrush(Color.FromArgb(54, 58, 61)), rect);
						graphics_.DrawString(this.Text, this.Font, new SolidBrush(Color.FromArgb(48, 119, 91)), new Rectangle(20, 2, this.int_0, this.int_1), Class16.stringFormat_0);
					}
					graphics_.DrawString(this.Text, this.Font, new SolidBrush(this.color_2), new Rectangle(20, 2, this.int_0, this.int_1), Class16.stringFormat_0);
				}
			}
			else
			{
				graphics_.FillRectangle(new SolidBrush(this.color_0), rect);
				Enum2 enum3 = this.enum2_0;
				if (enum3 == Enum2.Over)
				{
					graphics_.DrawRectangle(new Pen(this.color_1), rect);
				}
				else if (enum3 == Enum2.Down)
				{
					graphics_.DrawRectangle(new Pen(this.color_1), rect);
				}
				if (this.Boolean_0)
				{
					graphics_.DrawString("ü", new Font("Wingdings", 18f), new SolidBrush(this.color_1), new Rectangle(5, 7, this.int_1 - 9, this.int_1 - 9), Class16.stringFormat_1);
				}
				if (!base.Enabled)
				{
					graphics_.FillRectangle(new SolidBrush(Color.FromArgb(54, 58, 61)), rect);
					graphics_.DrawString(this.Text, this.Font, new SolidBrush(Color.FromArgb(140, 142, 143)), new Rectangle(20, 2, this.int_0, this.int_1), Class16.stringFormat_0);
				}
				graphics_.DrawString(this.Text, this.Font, new SolidBrush(this.color_2), new Rectangle(20, 2, this.int_0, this.int_1), Class16.stringFormat_0);
			}
			base.OnPaint(e);
			Class16.graphics_0.Dispose();
			e.Graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
			e.Graphics.DrawImageUnscaled(Class16.bitmap_0, 0, 0);
			Class16.bitmap_0.Dispose();
		}
	}

	// Token: 0x04000204 RID: 516
	private int int_0;

	// Token: 0x04000205 RID: 517
	private int int_1;

	// Token: 0x04000206 RID: 518
	private Enum2 enum2_0;

	// Token: 0x04000207 RID: 519
	private Control31.Enum5 enum5_0;

	// Token: 0x04000208 RID: 520
	private bool bool_0;

	// Token: 0x04000209 RID: 521
	[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	[CompilerGenerated]
	private Control31.Delegate12 delegate12_0;

	// Token: 0x0400020A RID: 522
	private Color color_0;

	// Token: 0x0400020B RID: 523
	private Color color_1;

	// Token: 0x0400020C RID: 524
	private Color color_2;

	// Token: 0x02000048 RID: 72
	// (Invoke) Token: 0x060003DA RID: 986
	public delegate void Delegate12(object sender);

	// Token: 0x02000049 RID: 73
	[Flags]
	public enum Enum5
	{
		// Token: 0x0400020E RID: 526
		Style1 = 0,
		// Token: 0x0400020F RID: 527
		Style2 = 1
	}
}
