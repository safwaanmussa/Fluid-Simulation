package application;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

public class FluidSimulation {
	
	public static PixelWriter getPixelWriter(GraphicsContext gfx) {
		return gfx.getPixelWriter();
	}
	
	public static void addDensity(PixelWriter pw, int x, int y, Color c, int gridSizeX, int gridSizeY) {
		
		
		for (int i = -5; i <= 5; i++) {
			for (int j = -5; j <= 5; j++) {
				if (i == 0 && j == 0) {
					pw.setColor(x, y, Color.WHITE);
					continue;
				}

				int checkX = x + i;
				int checkY = y + j;

				if (checkX >= 0 && checkX < gridSizeX && checkY >= 0 && checkY < gridSizeX)
					pw.setColor(checkX, checkY, c);

			}
		}
	}

}
