package com.esilvwl.plex_manager.setting

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.esilvwl.plex_manager.R
import kotlinx.android.synthetic.main.fragment_setting.*


class SettingFragment : Fragment() {

    private val TAG = SettingFragment::class.simpleName
    private val PREF_SETTING_FILE = "Settings"
    private val PREF_IP :String = "ip"
    private val PREF_PORT = "port"
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        //setup sharedPreferences
        sharedPreferences =  context?.getSharedPreferences(PREF_SETTING_FILE, Context.MODE_PRIVATE)!!

        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getSavedValues()

        validateSetting.setOnClickListener(View.OnClickListener {
            saveSetting()
        })
    }

    private fun getSavedValues()
    {
        //Get all element from sharedPreferences
        val preferences = sharedPreferences!!.all
        if(preferences.isNotEmpty())
        {
            serverIp.setText(preferences[PREF_IP].toString())
            serverPort.setText(preferences[PREF_PORT].toString())
        }
    }

    private fun saveSetting(){
        sharedPreferences.edit()
            .putString(PREF_IP, serverIp.text.toString())
            .putString(PREF_PORT, serverPort.text.toString())
            .apply()
    }
}