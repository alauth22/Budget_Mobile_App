package com.example.finalproject.GPS;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.google.android.gms.maps.model.LatLng;
import java.util.ArrayList;
import java.util.List;

public class DBHelperMap extends SQLiteOpenHelper {

    //database name
    private static final String DATABASE_NAME = "banks.db";
    private static final int DATABASE_VERSION = 1;
    //name of my three tables.
    private static final String LOCATIONS = "Locations";
    private static final String SERVICES = "Services";
    private static final String LOCATION_SERVICES = "LocationServices";

    //constructor
    public DBHelperMap(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        //locations table crate
        String createLocationsTable = "CREATE TABLE " + LOCATIONS + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, " +
                "address TEXT NOT NULL," +
                "latitude REAL NOT NULL, " +
                "longitude REAL NOT NULL);";

        //services table create
        String createServicesTable = "CREATE TABLE " + SERVICES + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL);";

        //bridge table here for many-to-many relationship
        String createLocationServicesTable = "CREATE TABLE " + LOCATION_SERVICES + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "location_id INTEGER NOT NULL, " +
                "service_id INTEGER NOT NULL, " +
                "FOREIGN KEY (location_id) REFERENCES " + LOCATIONS + "(id), " +
                "FOREIGN KEY (service_id) REFERENCES " + SERVICES + "(id));";

        //now execute all the queries so that we have all 3 tables.
        db.execSQL(createLocationsTable);
        db.execSQL(createServicesTable);
        db.execSQL(createLocationServicesTable);

        //insert the data.
        insertData(db);

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        // Drop existing tables
        db.execSQL("DROP TABLE IF EXISTS " + LOCATIONS);
        db.execSQL("DROP TABLE IF EXISTS " + SERVICES);
        db.execSQL("DROP TABLE IF EXISTS " + LOCATION_SERVICES);

        //recreate the tables if they are to be dropped.
        onCreate(db);

    }

    private void insertData(SQLiteDatabase db)
    {
        // Insert Locations
        db.execSQL("INSERT INTO " + LOCATIONS + " (name, address, latitude, longitude) VALUES " +
                "('North Shore Bannk', '1620 W Mason St, Green Bay, WI', 44.52473, -88.06576)," +
                "('Chase Bank', '911 Packerland Dr, Green Bay, WI', 44.52557, -88.09858)," +
                "('Nicolet National Bank', '111 N Washington St, Green Bay, WI', 44.51521, -88.01614)," +
                "('Capital Credit Union', '825 Morris Ave, Green Bay, WII', 44.49424, -88.05967)," +
                "('Associated Bank', '369 Cardinal Ln, Green Bay, WI', 44.55156, -88.08566);");

        // Insert Services
        db.execSQL("INSERT INTO " + SERVICES + " (name) VALUES " +
                "('ATM')," +
                "('Mortgage Loans')," +
                "('Investment Advising')," +
                "('Savings Accounts')," +
                "('Checking Accounts');");

        //Need to allow for each bank to offer multiple services because that is how we can do a many-to-many relationship
        db.execSQL("INSERT INTO " + LOCATION_SERVICES + " (location_id, service_id) VALUES " +
                "(1, 1), (1, 2)," +
                "(2, 3), (2, 5)," +
                "(3, 3), (3, 4)," +
                "(4, 2), (4, 3)," +
                "(5, 1), (5, 5);");

    }


    //get all the locations and their coordinates.
    public List<LatLng> getLocations() {
        List<LatLng> locations = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT latitude, longitude FROM " + LOCATIONS, null);

        while(cursor.moveToNext())
        {
            //get the index based off of this query in this method.
            double latitude = cursor.getDouble(0);
            double longitude = cursor.getDouble(1);
            locations.add(new LatLng(latitude, longitude));
        }

        cursor.close();
        db.close();

        return locations;
    }


    //get all the names of the banks.
    public List<String> getNames() {
        List<String> services = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT name  FROM " + LOCATIONS, null);

        while(cursor.moveToNext())
        {
            String name = cursor.getString(0);
            services.add(name);
        }

        cursor.close();
        db.close();
        return services;
    }



    //need a method to get the locations ID's so that we can query the inner join tables to get services.
    public List<Integer> getLocationIds() {
        List<Integer> locationIds = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id FROM " + LOCATIONS, null);
        while (cursor.moveToNext()) {
            int locationId = cursor.getInt(0);
            locationIds.add(locationId);
        }
        cursor.close();
        db.close();
        return locationIds;
    }


    //method to get the services, so have to do an inner join.
    public List<String> getServices (int locationId) {
        List<String> services = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT s.name " +
                "FROM " + SERVICES + " s " +
                "INNER JOIN " + LOCATION_SERVICES + " l ON s.id = l.service_id " +
                "WHERE l.location_id = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(locationId)});

        while (cursor.moveToNext()) {
            String name = cursor.getString(0);
            services.add(name);
        }

        cursor.close();
        db.close();

        return services;
    }

}