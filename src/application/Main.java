package application;

import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

	Point2D gridWorldSize = new Point2D(840d, 840d);
	private int gridSizeX;
	private int gridSizeY;
	
	private boolean simulating = true;
	
	@Override
	public void start(Stage primaryStage) {

		gridSizeX = (int) gridWorldSize.getX();
		gridSizeY = (int) gridWorldSize.getY();

		System.out.println(gridSizeX + gridSizeY);
		
		FluidPlane fluidplane = new FluidPlane(gridSizeX, gridSizeY, 1, 1, 1);
		
		Group root = new Group();
		Canvas canvas = new Canvas(gridWorldSize.getX(), gridWorldSize.getY());
		GraphicsContext gfx = canvas.getGraphicsContext2D();
		root.getChildren().add(canvas);
		
		root.setOnDragDetected(
				event -> {
					if (event.getButton() == MouseButton.PRIMARY) {
					event.consume();
					canvas.startFullDrag();
					}
				});
		
		canvas.setOnMouseClicked(
				event -> {
					if (event.getButton() == MouseButton.PRIMARY) {
						event.consume();
						FluidSimulation.addDye(FluidSimulation.getPixelWriter(gfx), (int) event.getX(), (int) event.getY(), Color.BLACK, gridSizeX, gridSizeX);
					}
				});
		
		canvas.setOnMouseDragged(
				event -> {
					event.consume();
					FluidSimulation.addDye(FluidSimulation.getPixelWriter(gfx), (int) event.getX(), (int) event.getY(), Color.BLACK, gridSizeX, gridSizeX);
				}
				);

		Scene scene = new Scene(root, Color.DARKSLATEGRAY);

		primaryStage.setScene(scene);
		primaryStage.show();
		
		while (simulating) {
			fluidplane.step();
		}

	}

	/*private void addDensity(Particle node) {

		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				if (i == 0 && j == 0) {
					node.setStyle(getStyle(Color.WHITE));
					continue;
				}

				int checkX = node.gridX + i;
				int checkY = node.gridY + j;

				if (checkX >= 0 && checkX < gridSizeX && checkY >= 0 && checkY < gridSizeX)
					gridArray[checkX][checkY].setStyle(getStyle(Color.PINK));

			}
		}

	}*/

	/*private String getStyle(Color color) {

		String colorString = color.toString();
		colorString = colorString.replace("0x", "#");
		String style = "-fx-background-color: " + colorString + "; -fx-border-style: solid; -fx-border-width: 1;"
				+ " -fx-border-color: black; -fx-min-width: " + (nodeRadius * 2) + "; -fx-min-height:"
				+ (nodeRadius * 2) + "; -fx-max-width:" + (nodeRadius * 2) + ";" + " -fx-max-height: "
				+ (nodeRadius * 2) + ";";

		return style;
	}*/

	public static void main(String[] args) {

		launch(args);
	}
}
