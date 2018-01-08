package com.coderz.creative.music.Fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import com.coderz.creative.music.CustomPagerAdapter
import com.coderz.creative.music.DB.DataBaseHelper
import com.coderz.creative.music.MainActivity
import com.coderz.creative.music.Model.Catagory
import com.coderz.creative.music.MyCustomAdapter
import com.coderz.creative.music.R
import kotlinx.android.synthetic.main.fragment_add_detail.*


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [AddDetailFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [AddDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddDetailFragment : Fragment(), View.OnClickListener {
    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null
    private var adapter: CustomPagerAdapter? = null
    private var mListener: OnFragmentInteractionListener? = null

    fun setAdapter(adapter: CustomPagerAdapter){
        this.adapter = adapter
    }

    fun getAdapter() : CustomPagerAdapter?{
        return adapter
    }

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
        return inflater!!.inflate(R.layout.fragment_add_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        add.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        var db = DataBaseHelper(activity.applicationContext, MainActivity.DATABASE_NAME, null, MainActivity.DATABASE_VERSION)
        var selectedId = catagory_type.checkedRadioButtonId

        var catagory = Catagory(null,editText.text.toString(),activity.findViewById<RadioButton>(selectedId).text.toString())
        db.createCatagory(catagory)
        Toast.makeText(activity,"Successfuly created",Toast.LENGTH_LONG).show()
        adapter?.notifyDataSetChanged()
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

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddDetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String, param2: String): AddDetailFragment {
            val fragment = AddDetailFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
