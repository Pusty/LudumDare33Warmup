package me.engine.render;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.imageio.ImageIO;

public class SheetLoader {
	BufferedImage bigImg;
	// The above line throws an checked IOException which must be caught.

	int width;
	int height;
	int rows;
	int cols;
	BufferedImage[] sprites;

	public SheetLoader(String url, int rows2, int cols2, int width2, int height2)
			throws IOException {
		bigImg = ImageIO.read(new File(url));
		initSize(rows2, cols2, width2, height2);
		initSheet();
	}

	public SheetLoader(URL url, int rows2, int cols2, int width2, int height2)
			throws IOException {
		bigImg = ImageIO.read(url);
		initSize(rows2, cols2, width2, height2);
		initSheet();
	}

	public SheetLoader(InputStream is, int rows2, int cols2, int width2, int height2)
			throws IOException {
		bigImg = ImageIO.read(is);
		initSize(rows2, cols2, width2, height2);
		initSheet();
	}

	public void initSize(int r, int c, int w, int h) {
		width = w;
		height = h;
		rows = r;
		cols = c;
		sprites = new BufferedImage[rows * cols];
	}

	public void initSheet() {

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {

				sprites[(i * cols) + j] = bigImg.getSubimage(j * width,
						i * height, width, height);
			}
		}
	}

	public BufferedImage getImage(int x, int y) {
		BufferedImage end = sprites[(y * rows) + x];
		return end;

	}

}
