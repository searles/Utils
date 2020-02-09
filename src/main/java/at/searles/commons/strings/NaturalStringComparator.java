package at.searles.commons.strings;

import java.util.Comparator;

public class NaturalStringComparator implements Comparator<String> {

    private final CharComparator charCmp;

    public NaturalStringComparator(CharComparator charCmp) {
        this.charCmp = charCmp;
    }

    private static boolean isNum(String s, int index) {
        return index < s.length() && '0' <= s.charAt(index) && s.charAt(index) <= '9';
    }

    public int compare(String s0, String s1) {
        // on return, i is always the length of the common prefix.
        int i = 0;

        int minlen = Math.min(s0.length(), s1.length());

        while(i < minlen) {
            // find end of current interval. If it is a number
            // it is the whole numeric group.
            if(isNum(s0, i) || isNum(s1, i)) {
                int l0 = numGroupLength(s0, i);
                int l1 = numGroupLength(s1, i);

                int cmp = compareTrailingZero(s0, s1, i, l0, l1);

                if(cmp != 0) {
                    return cmp;
                }

                if(l0 != l1) {
                    // length of numeric part is different (eg 02 vs 2)
                    return l0 - l1;
                }

                i += l0; // l0 == l1
            } else {
                int cmp = compareCharAt(s0, s1, i);

                if(cmp != 0) {
                    return cmp;
                }

                i++;
            }
        }

        return s0.length() - s1.length();
    }

    private static int minLength(String...strings) {
        int minLen = strings[0].length();

        for(int i = 1; i < strings.length; ++i) {
            if(strings[i].length() < minLen) {
                minLen = strings[i].length();
            }
        }

        return minLen;
    }

    public int prefixLen(String...strings) {
        // on return, i is always the length of the common prefix.
        int i = 0;

        int minlen = minLength(strings);

        while(i < minlen) {
            // find end of current interval. If it is a number
            // it is the whole numeric group.
            int len = nextPrefixSegmentLength(i, strings);

            if(len == 0) break;

            i += len;
        }

        return i;
    }

    private int nextPrefixSegmentLength(int offset, String... strings) {
        if(hasNumGroupAt(offset, strings)) {
            int l0 = numGroupLength(strings[0], offset);

            for(int m = 1; m < strings.length; ++m) {
                int lm = numGroupLength(strings[m], offset);

                int cmp = compareTrailingZero(strings[0], strings[m], offset, l0, lm);

                if(cmp != 0 || l0 != lm) {
                    return 0;
                }
            }

            return l0; // a number of length l0 is equal in all strings
        }

        for(int m = 1; m < strings.length; ++m) {
            if(compareCharAt(strings[0], strings[m], offset) != 0) {
                return 0;
            }
        }

        return 1;
    }

    private static boolean hasNumGroupAt(int offset, String... strings) {
        boolean isNum = false;

        for(int m = 0; m < strings.length && !isNum; ++m) {
            isNum = isNum(strings[m], offset);
        }

        return isNum;
    }

    protected int compareCharAt(String s0, String s1, int offset) {
        return charCmp.compare(s0.charAt(offset), s1.charAt(offset));
    }

    private static int numGroupLength(String s, int offset) {
        int len = 0;

        while(isNum(s, offset + len)) {
            len++;
        }
        return len;
    }

    private static int compareTrailingZero(String s0, String s1, int offset, int len0, int len1) {
        for(int k = Math.max(len0, len1); k > 0; k--) {
            char ch0 = k > len0 ? '0' : s0.charAt(offset + len0 - k);
            char ch1 = k > len1 ? '0' : s1.charAt(offset + len1 - k);

            if(ch0 != ch1) {
                // numeric value is different, eg 1 vs 2
                return ch0 - ch1;
            }
        }

        return 0;
    }
}
