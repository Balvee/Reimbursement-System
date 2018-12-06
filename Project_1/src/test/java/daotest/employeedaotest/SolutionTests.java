package daotest.employeedaotest;


import com.balvee.models.Employee;
import com.balvee.models.EmployeeTypes;
import com.balvee.viewcontrollers.EmployeeViewController;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class SolutionTests {
    static EmployeeViewController evc = new EmployeeViewController();
    static List<Employee> rListAll = new ArrayList<>();
    static Employee e = new Employee();

    @BeforeClass
    public static void init() {
        rListAll = evc.findAll();
        e = evc.findOne(4379666);
    } // init

    @Test
    public void testOne() {
        System.out.println("Test 1");
        Assert.assertEquals(rListAll.get(0).getType(), EmployeeTypes.EMPLOYEE);
    } // testOne

    @Test
    public void testTwo() {
        System.out.println("Test 2");
        Assert.assertEquals(e.getUsername(), "TimmyT");
    } // testTwo

    @Test
    public void testOneFail() {
        System.out.println("Test 1 Fail");
        Assert.assertEquals(rListAll.get(0).getType(), 0);
    } // testOneFail

    @Test
    public void testTwoFail() {
        System.out.println("Test 2 Fail");
        Assert.assertEquals(e.getUsername(), "Example");
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
