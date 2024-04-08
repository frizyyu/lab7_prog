package helpers;

import DBHelper.ReadFromDB;
import supportive.MusicBand;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;

public class MusicBandDbManipulator {
    private DBManipulator dmb;
    private final String SELECT_ALL = String.format("SELECT * FROM %s", "|"); //replace table name
    private final String CREATE_TABLE_MB = String.format("CREATE TABLE IF NOT EXISTS %s(id serial PRIMARY KEY, name varchar(80), x BIGINT NOT NULL CHECK(x < 268), y REAL CHECK(y < 55), creationDate varchar(80) NOT NULL, numberOfParticipants INT CHECK(numberOfParticipants > 0), albumsCount INT NOT NULL CHECK(albumsCount > 0), genre varchar(80) NOT NULL, studioName varchar(80) NOT NULL, address varchar(80) NOT NULL, author varchar(80) NOT NULL)", "|");
    private final String INSERT_INTO_TABLE = String.format("INSERT INTO \"%s\"(\"name\", x, y, creationDate, numberOfParticipants, albumsCount, genre, studioName, address, author) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", "|");
    private final String DELETE_FROM_TABLE = String.format("DELETE FROM %s WHERE id = ? AND author = ?", "|");
    private final String SELECT_MAX_ID = String.format("SELECT MAX(id) AS ID FROM %s", "|");
    private final String DELETE_ALL_FROM_TABLE = String.format("DELETE FROM %s WHERE author = ?", "|");
    private final String UPDATE_TABLE = String.format("UPDATE %s SET name = ?, x = ?, y = ?, creationDate = ?, numberOfParticipants = ?, albumsCount = ?, genre = ?, studioName = ?, address = ?, author = ? WHERE id = ? AND author = ?", "|");
    String tablename;
    public MusicBandDbManipulator(DBManipulator dmb) {
        this.dmb = dmb;
    }

    public LinkedHashSet<MusicBand> OnAwake(String tablename) throws IOException {
        //read from db and write to cllection
        ReadFromDB dbreader = new ReadFromDB();
        this.tablename = tablename;
        return dbreader.read("", this.tablename, dmb);
    }

    public boolean tableCreator(String filename) throws SQLException {
        dmb.createTable(CREATE_TABLE_MB.replace("|", filename));
        return true;
    }

    public boolean insertElement(ArrayList<Object> items, String table){
        //('%s', %s, %s, '%s', %s, %s, '%s', '%s', '%s', '%s')
        PreparedStatement preparedInsertUserStatement = null;
        try {
            preparedInsertUserStatement =
                    dmb.getPreparedStatement(INSERT_INTO_TABLE.replace("|", table), true);
            //preparedInsertUserStatement.setString(1, table);
            //System.out.println(items);
            preparedInsertUserStatement.setString(1, (String) items.get(0));
            preparedInsertUserStatement.setLong(2, (Long) items.get(1));
            preparedInsertUserStatement.setDouble(3, (Double) items.get(2));
            preparedInsertUserStatement.setString(4, (String) items.get(3));
            preparedInsertUserStatement.setInt(5, (Integer) items.get(4));
            preparedInsertUserStatement.setInt(6, (Integer) items.get(5));
            preparedInsertUserStatement.setString(7, (String) items.get(6));
            preparedInsertUserStatement.setString(8, (String) items.get(7));
            preparedInsertUserStatement.setString(9, (String) items.get(8));
            preparedInsertUserStatement.setString(10,  dmb.getCurrUser());
            //System.out.println(salt);
            //System.out.println(pswd);
            if (preparedInsertUserStatement.executeUpdate() == 0) throw new SQLException();
            //Main.logger.info("Выполнен запрос INSERT_USER.");
            //System.out.println(salt);
            //System.out.println(pswd);
            return true;
        } catch (SQLException exception) {
            exception.printStackTrace();
            //Main.logger.error("Произошла ошибка при выполнении запроса INSERT_USER!");
            //throw new DatabaseHandlingException();
            return false;
        } finally {
            dmb.closePreparedStatement(preparedInsertUserStatement);
        }

    }

    public boolean deleteAll(String table){
        PreparedStatement preparedInsertUserStatement = null;
        try {
            preparedInsertUserStatement =
                    dmb.getPreparedStatement(DELETE_ALL_FROM_TABLE.replace("|", table), true);
            //preparedInsertUserStatement.setString(1, table);
            System.out.println(dmb.getCurrUser());
            preparedInsertUserStatement.setString(1, dmb.getCurrUser());
            //System.out.println(salt);
            //System.out.println(pswd);
            if (preparedInsertUserStatement.executeUpdate() == 0) throw new SQLException();
            //Main.logger.info("Выполнен запрос INSERT_USER.");
            //System.out.println(salt);
            //System.out.println(pswd);
            return true;
        } catch (SQLException exception) {
            //exception.printStackTrace();
            //Main.logger.error("Произошла ошибка при выполнении запроса INSERT_USER!");
            //throw new DatabaseHandlingException();
            return false;
        } finally {
            dmb.closePreparedStatement(preparedInsertUserStatement);
        }
    }

    public boolean deleteElement(ArrayList<Object> items, String table){
        //('%s', %s, %s, '%s', %s, %s, '%s', '%s', '%s', '%s')
        PreparedStatement preparedInsertUserStatement = null;
        try {
            preparedInsertUserStatement =
                    dmb.getPreparedStatement(DELETE_FROM_TABLE.replace("|", table), true);
            //preparedInsertUserStatement.setString(1, table);
            preparedInsertUserStatement.setLong(1, (Long) items.get(0));
            preparedInsertUserStatement.setString(2, dmb.getCurrUser());
            //System.out.println(salt);
            //System.out.println(pswd);
            if (preparedInsertUserStatement.executeUpdate() == 0) throw new SQLException();
            //Main.logger.info("Выполнен запрос INSERT_USER.");
            //System.out.println(salt);
            //System.out.println(pswd);
            return true;
        } catch (SQLException exception) {
            //exception.printStackTrace();
            //Main.logger.error("Произошла ошибка при выполнении запроса INSERT_USER!");
            //throw new DatabaseHandlingException();
            return false;
        } finally {
            dmb.closePreparedStatement(preparedInsertUserStatement);
        }

    }
    public boolean updateElement(ArrayList<Object> items, String table){
        PreparedStatement preparedInsertUserStatement = null;
        try {
            preparedInsertUserStatement =
                    dmb.getPreparedStatement(UPDATE_TABLE.replace("|", table), true);
            //preparedInsertUserStatement.setString(1, table);
            preparedInsertUserStatement.setString(1, (String) items.get(0));
            preparedInsertUserStatement.setLong(2, (Long) items.get(1));
            preparedInsertUserStatement.setDouble(3, (Double) items.get(2));
            preparedInsertUserStatement.setString(4, (String) items.get(3));
            preparedInsertUserStatement.setInt(5, (Integer) items.get(4));
            preparedInsertUserStatement.setInt(6, (Integer) items.get(5));
            preparedInsertUserStatement.setString(7, (String) items.get(6));
            preparedInsertUserStatement.setString(8, (String) items.get(7));
            preparedInsertUserStatement.setString(9, (String) items.get(8));
            preparedInsertUserStatement.setString(10,  dmb.getCurrUser());
            //System.out.println(salt);
            //System.out.println(pswd);
            if (preparedInsertUserStatement.executeUpdate() == 0) throw new SQLException();
            //Main.logger.info("Выполнен запрос INSERT_USER.");
            //System.out.println(salt);
            //System.out.println(pswd);
            return true;
        } catch (SQLException exception) {
            //exception.printStackTrace();
            //Main.logger.error("Произошла ошибка при выполнении запроса INSERT_USER!");
            //throw new DatabaseHandlingException();
            return false;
        } finally {
            dmb.closePreparedStatement(preparedInsertUserStatement);
        }
    }

    public String selectIds(String tablename){
        PreparedStatement preparedSelectUserByUsernameAndPasswordStatement = null;
        try {
            if (tablename == null){
                return null;
            }
            preparedSelectUserByUsernameAndPasswordStatement =
                    dmb.getPreparedStatement(SELECT_MAX_ID.replace("|", tablename), false);
            //preparedSelectUserByUsernameAndPasswordStatement.setString(1, tablename);
            ResultSet resultSet = preparedSelectUserByUsernameAndPasswordStatement.executeQuery();
            //System.out.println(resultSet.getString("id"));
            if (resultSet.next())
                return resultSet.getString("ID");
            return null;
            //return resultSet.next();
        } catch (SQLException exception) {
            exception.printStackTrace();
            //Main.logger.error("Произошла ошибка при выполнении запроса SELECT_USER_BY_USERNAME_AND_PASSWORD!");
            //throw new DatabaseHandlingException();
            return null;
        } finally {
            dmb.closePreparedStatement(preparedSelectUserByUsernameAndPasswordStatement);
        }
    }

    public String selectAll(String tablename){
        PreparedStatement preparedSelectUserByUsernameAndPasswordStatement = null;
        try {
            if (tablename == null){
                return null;
            }
            preparedSelectUserByUsernameAndPasswordStatement =
                    dmb.getPreparedStatement(SELECT_ALL.replace("|", tablename), false);
            //preparedSelectUserByUsernameAndPasswordStatement.setString(1, tablename);
            ResultSet resultSet = preparedSelectUserByUsernameAndPasswordStatement.executeQuery();
            String res = "";
            //System.out.println();
            //Main.logger.info("Выполнен запрос SELECT_USER_BY_USERNAME_AND_PASSWORD.");
            /*Object[][] data;
            String[] cn = {
                    "Id",
                    "Name",
                    "X",
                    "Y",
                    "Creation date",
                    "Number of participants",
                    "Albums count",
                    "Genre",
                    "Studio",
                    "Name",
                    "Address"
            };*/
            //res =
            /*while (resultSet.next()){
                res += "Id: " + resultSet.getString("id") + " ";
                res += "Name: " + resultSet.getString("name") + " ";
                res += "Coordinates: ";
                res += "X: " + resultSet.getString("x") + " ";
                res += "Y: " + resultSet.getString("y") + " ";
                res += "Creation date: " + resultSet.getString("creationDate") + " ";
                res += "Number of participants: " + resultSet.getString("numberOfParticipants") + " ";
                res += "Albums count: " + resultSet.getString("albumsCount") + " ";
                res += "Genre: " + resultSet.getString("genre") + " ";
                res += "Studio: ";
                res += "Name: " + resultSet.getString("studioName") + " ";
                res += "Address: " + resultSet.getString("address") + " ";
                res += "\n";
            }*/
            while (resultSet.next()){
                res += String.format("{\"id\":%s,\"name\":\"%s\",\"coordinates\":{\"x\":%s,\"y\":%s},\"creationDate\":%s,\"numberOfParticipants\":%s,\"albumsCount\":%s,\"genre\":\"%s\",\"studio\":{\"name\":\"%s\",\"address\":\"%s\"}},,,",
                        resultSet.getString("id"), resultSet.getString("name"), resultSet.getString("x"), resultSet.getString("y"),
                        resultSet.getString("creationDate"), resultSet.getString("numberOfParticipants"), resultSet.getString("albumsCount"),
                        resultSet.getString("genre"), resultSet.getString("studioName"), resultSet.getString("address"));
            }
            //CommandLine.Help.TextTable tt = new CommandLine.Help.TextTable(cn, d);
            try {
                return res.substring(0, res.length() - 3);
            } catch (StringIndexOutOfBoundsException a){
                return null;
            }
            //return resultSet.next();
        } catch (SQLException exception) {
            //Main.logger.error("Произошла ошибка при выполнении запроса SELECT_USER_BY_USERNAME_AND_PASSWORD!");
            //throw new DatabaseHandlingException();
            return null;
        } finally {
            dmb.closePreparedStatement(preparedSelectUserByUsernameAndPasswordStatement);
        }
    }
}
