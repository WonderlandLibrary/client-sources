using System;
using System.Drawing;
using System.Windows.Forms;

// Token: 0x02000059 RID: 89
internal class Class20 : Label
{
	// Token: 0x060004A7 RID: 1191 RVA: 0x000165B8 File Offset: 0x000147B8
	protected virtual void OnTextChanged(EventArgs e)
	{
		base.OnTextChanged(e);
		base.Invalidate();
	}

	// Token: 0x060004A8 RID: 1192 RVA: 0x000165C8 File Offset: 0x000147C8
	public Class20()
	{
		base.SetStyle(ControlStyles.SupportsTransparentBackColor, true);
		this.Font = new Font("Segoe UI", 8f);
		this.ForeColor = Color.White;
		this.BackColor = Color.Transparent;
		this.Text = this.Text;
	}
}
