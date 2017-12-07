package Assignment4;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class PanelWithBackGround extends JPanel {

	private Image backGround;

	public PanelWithBackGround(URL file) throws IOException {
		this.backGround = ImageIO.read(file);

	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(backGround, 0, 0, this);
	}
}
