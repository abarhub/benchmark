# benchmark

Résultat :
```shell
> just benchall 1000
cd .\java ; just bench multi 1000
hyperfine --warmup 3 "java -cp target/classes com.exemple.HelloWorld multi 1000"
Benchmark 1: java -cp target/classes com.exemple.HelloWorld multi 1000
  Time (mean ± σ):      1.217 s ±  0.141 s    [User: 1.046 s, System: 0.147 s]
  Range (min … max):    1.060 s …  1.470 s    10 runs

cd .\java ; just bench multithread 1000
hyperfine --warmup 3 "java -cp target/classes com.exemple.HelloWorld multithread 1000"
Benchmark 1: java -cp target/classes com.exemple.HelloWorld multithread 1000
  Time (mean ± σ):     846.3 ms ±  53.3 ms    [User: 11112.5 ms, System: 1274.7 ms]
  Range (min … max):   746.6 ms … 932.1 ms    10 runs

cd .\rust ; just benchopt 1000
hyperfine --warmup 3 ".\target\release\exemple  1000"
Benchmark 1: .\target\release\exemple  1000
  Time (mean ± σ):     863.1 ms ±  36.6 ms    [User: 811.2 ms, System: 31.5 ms]
  Range (min … max):   825.9 ms … 950.0 ms    10 runs

cd .\python ; just bench 1000
hyperfine --warmup 3 "uv run main.py 1000"
Benchmark 1: uv run main.py 1000
  Time (mean ± σ):     342.9 ms ±  15.6 ms    [User: 332.8 ms, System: 147.2 ms]
  Range (min … max):   320.5 ms … 364.8 ms    10 runs

cd .\javascript ; just bench 1000
hyperfine --warmup 3 "node index.js 1000"
Benchmark 1: node index.js 1000
  Time (mean ± σ):     13.532 s ±  0.367 s    [User: 12.786 s, System: 0.341 s]
  Range (min … max):   12.984 s … 13.997 s    10 runs

cd .\go ; just bench multi 1000
hyperfine ".\hello  multi 1000"
Benchmark 1: .\hello  multi 1000
  Time (mean ± σ):      2.651 s ±  0.154 s    [User: 2.363 s, System: 0.232 s]
  Range (min … max):    2.512 s …  2.937 s    10 runs

cd .\go ; just bench multithread 1000
hyperfine ".\hello  multithread 1000"
Benchmark 1: .\hello  multithread 1000
  Time (mean ± σ):     384.8 ms ±   9.9 ms    [User: 4686.2 ms, System: 915.9 ms]
  Range (min … max):   372.5 ms … 400.4 ms    10 runs
```  

