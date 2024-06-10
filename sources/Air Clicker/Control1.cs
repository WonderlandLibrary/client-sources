using System;
using System.Drawing;
using System.Windows.Forms;

// Token: 0x02000015 RID: 21
internal class Control1 : Control0
{
	// Token: 0x170000CD RID: 205
	// (get) Token: 0x06000216 RID: 534 RVA: 0x00008A90 File Offset: 0x00006C90
	// (set) Token: 0x06000217 RID: 535 RVA: 0x00008AA8 File Offset: 0x00006CA8
	public int Int32_3
	{
		get
		{
			return this.int_5;
		}
		set
		{
			this.int_5 = value;
			base.Invalidate();
		}
	}

	// Token: 0x06000218 RID: 536 RVA: 0x00008AB8 File Offset: 0x00006CB8
	public Control1()
	{
		this.int_5 = 42;
		base.Int32_2 = 30;
		this.System.Windows.Forms.Control.BackColor = Color.FromArgb(50, 50, 50);
		this.pen_0 = new Pen(Color.FromArgb(24, 24, 24));
		this.pen_1 = new Pen(Color.FromArgb(60, 60, 60));
		this.solidBrush_2 = new SolidBrush(Color.FromArgb(50, 50, 50));
	}

	// Token: 0x06000219 RID: 537 RVA: 0x00008B30 File Offset: 0x00006D30
	protected override void \u206B\u202A\u206E\u200D\u206E\u206A\u202E\u200B\u200F\u200D\u206A\u202E\u200C\u200E\u200E\u200B\u200D\u202E\u200D\u200C\u206E\u200B\u206C\u206C\u202B\u206B\u206B\u200E\u200E\u202C\u206E\u206A\u202E\u200D\u202D\u200B\u206F\u200E\u206F\u202E\u202E()
	{
	}

	// Token: 0x0600021A RID: 538 RVA: 0x00008B34 File Offset: 0x00006D34
	protected override void \u206C\u200F\u202E\u202D\u202A\u202B\u200C\u202E\u206F\u202E\u206B\u206B\u202A\u206C\u200D\u206F\u206C\u200C\u202B\u202B\u206C\u202B\u200F\u200E\u200D\u206C\u200D\u202A\u200E\u206C\u206D\u202E\u200D\u202A\u206C\u206B\u202A\u206F\u206A\u202B\u202E()
	{
		this.graphics_0.Clear(this.System.Windows.Forms.Control.BackColor);
		base.method_37(this.pen_1, 1);
		this.graphics_0.DrawLine(this.pen_0, 0, 26, base.Width, 26);
		this.graphics_0.DrawLine(this.pen_1, 0, 25, base.Width, 25);
		checked
		{
			this.int_6 = Math.Max(base.method_28().Width + 20, 80);
			this.rectangle_5 = new Rectangle(this.int_6, 17, base.Width - this.int_6 * 2 + this.int_5, 8);
			this.graphics_0.DrawRectangle(this.pen_1, this.rectangle_5);
			this.graphics_0.DrawRectangle(this.pen_0, this.rectangle_5.X + 1, this.rectangle_5.Y + 1, this.rectangle_5.Width - 2, this.rectangle_5.Height);
			this.graphics_0.DrawLine(this.pen_0, 0, 29, base.Width, 29);
			this.graphics_0.DrawLine(this.pen_1, 0, 30, base.Width, 30);
			base.method_43(Brushes.Black, HorizontalAlignment.Left, 8, 1);
			base.method_43(Brushes.WhiteSmoke, HorizontalAlignment.Left, 7, 0);
			this.graphics_0.FillRectangle(this.solidBrush_2, 0, 27, base.Width, 2);
			base.method_40(Pens.Black);
		}
	}

	// Token: 0x04000086 RID: 134
	private int int_5;

	// Token: 0x04000087 RID: 135
	private Rectangle rectangle_5;

	// Token: 0x04000088 RID: 136
	private Pen pen_0;

	// Token: 0x04000089 RID: 137
	private Pen pen_1;

	// Token: 0x0400008A RID: 138
	private SolidBrush solidBrush_2;

	// Token: 0x0400008B RID: 139
	private int int_6;
}
