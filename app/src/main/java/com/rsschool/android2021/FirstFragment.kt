package com.rsschool.android2021

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import java.lang.RuntimeException
import java.util.*

class FirstFragment : Fragment() {

    private var generateButton: Button? = null
    private var previousResult: TextView? = null
    private var fragmentCallback : FragmentCallback? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        previousResult = view.findViewById(R.id.previous_result)
        generateButton = view.findViewById(R.id.generate)

        val result = arguments?.getInt(PREVIOUS_RESULT_KEY)
        previousResult?.text = "Previous result: ${result.toString()}"

        // TODO: val min = ...
        // TODO: val max = ...
        generateButton?.setOnClickListener {
            // TODO: send min and max to the SecondFragment
            val minValue = view.findViewById<EditText>(R.id.min_value).text.toString().toIntOrNull()
            val maxValue = view.findViewById<EditText>(R.id.max_value).text.toString().toIntOrNull()
            if (validateData(minValue,maxValue)){
                fragmentCallback?.sendData(minValue!!,maxValue!!)
            }
        }
    }

    private fun validateData(minValue: Int?, maxValue: Int?): Boolean {
        if (minValue==null || maxValue==null){
            showErrorSnackBar(R.string.invalid_datatype)
            return false
        }else{
            if (minValue<0){
                showErrorSnackBar(R.string.min_value_must_be_positive)
                return false
            }
            if (minValue>maxValue){
                showErrorSnackBar(R.string.min_value_must_be_lower_than_max)
                return false
            }
        }
        return true
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivity){
            fragmentCallback= context
        }else{
            throw RuntimeException("Fragment must be attached to the main activity")
        }
    }

    private fun showErrorSnackBar(stringId: Int) {
        Snackbar.make(requireView(),context?.getString(stringId).toString(),Snackbar.LENGTH_LONG).setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        generateButton=null
        fragmentCallback=null
        previousResult=null
    }

    companion object {

        @JvmStatic
        fun newInstance(previousResult: Int): FirstFragment {
            val fragment = FirstFragment()
            val args = Bundle()
            args.putInt(PREVIOUS_RESULT_KEY, previousResult)
            fragment.arguments = args
            return fragment
        }

        private const val PREVIOUS_RESULT_KEY = "PREVIOUS_RESULT"
    }
}