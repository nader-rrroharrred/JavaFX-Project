/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 *
 * @author Masterz
 */
public class Accounts {

    int id, user_id, account_number;
    String username, currency;
    double balance;
    LocalDate creation_date;

    public Accounts() {
    }

    public Accounts(int user_id, int account_number, String username, String currency, double balance) {
        this.user_id = user_id;
        this.account_number = account_number;
        this.username = username;
        this.currency = currency;
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getAccount_number() {
        return account_number;
    }

    public void setAccount_number(int account_number) {
        this.account_number = account_number;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public LocalDate getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(LocalDate creation_date) {
        this.creation_date = creation_date;
    }

    public int create() throws SQLException, ClassNotFoundException {
        Connection c = DB.getInstance().getConnection();
        PreparedStatement ps = null;
        int recordCounter = 0;
        String sql = "INSERT INTO accounts (user_id, account_number, username, currency, balance) VALUES (?, ?, ?, ?, ?)";
        ps = c.prepareStatement(sql);
        ps.setInt(1, this.getUser_id());
        ps.setInt(2, this.getAccount_number());
        ps.setString(3, this.getUsername());
        ps.setString(4, this.getCurrency());
        ps.setDouble(5, this.getBalance());
        recordCounter = ps.executeUpdate();
        if (recordCounter > 0) {
            System.out.println("Account with account number : " + this.getAccount_number() + " was created successfully!");
        }
        if (ps != null) {
            ps.close();
        }
        c.close();
        return recordCounter;
    }

    public static Accounts getAccounts() throws SQLException, ClassNotFoundException {
        Connection c = DB.getInstance().getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Accounts account = null;
        String sql = "SELECT * FROM accounts";
        ps = c.prepareStatement(sql);
        rs = ps.executeQuery();
        if (rs.next()) {
            account = new Accounts(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4),
                    rs.getDouble(5));
        }
        if (ps != null) {
            ps.close();
        }
        c.close();
        return account;
    }

    public int update() throws SQLException, ClassNotFoundException {
        Connection c = DB.getInstance().getConnection();
        PreparedStatement ps = null;
        int recordCounter = 0;
        String sql = "UPDATE accounts SET user_id=?, username=?, currency=?, balance=?WHERE account_number=?";
        ps = c.prepareStatement(sql);
        ps.setInt(1, this.getUser_id());
        ps.setString(2, this.getUsername());
        ps.setString(3, this.getCurrency());
        ps.setDouble(4, this.getBalance());
        ps.setInt(5, this.getAccount_number());
        recordCounter = ps.executeUpdate();
        if (recordCounter > 0) {
            System.out.println("Account with account number : " + this.getAccount_number() + " was updated successfully!");
        }
        if (ps != null) {
            ps.close();
        }
        c.close();
        return recordCounter;
    }

    public int delete() throws SQLException, ClassNotFoundException {
        Connection c = DB.getInstance().getConnection();
        PreparedStatement ps = null;
        int recordCounter = 0;
        String sql = "DELETE FROM accounts WHERE account_number=?";
        ps = c.prepareStatement(sql);
        ps.setInt(1, this.getAccount_number());
        recordCounter = ps.executeUpdate();
        if (recordCounter > 0) {
            System.out.println("Account with account number : " + this.getAccount_number() + " was deleted successfully!");
        }
        if (ps != null) {
            ps.close();
        }
        c.close();
        return recordCounter;
    }

}
