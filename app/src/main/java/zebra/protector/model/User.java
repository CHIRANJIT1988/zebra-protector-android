package zebra.protector.model;

/**
 * Created by CHIRANJIT on 7/22/2016.
 */
public class User
{

    public String user_id, name, email, password, phone_number, device_id, date_of_birth, location, gender, confirmation_code;


    public User()
    {

    }

    public User(String name, String email, String date_of_birth, String location, String gender)
    {

        this.name = name;
        this.email = email;
        this.date_of_birth = date_of_birth;
        this.location = location;
        this.gender = gender;
    }
}
