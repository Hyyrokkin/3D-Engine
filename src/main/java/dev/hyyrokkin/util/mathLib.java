package dev.hyyrokkin.util;

import java.lang.Math;

import dev.hyyrokkin.structs.matrix4x4;
import dev.hyyrokkin.structs.vec3D;

public class mathLib {
	
	public vec3D MatrixVectorMult(matrix4x4 m, vec3D v) {
		vec3D result = new vec3D();
		
		result.x = v.x * m.m[0][0] + v.y * m.m[1][0] + v.z * m.m[2][0] + v.w * m.m[3][0];
		result.y = v.x * m.m[0][1] + v.y * m.m[1][1] + v.z * m.m[2][1] + v.w * m.m[3][1];
		result.z = v.x * m.m[0][2] + v.y * m.m[1][2] + v.z * m.m[2][2] + v.w * m.m[3][2];
		result.w = v.x * m.m[0][3] + v.y * m.m[1][3] + v.z * m.m[2][3] + v.w * m.m[3][3];
		
		return result;
	}
	
	public vec3D DivByW(vec3D v1) {
		vec3D result = v1.clone();
		if(v1.w!= 0) {
			result.x = v1.x / v1.w;
			result.y = v1.y / v1.w;
			result.z = v1.z / v1.w;
		}
		return result;
	}
	
	public matrix4x4 MatrixMatrixMult(matrix4x4 m1, matrix4x4 m2) {
		matrix4x4 result = new matrix4x4();
		for(int i = 0; i < 4; i++) {
			for(int k = 0; k < 4; k++) {
				result.m[k][i] = ((m1.m[k][0] * m2.m[0][i]) + (m1.m[k][1] * m2.m[1][i]) + (m1.m[k][2] * m2.m[2][i]) + (m1.m[k][3] * m2.m[3][i]));
			}
		}
		return result;
	}
	
	public vec3D VectorAdd(vec3D v1, vec3D v2) {
		vec3D result = new vec3D();
		result.x = v1.x + v2.x;
		result.y = v1.y + v2.y;
		result.z = v1.z + v2.z;
		result.w = v1.w;
		return result;
	}
	
	public vec3D VectorSub(vec3D v1, vec3D v2) {
		vec3D result = new vec3D();
		result.x = v1.x - v2.x;
		result.y = v1.y - v2.y;
		result.z = v1.z - v2.z;
		result.w = v1.w;
		return result;
	}
	
	public vec3D VectorMul(vec3D v1, double k) {
		vec3D result = new vec3D();
		result.x = v1.x * k;
		result.y = v1.y * k;
		result.z = v1.z * k;
		result.w = v1.w;
		return result;
	}
	
	public vec3D VectorDiv(vec3D v1, double k) {
		vec3D result = new vec3D();
		result.x = v1.x / k;
		result.y = v1.y / k;
		result.z = v1.z / k;
		result.w = v1.w ;
		return result;
	}
	
	public double skalarProdukt(vec3D v1, vec3D v2) {		
		return (v1.x * v2.x) + (v1.y * v2.y) + (v1.z * v2.z);
	}
	
	public double VectorLaenge(vec3D v1) {
		return Math.sqrt(v1.x * v1.x + v1.y * v1.y + v1.z * v1.z);
	}
	
	public vec3D VerctorMakeUnit(vec3D v1) {
		vec3D result = v1.clone();
		double l = VectorLaenge(v1);
		result.x = v1.x / l;
		result.y = v1.y / l;
		result.z = v1.z / l;
		return result;
	}
	
	public vec3D VectorKreuzProdukt(vec3D v1, vec3D v2) {
		vec3D result = v1.clone();
		result.x =  v1.y * v2.z -  v1.z * v2.y;
		result.y =  v1.z * v2.x -  v1.x * v2.z;
		result.z =  v1.x * v2.y -  v1.y * v2.x;
		return result;
	}
	
	public matrix4x4 unitMatrix() {
		matrix4x4 matrix = new matrix4x4();
		matrix.m[0][0] = 1.0d;	
		matrix.m[1][1] = 1.0d;	
		matrix.m[2][2] = 1.0d;	
		matrix.m[3][3] = 1.0d;	
		return matrix;
	}
	
	public matrix4x4 rotMatX(double dTheta) {
		matrix4x4 rotMatX = new matrix4x4();
		rotMatX.m[0][0] = 1;
		rotMatX.m[1][1] = Math.cos(dTheta * 0.5d);
		rotMatX.m[1][2] = Math.sin(dTheta * 0.5d);
		rotMatX.m[2][1] = -Math.sin(dTheta * 0.5d);
		rotMatX.m[2][2] = Math.cos(dTheta * 0.5d);
		rotMatX.m[3][3] = 1;
		return rotMatX;
	}
	
	public matrix4x4 rotMatY(double dTheta) {
		matrix4x4 rotMatY = new matrix4x4();
		rotMatY.m[0][0] = Math.cos(dTheta);
		rotMatY.m[0][2] = Math.sin(dTheta);
		rotMatY.m[1][1] = 1;
		rotMatY.m[2][0] = -Math.sin(dTheta);
		rotMatY.m[2][2] = Math.cos(dTheta);
		rotMatY.m[3][3] = 1;
		return rotMatY;
	}	
	
	public matrix4x4 rotMatZ(double dTheta) {
		matrix4x4 rotMatZ = new matrix4x4();
		rotMatZ.m[0][0] = Math.cos(dTheta);
		rotMatZ.m[0][1] = Math.sin(dTheta);
		rotMatZ.m[1][0] = -Math.sin(dTheta);
		rotMatZ.m[1][1] = Math.cos(dTheta);
		rotMatZ.m[2][2] = 1;
		rotMatZ.m[3][3] = 1;
		return rotMatZ;
	}
	
	public matrix4x4 transMat(double x ,double y ,double z) {
		matrix4x4 transMat = new matrix4x4();
		transMat.m[0][0] = 1.0d;	
		transMat.m[1][1] = 1.0d;	
		transMat.m[2][2] = 1.0d;	
		transMat.m[3][3] = 1.0d;
		transMat.m[3][0] = x;	
		transMat.m[3][1] = y;	
		transMat.m[3][2] = z;	
		return transMat;
	}
	
	public matrix4x4 projMat(double dFov, double dAspectRatio, double dNear, double dFar) {
		matrix4x4 projMat = new matrix4x4();
		double dFovRad = 1.0d / Math.tan((dFov * 0.5d) / (180.d * Math.PI));
		projMat.m[0][0] = dAspectRatio * dFovRad;
		projMat.m[1][1] = dFovRad;
		projMat.m[2][2] = dFar / (dFar - dNear);
		projMat.m[3][2] = (-dFar * dNear) / (dFar - dNear);
		projMat.m[2][3] = 1.0d;
		projMat.m[3][3] = 0.0d;
		return projMat;
	}
	
	public matrix4x4 pointMat(vec3D pos, vec3D target, vec3D up) {
		matrix4x4 pointMat = new matrix4x4();
		
		vec3D nF = VectorSub(target, pos);
		nF = VerctorMakeUnit(nF);
		
		vec3D tmp = VectorMul(nF, skalarProdukt(up, nF));
		vec3D nU = VectorSub(up, tmp);
		nU = VerctorMakeUnit(nU);
		
		vec3D nR = VectorKreuzProdukt(nU, nF);
		
		pointMat.m[0][0] = nR.x;  pointMat.m[0][1] = nR.y;  pointMat.m[0][2] = nR.z;  pointMat.m[0][3] = 0;
		pointMat.m[1][0] = nU.x;  pointMat.m[1][1] = nU.y;  pointMat.m[1][2] = nU.z;  pointMat.m[1][3] = 0;
		pointMat.m[2][0] = nF.x;  pointMat.m[2][1] = nF.y;  pointMat.m[2][2] = nF.z;  pointMat.m[2][3] = 0;
		pointMat.m[3][0] = pos.x; pointMat.m[3][1] = pos.y; pointMat.m[3][2] = pos.z; pointMat.m[3][3] = 1;
		
		return pointMat;
	}
	
	public matrix4x4 lookMat(vec3D pos, vec3D target, vec3D up) {
		matrix4x4 lookMat = new matrix4x4();
		
		vec3D nF = VectorSub(target, pos);
		nF = VerctorMakeUnit(nF);
		
		vec3D tmp = VectorMul(nF, skalarProdukt(up, nF));
		vec3D nU = VectorSub(up, tmp);
		nU = VerctorMakeUnit(nU);
		
		vec3D nR = VectorKreuzProdukt(nU, nF);
		
		lookMat.m[0][0] = nR.x; 					 lookMat.m[0][1] = nU.x; 					  lookMat.m[0][2] = nF.x;  				   lookMat.m[0][3] = 0;
		lookMat.m[1][0] = nR.y;  					 lookMat.m[1][1] = nU.y; 					  lookMat.m[1][2] = nF.y; 					   lookMat.m[1][3] = 0;
		lookMat.m[2][0] = nR.z; 					 lookMat.m[2][1] = nU.z; 					  lookMat.m[2][2] = nF.z; 					   lookMat.m[2][3] = 0;
		lookMat.m[3][0] = -skalarProdukt(pos , nR); lookMat.m[3][1] = -skalarProdukt(pos , nU); lookMat.m[3][2] = -skalarProdukt(pos , nF); lookMat.m[3][3] = 1;
		
		return lookMat;
	}
	
	
	
}
