package de.vitox.particle;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;

public class ParticleGenerator {

	public static int anzahl;
	public static int breite;
	public static int hohe;

	public ArrayList<Particle> particles = new ArrayList();
	private Random random = new Random();
	private Timer timer = new Timer();

	public ParticleGenerator(int anzahl, int breite, int hohe) {
		this.anzahl = anzahl;
		this.breite = breite;
		this.hohe = hohe;
		for (int i = 0; i < anzahl; i++) {
			this.particles.add(new Particle(this.random.nextInt(breite), this.random.nextInt(hohe)));
		}
	}

	public void drawParticles() {
		for (Particle p : this.particles) {
			p.draw();
		}
	}
}
