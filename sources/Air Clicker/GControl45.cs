using System;
using System.ComponentModel;
using System.Diagnostics;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Runtime.CompilerServices;
using System.Threading;
using System.Windows.Forms;
using Microsoft.VisualBasic.CompilerServices;

// Token: 0x020000E1 RID: 225
[DefaultEvent("CheckedChanged")]
public class GControl45 : Control
{
	// Token: 0x17000298 RID: 664
	// (get) Token: 0x0600097B RID: 2427 RVA: 0x0002C7E4 File Offset: 0x0002A9E4
	// (set) Token: 0x0600097C RID: 2428 RVA: 0x0002C7FC File Offset: 0x0002A9FC
	public GControl45.GEnum18 GEnum18_0
	{
		get
		{
			return this.genum18_0;
		}
		set
		{
			this.genum18_0 = value;
			base.Invalidate();
		}
	}

	// Token: 0x0600097D RID: 2429 RVA: 0x0002C80C File Offset: 0x0002AA0C
	protected virtual void OnMouseEnter(EventArgs e)
	{
		base.OnMouseEnter(e);
		this.genum19_0 = GControl45.GEnum19.Over;
		base.Invalidate();
	}

	// Token: 0x0600097E RID: 2430 RVA: 0x0002C824 File Offset: 0x0002AA24
	protected virtual void OnMouseDown(MouseEventArgs e)
	{
		base.OnMouseDown(e);
		this.genum19_0 = GControl45.GEnum19.Down;
		base.Invalidate();
	}

	// Token: 0x0600097F RID: 2431 RVA: 0x0002C83C File Offset: 0x0002AA3C
	protected virtual void OnMouseLeave(EventArgs e)
	{
		base.OnMouseLeave(e);
		this.genum19_0 = GControl45.GEnum19.None;
		base.Invalidate();
	}

	// Token: 0x06000980 RID: 2432 RVA: 0x0002C854 File Offset: 0x0002AA54
	protected virtual void OnMouseUp(MouseEventArgs e)
	{
		base.OnMouseUp(e);
		this.genum19_0 = GControl45.GEnum19.Over;
		base.Invalidate();
	}

	// Token: 0x06000981 RID: 2433 RVA: 0x0002C86C File Offset: 0x0002AA6C
	protected virtual void OnTextChanged(EventArgs e)
	{
		base.OnTextChanged(e);
		base.Width = checked((int)Math.Round((double)(unchecked(base.CreateGraphics().MeasureString(this.Text, this.Font).Width + 6f + (float)base.Height))));
		base.Invalidate();
	}

	// Token: 0x17000299 RID: 665
	// (get) Token: 0x06000982 RID: 2434 RVA: 0x0002C8C0 File Offset: 0x0002AAC0
	// (set) Token: 0x06000983 RID: 2435 RVA: 0x0002C8C8 File Offset: 0x0002AAC8
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

	// Token: 0x06000984 RID: 2436 RVA: 0x0002C8D8 File Offset: 0x0002AAD8
	protected virtual void OnResize(EventArgs e)
	{
		base.OnResize(e);
		base.Height = 17;
	}

	// Token: 0x06000985 RID: 2437 RVA: 0x0002C8EC File Offset: 0x0002AAEC
	protected virtual void OnClick(EventArgs e)
	{
		this.bool_0 = !this.bool_0;
		GControl45.GDelegate24 gdelegate = this.gdelegate24_0;
		if (gdelegate != null)
		{
			gdelegate(this);
		}
		base.OnClick(e);
	}

	// Token: 0x14000029 RID: 41
	// (add) Token: 0x06000986 RID: 2438 RVA: 0x0002C920 File Offset: 0x0002AB20
	// (remove) Token: 0x06000987 RID: 2439 RVA: 0x0002C95C File Offset: 0x0002AB5C
	public event GControl45.GDelegate24 Event_0
	{
		[CompilerGenerated]
		add
		{
			GControl45.GDelegate24 gdelegate = this.gdelegate24_0;
			GControl45.GDelegate24 gdelegate2;
			do
			{
				gdelegate2 = gdelegate;
				GControl45.GDelegate24 value2 = (GControl45.GDelegate24)Delegate.Combine(gdelegate2, value);
				gdelegate = Interlocked.CompareExchange<GControl45.GDelegate24>(ref this.gdelegate24_0, value2, gdelegate2);
			}
			while (gdelegate != gdelegate2);
		}
		[CompilerGenerated]
		remove
		{
			GControl45.GDelegate24 gdelegate = this.gdelegate24_0;
			GControl45.GDelegate24 gdelegate2;
			do
			{
				gdelegate2 = gdelegate;
				GControl45.GDelegate24 value2 = (GControl45.GDelegate24)Delegate.Remove(gdelegate2, value);
				gdelegate = Interlocked.CompareExchange<GControl45.GDelegate24>(ref this.gdelegate24_0, value2, gdelegate2);
			}
			while (gdelegate != gdelegate2);
		}
	}

	// Token: 0x06000988 RID: 2440 RVA: 0x0002C998 File Offset: 0x0002AB98
	public GControl45()
	{
		this.genum19_0 = GControl45.GEnum19.None;
		base.SetStyle(ControlStyles.UserPaint, true);
		base.SetStyle(ControlStyles.AllPaintingInWmPaint, true);
		base.SetStyle(ControlStyles.OptimizedDoubleBuffer, true);
		base.SetStyle(ControlStyles.SupportsTransparentBackColor, true);
		this.GEnum18_0 = GControl45.GEnum18.Dark;
		this.BackColor = Color.FromArgb(50, 50, 50);
		this.ForeColor = Color.White;
		base.Size = new Size(147, 17);
		this.DoubleBuffered = true;
	}

	// Token: 0x06000989 RID: 2441 RVA: 0x0002CA1C File Offset: 0x0002AC1C
	protected virtual void OnPaint(PaintEventArgs e)
	{
		Bitmap bitmap = new Bitmap(base.Width, base.Height);
		Graphics graphics = Graphics.FromImage(bitmap);
		graphics.SmoothingMode = SmoothingMode.HighQuality;
		checked
		{
			Rectangle rect = new Rectangle(0, 0, base.Height - 1, base.Height - 1);
			graphics.Clear(this.BackColor);
			GControl45.GEnum18 genum = this.GEnum18_0;
			if (genum != GControl45.GEnum18.Light)
			{
				if (genum == GControl45.GEnum18.Dark)
				{
					graphics.FillRectangle(new SolidBrush(Color.FromArgb(215, Color.White)), rect);
				}
			}
			else
			{
				graphics.FillRectangle(new SolidBrush(Color.FromArgb(215, Color.Black)), rect);
			}
			if (this.Boolean_0)
			{
				Rectangle rectangle = new Rectangle((int)Math.Round(unchecked((double)rect.X + (double)rect.Width / 4.0)), (int)Math.Round(unchecked((double)rect.Y + (double)rect.Height / 4.0)), rect.Width / 2, rect.Height / 2);
				Point[] array = new Point[]
				{
					new Point(rectangle.X, rectangle.Y + rectangle.Height / 2),
					new Point(rectangle.X + rectangle.Width / 2, rectangle.Y + rectangle.Height),
					new Point(rectangle.X + rectangle.Width, rectangle.Y)
				};
				GControl45.GEnum18 genum2 = this.GEnum18_0;
				if (genum2 == GControl45.GEnum18.Light)
				{
					int num = array.Length - 2;
					for (int i = 0; i <= num; i++)
					{
						graphics.DrawLine(new Pen(Color.White, 2f), array[i], array[i + 1]);
					}
				}
				else if (genum2 == GControl45.GEnum18.Dark)
				{
					int num2 = array.Length - 2;
					for (int j = 0; j <= num2; j++)
					{
						graphics.DrawLine(new Pen(Color.Black, 2f), array[j], array[j + 1]);
					}
				}
			}
			graphics.DrawString(this.Text, this.Font, new SolidBrush(this.ForeColor), new Point(18, 2), new StringFormat
			{
				Alignment = StringAlignment.Near,
				LineAlignment = StringAlignment.Near
			});
			NewLateBinding.LateCall(e.Graphics, null, "DrawImage", new object[]
			{
				bitmap.Clone(),
				0,
				0
			}, null, null, null, true);
			graphics.Dispose();
			bitmap.Dispose();
		}
	}

	// Token: 0x04000499 RID: 1177
	private GControl45.GEnum18 genum18_0;

	// Token: 0x0400049A RID: 1178
	private GControl45.GEnum19 genum19_0;

	// Token: 0x0400049B RID: 1179
	private bool bool_0;

	// Token: 0x0400049C RID: 1180
	[CompilerGenerated]
	[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	private GControl45.GDelegate24 gdelegate24_0;

	// Token: 0x020000E2 RID: 226
	public enum GEnum18
	{
		// Token: 0x0400049E RID: 1182
		Light,
		// Token: 0x0400049F RID: 1183
		Dark
	}

	// Token: 0x020000E3 RID: 227
	public enum GEnum19
	{
		// Token: 0x040004A1 RID: 1185
		None,
		// Token: 0x040004A2 RID: 1186
		Over,
		// Token: 0x040004A3 RID: 1187
		Down
	}

	// Token: 0x020000E4 RID: 228
	// (Invoke) Token: 0x0600098D RID: 2445
	public delegate void GDelegate24(object sender);
}
