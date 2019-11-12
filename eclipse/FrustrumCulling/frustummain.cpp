/* ------------------------------------------------------

 View Frustum Culling - Lighthouse3D

  -----------------------------------------------------*/


#include <SDL/SDL.h>

#ifdef __APPLE__
#include <OpenGL/OpenGL.h>
#include <OpenGL/glu.h>
#else
#include <GL/gl.h>
#include <GL/glu.h>
#endif


#include "Vec3.h"
#include "FrustumR.h"
#include "FPSCamera.hpp"

#include <stdio.h>
#include <stdlib.h>

float a = 0;

float nearP = 1.0f, farP = 100.0f;
float angle = 45, ratio=1;

int frame=0, timebase=0;

int frustumOn = 1;
int spheresDrawn = 0;
int spheresTotal = 0;
int width = 800;
int height = 600;

enum eKeys {
    LEFT,
    TOP,
    RIGHT,
    BOTTOM
};

bool keys[] = {false, false, false, false};


FrustumR frustum;
FPSCamera camera;
GLUquadric *quadric;





void render() {

	glColor3f(0,1,0);
	spheresTotal=0;
	spheresDrawn=0;

	for (int i = -300; i < 300; i+=4)
			for(int k =  -300; k < 300; k+=4) {
				spheresTotal++;
				Vec3 a(i,0,k);
				if (!frustumOn || (frustum.sphereInFrustum(a,0.5) != FrustumR::OUTSIDE)) {
					glPushMatrix();
					glTranslatef(i,0,k);
                    gluSphere(quadric, 0.5, 5, 5);
					glPopMatrix();
					spheresDrawn++;
				}
			}
    
}



float fps;

void renderScene(void) {

	char title[80];
	float time;


    glEnable(GL_LIGHTING);
	glClearColor(0.0f,0.0f,0.0f,0.0f);
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

    camera.apply();

	frustum.setCamDef(camera.getPos(), camera.getDir(), camera.getUp());

	render();

	frame++;
    time= SDL_GetTicks();
	if (time - timebase > 1000) {
		fps = frame*1000.0/(time-timebase);
		timebase = time;
		frame = 0;
		sprintf(title, "Frustum:%d  Spheres Drawn: %d Total Spheres: %d FPS: %8.2f",
					frustumOn,spheresDrawn,spheresTotal,fps);
        SDL_WM_SetCaption(title, NULL);
	}

    glDisable(GL_LIGHTING);
    glLineWidth(3.0f);
    frustum.drawPlanes();
    
    SDL_GL_SwapBuffers();
}



void handle_key_up(SDL_keysym* keysym){
    switch(keysym->sym) {
            
        case SDLK_w:
            keys[TOP] = false;
            break;
            
        case SDLK_s:
            keys[BOTTOM] = false;
            break;
        case SDLK_d:
            keys[RIGHT] = false;
            break;
            
        case SDLK_a:
            keys[LEFT] = false;
            break;
    }
}

void handle_key_down(SDL_keysym* keysym) {

	switch(keysym->sym) {

		case SDLK_w:
            keys[TOP] = true;
			break;

        case SDLK_s:
            keys[BOTTOM] = true;
			break;
        case SDLK_d:
            keys[RIGHT] = true;
			break;

        case SDLK_a:
            keys[LEFT] = true;
			break;

		/*case 't':
		case 'T':
			v.set(0,0,1);
			p = p - v * 0.1;
			l = l - v * 0.1;
			break;
		case 'g':
		case 'G':
			v.set(0,0,1);
			p = p + v * 0.1;
			l = l + v * 0.1;
			break;

		case 'r':
		case 'R':
			p.set(0,0,5);
			l.set(0,0,0);
			u.set(0,1,0);
			break;*/

        case SDLK_f:
			frustumOn = !frustumOn;
			break;
        case SDLK_ESCAPE:
            gluDeleteQuadric(quadric);
            SDL_Quit();
            exit(-1);
			break;


	}
}

void handle_mouse_motion(SDL_MouseMotionEvent* motion){
    
    static bool first = true;
    
    
    int halfWidth = width / 2;
    int halfHeight = height / 2;
    
    if (first) {
        if (motion->x == halfWidth && motion->y == halfHeight)
            first = false;
        else
            SDL_WarpMouse(width / 2, height / 2);
    } else {
        
        float ydelta = (motion->x- halfWidth) * 0.05f;
        float pdelta = (motion->y - halfHeight) * 0.05f;
    
        camera.yaw(ydelta);
        camera.pitch(pdelta);
        SDL_WarpMouse(halfWidth, halfHeight);
    }
}

void setup_opengl(){
    glEnable(GL_DEPTH_TEST);
    glEnable(GL_CULL_FACE);
    glEnable(GL_LIGHTING);
    glEnable(GL_LIGHT0);
    
    
    quadric = gluNewQuadric();
    
    camera.setWSize(width, height);
    camera.setAngle(angle);
    camera.setPlanes(1.0f, 100.0f);
    frustum.setCamInternals(camera.getAngle(), camera.getRatio(),
                            camera.getNear(), camera.getFar());
}

void process_events(){
    /* Our SDL event placeholder. */
    SDL_Event event;
    
    /* Grab all the events off the queue. */
    while( SDL_PollEvent( &event ) ) {
        
        switch( event.type ) {
            case SDL_KEYDOWN:
                /* Handle key presses. */
                handle_key_down( &event.key.keysym );
                break;
                
            case SDL_KEYUP:
                handle_key_up(&event.key.keysym);
                break;
                
            case SDL_MOUSEMOTION:
                handle_mouse_motion(&event.motion);
                break;
                
            case SDL_VIDEORESIZE:
                camera.setWSize(event.resize.w, event.resize.h);
                // Set the viewport to be the entire window
                glViewport(0, 0, event.resize.w, event.resize.h);
                frustum.setCamInternals(camera.getAngle(), camera.getRatio(),
                                        camera.getNear(), camera.getFar());
                break;
                
            case SDL_QUIT:
                /* Handle quit requests (like Ctrl-c). */
                SDL_Quit();
                exit(-1);
                break;
        }
    }
}

void update(){
    
    if (keys[TOP]) camera.move(-5 * (1.0f/fps));

    if (keys[BOTTOM]) camera.move(5 * (1.0f/fps));
    
    if (keys[RIGHT]) camera.strafe(5 * (1.0f/fps));
    
    if (keys[LEFT]) camera.strafe(-5 * (1.0f/fps));
}
    
int main(int argc, char **argv) {
    
    
    /* Information about the current video settings. */
    const SDL_VideoInfo* info = NULL;
    
    /* Color depth in bits of our window. */
    int bpp = 0;
    /* Flags we will pass into SDL_SetVideoMode. */
    int flags = 0;
    
    /* First, initialize SDL's video subsystem. */
    if( SDL_Init( SDL_INIT_VIDEO ) < 0 ) {
        /* Failed, exit. */
        fprintf( stderr, "Video initialization failed: %s\n",
                SDL_GetError( ) );
        SDL_Quit();
        exit(-1);
    }
    
    /* Let's get some video information. */
    info = SDL_GetVideoInfo( );
    
    if( !info ) {
        /* This should probably never happen. */
        fprintf( stderr, "Video query failed: %s\n",
        SDL_GetError( ) );
        SDL_Quit();
        exit(-1);
    }
    
    /*
     * Set our width/height to 640/480 (you would
     * of course let the user decide this in a normal
     * app). We get the bpp we will request from
     * the display. On X11, VidMode can't change
     * resolution, so this is probably being overly
     * safe. Under Win32, ChangeDisplaySettings
     * can change the bpp.
     */

    bpp = info->vfmt->BitsPerPixel;
    SDL_GL_SetAttribute( SDL_GL_RED_SIZE, 5 );
    SDL_GL_SetAttribute( SDL_GL_GREEN_SIZE, 5 );
    SDL_GL_SetAttribute( SDL_GL_BLUE_SIZE, 5 );
    SDL_GL_SetAttribute( SDL_GL_DEPTH_SIZE, 16 );
    SDL_GL_SetAttribute( SDL_GL_DOUBLEBUFFER, 1 );

    flags = SDL_OPENGL;
    
    if( SDL_SetVideoMode( width, height, bpp, flags ) == 0 ) {
        /*
         * This could happen for a variety of reasons,
         * including DISPLAY not being set, the specified
         * resolution not being available, etc.
         */
        fprintf( stderr, "Video mode set failed: %s\n",
        SDL_GetError( ) );
        SDL_Quit();
        exit(-1);
    }
    
    setup_opengl();

    SDL_EnableKeyRepeat(0, 0);
    SDL_WM_GrabInput(SDL_GRAB_ON);
    printf("Help Screen\n\nW,S,A,D: Pan vertically and Horizontally\nT,G:Move forward/backward\nR: Reset Position\n\nF: Turn frustum On/Off");
    
    while (1) {
        process_events();
        update();
        renderScene();
    }

	return 0;
}

