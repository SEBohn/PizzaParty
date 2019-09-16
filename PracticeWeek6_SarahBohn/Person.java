//open package for class
package business;

//start of class for client
public class Person {
    
    private static final String DEFAULT_NAME = "Not Given";
    
    //variables for client name
    String firstName;
    String lastName;
    
    //constructors for name
    public Person()
    {
        this.firstName = DEFAULT_NAME;
        this.lastName = DEFAULT_NAME;
    }
    public Person(String firstName, String lastName) {
        setFirstName(firstName);
        setLastName(lastName);
    }
     public String getFirstName() {
        return firstName;
    }

    public final void setFirstName(String firstName) {
        this.firstName = firstName; 
    }
    public String getLastName() {
        return lastName;
    }
    public final void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getFullName() {
        return firstName + " " + lastName;
    }
    @Override
    public String toString() {
        return getFullName();
    }
}
