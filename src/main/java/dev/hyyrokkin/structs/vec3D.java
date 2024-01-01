package dev.hyyrokkin.structs;

public class vec3D {
	public double x = 0, y = 0, z=0, w = 1;	
	
	public vec3D() {
		
	}
	
	public vec3D(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public vec3D clone() {
		vec3D rt = new vec3D();
		rt.x = this.x;
		rt.y = this.y;
		rt.z = this.z;
		rt.w = this.w;
		return rt;
	}
}
