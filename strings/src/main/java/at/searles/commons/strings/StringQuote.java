package at.searles.commons.strings;

/**
 * Class to quote and unquote strings in C++-style. So,
 * no UTF-16 high-surrogate/low-surrogate.
 */
public class StringQuote {
    public static String quote(String str) {
        StringBuilder sb = new StringBuilder();

        sb.append("\"");

        int cp;

        for(int index = 0; index < str.length(); index += Character.charCount(cp)) {
            cp = str.codePointAt(index);

            if(cp < 32) {
                switch(cp) {
                    case '\n':
                        sb.append("\\n");
                        break;
                    case '\r':
                        sb.append("\\r");
                        break;
                    case '\t':
                        sb.append("\\t");
                        break;
                    default:
                        sb.append(String.format("\\x%2x", cp));
                }
            } else if(cp < 127) {
                switch(cp) {
                    case '\\':
                        sb.append("\\\\");
                        break;
                    case '"':
                        sb.append("\\\"");
                        break;
                    default:
                        sb.appendCodePoint(cp);
                }
            } else if(cp < 0x100){
                sb.append(String.format("\\x%02x", cp));
            } else if(cp < 0x10000){
                sb.append(String.format("\\u%04x", cp));
            } else {
                // no messing around with UTF-16-hs-stuff...
                sb.append(String.format("\\U%08x", cp));
            }
        }

        sb.append("\"");

        return sb.toString();
    }

    /**
     * throws an exception if in bad format.
     */
    public static String unquote(String str) {
        StringBuilder sb = new StringBuilder();
        int end = str.length() - 1; // last char excl.

        for(int index = 1; index < end; ++index) {
            char ch = str.charAt(index);
            if(ch != '\\') {
                sb.append(ch);
            } else {
                index++;
                ch = str.charAt(index);

                switch (ch) {
                    case 'x':
                        index++;
                        sb.append((char) Integer.parseInt(str.substring(index, index + 2), 16));
                        index += 2;
                        break;
                    case 'u':
                        index++;
                        sb.append((char) Integer.parseInt(str.substring(index, index + 4), 16));
                        index += 4;
                        break;
                    case 'U':
                        index++;
                        sb.appendCodePoint(Integer.parseInt(str.substring(index, index + 8), 16));
                        index += 8;
                        break;
                    case 'n': sb.append('\n'); break;
                    case 'r': sb.append('\r'); break;
                    case 't': sb.append('\t'); break;
                    default: sb.append(ch);
                }
            }
        }

        return sb.toString();
    }
}
