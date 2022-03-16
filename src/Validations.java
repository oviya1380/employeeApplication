import java.util.Scanner;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.sql.Connection;
import java.sql.DriverManager;
import java.text.ParseException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;


public abstract class Validations{                                  //abstract class
	 public abstract String validCheck(String details,String conditions,String Invalid);//abstract method
	 public void employeeDetailsMethod(int noOfEmployee){
	   for(int count=1;count<=noOfEmployee;count++) {
       Details detailsEmployee=new Details();//object creation for class Details
	   System.out.println("\nEmployee:"+(count));
	   detailsEmployee.setEmployeeId(validCheck(" Id:","^ACE+[0-9]{4}","Please enter proper EmployeeId.No special characters are allowed.EmployeeId should begin with ACE followed by 4 digits"));
	   Scanner scanner =new Scanner(System.in);
	 while(true) {
		System.out.println("Enter your Employee Name:");
		String name=scanner.nextLine();
	 try {
		if(!name.matches("^(?=.{2,20}$)(([a-zA-Z])\\2?(?!\\2))+$")) {
		  throw new Exception();
		}
		}
	 catch(Exception error) {
			System.out.println("Please enter proper name .No special characters and neumerics are allowed");
			continue;
	      }
		detailsEmployee.setName(name);
		  break;
	    }
	   detailsEmployee.setEmailId(validCheck(" MailId:","^(?=.{2,30}$)(([a-zA-Z0-9])\\2?(?!\\2))+(?:\\."+"[a-zA-Z0-9]+)*@"+"(?:[a-zA-Z]+\\.)+[.com]{2,7}$","Please enter proper mailId Example:mail@domain.com. "+ "Domain must contains only alphabets.No special characters are allowed"));
	   detailsEmployee.setDob(dobValidation("DOB"));
	   detailsEmployee.setDoj(dobValidation("DOJ"));
	   try {
		   Class.forName("com.mysql.jdbc.Driver");
		   Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/employee_application?autoReconnect=true&useSSL=false","root","Oviya@1380");
		   PreparedStatement pstmt=con.prepareStatement("INSERT INTO employeedetails(EmployeeId,EmployeeName,EmployeeMailid,EmployeeDob,EmployeeDoj)VALUES(?,?,?,?,?)");
	       pstmt.setString(1,detailsEmployee.employeeId); 
	       pstmt.setString(2,detailsEmployee.name); 
	       pstmt.setString(3,detailsEmployee.emailId); 
	       pstmt.setString(4,detailsEmployee.dob); 
	       pstmt.setString(5,detailsEmployee.doj); 
	       pstmt.executeUpdate();
	       con.close();
	   }
	   catch(Exception e) {
		   System.out.println(e);
	   }
	   System.out.println();
		}
		System.out.println("Details has been added");
		System.out.println();
	}
	 //employee details display
	public void detailsDisplay(){
		 Scanner scanner =new Scanner(System.in);
		int number=1;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/employee_application?autoReconnect=true&useSSL=false","root","Oviya@1380");
			PreparedStatement prepStmt=con.prepareStatement("SELECT COUNT(*) FROM employeedetails");
			ResultSet record=prepStmt.executeQuery();
			record.next();
			int rows=record.getInt(1);
			if(rows==0) {
			System.out.println("Employee arraylist is empty.Add the details of employee and then display it");
		}else {
			System.out.println("Enter your display option:");
			System.out.println("1.Enter Id to display the employee:");
			System.out.println("2.Display full details");
			int disNum=scanner.nextInt();
		switch(disNum) {
		case 1:
			System.out.println("Enter employee id to display:");
			String idDisplay=scanner.next();
			PreparedStatement statementDsply=con.prepareStatement("SELECT * FROM employeedetails where EmployeeId=?");
			statementDsply.setString(1, idDisplay);
			ResultSet records=statementDsply.executeQuery();
			while(records.next()) {
		    System.out.println("\nEmployee Details:"+(number));
		    System.out.println("Employee Id : "+records.getString(1));
		    System.out.println("Name        : "+records.getString(2));
		    System.out.println("Email Id    : "+records.getString(3));
		    System.out.println("DOB         : "+records.getString(4));
		    System.out.println("DOJ         : "+records.getString(5));
		    number++;
		    }
			break;
		case 2:	
			PreparedStatement statementDisplay=con.prepareStatement("SELECT * FROM employeedetails");
			ResultSet recorders=statementDisplay.executeQuery();
			while(recorders.next()) {
		    System.out.println("\nEmployee Details:"+(number));
		    System.out.println("Employee Id : "+recorders.getString(1));
		    System.out.println("Name        : "+recorders.getString(2));
		    System.out.println("Email Id    : "+recorders.getString(3));
		    System.out.println("DOB         : "+recorders.getString(4));
		    System.out.println("DOJ         : "+recorders.getString(5));
		    number++;
		}
			break;
	}
		}
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}
	public void deleteDetails(String idEmployee) {
		try {
			   Class.forName("com.mysql.jdbc.Driver");
			   Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/employee_application?autoReconnect=true&useSSL=false","root","Oviya@1380");
			   PreparedStatement prepStmt=con.prepareStatement("DELETE FROM employeedetails WHERE EmployeeId=(?)");
			   prepStmt.setString(1, idEmployee);
			   int rows=prepStmt.executeUpdate();
			   if(rows==0) {
				   System.out.println("EmployeeId is not found.Enter proper EmployeeId");
			   }
			   else {
				   System.out.println("Details has been deleted");
			   }
		       con.close();
		   }
		 catch(Exception e){
			   System.out.println(e);
		   }
	     	}
	public void updateEmployeeDetails(String employeeId) {
		Scanner scanner=new Scanner(System.in);
		try {
			   Class.forName("com.mysql.jdbc.Driver");
			   Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/employee_application?autoReconnect=true&useSSL=false","root","Oviya@1380");
			   PreparedStatement prepStmt=con.prepareStatement("SELECT COUNT(*) FROM employeedetails WHERE EmployeeId=(?)");
			   prepStmt.setString(1, employeeId);
			   ResultSet record=prepStmt.executeQuery(); 
			   record.next();
			   int rs=record.getInt(1);
			if(rs==0) {
				   System.out.println("No id found");
			   }
			else {
		while(true) {
			   System.out.println("which field do you want to update?\n1.EmployeeID\n2.Employeename\n3.EmployeeEmail\n4.DateOfBirth\n5.DateOfJoining\n6.Exit");
			   int choose=scanner.nextInt();
			switch(choose) {
			case 1:
			    String id=validCheck(" Id to update:","^ACE+[0-9]{4}","Please enter proper EmployeeId.No special characters are allowed.EmployeeId should begin with ACE followed by 4 digits");
				PreparedStatement prepstmtId=con.prepareStatement("UPDATE employeedetails set EmployeeId=(?) WHERE EmployeeId=(?)");
			    prepstmtId.setString(1,id);
				prepstmtId.setString(2,employeeId);
				prepstmtId.executeUpdate();
				break;
			case 2:
				String name=validCheck("Name to update:","^(?=.{2,20}$)(([a-zA-Z])\\2?(?!\\2))+$","Please enter proper name .No special characters and neumerics are allowed");
				PreparedStatement prepstmtName=con.prepareStatement("UPDATE employeedetails set EmployeeName=(?) WHERE EmployeeId=(?)");
				prepstmtName.setString(1,name);
				prepstmtName.setString(2,employeeId);
				prepstmtName.executeUpdate();
				break;
			case 3:
				String mailId=validCheck("MailId to update:","^(?=.{2,30}$)(([a-zA-Z0-9])\\2?(?!\\2))+(?:\\."+"[a-zA-Z0-9]+)*@"+"(?:[a-zA-Z]+\\.)+[.com]{2,7}$","Please enter proper mailId Example:mail@domain.com.\nDomain must contains only alphabets.No special characters are allowed");
				PreparedStatement prepstmtMailId=con.prepareStatement("UPDATE employeedetails set EmployeeMailId=(?) WHERE EmployeeId=(?)");
				prepstmtMailId.setString(1,mailId);
				prepstmtMailId.setString(2,employeeId);
				prepstmtMailId.executeUpdate();
				break;
			case 4:
			    String dob=dobValidation("DOB");
			    PreparedStatement prepstmtDob=con.prepareStatement("UPDATE employeedetails set EmployeeDob=(?) WHERE EmployeeId=(?)");
			    prepstmtDob.setString(1,dob);
				prepstmtDob.setString(2,employeeId);
				prepstmtDob.executeUpdate();
				break;
			case 5:
				String doj=dobValidation("DOJ");
				PreparedStatement prepstmtDoj=con.prepareStatement("UPDATE employeedetails set EmployeeDoj=(?) WHERE EmployeeId=(?)");
				prepstmtDoj.setString(1,doj);
				prepstmtDoj.setString(2,employeeId);
				prepstmtDoj.executeUpdate();
				break;
				}
				if(choose==6) {
						break;
					}
			   }
			   con.close();
			   }
		}
			catch(Exception e) {
				   System.out.println(e);
		  }
		}
	
	//overridden
	public String dobValidation(String date) { 
		while(true) {
			String dateOfJoin=validCheck(" DateOfJoining in dd/mm/yyyy format:","^(29-02-(2000|2400|2800|(19|2[0-9](0[48]|[2468][048]|[13579][26]))))$"
	  					+"|^((0[1-9]|1[0-9]|2[0-8])-02-((19|2[0-9])[0-9]{2}))$"
	  					+"|^(0[1-9]|[12][0-9]|3[01])-(0[13578]|10|12)-((19|2[0-9])[0-9]{2})$"
	  					+"|^((0[1-9]|[12][0-9]|30)-(0[469]|11)-((19|2[0-9])[0-9]{2}))$","Enter the valid data birth."
	  							+ "Age should greater than 18 and less than 60");
		long validDays=0;
		SimpleDateFormat dateCheck =new SimpleDateFormat("dd-mm-yyyy");
		try {
			Date dobDate=dateCheck.parse(dateOfJoin);
			Date dDate = new Date();
			String stringDate=dateCheck.format(dDate);
			Date currentDate=dateCheck.parse(stringDate);
			validDays= ((currentDate.getTime()-dobDate.getTime())/86400000)%365;
			System.out.println(validDays);
			} 
		catch (ParseException error) {
	     	System.out.println(error);
		   }
		 if(validDays>0 && validDays<=7){
			return dateOfJoin;
			}
			System.out.println("Future dates and present dates are not allowed");
			continue;
			}
		  } 
      }

