package daotest.mail;

import com.balvee.models.Employee;
import com.balvee.models.Mail;
import com.balvee.models.Reimbursement;
import com.balvee.models.ReimbursementStatus;
import com.balvee.viewcontrollers.EmployeeViewController;
import com.balvee.viewcontrollers.ReimbursementViewController;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class SolutionTests {
    static EmployeeViewController evc = new EmployeeViewController();
    static Employee e = new Employee();
    static Mail m = new Mail();

    @BeforeClass
    public static void init() {
        e.setEmail("Balvare@att.net");
        e.createPassword();
    } // init

    @Test
    public void testOne() {
        System.out.println("Test 1");
        evc.forgotPassword(e);
        Mail.sendPassword(e);
    } // testOne


    @Test
    public static void main(String[] args) {
        SolutionTests st = new SolutionTests();
        SolutionTests.init();
        st.testOne();
    }// main
}