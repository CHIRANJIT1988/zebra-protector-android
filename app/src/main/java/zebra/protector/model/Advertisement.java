package zebra.protector.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CHIRANJIT on 4/26/2016.
 */
public class Advertisement
{

    public static List<Advertisement> advertisementList = new ArrayList<>();

    public int advertisement_id, store_id, category_id;
    public String message, timestamp, file_name, store_name;
    public double store_rating;


    public Advertisement()
    {

    }

    public Advertisement(int store_id, String store_name, int category_id, String file_name)
    {

        this.store_id = store_id;
        this.store_name = store_name;
        this.category_id = category_id;
        this.file_name = file_name;
    }


    public Advertisement(int store_id, String store_name, double store_rating, int category_id, String message, String file_name, String timestamp)
    {

        this.store_id = store_id;
        this.store_name = store_name;
        this.store_rating = store_rating;
        this.category_id = category_id;
        this.file_name = file_name;
        this.message = message;
        this.timestamp = timestamp;
    }
}
