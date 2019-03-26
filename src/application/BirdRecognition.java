package application;

import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.awt.Color;
import java.awt.Graphics;

public class BirdRecognition {
	private static int BLACK = Color.BLACK.getRGB();
	
	private BufferedImage bwImage;
	
	public BirdRecognition(BufferedImage bwImage) {
		this.bwImage = bwImage;
	}
	
	public BufferedImage outlineBirdsInImage(BufferedImage image) {
		UnionFind uf = new UnionFind(image.getWidth() * image.getHeight());
		Graphics imageGraphics = image.getGraphics();
		for(int y = 0; y < image.getHeight(); y++) {
			for(int x = 0; x < image.getWidth(); x++) {
				int currentPixel = getPixelId(x, y);
				
				if (bwImage.getRGB(x,y) == Color.WHITE.getRGB()) {
					continue;
				}
				
				// Check left.
				if(x > 0 && bwImage.getRGB(x - 1, y) == BLACK) {
					uf.join(currentPixel, getPixelId(x - 1, y));
				}
				if(x < bwImage.getWidth() - 1 && bwImage.getRGB(x + 1, y) == BLACK) {
					uf.join(currentPixel, getPixelId(x + 1, y));
				}
				if(y > 0 && bwImage.getRGB(x, y - 1) == BLACK) {
					uf.join(currentPixel, getPixelId(x, y - 1));
				}
				if(y < bwImage.getHeight() - 1 && bwImage.getRGB(x, y + 1) == BLACK) {
					uf.join(currentPixel, getPixelId(x, y + 1));
				}

				if(x > 0 && y > 0 && bwImage.getRGB(x - 1, y - 1) == BLACK) {
					uf.join(currentPixel, getPixelId(x - 1, y - 1));
				}
				if(x < bwImage.getWidth() - 1 && y < bwImage.getHeight() - 1 && bwImage.getRGB(x + 1, y + 1) == BLACK) {
					uf.join(currentPixel, getPixelId(x + 1, y + 1));
				}
				if(x > 0 && y < bwImage.getHeight() - 1  && bwImage.getRGB(x - 1, y + 1) == BLACK) {
					uf.join(currentPixel, getPixelId(x - 1, y + 1));
				}
				if(x < bwImage.getWidth() - 1 && y > 0 && bwImage.getRGB(x + 1, y - 1) == BLACK) {
					uf.join(currentPixel, getPixelId(x + 1, y - 1));
				}
			}
		}
		
		Set<Integer> roots = uf.getRoots(1);
		Iterator<Integer> iter = roots.iterator();
		while(iter.hasNext()) {
			int root = iter.next();
			List<Integer> nodes = uf.getConnectedNodes(root);
			
			int leftmost = -1;
			int rightmost = -1;
			int topmost = -1;
			int bottommost = -1;
			
			for(int i = 0; i < nodes.size(); i++) {
				int nodeX = nodes.get(i) % bwImage.getWidth();
				int nodeY = nodes.get(i) / bwImage.getWidth();
				
				if(leftmost == -1 || nodeX < leftmost) {
					leftmost = nodeX;
				}
				if(rightmost == -1 || nodeX > rightmost) {
					rightmost = nodeX;
				}
				if(topmost == -1 || nodeY < topmost) {
					topmost = nodeY;
				}
				if(bottommost == -1 || nodeY > bottommost) {
					bottommost = nodeY;
				}
			}
			
			int width = rightmost - leftmost;
			int height = bottommost - topmost;
			imageGraphics.setColor(Color.RED);
			imageGraphics.drawRect(leftmost, topmost, width, height);
		}
		imageGraphics.dispose();
		return image;
	}
	
	private int getPixelId(int x, int y) {
		return (bwImage.getWidth() * y) + x;
	}
}
