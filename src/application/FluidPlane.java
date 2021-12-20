package application;

public class FluidPlane {

	private static int N;
	private int gridSizeX;
	private int gridSizeY;

	private float dt;
	private float diff;
	private float visc;

	private float[] densityP;
	private float[] densityN;

	private float[] velocityX;
	private float[] velocityY;

	private float[] velocityXP;
	private float[] velocityYP;

	public FluidPlane(int gridSizeX, int gridSizeY, float dt, float diff, float visc) {

		this.gridSizeX = gridSizeX;
		this.gridSizeY = gridSizeY;
		FluidPlane.N = gridSizeX * gridSizeY;

		this.dt = dt;
		this.diff = diff;
		this.visc = visc;

		this.densityP = new float[N];
		this.densityN = new float[N];

		this.velocityX = new float[N];
		this.velocityY = new float[N];

		this.velocityXP = new float[N];
		this.velocityYP = new float[N];

	}

	public void resetPlane() {

		this.setDensityP(new float[N]);
		this.setDensityN(new float[N]);

		this.setVelocityXP(new float[gridSizeX]);
		this.setVelocityYP(new float[gridSizeX]);

		this.setVelocityX(new float[gridSizeX]);
		this.setVelocityY(new float[gridSizeX]);

	}

	public void addDensity(int x, int y, float amount) {

		int index = IX(x, y);
		this.getDensityN()[index] += amount;

	}

	public void addVelocity(int x, int y, float amountX, float amountY) {

		int index = IX(x, y);
		this.getVelocityX()[index] += amountX;
		this.getVelocityY()[index] += amountY;

	}

	public static void diffuse(int b, float[] x, float[] x0, float diff, float dt, int iter) {

		float a = dt * diff * (N - 2) * (N - 2);
		lin_solve(b, x, x0, a, 1 + 6 * a, iter);
	}

	private static void lin_solve(int b, float[] x, float[] x0, float a, float c, int iter) {

		float cRecip = 1.0f / c;
		for (int k = 0; k < iter; k++) {
			for (int j = 1; j < N - 1; j++) {
				for (int i = 1; i < N - 1; i++) {
					x[IX(i, j)] = (x0[IX(i, j)]
							+ a * (x[IX(i + 1, j)] + x[IX(i - 1, j)] + x[IX(i, j + 1)] + x[IX(i, j - 1)])) * cRecip;
				}
			}
			set_bnd(b, x);
		}
	}

	public static void project(float[] velocX, float[] velocY, float[] p, float[] div, int iter) {

		for (int j = 1; j < N - 1; j++) {
			for (int i = 1; i < N - 1; i++) {
				div[IX(i, j)] = -0.5f
						* (velocX[IX(i + 1, j)] - velocX[IX(i - 1, j)] + velocY[IX(i, j + 1)] - velocY[IX(i, j - 1)])
						/ N;
				p[IX(i, j)] = 0;
			}

		}

		set_bnd(0, div);
		set_bnd(0, p);

		lin_solve(0, p, div, 1, 6, iter);

		for (int j = 1; j < N - 1; j++) {
			for (int i = 1; i < N - 1; i++) {
				velocX[IX(i, j)] -= 0.5f * (p[IX(i + 1, j)] - p[IX(i - 1, j)]) * N;
				velocY[IX(i, j)] -= 0.5f * (p[IX(i, j + 1)] - p[IX(i, j - 1)]) * N;
			}
		}

		set_bnd(1, velocX);
		set_bnd(2, velocY);
	}

	static void advect(int b, float[] d, float[] d0, float[] velocX, float[] velocY, float dt) {
		float i0, i1, j0, j1;

		float dtx = dt * (N - 2);
		float dty = dt * (N - 2);

		float s0, s1, t0, t1;
		float tmp1, tmp2, x, y;

		float Nfloat = N;
		float ifloat, jfloat;
		int i, j;

		for (j = 1, jfloat = 1; j < N - 1; j++, jfloat++) {
			for (i = 1, ifloat = 1; i < N - 1; i++, ifloat++) {

				tmp1 = dtx * velocX[IX(i, j)];
				tmp2 = dty * velocY[IX(i, j)];

				x = ifloat - tmp1;
				y = jfloat - tmp2;

				if (x < 0.5f)
					x = 0.5f;
				
				if (x > Nfloat + 0.5f)
					x = Nfloat + 0.5f;
				
				i0 = (float) Math.floor(x);
				i1 = i0 + 1.0f;
				
				if (y < 0.5f)
					y = 0.5f;
				
				if (y > Nfloat + 0.5f)
					y = Nfloat + 0.5f;
				
				j0 = (float) Math.floor(y);
				j1 = j0 + 1.0f;

				s1 = x - i0;
				s0 = 1.0f - s1;
				t1 = y - j0;
				t0 = 1.0f - t1;

				int i0i = Math.round(i0);
				int i1i = Math.round(i1);
				int j0i = Math.round(j0);
				int j1i = Math.round(j1);

				d[IX(i, j)] =
							s0 * (t0 * d0[IX(i0i, j0i)]) + (t1 * d0[IX(i0i, j1i)])
							+ s1 * (t0 * d0[IX(i1i, j0i)]) + (t1 * d0[IX(i1i, j1i)]);
				
			}

		}
		set_bnd(b, d);
	}
	
	private static void set_bnd(int b, float[] x)
	{
		
	        for(int i = 1; i < N - 1; i++) {
	            x[IX(i, 0 )] = b == 2 ? -x[IX(i, 1 )] : x[IX(i, 1)];
	            x[IX(i, N-1)] = b == 2 ? -x[IX(i, N-2)] : x[IX(i, N-2)];
	        }
	        
	        for(int j = 1; j < N - 1; j++) {
	            x[IX(0  , j)] = b == 1 ? -x[IX(1  , j)] : x[IX(1  , j)];
	            x[IX(N-1, j)] = b == 1 ? -x[IX(N-2, j)] : x[IX(N-2, j)];
	        }
	    
	    x[IX(0, 0)]       = 0.33f * (x[IX(1, 0)]
	                                  + x[IX(0, 1)]
	                                  + x[IX(0, 0)]);
	    
	    x[IX(0, N-1)]     = 0.33f * (x[IX(1, N-1)]
	                                  + x[IX(0, N-2)]
	                                  + x[IX(0, N-1)]);
	    
	    x[IX(N-1, 0)]     = 0.33f * (x[IX(N-2, 0)]
	                                  + x[IX(N-1, 1)]
	                                  + x[IX(N-1, 0)]);
	    
	    x[IX(N-1, N-1)]   = 0.33f * (x[IX(N-2, N-1)]
	                                  + x[IX(N-1, N-2)]
	                                  + x[IX(N-1, N-1)]);
	    
	}

	/* GETTERS AND SETTERS */

	public int getN() {
		return N;
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

	public float[] getDensityP() {
		return densityP;
	}

	public void setDensityP(float[] densityP) {
		this.densityP = densityP;
	}

	public float[] getDensityN() {
		return densityN;
	}

	public void setDensityN(float[] densityN) {
		this.densityN = densityN;
	}

	public float[] getVelocityX() {
		return velocityX;
	}

	public void setVelocityX(float[] velocityX) {
		this.velocityX = velocityX;
	}

	public float[] getVelocityY() {
		return velocityY;
	}

	public void setVelocityY(float[] velocityY) {
		this.velocityY = velocityY;
	}

	public float[] getVelocityXP() {
		return velocityXP;
	}

	public void setVelocityXP(float[] velocityXP) {
		this.velocityXP = velocityXP;
	}

	public float[] getVelocityYP() {
		return velocityYP;
	}

	public void setVelocityYP(float[] velocityYP) {
		this.velocityYP = velocityYP;
	}

	public void setVisc(float visc) {
		this.visc = visc;
	}

	private static int IX(int x, int y) {
		return x + y * N;
	}

	/*public void step(FluidPlane plane)
	{
	    
	    FluidPlane.diffuse(1, plane.getVelocityXP(), plane.getVelocityX(), plane.getVisc(), plane.getDt(), 4);
	    FluidPlane.diffuse(2, plane.getVelocityYP(), plane.getVelocityY(), plane.getVisc(), plane.getDt(), 4);
	    
	    FluidPlane.project(plane.getVelocityXP(), plane.getVelocityYP(), plane.getVelocityX(), plane.getVelocityY(), 4);
	    
	    FluidPlane.advect(1, plane.getVelocityX(), plane.getVelocityXP(), plane.getVelocityXP(), plane.getVelocityY(), plane.getDt());
	    FluidPlane.advect(2, plane.getVelocityY(), plane.getVelocityYP(), plane.getVelocityXP(), plane.getVelocityYP(), plane.getDt());
	    
	    FluidPlane.project(plane.getVelocityXP(), plane.getVelocityYP(), plane.getVelocityX(), plane.getVelocityY(), 4);
	    
	    FluidPlane.diffuse(0, plane.getDensityP(), plane.getDensityN(), plane.getDiff(), plane.getDt(), 4);
	    FluidPlane.advect(0, plane.getDensityN(), plane.getDensityP(), plane.getVelocityX(), plane.getVelocityY(), plane.getDt());
	}*/
	
	void step() {
	    diffuse(1, velocityXP, velocityX, visc, dt, 4);
	    diffuse(2, velocityYP, velocityY, visc, dt, 4);
	    
	    project(velocityXP, velocityYP, velocityX, velocityY, 4);
	    
	    advect(1, velocityX, velocityXP, velocityXP, velocityYP, dt);
	    advect(2, velocityY, velocityYP, velocityXP, velocityYP, dt);
	    
	    project(velocityXP, velocityYP, velocityX, velocityY, 4);
	    
	    diffuse(0, densityP, densityN, diff, dt, 4);
	    advect(0, densityN, densityP, velocityX, velocityY, dt);
	}

}
