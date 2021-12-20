package application;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

public class FluidSimulation {
	
	public static PixelWriter getPixelWriter(GraphicsContext gfx) {
		return gfx.getPixelWriter();
	}
	
	public static void addDye(PixelWriter pw, int x, int y, Color c, int gridSizeX, int gridSizeY) {
		
		
		for (int i = 0; i <= gridSizeX; i++) {
			for (int j = 0; j <= gridSizeY; j++) {
				
					pw.setColor(x, y, c);

			}
		}
	}
	
}
