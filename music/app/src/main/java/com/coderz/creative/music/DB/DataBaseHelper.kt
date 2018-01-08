package com.coderz.creative.music.DB

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.coderz.creative.music.Model.Catagory
import com.coderz.creative.music.Model.Spend
import kotlinx.android.synthetic.main.fragment_add.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by kamal on 16/12/17.
 */


class DataBaseHelper(context: Context?, name: String?, factory: SQLiteDatabase.CursorFactory?, version: Int) : SQLiteOpenHelper(context, name, factory, version) {
    companion object {
        const val CATAGORY_TABLE ="CATAGORY"
        const val CATAGORY_OTHER_TABLE ="CATAGORY_OTHER"
        const val SPEND_TRACKER_TABLE = "SPEND_TRACKER"

        const val CATAGORY_NAME = "name"
        const val CATAGORY_ID = "id"
        const val TYPE="type"

        const val TITLE = "title"
        const val DESC = "desc"
        const val SPEND_ID ="id"
        const val AMOUNT= "amount"
        const val DATE ="date"

        const val CREATE_CATAGORY_TABLE = "create table "+ CATAGORY_TABLE + "(" + CATAGORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + CATAGORY_NAME+ " TEXT )"
        const val CREATE_OTHER_CATAGORY_TABLE = "create table "+ CATAGORY_OTHER_TABLE + "(" + CATAGORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + CATAGORY_NAME+ " TEXT,"+ TYPE+" TEXT)"
        const val CREATE_SPEND_TABLE = "create table "+ SPEND_TRACKER_TABLE+ "(" + SPEND_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + AMOUNT + " DOUBLE," + DATE + " DATE," + TITLE +" TEXT,"+ DESC+" TEXT,"+ TYPE+ " Text)"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        println("create database table")
        db?.execSQL(CREATE_CATAGORY_TABLE)
        db?.execSQL(CREATE_OTHER_CATAGORY_TABLE)
        db?.execSQL(CREATE_SPEND_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {

    }

    fun createCatagory(catagory:Catagory){
        var db= this.writableDatabase
        var values  = ContentValues()
        values.put(CATAGORY_NAME,catagory.name)
        values.put(CATAGORY_ID,catagory.id)
        values.put(TYPE,catagory.type)
        db.insert(CATAGORY_OTHER_TABLE,null,values)
    }

    fun getAllCatgory(): List<Catagory>{
        var db= this.readableDatabase
        var query ="select * from "+ CATAGORY_OTHER_TABLE
        var cursor = db.rawQuery(query,null)
        var catagoryList = ArrayList<Catagory>()
        if(cursor.moveToFirst()){
            do {
                var obj = Catagory(cursor.getInt(cursor.getColumnIndex(CATAGORY_ID)),cursor.getString(cursor.getColumnIndex(CATAGORY_NAME)),cursor.getString(cursor.getColumnIndex(TYPE)))
                catagoryList.add(obj)
            }while(cursor.moveToNext())
        }
        return catagoryList
    }

    fun closeDB(){
        var db = this.getReadableDatabase()
        if(db!=null && db.isOpen)
            db.close()
    }

    fun createSpend(spend : Spend){
        var db= this.writableDatabase
        var values  = ContentValues()
        values.put(AMOUNT,spend.amount)

        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val date = sdf.format(spend.date)
        values.put(DATE,date)

        values.put(TITLE, spend.title)
        values.put(DESC, spend.desc)
        values.put(TYPE, spend.catagory)

        db.insert(SPEND_TRACKER_TABLE,null,values)
    }

    fun getAllSpend() : List<Spend>{
        var db= this.readableDatabase
        var query ="select * from "+ SPEND_TRACKER_TABLE;
        var cursor = db.rawQuery(query,null)
        var spendList = ArrayList<Spend>()
        if(cursor.moveToFirst()){
            do {
                val sdf = SimpleDateFormat("yyyy-MM-dd")
                val date = sdf.parse(cursor.getString(cursor.getColumnIndex(DATE)))
                var obj = Spend(cursor.getInt(cursor.getColumnIndex(SPEND_ID)),cursor.getDouble(cursor.getColumnIndex(AMOUNT)),date,cursor.getString(cursor.getColumnIndex(TITLE)),cursor.getString(cursor.getColumnIndex(DESC)),cursor.getString(cursor.getColumnIndex(TYPE)))
                spendList.add(obj)
            }while(cursor.moveToNext())
        }
        return spendList
    }

    fun getDateDaysAgo(days:Int):String {
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val calender = Calendar.getInstance()
        calender.time = Date()
        calender.add(Calendar.DAY_OF_MONTH,-days)
        val date = sdf.format(calender.time)
        return date
    }

    fun getAllSpendForToday() : List<Spend>{
        var db= this.readableDatabase
        val sdf = SimpleDateFormat("yyyy-MM-dd")
       // val date = sdf.format(Date())
        val date = getDateDaysAgo(30)

        var query = "select * from $SPEND_TRACKER_TABLE where $DATE >= '$date'"
        println(query)
        var cursor = db.rawQuery(query,null)
        var spendList = ArrayList<Spend>()
        if(cursor.moveToFirst()){
            do {
                val date = sdf.parse(cursor.getString(cursor.getColumnIndex(DATE)))
                var obj = Spend(cursor.getInt(cursor.getColumnIndex(SPEND_ID)),cursor.getDouble(cursor.getColumnIndex(AMOUNT)),date,cursor.getString(cursor.getColumnIndex(TITLE)),cursor.getString(cursor.getColumnIndex(DESC)),cursor.getString(cursor.getColumnIndex(TYPE)))
                spendList.add(obj)
            }while(cursor.moveToNext())
        }
        println(spendList.size)
        return spendList
    }

}