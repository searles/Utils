package at.searles.commons.strings;

import at.searles.commons.strings.CharComparators;
import at.searles.commons.strings.NaturalStringComparator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

public class StringTest {

    private NaturalStringComparator cmp;
    private String[] strings;

    @Before
    public void setUp() {
        cmp = new NaturalStringComparator(CharComparators.CASE_SENSITIVE);
    }

    @Test
    public void testStringOrder() {
        withStrings(new String[]{ "0", "00", "", "aa", "a0", "a", "a01", "a01.b", "a01.a1", "a10", "a1", "a2", "b"});

        sortStrings();

        assertOrder(new String[]{ "", "a", "aa", "a0", "a1", "a01", "a01.a1", "a01.b", "a2", "a10", "b", "0", "00"});
    }

    private void assertOrder(String[] order) {
        for(int i = 0; i < Math.max(strings.length, order.length); ++i) {
            Assert.assertEquals(order[i], strings[i]);
        }
    }

    private void sortStrings() {
        Arrays.sort(strings, cmp);
    }

    private void withStrings(String[] strings) {
        this.strings = strings;
    }
}
