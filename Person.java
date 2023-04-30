//Shaohai Li (sli34@toromail.csudh.edu)
import java.io.*;
import java.util.*;
import java.util.Scanner;
public class Person {

        //Fields
        private String firstName;
        private String lastName;
        private String emailAddress;
        private String SSN;
        private int id;
        private List<Transaction> transactionList;


        //Constructors
        public Person(int id,String fName, String lName, String email, String SSN) {
            this.id = id;
            firstName=fName;
            lastName=lName;
            this.emailAddress=email;
            this.SSN=SSN;
            transactionList = new ArrayList<Transaction>();
        }

        //Methods


        @Override
        public String toString() {
            return id+":"+firstName+":"+lastName+":"+emailAddress+":"+SSN;
        }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getSSN() {
        return SSN;
    }

    public void setSSN(String SSN) {
        this.SSN = SSN;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Transaction> getTransactionList() {
        return transactionList;
    }

    public void setTransactionList(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }
}

