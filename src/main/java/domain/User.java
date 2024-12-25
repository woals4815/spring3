package domain;

public class User {
    private String id;
    private String name;
    private String password;

    //자바빈의 규약을 따르는 클래스에 생성자를 명시적으로 추가할 땐 기본 생성자도 함께 정의해줘야 함
    public User() {}

    public User(String id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
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
}
