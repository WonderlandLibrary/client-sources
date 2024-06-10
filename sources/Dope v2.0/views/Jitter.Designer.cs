
namespace Dope.views
{
    partial class Jitter
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
            this.JitterToggle = new Dope.Theme.Switch();
            this.JStrengh = new Dope.Theme.Slider();
            this.JBind = new Dope.Theme.Bind();
            this.SuspendLayout();
            // 
            // JitterToggle
            // 
            this.JitterToggle.Checked = false;
            this.JitterToggle.Font = new System.Drawing.Font("Century Gothic", 14.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.JitterToggle.Location = new System.Drawing.Point(12, 12);
            this.JitterToggle.Name = "JitterToggle";
            this.JitterToggle.Size = new System.Drawing.Size(158, 34);
            this.JitterToggle.TabIndex = 1;
            this.JitterToggle.Text = "Jitter";
            this.JitterToggle.Click += new System.EventHandler(this.JitterToggle_Click);
            // 
            // JStrengh
            // 
            this.JStrengh.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.JStrengh.Diviser = 10;
            this.JStrengh.Location = new System.Drawing.Point(12, 52);
            this.JStrengh.Maximum = 100;
            this.JStrengh.Minimum = 0;
            this.JStrengh.Name = "JStrengh";
            this.JStrengh.ShowValue = false;
            this.JStrengh.Size = new System.Drawing.Size(275, 45);
            this.JStrengh.TabIndex = 2;
            this.JStrengh.Text = "Strengh";
            this.JStrengh.Value = 100;
            this.JStrengh.Scroll += new Dope.Theme.Slider.ScrollEventHandler(this.JStrengh_Scroll);
            // 
            // JBind
            // 
            this.JBind.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.JBind.AutoSize = true;
            this.JBind.Key = 0;
            this.JBind.Location = new System.Drawing.Point(300, 19);
            this.JBind.Name = "JBind";
            this.JBind.Size = new System.Drawing.Size(55, 21);
            this.JBind.TabIndex = 7;
            this.JBind.Text = "[Bind]";
            this.JBind.TextChanged += new System.EventHandler(this.JBind_TextChanged);
            // 
            // Jitter
            // 
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
            this.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(20)))), ((int)(((byte)(20)))), ((int)(((byte)(20)))));
            this.ClientSize = new System.Drawing.Size(461, 411);
            this.Controls.Add(this.JBind);
            this.Controls.Add(this.JStrengh);
            this.Controls.Add(this.JitterToggle);
            this.Font = new System.Drawing.Font("Century Gothic", 12F);
            this.ForeColor = System.Drawing.Color.DarkGray;
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.None;
            this.Name = "Jitter";
            this.Text = "Jitter";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private Theme.Switch JitterToggle;
        private Theme.Slider JStrengh;
        private Theme.Bind JBind;
    }
}