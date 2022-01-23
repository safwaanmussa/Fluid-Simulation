# Fluid-Simulation

This project is a 2-D fluid simulation for incompressible fluids.

Graphics were handled with JavaFX Canvas.

What I learnt...

- How to compute mathematical equations (Navier Stokes) to a suitable time complexity. 
- Solid understanding of the need for parallel programming within graphically intense applications. 
    Multiple functions needed to be run in parallel in order for the application to function, such as the Canvas rendering
    and physics update methods being run each frame.
- The need of reducing accuracy in order to ensure performance. Somtimes high accuracy is more a detriment than an advantage. 
    In particular for the Gauss Siedel linear iterative solver, reducing the number of iterations ensured a suitable performance,
    while maintaining a good level of accuracy.
- Updates are run every single frame, and having complex data structures to iterate over is highly inefficient. I learnt using simple
    data structures effectively can massively reduce space complexity and increase performance.

What improvements to make?

- Removing divergence and adding curl to make a more realistic simulation of fluids.
- Better optimise code to allow for higher resolutions of the canvas. This could be either a more effective solution on parallel processing,
    restructuring functions to a better computational method to calculate advection/projection, better physics update method,
    or remaking the application within a lower level language, such as C.
- Add dye colouring to the simulation instead of plain white on black.
