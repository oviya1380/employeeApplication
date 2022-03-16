/*Title       -Employee management system
 *Author     - Oviya 
 *Created at -06-10-2021
 *Updated at -12-11-2021
 *Reviewed by-Akshaya
 * */

import java.util.Scanner;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.lang.Thread;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
public  class Employee extends Validations{  // employee class inherits validation class
	Scanner scanner =new Scanner(System.in);
	public void employeeDetails() {
	System.out.println("\n**********Welcome to Employee management system**********");
	while(true) {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException error) {
			System.out.println(error);
		}
		 System.out.println("\nList of operations:");
		 System.out.println("1.Add new employee details:");
		 System.out.println("2.Update employee details :");
		 System.out.println("3.Delete employee details :");
		 System.out.println("4.Display employee details:");
		 System.out.println("5.Exit  :");
		 System.out.println("\nEnter your choice of operation:");
		 int choice=scanner.nextInt();
		 switch(choice) {
		 case 1:
			 System.out.println("No of employees want to add:");
			 int noOfEmployee=scanner.nextInt();
			 while(noOfEmployee<=10) {
			 employeeDetailsMethod(noOfEmployee);
			 break;
			 }
			 if(noOfEmployee>10) {
				 System.out.println("Employees count should be less than 10");
			 }
			 break;
		 case 2:
			 try{  
				Class.forName("com.mysql.jdbc.Driver");
				Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/employee_application?autoReconnect=true&useSSL=false","root","Oviya@1380");    
				Statement stmt=con.createStatement();  
				ResultSet rs=stmt.executeQuery("select * from employeedetails");  
				if(rs.next()==false) {
					System.out.println("Empty database");
				}
				else {
					System.out.println("Enter the EmployeeId which is to be update:");
					String idUpdate=scanner.next();
					updateEmployeeDetails(idUpdate);
				}
				con.close();  
				}catch(Exception e){ System.out.println(e);}  
			 break;
		case 3:
			 System.out.println("Enter the EmployeeId to remove:");
			 String idRemove=scanner.next();
			 deleteDetails(idRemove);
			 break;
		case 4:
			 detailsDisplay();
			 break;
		case 5:
			 break;
		 }
			 if(choice==5) {
				 break;
			 }
	}
	}
 public String validCheck(String details,String conditions,String invalid) {
	 Connection con = null;
	try {
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/employee_application?autoReconnect=true&useSSL=false","root","Oviya@1380");
	} 
	catch (SQLException e) {
		System.out.println(e);
	}
    	 while(true) {
	         int test=0;
	         System.out.println("Enter the employee "+details);
	         String data =scanner.next();
	         if(conditions=="^ACE+[0-9]{4}" || conditions=="^(?=.{2,30}$)(([a-zA-Z0-9])\\2?(?!\\2))+(?:\\."+"[a-zA-Z0-9]+)*@"+"(?:[a-zA-Z]+\\.)+[.com]{2,7}$"){
		       try {
		    	   PreparedStatement prpStmt=con.prepareStatement("SELECT count(*)FROM employeedetails WHERE EmployeeId=(?) or EmployeeMailId=(?)");
		           prpStmt.setString(1, data);
		           prpStmt.setString(2, data);
		           ResultSet record=prpStmt.executeQuery();
		           record.next();
		           int index=record.getInt(1);
		           if(index!=0) {
		    	   System.out.println("Duplicate values are not allowed in employee details.Already we have that employee Id");
			       test=1;
	     }
	     	}
		
	         catch(Exception e) {
	        	 System.out.println(e);
      	}	
	         }
	         if(test==1) {
	        	 continue;
	         }
	      try{
		   if(!data.matches(conditions)) {
		        throw new Exception();
				}return data;
			 }
		  catch(Exception error) {
				System.out.println(invalid);
				   continue;
			   }
		   }
		}
	  
	//overriding
	public String dobValidation(String date) { 
		if(date=="DOJ") {
			String dobValue=super.dobValidation(date);
			return dobValue;
		}
		else {
		while(true) {
			String dateOfBirth=validCheck(" DateOfBirth in dd/mm/yyyy in format:","^(29-02-(2000|2400|2800|(19|2[0-9](0[48]|[2468][048]|[13579][26]))))$"
	  					+"|^((0[1-9]|1[0-9]|2[0-8])-02-((19|2[0-9])[0-9]{2}))$"
	  					+"|^(0[1-9]|[12][0-9]|3[01])-(0[13578]|10|12)-((19|2[0-9])[0-9]{2})$"
	  					+"|^((0[1-9]|[12][0-9]|30)-(0[469]|11)-((19|2[0-9])[0-9]{2}))$","Enter the valid data birth."
	  							+ "Age should greater than 18 and less than 60");
		long ageValid=0;
		SimpleDateFormat dateCheck =new SimpleDateFormat("dd-mm-yyyy");
		try {
			Date dobDate=dateCheck.parse(dateOfBirth);
			Date dDate = new Date();
			String stringDate=dateCheck.format(dDate);
			Date currentDate=dateCheck.parse(stringDate);
			ageValid= ((currentDate.getTime()-dobDate.getTime())/86400000)/365;
			} 
		catch (ParseException error) {
		System.out.println(error);
		}
		if(ageValid>=18 && ageValid<=60) {
			return dateOfBirth;
			}
			System.out.println("Age is not between 18 and 60");
			continue;
		}
		}
		}	    
        }




	






