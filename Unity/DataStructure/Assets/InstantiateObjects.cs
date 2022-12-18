using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEditor;

public class InstantiateObjects : MonoBehaviour
{
    public Vector3 numObjects;
    public Vector3 distance;
    public GameObject prefab;

    [ContextMenu("InstantiatePrefab")]
    void InstantiatePrefab()
    {
        for (int i = 0; i < numObjects.x; i++)
        {
            for (int j = 0; j < numObjects.y; j++)
            {
                for (int k = 0; k < numObjects.z; k++)
                {
                    GameObject.Instantiate(prefab,
                        new Vector3(i * distance.x, j * distance.y, k * distance.z),
                        Quaternion.identity, gameObject.transform);
                }
            }
        }
    }

    // Start is called before the first frame update
    void Start()
    {
        
    }

    // Update is called once per frame
    void Update()
    {
        
    }
}
