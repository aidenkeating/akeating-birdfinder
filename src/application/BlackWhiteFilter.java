package application;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class BlackWhiteFilter {
	private int threshold;
	
	public BlackWhiteFilter(int threshold) {
		this.threshold = threshold;
	}
	
	public BufferedImage applyToImage(BufferedImage image) {
		BufferedImage bwImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
		for(int y = 0; y < image.getHeight(); y++) {
			for(int x = 0; x < image.getWidth(); x++) {
				int rgb = image.getRGB(x, y);
				int r = (0x00ff0000 & rgb) >> 16;
				int g = (0x0000ff00 & rgb) >> 8;
				int b = (0x000000ff & rgb);
				
				if (r > threshold || g > threshold || b > threshold) {
					bwImage.setRGB(x, y, Color.WHITE.getRGB());
				} else {
					bwImage.setRGB(x, y, Color.BLACK.getRGB());
				}
			}
		}
		return bwImage;
	}

}
