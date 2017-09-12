package com.twineworks.collections.shapemap;

import com.twineworks.collections.Util;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

import java.util.HashMap;

@State(Scope.Thread)
public class SmallMapReadBenchmark {

  private HashMap<String, String> hashMap;
  private ShapeMap<String> shapeMap;

  private ShapeMap.Accessor<String> a;
  private ShapeKey k;

  @Setup
  public void setup() {

    // sets up a ShapeMap and a HashMap with 2 keys each
    //
    // {
    //   :k0 -> v0
    //   :k1 -> v1
    // }
    //
    // Benchmarks access key 0


    // HashMap
    hashMap = Util.hashMapRange(2);

    // ShapeMap
    shapeMap = new ShapeMap<>(hashMap);

    k = ShapeKey.get("k0");
    a = ShapeMap.accessor(k);

    // learn the location once
    a.get(shapeMap);

  }

  @Benchmark
  public String hash_map__get() {
    return hashMap.get("k0");
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

