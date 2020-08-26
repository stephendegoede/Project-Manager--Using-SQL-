// The following class is a Project Object that holds various
// information relating to a project

import java.util.*;

public class Project {
    // Create the required variables
    private String pNumber;
    private String pName;
    private String bType;
    private String pAddress;
    private String erfNumber;
    private double totalCharge;
    private double paidToDate;
    private String deadline;
    private boolean finalized;
    private String finalizedDate;
    private KeyPerson architect;
    private KeyPerson contractor;
    private KeyPerson customer;

    // Create the constructor of the object to save initial values
    public Project (String pNumber, String pName, String bType, String pAddress, String erfNumber, double totalCharge, double paidToDate, String deadline, boolean finalized, String finalizedDate, KeyPerson architect, KeyPerson contractor, KeyPerson customer){
        this.pNumber = pNumber;
        this.pName = pName;
        this.bType = bType;
        this.pAddress = pAddress;
        this.erfNumber = erfNumber;
        this.totalCharge = totalCharge;
        this.paidToDate = paidToDate;
        this.deadline = deadline;
        this.finalized = finalized;
        this.finalizedDate = finalizedDate;
        this.architect = architect;
        this.contractor = contractor;
        this.customer = customer;
    }

    // Get methods to return applicable information of the project
    public String getPNumber(){
        return pNumber;
    }

    public boolean getFinalized(){
        return finalized;
    }
    
    public String getFinalizedString(){
        if(finalized){
            return "Finalized";
        }

        else{
            return "Unfinalized";
        }
    }

    public KeyPerson getArchitect(){
        return architect;
    }

    public KeyPerson getContractor(){
        return contractor;
    }

    public KeyPerson getCustomer(){
        return customer;
    }

    public String getDeadline(){
        return deadline;
    }

    public double getTotalCharge(){
        return totalCharge;
    }

    public double getPaidToDate(){
        return paidToDate;
    }

    // Set methods to set private variables
    public void setPaidToDate(double paidToDate){
        if(paidToDate > 0){
            this.paidToDate = paidToDate;
        }
    }

    public void setDeadline(String deadline){
        if(deadline.isEmpty() != true){
            this.deadline = deadline;
        }
    }

    public void setFinalizedDate(String finalizedDate){
        if(deadline.isEmpty() != true){
            this.finalizedDate = finalizedDate;
        }
    }

    public void setFinalized(String finalized){
        if(finalized.equals("1")){
            if(this.finalized == true){
                this.finalized = false;
            }

            else{
                this.finalized = true;
            }
        }
    }

    public void setArchitect(KeyPerson architect){
        this.architect = architect;
    }

    public void setContractor(KeyPerson contractor){
        this.contractor = contractor;
    }

    public void setCustomer(KeyPerson customer){
        this.customer = customer;
    }

    // This method sends the information to be written to the text file
    public String write() {
        String output = pNumber;
        output += "\n"+pName;
        output += "\n"+bType;
        output += "\n"+pAddress;
        output += "\n"+erfNumber;
        output += "\n"+totalCharge;
        output += "\n"+paidToDate;
        output += "\n"+deadline;
        output += "\n"+finalized;
        output += "\n"+finalizedDate;
        output += "\n" + architect.write();
        output += "\n" + contractor.write();
        output += "\n" + customer.write();
     
        return output;
     }

    //  This method generates a string to write the invoice file
     public String writeInvoice() {
        String output = "" + customer;
        output += "\n\nProject Number:\t" + pNumber;
        output += "\nProject Name:\t" + pName;
        output += "\nFinalized Date:\t" + finalizedDate;
        output += "\n\nTotal Charge:\t\t" + totalCharge;
        output += "\nPaid To Date:\t\t" + paidToDate;
        output += "\nCHARGE OUTSTANDING: " + (totalCharge - paidToDate);

        return output;
     }

    //  toDB method to return an array of information to be wrtitten to the DB
     public List<String> toDB() {
        List<String> output = new ArrayList<String>();
        output.add(pNumber);
        output.add(pName);
        output.add(bType);
        output.add(pAddress);
        output.add(erfNumber);
        output.add(String.valueOf(totalCharge));
        output.add(String.valueOf(paidToDate));
        output.add(deadline);
        output.add(String.valueOf(finalized));
        output.add(finalizedDate);
        output.add(architect.fullName());
        output.add(contractor.fullName());
        output.add(customer.fullName());
     
        return output;
     }

    // toString method when information is printed to screen
    public String toString() {
        String output = "Project Number: " + pNumber;
        output += "\nProject Name: " + pName;
        output += "\nBuilding Type: " + bType;
        output += "\nProject Address: " + pAddress;
        output += "\nErf Number: " + erfNumber;
        output += "\nTotal Charge: " + totalCharge;
        output += "\nPaid To Date: " + paidToDate;
        output += "\nDeadline: " + deadline;

        if(finalized){
            output += "\nFinalization: Finalized";
        }

        else{
            output += "\nFinalization: Unfinalized";
        }

        output += "\nFinalized Date: " + finalizedDate;
        output += "\n\nPersons:";
        output += "\n\n" + architect;
        output += "\n\n" + contractor;
        output += "\n\n" + customer;
     
        return output;
     }
}