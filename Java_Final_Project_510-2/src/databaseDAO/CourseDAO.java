 package databaseDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.*;


public class CourseDAO extends DataBaseConnector{

	public Course create(Course course) {
		String query = "INSERT INTO krose1_course_info (id, name, type) VALUES (? ,?, ?) ;";
		try(PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){
			statement.setString(1, course.getId());
            statement.setString(2, course.getName());
            statement.setString(3, course.getType());
            statement.executeUpdate();
        } catch(SQLException e){
        	course = null;
            System.out.println("Error Creating Student: " + e);
        }
		return course;
	}
	public List<Course> getcourse() {
		List<Course> course = new ArrayList<>();
		String query = "SELECT * FROM krose1_course_info;";
		try(PreparedStatement statement = connection.prepareStatement(query)){
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
            	Course co = new Course();
            	co.setId(resultSet.getString("id"));
            	co.setName(resultSet.getString("name"));
            	co.setType(resultSet.getString("type"));
            	course.add(co);

            }
        } catch(SQLException e){
            System.out.println("Error fetching Accounts: " + e);
        }
		return course;
	}

	public void update(Course cou){

		String query = "update krose1_course_info set name=? , type=? Where id = ?;";
		//String query = "update student_details set  student_Name= ? ,student_gender= ? , student_email= ?  Where student_id=? ;";
		try(PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){
			statement.setString(1, cou.getName());
			statement.setString(2, cou.getType());
			statement.setString(3, cou.getId());

            statement.executeUpdate();
            System.out.println("Student details updated sucessfully sucessfully");

        } catch(SQLException e){
        	cou = null;
            System.out.println("Teaching assistant has not been deleted : " + e);
        }
	}

//	public Course addstudentcourse(Course cou){
//
//		String query = "INSERT INTO krose1_student_course_info (id, name, type) VALUES (? ,?, ?) ;";
//		try(PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){
//			statement.setString(1, cou.getId());
//            statement.setString(2, cou.getName());
//            statement.executeUpdate();
//        } catch(SQLException e){
//        	cou = null;
//            System.out.println("Error Creating Student: " + e);
//        }
//		return cou;
//
//	}



	public void delete(Course cou){
		String query = "delete from krose1_course_info where id = ?;";
		try(PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){
			statement.setString(1, cou.getId());

            statement.executeUpdate();
            System.out.println("Student Deleted sucessfully");

        } catch(SQLException e){
        	cou = null;
            System.out.println("Teaching assistant has not been deleted : " + e);
        }
	}
}
