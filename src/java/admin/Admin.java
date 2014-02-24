package admin;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author patricio alberto
 */
public class Admin implements java.io.Serializable {
    
    public int idAdmin;
    public String username;
    public String email;
    public String password;
    public String createTime;
    public String pwd1;
    public String pwd2;
    public int typeAdmin;

    public Admin() {
    }

    public int getTypeAdmin() {
        return typeAdmin;
    }

    public void setTypeAdmin(int typeAdmin) {
        this.typeAdmin = typeAdmin;
    }
    
    public String getPwd1() {
        return pwd1;
    }

    public void setPwd1(String pwd1) {
        this.pwd1 = pwd1;
    }

    public String getPwd2() {
        return pwd2;
    }

    public void setPwd2(String pwd2) {
        this.pwd2 = pwd2;
    }

    public int getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(int idAdmin) {
        this.idAdmin = idAdmin;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
            
}
