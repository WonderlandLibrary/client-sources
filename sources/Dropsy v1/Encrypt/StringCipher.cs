using System;
using System.IO;
using System.Linq;
using System.Security.Cryptography;
using System.Text;

namespace Encrypt
{
	// Token: 0x0200001F RID: 31
	public static class StringCipher
	{
		// Token: 0x060000FA RID: 250 RVA: 0x00011550 File Offset: 0x0000F950
		public static string Encrypt(string plainText, string passPhrase)
		{
			byte[] array = StringCipher.Generate256BitsOfRandomEntropy();
			byte[] array2 = StringCipher.Generate256BitsOfRandomEntropy();
			byte[] bytes = Encoding.UTF8.GetBytes(plainText);
			string result;
			using (Rfc2898DeriveBytes rfc2898DeriveBytes = new Rfc2898DeriveBytes(passPhrase, array, 1000))
			{
				byte[] bytes2 = rfc2898DeriveBytes.GetBytes(32);
				using (RijndaelManaged rijndaelManaged = new RijndaelManaged())
				{
					rijndaelManaged.BlockSize = 256;
					rijndaelManaged.Mode = CipherMode.CBC;
					rijndaelManaged.Padding = PaddingMode.PKCS7;
					using (ICryptoTransform cryptoTransform = rijndaelManaged.CreateEncryptor(bytes2, array2))
					{
						using (MemoryStream memoryStream = new MemoryStream())
						{
							using (CryptoStream cryptoStream = new CryptoStream(memoryStream, cryptoTransform, CryptoStreamMode.Write))
							{
								cryptoStream.Write(bytes, 0, bytes.Length);
								cryptoStream.FlushFinalBlock();
								byte[] inArray = array.Concat(array2).ToArray<byte>().Concat(memoryStream.ToArray()).ToArray<byte>();
								memoryStream.Close();
								cryptoStream.Close();
								result = Convert.ToBase64String(inArray);
							}
						}
					}
				}
			}
			return result;
		}

		// Token: 0x060000FB RID: 251 RVA: 0x00011694 File Offset: 0x0000FA94
		public static string Decrypt(string cipherText, string passPhrase)
		{
			byte[] array = Convert.FromBase64String(cipherText);
			byte[] salt = array.Take(32).ToArray<byte>();
			byte[] rgbIV = array.Skip(32).Take(32).ToArray<byte>();
			byte[] array2 = array.Skip(64).Take(array.Length - 64).ToArray<byte>();
			string @string;
			using (Rfc2898DeriveBytes rfc2898DeriveBytes = new Rfc2898DeriveBytes(passPhrase, salt, 1000))
			{
				byte[] bytes = rfc2898DeriveBytes.GetBytes(32);
				using (RijndaelManaged rijndaelManaged = new RijndaelManaged())
				{
					rijndaelManaged.BlockSize = 256;
					rijndaelManaged.Mode = CipherMode.CBC;
					rijndaelManaged.Padding = PaddingMode.PKCS7;
					using (ICryptoTransform cryptoTransform = rijndaelManaged.CreateDecryptor(bytes, rgbIV))
					{
						using (MemoryStream memoryStream = new MemoryStream(array2))
						{
							using (CryptoStream cryptoStream = new CryptoStream(memoryStream, cryptoTransform, CryptoStreamMode.Read))
							{
								byte[] array3 = new byte[array2.Length];
								int count = cryptoStream.Read(array3, 0, array3.Length);
								memoryStream.Close();
								cryptoStream.Close();
								@string = Encoding.UTF8.GetString(array3, 0, count);
							}
						}
					}
				}
			}
			return @string;
		}

		// Token: 0x060000FC RID: 252 RVA: 0x000117FC File Offset: 0x0000FBFC
		private static byte[] Generate256BitsOfRandomEntropy()
		{
			byte[] array = new byte[32];
			using (RNGCryptoServiceProvider rngcryptoServiceProvider = new RNGCryptoServiceProvider())
			{
				rngcryptoServiceProvider.GetBytes(array);
			}
			return array;
		}

		// Token: 0x0400013C RID: 316
		private const int Keysize = 256;

		// Token: 0x0400013D RID: 317
		private const int DerivationIterations = 1000;
	}
}
