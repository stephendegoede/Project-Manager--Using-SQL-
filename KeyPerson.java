// The following class is a KeyPerson object that holds
// the information for key persons

import java.util.*;

public class KeyPerson {
    // Create the required variables
    private String jobDescription;
    private String name;
    private String surname;
    private String telephone;
    private String email;
    private String address;

    // Create the constructor of the object to save initial values
    public KeyPerson (String jobDescription, String name, String surname, String telephone, String email, String address){
        this.jobDescription = jobDescription;
        this.name = name;
        this.surname = surname;
        this.telephone = telephone;
        this.email = email;
        this.address = address;
    }
    
    // Get methods to return applicable information of the person
    public String getJobDescription(){
        return jobDescription;
    }

    public String getSurname(){
        return surname;
    }

    public String fullName(){
        return name + " " + surname;
    }

    // Set methods to set private variables
    public void setTelephone(String telephone){
        if(telephone.isEmpty() != true){
            this.telephone = telephone;
        }
    }

    public void setEmail(String email){
        if(email.isEmpty() != true){
            this.email = email;
        }
    }

    // This method sends the information to be written to the text file
    public String write() {
        String output = jobDescription;
        output += "\n"+ name;
        output += "\n" + surname;
        output += "\n" + telephone;
        output += "\n" + email;
        output += "\n" + address;
     
        return output;
     }

    // This method sends the information to be written to the DB
    public List<String> toDB() {
        List<String> output = new ArrayList<String>();
        output.add(jobDescription);
        output.add(name + " " + surname);
        output.add(telephone);
        output.add(email);
        output.add(address);
     
        return output;
     }

    // toString method when information is printed to screen
    public String toString() {
        String output = jobDescription;
        output += "\nName: " + name + " " + surname;
        output += "\nTelephone: " + telephone;
        output += "\nEmail: " + email;
        output += "\nAddress: " + address;
     
        return output;
     }
}