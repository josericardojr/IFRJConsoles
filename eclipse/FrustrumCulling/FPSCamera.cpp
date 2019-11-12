//
//  FPSCamera.cpp
//  OpenGLSamples
//
//  Created by José Ricardo da Silva  Júnior on 04/01/17.
//  Copyright © 2017 José Ricardo da Silva  Júnior. All rights reserved.
//

#include "FPSCamera.hpp"

FPSCamera::FPSCamera(){
    pos = Vec3(0, 0, 50);
    up = Vec3(0, 1, 0);
    direction = Vec3(0, 0, 1);
    
    m_yaw = 90;
    m_pitch = 0;
    
    update();
}


void FPSCamera::apply(){

    Vec3 target = getTarget();
    // Reset the coordinate system before modifying
    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();
    
    // Set the correct perspective.
    gluPerspective(m_angle,m_ratio,m_near,m_far);
    
    
    glMatrixMode(GL_MODELVIEW);
    glLoadIdentity();
    
    //fprintf(stderr, "\nx: %f - y: %f - z: %f", direction.x, direction.y, direction.z);
    gluLookAt(pos.x, pos.y, pos.z,
              target.x, target.y, target.z,
              up.x, up.y, up.z);
    
    //fprintf(stderr, "\npx: %f - py: %f - pz: %f", pos.x, pos.y, pos.z);
    //fprintf(stderr, "\ntx: %f - ty: %f - tz: %f", target.x, target.y, target.z);
}

void FPSCamera::move(float delta){
    pos = pos + (direction * delta);
    
}

Vec3 FPSCamera::getTarget(){
    Vec3 target;
    target.x = pos.x - (direction.x * 10);
    target.y = pos.y - (direction.y * 10);
    target.z = pos.z - (direction.z * 10);
 
    return target;
}

void FPSCamera::yaw(float _delta){
    m_yaw += _delta;
    update();
}

void FPSCamera::pitch(float delta){
    m_pitch += delta;
    
    if (m_pitch > 45) m_pitch = 45;
    if (m_pitch < -45) m_pitch = -45;
   
    update();
}

void FPSCamera::strafe(float delta){
    pos.x += strafeAxis.x * delta;
    pos.z += strafeAxis.z * delta;
}

void FPSCamera::setAngle(float angle){
    m_angle = angle;
}

void FPSCamera::setPlanes(float near, float far){
    m_near = near;
    m_far = far;
}

void FPSCamera::setWSize(int w, int h){
    // Prevent a divide by zero, when window is too short
    // (you cant make a window of zero width).
    if(h == 0)
        h = 1;
    
    m_ratio = w / h;
}

void FPSCamera::update(){
    direction.x = cosf(m_yaw * (M_PI / 180.0f)) * cosf(m_pitch * (M_PI / 180.0f));
    direction.y = sinf(m_pitch * (M_PI / 180.0f));
    direction.z = sinf(m_yaw * (M_PI / 180.0f)) * cosf(m_pitch * (M_PI / 180.0f));
    direction.normalize();
    
    Vec3 _up = Vec3(0, 1, 0);
    strafeAxis = _up * direction;
    strafeAxis.normalize();
    
    up = direction * strafeAxis;
}
