package com.coderz.creative.music.Fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.coderz.creative.music.R
import android.widget.ArrayAdapter
import android.widget.DatePicker
import com.coderz.creative.music.DB.DataBaseHelper
import com.coderz.creative.music.MainActivity
import kotlinx.android.synthetic.main.fragment_add.*
import java.text.SimpleDateFormat
import java.util.*
import android.app.DatePickerDialog
import android.view.View.OnClickListener
import android.widget.Toast
import com.coderz.creative.music.Model.Catagory
import com.coderz.creative.music.Model.Spend


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [AddFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [AddFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddFragment : Fragment(), DatePickerDialog.OnDateSetListener, OnClickListener {
    override fun onClick(view: View?) {
        when(view?.id){
            R.id.add_saving_spend ->
            {
                var db = DataBaseHelper(activity.applicationContext, MainActivity.DATABASE_NAME, null, MainActivity.DATABASE_VERSION)
                val df= SimpleDateFormat("yyyy / MM / dd ")
                val date_value = df.parse(date.text.toString())
                var spend = Spend(null, amount.text.toString().toDouble(),date_value ,title.text.toString(),description.text.toString(),(catgories.selectedItem as Catagory).type)
                db.createSpend(spend)
                Toast.makeText(activity,"Successfuly added", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, day: Int) {
        var calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR,year)
        calendar.set(Calendar.MONTH,month)
        calendar.set(Calendar.DAY_OF_MONTH,day)
        var df= SimpleDateFormat("yyyy / MM / dd ")
        date.text = df.format(calendar.time)
    }



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

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_add, container, false)
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        var db = DataBaseHelper(activity.applicationContext, MainActivity.DATABASE_NAME, null, MainActivity.DATABASE_VERSION)
        val dataAdapter = ArrayAdapter<Catagory>(activity,
                android.R.layout.simple_spinner_item, db.getAllCatgory())
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        catgories.adapter =dataAdapter

        var calender = Calendar.getInstance()
        var df= SimpleDateFormat("yyyy / MM / dd ")
        date.text = df.format(calender.time)
        date.setOnClickListener {
            DatePickerDialog(context, this, calender
                    .get(Calendar.YEAR), calender.get(Calendar.MONTH),
                    calender.get(Calendar.DAY_OF_MONTH)).show()
        }

        add_saving_spend.setOnClickListener(this)
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

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String, param2: String): AddFragment {
            val fragment = AddFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
