using System;
using System.IO;

// Token: 0x0200004A RID: 74
public class 17AE5F89
{
	// Token: 0x06000266 RID: 614 RVA: 0x002FFDAC File Offset: 0x001801AC
	public void 1AE52179(uint 3F2F772C)
	{
		if (this.44AF5948 != 3F2F772C)
		{
			this.577C6B75 = new byte[3F2F772C];
		}
		this.44AF5948 = 3F2F772C;
		this.53012BEB = 0U;
		this.627F06C8 = 0U;
	}

	// Token: 0x06000267 RID: 615 RVA: 0x002FFDDC File Offset: 0x001801DC
	public void 4F787D3B(Stream 4E8C27D1, bool 258058EB)
	{
		this.15DF4A99();
		this.5B5155F8 = 4E8C27D1;
		if (!258058EB)
		{
			this.627F06C8 = 0U;
			this.53012BEB = 0U;
			this.70AF2E6E = 0U;
		}
	}

	// Token: 0x06000268 RID: 616 RVA: 0x002FFE08 File Offset: 0x00180208
	public void 15DF4A99()
	{
		this.432C1D1E();
		this.5B5155F8 = null;
	}

	// Token: 0x06000269 RID: 617 RVA: 0x002FFE18 File Offset: 0x00180218
	public void 432C1D1E()
	{
		uint num = this.53012BEB - this.627F06C8;
		if (num == 0U)
		{
			return;
		}
		this.5B5155F8.Write(this.577C6B75, (int)this.627F06C8, (int)num);
		if (this.53012BEB >= this.44AF5948)
		{
			this.53012BEB = 0U;
		}
		this.627F06C8 = this.53012BEB;
	}

	// Token: 0x0600026A RID: 618 RVA: 0x002FFE78 File Offset: 0x00180278
	public void 6C742E61(uint 1E733EF0, uint 18EC6C8C)
	{
		uint num = this.53012BEB - 1E733EF0 - 1U;
		if (num >= this.44AF5948)
		{
			num += this.44AF5948;
		}
		while (18EC6C8C > 0U)
		{
			if (num >= this.44AF5948)
			{
				num = 0U;
			}
			byte[] array = this.577C6B75;
			uint num2 = this.53012BEB;
			this.53012BEB = num2 + 1U;
			array[(int)num2] = this.577C6B75[(int)num++];
			if (this.53012BEB >= this.44AF5948)
			{
				this.432C1D1E();
			}
			18EC6C8C -= 1U;
		}
	}

	// Token: 0x0600026B RID: 619 RVA: 0x002FFF00 File Offset: 0x00180300
	public void 427E7B6A(byte 5123386C)
	{
		byte[] array = this.577C6B75;
		uint num = this.53012BEB;
		this.53012BEB = num + 1U;
		array[(int)num] = 5123386C;
		if (this.53012BEB >= this.44AF5948)
		{
			this.432C1D1E();
		}
	}

	// Token: 0x0600026C RID: 620 RVA: 0x002FFF40 File Offset: 0x00180340
	public byte 280B366B(uint 69167273)
	{
		uint num = this.53012BEB - 69167273 - 1U;
		if (num >= this.44AF5948)
		{
			num += this.44AF5948;
		}
		return this.577C6B75[(int)num];
	}

	// Token: 0x04000191 RID: 401
	private byte[] 577C6B75;

	// Token: 0x04000192 RID: 402
	private uint 53012BEB;

	// Token: 0x04000193 RID: 403
	private uint 44AF5948;

	// Token: 0x04000194 RID: 404
	private uint 627F06C8;

	// Token: 0x04000195 RID: 405
	private Stream 5B5155F8;

	// Token: 0x04000196 RID: 406
	private uint 07EC2C4B = 1U;

	// Token: 0x04000197 RID: 407
	public uint 70AF2E6E;
}
