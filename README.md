## Bloom Filter showcase

A simple in-memory single threaded bloom filter implementation with some accuracy benchmarking.

The bloom filter uses Guava's Murmur hash as it's quicker than cryptographic hash algorithms.

Console output shows how varying the bloom filter size & number of hashes will roughly affect false-positive lookup rates. 

Example run against a list of ~58k english words. 
```
                             1              2              3              4              5              6              7              8              9             10
         58,110         63.137         74.822         85.897         93.067         97.202         98.522         99.327         99.723         99.883         99.981
        116,220         39.246         39.926         47.028         55.636         65.348         73.729         80.671         86.021         90.344         93.299
        232,440         21.776         15.424         14.698         15.777         18.560         21.803         26.011         31.210         36.593         42.843
        464,880         11.778          4.932          2.972          2.402          2.172          2.112          2.309          2.586          2.884          3.456
        929,760          6.037          1.316          0.530          0.234          0.145          0.115          0.076          0.055          0.038          0.048
      1,859,520          3.027          0.356          0.083          0.019          0.003          0.002          0.000          0.002          0.000          0.000
      3,719,040          1.514          0.083          0.012          0.003          0.000          0.000          0.000          0.000          0.000          0.000
      7,438,080          0.804          0.015          0.000          0.000          0.000          0.000          0.000          0.000          0.000          0.000
     14,876,160          0.391          0.007          0.000          0.000          0.000          0.000          0.000          0.000          0.000          0.000
     29,752,320          0.194          0.000          0.000          0.000          0.000          0.000          0.000          0.000          0.000          0.000
```

## Resources
- [Bloom filter introduction](https://brilliant.org/wiki/bloom-filter/)
- [Java BitSet](https://www.baeldung.com/java-bitset) 
- [List of english words](http://www.mieliestronk.com/wordlist.html)
- [MurmurHash](https://en.wikipedia.org/wiki/MurmurHash)
- [Google/Guava](https://github.com/google/guava)
- [(interesting read) CloudFlare - When bloom filters don't bloom](https://blog.cloudflare.com/when-bloom-filters-dont-bloom/)
