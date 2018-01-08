package com.coderz.creative.music.Fragment

import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.RadioGroup
import android.widget.RadioGroup.OnCheckedChangeListener
import android.widget.Toast
import com.coderz.creative.music.Model.Spend

import com.coderz.creative.music.R
import kotlinx.android.synthetic.main.fragment_summary.*
import kotlinx.android.synthetic.main.fragment_summary_fragment_layout.*
import java.text.SimpleDateFormat

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [SummaryFragmentLayout.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [SummaryFragmentLayout.newInstance] factory method to
 * create an instance of this fragment.
 */
class SummaryFragmentLayout : Fragment(), OnCheckedChangeListener {
    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
        Toast.makeText(context, mParam1, Toast.LENGTH_LONG).show()

        var newFilterList= filterList
        when(checkedId){
            R.id.filter_expense ->{
               // Toast.makeText(activity,"expense",Toast.LENGTH_LONG).show()
                newFilterList = newFilterList.filter { obj -> obj.catagory == "Expense" }

            }

            R.id.filter_saving ->{
             //  Toast.makeText(activity,"saving",Toast.LENGTH_LONG).show()
                newFilterList = newFilterList.filter { obj -> obj.catagory == "Saving" }
            }
        }

        spendList.clear()

        for(spend in newFilterList){
            val sdf = SimpleDateFormat("dd-MM-yyyy")
            val date = sdf.format(spend.date)
            spendList.add(spend.title+ " " +spend.amount.toString()+" "+date)
        }

        adapter = ArrayAdapter<String>(activity,
                R.layout.activity_listview, spendList)

        val listView = filter_list
        listView.adapter = adapter
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
        return inflater!!.inflate(R.layout.fragment_summary_fragment_layout, container, false)
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        if (mListener != null) {
            mListener!!.onFragmentInteraction(uri)
        }
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if(mParam1.equals("Day")){
            setFilterList(day_filterList)
        }
        else if (mParam1.equals("Month")){
            setFilterList(month_filterList)
        }
        else if (mParam1.equals("Week")){
            setFilterList(week_filterList)
        }
        filter_type.setOnCheckedChangeListener(this)
        filter_all.isChecked = true
    }

    fun getFilterList(): List<Spend>{
        return filterList
    }

    fun setFilterList(list:List<Spend>){
        filterList=list
    }

    fun setDayFilterList(list:List<Spend>){
        day_filterList=list
    }

    fun getDayFilterList(): List<Spend>{
        return day_filterList
    }

    fun setMonthFilterList(list:List<Spend>){
        month_filterList=list
    }

    fun getMonthFilterList(): List<Spend>{
        return month_filterList
    }

    fun setWeekFilterList(list:List<Spend>){
        week_filterList=list
    }

    fun getWeekFilterList(): List<Spend>{
        return week_filterList
    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"
        private var adapter:ArrayAdapter<String>? = null
        private var spendList = ArrayList<String>()
        private var filterList:List<Spend> = ArrayList<Spend>()
        private var day_filterList:List<Spend> = ArrayList<Spend>()
        private var month_filterList:List<Spend> = ArrayList<Spend>()
        private var week_filterList:List<Spend> = ArrayList<Spend>()
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SummaryFragmentLayout.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String, param2: String): SummaryFragmentLayout {
            val fragment = SummaryFragmentLayout()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
