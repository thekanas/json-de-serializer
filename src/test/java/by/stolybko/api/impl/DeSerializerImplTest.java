package by.stolybko.api.impl;

import by.stolybko.api.DeSerializer;
import by.stolybko.api.exception.ClassAndJsonMappingException;
import by.stolybko.entity.Order;
import by.stolybko.entity.Store;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class DeSerializerImplTest {

    @Test
    void deSerializingJsonTest() throws JsonProcessingException {
        // given
        String json = """
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
                                    "createDate": "2021-09-30T14:30:00+01:00"
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
        ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();
        objectMapper.configure(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE, false);
        Store expected = objectMapper.readValue(json, Store.class);

        // when
        DeSerializer deSerializer = new DeSerializerImpl();
        Store actual = deSerializer.deSerializingJson(Store.class, json);

        // then
        assertEquals(expected, actual);
    }

    @Test
    void deSerializingJsonShouldTrowClassAndJsonMappingException_whenJsonDoesNotMatchObject() {
        // given
        String json = """
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
                    "!!!!!!!!!!!1": "2021-09-30T15:30:00+01:00"
                }
                            """;
        DeSerializer deSerializer = new DeSerializerImpl();

        // when, then
        var exception = assertThrows(ClassAndJsonMappingException.class, () -> deSerializer.deSerializingJson(Order.class, json));
        assertThat(exception.getMessage())
                .isEqualTo("NoSuchFieldException: !!!!!!!!!!!1");
    }

}