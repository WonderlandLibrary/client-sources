using System;
using System.Drawing;
using System.Drawing.Text;
using System.Windows.Forms;

// Token: 0x02000018 RID: 24
internal class Control5 : Control
{
	// Token: 0x06000228 RID: 552 RVA: 0x0000937C File Offset: 0x0000757C
	public Control5()
	{
		this.string_0 = "NET";
		this.string_1 = "SEAL";
		base.SetStyle(ControlStyles.UserPaint | ControlStyles.Opaque | ControlStyles.ResizeRedraw | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
		base.SetStyle(ControlStyles.Selectable, false);
		this.Font = new Font("Segoe UI", 11.25f, FontStyle.Bold);
		this.solidBrush_0 = new SolidBrush(Color.FromArgb(51, 181, 229));
	}

	// Token: 0x170000D1 RID: 209
	// (get) Token: 0x06000229 RID: 553 RVA: 0x000093F0 File Offset: 0x000075F0
	// (set) Token: 0x0600022A RID: 554 RVA: 0x00009408 File Offset: 0x00007608
	public string String_0
	{
		get
		{
			return this.string_0;
		}
		set
		{
			this.string_0 = value;
			base.Invalidate();
		}
	}

	// Token: 0x170000D2 RID: 210
	// (get) Token: 0x0600022B RID: 555 RVA: 0x00009418 File Offset: 0x00007618
	// (set) Token: 0x0600022C RID: 556 RVA: 0x00009430 File Offset: 0x00007630
	public string String_1
	{
		get
		{
			return this.string_1;
		}
		set
		{
			this.string_1 = value;
			base.Invalidate();
		}
	}

	// Token: 0x0600022D RID: 557 RVA: 0x00009440 File Offset: 0x00007640
	protected virtual void OnPaint(PaintEventArgs e)
	{
		Class9.graphics_0 = e.Graphics;
		Class9.graphics_0.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
		Class9.graphics_0.Clear(this.BackColor);
		this.sizeF_0 = Class9.graphics_0.MeasureString(this.String_0, this.Font, base.Width, StringFormat.GenericTypographic);
		this.sizeF_1 = Class9.graphics_0.MeasureString(this.String_1, this.Font, base.Width, StringFormat.GenericTypographic);
		this.pointF_0 = new PointF(0f, (float)(base.Height / 2) - this.sizeF_0.Height / 2f);
		this.pointF_1 = new PointF(this.sizeF_0.Width + 1f, (float)(base.Height / 2) - this.sizeF_0.Height / 2f);
		Class9.graphics_0.DrawString(this.String_0, this.Font, Brushes.Black, this.pointF_0.X + 1f, this.pointF_0.Y + 1f);
		Class9.graphics_0.DrawString(this.String_0, this.Font, Brushes.WhiteSmoke, this.pointF_0);
		Class9.graphics_0.DrawString(this.String_1, this.Font, Brushes.Black, this.pointF_1.X + 1f, this.pointF_1.Y + 1f);
		Class9.graphics_0.DrawString(this.String_1, this.Font, this.solidBrush_0, this.pointF_1);
	}

	// Token: 0x040000A3 RID: 163
	private string string_0;

	// Token: 0x040000A4 RID: 164
	private string string_1;

	// Token: 0x040000A5 RID: 165
	private SolidBrush solidBrush_0;

	// Token: 0x040000A6 RID: 166
	private PointF pointF_0;

	// Token: 0x040000A7 RID: 167
	private PointF pointF_1;

	// Token: 0x040000A8 RID: 168
	private SizeF sizeF_0;

	// Token: 0x040000A9 RID: 169
	private SizeF sizeF_1;
}
