using System;
using System.ComponentModel;
using System.Drawing;
using System.Windows.Forms;

// Token: 0x02000096 RID: 150
public class GClass4 : Label
{
	// Token: 0x170001D9 RID: 473
	// (get) Token: 0x060006CB RID: 1739 RVA: 0x000207D8 File Offset: 0x0001E9D8
	// (set) Token: 0x060006CC RID: 1740 RVA: 0x000207F0 File Offset: 0x0001E9F0
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

	// Token: 0x060006CD RID: 1741 RVA: 0x000207FC File Offset: 0x0001E9FC
	protected virtual void OnTextChanged(EventArgs e)
	{
		base.OnTextChanged(e);
		base.Invalidate();
	}

	// Token: 0x060006CE RID: 1742 RVA: 0x0002080C File Offset: 0x0001EA0C
	public GClass4()
	{
		this.color_0 = Color.FromArgb(255, 255, 255);
		base.SetStyle(ControlStyles.SupportsTransparentBackColor, true);
		this.Font = new Font("Segoe UI", 9f);
		this.ForeColor = this.color_0;
		this.BackColor = Color.Transparent;
		this.Text = this.Text;
	}

	// Token: 0x04000342 RID: 834
	private Color color_0;
}
