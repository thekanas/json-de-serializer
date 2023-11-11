package by.stolybko;

import by.stolybko.api.DeSerializer;
import by.stolybko.api.Serializer;
import by.stolybko.entity.Store;


public class Runner {

static String json = """
{
    "name": "UniverMag",
    "customers": [
        {
            "id": "1024a45d-f272-4189-a58c-fc24f2822bad",
            "firstName": "Reuben1",
            "lastName": "Martin1",
            "dateBirth": "2003-11-03",
            "orders": [
                {
                    "id": "2024a45d-f272-4189-a58c-fc24f2822bad",
                    "products": [
                        {
                            "id": "3024a45d-f272-4189-a58c-fc24f2822bac",
                            "name": "1colbasa",
                            "price": 205.6
                        },
                        {
                            "id": "4024a45d-f272-4189-a58c-fc24f2822bac",
                            "name": "1colbasa",
                            "price": 255.6
                        }
                    ],
                    "createDate": "2021-09-30T15:30:00+01:00"
                }
            ]
        },
        {
            "id": "5024a45d-f272-4189-a58c-fc24f2822bad",
            "firstName": "Reuben2",
            "lastName": "Martin2",
            "dateBirth": "1999-11-03",
            "orders": [
                {
                    "id": "2024a45d-f272-4189-a58c-fc24f2822bad",
                    "products": [
                        {
                            "id": "3024a45d-f272-4189-a58c-fc24f2822bac",
                            "name": "1colbasa",
                            "price": 205.6
                        },
                        {
                            "id": "4024a45d-f272-4189-a58c-fc24f2822bac",
                            "name": "1colbasa",
                            "price": 255.6
                        }
                    ],
                    "createDate": "2021-09-30T15:30:00+01:00"
                }
            ]
        }
    ],
    "products" : [
        {
            "id": "3024a45d-f272-4189-a58c-fc24f2822bac",
            "name": "1colbasa",
            "price": 205.6
        },
        {
            "id": "4024a45d-f272-4189-a58c-fc24f2822bac",
            "name" : "1colbasa",
            "price": 255.6
        }
    ]
}
            """;
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException {

        DeSerializer deSerializer = new DeSerializer();
        Store store = deSerializer.deSerializingJson(Store.class, json);
        System.out.println(store);
        Serializer serializer = new Serializer();
        System.out.println(serializer.serializing(store));

    }
}