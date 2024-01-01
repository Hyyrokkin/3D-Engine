package dev.hyyrokkin.structs;
import java.util.ArrayList;

public class mesh{
		public ArrayList<vec3D> vertex = new ArrayList<vec3D>();
		public ArrayList<triangle> tris = new ArrayList<triangle>();
		
		
		public void clear() {
			vertex.clear();
			tris.clear();
		}
	}
