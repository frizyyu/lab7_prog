package helpers;

import DBHelper.ReadFromDB;
import org.apache.commons.lang3.ArrayUtils;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class UserDB {
    private final String SELECT_USER_BY_USERNAME = String.format("SELECT * FROM %s WHERE %s = ?", "users", "username");
    private final String SELECT_USER_BY_FILENAME = String.format("SELECT * FROM %s WHERE %s = ?", "users", "lastFile");
    private final String INSERT_USER = String.format("INSERT INTO %s (%s, %s, %s) VALUES (?, ?, ?)", "users", "username", "pswd", "salt");
    String INSERT_FILE_NAME = String.format("UPDATE %s SET %s = ? WHERE username = '%s'", "users", "lastFile", "|");
    private DBManipulator dmb;

    public UserDB(DBManipulator dmb) {
        this.dmb = dmb;
    }
    public String getUserName(String fileName){
        PreparedStatement preparedSelectUserByUsernameAndPasswordStatement = null;
        try {
            preparedSelectUserByUsernameAndPasswordStatement =
                    dmb.getPreparedStatement(SELECT_USER_BY_FILENAME, false);
            preparedSelectUserByUsernameAndPasswordStatement.setString(1, fileName);
            ResultSet resultSet = preparedSelectUserByUsernameAndPasswordStatement.executeQuery();
            //Main.logger.info("Выполнен запрос SELECT_USER_BY_USERNAME_AND_PASSWORD.");
            String res = "";
            while (resultSet.next()){
                res += resultSet.getString("userName") + "|";
            }
            return res.substring(0, res.length()-1);
        } catch (SQLException exception) {
            //Main.logger.error("Произошла ошибка при выполнении запроса SELECT_USER_BY_USERNAME_AND_PASSWORD!");
            //throw new DatabaseHandlingException();
            return null;
        } finally {
            dmb.closePreparedStatement(preparedSelectUserByUsernameAndPasswordStatement);
        }
    }
    public String getFileName(String userName) {
        PreparedStatement preparedSelectUserByUsernameAndPasswordStatement = null;
        try {
            preparedSelectUserByUsernameAndPasswordStatement =
                    dmb.getPreparedStatement(SELECT_USER_BY_USERNAME, false);
            preparedSelectUserByUsernameAndPasswordStatement.setString(1, userName);
            ResultSet resultSet = preparedSelectUserByUsernameAndPasswordStatement.executeQuery();
            //Main.logger.info("Выполнен запрос SELECT_USER_BY_USERNAME_AND_PASSWORD.");
            if (resultSet.next()){
                return resultSet.getString("lastFile");
            }
        } catch (SQLException exception) {
            //Main.logger.error("Произошла ошибка при выполнении запроса SELECT_USER_BY_USERNAME_AND_PASSWORD!");
            //throw new DatabaseHandlingException();
            return null;
        } finally {
            dmb.closePreparedStatement(preparedSelectUserByUsernameAndPasswordStatement);
        }
        return null;
    }

    public boolean checkUserByUsernameAndPassword(String userName, String pswd){
        PreparedStatement preparedSelectUserByUsernameAndPasswordStatement = null;
        try {
            preparedSelectUserByUsernameAndPasswordStatement =
                    dmb.getPreparedStatement(SELECT_USER_BY_USERNAME, false);
            preparedSelectUserByUsernameAndPasswordStatement.setString(1, userName);
            ResultSet resultSet = preparedSelectUserByUsernameAndPasswordStatement.executeQuery();
            //String[] st = new String[4096];

            //System.out.println(ArrayUtils.addAll(pswd.toCharArray(), salt.toCharArray()));

            if (resultSet.next()) {
                pswd += resultSet.getString("salt");
                PasswordHasher pshshr = new PasswordHasher();
                //System.out.println(resultSet.getString("pswd"));
                //System.out.println(pshshr.hashing(pswd));
                if (pshshr.hashing(pswd).equals(resultSet.getString("pswd"))){
                    //ReadFromDB.fileName = resultSet.setString("lastFile");
                    return true;
                }
                //System.out.println(resultSet.getString("salt"));
            }
            return false;
            //Main.logger.info("Выполнен запрос SELECT_USER_BY_USERNAME_AND_PASSWORD.");
        } catch (SQLException exception) {
            exception.printStackTrace();
            //Main.logger.error("Произошла ошибка при выполнении запроса SELECT_USER_BY_USERNAME_AND_PASSWORD!");
            //throw new DatabaseHandlingException();
            return false;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } finally {
            dmb.closePreparedStatement(preparedSelectUserByUsernameAndPasswordStatement);
        }
    }

    public void updatelastFile(String filename, String userName){
        PreparedStatement preparedInsertUserStatement = null;
        try {
            preparedInsertUserStatement =
                    dmb.getPreparedStatement(INSERT_FILE_NAME.replace("'|'", String.format("'%s'", userName)), false);
            preparedInsertUserStatement.setString(1, filename);
            //System.out.println(salt);
            //System.out.println(pswd);
            if (preparedInsertUserStatement.executeUpdate() == 0) throw new SQLException();
            //Main.logger.info("Выполнен запрос INSERT_USER.");
            //System.out.println(salt);
            //System.out.println(pswd);
        } catch (SQLException exception) {
            exception.printStackTrace();
            //Main.logger.error("Произошла ошибка при выполнении запроса INSERT_USER!");
            //throw new DatabaseHandlingException();
        } finally {
            dmb.closePreparedStatement(preparedInsertUserStatement);
        }
    }

    public boolean insertUser(String userName, String pswd){
        PreparedStatement preparedInsertUserStatement = null;
        SaltCreator saltCr = new SaltCreator();
        String salt = saltCr.petersburgSalt();
        try {
            PasswordHasher pswdhshr = new PasswordHasher();
            pswd = pswdhshr.hashing(pswd + salt);
            preparedInsertUserStatement =
                    dmb.getPreparedStatement(INSERT_USER, false);
            preparedInsertUserStatement.setString(1, userName);
            preparedInsertUserStatement.setString(2, pswd);
            preparedInsertUserStatement.setString(3, salt);
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
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } finally {
            dmb.closePreparedStatement(preparedInsertUserStatement);
        }
    }
}
