using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class RandomColor : MonoBehaviour
{

    struct Cube
    {
        public Vector3 position;
        public Color color;
    }
    
    public int counts = 10;
    public GameObject modelPref;
    public int interactions = 100;
    Cube[] data;
    GameObject[] gameObjects;

    // Start is called before the first frame update
    void Start()
    {
        
    }

    // Update is called once per frame
    void Update()
    {
        
    }

    void OnGUI()
    {
        
    }

    void createCubes()
    {
        data = new Cube[counts * counts];
        gameObjects = new GameObject[counts * counts];

        for (int i = 0; i < counts; i++)
        {
            float offsetX = (-counts / 2 + i);

            for (int k = 0; k < counts; k++)
            {
                float offsetY = (-counts / 2 + k);

                Color _colorInic = Random.ColorHSV();

                GameObject go = GameObject.Instantiate(modelPref, new Vector3(offsetX * 0.6f, 0, offsetY * 0.6f), Quaternion.identity);
                go.GetComponent<MeshRenderer>().material.SetColor("_Color", _colorInic);
                gameObjects[i * counts + k] = go;

                data[i * counts + k] = new Cube();
                data[i * counts + k].position = go.transform.position;
                data[i * counts + k].color = _colorInic;
            }
        }
    }

}
