using System;
using System.Collections;
using System.ComponentModel;
using System.Diagnostics;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Runtime.CompilerServices;
using System.Threading;
using System.Windows.Forms;

// Token: 0x020000BF RID: 191
[DefaultEvent("CheckedChanged")]
internal class Control47 : Control
{
	// Token: 0x17000275 RID: 629
	// (get) Token: 0x060008AC RID: 2220 RVA: 0x00029258 File Offset: 0x00027458
	// (set) Token: 0x060008AD RID: 2221 RVA: 0x00029260 File Offset: 0x00027460
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
			Control47.Delegate17 @delegate = this.delegate17_0;
			if (@delegate != null)
			{
				@delegate(this);
			}
			base.Invalidate();
		}
	}

	// Token: 0x14000020 RID: 32
	// (add) Token: 0x060008AE RID: 2222 RVA: 0x00029294 File Offset: 0x00027494
	// (remove) Token: 0x060008AF RID: 2223 RVA: 0x000292CC File Offset: 0x000274CC
	public event Control47.Delegate17 Event_0
	{
		[CompilerGenerated]
		add
		{
			Control47.Delegate17 @delegate = this.delegate17_0;
			Control47.Delegate17 delegate2;
			do
			{
				delegate2 = @delegate;
				Control47.Delegate17 value2 = (Control47.Delegate17)Delegate.Combine(delegate2, value);
				@delegate = Interlocked.CompareExchange<Control47.Delegate17>(ref this.delegate17_0, value2, delegate2);
			}
			while (@delegate != delegate2);
		}
		[CompilerGenerated]
		remove
		{
			Control47.Delegate17 @delegate = this.delegate17_0;
			Control47.Delegate17 delegate2;
			do
			{
				delegate2 = @delegate;
				Control47.Delegate17 value2 = (Control47.Delegate17)Delegate.Remove(delegate2, value);
				@delegate = Interlocked.CompareExchange<Control47.Delegate17>(ref this.delegate17_0, value2, delegate2);
			}
			while (@delegate != delegate2);
		}
	}

	// Token: 0x060008B0 RID: 2224 RVA: 0x00029308 File Offset: 0x00027508
	protected virtual void OnMouseDown(MouseEventArgs e)
	{
		if (!this.bool_0)
		{
			this.Boolean_0 = true;
		}
		base.Focus();
		base.OnMouseDown(e);
	}

	// Token: 0x060008B1 RID: 2225 RVA: 0x0002932C File Offset: 0x0002752C
	protected virtual void OnMouseMove(MouseEventArgs e)
	{
		base.OnMouseMove(e);
		this.int_0 = e.X;
		base.Invalidate();
	}

	// Token: 0x060008B2 RID: 2226 RVA: 0x00029348 File Offset: 0x00027548
	protected virtual void OnTextChanged(EventArgs e)
	{
		base.OnTextChanged(e);
		checked
		{
			int num = (int)Math.Round((double)base.CreateGraphics().MeasureString(this.Text, this.Font).Width);
			base.Width = 28 + num;
		}
	}

	// Token: 0x060008B3 RID: 2227 RVA: 0x00029390 File Offset: 0x00027590
	protected virtual void OnResize(EventArgs e)
	{
		base.OnResize(e);
		base.Height = 17;
	}

	// Token: 0x060008B4 RID: 2228 RVA: 0x000293A4 File Offset: 0x000275A4
	public Control47()
	{
		base.Width = 159;
		base.Height = 17;
		this.DoubleBuffered = true;
	}

	// Token: 0x060008B5 RID: 2229 RVA: 0x000293C8 File Offset: 0x000275C8
	private void method_0()
	{
		if (base.IsHandleCreated && this.bool_0)
		{
			try
			{
				foreach (object obj in base.Parent.Controls)
				{
					Control control = (Control)obj;
					if (control != this && control is Control47)
					{
						((Control47)control).Boolean_0 = false;
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

	// Token: 0x060008B6 RID: 2230 RVA: 0x00029458 File Offset: 0x00027658
	protected virtual void OnPaint(PaintEventArgs e)
	{
		base.OnPaint(e);
		Graphics graphics = e.Graphics;
		graphics.Clear(base.Parent.BackColor);
		graphics.SmoothingMode = SmoothingMode.HighQuality;
		graphics.FillEllipse(new SolidBrush(Color.FromArgb(66, 76, 85)), new Rectangle(0, 0, 16, 16));
		if (this.bool_0)
		{
			graphics.DrawString("a", new Font("Marlett", 15f), new SolidBrush(Color.FromArgb(181, 41, 42)), new Point(-3, -2));
		}
		graphics.DrawString(this.Text, this.Font, new SolidBrush(Color.FromArgb(116, 125, 132)), new Point(20, 0));
	}

	// Token: 0x04000429 RID: 1065
	private int int_0;

	// Token: 0x0400042A RID: 1066
	private bool bool_0;

	// Token: 0x0400042B RID: 1067
	[CompilerGenerated]
	[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	private Control47.Delegate17 delegate17_0;

	// Token: 0x020000C0 RID: 192
	// (Invoke) Token: 0x060008BA RID: 2234
	public delegate void Delegate17(object sender);
}
