package application;

public class FluidPlane {
	
	private int gridSizeX;
	private int gridSizeY;
	
	private float dt;
	private float diff;
	private float visc;
    
	private float[][] densityP;
	private float[][] densityN;
    
	private float[][] velocityX;
	private float[][] velocityY;

	private float[][] velocityXP;
	private float[][] velocityYP;
	
	public FluidPlane(int gridSizeX, int gridSizeY, float dt, float diff, float visc) {
		
		this.gridSizeX = gridSizeX;
		this.gridSizeY = gridSizeY;
		
		this.dt = dt;
		this.diff = diff;
		this.visc = visc;
		
		this.densityP = new float[gridSizeX][gridSizeY];
		this.densityN = new float[gridSizeX][gridSizeY];
	    
		this.velocityX = new float[gridSizeX][gridSizeY];
		this.velocityY = new float[gridSizeX][gridSizeY];

		this.velocityXP = new float[gridSizeX][gridSizeY];
		this.velocityYP = new float[gridSizeX][gridSizeY];
		
	}
	
	public void resetPlane() {
		
		this.setDensityP(new float[gridSizeX][gridSizeY]);
		this.setDensityN(new float[gridSizeX][gridSizeY]);
		
		this.setVelocityXP(new float[gridSizeX][gridSizeY]);
		this.setVelocityYP(new float[gridSizeX][gridSizeY]);
		
		this.setVelocityX(new float[gridSizeX][gridSizeY]);
		this.setVelocityY(new float[gridSizeX][gridSizeY]);
		
	}
	
	public void addDensity(int x, int y, float amount) {
		
		this.getDensityN()[x][y] += amount;
		
	}
	
	public void addVelocity(int x, int y, float amountX, float amountY) {
		
		this.getVelocityX()[x][y] += amountX;
		this.getVelocityY()[x][y] += amountY;
		
	}

	

	public int getGridSizeX() {
		return gridSizeX;
	}

	public void setGridSizeX(int gridSizeX) {
		this.gridSizeX = gridSizeX;
	}

	public int getGridSizeY() {
		return gridSizeY;
	}

	public void setGridSizeY(int gridSizeY) {
		this.gridSizeY = gridSizeY;
	}

	public float getDt() {
		return dt;
	}

	public void setDt(float dt) {
		this.dt = dt;
	}

	public float getDiff() {
		return diff;
	}

	public void setDiff(float diff) {
		this.diff = diff;
	}

	public float getVisc() {
		return visc;
	}

	public void setVisc(float visc) {
		this.visc = visc;
	}

	public float[][] getDensityP() {
		return densityP;
	}

	public void setDensityP(float[][] densityP) {
		this.densityP = densityP;
	}

	public float[][] getDensityN() {
		return densityN;
	}

	public void setDensityN(float[][] densityN) {
		this.densityN = densityN;
	}

	public float[][] getVelocityX() {
		return velocityX;
	}

	public void setVelocityX(float[][] velocityX) {
		this.velocityX = velocityX;
	}

	public float[][] getVelocityY() {
		return velocityY;
	}

	public void setVelocityY(float[][] velocityY) {
		this.velocityY = velocityY;
	}

	public float[][] getVelocityXP() {
		return velocityXP;
	}

	public void setVelocityXP(float[][] velocityXP) {
		this.velocityXP = velocityXP;
	}

	public float[][] getVelocityYP() {
		return velocityYP;
	}

	public void setVelocityYP(float[][] velocityYP) {
		this.velocityYP = velocityYP;
	}
	
	
	

}
