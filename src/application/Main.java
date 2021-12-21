package application;

import javafx.animation.AnimationTimer;
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

	Point2D gridWorldSize = new Point2D(600d, 600d);
	private int gridSizeX;
	private int gridSizeY;

	@Override
	public void start(Stage primaryStage) {

		gridSizeX = (int) gridWorldSize.getX();
		gridSizeY = (int) gridWorldSize.getY();

		System.out.println(gridSizeX + gridSizeY);

		Group root = new Group();
		Canvas canvas = new Canvas(gridWorldSize.getX(), gridWorldSize.getY());
		GraphicsContext gfx = canvas.getGraphicsContext2D();
		root.getChildren().add(canvas);

		FluidPlane fluidplane = new FluidPlane(gridSizeX, gridSizeY, 0.1f, 0, 0, gfx);

		root.setOnDragDetected(event -> {
			if (event.getButton() == MouseButton.PRIMARY) {
				event.consume();
				canvas.startFullDrag();
			}
		});

		canvas.setOnMouseClicked(event -> {
			if (event.getButton() == MouseButton.PRIMARY) {
				event.consume();
				System.out.println("Doing smthn?");
				fluidplane.addDensity((int) event.getX(), (int) event.getY(), 10);
				fluidplane.addVelocity((int) event.getX(), (int) event.getY(), 100, 100);
				// FluidSimulation.addDye(FluidSimulation.getPixelWriter(gfx), (int)
				// event.getX(), (int) event.getY(), Color.BLACK, gridSizeX, gridSizeX);
			}
		});

		canvas.setOnMouseDragged(event -> {
			event.consume();
			// FluidSimulation.addDye(FluidSimulation.getPixelWriter(gfx), (int)
			// event.getX(), (int) event.getY(), Color.BLACK, gridSizeX, gridSizeX);
		});

		Scene scene = new Scene(root, Color.DARKSLATEGRAY);

		primaryStage.setScene(scene);
		primaryStage.show();

		AnimationTimer updateCanvas = new Draw(fluidplane);
		updateCanvas.start();

	}

	public static void main(String[] args) {

		launch(args);
	}
	
	private class Draw extends AnimationTimer {
		
		FluidPlane plane;
		
		public Draw(FluidPlane plane) {
			this.plane = plane;
		}

		@Override
		public void handle(long arg0) {
			
			plane.step();
			plane.renderD();
			
		}
		
	}
}
