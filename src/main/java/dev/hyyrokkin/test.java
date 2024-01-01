package dev.hyyrokkin;

import dev.hyyrokkin.structs.matrix4x4;
import dev.hyyrokkin.structs.vec3D;
import dev.hyyrokkin.util.mathLib;

public class test {
	mathLib lib = new mathLib();
	matrix4x4 matR = lib.rotMatY(20);
	matrix4x4 matP = lib.projMat(90, 1.25, 0.1, 1000);
	vec3D[] vec = {new vec3D(-1, -1, -1),new vec3D(-1, 1, -1),new vec3D(1, 1, -1),new vec3D(1, -1, -1),new vec3D(-1, -1, 1),new vec3D(-1, 1, 1),new vec3D(1, 1, 1),new vec3D(1, -1, 1)};
	vec3D[] vecR = vec.clone();
	vec3D[] vecP = vec.clone();
	vec3D[] vecW = vec.clone();
	vec3D[] vecF = vec.clone();
	test(){
		System.out.println("Trans:");
		for(int i = 0; i< vec.length;i++) {
			vecR[i] = lib.MatrixVectorMult(matR, vec[i]);
			vecR[i].z = vecR[i].z + 10;
			System.out.println(vecR[i].x+"   "+vecR[i].y+"   "+ vecR[i].z);
		}	
		System.out.println("Proj:");
		for(int i = 0; i< vec.length;i++) {
			vecP[i] = lib.MatrixVectorMult(matP, vecR[i]);
			System.out.println(vecP[i].x+"   "+vecP[i].y+"   "+ vecP[i].z + "   " + vecP[i].w);	
		}
		System.out.println("Div by W:");
		for(int i = 0; i< vec.length;i++) {
			vecW[i] = lib.DivByW(vecP[i]);
			System.out.println(vecW[i].x+"   "+vecW[i].y+"   "+ vecW[i].z + "   " + vecW[i].w);
		}
		System.out.println("Final:");
		for(int i = 0; i< vec.length;i++) {
			vec3D tmp = vecW[i];
			tmp.x = (tmp.x + 1)/2*1000;
			tmp.y = (tmp.y + 1)/2*800;
			vecF[i]= tmp;
			System.out.println(vecF[i].x+"   "+vecW[i].y+"   "+ vecW[i].z + "   " + vecW[i].w);
		}
	}
	
	public static void main(String[] args) {
		new test();
	}	
}
