package com.coderz.creative.music.Fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.coderz.creative.music.CustomPagerAdapter
import com.coderz.creative.music.DB.DataBaseHelper
import com.coderz.creative.music.MainActivity

import com.coderz.creative.music.R
import kotlinx.android.synthetic.main.fragment_catagory_list.*
import kotlinx.android.synthetic.main.fragment_show_expense.*
import kotlinx.android.synthetic.main.fragment_summary.*

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [SummaryFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [SummaryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SummaryFragment : Fragment() {

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
        return inflater!!.inflate(R.layout.fragment_summary, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        var pagerAdapter = CustomPagerAdapter(childFragmentManager)

        var db = DataBaseHelper(activity.applicationContext, MainActivity.DATABASE_NAME, null, MainActivity.DATABASE_VERSION)
        /*var spendList = ArrayList<String>()
        for(spend in db.getAllSpend()){
            spendList.add(spend.title+ " " +spend.amount.toString())
        }
        println("catagory size :"+spendList.size)*/
        var spendList = db.getAllSpend()

        var list= spendList

        var sfl1= DayFragment.newInstance("Day","Day Summary")
        sfl1.setFilterList(db.getAllSpendForToday(0))

        var sfl2= MonthFragment.newInstance("Month","Month Summary")
        sfl2.setFilterList(db.getAllSpendForToday(30))

        var sfl3= SummaryFragmentLayout.newInstance("Week","Week Summary")
        sfl3.setFilterList(db.getAllSpendForToday(7))

        pagerAdapter.addFragment(sfl1,"DAY")
        pagerAdapter.addFragment(sfl3,"WEEK")
        pagerAdapter.addFragment(sfl2,"MONTH")
        summary_view_pager.adapter = pagerAdapter
       // summary_view_pager.currentItem=1
        summary_tab_layout.setupWithViewPager(summary_view_pager)
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        if (mListener != null) {
            mListener!!.onFragmentInteraction(uri)
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
      /*  if (context is OnFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnFragmentInteractionListener")
        }*/
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

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SummaryFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String, param2: String): SummaryFragment {
            val fragment = SummaryFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
