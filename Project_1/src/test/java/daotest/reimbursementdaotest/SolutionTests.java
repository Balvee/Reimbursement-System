package daotest.reimbursementdaotest;

import com.balvee.models.Reimbursement;
import com.balvee.models.ReimbursementStatus;
import com.balvee.viewcontrollers.ReimbursementViewController;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class SolutionTests {
    static ReimbursementViewController rvc = new ReimbursementViewController();
    static List<Reimbursement> rListAll = new ArrayList<>();
    static List<Reimbursement> rList = new ArrayList<>();

    @BeforeClass
    public static void init() {
        rListAll = rvc.findAll(0, 0);
        rList = rvc.findOne(4379666, 0);
    } // init

    @Test
    public void testOne() {
        System.out.println("Test 1");
        Assert.assertEquals(rList.get(0).getAmount(), 250, 0);
    } // testOne

    @Test
    public void testTwo() {
        System.out.println("Test 2");
        Assert.assertEquals(rListAll.get(0).getType(), ReimbursementStatus.APPROVED);
    } // testTwo

    @Test
    public void testOneFail() {
        System.out.println("Test 1 Fail");
        Assert.assertEquals(rList.get(0).getType(), 0);
    } // testOneFail

    @Test
    public void testTwoFail() {
        System.out.println("Test 2 Fail");
        Assert.assertEquals(rListAll.get(0).getType(), ReimbursementStatus.PENDING);
    } // testTwoFail

    @Test
    public static void main(String[] args) {
        SolutionTests st = new SolutionTests();
        SolutionTests.init();

        //st.testOneFail();
        st.testOne();
        //st.testTwoFail();
        st.testTwo();

    }// main
}