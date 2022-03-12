package bloom;

public interface BloomFilter {

    void insert(Hashable h);

    boolean lookup(Hashable h);

}
