package com.example.pleasedo;

import java.sql.*;

public class data {

    public static void main(String[] args){

        String nnm = "u";
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin","root","");
            System.out.println("Connection");

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM users where name = '"+nnm+"'");

            if(resultSet.next()){
                System.out.println("Success");
            }
            else{
                System.out.println("not found");

            }
//            while (resultSet.next()) {
//                String name = resultSet.getString("name");
//                System.out.println(name);
//            }

            connection.close();

        } catch (SQLException e) {
            System.out.println("error");
            e.printStackTrace(); // Print the stack trace for detailed error information
        }
    }
}
