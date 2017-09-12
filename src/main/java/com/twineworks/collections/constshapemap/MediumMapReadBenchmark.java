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
public class MediumMapReadBenchmark {

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
    //   :k99-> v99
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
  public String hash_map__get() {
    return hashMap.get("k42");
  }

  @Benchmark
  public String const_shape_map__get(){
    return constShapeMap.get(k);
  }

  @Benchmark
  public String const_shape_map_accessor__get(){
    return a.get(constShapeMap);
  }

  @Benchmark
  public String const_shape_map__geta(){
    return constShapeMap.geta(a);
  }

}

