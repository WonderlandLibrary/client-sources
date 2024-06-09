using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Text;
using System.Windows.Forms;

namespace Xh0kO1ZCmA
{
	internal class FlatContextMenuStrip : System.Windows.Forms.ContextMenuStrip
	{
		public FlatContextMenuStrip()
		{
			base.Renderer = new ToolStripProfessionalRenderer(new FlatContextMenuStrip.TColorTable());
			base.ShowImageMargin = false;
			base.ForeColor = Color.White;
			this.Font = new System.Drawing.Font("Segoe UI", 8f);
		}

		protected override void OnPaint(PaintEventArgs e)
		{
			base.OnPaint(e);
			e.Graphics.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
		}

		protected override void OnTextChanged(EventArgs e)
		{
			base.OnTextChanged(e);
			base.Invalidate();
		}

		public class TColorTable : ProfessionalColorTable
		{
			private Color BackColor;

			private Color CheckedColor;

			private Color BorderColor;

			[Category("Colors")]
			public Color _BackColor
			{
				get
				{
					return this.BackColor;
				}
				set
				{
					this.BackColor = value;
				}
			}

			[Category("Colors")]
			public Color _BorderColor
			{
				get
				{
					return this.BorderColor;
				}
				set
				{
					this.BorderColor = value;
				}
			}

			[Category("Colors")]
			public Color _CheckedColor
			{
				get
				{
					return this.CheckedColor;
				}
				set
				{
					this.CheckedColor = value;
				}
			}

			public override Color ButtonSelectedBorder
			{
				get
				{
					return this.BackColor;
				}
			}

			public override Color CheckBackground
			{
				get
				{
					return this.CheckedColor;
				}
			}

			public override Color CheckPressedBackground
			{
				get
				{
					return this.CheckedColor;
				}
			}

			public override Color CheckSelectedBackground
			{
				get
				{
					return this.CheckedColor;
				}
			}

			public override Color ImageMarginGradientBegin
			{
				get
				{
					return this.CheckedColor;
				}
			}

			public override Color ImageMarginGradientEnd
			{
				get
				{
					return this.CheckedColor;
				}
			}

			public override Color ImageMarginGradientMiddle
			{
				get
				{
					return this.CheckedColor;
				}
			}

			public override Color MenuBorder
			{
				get
				{
					return this.BorderColor;
				}
			}

			public override Color MenuItemBorder
			{
				get
				{
					return this.BorderColor;
				}
			}

			public override Color MenuItemSelected
			{
				get
				{
					return this.CheckedColor;
				}
			}

			public override Color SeparatorDark
			{
				get
				{
					return this.BorderColor;
				}
			}

			public override Color ToolStripDropDownBackground
			{
				get
				{
					return this.BackColor;
				}
			}

			public TColorTable()
			{
				this.BackColor = Color.FromArgb(45, 47, 49);
				this.CheckedColor = Helpers._FlatColor;
				this.BorderColor = Color.FromArgb(53, 58, 60);
			}
		}
	}
}