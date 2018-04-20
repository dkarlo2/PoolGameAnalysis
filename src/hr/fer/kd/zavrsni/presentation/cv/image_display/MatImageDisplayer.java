package hr.fer.kd.zavrsni.presentation.cv.image_display;

import hr.fer.kd.zavrsni.presentation.cv.painter.Graphics2DPainter;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Point;
import org.opencv.highgui.Highgui;

public class MatImageDisplayer extends JComponent {

	private static final long serialVersionUID = 3645337325383426333L;

	private static final Dimension MINIMUM_SIZE = new Dimension(100, 100);

	private Graphics2DPainter painter;

	private double scale;

	private Mat image;
	private BufferedImage bufImage;

	public MatImageDisplayer() {
		this(null);
	}

	public MatImageDisplayer(Mat image) {
		this.image = image;

		addComponentListener(new ComponentAdapter() {

			@Override
			public void componentResized(ComponentEvent e) {
				display();
			}

			@Override
			public void componentShown(ComponentEvent e) {
				display();
			}

		});
	}

	public void setPainter(Graphics2DPainter p) {
		painter = p;
	}

	public double getScale() {
		return scale;
	}

	public Point getPointOnImage(java.awt.Point p) {
		Point point = new Point();
		point.x = p.getX() / scale;
		point.y = p.getY() / scale;
		return point;
	}

	public void display(Mat image) {
		this.image = image;
	}

	private void display() {
		if (image == null) {
			return;
		}

		MatOfByte matOfByte = new MatOfByte();

		Highgui.imencode(".png", image, matOfByte);

		byte[] byteArray = matOfByte.toArray();

		try {
			InputStream in = new ByteArrayInputStream(byteArray);
			bufImage = ImageIO.read(in);
		} catch (IOException e) {
			throw new RuntimeException("Can't display the image!");
		}

		scaleImage();
		repaint();
	}

	private void scaleImage() {
		int width = getWidth();
		int height = getHeight();

		if (width == 0 || height == 0) {
			return;
		}

		int w = image.cols();
		int h = image.rows();

		scale = Math.min((double)width/w, (double)height/h);
		w*=scale;
		h*=scale;
		bufImage = toBufferedImage(bufImage.getScaledInstance(w, h, Image.SCALE_SMOOTH));
	}

	private static BufferedImage toBufferedImage(Image img) {
		if (img instanceof BufferedImage) {
			return (BufferedImage) img;
		}

		BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

		Graphics2D bGr = bimage.createGraphics();
		bGr.drawImage(img, 0, 0, null);
		bGr.dispose();

		return bimage;
	}

	@Override
	public Dimension getMinimumSize() {
		return MINIMUM_SIZE;
	}

	@Override
	protected void paintComponent(Graphics g) {
		if (bufImage == null) {
			return;
		}

		Insets insets = getInsets();
		g.drawImage(bufImage, insets.left, insets.top, null);

		if (painter != null) {
			painter.paint((Graphics2D)g);
		}
	}


}
