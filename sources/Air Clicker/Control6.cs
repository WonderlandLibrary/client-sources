using System;
using System.Drawing;
using System.Drawing.Text;
using System.Windows.Forms;

// Token: 0x02000019 RID: 25
internal class Control6 : Control
{
	// Token: 0x0600022E RID: 558 RVA: 0x000095E0 File Offset: 0x000077E0
	public Control6()
	{
		this.string_0 = "Ќąȼh";
		this.string_1 = "ȼℓąƶƶ";
		base.SetStyle(ControlStyles.UserPaint | ControlStyles.Opaque | ControlStyles.ResizeRedraw | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
		base.SetStyle(ControlStyles.Selectable, false);
		this.Font = new Font("Segoe UI", 11.25f, FontStyle.Bold);
		this.solidBrush_0 = new SolidBrush(Color.FromArgb(51, 181, 229));
	}

	// Token: 0x170000D3 RID: 211
	// (get) Token: 0x0600022F RID: 559 RVA: 0x00009654 File Offset: 0x00007854
	// (set) Token: 0x06000230 RID: 560 RVA: 0x0000966C File Offset: 0x0000786C
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

	// Token: 0x170000D4 RID: 212
	// (get) Token: 0x06000231 RID: 561 RVA: 0x0000967C File Offset: 0x0000787C
	// (set) Token: 0x06000232 RID: 562 RVA: 0x00009694 File Offset: 0x00007894
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

	// Token: 0x06000233 RID: 563 RVA: 0x000096A4 File Offset: 0x000078A4
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

	// Token: 0x040000AA RID: 170
	private string string_0;

	// Token: 0x040000AB RID: 171
	private string string_1;

	// Token: 0x040000AC RID: 172
	private SolidBrush solidBrush_0;

	// Token: 0x040000AD RID: 173
	private PointF pointF_0;

	// Token: 0x040000AE RID: 174
	private PointF pointF_1;

	// Token: 0x040000AF RID: 175
	private SizeF sizeF_0;

	// Token: 0x040000B0 RID: 176
	private SizeF sizeF_1;
}
