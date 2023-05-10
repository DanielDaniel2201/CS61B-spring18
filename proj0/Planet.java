public class Planet {
	public double xxPos;
	public double yyPos;
	public double xxVel;
	public double yyVel;
	public double mass;
	public String imgFileName;
	public static final double G = 6.67e-11;

	public Planet(double xP, double yP, double xV,
              double yV, double m, String img) {
		xxPos = xP;
		yyPos = yP;
		xxVel = xV;
		yyVel = yV;
		mass = m;
		imgFileName = img;
	}

	public Planet(Planet p) {
		xxPos = p.xxPos;
		yyPos = p.yyPos;
		xxVel = p.xxVel;
		yyVel = p.yyVel;
		mass = p.mass;
		imgFileName = p.imgFileName;
	}

	public double calcDistance (Planet p) {
		double dx = p.xxPos - this.xxPos;
		double dy = p.yyPos - this.yyPos;
		return Math.sqrt(dx * dx + dy * dy);
	}

	public double calcForceExertedBy (Planet p) {
		double distance = this.calcDistance(p);
		return G * this.mass * p.mass / (distance * distance);
	}

	public double calcForceExertedByX (Planet p) {
		return this.calcForceExertedBy(p) * (p.xxPos - this.xxPos) / this.calcDistance(p);
	}

	public double calcForceExertedByY (Planet p) {
		return this.calcForceExertedBy(p) * (p.yyPos - this.yyPos) / this.calcDistance(p);
	}

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

	public void update(double dt, double fX, double fY) {
		double accelerationX = fX / this.mass;
		double accelerationY = fY / this.mass;
		this.xxVel = this.xxVel + dt * accelerationX;
		this.yyVel = this.yyVel + dt * accelerationY;
		this.xxPos = this.xxPos + dt * this.xxVel;
		this.yyPos = this.yyPos + dt * this.yyVel;
	}

	public void draw() {
		StdDraw.picture(xxPos, yyPos, "images/" + imgFileName);
	}
}