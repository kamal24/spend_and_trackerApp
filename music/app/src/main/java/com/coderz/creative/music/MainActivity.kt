package com.coderz.creative.music

import android.Manifest
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import android.support.design.widget.NavigationView
import android.widget.Toast
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import com.coderz.creative.music.DB.DataBaseHelper
import com.coderz.creative.music.Fragment.*
import android.support.design.widget.Snackbar
import android.view.*


class MainActivity : AppCompatActivity(),ActivityCompat.OnRequestPermissionsResultCallback {
    var drawer : DrawerLayout? =null
    var navigationView : NavigationView? = null
    var mToggle: ActionBarDrawerToggle? = null

    companion object {
        const val REQUEST_PERMISSION = 1
        const val DATABASE_NAME = "coffer.db"
        const val DATABASE_VERSION =1
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        var inflater: MenuInflater? = menuInflater
        inflater?.inflate(R.menu.setting_menu,menu)
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setTheme(R.style.Grey)

       // var db = DataBaseHelper(applicationContext, DATABASE_NAME, null, DATABASE_VERSION)
        //db.getReadableDatabase();
        setContentView(R.layout.navigation_drawer)

        drawer = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.navigation)

        mToggle = ActionBarDrawerToggle(this,drawer, R.string.open,R.string.close)
        drawer?.addDrawerListener(mToggle!!)
        mToggle?.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //EnableRunTimePermission()
        setupDrawerContent()
    }

    fun EnableRunTimePermission(){
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.READ_CONTACTS)!= PackageManager.PERMISSION_GRANTED)
            requestContactsPermissions()
        else
            showContactPerview()
    }

    fun showContactPerview(){
        var fragmentManager:FragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().replace(R.id.content_frame, ContactFragment()).commit()
        navigationView?.setCheckedItem(R.id.nav_home)
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(mToggle!!.onOptionsItemSelected(item))
            return true

        return super.onOptionsItemSelected(item)
    }

    fun selectItemDrawer(item : MenuItem) : Unit {
        var fragment: Fragment? =null

        when (item.itemId) {
            R.id.nav_home -> {
               fragment = SummaryFragment()
            }

            R.id.nav_movies -> {
                fragment = ShowExpenseFragment()
            }

            R.id.nav_settings -> {
                fragment = AddFragment()
            }

            R.id.nav_photos ->{
                fragment = HomeFragment()
            }
        }

        var fragmentManager:FragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().replace(R.id.content_frame,fragment).commit()
        setTitle(item.title)
        item.setChecked(true)
        drawer?.closeDrawers()

    }

    fun setupDrawerContent() : Unit {
        navigationView?.setNavigationItemSelectedListener(object : NavigationView.OnNavigationItemSelectedListener{
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                selectItemDrawer(item)
                return  true
            }

        })
    }

    override  fun onBackPressed() {
        if(drawer!!.isDrawerOpen(Gravity.START))
            drawer?.closeDrawer(Gravity.START)
        else
            super.onBackPressed()
    }

    fun requestContactsPermissions() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this@MainActivity,
                Manifest.permission.READ_CONTACTS)) {

            Toast.makeText(this@MainActivity, "CONTACTS permission allows us to Access CONTACTS app inside request", Toast.LENGTH_LONG).show()
            Snackbar.make(findViewById(R.id.navigation), "Need Permission to access contacts ",
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction("Ok", object : View.OnClickListener {
                        override  fun onClick(view: View) {
                            ActivityCompat
                                    .requestPermissions(this@MainActivity,  arrayOf(Manifest.permission.READ_CONTACTS),
                                            REQUEST_PERMISSION)
                        }
                    })
                    .show()
        } else {
            ActivityCompat.requestPermissions(this@MainActivity, arrayOf(Manifest.permission.READ_CONTACTS), REQUEST_PERMISSION)
        }
    }

    override fun onRequestPermissionsResult(RC: Int, per: Array<String>, PResult: IntArray) {

        when (RC) {

            REQUEST_PERMISSION ->

                if (PResult.size > 0 && PResult[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(this@MainActivity, "Permission Granted, Now your application can access CONTACTS.", Toast.LENGTH_LONG).show()

                } else {

                    Toast.makeText(this@MainActivity, "Permission Canceled, Now your application cannot access CONTACTS.", Toast.LENGTH_LONG).show()

                }
        }
    }
}
