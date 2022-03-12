import bloom.BloomFilter;
import bloom.impl.MurmurBloomFilter;
import bloom.impl.StaticHashable;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final int BENCH_WIDTH_FACTOR = 10;
    private static final int BENCH_MAX_NUM_HASHES = 10;

    private static final int COLUMN_WIDTH = 15;

    private static final String INPUTS_FILE_NAME = "words.txt";
    public static void main(String[] args) {
        final List<String> insertsData = readInsertsData(INPUTS_FILE_NAME);

        // column headers
        System.out.printf("%" + COLUMN_WIDTH + "s", "");
        for (int j = 0; j < BENCH_MAX_NUM_HASHES; j++) {
            System.out.printf("%" + COLUMN_WIDTH + "s", j+1);
        }
        System.out.println();

        int width = insertsData.size();
        for (int i = 0; i < BENCH_WIDTH_FACTOR; i++, width *= 2) {
            System.out.printf("%," + COLUMN_WIDTH + "d", width);
            for (int hashCount = 1; hashCount <= BENCH_MAX_NUM_HASHES; hashCount++) {
                BenchResult results = bench(insertsData, width, hashCount);
                // false-positive percentage
                double fpp = (results.falsePositiveCount * 100.0 / results.negativeTestCount);
                System.out.printf("%" + COLUMN_WIDTH + ".3f", fpp);
            }
            System.out.println();
        }
    }

    private static class BenchResult {
        private int bloomWidth;
        private int bloomHashes;
        private int positiveTestCount; // number of times a known to exist element was looked up
        private int negativeTestCount; // number of times a known to be missing element was looked up
        private int falsePositiveCount; // number of times a known to be missing element falsely results in a hit
        private int falseNegativeCount; // number of times a known to exist element falsely results in a miss
    }

    private static BenchResult bench(List<String> insertsData, int bloomWidth, int bloomHashCount) {
        BenchResult results = new BenchResult();
        BloomFilter bloomFilter = new MurmurBloomFilter(bloomWidth, bloomHashCount);
        results.bloomWidth = bloomWidth;
        results.bloomHashes = bloomHashCount;
        for (String s : insertsData) {
            bloomFilter.insert(StaticHashable.String(s));
        }

        for (String s : insertsData) {
            // lookup an element that is known to exist
            boolean contains = bloomFilter.lookup(StaticHashable.String(s));
            results.positiveTestCount++;
            if (!contains) {
                results.falseNegativeCount++;
            }

            // lookup an element that is known to not exist
            contains = bloomFilter.lookup(StaticHashable.String(s + Math.random()));
            results.negativeTestCount++;
            if (contains) {
                results.falsePositiveCount++;
            }
        }
        return results;
    }

    private static List<String> readInsertsData(String fileName) {
        // Load insertion data
        final List<String> insertSet = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(fileName))) {
            while (scanner.hasNextLine()) {
                insertSet.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Unable to load inserts data", e);
        }
        return insertSet;
    }

}
