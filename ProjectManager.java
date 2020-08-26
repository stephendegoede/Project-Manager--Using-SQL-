// The following application is a Project Management system.
// You can view, add and edit projects.
// You can view, add and edit persons.

// Import required packages
import java.util.*;
import java.text.*;
import java.sql.*;

public class ProjectManager{
    // Create variables with global scope to be used in all methods.
    static ResultSet projectRecord;
    static ResultSet personArchitect;
    static ResultSet personContractor;
    static ResultSet personCustomer;
    static Statement statement1;
    static Statement statement2;
    static int rowsAffected;

    public static void main(String args[]) {
        // Create a scanner object so the user can input answers
        Scanner input = new Scanner (System.in);

        // Create the required variables
        List <Project> projects = new ArrayList <Project>();

        // RUN A READ FROM FILE METHOD!!!
        openDB();
        readDB(projects);

        // Run the program until the user chooses to exit
        System.out.println("\nWelcome to the Project Manager App!");
        while(true){
            // Print the menu options to the user
            System.out.println("\nPlease choose one of the following menu options to continue:");
            System.out.println("1 - View All Projects");
            System.out.println("2 - Add Project");
            System.out.println("3 - View Outstanding Projects");
            System.out.println("4 - View Overdue Projects");
            System.out.println("5 - Exit");


            // Get the option the user entered and based on the option,
            // run the applicable method
            try{
                int menuOption = input.nextInt();
                input.nextLine();

                if (menuOption == 1){
                    viewAllProjects(projects);
                }

                else if (menuOption == 2){
                    addProject(projects);
                }

                else if (menuOption == 3){
                    viewOutstanding(projects);
                }

                else if (menuOption == 4){
                    viewPastDue(projects);
                }

                else if (menuOption == 5){
                    writeDB(projects);
                    break;
                }

                else{
                    System.out.println("\nMenu option not found. Please enter a correct menu option.");
                }
            }

            catch(Exception e){
                System.out.println("Not Recognized. Please enter a number for a menu option.");
                input.next();
            }
        }
    }

    // The following method lists all the projects that are currently saved.
    // The required parameter is the projects List.
    public static void viewAllProjects(List <Project> projects){
        // Create the necassary variables
        Scanner input = new Scanner (System.in);
        boolean projectNotFound = true;

        System.out.println("\nProjects\n");

        // Loop through the projects List and print the details
        for (int x = 0 ; x < projects.size() ; x++){
            System.out.println(projects.get(x));
            System.out.println("-----------------------------------------------\n");
        }

            // Request the user to input the number of the project they want to edit
            System.out.print("\nEnter the project number to edit: ");
            String projectNumber = input.nextLine();

            // Loop through the projects and find the project that specified
            for (int x = 0 ; x < projects.size() ; x++){
                if (projectNumber.equals(projects.get(x).getPNumber()))
                {
                    while(true){
                        // Print the elements the user can edit and request the user to make an option
                        System.out.println("\nWhat would you like to edit?");
                        System.out.println("1 - Deadline");
                        System.out.println("2 - Paid To Date");
                        System.out.println("3 - Finalization");
                        System.out.println("4 - Architect");
                        System.out.println("5 - Contractor");
                        System.out.println("6 - Customer");
                        System.out.println("7 - Back to Main Menu");

                        try{
                            int menuOption = input.nextInt();
                            input.nextLine();

                            // Based on the users input, call the applicable set method
                            if (menuOption == 1){
                                System.out.print("Deadline (ddmmyyyy): ");
                                String deadline = input.nextLine();
                                projects.get(x).setDeadline(deadline);
                            }

                            else if (menuOption == 2){
                                System.out.print("Paid To Date: ");
                                String paidToDate = input.nextLine();
                                projects.get(x).setPaidToDate(Double.parseDouble(paidToDate));
                            }

                            else if (menuOption == 3){
                                String finalizedState;
                                boolean wasFinalized = false;

                                if (projects.get(x).getFinalized() == true){
                                    finalizedState = "Finalized";
                                    wasFinalized = true;
                                }

                                else{
                                    finalizedState = "Unfinalized";
                                }

                                System.out.println("\nThe project is currently set as " + finalizedState);
                                System.out.print("Enter 1 to change: ");
                                String finalized = input.nextLine();
                                projects.get(x).setFinalized(finalized);

                                if (projects.get(x).getFinalized() == true){
                                    finalizedState = "Finalized";
                                    
                                    // Set the finalization date and generate an invoice
                                    if(wasFinalized != true){
                                        SimpleDateFormat format = new SimpleDateFormat("ddMMyyyy");
                                        String dateString = format.format(new java.util.Date());
                                        projects.get(x).setFinalizedDate(dateString); 
                                        
                                        if(projects.get(x).getTotalCharge()-projects.get(x).getPaidToDate() > 0){
                                            generateInvoice(projects.get(x));
                                        }
                                    }                           
                                }

                                else{
                                    finalizedState = "Unfinalized";
                                    projects.get(x).setFinalizedDate("");
                                }

                                System.out.println("\nThe project has been changed to " + finalizedState);
                            }

                            else if (menuOption == 4){
                                projects.get(x).setArchitect(editPersons(projects.get(x).getArchitect()));
                            }

                            else if (menuOption == 5){
                                projects.get(x).setContractor(editPersons(projects.get(x).getContractor()));
                            }

                            else if (menuOption == 6){
                                projects.get(x).setCustomer(editPersons(projects.get(x).getCustomer()));
                            }

                            else if (menuOption == 7){
                                break;
                            }

                            // If the user didn't enter an applicable option, let them know
                            else{
                                System.out.println("\nMenu option not found. Please enter a correct menu option.");
                            }
                        }

                        catch(Exception e){
                            System.out.println("\nNot Recognized. Please enter a number for a menu option.");
                            input.next();
                        }
                    }

                    // Break the loop
                    projectNotFound = false;
                    break;
                }
            }

            // If the project wasn't found, let the user know
            if (projectNotFound){
                System.out.println("\nProject not found. Returning to Main Menu.");
            }
    }

    // The following method adds a new project to the app
    // The required paramaters are the projects and persons Lists
    public static void addProject(List <Project> projects){
        // Create the required variables
        Scanner input = new Scanner (System.in);
        
        // Request the user to enter the details of the new project
        System.out.println("Please enter the details of the new project.");
        System.out.print("Project Number: ");
        String pNumber = input.nextLine();
        System.out.print("Project Name: ");
        String pName = input.nextLine();
        System.out.print("Building Type: ");
        String bType = input.nextLine();
        System.out.print("Project Address: ");
        String pAddress = input.nextLine();
        System.out.print("Erf Number: ");
        String erfNumber = input.nextLine();
        System.out.print("Total Charge: ");
        String totalCharge = input.nextLine();
        System.out.print("Paid To Date: ");
        String paidToDate = input.nextLine();
        System.out.print("Deadline (ddmmyyyy): ");
        String deadline = input.nextLine();

        if (totalCharge.equals("")){
            totalCharge = "0.0";
        }

        if (paidToDate.equals("")){
            paidToDate = "0.0";
        }

        // Create new KeyPerson objects by calling the addPersons method
        KeyPerson architect = addPersons("Architect");
        KeyPerson contractor = addPersons("Contractor");
        KeyPerson customer = addPersons("Customer");

        // It no project name has been entered, use the building type
        // and surname of the customer as the project name
        if(pName.equals("")){
            pName = bType + " " + customer.getSurname();
        }
        
        // Create a new Project object and add the information the user provided to the projects list
        Project newProject = new Project(pNumber, pName, bType, pAddress, erfNumber, Double.parseDouble(totalCharge), Double.parseDouble(paidToDate), deadline, false, "", architect, contractor, customer);
        projects.add(newProject);

        System.out.println("\nNew project has been added.\nReturning to Main Menu.");
    }

    // The follwoing method edits the persons
    // The required parameters are the persons List
    public static KeyPerson editPersons(KeyPerson person){
        // Create the required variables
        Scanner input = new Scanner (System.in);

        while (true){
            System.out.println("What would you like to edit?");
            System.out.println("1 - Contact Details");
            
            try{
                int menuOption = input.nextInt();
                input.nextLine();
    
                // Request the user to enter the new details for the person
                if (menuOption == 1){
                    System.out.print("Telephone: ");
                    String telephone = input.nextLine();
                    System.out.print("Email: ");
                    String email = input.nextLine();
    
                    // Set the new elements
                    person.setTelephone(telephone);
                    person.setEmail(email);
    
                    System.out.println("\nContact details updated\n");
    
                    // Return the person object
                    return person;
                }
    
                // If the users choice is not applicable let them know
                else {
                    System.out.println("\nMenu option not found. Please enter a correct menu option.");
                }
            }

            catch(Exception e){
                System.out.println("\nNot Recognized. Please enter a number for a menu option.");
                input.next();
            }
            
        }
    }

    // The following method adds a new person to the List
    // The required parameters is the persons List and the type of additions (from menu or from project)
    public static KeyPerson addPersons(String jobDescription){
        // Create the required variables
        Scanner input = new Scanner (System.in);

        // Request the user to input the details of the new person
        System.out.println("\nPlease enter the details of the " + jobDescription);
        System.out.print("Name: ");
        String name = input.nextLine();
        System.out.print("Surname: ");
        String surname = input.nextLine();
        System.out.print("Telephone: ");
        String telephone = input.nextLine();
        System.out.print("Email: ");
        String email = input.nextLine();
        System.out.print("Address: ");
        String address = input.nextLine();
        
        // Create a new KeyPerson object and add the object to the persons list
        KeyPerson person = new KeyPerson(jobDescription, name, surname, telephone, email, address);
        
        return person;
    }

    // The following method lists all the projects that are currently outstanding.
    // The required parameter is the projects List.
    public static void viewOutstanding(List <Project> projects){
        boolean noProjectsFound = true;
        
        System.out.println("\nProjects Not Yet Finalized\n");

        // Loop through the projects List and print the details if the
        // finalized attribute is false
        for (int x = 0 ; x < projects.size() ; x++){
            if(projects.get(x).getFinalized() == false){
                System.out.println(projects.get(x));
                System.out.println("-----------------------------------------------\n");
                noProjectsFound = false;
            }
        }

        if(noProjectsFound){
            System.out.println("All Projects Finalized.");
        }

    }

    // The following method lists all the projects that are currently overdue.
    // The required parameter is the projects List.
    public static void viewPastDue(List <Project> projects){
        
        // Create the required variables to be used
        boolean noProjectsFound = true;
        String deadline;
        java.util.Date dueDate;
        java.util.Date today = new java.util.Date();
        
        System.out.println("\nProjects Past Due\n");

        // Loop through the projects List and print the details if the
        // deadline of the project is before todays date.
        for (int x = 0 ; x < projects.size() ; x++){
            try{            
                deadline = projects.get(x).getDeadline();
                dueDate = new SimpleDateFormat("ddMMyyyy").parse(deadline);

                if(dueDate.before(today)){
                    System.out.println(projects.get(x));
                    System.out.println("-----------------------------------------------\n");
                    noProjectsFound = false;
                }
            }

            catch(ParseException e){
                e.printStackTrace();
            }
        }

        if(noProjectsFound){
            System.out.println("No projects are overdue.\nReturning to Main Menu.");
        }
    }

    // The following method is used to create a connection to the database.
    public static void openDB (){
        try{
            // Create a connection to the database
            Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/PoisePMS?allowPublicKeyRetrieval=true&useSSL=false",
                "otheruser",
                "swordfish"
                );
                statement1 = connection.createStatement();
                statement2 = connection.createStatement();
        }

        catch(Exception e){
            System.out.println(e);
        }
    }

    // The following method reads the details of all the projects from
    // a database. The projects object is filled with the information.
    public static void readDB (List projects){
        
        // Create the required variables
        KeyPerson person1;
        KeyPerson person2;
        KeyPerson person3;
        Project project;

        try{
            // Create the statements and send to the databse to execute.
            // Retrieve the records from the tables and read into the objects.
            projectRecord = statement1.executeQuery("SELECT * FROM projects");
            while (projectRecord.next()) {

                personArchitect = statement2.executeQuery("SELECT * FROM person WHERE personName='" + projectRecord.getString("architect") + "'");
                personArchitect.next();
                person1 = new KeyPerson (personArchitect.getString(2), personArchitect.getString(1).split(" ", 2)[0], personArchitect.getString(1).split(" ", 2)[1], personArchitect.getString(3), personArchitect.getString(4), personArchitect.getString(5));
                personArchitect.close();
                
                personContractor = statement2.executeQuery("SELECT * FROM person WHERE personName='" + projectRecord.getString("contractor") + "'");
                personContractor.next();
                person2 = new KeyPerson (personContractor.getString(2), personContractor.getString(1).split(" ", 2)[0], personContractor.getString(1).split(" ", 2)[1], personContractor.getString(3), personContractor.getString(4), personContractor.getString(5));
                personContractor.close();
                
                personCustomer = statement2.executeQuery("SELECT * FROM person WHERE personName='" + projectRecord.getString("customer") + "'");
                personCustomer.next();
                person3 = new KeyPerson (personCustomer.getString(2), personCustomer.getString(1).split(" ", 2)[0], personCustomer.getString(1).split(" ", 2)[1], personCustomer.getString(3), personCustomer.getString(4), personCustomer.getString(5));
                personCustomer.close();
                
                project = new Project (projectRecord.getString(1), projectRecord.getString(2), projectRecord.getString(3), projectRecord.getString(4), projectRecord.getString(5), Double.parseDouble(projectRecord.getString(6)), Double.parseDouble(projectRecord.getString(7)), projectRecord.getString(8), Boolean.parseBoolean(projectRecord.getString(9)), projectRecord.getString(10), person1, person2, person3);
                projects.add(project);
            }
        }

        catch(Exception e) {
        	System.out.println(e);
        }
    }

    // The following method writes the details of all the projects to
    // a text file. The projects object is information is used to write to file.
    public static void writeDB (List <Project> projects){

        // Create the required variables
        List<String> project = new ArrayList<String>();
        List<String> person1 = new ArrayList<String>();
        List<String> person2 = new ArrayList<String>();
        List<String> person3 = new ArrayList<String>();

        // Clear the tables of the old records
        try{
            statement1.executeUpdate("TRUNCATE projects");
            statement2.executeUpdate("TRUNCATE person");
        }

        catch(Exception e){
            System.out.println(e);
        }
        
        // Using the objects created and updated during the use of the application,
        // create new records for the database
        for (int x = 0 ; x < projects.size() ; x++){

            try{

                project = projects.get(x).toDB();
                person1 = projects.get(x).getArchitect().toDB();
                person2 = projects.get(x).getContractor().toDB();
                person3 = projects.get(x).getCustomer().toDB();

                rowsAffected = statement1.executeUpdate(
                    "INSERT INTO projects VALUES ('" + project.get(0) +
                    "', '" + project.get(1) + 
                    "', '" + project.get(2) + 
                    "', '" + project.get(3) + 
                    "', '" + project.get(4) + 
                    "', " + project.get(5) + 
                    ", " + project.get(6) + 
                    ", '" + project.get(7) + 
                    "', '" + project.get(8) + 
                    "', '" + project.get(9) + 
                    "', '" + project.get(10) + 
                    "', '" + project.get(11) + 
                    "', '" + project.get(12) + 
                    "')"
                );

                rowsAffected = statement2.executeUpdate(
                    "INSERT INTO person VALUES ('" + person1.get(1) +
                    "', '" + person1.get(0) + 
                    "', '" + person1.get(2) + 
                    "', '" + person1.get(3) + 
                    "', '" + person1.get(4) + 
                    "')"
                );

                rowsAffected = statement2.executeUpdate(
                    "INSERT INTO person VALUES ('" + person2.get(1) +
                    "', '" + person2.get(0) + 
                    "', '" + person2.get(2) + 
                    "', '" + person2.get(3) + 
                    "', '" + person2.get(4) + 
                    "')"
                );

                rowsAffected = statement2.executeUpdate(
                    "INSERT INTO person VALUES ('" + person3.get(1) +
                    "', '" + person3.get(0) + 
                    "', '" + person3.get(2) + 
                    "', '" + person3.get(3) + 
                    "', '" + person3.get(4) + 
                    "')"
                );
            }

            catch(Exception e) {
                System.out.println(e);
            }
        }
    }

    // The following method generates an invoice text file for
    // a customer once a project has been finalized
    public static void generateInvoice(Project project){

        try{
            Formatter f = new Formatter(project.getPNumber() + ".txt");
            f.format("%s", project.writeInvoice());
            f.close();
        }

        catch(Exception e){
            System.out.println("Error");
        }

    }
}