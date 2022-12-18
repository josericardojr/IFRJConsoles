using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Main : MonoBehaviour
{ 
   
    public float staticSphereRadius;
    public float dynamicSphereRadiusMin;
    public float dynamicSphereRadiusMax;
    public Transform[] staticSpheres;    
    public Vector3 minSpace;
    public Vector3 maxSpace;
    public int interactions = 100;
    float totalTime = 0;
    int currentInteraction = 0;

    public float minSpeed = 10;
    public float maxSpeed = 20;
    public int numParticles = 10;
    public bool useGPU = false;


    // Struct to keep particles info
    struct Sphere
    {
        public float radius;
        public Vector3 position;
        public float speed;
        public Vector3 direction;
    }

    Sphere[] bufferDynamicSpheres;
    Sphere[] bufferStaticSpheres;
    Transform[] dynamicSpheres;


    // Start is called before the first frame update
    void Start()
    {
        bufferDynamicSpheres = new Sphere[numParticles];
        bufferStaticSpheres = new Sphere[staticSpheres.Length];
        dynamicSpheres = new Transform[numParticles];

        ParticleSpawn();

        // Add the dynamic particles to a buffer in order to process with the physics
        for (int i = 0; i < numParticles; i++)
        {
            float radius = Random.Range(dynamicSphereRadiusMin, dynamicSphereRadiusMax);
            bufferDynamicSpheres[i] = new Sphere();
            bufferDynamicSpheres[i].radius = radius;
            bufferDynamicSpheres[i].speed = Random.Range(minSpeed, maxSpeed);
            bufferDynamicSpheres[i].position = dynamicSpheres[i].position;
            bufferDynamicSpheres[i].direction = dynamicSpheres[i].rotation * Vector3.forward;
            dynamicSpheres[i].localScale = new Vector3(radius, radius, radius);
        }

        // Add the static particles to a buffer in order to process with the physics
        for (int i = 0; i < staticSpheres.Length; i++)
        {
            bufferStaticSpheres[i] = new Sphere();
            bufferStaticSpheres[i].radius = staticSphereRadius;
            bufferStaticSpheres[i].position = staticSpheres[i].position;
        }


        if (useGPU)
        {
            // Initialize GPU Data
        }

    }

    // Update is called once per frame
    void Update()
    {
        float begin = Time.realtimeSinceStartup;

        if (useGPU)
        {
            // Perform the simulation in GPU
            GPUSim();
        }
        else
        {
            // Perform the simulation in CPU
            CPUSim();
        }


        totalTime += Time.realtimeSinceStartup - begin;
        currentInteraction++;

        if (currentInteraction == interactions)
        {
            float _mean = totalTime / interactions;
            Debug.Log("Mean (ms): " + _mean);
        }

    }

    private void CPUSim()
    {
        for (int i = 0; i < dynamicSpheres.Length; i++)
        {
            Transform _ds = dynamicSpheres[i];
            _ds.Translate(new Vector3(0, 0, bufferDynamicSpheres[i].speed * Time.deltaTime));

            // Check collision against walls
            if (_ds.position.x - bufferDynamicSpheres[i].radius < minSpace.x || _ds.position.z - bufferDynamicSpheres[i].radius < minSpace.z ||
                _ds.position.x + bufferDynamicSpheres[i].radius > maxSpace.x || dynamicSpheres[i].position.z + bufferDynamicSpheres[i].radius > maxSpace.z)
            {
                dynamicSpheres[i].rotation = Quaternion.LookRotation(Vector3.Normalize(-dynamicSpheres[i].forward));
                dynamicSpheres[i].Rotate(Vector3.up, Random.Range(0f, 7f));
                dynamicSpheres[i].Translate(new Vector3(0, 0, 0.5f));
            }

            // Check collision with static spheres
            foreach (Transform ss in staticSpheres)
            {
                Vector3 dir = dynamicSpheres[i].transform.position - ss.transform.position;
                dir.y = 0;
                float mag = Vector3.SqrMagnitude(dir);

                if (mag < staticSphereRadius + bufferDynamicSpheres[i].radius)
                {
                    dynamicSpheres[i].rotation = Quaternion.LookRotation(Vector3.Normalize(dir));
                    dynamicSpheres[i].Translate(new Vector3(0, 0, staticSphereRadius + bufferDynamicSpheres[i].radius - mag));
                    break;
                }
            }

        }
    }

    private void GPUSim()
    {
        // Complete the simulation code in GPU

    }

    private void OnDestroy()
    {
        if (useGPU)
        {
            // Release GPU Data
        }
    }

    // Particle spawner
    private void ParticleSpawn()
    {        
        int spanwedParticles = 0;
        float currentZ = minSpace.z + dynamicSphereRadiusMax * 4;
        float currentX = minSpace.x + dynamicSphereRadiusMax * 4;

        while (spanwedParticles < numParticles) 
        {
            GameObject _sp = GameObject.CreatePrimitive(PrimitiveType.Sphere);            
            _sp.transform.Rotate(Vector3.up, Random.Range(0f, 360f));
            _sp.GetComponent<Renderer>().material.color = Random.ColorHSV();
            _sp.transform.position = new Vector3(currentX, 0, currentZ);
            dynamicSpheres[spanwedParticles] = _sp.transform;            

            currentX += dynamicSphereRadiusMax * 4;

            if (currentX > maxSpace.x - dynamicSphereRadiusMax * 4)
            {
                currentX = minSpace.x + dynamicSphereRadiusMax * 4;
                currentZ += dynamicSphereRadiusMax * 4;
            }
            
            spanwedParticles++;
        }
    }
}
