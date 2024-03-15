package org.example.Model;

/**
 * Clasa client reprezinta un obiect de tip client cu proprietatile id, name, address și age.
 */

public class client {

    private int id;
    private String name;
    private String address;
    private int age;

    public client(int id, String name, String address, int age)
    {
        this.id=id;
        this.name=name;
        this.address=address;
        this.age=age;

    }

    public client(String name, String address, int age)
    {
        super();
        this.name=name;
        this.address=address;
        this.age=age;

    }

    public client() {}

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }

    public void setAddress(String address) { this.address = address; }

    public int getAge() { return age; }

    public void setAge(int age) { this.age = age; }

    @Override
    public String toString() {
        return "Client: id=" + id + ", name=" + name + ", address=" + address +  ", age=" + age ;
    }

}
