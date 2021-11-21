import com.mtest.aaa.App;
import com.mtest.aaa.OrderCalculate;
import org.junit.Assert;
import org.junit.Test;

public class AppTest {


    @Test
    public void testNoElement() {
        Assert.assertEquals(false, new OrderCalculate().valid(new String[]{}));
    }
    @Test
    public void testOneElementNoCorr() {
        Assert.assertEquals(false, new OrderCalculate().valid(new String[]{"5--2"}));


    }
    @Test
    public void testOneElement() {
        Assert.assertEquals(true, new OrderCalculate().valid(new String[]{"15-02"}));


    }
    @Test
    public void testOneElementWithCardNoCorr() {
        Assert.assertEquals(false, new OrderCalculate().valid(new String[]{"5-2", "card-145+"}));


    }

    @Test
    public void testOneElementWithCard() {
        Assert.assertEquals(true, new OrderCalculate().valid(new String[]{"5-2", "card-145"}));


    }

    @Test
    public void testOnlyCard() {
        Assert.assertEquals(false, new OrderCalculate().valid(new String[]{"card-145"}));


    }


    @Test
    public void testTwoElement() {
        Assert.assertEquals(true, new OrderCalculate().valid(new String[]{"5-2", "2-1"}));


    }


    @Test
    public void testTheeElementWithCard() {
        Assert.assertEquals(true, new OrderCalculate().valid(new String[]{"5-2","12-5","123-12","card-145"}));


    }

    @Test
    public void testThreeElement() {
        Assert.assertEquals(false, new OrderCalculate().valid(new String[]{"5-2", "1-2", "2k-15"}));
    }
    @Test
    public void readWithOutCard() {
        Assert.assertEquals(5, new OrderCalculate().loadPriceList());
    }
    @Test
    public void readAndParseWithCard() {
        OrderCalculate orcalc =new OrderCalculate();
        String[] s =new String[]{"5-2", "1-2", "card-15"};
        orcalc.valid(s);
        Assert.assertEquals(2, orcalc.parse(s));
    }
    @Test
    public void calculate1() {
        OrderCalculate orcalc =new OrderCalculate();
        String[] s =new String[]{"5-2", "1-6"};
        orcalc.valid(s);
        orcalc.loadPriceList();
        orcalc.parse(s);
        orcalc.fillingLines();
        orcalc.result();
        Assert.assertEquals(2, orcalc.parse(s));
    }
}
