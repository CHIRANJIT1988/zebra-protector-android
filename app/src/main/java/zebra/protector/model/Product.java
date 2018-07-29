package zebra.protector.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 12-06-2015.
 */

public class Product implements Serializable
{

    public static List<Product> productList = new ArrayList<>();
    public static List<Product> productCategoryList = new ArrayList<>();
    public static List<Product> productSubCategoryList = new ArrayList<>();


    //private List<Product> productList = new ArrayList<>();
    //private List<Product> productSubCategoryList = new ArrayList<>();
    //private List<Product> productCategoryList = new ArrayList<>();


    public int product_id, category_id, sub_category_id, amount, quantity;
    public double price;
    public String product_name, category_name, category_thumbnail, sub_category_name, unit, product_thumbnail, date;


    public Product()
    {

    }


    public Product(int category_id, String category_name, String category_thumbnail)
    {
        this.category_id = category_id;
        this.category_name = category_name;
        this.category_thumbnail = category_thumbnail;
    }


    public Product(int category_id, int sub_category_id, String sub_category_name)
    {
        this.category_id = category_id;
        this.sub_category_id = sub_category_id;
        this.sub_category_name = sub_category_name;
    }


    public Product(int category_id, int sub_category_id, int product_id, int quantity, String date)
    {
        this.category_id = category_id;
        this.sub_category_id = sub_category_id;
        this.product_id = product_id;
        this.quantity = quantity;
        this.date = date;
    }


    public Product(int category_id, int sub_category_id, int product_id, String product_name, int amount, String unit, double price, String product_thumbnail)
    {

        this.category_id = category_id;
        this.sub_category_id = sub_category_id;
        this.product_id = product_id;
        this.product_name = product_name;
        this.amount = amount;
        this.unit = unit;
        this.price = price;
        this.product_thumbnail = product_thumbnail;
    }


    public void setProductId(int product_id)
    {
        this.product_id = product_id;
    }

    public int getProductId()
    {
        return this.product_id;
    }


    public void setCategoryId(int category_id)
    {
        this.category_id = category_id;
    }

    public int getCategoryId()
    {
        return this.category_id;
    }


    public void setSubCategoryId(int sub_category_id)
    {
        this.sub_category_id = sub_category_id;
    }

    public int getSubCategoryId()
    {
        return this.sub_category_id;
    }


    public void setProductName(String product_name)
    {
        this.product_name = product_name;
    }

    public String getProductName()
    {
        return this.product_name;
    }


    public void setCategoryName(String category_name)
    {
        this.category_name = category_name;
    }

    public String getCategoryName()
    {
        return this.category_name;
    }


    public void setCategoryThumbnail(String category_thumbnail)
    {
        this.category_thumbnail = category_thumbnail;
    }

    public String getCategoryThumbnail()
    {
        return this.category_thumbnail;
    }


    public void setSubCategoryName(String sub_category_name)
    {
        this.sub_category_name = sub_category_name;
    }

    public String getSubCategoryName()
    {
        return this.sub_category_name;
    }


    public void setAmount(int amount)
    {
        this.amount = amount;
    }

    public int getAmount()
    {
        return this.amount;
    }


    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }

    public int getQuantity()
    {
        return this.quantity;
    }


    public void setUnit(String unit)
    {
        this.unit = unit;
    }

    public String getUnit()
    {
        return this.unit;
    }


    public void setDatetime(String date)
    {
        this.date = date;
    }

    public String getDatetime()
    {
        return this.date;
    }


    public void setPrice(double price)
    {
        this.price = price;
    }

    public double getPrice()
    {
        return this.price;
    }


    public void setProductThumbnail(String product_thumbnail)
    {
        this.product_thumbnail = product_thumbnail;
    }

    public String getProductThumbnail()
    {
        return this.product_thumbnail;
    }



    /*public List<Product> getProductCategoryList()
    {

        productCategoryList.add(new Product(1, "Grocery", "grocery_thumb"));
        productCategoryList.add(new Product(2, "Fruits & vegetables", "fruit_thumb"));
        productCategoryList.add(new Product(3, "Bakery & sweet shops", "bakery_thumb"));
        productCategoryList.add(new Product(4, "Fish & meat", "fish_thumb"));
        productCategoryList.add(new Product(5, "Foods", "food_thumb"));
        productCategoryList.add(new Product(6, "Laundry", "laundry_thumb"));
        productCategoryList.add(new Product(7, "Pharmacy", "pharmacy_thumb"));

        return productCategoryList;
    }*/


    /*public List<Product> getProductSubCategoryList()
    {

        productSubCategoryList.add(new Product(1, 1, "rice & flours"));
        productSubCategoryList.add(new Product(1, 2, "pulses"));
        productSubCategoryList.add(new Product(1, 3, "edible oil"));
        productSubCategoryList.add(new Product(1, 4, "personal care"));
        productSubCategoryList.add(new Product(1, 5, "biscuits & beverages"));

        productSubCategoryList.add(new Product(2, 1, "Fruits"));
        productSubCategoryList.add(new Product(2, 2, "vegetables"));

        productSubCategoryList.add(new Product(3, 1, "cakes & pastries"));
        productSubCategoryList.add(new Product(3, 2, "cookies & biscuits"));
        productSubCategoryList.add(new Product(3, 3, "breads & sandwich"));
        productSubCategoryList.add(new Product(3, 4, "Mithais"));

        productSubCategoryList.add(new Product(4, 1, "Fresh"));
        productSubCategoryList.add(new Product(4, 2, "frozen"));

        productSubCategoryList.add(new Product(5, 1, "meals"));
        productSubCategoryList.add(new Product(5, 2, "fast foods"));

        productSubCategoryList.add(new Product(6, 1, "wash & iron"));
        productSubCategoryList.add(new Product(6, 2, "iron"));
        productSubCategoryList.add(new Product(6, 3, "dry clean"));

        productSubCategoryList.add(new Product(7, 1, "products"));
        productSubCategoryList.add(new Product(7, 2, "medicines"));

        return productSubCategoryList;
    }*/


    public List<Product> getProductSubCategoryList(int id)
    {

        List<Product> list = new ArrayList<>();

        //for(Product product: getProductSubCategoryList())
        for(Product product: productSubCategoryList)
        {
            if(product.getCategoryId() == id)
            {
                list.add(product);
            }
        }

        return list;
    }



    /*public List<Product> getProductList()
    {

        productList.add(new Product(1, 1, 1, "rice & flours 1", 500, "gm", 40, "chicken.jpg"));
        productList.add(new Product(1, 1, 2, "rice & flours 2", 1, "kg", 75.5,  "mutton.jpg"));
        productList.add(new Product(1, 1, 3, "rice & flours 3", 1, "kg", 85.5,  "mutton.jpg"));
        productList.add(new Product(1, 2, 4, "pulses 1", 1, "kg", 75.5,  "mutton.jpg"));
        productList.add(new Product(1, 2, 5, "pulses 2", 1, "kg", 75.5,  "mutton.jpg"));


        productList.add(new Product(2, 8, 6, "dry fruit - borges - california walnuts", 3, "litre", 30.5, "rice.jpg"));
        productList.add(new Product(2, 8, 7, "dry fruit - borges - california walnuts", 250, "gm", 400, "paratha.jpg"));
        productList.add(new Product(2, 9, 8, "dry fruit - borges - california walnuts", 10, "kg", 420.5, "paneer.jpg"));
        productList.add(new Product(2, 9, 9, "dry fruit - borges - california walnuts", 3, "box", 126, "drinks.jpg"));

        productList.add(new Product(7, 1, 10, "Glocuse", 2, "piece", 140, "drinks.jpg"));
        productList.add(new Product(7, 1, 11, "ORS", 1, "piece", 23, "drinks.jpg"));

        return productList;
    }*/


    public List<Product> getProductList(int category_id, int sub_category_id)
    {

        List<Product> list = new ArrayList<>();

        //for(Product product: getProductList())
        for(Product product: productList)
        {
            if(product.getCategoryId() == category_id && product.getSubCategoryId() == sub_category_id)
            {
                list.add(product);
            }
        }

        return list;
    }



    public static boolean countProduct(int category_id)
    {

        for(Product product: productList)
        {

            if(product.getCategoryId() == category_id)
            {
                return true;
            }
        }

        return false;
    }



    public Product getProduct(int category_id, int sub_category_id, int product_id)
    {

        //for(Product product: getProductList())
        for(Product product: productList)
        {
            if(product.getCategoryId() == category_id && product.getSubCategoryId() == sub_category_id && product.getProductId() == product_id)
            {
                return product;
            }
        }

        return null;
    }


    public static String getCategoryName(int category_id)
    {

        for(Product product: productCategoryList)
        {
            if(product.getCategoryId() == category_id)
            {
                return product.getCategoryName();
            }
        }

        return null;
    }
}