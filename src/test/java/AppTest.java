import com.mtest.aaa.WorkWithFile;
import com.mtest.aaa.OrderCalculate;
import com.mtest.aaa.Product;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.HashMap;

public class AppTest {


    @Test
    public void testNoElement() {
        OrderCalculate orderCalculate =new OrderCalculate();
        String[] s =new String[]{"5-2", "1-2", "card-15"};
        Assert.assertEquals(true, orderCalculate.validate(s));
        System.out.println(orderCalculate.validMessage);
    }
    @Test
    public void testOneElementNoCorr() {
        OrderCalculate orderCalculate =new OrderCalculate();
        String[] s =new String[]{"1-2","5--2"};
        Assert.assertEquals(false, orderCalculate.validate(s));
        System.out.println(orderCalculate.validMessage);


    }
    @Test
    public void testOneElement() {

        OrderCalculate orderCalculate =new OrderCalculate();
        String[] s =new String[]{"15-02"};
        Assert.assertEquals(true, orderCalculate.validate(s));
        System.out.println(orderCalculate.validMessage);
    }
    @Test
    public void testOneElementWithCardNoCorr() {
        OrderCalculate orderCalculate =new OrderCalculate();
        String[] s =new String[]{"5-2","10-1","card-145+"};
        Assert.assertEquals(false, orderCalculate.validate(s));
        System.out.println(orderCalculate.validMessage);

    }

    @Test
    public void testWithNotInBaseCard() {
        OrderCalculate orderCalculate =new OrderCalculate();
        String[] s =new String[]{"5-2","1-2", "card-1"};
        Assert.assertEquals(true, orderCalculate.validate(s));
        System.out.println(orderCalculate.validMessage);



    }

    @Test
    public void testOnlyCard() {
        OrderCalculate orderCalculate =new OrderCalculate();
        String[] s =new String[]{"card-145"};
        Assert.assertEquals(false, orderCalculate.validate(s));
        System.out.println(orderCalculate.validMessage);


    }


    @Test
    public void testTwoElement() {
        OrderCalculate orderCalculate =new OrderCalculate();
        String[] s =new String[]{"5-2","2-1"};
        Assert.assertEquals(true, orderCalculate.validate(s));
        System.out.println(orderCalculate.validMessage);



    }


    @Test
    public void testTheeElementWithCard() {
        OrderCalculate orderCalculate =new OrderCalculate();
        String[] s =new String[]{"5-2","12-5","123-12","card-145"};
        Assert.assertEquals(true, orderCalculate.validate(s));

        System.out.println(orderCalculate.validMessage);
    }


    @Test
    public void NoFoundFile() {
        WorkWithFile workWithFile=new WorkWithFile();
        Assert.assertEquals(false, workWithFile.loadPriceList("/price6List.txt",new HashMap<Integer, Product>()));
        System.out.println(workWithFile.validFileMessage);

    }
    @Test
    public void readFromFileWhithMistake() {
        WorkWithFile workWithFile=new WorkWithFile();
        Assert.assertEquals(true, workWithFile.loadPriceList("/priceList.txt",new HashMap<Integer, Product>()));
        System.out.println(workWithFile.validFileMessage);
    }
    @Test
    public void readAndParseWithCard() {
        OrderCalculate orderCalculate =new OrderCalculate();
        String[] s =new String[]{"5-2", "1-2", "card-15"};
        orderCalculate.validate(s);
        Assert.assertEquals(2, orderCalculate.parse(s).size());
    }
    @Test
    public void calculate1() {
        OrderCalculate orcalc =new OrderCalculate();
        WorkWithFile workWithFile=new WorkWithFile();
        String[] s =new String[]{"5-2","1-3","10-1","card-1"};
        orcalc.validate(s);
        workWithFile.loadPriceList("/priceList.txt",orcalc.priceList);
        orcalc.linesOfCheck=orcalc.parse(s);
        Assert.assertEquals(280, orcalc.fillingLines(),0.001);
        System.out.println(orcalc.result());

    }
    @Test
    public void calculate2() {
        OrderCalculate orcalc =new OrderCalculate();
        WorkWithFile workWithFile=new WorkWithFile();
        String[] s =new String[]{"5-2","1-6","10-1"};
        orcalc.validate(s);
        workWithFile.loadPriceList("/priceList.txt",orcalc.priceList);
        orcalc.linesOfCheck=orcalc.parse(s);
        Assert.assertEquals(340, orcalc.fillingLines(),0.001);
        System.out.println(orcalc.result());

    }
    @Test
    public void calculate3() {
        WorkWithFile workWithFile=new WorkWithFile();
        OrderCalculate orcalc =new OrderCalculate();
        String[] s =new String[]{"5-2","1-2","10-1"};
        orcalc.validate(s);
        workWithFile.loadPriceList("/priceList.txt",orcalc.priceList);
        orcalc.linesOfCheck=orcalc.parse(s);
        Assert.assertEquals(260, orcalc.fillingLines(),0.001);
        System.out.println(orcalc.result());

    }
    @Test
    public void calculate4() {
        WorkWithFile workWithFile=new WorkWithFile();
        OrderCalculate orcalc =new OrderCalculate();
        String[] s =new String[]{"5-2","7-2","10-1"};
        orcalc.validate(s);
        workWithFile.loadPriceList("/priceList.txt",orcalc.priceList);
        orcalc.linesOfCheck=orcalc.parse(s);
        Assert.assertEquals(220, orcalc.fillingLines(),0.001);
        System.out.println(orcalc.result());

    }
    @Test
    public void calculate5() {
        WorkWithFile workWithFile=new WorkWithFile();
        OrderCalculate orcalc =new OrderCalculate();
        String[] s =new String[]{"8-2","7-2","card-12"};
        orcalc.validate(s);
        workWithFile.loadPriceList("/priceList.txt",orcalc.priceList);
        orcalc.linesOfCheck=orcalc.parse(s);
        Assert.assertEquals(0, orcalc.fillingLines(),0.001);
        System.out.println(orcalc.result());
        File out=new File("check.txt");
        workWithFile.checkToFile(out.getPath(), orcalc.result());

    }
    @Test
    public void calculate6() {
        WorkWithFile workWithFile=new WorkWithFile();
        OrderCalculate orcalc =new OrderCalculate();
        String[] s =new String[]{"5-2","9-2","12-1","1-6","10-1","card-12"};
        orcalc.validate(s);
        workWithFile.loadPriceList("/priceList.txt",orcalc.priceList);
        orcalc.linesOfCheck=orcalc.parse(s);
        Assert.assertEquals(620, orcalc.fillingLines(),0.001);
        System.out.println(orcalc.result());
        File out=new File("check.txt");
        workWithFile.checkToFile(out.getPath(), orcalc.result());
    }
}
