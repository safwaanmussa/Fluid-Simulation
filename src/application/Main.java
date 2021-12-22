package application;

import java.util.Random;

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

	Point2D gridWorldSize = new Point2D(400d, 400d);
	private int gridSizeX;
	private int gridSizeY;
	
	private int SCALE = 10;

	@Override
	public void start(Stage primaryStage) {

		gridSizeX = (int) gridWorldSize.getX();
		gridSizeY = (int) gridWorldSize.getY();

		System.out.println(gridSizeX + gridSizeY);

		Group root = new Group();
		Canvas canvas = new Canvas(gridWorldSize.getX(), gridWorldSize.getY());
		GraphicsContext gfx = canvas.getGraphicsContext2D();
		root.getChildren().add(canvas);

		FluidPlane fluidplane = new FluidPlane(gridSizeX, gridSizeY, 0.2f, 0.0001f, 1f, gfx, SCALE);

		root.setOnDragDetected(event -> {
			if (event.getButton() == MouseButton.PRIMARY) {
				event.consume();
				canvas.startFullDrag();
			}
		});

		canvas.setOnMouseClicked(event -> {
			if (event.getButton() == MouseButton.PRIMARY) {
				event.consume();
				fluidplane.addDensity((int) event.getX(), (int) event.getY(), randInRange(0, 100));
				fluidplane.addVelocity((int) event.getX(), (int) event.getY(), randInRange(1, 100), randInRange(1, 100));
				
			}
		});

		canvas.setOnMouseDragged(event -> {
			event.consume();
			fluidplane.addDensity((int) event.getX(), (int) event.getY(), randInRange(0, 100));
			fluidplane.addVelocity((int) event.getX(), (int) event.getY(), randInRange(1, 100), randInRange(1, 100));
		});

		Scene scene = new Scene(root, Color.BLACK);

		primaryStage.setScene(scene);
		primaryStage.show();

		AnimationTimer updateCanvas = new Draw(fluidplane);
		updateCanvas.start();

	}
	
	private int randInRange(int min, int max) {
		return new Random().nextInt(max - min + 1) + min;
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
