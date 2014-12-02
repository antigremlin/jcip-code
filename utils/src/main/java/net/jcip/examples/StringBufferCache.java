package net.jcip.examples;

import static java.lang.ThreadLocal.withInitial;

public class StringBufferCache {
    private static ThreadLocal<StringBuilder> strBuilder = new ThreadLocal<StringBuilder>() {
        @Override protected StringBuilder initialValue() {
            return new StringBuilder(128);
        }
    };

    private static ThreadLocal<StringBuilder> strBuilder2 =
            withInitial(() -> new StringBuilder(128));

    private static ThreadLocal<StringBuilder> strBuilder3 =
            withInitial(StringBufferCache::newStringBuilder);
    private static StringBuilder newStringBuilder() {
        return new StringBuilder(128);
    }

    private final StringBuilder buf = new StringBuilder(128);

    public String toString() {
        StringBuilder buf = strBuilder.get();
        buf.append("foo bar baz");
        String result = buf.toString();
        buf.delete(0, buf.length());
        return result;
    }

    public String simpleToString() {
        StringBuilder buf = new StringBuilder(128);
        buf.append("foo bar baz");
        return buf.toString();
    }

    public String nonSafeToString() {
        buf.delete(0, buf.length());
        buf.append("foo bar baz");
        return buf.toString();
    }

}
