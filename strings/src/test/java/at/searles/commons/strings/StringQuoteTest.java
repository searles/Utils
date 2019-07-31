package at.searles.commons.strings;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class StringQuoteTest {

    @Test
    public void quote() {
        Assert.assertEquals("\"abc\"", StringQuote.quote("abc"));
        Assert.assertEquals("\"\\\"\"", StringQuote.quote("\""));
        Assert.assertEquals("\"\\\\\"", StringQuote.quote("\\"));
        Assert.assertEquals("\"\\n\"", StringQuote.quote("\n"));
        Assert.assertEquals("\"\\xff\"", StringQuote.quote("\u00ff"));
        Assert.assertEquals("\"\\U00010437\"", StringQuote.quote("\uD801\uDC37")); // U+10437
    }

    @Test
    public void unquote() {
        Assert.assertEquals("abc", StringQuote.unquote("\"abc\""));
        Assert.assertEquals("\"", StringQuote.unquote("\"\\\"\""));
        Assert.assertEquals("\\", StringQuote.unquote("\"\\\\\""));
        Assert.assertEquals("\n", StringQuote.unquote("\"\\n\""));
        Assert.assertEquals("\u00ff", StringQuote.unquote("\"\\xff\""));
        Assert.assertEquals("\uD801\uDC37", StringQuote.unquote("\"\\U00010437\"")); // U+10437
    }
}