package zebra.protector.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CHIRANJIT on 8/16/2016.
 */
public class Category
{

    public static List<Category> categoryList = new ArrayList<>();
    public List<SubCategory> subCategoryList;

    public String category_id, category_name, description, image;
    public int is_guest_allowed;


    public Category()
    {

    }


    public Category(String category_id, String category_name, String description, String image, int is_guest_allowed, List<SubCategory> subCategoryList)
    {

        this.category_id = category_id;
        this.category_name = category_name;
        this.description = description;
        this.image = image;
        this.is_guest_allowed = is_guest_allowed;

        this.subCategoryList = subCategoryList;
    }
}
