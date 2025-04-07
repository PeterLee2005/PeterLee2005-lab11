import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.util.*;
import java.beans.Transient;
import java.io.*;
import java.net.*;
import java.time.*;

public class Lab11_Tests {
    /*
        Complete the test case below that checks to see that threads A and B have both contributed 100 entries respectively
        to the shared ArrayList when they have both finished running.
    */
    @Test
    public void test1() {
        Lab11_Thread threadA = new Lab11_Thread("A1", 100);
        Lab11_Thread threadB = new Lab11_Thread("B1", 100);
        threadA.setData(new ArrayList<>());

        threadA.start();
        threadB.start();

        try {
            threadA.join();
            threadB.join();
        } catch (InterruptedException e) {
            System.out.println ("test1: Interrupted while joining a thread");
        }

        ArrayList<String> result = threadA.getData();
        int runsA = 0;
        int runsB = 0;

        for (String s : result) {
            if (s.startsWith("A1")) {
                runsA++;
            }
            if (s.startsWith("B1")) {
                runsB++;
            }
        }

        assertEquals(100, runsA);
        assertEquals(100, runsB);

    }

    /*
        Complete the test case below that checks to see if the shared ArrayList has at least 10 entries after 500ms of system time
    */
    @Test
    public void test2() {

        Lab11_Thread threadA = new Lab11_Thread("A2", 500);
        Lab11_Thread threadB = new Lab11_Thread("B2", 500);
        threadA.setData(new ArrayList<>());

        threadA.start();
        threadB.start();
        try {
            Thread.sleep(500); 
        } catch (Exception e){
            e.printStackTrace();
        }

        int entries = threadA.getData().size();
        boolean x = entries >= 10;
        
        assertEquals(true, x);

    }

    /*
        Complete the test case below that checks to see if thread A finishes adding its 10 entries before thread B was allowed to 
        add anything to the shared ArrayList
    */
    @Test
    public void test3() {
        Lab11_Thread threadA = new Lab11_Thread("A3", 10);
        Lab11_Thread threadB = new Lab11_Thread("B3", 10);
        threadA.setData(new ArrayList<>());

        threadA.start();
        
        try {
            threadA.join();
        } catch (Exception e){
            e.printStackTrace();
        }
        
        threadB.start();

        try {
            threadB.join();
        } catch (Exception e){
            e.printStackTrace();
        }

        ArrayList<String> result = threadA.getData();
        int index = 0;
        for (String s : result) {
            if (s.startsWith("B3")) {
                break;
            }
            if (s.startsWith("A3")) {
                index++;
            }
        }

        assertEquals(10, index);
    }

    /* Checks to see if there are any nulls in the data */

    @Test
    public void test4() {
        Lab11_Thread threadA = new Lab11_Thread("A4", 50);
        threadA.setData(new ArrayList<>());

        threadA.start();
        try {
            threadA.join();
        } catch (Exception e) {
            e.printStackTrace();
        }

        boolean check = true;
        for (String s : threadA.getData()) {
            if (s == null || s.trim().isEmpty()) {
                check = false;
                break;
            }
        }

        assertEquals(true, check);
    }

    /* Tests to see if there are any duplicate strings in the result */

    @Test
    public void test5() {
        Lab11_Thread threadA = new Lab11_Thread("A5", 50);
        threadA.setData(new ArrayList<>());

        threadA.start();
        try {
            threadA.join();
        } catch (Exception e) {
            e.printStackTrace();
        }

        ArrayList<String> result = threadA.getData();
        boolean check = true;

        for (int i=0; i<result.size(); i++) {
            String curr = result.get(i);
            for (int j=i+1; j<result.size(); j++) {
                if (curr.equals(result.get(j))) {
                    check = false;
                    break;
                }
            }
        }

        assertEquals(true, check);
    }
}
