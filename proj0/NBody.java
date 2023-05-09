public class NBody {
	public static double readRadius(String file_name) {
		In in = new In(file_name);
		int N = in.readInt();
		return in.readDouble();
	}

	public static Planet[] readPlanets(String file_name) {
		In in = new In(file_name);
		int N = in.readInt();
		double radius = in.readDouble();
		Planet[] Planets = new Planet[N];
		int cnt = 0;
		while (cnt <= N - 1) {
			double xP = in.readDouble();
			double yP = in.readDouble();
			double xV = in.readDouble();
			double yV = in.readDouble();
			double m = in.readDouble();
			String img = in.readString();
			Planet P = new Planet(xP, yP, xV, yV, m, img);
			Planets[cnt] = P;
			cnt += 1;
		} return Planets;
	}

	public static void main(String[] args) {
		double T = Double.parseDouble(args[0]);
		double dt = Double.parseDouble(args[1]);
		String filename = (args[2]);
		readRadius(filename);
		readPlanets(filename);
	}
}