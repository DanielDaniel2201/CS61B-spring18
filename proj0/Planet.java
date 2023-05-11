public class Planet {
	public double xxPos;
	public double yyPos;
	public double xxVel;
	public double yyVel;
	public double mass;
	public String imgFileName;
	private static final double G = 6.67e-11;

	// Planet constructor
	public Planet(double xP, double yP, double xV,
              double yV, double m, String img) {
		xxPos = xP;
		yyPos = yP;
		xxVel = xV;
		yyVel = yV;
		mass = m;
		imgFileName = img;
	}

	//the second Planet constructor
	public Planet(Planet p) {
		xxPos = p.xxPos;
		yyPos = p.yyPos;
		xxVel = p.xxVel;
		yyVel = p.yyVel;
		mass = p.mass;
		imgFileName = p.imgFileName;
	}

	//calculates the distance between this planet and the planet p passed in.
	public double calcDistance (Planet p) {
		double dx = p.xxPos - this.xxPos;
		double dy = p.yyPos - this.yyPos;
		return Math.sqrt(dx * dx + dy * dy);
	}

	//takes in a planet, and returns a double describing the force exerted on this planet by the given planet.
	public double calcForceExertedBy (Planet p) {
		double distance = this.calcDistance(p);
		return G * this.mass * p.mass / (distance * distance);
	}

	//takes in a planet, and returns the exerted force in x direction.
	public double calcForceExertedByX (Planet p) {
		return this.calcForceExertedBy(p) * (p.xxPos - this.xxPos) / this.calcDistance(p);
	}

	//takes in a planet, and returns the exerted force in y direction.
	public double calcForceExertedByY (Planet p) {
		return this.calcForceExertedBy(p) * (p.yyPos - this.yyPos) / this.calcDistance(p);
	}

	//takes in an array of Planets and calculate the net X force exerted by all planets in that array upon the current Planet.
	public double calcNetForceExertedByX (Planet[] array) {
		double sum = 0;
		for (Planet p: array) {
			if (this.equals(p)) {
				continue;
			} else {
				sum = sum + this.calcForceExertedByX(p);
			} 
		} return sum;
	}

	//takes in an array of Planets and calculate the net Y force exerted by all planets in that array upon the current Planet.
	public double calcNetForceExertedByY (Planet[] array) {
		double sum = 0;
		for (Planet p: array) {
			if (this.equals(p)) {
				continue;
			} else {
				sum = sum + this.calcForceExertedByY(p);
			} 
		} return sum;
	}

	//takes a dt, net force exerted in two directions, and updates the planet's state
	public void update(double dt, double fX, double fY) {
		double accelerationX = fX / this.mass;
		double accelerationY = fY / this.mass;
		this.xxVel = this.xxVel + dt * accelerationX;
		this.yyVel = this.yyVel + dt * accelerationY;
		this.xxPos = this.xxPos + dt * this.xxVel;
		this.yyPos = this.yyPos + dt * this.yyVel;
	}

	//draws this planet on the offscreen canvas, and does not display anything until StdDraw.show() is called.
	public void draw(){
		StdDraw.picture(xxPos, yyPos, "images/" + imgFileName);
	}
}