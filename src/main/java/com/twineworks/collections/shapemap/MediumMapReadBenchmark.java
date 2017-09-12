package com.twineworks.collections.shapemap;

import com.twineworks.collections.Util;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

import java.util.HashMap;

@State(Scope.Thread)
public class MediumMapReadBenchmark {

  private HashMap<String, String> hashMap;
  private ShapeMap<String> shapeMap;

  private ShapeMap.Accessor<String> a;
  private ShapeKey k;

  @Setup
  public void setup() {

    // sets up a ShapeMap and a HashMap with 100 keys each
    //
    // {
    //   :k0 -> v0
    //   :k1 -> v1
    //   ...
    //   :k99-> v99
    // }
    //
    // Benchmarks access key 42


    // HashMap
    hashMap = Util.hashMapRange(100);

    // ShapeMap
    shapeMap = new ShapeMap<>(hashMap);

    k = ShapeKey.get("k42");
    a = ShapeMap.accessor(k);

    // learn the location once
    a.get(shapeMap);

  }

  @Benchmark
  public String hash_map__get() {
    return hashMap.get("k42");
  }

  @Benchmark
  public String shape_map__get(){
    return shapeMap.get(k);
  }

  @Benchmark
  public String shape_map_accessor__get(){
    return a.get(shapeMap);
  }

  @Benchmark
  public String shape_map__geta(){
    return shapeMap.geta(a);
  }

}

