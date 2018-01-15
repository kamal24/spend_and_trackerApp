package com.coderz.creative.music.Fragment

import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.coderz.creative.music.DB.DataBaseHelper
import com.coderz.creative.music.MainActivity

import com.coderz.creative.music.R
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.animation.EasingFunction
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import java.util.*
import kotlin.math.exp

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [HomeFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {

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
        return inflater!!.inflate(R.layout.fragment_home, container, false)
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        if (mListener != null) {
            mListener!!.onFragmentInteraction(uri)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        var pieChart = pie_chart_spend

        var calendar = Calendar.getInstance()
        var currentMonth = calendar.get(Calendar.MONTH)
        var currentYear = calendar.get(Calendar.YEAR)

        var db = DataBaseHelper(activity.applicationContext, MainActivity.DATABASE_NAME, null, MainActivity.DATABASE_VERSION)

        pieChart.description.isEnabled=false
        pieChart.setUsePercentValues(true)

        pieChart.dragDecelerationFrictionCoef=0.95f

        pieChart.isDrawHoleEnabled = true
        pieChart.transparentCircleRadius=60f

        var mutableEntries = mutableListOf<PieEntry>()
        var entries:List<PieEntry> = mutableEntries
        var spendList = db.getAllSpendForToday(30)
        var expense = 0.0
        var saving  = 0.0
        spendList.forEach {
            if( it.catagory == "Saving")
                saving += it.amount
            else if(it.catagory == "Expense")
                expense += it.amount
        }

        var expensePercent = ((expense/saving)*100).toFloat();

        if(100- expensePercent >=0){
            mutableEntries.add(PieEntry(expensePercent,"Expense"))
            mutableEntries.add(PieEntry(100-expensePercent,"Saving"))
        }
        else{
            mutableEntries.add(PieEntry(expensePercent,"Expense"))
            mutableEntries.add(PieEntry(0f,"Saving"))
        }

        var dataSet = PieDataSet(entries,"Spend Tracker")
        dataSet.sliceSpace = 3f
        dataSet.selectionShift = 5f
        dataSet.colors= ColorTemplate.JOYFUL_COLORS.toMutableList()

        pieChart.animateY(1000, Easing.EasingOption.EaseInOutCubic)

        var pieData = PieData(dataSet)
        pieData.setValueTextSize(15f)
        pieData.setValueTextColor(Color.BLACK)
        pieChart.data = pieData

        var spendListForLastYear = db.getAllSpendForToday(365)
        var expenseList = ArrayList<Float>()
        var savingList = ArrayList<Float>()
        var labels= ArrayList<String>()

        var count =0

        while (count < 12){
            var expense=0.0
            var saving = 0.0
            spendListForLastYear.forEach {
                if(it.date.month== currentMonth && it.catagory=="Expense")
                    expense += it.amount
                else if(it.date.month== currentMonth && it.catagory=="Saving")
                    saving += it.amount

            }
            expenseList.add(expense.toFloat())
            savingList.add(saving.toFloat())
            when(currentMonth+1){
                1 -> labels.add("Jan "+currentYear)
                2 -> labels.add("Feb "+currentYear)
                3 -> labels.add("Mar "+currentYear)
                4 -> labels.add("Apr "+currentYear)
                5 -> labels.add("May "+currentYear)
                6 -> labels.add("Jun "+currentYear)
                7 -> labels.add("Jul "+currentYear)
                8 -> labels.add("Aug "+currentYear)
                9 -> labels.add("Sep "+currentYear)
                10 -> labels.add("Oct "+currentYear)
                11 -> labels.add("Nov "+currentYear)
                12 -> labels.add("Dec "+currentYear)
            }

            currentMonth--
            if(currentMonth<0) {
                currentMonth = 11
                currentYear--
            }

            count++
        }

        /* bar chart logic start from here */
        var barChart = bar_chart_spend
        var mutableBarEntries = mutableListOf<BarEntry>()
        var barEntries:List<BarEntry> = mutableBarEntries

        var num = 0

        expenseList.forEach {
            mutableBarEntries.add(BarEntry(num.toFloat(),floatArrayOf(expenseList[num],savingList[num]-expenseList[num])))
            num++
        }

        var bardataSet = BarDataSet(barEntries,"Bar Chart Tracker")
        bardataSet.stackLabels = arrayOf("Expense","Saving")
        bardataSet.colors = ColorTemplate.JOYFUL_COLORS.toMutableList()
        bardataSet.setDrawIcons(false)

        var barData = BarData(bardataSet)
        barChart.description.isEnabled=false

        barChart.xAxis.valueFormatter = IndexAxisValueFormatter(labels)
        barChart.data = barData
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
       /* if (context is OnFragmentInteractionListener) {
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
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String, param2: String): HomeFragment {
            val fragment = HomeFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
