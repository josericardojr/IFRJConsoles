//
//  FPSCamera.hpp
//  OpenGLSamples
//
//  Created by José Ricardo da Silva  Júnior on 04/01/17.
//  Copyright © 2017 José Ricardo da Silva  Júnior. All rights reserved.
//

#ifndef FPSCamera_hpp
#define FPSCamera_hpp

#ifdef __APPLE__
#include <GLUT/glut.h>
#else
#include <GL/glut.h>
#endif

#include <stdio.h>
#include <math.h>
#include "Vec3.h"


class FPSCamera {
private:
    float m_yaw, m_pitch;
    float m_angle, m_near, m_far, m_ratio;
    
    Vec3 pos;
    Vec3 direction;
    Vec3 up;
    Vec3 strafeAxis;
    
    
private:
    void update();
    
public:
    FPSCamera();
    
    void setAngle(float angle);
    void setPlanes(float near, float far);
    Vec3 getPos(){ return pos; }
    Vec3 getDir(){ return direction; }
    Vec3 getUp(){ return up; }
    Vec3 getTarget();
    float getRatio(){ return m_ratio; }
    float getAngle(){ return m_angle; }
    float getNear(){ return m_near; }
    float getFar(){ return m_far; }
    
    void apply();
    void setWSize(int w, int h);
    void move(float delta);
    void yaw(float delta);
    void pitch(float delta);
    void strafe(float delta);
};

#endif /* FPSCamera_hpp */
