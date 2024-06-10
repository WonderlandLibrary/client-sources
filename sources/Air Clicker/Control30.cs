using System;
using System.Collections;
using System.ComponentModel;
using System.Diagnostics;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Runtime.CompilerServices;
using System.Threading;
using System.Windows.Forms;

// Token: 0x02000044 RID: 68
[DefaultEvent("CheckedChanged")]
internal class Control30 : Control
{
	// Token: 0x1700012A RID: 298
	// (get) Token: 0x060003B0 RID: 944 RVA: 0x00011F84 File Offset: 0x00010184
	// (set) Token: 0x060003B1 RID: 945 RVA: 0x00011F8C File Offset: 0x0001018C
	public bool Boolean_0
	{
		get
		{
			return this.bool_0;
		}
		set
		{
			this.bool_0 = value;
			this.method_0();
			Control30.Delegate11 @delegate = this.delegate11_0;
			if (@delegate != null)
			{
				@delegate(this);
			}
			base.Invalidate();
		}
	}

	// Token: 0x1400000A RID: 10
	// (add) Token: 0x060003B2 RID: 946 RVA: 0x00011FC0 File Offset: 0x000101C0
	// (remove) Token: 0x060003B3 RID: 947 RVA: 0x00011FFC File Offset: 0x000101FC
	public event Control30.Delegate11 Event_0
	{
		[CompilerGenerated]
		add
		{
			Control30.Delegate11 @delegate = this.delegate11_0;
			Control30.Delegate11 delegate2;
			do
			{
				delegate2 = @delegate;
				Control30.Delegate11 value2 = (Control30.Delegate11)Delegate.Combine(delegate2, value);
				@delegate = Interlocked.CompareExchange<Control30.Delegate11>(ref this.delegate11_0, value2, delegate2);
			}
			while (@delegate != delegate2);
		}
		[CompilerGenerated]
		remove
		{
			Control30.Delegate11 @delegate = this.delegate11_0;
			Control30.Delegate11 delegate2;
			do
			{
				delegate2 = @delegate;
				Control30.Delegate11 value2 = (Control30.Delegate11)Delegate.Remove(delegate2, value);
				@delegate = Interlocked.CompareExchange<Control30.Delegate11>(ref this.delegate11_0, value2, delegate2);
			}
			while (@delegate != delegate2);
		}
	}

	// Token: 0x060003B4 RID: 948 RVA: 0x00012034 File Offset: 0x00010234
	protected virtual void OnClick(EventArgs e)
	{
		if (!this.bool_0)
		{
			this.Boolean_0 = true;
		}
		base.OnClick(e);
	}

	// Token: 0x060003B5 RID: 949 RVA: 0x00012050 File Offset: 0x00010250
	private void method_0()
	{
		if (base.IsHandleCreated && this.bool_0)
		{
			try
			{
				foreach (object obj in base.Parent.Controls)
				{
					Control control = (Control)obj;
					if (control != this && control is Control30)
					{
						((Control30)control).Boolean_0 = false;
						base.Invalidate();
					}
				}
			}
			finally
			{
				IEnumerator enumerator;
				if (enumerator is IDisposable)
				{
					(enumerator as IDisposable).Dispose();
				}
			}
		}
	}

	// Token: 0x060003B6 RID: 950 RVA: 0x000120E8 File Offset: 0x000102E8
	protected virtual void OnCreateControl()
	{
		base.OnCreateControl();
		this.method_0();
	}

	// Token: 0x1700012B RID: 299
	// (get) Token: 0x060003B7 RID: 951 RVA: 0x000120F8 File Offset: 0x000102F8
	// (set) Token: 0x060003B8 RID: 952 RVA: 0x00012110 File Offset: 0x00010310
	[Category("Options")]
	public Control30.Enum4 Enum4_0
	{
		get
		{
			return this.enum4_0;
		}
		set
		{
			this.enum4_0 = value;
		}
	}

	// Token: 0x060003B9 RID: 953 RVA: 0x0001211C File Offset: 0x0001031C
	protected virtual void OnResize(EventArgs e)
	{
		base.OnResize(e);
		base.Height = 22;
	}

	// Token: 0x060003BA RID: 954 RVA: 0x00012130 File Offset: 0x00010330
	protected virtual void OnMouseDown(MouseEventArgs e)
	{
		base.OnMouseDown(e);
		this.enum2_0 = Enum2.Down;
		base.Invalidate();
	}

	// Token: 0x060003BB RID: 955 RVA: 0x00012148 File Offset: 0x00010348
	protected virtual void OnMouseUp(MouseEventArgs e)
	{
		base.OnMouseUp(e);
		this.enum2_0 = Enum2.Over;
		base.Invalidate();
	}

	// Token: 0x060003BC RID: 956 RVA: 0x00012160 File Offset: 0x00010360
	protected virtual void OnMouseEnter(EventArgs e)
	{
		base.OnMouseEnter(e);
		this.enum2_0 = Enum2.Over;
		base.Invalidate();
	}

	// Token: 0x060003BD RID: 957 RVA: 0x00012178 File Offset: 0x00010378
	protected virtual void OnMouseLeave(EventArgs e)
	{
		base.OnMouseLeave(e);
		this.enum2_0 = Enum2.None;
		base.Invalidate();
	}

	// Token: 0x060003BE RID: 958 RVA: 0x00012190 File Offset: 0x00010390
	public Control30()
	{
		this.enum2_0 = Enum2.None;
		this.color_0 = Color.FromArgb(45, 47, 49);
		this.color_1 = Class16.color_0;
		this.color_2 = Color.FromArgb(243, 243, 243);
		base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
		this.DoubleBuffered = true;
		this.Cursor = Cursors.Hand;
		base.Size = new Size(100, 22);
		this.BackColor = Color.FromArgb(60, 70, 73);
		this.Font = new Font("Segoe UI", 10f);
	}

	// Token: 0x060003BF RID: 959 RVA: 0x00012234 File Offset: 0x00010434
	protected virtual void OnPaint(PaintEventArgs e)
	{
		Class16.bitmap_0 = new Bitmap(base.Width, base.Height);
		Class16.graphics_0 = Graphics.FromImage(Class16.bitmap_0);
		checked
		{
			this.int_0 = base.Width - 1;
			this.int_1 = base.Height - 1;
			Rectangle rect = new Rectangle(0, 2, base.Height - 5, base.Height - 5);
			Rectangle rect2 = new Rectangle(4, 6, this.int_1 - 12, this.int_1 - 12);
			Graphics graphics_ = Class16.graphics_0;
			graphics_.SmoothingMode = SmoothingMode.HighQuality;
			graphics_.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
			graphics_.Clear(this.BackColor);
			Control30.Enum4 @enum = this.enum4_0;
			if (@enum == Control30.Enum4.Style1)
			{
				graphics_.FillEllipse(new SolidBrush(this.color_0), rect);
				Enum2 enum2 = this.enum2_0;
				if (enum2 == Enum2.Over)
				{
					graphics_.DrawEllipse(new Pen(this.color_1), rect);
				}
				else if (enum2 == Enum2.Down)
				{
					graphics_.DrawEllipse(new Pen(this.color_1), rect);
				}
				if (this.Boolean_0)
				{
					graphics_.FillEllipse(new SolidBrush(this.color_1), rect2);
				}
			}
			else if (@enum == Control30.Enum4.Style2)
			{
				graphics_.FillEllipse(new SolidBrush(this.color_0), rect);
				Enum2 enum3 = this.enum2_0;
				if (enum3 == Enum2.Over)
				{
					graphics_.DrawEllipse(new Pen(this.color_1), rect);
					graphics_.FillEllipse(new SolidBrush(Color.FromArgb(118, 213, 170)), rect);
				}
				else if (enum3 == Enum2.Down)
				{
					graphics_.DrawEllipse(new Pen(this.color_1), rect);
					graphics_.FillEllipse(new SolidBrush(Color.FromArgb(118, 213, 170)), rect);
				}
				if (this.Boolean_0)
				{
					graphics_.FillEllipse(new SolidBrush(this.color_1), rect2);
				}
			}
			graphics_.DrawString(this.Text, this.Font, new SolidBrush(this.color_2), new Rectangle(20, 2, this.int_0, this.int_1), Class16.stringFormat_0);
			base.OnPaint(e);
			Class16.graphics_0.Dispose();
			e.Graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
			e.Graphics.DrawImageUnscaled(Class16.bitmap_0, 0, 0);
			Class16.bitmap_0.Dispose();
		}
	}

	// Token: 0x040001F8 RID: 504
	private Enum2 enum2_0;

	// Token: 0x040001F9 RID: 505
	private int int_0;

	// Token: 0x040001FA RID: 506
	private int int_1;

	// Token: 0x040001FB RID: 507
	private Control30.Enum4 enum4_0;

	// Token: 0x040001FC RID: 508
	private bool bool_0;

	// Token: 0x040001FD RID: 509
	[CompilerGenerated]
	[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	private Control30.Delegate11 delegate11_0;

	// Token: 0x040001FE RID: 510
	private Color color_0;

	// Token: 0x040001FF RID: 511
	private Color color_1;

	// Token: 0x04000200 RID: 512
	private Color color_2;

	// Token: 0x02000045 RID: 69
	// (Invoke) Token: 0x060003C3 RID: 963
	public delegate void Delegate11(object sender);

	// Token: 0x02000046 RID: 70
	[Flags]
	public enum Enum4
	{
		// Token: 0x04000202 RID: 514
		Style1 = 0,
		// Token: 0x04000203 RID: 515
		Style2 = 1
	}
}
