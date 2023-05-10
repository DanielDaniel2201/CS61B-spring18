public class NBody {
	//returns the radius of the universe.
	public static double readRadius(String file_name) {
		In in = new In(file_name);
		int N = in.readInt();
		return in.readDouble();
	}

	//returns an array of Planet object.
	public static Planet[] readPlanets(String file_name) {
		In in = new In(file_name);
		int N = in.readInt();
		double radius = in.readDouble();
		Planet[] Planets = new Planet[N];
		for (int i = 0; i < N; i += 1) {
			double xP = in.readDouble();
			double yP = in.readDouble();
			double xV = in.readDouble();
			double yV = in.readDouble();
			double m = in.readDouble();
			String img = in.readString();
			Planet P = new Planet(xP, yP, xV, yV, m, img);
			Planets[i] = P;
		} return Planets;
	}

	public static void main(String[] args) {
		double T = Double.parseDouble(args[0]);
		double dt = Double.parseDouble(args[1]);
		String filename = (args[2]);
		double r = readRadius(filename);
		Planet[] Planets = readPlanets(filename);

		double t = 0;
		int N = Planets.length;
		while (t < T) {
			double[] xForces = new double[N];
			double[] yForces = new double[N];

			//Calculate the net x and y forces for each planet, storing these in the xForces and yForces arrays respectively.
			for (int i = 0; i < N; i += 1) {
				xForces[i] = Planets[i].calcNetForceExertedByX(Planets);
				yForces[i] = Planets[i].calcNetForceExertedByY(Planets);
			}
			//updates each planet’s position, velocity, and acceleration.
			for (int i = 0; i < N; i += 1) {
				Planets[i].update(dt, xForces[i], yForces[i]);
			}

			//draws the universe and the planets on the offscreen canvas.
			StdDraw.setScale(-r, r);
			StdDraw.picture(0, 0, "images/starfield.jpg");
			StdDraw.enableDoubleBuffering();

			for (Planet p: Planets) {
				p.draw();
			}

			//shows what is drawn on the offscreen canvas.
			StdDraw.show();

			StdDraw.pause(10);

			t += dt;
		}

		StdOut.printf("%d\n", Planets.length);
		StdOut.printf("%.2e\n", Planets);
		for (int i = 0; i < Planets.length; i++) {
			StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
					Planets[i].xxPos, Planets[i].yyPos, Planets[i].xxVel,
					Planets[i].yyVel, Planets[i].mass, Planets[i].imgFileName);
		}
		//here are many more universes provided in the skeleton other than planets.txt.
		//You can find them in the data folder,
		//e.g. java NBody 20000000 20000 ./data/suninterference.txt.
	}
}