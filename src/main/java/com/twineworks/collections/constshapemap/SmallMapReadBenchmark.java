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
public class SmallMapReadBenchmark {

  private HashMap<String, String> hashMap;
  private ConstShapeMap<String> constShapeMap;

  private ConstShapeMap.Accessor<String> a;
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
    constShapeMap = new ConstShapeMap<>(hashMap);

    k = ShapeKey.get("k0");
    a = ConstShapeMap.accessor(k);

    // learn the location once
    a.get(constShapeMap);

  }

  @Benchmark
  public String hash_map__get() {
    return hashMap.get("k0");
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

