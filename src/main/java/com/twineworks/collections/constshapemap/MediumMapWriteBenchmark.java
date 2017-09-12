package com.twineworks.collections.constshapemap;

import com.twineworks.collections.Util;
import com.twineworks.collections.shapemap.ConstShapeMap;
import com.twineworks.collections.shapemap.ShapeKey;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

import java.util.HashMap;

@State(Scope.Thread)
public class MediumMapWriteBenchmark {

  private HashMap<String, String> hashMap;
  private ConstShapeMap<String> constShapeMap;

  private ConstShapeMap.Accessor<String> a;
  private ShapeKey k;

  @Setup
  public void setup() {

    // sets up a ShapeMap and a HashMap with 100 keys each
    //
    // {
    //   :k0 -> v0
    //   :k1 -> v1
    //   ...
    //   :k99 -> v99
    // }
    //
    // Benchmarks access key 42


    // HashMap
    hashMap = Util.hashMapRange(100);

    // ShapeMap
    constShapeMap = new ConstShapeMap<>(hashMap);

    k = ShapeKey.get("k42");
    a = ConstShapeMap.accessor(k);

    // learn the location once
    a.get(constShapeMap);

  }

  @Benchmark
  public String hash_map__put() {
    return hashMap.put("k42", "vn1");
  }

  @Benchmark
  public String const_shape_map__put(){
    return constShapeMap.put(k, "vn1");
  }

  @Benchmark
  public String const_shape_map_accessor__put(){
    return a.put(constShapeMap, "vn1");
  }

  @Benchmark
  public String const_shape_map__puta(){
    return constShapeMap.puta(a, "vn1");
  }

  @Benchmark
  public Object const_shape_map_accessor__set(){
    a.set(constShapeMap, "vn1");
    return a;
  }

  @Benchmark
  public Object const_shape_map__seta(){
    constShapeMap.seta(a, "vn1");
    return a;
  }

}

