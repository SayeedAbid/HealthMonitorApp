package com.example.healthmonitorapp.ui.fragments;


import android.content.Context;

public class InsertFoodDB {

    Context ctx;

    public InsertFoodDB(Context ctx) {
        this.ctx = ctx;
    }
    public void setupinsertfood(String data){

        DBAdapter db = new DBAdapter(ctx);
        db.open();
        db.insertRecord("food","_id, food_title,food_cal,food_text",data);
        db.close();
    }

    public void setupInserttoCategories(String data){


        DBAdapter db = new DBAdapter(ctx);
        db.open();
        db.insertRecord("categories","_id, category_title,category_parent_id,category_imageA,category_text",data);
        db.close();
    }

    public void insertToAllCategories(){

        setupInserttoCategories("NULL ,'Drinks','0','',NULL");
        setupInserttoCategories("NULL ,'Soda','9','',NULL");

        setupInserttoCategories("NULL ,'Fruits and Vegetables','0','',NULL");
        setupInserttoCategories("NULL ,'Frozen Fruits and Vegetables','11','',NULL");
        setupInserttoCategories("NULL ,'Fruit','11','',NULL");
        setupInserttoCategories("NULL ,'Vegetables','11','',NULL");
        setupInserttoCategories("NULL ,'Canned Fruits and Vegetables','11','',NULL");

        setupInserttoCategories("NULL ,'Bread','0','',NULL");
        setupInserttoCategories("NULL ,'Bread','1','',NULL");
        setupInserttoCategories("NULL ,'Cereals','1','',NULL");
        setupInserttoCategories("NULL ,'Frozen Bread and Rolls','1','',NULL");
        setupInserttoCategories("NULL ,'Crispbread','1','',NULL");

        setupInserttoCategories("NULL ,'Dishes','0','',NULL");
        setupInserttoCategories("NULL ,'Rice','35','',NULL");
        setupInserttoCategories("NULL ,'Noodles','35','',NULL");
        setupInserttoCategories("NULL ,'Pasta','35','',NULL");
        setupInserttoCategories("NULL ,'Taco','35','',NULL");
        setupInserttoCategories("NULL ,'Pizza','35','',NULL");
        setupInserttoCategories("NULL ,'Burger','35','',NULL");


        setupInserttoCategories("NULL ,'Health','0','',NULL");
        setupInserttoCategories("NULL ,'Meal Subsitutes','16','',NULL");
        setupInserttoCategories("NULL ,'Protein Powder','16','',NULL");
        setupInserttoCategories("NULL ,'Protein Bars','16','',NULL");

        setupInserttoCategories("NULL ,'Meat and Fish','0','',NULL");
        setupInserttoCategories("NULL ,'Chicken','20','',NULL");
        setupInserttoCategories("NULL ,'Lamb','20','',NULL");
        setupInserttoCategories("NULL ,'Beef','20','',NULL");
        setupInserttoCategories("NULL ,'Mutton','20','',NULL");
        setupInserttoCategories("NULL ,'Sea Food','20','',NULL");
        setupInserttoCategories("NULL ,'Fishes','20','',NULL");

        setupInserttoCategories("NULL ,'Dairy Products and Eggs','0','',NULL");
        setupInserttoCategories("NULL ,'Eggs','27','',NULL");
        setupInserttoCategories("NULL ,'Cream and Sour Cream','27','',NULL");
        setupInserttoCategories("NULL ,'Yoghurt','27','',NULL");
        setupInserttoCategories("NULL ,'Cheese','27','',NULL");
        setupInserttoCategories("NULL ,'Milk','27','',NULL");
        setupInserttoCategories("NULL ,'Butter','27','',NULL");
        setupInserttoCategories("NULL ,'Margarine','27','',NULL");

        setupInserttoCategories("NULL ,'Dessert and Baking','0','',NULL");
        setupInserttoCategories("NULL ,'Baking','6','',NULL");
        setupInserttoCategories("NULL ,'Biscuit','6','',NULL");


        setupInserttoCategories("NULL ,'Snacks','0','',NULL");
        setupInserttoCategories("NULL ,'Nuts','42','',NULL");
        setupInserttoCategories("NULL ,'Potato Chips','42','',NULL");

    }


    public void insertAllfood(){

        setupinsertfood("NULL, 'Pasta','131','Gives Energy!'");
        setupinsertfood("NULL, 'Mashed Potatoes','88','Gives Energy!'");
        setupinsertfood("NULL, 'Noodles','138','Gives Energy!'");
        setupinsertfood("NULL, 'Oatmeal Porridge','389.1','High Calories'");
        setupinsertfood("NULL, 'Egg, whole cooked , hard boiled','155','Rich in Protein! Better for Low Blood Pressure , Anaemia'");
        setupinsertfood("NULL, 'Rice','130','High Carbohydrate'");
        setupinsertfood("NULL, 'Bread','265','Rich in Carbohydrate'");

        setupinsertfood("NULL, 'Grilled or boiled Lamb','294','Eat Occassionally!! Bad for Heart and Diabetic Patient'");
        setupinsertfood("NULL, 'Grilled or boiled Beef','250','Eat Occassionally!! Bad for Heart and Diabetic Patient'");
        setupinsertfood("NULL, 'Grilled or boiled Chicken','239','Rich in Protein'");
        setupinsertfood("NULL, 'Grilled or boiled Mutton','294','Eat Occassionally!! Bad for Heart and Diabetic Patient'");


        setupinsertfood("NULL, 'Vegetable Soup ','159','Healthy Food'");
        setupinsertfood("NULL, 'Barley','354','Adds Benefits to Heart,Blood Pressure'");
        setupinsertfood("NULL, 'Mushroom','22','Rich in Protein! Good for Vegetarian'");
        setupinsertfood("NULL, 'Fruit Salad(apple,orange,grapefruit)','50','Healthy Food! Good for Skin'");
        setupinsertfood("NULL, 'Walnuts','654','Reduce Risk of Heart Disease '");
        setupinsertfood("NULL, 'Chicken Salad','48','Healthy Food!'");
        setupinsertfood("NULL, 'Cooked Vegetables(cauliflower,spinach,peas)','48.18','Rich in Fibre'");
        setupinsertfood("NULL, 'Cooked or Boiled Beans(lentils,soybean)','116','Rich in Protein! Good for Vegetarian '");
        setupinsertfood("NULL, 'Tofu','76','Rich in Protein ! Good for Vegans'");
        setupinsertfood("NULL, 'Leafy Green Vegetables ','23','Very Healthy! Adds Fibre'");


        setupinsertfood("NULL, 'Salmon','208','Rich in Protein! Good For Heart Diseased Patients'");
        setupinsertfood("NULL, 'Sea Food(squids,crabs,shrimp)','99.7','Healthy Food!'");

        setupinsertfood("NULL, 'Dairy Products','46','Eat Carefully ! Some people are intolerant of it'");
        setupinsertfood("NULL, 'Cheese','402','High Calories! Bad for Heart and Diabetic Patient'");
        setupinsertfood("NULL, 'Cookie','502','Try to eat less'");
        setupinsertfood("NULL, 'Dark Chocolate','546','Good for Heart Disease Patients '");
        setupinsertfood("NULL, 'Milk','42','Completes Balanced Diet'");
        setupinsertfood("NULL, 'Butter','717','Bad for Heart Disease Patient'");
        setupinsertfood("NULL, 'Fat-Free Yoghurt','61','Healthy! Effective for Diabetic patient'");


        setupinsertfood("NULL, 'Sausage','301','Eat Occasionally'");
        setupinsertfood("NULL, 'French Fries','312','Eat Occasionally'");
        setupinsertfood("NULL, 'Soda','41','Avoid!!'");



    }

}

