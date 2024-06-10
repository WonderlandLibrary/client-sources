using System;
using System.Drawing;
using System.Windows.Forms;

// Token: 0x020000B8 RID: 184
internal class Class26 : LinkLabel
{
	// Token: 0x0600088F RID: 2191 RVA: 0x000288B8 File Offset: 0x00026AB8
	public Class26()
	{
		this.Font = new Font("Segoe UI", 9f, FontStyle.Regular);
		this.BackColor = Color.Transparent;
		base.LinkColor = Color.FromArgb(181, 41, 42);
		base.ActiveLinkColor = Color.FromArgb(153, 34, 34);
		base.VisitedLinkColor = Color.FromArgb(181, 41, 42);
		base.LinkBehavior = LinkBehavior.NeverUnderline;
	}
}
