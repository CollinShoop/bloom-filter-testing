package bloom.impl;

import bloom.Hashable;

public class StaticHashable implements Hashable {

    private byte[] bytes;

    public StaticHashable(byte[] bytes) {
        this.bytes = bytes;
    }

    @Override
    public byte[] toBytes() {
        return bytes;
    }

    public static StaticHashable String(String s) {
        return new StaticHashable(s.getBytes());
    }

}
