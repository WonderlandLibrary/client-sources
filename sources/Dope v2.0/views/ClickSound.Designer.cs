
namespace Dope.views
{
    partial class ClickSound
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.combo1 = new Dope.Theme.Combo();
            this.slider1 = new Dope.Theme.Slider();
            this.bind1 = new Dope.Theme.Bind();
            this.switch1 = new Dope.Theme.Switch();
            this.SuspendLayout();
            // 
            // combo1
            // 
            this.combo1.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(20)))), ((int)(((byte)(20)))), ((int)(((byte)(20)))));
            this.combo1.DrawMode = System.Windows.Forms.DrawMode.OwnerDrawFixed;
            this.combo1.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.combo1.ForeColor = System.Drawing.Color.Gainsboro;
            this.combo1.FormattingEnabled = true;
            this.combo1.Items.AddRange(new object[] {
            "Default",
            "Regular",
            "G502",
            "GPro",
            "G303",
            "HP"});
            this.combo1.Location = new System.Drawing.Point(22, 103);
            this.combo1.Name = "combo1";
            this.combo1.Size = new System.Drawing.Size(199, 28);
            this.combo1.StartIndex = 0;
            this.combo1.TabIndex = 11;
            this.combo1.SelectedIndexChanged += new System.EventHandler(this.combo1_SelectedIndexChanged);
            // 
            // slider1
            // 
            this.slider1.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.slider1.Diviser = 10;
            this.slider1.Location = new System.Drawing.Point(12, 52);
            this.slider1.Maximum = 100;
            this.slider1.Minimum = 0;
            this.slider1.Name = "slider1";
            this.slider1.ShowValue = false;
            this.slider1.Size = new System.Drawing.Size(275, 45);
            this.slider1.TabIndex = 10;
            this.slider1.Text = "Volume";
            this.slider1.Value = 100;
            this.slider1.Scroll += new Dope.Theme.Slider.ScrollEventHandler(this.slider1_Scroll);
            // 
            // bind1
            // 
            this.bind1.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.bind1.AutoSize = true;
            this.bind1.Key = 0;
            this.bind1.Location = new System.Drawing.Point(300, 19);
            this.bind1.Name = "bind1";
            this.bind1.Size = new System.Drawing.Size(55, 21);
            this.bind1.TabIndex = 9;
            this.bind1.Text = "[Bind]";
            this.bind1.TextChanged += new System.EventHandler(this.bind1_TextChanged);
            // 
            // switch1
            // 
            this.switch1.Checked = false;
            this.switch1.Font = new System.Drawing.Font("Century Gothic", 14.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.switch1.Location = new System.Drawing.Point(12, 12);
            this.switch1.Name = "switch1";
            this.switch1.Size = new System.Drawing.Size(193, 34);
            this.switch1.TabIndex = 8;
            this.switch1.Text = "Click Sound";
            this.switch1.Click += new System.EventHandler(this.switch1_Click);
            // 
            // ClickSound
            // 
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
            this.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(20)))), ((int)(((byte)(20)))), ((int)(((byte)(20)))));
            this.ClientSize = new System.Drawing.Size(461, 411);
            this.Controls.Add(this.combo1);
            this.Controls.Add(this.slider1);
            this.Controls.Add(this.bind1);
            this.Controls.Add(this.switch1);
            this.Font = new System.Drawing.Font("Century Gothic", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.ForeColor = System.Drawing.Color.Gainsboro;
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.None;
            this.Name = "ClickSound";
            this.Text = "ClickSound";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private Theme.Bind bind1;
        private Theme.Switch switch1;
        private Theme.Slider slider1;
        private Theme.Combo combo1;
    }
}