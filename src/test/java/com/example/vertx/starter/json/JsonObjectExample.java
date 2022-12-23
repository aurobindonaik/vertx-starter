package com.example.vertx.starter.json;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonObjectExample {
  @Test
  void jsonObjectCanBeMapped() {
    JsonObject myJsonObject = new JsonObject();
    myJsonObject.put("id", 1);
    myJsonObject.put("name", "Alice");
    myJsonObject.put("loves_vertex", true);

    String encodedString = myJsonObject.encode();
    assertEquals("{\"id\":1,\"name\":\"Alice\",\"loves_vertex\":true}", encodedString);

    JsonObject decodedJsonObject = new JsonObject(encodedString);
    assertEquals(myJsonObject, decodedJsonObject);
  }

  @Test
  void jsonObjectCanBeCreatedFromMap() {
    final Map<String, Object> myMap = new HashMap<>();
    myMap.put("id", 1);
    myMap.put("name", "Alice");
    myMap.put("loves_vertex", true);

    final JsonObject asJsonObject = new JsonObject(myMap);
    assertEquals(myMap, asJsonObject.getMap());
    assertEquals(1, asJsonObject.getInteger("id"));
    assertEquals("Alice", asJsonObject.getString("name"));
    assertEquals(true, asJsonObject.getBoolean("loves_vertex"));
  }

  @Test
  void jsonArrayCanBeMapped() {
    JsonArray myJsonArray = new JsonArray();
    myJsonArray
      .add(new JsonObject().put("id", 1))
      .add(new JsonObject().put("id", 2))
      .add(new JsonObject().put("id", 3))
      .add("randomValue")
    ;

    assertEquals("[{\"id\":1},{\"id\":2},{\"id\":3},\"randomValue\"]", myJsonArray.encode());
  }

  @Test
  void canMapJavaObjects() {
    Person alice = new Person(1, "", true);
    JsonObject jsonObject = JsonObject.mapFrom(alice);
    assertEquals(alice.getId(), jsonObject.getInteger("id"));
    assertEquals(alice.getName(), jsonObject.getString("name"));
    assertEquals(alice.isLovesVertx(), jsonObject.getBoolean("lovesVertx"));

    Person mappedPerson = jsonObject.mapTo(Person.class);
    assertEquals(alice.getId(), mappedPerson.getId());
    assertEquals(alice.getName(), mappedPerson.getName());
    assertEquals(alice.isLovesVertx(), mappedPerson.isLovesVertx());
  }
}
