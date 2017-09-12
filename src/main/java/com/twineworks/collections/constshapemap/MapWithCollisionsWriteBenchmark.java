package com.twineworks.collections.constshapemap;

import com.twineworks.collections.shapemap.ConstShapeMap;
import com.twineworks.collections.shapemap.ShapeKey;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

import java.util.HashMap;

@State(Scope.Thread)
public class MapWithCollisionsWriteBenchmark {

  private HashMap<String, String> hashMap;
  private ConstShapeMap<String> constShapeMap;

  private ConstShapeMap.Accessor<String> a1, a2;
  private ShapeKey k1, k2;

  @Setup
  public void setup() {

    // sets up a ShapeMap and a HashMap with 2 keys each, the keys have the same hashCode
    // and are expected to give inferior performance in regular HashMaps
    //
    // {
    //   :FB -> v1
    //   :Ea -> v2
    // }
    //

    // ShapeMap
    k1 = ShapeKey.get("FB");
    k2 = ShapeKey.get("Ea");

    constShapeMap = new ConstShapeMap<>(k1, k2);

    a1 = ConstShapeMap.accessor(k1);
    a2 = ConstShapeMap.accessor(k2);

    a1.put(constShapeMap, "v1");
    a2.put(constShapeMap, "v2");

    // HashMap
    hashMap = new HashMap<>(16);
    hashMap.put("FB", "v1");
    hashMap.put("Ea", "v2");

    if (k1.hashCode() != k2.hashCode()) throw new AssertionError("Keys are expected to have same hash codes");
    if ("FB".hashCode() != "Ea".hashCode()) throw new AssertionError("Keys are expected to have same hash codes");

  }

  @Benchmark
  public String hash_map__put() {
    return hashMap.put("Ea", "vn1");
  }

  @Benchmark
  public String const_shape_map__put(){
    return constShapeMap.put(k2, "vn1");
  }

  @Benchmark
  public String const_shape_map_accessor__put(){
    return a2.put(constShapeMap, "vn1");
  }

  @Benchmark
  public String const_shape_map__puta(){
    return constShapeMap.puta(a2, "vn1");
  }

  @Benchmark
  public Object const_shape_map_accessor__set(){
    a2.set(constShapeMap, "vn1");
    return a2;
  }

  @Benchmark
  public Object const_shape_map__seta(){
    constShapeMap.seta(a2, "vn1");
    return a2;
  }

}

