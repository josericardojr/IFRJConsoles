// FrustumG.h: Geometric version of Frustum Culling 
//
//////////////////////////////////////////////////////////////////////


#ifndef _FRUSTUMR_
#define _FRUSTUMR_

#ifndef _VEC3_
#include "Vec3.h"
#endif

class Vec3;

//#ifndef _MAT4X4_
//#include "Mat4x4.h"
//#endif

//class Mat4x4;


#ifndef _PLANE_
#include "Plane.h"
#endif

class Plane;

#ifndef _AABOX_
#include "AABox.h"
#endif

class AABox;




class FrustumR 
{
private:

	enum {
		TOP = 0,
		BOTTOM,
		LEFT,
		RIGHT,
		NEARP,
		FARP
	};


public:

	static enum {OUTSIDE, INTERSECT, INSIDE};

	Plane pl[6];


	Vec3 ntl,ntr,nbl,nbr,ftl,ftr,fbl,fbr,X,Y,Z,camPos;
	float nearD, farD, ratio, angle;
	float sphereFactorX, sphereFactorY;
	double tang;
	double nw,nh,fw,fh;

	FrustumR();
	~FrustumR();

	void setFrustum(float *m);
	void setCamInternals(float angle, float radius, float nearD, float farD);
	void setCamDef(Vec3 p, Vec3 l, Vec3 u);
	int pointInFrustum(Vec3 &p);
	int sphereInFrustum(Vec3 &p, float radius);
	int boxInFrustum(AABox &b);

	void drawPoints();
	void drawLines();
	void drawPlanes();
	void drawNormals();

	void printPlanes();

};


#endif
