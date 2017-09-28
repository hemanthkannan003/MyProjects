package databaseDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import controller.LoginController;
import model.*;


public class StudentDAO extends DataBaseConnector {

	public static String  temp_user_name;

	public Student create(Student student) {

		String query = "INSERT INTO krose1_student_info (id, name, password) VALUES (? ,?, ?) ;";
		try(PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){
			statement.setString(1, student.getId());
			statement.setString(2, student.getName());
			statement.setString(3, student.getPassword());
			statement.executeUpdate();
		} catch(SQLException e){
			student = null;
			System.out.println("Error Creating Student: " + e);
		}
		return student;
	}

	public List<Student> getstudent() {
		List<Student> student = new ArrayList<>();
		String query = "SELECT * FROM krose1_student_info;";
		try(PreparedStatement statement = connection.prepareStatement(query)){
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()) {
				Student su = new Student();
				su.setId(resultSet.getString("id"));
				su.setName(resultSet.getString("name"));
				student.add(su);

			}
		} catch(SQLException e){
			System.out.println("Error fetching Accounts: " + e);
		}
		return student;
	}

	public List<Course> getstudentcourse() {
		List<Course> course = new ArrayList<>();
		String query = "SELECT * FROM krose1_student_course_info WHERE student_name=?;";
		try(PreparedStatement statement = connection.prepareStatement(query)){
			statement.setString(1,temp_user_name);
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()) {
				System.out.println("I got the results from the student view");
				Course su = new Course();
				su.setId(resultSet.getString("course_id"));
				su.setName(resultSet.getString("course_name"));
				course.add(su);

			}
		} catch(SQLException e){
			System.out.println("Error in feching details of course relate to student: " + e);
		}
		return course;
	}


	public void delete(Student stu){
		//		String query = "INSERT INTO `dbfp`.`sshekha1_teachingasst` (`TA_id`, `username`, `password`, `TA_fname`, `TA_lname`, `id`) VALUES (?,?,?,?,?,?);";
		String query = "delete from krose1_student_info where id = ?;";
		try(PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){
			statement.setString(1, stu.getId());

			statement.executeUpdate();
			System.out.println("Student Deleted sucessfully");

		} catch(SQLException e){
			stu = null;
			System.out.println("Teaching assistant has not been deleted : " + e);
		}
	}

	public void deletestudentcoursedata(Course cou){
		int registernumber=20002;
		String selectRegister ="select Register_Id FROM krose1_student_course_info WHERE student_name=? AND course_id=?;";
		try(PreparedStatement statement = connection.prepareStatement(selectRegister)){
			statement.setString(1,temp_user_name);
			System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXStuden"+temp_user_name);
			statement.setString(2,cou.getId());
			System.out.println(cou.getId());
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()){
				System.out.println("This is been printed"+resultSet.getInt("Register_Id"));
				registernumber=resultSet.getInt(1);
			}
			System.out.println(registernumber);

		} catch(SQLException e){
			System.out.println("Error in feching details of course relate to student: " + e);
		}
		String query = "delete FROM krose1_student_course_info where Register_Id = ?;";
		try(PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){
			statement.setInt(1, registernumber);

			statement.executeUpdate();
			System.out.println("The course Mapped to student deleted sucessfuly");

		} catch(SQLException e){
			cou = null;
			System.out.println("Problem in deleting the value of course mapped to student: " + e);
		}
	}


	public void update(Student stu){

		String query = "update krose1_student_info set name=? , password=? Where id = ?;";
		//String query = "update student_details set  student_Name= ? ,student_gender= ? , student_email= ?  Where student_id=? ;";
		try(PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){
			statement.setString(1, stu.getName());
			statement.setString(2, stu.getPassword());
			statement.setString(3, stu.getId());

			statement.executeUpdate();
			System.out.println("Student details updated sucessfully sucessfully");

		} catch(SQLException e){
			stu = null;
			System.out.println("Teaching assistant has not been deleted : " + e);
		}
	}

	public List<Student> getstudentforupdate() {
		List<Student> student = new ArrayList<>();
		String query = "SELECT id FROM krose1_student_info;";
		try(PreparedStatement statement = connection.prepareStatement(query)){
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()) {
				Student su = new Student();
				su.setId(resultSet.getString("id"));
				student.add(su);

			}
		} catch(SQLException e){
			System.out.println("Error fetching Accounts: " + e);
		}
		return student;
	}

	public Course addstudentcourse(Course cur){
		System.out.println("I'm in DAO");
		String query = "INSERT INTO krose1_student_course_info (student_name, course_id,course_name) VALUES (? ,?, ?) ;";
		try(PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){
			System.out.println(temp_user_name);
			statement.setString(1,temp_user_name);
			statement.setString(2, cur.getId());
			statement.setString(3, cur.getName());

			statement.executeUpdate();
		} catch(SQLException e){
			cur = null;
			System.out.println("Error Creating Student: " + e);
		}
		return cur;

	}


	public String loginstudentfinder (String username, String password){

		String condition =null;
		System.out.println("usename in DAO before Query execution:-"+username);

		String query = "SELECT * FROM krose1_student_info WHERE name = ?;";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, username);
			ResultSet resultSet = statement.executeQuery();

			if (resultSet == null){
				LoginController lgc =new LoginController();
				lgc.errorlogin();
			}

			else if (resultSet.next()) {
				Student sud = new Student();
				sud.setPassword(resultSet.getString("password"));
				System.out.println(username);
				System.out.println("password from screen"+password);
				System.out.println("From Data Base"+resultSet.getString("password"));
				if (password.equalsIgnoreCase(resultSet.getString("password"))){

					LoginController lgc =new LoginController();
					lgc.loadStudentLogin();
					temp_user_name=username;

				}

				else{
					LoginController lgc =new LoginController();
					lgc.errorlogin();
				}
			}

			System.out.println(temp_user_name);

		} catch (SQLException e) {
			System.out.println("Error Finding Teaching Assistant by Username: "
					+ e);
		}

		return condition;


	}

}
