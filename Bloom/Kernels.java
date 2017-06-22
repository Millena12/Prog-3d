package pucpr;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Kernels 
{			
		static BufferedImage add(BufferedImage img, BufferedImage img1) throws IOException
		{		
			BufferedImage out = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);		
			
			for(int y = 0; y < img.getHeight(); y++)
			{
				for(int x = 0; x < img.getWidth(); x++)
				{										
					Color pixel = new Color(img.getRGB(x,y));
					Color pixel2 = new Color(img1.getRGB(x,y));												
							
					int r = pixel2.getRed() + pixel.getRed();
					int g = pixel2.getGreen() + pixel.getGreen();
					int b = pixel2.getBlue() + pixel.getBlue();
							
					r = r < 0 ? r = 0 :(r > 255 ? r = 255: r);
					g = g < 0 ? g = 0 :(g > 255 ? g = 255: g);
					b = b < 0 ? b = 0 :(b > 255 ? b = 255: b);
							
					Color result = new Color(r, g, b);
					out.setRGB(x, y, result.getRGB()); 			
				}
			}
			return out;
		}
	
		public static void HSBBright(BufferedImage img, float bright) throws IOException
		{			
			BufferedImage img1 = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
		
			for (int y = 0; y < img.getHeight(); y++) 
			{
				for (int x = 0; x < img.getWidth(); x++) 
				{					
					Color pixel = new Color(img.getRGB(x, y));
					float[] hsb  = Color.RGBtoHSB(pixel.getRed(),pixel.getGreen(), pixel.getBlue(), null); 
					
					hsb[2] *= bright;
					hsb[2] = hsb[2] < 0 ? 0 : (hsb[2] > 1 ? 1 : hsb[2]);
					
					int color = Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]);
					img1.setRGB(x, y, color);
				}
			}
			
			ImageIO.write(img,"jpg", new File("C:/Users/lucas/Desktop/eqBright.png"));
		}
		
		public static void HSBSaturation(BufferedImage img, float saturation) throws IOException
		{		
			BufferedImage img1 = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
		
			for (int y = 0; y < img.getHeight(); y++) 
			{
				for (int x = 0; x < img.getWidth(); x++) 
				{					
					Color pixel = new Color(img.getRGB(x, y));
					float[] hsb  = Color.RGBtoHSB(pixel.getRed(),pixel.getGreen(),
							pixel.getBlue(), null); 
					
					hsb[1] *= saturation;
					hsb[1] = hsb[1] < 0 ? 0 : (hsb[1] > 1 ? 1 : hsb[1]);
					
					int color = Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]);
					img1.setRGB(x, y, color);
				}
			}
			
			ImageIO.write(img,"jpg", new File("C:/Users/lucas/Desktop/eqSat.png"));
		}
		
		public static void HSBHue(BufferedImage img, float hue) throws IOException
		{		
			BufferedImage img1 = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
		
			for (int y = 0; y < img.getHeight(); y++) 
			{
				for (int x = 0; x < img.getWidth(); x++) 
				{					
					Color pixel = new Color(img.getRGB(x, y));
					float[] hsb  = Color.RGBtoHSB(pixel.getRed(),pixel.getGreen(),
							pixel.getBlue(), null); 
					
					hsb[0] *= hue;
					hsb[0] = hsb[1] < 0 ? 0 : (hsb[1] > 1 ? 1 : hsb[1]);
					
					int color = Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]);
					img1.setRGB(x, y, color);
				}
			}
			
			ImageIO.write(img,"jpg", new File("C:/Users/lucas/Desktop/eqHue.png"));
		}
				
		public static BufferedImage Dilate(BufferedImage img, float[][]kernel) throws IOException
		{
			BufferedImage out = new BufferedImage(img.getWidth(),img.getHeight(), BufferedImage.TYPE_INT_RGB);
			
			float cor = 0;
			float[] HSB = new float[3];
			
			for (int y = 0; y < img.getHeight(); y++) 
			{
				for (int x = 0; x < img.getWidth(); x++)
				{														
					for(int i = 0; i < kernel.length; i++ )
					{					
						for(int j = 0; j < kernel.length; j++)
						{
							int px = x + (i - 1);
							int py = y + (j - 1);
							if(px >= 0 && py >= 0 && px < img.getWidth() && py <img.getHeight())
							{
								Color cor2 = new Color(img.getRGB(px, py));
								
								Color.RGBtoHSB(cor2.getRed(), cor2.getGreen(), cor2.getBlue(), HSB);
								
								if(( HSB[2]*kernel[i][j]) > cor)
									{
										cor = HSB[2];
									}								
							}						
						}					
					}					
					out.setRGB(x, y, Color.HSBtoRGB(HSB[0], HSB[1], cor));
					cor = 0; //0
				}
			}
			return out;
		}
		
		public static BufferedImage Erote(BufferedImage img, float[][]kernel) throws IOException
		{
			BufferedImage out = new BufferedImage(img.getWidth(),img.getHeight(), BufferedImage.TYPE_INT_RGB);
			
			float cor = 1;
			float[] HSB = new float[3];
			for (int y = 0; y < img.getHeight(); y++) 
			{
				for (int x = 0; x < img.getWidth(); x++)
				{	
					for(int i = 0; i < kernel.length; i++ )
					{					
						for(int j = 0; j< kernel.length; j++)
						{
							int px = x + (i - 1);
							int py = y + (j - 1);
							if(px >= 0 && py >= 0 && px < img.getWidth() && py <img.getHeight())
							{
								Color cor2 = new Color(img.getRGB(px, py));
								
								Color.RGBtoHSB(cor2.getRed(), cor2.getGreen(), cor2.getBlue(), HSB);
								
								if(kernel[i][j] != 0)
								{
									if( HSB[2] < cor)
									{									
										cor = HSB[2];
									}
								}							
							}					
						}					
					}					
					out.setRGB(x, y, Color.HSBtoRGB(HSB[0], HSB[1], cor));
					cor = 1;
				}
			}
			return out;
		}
		
		public static BufferedImage Open(BufferedImage img, float[][] kernel, int potency) throws IOException
		{
			BufferedImage out = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
			
			for (int i = 0; i < potency; i++)
			{
				out = Erote(img, kernel);
			}
			
			for (int i = 0; i < potency; i++)
			{
				out = Dilate(img, kernel);
			}
			
			return out;
		}
		
		public static BufferedImage Close(BufferedImage img, float[][] kernel, int potency) throws IOException
		{
			BufferedImage out = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
			
			for (int i = 0; i < potency; i++)
			{
				out = Dilate(img, kernel);
			}
			
			for (int i = 0; i < potency; i++)
			{
				out = Erote(img, kernel);
			}
			
			return out;
		}
		
		public static BufferedImage Convolve(BufferedImage img, float[][] kernel)
		{
			BufferedImage out = new BufferedImage(img.getWidth(),img.getHeight(), BufferedImage.TYPE_INT_RGB);		
			
			int r = 0, g = 0, b = 0;
			
			for (int y = 0; y < img.getHeight(); y++) 
			{
				for (int x = 0; x < img.getWidth(); x++)
				{	
					r=0;
					g=0;
					b=0;
					
					for(int i = 0; i < kernel.length; i++ )
					{					
						for(int j = 0; j < kernel.length; j++)
						{
							if(x + i >= 0 && y + j >= 0 && x + i < img.getWidth() && y + j <img.getHeight())
							{
								r += (int)(new Color(img.getRGB(x+i, y+j)).getRed()*kernel[i][j]);
								g += (int)(new Color(img.getRGB(x+i, y+j)).getGreen()*kernel[i][j]);
								b += (int)(new Color(img.getRGB(x+i, y+j)).getBlue()*kernel[i][j]);
								
							}
							else 
							{
								r += 0;
								g += 0;
								b += 0;
							}						
						}					
					}			
					r = r < 0 ? r = 0 :(r > 255 ? r = 255: r);
					g = g < 0 ? g = 0 :(g > 255 ? g = 255: g);
					b = b < 0 ? b = 0 :(b > 255 ? b = 255: b);
					out.setRGB(x, y, new Color(r,g,b).getRGB());
				}
			}			
			return out;
		}
		
		public static BufferedImage BloomEach(BufferedImage img, int kernelSize)
		{
			BufferedImage out = new BufferedImage(img.getWidth(),img.getHeight(), BufferedImage.TYPE_INT_RGB);	
			
			float cor = 0;
			
			float[] HSB = new float[3];
			
			for (int y = 0; y < img.getHeight(); y++) 
			{
				for (int x = 0; x < img.getWidth(); x++)
				{						
					for(int i = 0; i < kernelSize; i++ )
					{					
						for(int j = 0; j < kernelSize; j++)
						{
							int px = x + (i - 1);
							int py = y + (j - 1);
							if(px >= 0 && py >= 0 && px < img.getWidth() && py <img.getHeight())
							{
								Color cor2 = new Color(img.getRGB(px, py));
								
								Color.RGBtoHSB(cor2.getRed(), cor2.getGreen(), cor2.getBlue(), HSB);
								if( HSB[2] > 0.8f)
								{									
									cor = (float)HSB[2] * (float)(1/(float)kernelSize);
									out.setRGB(x, y, Color.HSBtoRGB(HSB[0], HSB[1], cor));
								}
							}
						}
					}						
				}
			}			
			return out;
		}
		
		public static BufferedImage Bloom(BufferedImage img) throws IOException
		{	
			BufferedImage bloom = BloomEach(img, 5);			
			img = add(img, bloom);
			bloom = BloomEach(img, 11);
			img = add(img, bloom);
			bloom = BloomEach(img, 21);
			img = add(img, bloom);
			bloom = BloomEach(img, 41);
			img = add(img, bloom);
			
			return img;
		}
		
		public static void main(String[] args ) throws IOException
		{
			BufferedImage img = ImageIO.read(new File("C:/Users/lucas/Desktop/mario.jpg"));

			float[][] matriz = {
					{-1.0f, 0.0f, 1.0f},
					 {-2.0f, 0.0f, 2.0f}, 
					 {-1.0f, 0.0f, 1.0f}
				};
			
			BufferedImage img2 = Dilate(img, matriz);
			ImageIO.write(img2,"jpg",new File ("C:/Users/lucas/Desktop/Dilate.jpg"));
			img2 = Erote(img, matriz);
			ImageIO.write(img2,"jpg",new File ("C:/Users/lucas/Desktop/Erote.jpg"));
			img2 = Open(img, matriz, 2);
			ImageIO.write(img2,"jpg",new File ("C:/Users/lucas/Desktop/Open.jpg"));
			img2 = Close(img, matriz, 2);
			ImageIO.write(img2,"jpg",new File ("C:/Users/lucas/Desktop/Close.jpg"));
			img2 = Convolve(img, matriz);
			ImageIO.write(img2,"jpg",new File ("C:/Users/lucas/Desktop/Convolve.jpg"));
			img = ImageIO.read(new File("C:/Users/lucas/Desktop/metroid2.jpg"));
			img2 = Bloom(img);
			ImageIO.write(img2,"jpg",new File ("C:/Users/lucas/Desktop/Bloom.jpg"));
		}
}
