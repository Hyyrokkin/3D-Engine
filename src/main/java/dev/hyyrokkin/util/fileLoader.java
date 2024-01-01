package dev.hyyrokkin.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import dev.hyyrokkin.structs.mesh;
import dev.hyyrokkin.structs.triangle;
import dev.hyyrokkin.structs.vec3D;

public class fileLoader {
	
	private String path;
	
	public fileLoader(String path) {
		this.path = path;
	}
	
	public mesh loadFile() {
		mesh result = new mesh();
		

	        try{
	            BufferedReader br = new BufferedReader(new FileReader(new File(path)));
	            
	            String[] line;
	            String s;
	            
	            while((s = br.readLine()) != null) {
	            	line = s.trim().split(" ");
	            	if(line[0].contentEquals("v")) {
	            		result.vertex.add(new vec3D(Double.parseDouble(line[1]), Double.parseDouble(line[2]), Double.parseDouble(line[3])));
	            	}else if(line[0].contentEquals("f")) {
	            		result.tris.add(new triangle(Integer.parseInt(line[1]), Integer.parseInt(line[2]), Integer.parseInt(line[3])));
	            	}
	            }
	            br.close();
	        }catch(IOException e){
	        	e.printStackTrace();
	        }	    
		
		return result;
	}	
}