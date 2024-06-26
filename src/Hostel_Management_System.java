import java.util.*;
import java.io.*;

public class Hostel_Management_System {
    public static void main(String[] args) {
        System.out.println("""
                            ********************************************************************************************
                            ********************||||||||     Welcome to Ali Boys Hostel    ||||||||*********************
                            ********************************************************************************************
                            """);
        Scanner in = new Scanner(System.in);
        String Employee_File_Path="Employees.csv";
        String Resident_File_Path = "Residents.csv";
        String Complaint_File_Path = "Complaints.csv";
        File file1 = new File(Employee_File_Path);
        File file2 = new File(Resident_File_Path);
        File file3 = new File(Complaint_File_Path);

        // Checking if file exists or not
        if (!file1.exists()) {
            create_File(Employee_File_Path);
        }
        if (!file2.exists()) {
            create_File(Resident_File_Path);
        }
        if(!file3.exists()){
            create_File(Complaint_File_Path);
        }

        label:
        while(true) {
            System.out.println("""
                               
                               Choose one of the following options:\s
                               0. To Exit
                               1. For Admin Portal
                               2. For Resident Portal
                               3. For Employee Portal
                               Enter your option:""");
            String admin_option=in.next();
            switch (admin_option) {
                case "0":
                    break label;
                case "1":
                    System.out.println("Password for admin portal is: ADMIN");
                    String password;
                    do {
                        System.out.println("Enter Password: ");
                        password = in.next();
                        if (!Objects.equals(password, "ADMIN")) {
                            System.out.println("Invalid Password. Try again...");
                        }
                    } while (!Objects.equals(password, "ADMIN"));
                    System.out.println("\nWelcome to Admin Portal...");
                    while (true) {
                        System.out.println("""
                                                            
                                Choose one of the following options:\s
                                0. To exit Admin portal.
                                1. To add a new resident.
                                2. To add a new Employee.
                                3. To view residents.
                                4. To view Employees.
                                5. To search a specific resident.
                                6. To search a specific employee.
                                7. To delete a resident.
                                8. To delete an employee.
                                9. To update a resident's info.
                                10.To update an employee's info.
                                11.To view complaints.
                                12.To delete a complaint.
                                13.To view menu.
                                Enter your option:""");
                        String option = in.next();
                        if (Objects.equals(option, "0")) {
                            System.out.println("Thanks for visiting.");
                            break;
                        } else if (Objects.equals(option, "1")) {
                            List<String[]> newData = new ArrayList<>();
                            newData.add(resident_Data(Resident_File_Path));
                            writeCSV(Resident_File_Path, newData);
                        } else if (Objects.equals(option, "2")) {
                            List<String[]> newData = new ArrayList<>();
                            newData.add(employee_Data(Employee_File_Path));
                            writeCSV(Employee_File_Path, newData);
                        } else if (Objects.equals(option, "3")) {
                            viewIndividuals(Resident_File_Path);
                        } else if (Objects.equals(option, "4")) {
                            viewIndividuals(Employee_File_Path);
                        } else if (Objects.equals(option, "5")) {
                            System.out.println("Enter the CNIC of the resident that you want to search.");
                            String cnic = CNIC_Check();
                            searchData(Resident_File_Path, cnic);
                        } else if (Objects.equals(option, "6")) {
                            System.out.println("Enter the CNIC of the employee that you want to search.");
                            String cnic = CNIC_Check();
                            searchData(Employee_File_Path, cnic);
                        } else if (Objects.equals(option, "7")) {
                            System.out.println("Enter the CNIC of the resident that you want to delete.");
                            String cnic = CNIC_Check();
                            deleter(Resident_File_Path, cnic);
                        } else if (Objects.equals(option, "8")) {
                            System.out.println("Enter the CNIC of the employee that you want to delete.");
                            String cnic = CNIC_Check();
                            deleter(Employee_File_Path, cnic);
                        } else if (Objects.equals(option, "9")) {
                            System.out.println("Enter the CNIC of the resident that you want to Update.");
                            String cnic = CNIC_Check();
                            ResidentDataUpdater(Resident_File_Path, cnic);
                        } else if (Objects.equals(option, "10")) {
                            System.out.println("Enter the CNIC of the employee that you want to Update.");
                            String cnic = CNIC_Check();
                            EmployeeDataUpdater(Employee_File_Path, cnic);
                        } else if (Objects.equals(option, "11")) {
                            viewIndividuals(Complaint_File_Path);
                        } else if (Objects.equals(option, "12")) {
                            System.out.println("Enter the CNIC of the resident whose complaint you want to delete.");
                            String cnic = CNIC_Check();
                            if(searchCNIC(Resident_File_Path,cnic)) {
                                deleter(Complaint_File_Path, cnic);
                            }
                        } else if (Objects.equals(option, "13")) {
                            viewMenu();
                        } else {
                            System.out.println("Invalid Input.Try Again...");
                        }
                    }
                    break;
                case "2":
                    System.out.println("\nWelcome to Resident Portal...");
                    while (true) {
                        System.out.println("""
                                
                                Choose one of the following options:\s
                                0. To exit Resident portal.
                                1. To view Menu.
                                2. To add a new Complaint.
                                3. To update a complaint.
                                Enter your option:""");
                        String ResidentOption=in.next();
                            if(ResidentOption.equals("0")){
                                break ;}
                            else if (ResidentOption.equals("1")) {
                                viewMenu();}
                            else if(ResidentOption.equals("2")){
                                List<String[]> newData = new ArrayList<>();
                                newData.add(complaint_Data(Resident_File_Path));
                                writeCSV(Complaint_File_Path, newData);}
                            else if(ResidentOption.equals("3")){
                                System.out.println("Enter your CNIC to update your complaint: ");
                                String cnic = CNIC_Check();
                                if(searchCNIC(Resident_File_Path,cnic)) {
                                    ComplaintDataUpdater(Complaint_File_Path, cnic);
                                }
                                }
                            else{
                                System.out.println("Invalid Input. Try Again...");
                            }
                        }
                    break;
                case "3":
                    employee();
                    break;
                default:
                    System.out.println("Invalid Input. Try again...");
                    break;
            }
        }
    }

    public static void create_File(String path) {                                     // Method for creating a file
        try (FileWriter writer = new FileWriter(path)) {
            switch (path) {
                case "Residents.csv" ->
                        writer.write("CNIC,Room Number,Name,Age,Mobile Number,City,Meal Charges,Rent Status,Duration of Stay,Date of Joining" + "\n");
                case "Employees.csv" ->
                        writer.write("CNIC,Name,Age,Mobile Number,City,Salary,Contract Time,Salary Status,Date of Joining" + "\n");
                case "Complaints.csv" ->
                        writer.write("CNIC,Room Number,Name,Complaints" + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String[]> readCSV(String filePath) {                           // Method for reading a file
        List<String[]> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] row = line.split(",");
                data.add(row);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static void viewIndividuals(String path){                                 // Method to view all entities of a file
        List<String[]> data = readCSV(path);
        for (String[] row : data) {
            for (String value : row) {
                System.out.printf("|%-16s  ", value);
            }
            System.out.println();
        }
    }

    public static void writeCSV(String filePath, List<String[]> data) {                // Method for writing a file
        try (FileWriter writer = new FileWriter(filePath, true)) {
            for (String[] row : data) {
                String line = String.join(",", row);
                writer.write(line+"\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void searchData(String path, String data) {                          // Method for Searching in a file
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String Line;
            boolean found = false;

            while ((Line = reader.readLine()) != null) {
                if (Line.contains(data)) {
                    String[] values = Line.split(",");
                    System.out.println("The details of the searched individual are: ");
                    switch (path) {
                        case "Residents.csv" ->
                                System.out.printf("|%-16s |%-16s |%-16s |%-16s |%-16s |%-16s |%-16s |%-16s |%-16s |%-16s\n", "CNIC", "Room Number", "Name", "Age", "Mobile Number", "City", "Meal Charges", "Rent Status", "Duration of Stay", "Date of Joining");
                        case "Employees.csv" ->
                                System.out.printf("|%-16s |%-16s |%-16s |%-16s |%-16s |%-16s |%-16s |%-16s %-16s \n", "CNIC", "Name", "Age", "Mobile Number", "City", "Salary", "Contract Time", "Salary Status", "Date of Joining");
                        case "Complaints.csv" ->
                                System.out.printf("|%-16s |%-16s |%-16s |%-16s \n", "CNIC", "Room Number", "Name", "Complain");
                    }
                    for(String row:values){
                        System.out.printf("|%-16s ",row);
                    }
                    found = true;
                }
            }

            if (!found) {
                System.out.println("Individual with this CNIC does not exist in our database.\n");
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file: " + e.getMessage());
        }
    }

    public static void writeUpdatedCSV(String filePath, List<String[]> data) {         //    Method to write updated CSV file
        try (FileWriter writer = new FileWriter(filePath)) {
            for (String[] row : data) {
                String line = String.join(",", row);
                writer.write(line+"\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleter(String filepath, String info) {                         // Method for Deleting some data

        List<String[]> data = readCSV(filepath);    // Read existing data from CSV file
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String Line;
            boolean found = false;
            while ((Line = reader.readLine()) != null) {
                if (Line.contains(info)) {
                    // Deleting the row with "info"
                    data.removeIf(row -> row[0].equals(info));
                    found=true;
                }
            }
            if (found) {
                System.out.println("Deleted Successfully.");
            } else {
                if(filepath.equals("Complaints.csv")){
                    System.out.println("No complaint registered against this CNIC.");
                }
                else{
                    System.out.println("Individual with this CNIC does not exist.");}
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file: " + e.getMessage());
        }

        try (FileWriter writer = new FileWriter(filepath)) {// Write updated data to CSV file
            for (String[] row : data) {
                String line = String.join(",", row);
                writer.write(line+"\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String[] complaint_Data(String path){
        Scanner in=new Scanner(System.in);
        String[] complaints=new String[4];
        System.out.println("Enter CNIC: ");
        complaints[0]=Complaints_CNIC_Check(path);
        complaints[1]=chooseDataForComplaints(path,complaints[0],1);
        complaints[2]=chooseDataForComplaints(path,complaints[0],2);
        System.out.println("Enter your Complaint: ");
        complaints[3]=in.nextLine();
        return complaints;
    }
    public static String chooseDataForComplaints(String path,String data,int check) {                          // Method for Getting name of Resident for complaints
        String element = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String Line;
            while ((Line = reader.readLine()) != null) {
                if (Line.contains(data)) {
                    String[] values = Line.split(",");
                    if(check==1){
                        element = values[1];
                    }
                    else if(check==2) {
                        element = values[2];
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file: " + e.getMessage());
        }
        return element;
    }
    public static String[] resident_Data(String path) {                             //    Method to Enter a resident
        String[] resident=new String[10];
        System.out.println("Enter CNIC: ");
        resident[0]=CNIC_check(path);
        System.out.println("Enter Room number: (1-30)");
        resident[1]=roomnum(path);
        System.out.println("Enter Name: ");
        resident[2]=alphabets();
        System.out.println("Enter age: ");
        resident[3]=age();
        System.out.println("Enter Mobile number: ");
        resident[4]=mobNum();
        System.out.println("Enter city:");
        resident[5]=alphabets();
        resident[6]="NONE";
        System.out.println("Is the rent collected?(Yes or No)");
        resident[7]=yesOrNo();
        System.out.println("Enter duration of stay in months.Max limit is 12, after that you'll have to register again.");
        resident[8]=Duration()+" months";
        resident[9]= String.valueOf(new Date());

        return resident;
    }

    public static String[] employee_Data(String path) {                             //    Method to Enter an employee
        String[] employee=new String[9];
        System.out.println("Enter CNIC: ");
        employee[0]=CNIC_check(path);
        System.out.println("Enter Name: ");
        employee[1]=alphabets();
        System.out.println("Enter age: ");
        employee[2]=age();
        System.out.println("Enter Mobile number: ");
        employee[3]=mobNum();
        System.out.println("Enter city:");
        employee[4]=alphabets();
        System.out.println("Enter Salary: (20000-30000)");
        employee[5]="Rs."+salary();
        System.out.println("Enter duration of contract in months.Max limit is 12, after that you'll have to sign the contract again.");
        employee[6]=Duration()+" months";
        System.out.println("Is the salary paid?(Yes or No)");
        employee[7]=yesOrNo();
        employee[8]= String.valueOf(new Date());

        return employee;
    }

    public static String alphabets(){                                              //    Method to check Names and other things like that
        String name;
        Scanner in=new Scanner(System.in);
        do {
            name = in.nextLine();
            if(!name.matches("[a-zA-Z ]+")){
            System.out.println("Invalid input.Try Again");}
        } while (!name.matches("[a-zA-Z ]+"));
        return name;
    }

    public static String Meal_Charges() {                                         //    Method to calculate meal charges
        Scanner in = new Scanner(System.in);
        int quantity;
        while(true){
        try {
            quantity = in.nextInt();
            if (quantity >= 0 && quantity <= 93) {
                break;
            }
            System.out.println("Invalid Input. Try Again...");
        } catch (Exception e) {
            e.printStackTrace();
        }}
        quantity*=80;
        return String.valueOf(quantity+7000);
    }
    public static String CNIC_check(String path){                                   //    Method to check whether the entered CNIC is valid or not
        Scanner in=new Scanner(System.in);
        String cnic;
        while(true){
            cnic=in.next();
            if(cnic.matches("\\d{13}")){
                try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
                    String Line;
                    boolean found = false;
                    while ((Line = reader.readLine()) != null) {
                        if (Line.contains(cnic)) {
                            found = true;
                        }
                    }
                    if (found) {
                        System.out.println("Individual with this CNIC already exists.Try Again...");
                    } else {
                        break;
                    }
                } catch (IOException e) {
                    System.out.println("An error occurred while reading the file: " + e.getMessage());
                }
            }
            else{
                System.out.println("Invalid Input.Try Again");
            }
        }
        return cnic;
    }

    public static String Complaints_CNIC_Check(String path){                                      //    Method to check whether the entered CNIC is valid or not
        Scanner in=new Scanner(System.in);
        String cnic;
        while(true){
            cnic=in.next();
            if(cnic.matches("\\d{13}")){
                try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
                    String Line;
                    boolean found = false;
                    while ((Line = reader.readLine()) != null) {
                        if (Line.contains(cnic)) {
                            found = true;
                        }
                    }
                    if (found) {
                        break;
                    } else {
                        System.out.println("Resident with this CNIC is not registered.Try Again...");
                    }
                } catch (IOException e) {
                    System.out.println("An error occurred while reading the file: " + e.getMessage());
                }
            }
            else{
                System.out.println("Invalid Input.Try Again");
            }
        }
        return cnic;
    }

    public static String roomnum(String path) {                                          //    Method to check whether the entered room number is valid or not
        Scanner in = new Scanner(System.in);
        String number;
        int room;
        while (true) {
            try {
                room = in.nextInt();
                if (room >= 1 && room <= 30) {
                    number = String.valueOf(room);
                        int count = 0;
                    try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
                        String Line;
                        while ((Line = reader.readLine()) != null) {
                            String[] values = Line.split(",");
                            if (Objects.equals(values[1], number)) {
                                count++;
                            }
                        }
                        if (count >= 3) {
                            System.out.println("Room already full.Try another room...");
                        } else {
                            break;
                        }
                    } catch (IOException e) {
                        System.out.println("An error occurred while reading the file: " + e.getMessage());
                    }

                } else {
                    System.out.println("Invalid Input.Try Again");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid Input.Try Again");
                in.nextLine();
            }
        }
        return number;
    }

    public static String roomnumUpdater(String path,String currentNum) {                    // Method to update room number
        Scanner in = new Scanner(System.in);
        String number;
        int room;
        while (true) {
            try {
                room = in.nextInt();
                if(String.valueOf(room).equals(currentNum)){
                    System.out.println("You entered the same room number.");
                    return currentNum;
                }
                if (room >= 1 && room <= 30) {
                    number = String.valueOf(room);
                    int count = 0;
                    try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
                        String Line;
                        while ((Line = reader.readLine()) != null) {
                            String[] values = Line.split(",");
                            if (Objects.equals(values[1], number)) {
                                count++;
                            }
                        }
                        if (count >= 3) {
                            System.out.println("Room already full.Try another room...");
                        } else {
                            break;
                        }
                    } catch (IOException e) {
                        System.out.println("An error occurred while reading the file: " + e.getMessage());
                    }

                } else {
                    System.out.println("Invalid Input.Try Again");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid Input.Try Again");
                in.nextLine();
            }
        }
        return number;
    }

    public static String age(){                                              //    Method to check whether the entered age is valid or not
        Scanner in=new Scanner(System.in);
        int age;
        while(true){
            try{
                age=in.nextInt();
                if(age>=18 && age<=70){
                    break;
                }
                else if(age>=70){
                    System.out.println("Greater than age limit.Try Again...");
                }
                else {
                    System.out.println("Smaller than age limit.Try Again...");
                }
            }
            catch(InputMismatchException e){
                System.out.println("Invalid Input.Try Again");
                in.nextLine();
            }
        }
        return String.valueOf(age);

    }

    public static String mobNum(){                                            //    Method to check whether the entered mobile number is valid or not
        Scanner in=new Scanner(System.in);
        String number;
        do {
            number = in.next();
            if(!number.matches("\\d{11}")) {
                System.out.println("Invalid Input.Try Again...");
            }
        } while (!number.matches("\\d{11}"));
        return number;
    }

    public static String yesOrNo(){                                                 // Method to enter rent status
        Scanner in=new Scanner(System.in);
        while(true) {
            String option = in.next();
            if(Objects.equals(option, "Yes") || Objects.equals(option, "yes")){
                return "Collected";
            }
            else if(Objects.equals(option, "No") || Objects.equals(option, "no")){
                return "Not Collected";
            }
            else{
                System.out.println("Invalid Input Try Again...");
            }
        }
    }

    public static String salary() {                                           //   Method to Enter salary
        Scanner in = new Scanner(System.in);
        int Salary;
        while(true) {
            try {
                Salary = in.nextInt();
                if (Salary >= 20000 && Salary <= 30000) {
                    return String.valueOf(Salary);
                }
                else{
                    System.out.println("Invalid Input. Try again...");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid Input. Try again...");
            }
        }
    }

    public static String Duration(){                                          //    Method to input duration of stay or Contract
        Scanner in=new Scanner(System.in);
        int duration;
        while(true){
            try{
                duration=in.nextInt();
                if(duration>=0&&duration<=12){
                    break;
                }
                else{
                    System.out.println("Invalid Input.Try Again");
                }
            }
            catch(InputMismatchException e){
                System.out.println("Invalid Input.Try Again");
                in.nextLine();
            }
        }
        return String.valueOf(duration);
    }

    public static String CNIC_Check(){                                       //    Method to check whether the CNIC entered for checking is valid or not
        Scanner in=new Scanner(System.in);
        String cnic;
        while(true){
            try{
                cnic=in.next();
                if(cnic.matches("\\d{13}")){
                    break;
                }
                else{
                    System.out.println("Invalid Input.Try Again");
                }
            }
            catch(InputMismatchException e){
                System.out.println("Invalid Input.Try Again");
                in.nextLine();
            }
        }
        return cnic;
    }

    public static void ResidentDataUpdater(String filepath, String info) {                 // Method to update Resident Data
        Scanner in=new Scanner(System.in);

        // Read existing data from CSV file
        List<String[]> data = readCSV(filepath);

        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String Line;
            boolean found = false;
            while ((Line = reader.readLine()) != null) {
                data.removeIf(row -> row[0].equals(info));

                if (Line.contains(info)) {
                    String[] values = Line.split(",");
                    System.out.println("""
                            
                            Choose one of the following options:\s
                            0. To Exit
                            1. Room no.
                            2. Age
                            3. Mobile No.
                            4. Rent
                            5. Rent Status
                            6. Time of Stay
                            Enter your option:""");
                    String option=in.next();
                    if(Objects.equals(option, "0")){
                        break;
                    }
                    else if(Objects.equals(option, "1")){
                        System.out.println("The current Room number is: "+values[1]);
                        System.out.println("Enter new room number: (1-30)");
                        values[1]= roomnumUpdater(filepath,values[1]);
                        found=true;
                    }
                    else if(Objects.equals(option, "2")){
                        System.out.println("The current age is: "+values[3]);
                        System.out.println("Enter new age: ");
                        values[3]= age();
                        found=true;
                    }
                    else if(Objects.equals(option, "3")){
                        System.out.println("The current mobile number is: "+values[4]);
                        System.out.println("Enter new mobile number : ");
                        values[4]= mobNum();
                        found=true;
                    }
                    else if(Objects.equals(option, "4")){
                        System.out.println("The current rent is: "+values[6]);
                        System.out.println("Enter meals eaten this month to calculate overall rent: (Max 93 meals according to 31 days a month)");
                        values[6]= "Rs. "+Meal_Charges();
                        found=true;
                    }
                    else if(Objects.equals(option, "5")){
                        System.out.println("The current status is: "+values[7]);
                        System.out.println("Enter new Rent Status: (Yes for 'Collected' & No for 'Not Collected'");
                        values[7]= yesOrNo();
                        found=true;
                    }
                    else if(Objects.equals(option, "6")){
                        System.out.println("The current duration of stay is: "+values[8]);
                        System.out.println("Enter new duration of stay is: ");
                        values[8]= Duration()+" months";
                        found=true;
                    }
                    else{
                        System.out.println("Invalid Input. Try Again...");
                    }

                    data.add(values);
                    writeUpdatedCSV(filepath, data);
                    break;
                }
            }
            if (found) {
                System.out.println("Updated Successfully.");
            } else {
                System.out.println("The resident with this CNIC does not exist.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file: " + e.getMessage());
        }
    }

    public static void EmployeeDataUpdater(String filepath, String info) {                 // Method to update Employee Data
        Scanner in=new Scanner(System.in);

        // Read existing data from CSV file
        List<String[]> data = readCSV(filepath);

        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String Line;
            boolean found = false;
            while ((Line = reader.readLine()) != null) {
                data.removeIf(row -> row[0].equals(info));

                if (Line.contains(info)) {
                    String[] values = Line.split(",");
                    System.out.println("""
                            
                            Choose one of the following options:\s
                            0. To Exit
                            1. Age
                            2. Mobile number
                            3. Salary
                            4. Contract Time
                            5. Salary Status
                            Enter your option:""");
                    String option=in.next();
                    if(Objects.equals(option, "0")){
                        break;
                    }
                    else if(Objects.equals(option, "1")){
                        System.out.println("The current age is: "+values[2]);
                        System.out.println("Enter new age: ");
                        values[2]= age();
                        found=true;
                    }
                    else if(Objects.equals(option, "2")){
                        System.out.println("The current mobile number is: "+values[3]);
                        System.out.println("Enter new mobile number : ");
                        values[3]= mobNum();
                        found=true;
                    }
                    else if(Objects.equals(option, "3")){
                        System.out.println("The current Salary is: "+values[5]);
                        System.out.println("Enter new salary: ");
                        values[5]= "Rs." +salary();
                        found=true;
                    }
                    else if(Objects.equals(option, "4")){
                        System.out.println("The current Contract time is: "+values[6]);
                        System.out.println("Enter updated contract time: (Max limit is 12, after that you'll have to sign the contract again.)");
                        values[6]= Duration()+" months";
                        found=true;
                    }
                    else if(Objects.equals(option, "5")){
                        System.out.println("The current status is: "+values[7]);
                        System.out.println("Enter new Salary Status: (Yes for 'Paid' & No for 'Not Paid'");
                        values[7]= yesOrNo();
                        found=true;
                    }
                    else{
                        System.out.println("Invalid Input. Try Again...");
                    }

                    data.add(values);
                    writeUpdatedCSV(filepath, data);
                    break;
                }
            }
            if (found) {
                System.out.println("Updated Successfully.");
            } else {
                System.out.println("The resident with this CNIC does not exist.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file: " + e.getMessage());
        }
    }

    public static void ComplaintDataUpdater(String filepath, String info) {                 // Method to update Complaints
        Scanner in=new Scanner(System.in);

        // Read existing data from CSV file
        List<String[]> data = readCSV(filepath);

        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String Line;
            boolean found = false;
            while ((Line = reader.readLine()) != null) {
                data.removeIf(row -> row[0].equals(info));

                if (Line.contains(info)) {
                    String[] values = Line.split(",");
                    System.out.println("The previous complaint is: "+values[3]);
                    System.out.println("Enter Updated Complaint:");
                    values[3]=in.nextLine();
                    data.add(values);
                    writeUpdatedCSV(filepath, data);
                    found=true;
                    break;
                }
            }
            if (found) {
                System.out.println("Updated Successfully.");
            } else {
                System.out.println("No complaint against this CNIC.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file: " + e.getMessage());
        }
    }

    public static void viewMenu(){
        System.out.println("""
                           The menu for the hostel is:\s
                              Day   |       Breakfast        |        Lunch        |           Dinner
                           ---------------------------------------------------------------------------------------
                           Monday   | Omelette, Paratha, Tea |    Lobia, Chatni    | Chicken Biryani, Raita, Zarda
                           Tuesday  |  Egg fry, Paratha, Tea |        Curry        |     Mix Vegetables, Kheer
                           Wednesday|  Channa, Paratha, Tea  | Seasonal Vegetables |     Chicken Korma, Custard
                           Thursday |  Egg fry, Paratha, Tea | Daal Channa, Chatni | Chicken Pulao, Kebab, Raita
                           Friday   |   Nihari, Naan, Tea    |     Daal Chawal     |      Aloo Gosht, Kheer
                           Saturday | Omelette, Paratha, Tea |      Aloo Qeema     |      Daal Mash, Chatni
                           Sunday   |  Channa, Halwa, Kulcha |   Brunch on Sunday  |       Chicken Haleem""");
    }

    public static boolean searchCNIC(String path, String data) {                          // Method for Searching Resident to update complaint
        boolean found = false;
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String Line;
            while ((Line = reader.readLine()) != null) {
                if (Line.contains(data)) {
                    found = true;
                }
            }
            if (!found) {
                System.out.println("Resident with this CNIC does not exist in our database.\n");
            }

        } catch (IOException e) {
            System.out.println("An error occurred while reading the file: " + e.getMessage());
        }
        return found;
    }

    public static void employee() {
        Scanner in = new Scanner(System.in);
        String day;
        while (true) {
            System.out.println("""
                               
                               Choose one of the following options:\s
                               0. To Exit
                               1. If you are a Cook
                               2. If you are a Cleaner
                               3. If you are a Warden
                               Enter your option:""");
            String worker_options = in.next();
            if(Objects.equals(worker_options,"0")){
                break;
            }
            else if (Objects.equals(worker_options, "1")) {
                do {
                    System.out.println("""
                                                        
                            Enter the day for which you want the menu for:\s
                            0. To Exit
                            1. for Monday
                            2. for Tuesday
                            3. for Wednesday
                            4. for Thursday
                            5. for Friday
                            6. for Saturday
                            7. for Sunday
                            Enter your option:""");
                    day = in.next();
                    if (Objects.equals(day, "1")) {
                        System.out.println("The menu for today is:");
                        System.out.println("Breakfast: Omelette, Paratha, Tea\nLunch: Lobia, Chatni\nDinner: Chicken Biryani, Raita, Zarda");
                    } else if (Objects.equals(day, "2")) {
                        System.out.println("The menu for today is:");
                        System.out.println("Breakfast: Egg fry, Paratha, Tea\nLunch: Curry\nDinner: Mix Vegetables, Kheer");
                    } else if (Objects.equals(day, "3")) {
                        System.out.println("The menu for today is:");
                        System.out.println("Breakfast: Channa, Paratha, Tea\nLunch: Seasonal Vegetables\nDinner: Chicken Korma, Custard");
                    } else if (Objects.equals(day, "4")) {
                        System.out.println("The menu for today is:");
                        System.out.println("Breakfast: Egg fry, Paratha, Tea\nLunch: Daal Channa, Chatni\nDinner: Chiken Pulao, Kebab, Raita");
                    } else if (Objects.equals(day, "5")) {
                        System.out.println("The menu for today is:");
                        System.out.println("Breakfast: Nihari, Naan, Tea\nLunch: Daal Chawal\nDinner: Aloo Gosht, Kheer");
                    } else if (Objects.equals(day, "6")) {
                        System.out.println("The menu for today is:");
                        System.out.println("Breakfast: Omellete, Paratha, Tea\nLunch: Aloo Qeema\nDinner: Daal Mash, Chatni");
                    } else if (Objects.equals(day, "7")) {
                        System.out.println("The menu for today is:");
                        System.out.println("Brunch: Channa, Halwa, Kulcha\nDinner: Chicken Haleem");
                    }else if(Objects.equals(day,"0")){
                        break;
                    } else {
                        System.out.println("Invalid Input. Try Again...");
                    }
                } while (!Objects.equals(day, "0"));
            }

            else if(worker_options.equals("2")){
                do {
                    System.out.println("""
                                                       
                            Enter the day for which you want the schedule for:\s
                            0. To Exit
                            1. for Monday
                            2. for Tuesday
                            3. for Wednesday
                            4. for Thursday
                            5. for Friday
                            6. for Saturday
                            7. for Sunday
                            Enter your option:""");
                    day = in.next();
                    if (Objects.equals(day, "1")) {
                        System.out.println("The schedule for today is: \n"+
                                           "You have to clean the rooms 1-5 today.");
                    } else if (Objects.equals(day, "2")) {
                        System.out.println("The schedule for today is: \n"+
                                           "You have to clean the rooms 6-10 today.");
                    } else if (Objects.equals(day, "3")) {
                        System.out.println("The schedule for today is: \n"+
                                           "You have to clean the rooms 11-15 today.");
                    } else if (Objects.equals(day, "4")) {
                        System.out.println("The schedule for today is: \n"+
                                           "You have to clean the rooms 16-20 today.");
                    } else if (Objects.equals(day, "5")) {
                        System.out.println("The schedule for today is: \n"+
                                           "You have to clean the rooms 21-25 today.");
                    } else if (Objects.equals(day, "6")) {
                        System.out.println("The schedule for today is: \n"+
                                           "You have to clean the rooms 26-30 today.");
                    } else if (Objects.equals(day, "7")) {
                        System.out.println("Today is Sunday. Go and chill!");
                    }else if(Objects.equals(day,"0")){
                        break;
                    } else {
                        System.out.println("Invalid Input. Try Again...");
                    }
                } while (!Objects.equals(day, "0"));
            }

            else if(worker_options.equals("3")){
                do {
                    System.out.println("""
                                                       
                            Enter the day for which you want the schedule for:\s
                            0. To Exit
                            1. for Monday
                            2. for Tuesday
                            3. for Wednesday
                            4. for Thursday
                            5. for Friday
                            6. for Saturday
                            7. for Sunday
                            Enter your option:""");
                    day = in.next();
                    if (Objects.equals(day, "1")) {
                        System.out.println("The schedule for today is: \n"+
                                           "You have to check the rooms 1-5 today.");
                    } else if (Objects.equals(day, "2")) {
                        System.out.println("The schedule for today is: \n"+
                                           "You have to check the rooms 6-10 today.");
                    } else if (Objects.equals(day, "3")) {
                        System.out.println("The schedule for today is: \n"+
                                           "You have to check the rooms 11-15 today.");
                    } else if (Objects.equals(day, "4")) {
                        System.out.println("The schedule for today is: \n"+
                                           "You have to check the rooms 16-20 today.");
                    } else if (Objects.equals(day, "5")) {
                        System.out.println("The schedule for today is: \n"+
                                           "You have to check the rooms 21-25 today.");
                    } else if (Objects.equals(day, "6")) {
                        System.out.println("The schedule for today is: \n"+
                                           "You have to check the rooms 26-30 today.");
                    } else if (Objects.equals(day, "7")) {
                        System.out.println("You have to check the kitchen today.");
                    } else if(Objects.equals(day,"0")){
                        break;
                    } else {
                        System.out.println("Invalid Input. Try Again...");
                    }
                } while (!Objects.equals(day, "0"));
            }
            else{
                System.out.println("Invalid Input. Try Again...");
            }
            }
        }
    }