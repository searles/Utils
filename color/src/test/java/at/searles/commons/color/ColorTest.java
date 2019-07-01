package at.searles.commons.color;

import org.junit.Assert;
import org.junit.Test;

public class ColorTest {
    @Test
    public void hueTest() {
        for(float hue = 0f; hue < 1.f; hue += 0.25f) {


            int color = ColorCommons.color(hue);
            float[] rgba = Colors.int2rgb(color, new float[3]);
            float hue2 = ColorCommons.hue(rgba[0], rgba[1], rgba[2]);

            Assert.assertEquals(hue, hue2, 0.001f);
        }
    }
}
