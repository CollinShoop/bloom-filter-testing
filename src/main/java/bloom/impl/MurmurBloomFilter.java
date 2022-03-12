package bloom.impl;

import bloom.BloomFilter;
import bloom.Hashable;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

import java.util.BitSet;

/**
 * In memory implementation of a bloom filter using
 * a seeded Murmur3 hash.
 */
public class MurmurBloomFilter implements BloomFilter {

    private final int size;
    // use a bit set instead of primitives array to store bit data more efficiently
    private final BitSet bitSet;
    private final HashFunction[] hashes;

    public MurmurBloomFilter(int size, int hashCount) {
        this.size = size;
        this.bitSet = new BitSet(size);
        this.hashes = getHashes(hashCount);
    }

    @Override
    public void insert(Hashable h) {
        for (HashFunction hashFn : hashes) {
            long hash = hashFn.hashBytes(h.toBytes()).padToLong();
            bitSet.set((int)(hash%size));
        }
    }

    @Override
    public boolean lookup(Hashable h) {
        for (HashFunction hashFn : hashes) {
            long hash = hashFn.hashBytes(h.toBytes()).padToLong();
            if (!bitSet.get((int)(hash%size))) {
                return false; // short-circuit
            }
        }
        return true;
    }

    private static HashFunction[] getHashes(int numHashes) {
        HashFunction[] hashes = new HashFunction[numHashes];
        for (int i = 0; i < numHashes; i++) {
            hashes[i] = Hashing.murmur3_32_fixed(i);
        }
        return hashes;
    }

}
