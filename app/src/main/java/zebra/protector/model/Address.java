package zebra.protector.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by CHIRANJIT on 10/8/2015.
 */
public class Address implements Serializable
{


    public static List<Address> addressList = new ArrayList<>();

    public int address_id, user_id;
    public String name, pincode, phone_no, address, landmark, city, state, country;


    public Address()
    {

    }


    public Address(int address_id, int user_id, String name, String pincode, String phone_no, String landmark, String address, String city, String state, String country)
    {

        this.address_id = address_id;
        this.user_id = user_id;
        this.name = name;
        this.pincode = pincode;
        this.phone_no = phone_no;
        this.landmark = landmark;
        this.address = address;
        this.city = city;
        this.state = state;
        this.country = country;
    }


    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return this.name;
    }


    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getAddress()
    {
        return this.address;
    }
}