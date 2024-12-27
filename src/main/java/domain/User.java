package domain;

import dao.Level;

public class User {

    Level level;
    private String id;
    private String name;
    private String password;
    private int login;
    private int recommend;
    private String email;

    //자바빈의 규약을 따르는 클래스에 생성자를 명시적으로 추가할 땐 기본 생성자도 함께 정의해줘야 함
    public User() {}

    public User(String id, String name, String password, Level level, int login, int recommend, String email) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.level = level;
        this.login = login;
        this.recommend = recommend;
        this.email = email;
    }

    public Level getLevel() {
        return level;
    }
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public String getPassword() {
        return password;
    }

    public void setId(String id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setLevel(Level level) {
        this.level = level;
    }
    public int getLogin() {
        return login;
    }
    public void setLogin(int login) {
        this.login = login;
    }
    public int getRecommend() {
        return recommend;
    }
    public void setRecommend(int recommend) {
        this.recommend = recommend;
    }
    public void upgradeLevel() {
        if (this.level.getNext() != null) {
            this.level = this.level.getNext();
        }
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
