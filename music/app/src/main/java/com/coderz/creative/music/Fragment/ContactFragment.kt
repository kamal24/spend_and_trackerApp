package com.coderz.creative.music.Fragment

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.PermissionChecker.checkSelfPermission
import android.support.v7.widget.LinearLayoutManager

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import com.coderz.creative.music.LinearLayoutSpaceItemDecoration

import com.coderz.creative.music.Model.Songs
import com.coderz.creative.music.MyCustomAdapter
import com.coderz.creative.music.R
import com.coderz.creative.music.R.id.songList

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ContactFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ContactFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ContactFragment : Fragment()  {

    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null

    private var mListener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments.getString(ARG_PARAM1)
            mParam2 = arguments.getString(ARG_PARAM2)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        showContacts()
    }

    fun showContacts(){
        // Check the SDK version and whether the permission is already granted or not.
            var recyclerView = activity.findViewById<RecyclerView>(songList) as RecyclerView
            recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
            recyclerView.addItemDecoration(LinearLayoutSpaceItemDecoration(16))
            val songsList = readContacts()
            //println(songsList.size)
            // adding the adapter to recyclerView
           // if (songsList != null)
            recyclerView.adapter = MyCustomAdapter(songsList!!) {
                    //Anko library has its own definition of toast which we have addded in build.gradle
                    // toast("${it.name} Clicked")//A toast that displays the name of OS which you clicked on
            }
    }

    fun readContacts(): ArrayList<Songs>? {
            var cursor = context.contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null)
            var contactList = ArrayList<Songs>()
            while (cursor.moveToNext()){
                var img: Int =cursor.getInt(cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID)))
                if(img ==null)
                    img= R.drawable.dialer
                var song: Songs = Songs(R.drawable.dialer,cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                        ,cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)))
                contactList.add(song)
            }
            return contactList
    }



    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_contact, container, false)
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        if (mListener != null) {
            mListener!!.onFragmentInteraction(uri)
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"
        private val PERMISSIONS_REQUEST_READ_CONTACTS = 79

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ContactFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String, param2: String): ContactFragment {
            val fragment = ContactFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
