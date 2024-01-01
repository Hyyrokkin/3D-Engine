package dev.hyyrokkin;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



import javax.swing.*;

import java.util.Collections;
import java.util.Comparator;

import dev.hyyrokkin.structs.matrix4x4;
import dev.hyyrokkin.structs.mesh;
import dev.hyyrokkin.structs.triangle;
import dev.hyyrokkin.structs.vec3D;
import dev.hyyrokkin.util.IsKeyPressed;
import dev.hyyrokkin.util.fileLoader;
import dev.hyyrokkin.util.mathLib;

public class T3D extends JPanel implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	mathLib lib = new mathLib();
	fileLoader lod;
	Timer timer;
	
	int width = 1600;
	int height = 1000;
	float xScale = 1f;
	float yScale = 1f;
	int fps = 60;
	double dTheta = 00;
	double dYaw = 0;
	
	String path = "bin/dev/hyyrokkin/seahorse.obj";
	Boolean wireframe = true;
	
	mesh mesh = new mesh();
	mesh meshTransform = new mesh();
	mesh meshLit = new mesh();
	mesh meshView = new mesh();
	mesh meshProjected = new mesh();
	
	matrix4x4 projecMat = new matrix4x4();
	matrix4x4 rotZMat = new matrix4x4();
	matrix4x4 rotXMat = new matrix4x4();
	matrix4x4 rotYMat = new matrix4x4();
	
	vec3D vCamera = new vec3D();
	vec3D vLookDir = new vec3D();
	vec3D vLightDir = new vec3D();
	
	double dNear = 0.1d;
	double dFar = 1000.0d;
	double dFov = 90.0d;
	double dAspectRatio = (double)this.height / (double)this.width;

	
	
	private void Create() {
		
		timer = new Timer(1000/fps, this);
		lod = new fileLoader(path);
		
		vLightDir.x =   0.0d;
		vLightDir.y = - 0.5d;
		vLightDir.z = - 1.0d;
		vLightDir = lib.VerctorMakeUnit(vLightDir);
		
		
		projecMat  = lib.projMat(dFov, dAspectRatio, dNear, dFar);
		
		mesh = lod.loadFile();
		
	}
	
	
	private void Update() {
		meshTransform.clear();
		meshLit.clear();
		meshView.clear();
		meshProjected.clear();
		
		
		rotZMat = lib.rotMatZ(dTheta + Math.PI);
		rotYMat = lib.rotMatY(dTheta + Math.PI);
		rotXMat = lib.rotMatX(dTheta);
		
		matrix4x4 transMat = lib.transMat(0.0d, 0.0d, 20.0d);
		
		matrix4x4 worldMat = lib.unitMatrix();
		worldMat = lib.MatrixMatrixMult( worldMat, rotXMat);
		worldMat = lib.MatrixMatrixMult( worldMat ,rotYMat);
		worldMat = lib.MatrixMatrixMult( worldMat ,rotZMat);
		worldMat = lib.MatrixMatrixMult( worldMat, transMat);
		
		vec3D vUp = new vec3D(0, 1, 0);
		vec3D vTarget = new vec3D(0, 0, 1);
		matrix4x4 camRotMat = lib.rotMatY(dYaw);
		vLookDir = lib.MatrixVectorMult(camRotMat, vTarget);
		vTarget = lib.VectorAdd(vCamera, vLookDir);
		
		//matrix4x4 pointMat = lib.pointMat(vCamera, vTarget, vUp);
		matrix4x4 viewMat = lib.lookMat(vCamera, vTarget, vUp);
		
		for(vec3D vec : mesh.vertex) {
			
			vec3D vecTrans = vec.clone();
			
			vecTrans = lib.MatrixVectorMult(worldMat, vecTrans);
			meshTransform.vertex.add(vecTrans);
		}
		for(triangle tri : mesh.tris) {
			meshTransform.tris.add(new triangle(tri));
		}		
		
		vec3D camPoint = vCamera.clone();
		lib.MatrixVectorMult(viewMat, camPoint);
		for(vec3D vecTrans : meshTransform.vertex) {
			meshView.vertex.add(lib.MatrixVectorMult(viewMat, vecTrans));
		}
		for(triangle tri : meshTransform.tris) {
			meshView.tris.add(new triangle(tri));
		}	
		
		
		for(vec3D vec : meshView.vertex) {
			meshLit.vertex.add(vec.clone());
		}
		
		for(triangle tri : meshView.tris) {
			vec3D normal, line1, line2;
			line1 = lib.VectorSub(meshView.vertex.get(tri.v[1]-1), meshView.vertex.get(tri.v[0]-1));
			line2 = lib.VectorSub(meshView.vertex.get(tri.v[2]-1), meshView.vertex.get(tri.v[0]-1));
			normal = lib.VectorKreuzProdukt(line1, line2);
			normal = lib.VerctorMakeUnit(normal);
			
			camPoint = lib.VectorSub(camPoint, meshView.vertex.get(tri.v[0]-1));
 			camPoint = lib.VerctorMakeUnit(camPoint);
			
			if(lib.skalarProdukt(normal, camPoint) > 0.0d) {
				tri.col = lib.skalarProdukt(normal, vLightDir);
				meshLit.tris.add(new triangle(tri));
			}
		}
		
		
		Collections.sort(meshLit.tris, new Comparator<triangle>() {
	        @Override
	        public int compare(triangle t1, triangle t2)
	        {
	        	double d1 = (meshLit.vertex.get(t1.v[0]-1).z + meshLit.vertex.get(t1.v[1]-1).z + meshLit.vertex.get(t1.v[2]-1).z) / 3.0d;
	        	double d2 = (meshLit.vertex.get(t2.v[0]-1).z + meshLit.vertex.get(t2.v[1]-1).z + meshLit.vertex.get(t2.v[2]-1).z) / 3.0d;
	            if(d1 < d2) {
	            	return 1;
	            }else if(d1 == d2) {
	            	return 0;
	            }else{
	            	return -1;
	            }
	        }
	    });
		
		for(vec3D vecLit : meshLit.vertex) {
			
			vec3D vecProjected = lib.MatrixVectorMult(projecMat, vecLit);
			vecProjected = lib.DivByW(vecProjected);			
			
			vecProjected.x += 1.0d; vecProjected.y += 1.0d;

			vecProjected.x = (vecProjected.x * 0.5d) * (double) width;
			vecProjected.y = (vecProjected.y * 0.5d) * (double) height;
			
			meshProjected.vertex.add(vecProjected);
		}
		for(triangle tri : meshLit.tris) {
			meshProjected.tris.add(new triangle(tri));
		}	
		
		
 
	}

	
	
	public T3D(String[] args) {	
		try{
			if(args[0] != "") {
				this.path = args[0];
			}
			if(args[1] != "") {
				if(args[1].equalsIgnoreCase("w")) {
					wireframe = true;
				}
			}
    	}catch(Exception e) {
    		e.printStackTrace();
    		System.out.println("Kein Modell geladen. lambo.obj wird geladen");
    	}
		
		Create();	
		Update();
		 		
		JFrame jf = new JFrame();	
		jf.setTitle("3D Test");
		jf.setSize(this.width, this.height);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setResizable(false);
		jf.add(this);
		jf.setVisible(true);
		
		timer.start();
	}
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		this.setBackground(Color.DARK_GRAY);
		
		Graphics2D g2D = (Graphics2D) g;
		for(triangle tri : meshProjected.tris) {
			int[] xPoints = new int[3];
			int[] yPoints = new int[3];
			int nPoints = 0;
			while(nPoints < 3) {
				xPoints[nPoints] = (int) meshProjected.vertex.get(tri.v[nPoints]-1).x;
				yPoints[nPoints] = (int) meshProjected.vertex.get(tri.v[nPoints]-1).y;
				nPoints++;
			}
			
			int val = (int) ((tri.col * 235) < 0 ? 0 :(tri.col * 235));
			int ofset = 20;
			g2D.setColor(new Color(ofset + val, ofset + val, ofset + val));
			g2D.fillPolygon(xPoints, yPoints, nPoints);
			if(wireframe == true) {
				g2D.setColor(new Color(0, 0, 0));
				g2D.drawPolygon(xPoints, yPoints, nPoints);
			}
		}
		
		
	}
	
	

	@Override
	public void actionPerformed(ActionEvent e) {
		
    //dTheta += 1.0d/fps ;
		
	if(IsKeyPressed.isSpacePressed()) {
		vCamera.y -= 1.0d * 2.0d/fps; 
	}
	if(IsKeyPressed.isControlPressed()) {
		vCamera.y += 1.0d * 2.0d/fps; 
	}
	if(IsKeyPressed.isAPressed()) {
		vCamera = lib.VectorAdd(vCamera, lib.VectorMul(lib.VectorKreuzProdukt(vLookDir, new vec3D(0, 1, 0)), 0.05d));
	}
	if(IsKeyPressed.isDPressed()) {
		vCamera = lib.VectorSub(vCamera, lib.VectorMul(lib.VectorKreuzProdukt(vLookDir, new vec3D(0, 1, 0)), 0.05d));
	}
	
	if(IsKeyPressed.isQPressed()) {
		dYaw += 1.0d * 0.4d/fps; 
	}
	if(IsKeyPressed.isEPressed()) {
		dYaw -= 1.0d * 0.4d/fps; 
	}
	
	vec3D vForward = lib.VectorMul(vLookDir, 16.0d * 1/fps);
	
	if(IsKeyPressed.isWPressed()) {
		vCamera = lib.VectorAdd(vCamera, vForward);
	}
	if(IsKeyPressed.isSPressed()) {
		vCamera = lib.VectorSub(vCamera, vForward);
	}
	
		
		Update();
		
		repaint();
	
	}
	
}
