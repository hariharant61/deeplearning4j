package org.deeplearning4j.util;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.jblas.DoubleMatrix;
/**
 * Image loader for taking images and converting them to matrices
 * @author Adam Gibson
 *
 */
public class ImageLoader {

	private int width = -1;
	private int height = -1;




	public ImageLoader() {
		super();
	}

	public ImageLoader(int width, int height) {
		super();
		this.width = width;
		this.height = height;
	}

	public DoubleMatrix asRowVector(File f) throws Exception {
		return MatrixUtil.toMatrix(flattenedImageFromFile(f));
	}

	public DoubleMatrix asMatrix(File f) throws IOException {
		return MatrixUtil.toMatrix(fromFile(f));
	}

	public int[] flattenedImageFromFile(File f) throws Exception {
		return ArrayUtil.flatten(fromFile(f));
	}

	public int[][] fromFile(File file) throws IOException  {
		BufferedImage image = ImageIO.read(file);
		if(height > 0 && width > 0)
			image =  toBufferedImage(image.getScaledInstance(height, width, Image.SCALE_SMOOTH));
		Raster raster = image.getData();
		int w = raster.getWidth(),h = raster.getHeight();
		int[][] ret = new int[w][h];
		for(int i = 0; i < w; i++)
			for(int j = 0; j < h; j++)
				ret[i][j] = raster.getSample(i, j, 0);

		return ret;
	}

	/**
	 * Converts a given Image into a BufferedImage
	 *
	 * @param img The Image to be converted
	 * @return The converted BufferedImage
	 */
	public static BufferedImage toBufferedImage(Image img)
	{
	    if (img instanceof BufferedImage)
	    {
	        return (BufferedImage) img;
	    }

	    // Create a buffered image with transparency
	    BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

	    // Draw the image on to the buffered image
	    Graphics2D bGr = bimage.createGraphics();
	    bGr.drawImage(img, 0, 0, null);
	    bGr.dispose();

	    // Return the buffered image
	    return bimage;
	}
	
}
