package dev.hyyrokkin.structs;

public class triangle {

	public int[] v = new int[3];
	public double col = 1;
	
	public triangle() {
		
	}
	
	public triangle(triangle tri) {
		this.v[0] = tri.v[0];
		this.v[1] = tri.v[1];
		this.v[2] = tri.v[2];
		this.col = tri.col;
	}
	
	public triangle(int v0, int v1, int v2) {
		v[0] = v0;
		v[1] = v1;
		v[2] = v2;
	}
	
}
