package com.tjEnterprises.phase10Counter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class PlayerFragment : Fragment() {

    private lateinit var controller: Controller
    private lateinit var player: Player

    private lateinit var btnPhasen: Button
    private lateinit var editText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment and init views
        val view =  inflater.inflate(R.layout.fragment_player, container, false)
        btnPhasen = view.findViewById(R.id.btnPhasen)
        editText = view.findViewById(R.id.etPunkte)

        return view
    }

    override fun onStart() {
        super.onStart()
        //set correct values
        updateViews(player.getPlayerName(), player.getGesamtPunktzahl(), player.getPhasenAsString())

        //set player tag to identify the pressed button
        btnPhasen.tag = player.getPlayerNR()

        btnPhasen.setOnClickListener { v ->
            controller.phasenOnClick(v)
        }

        editText.setOnFocusChangeListener { _, _ ->
            try {
                if (editText.text.toString() != "") {
                    controller.addPunkteToPlayer(player.getPlayerNR(), editText.text.toString().toInt())
                    editText.setText("")
                }
            } catch (e: NumberFormatException){
                val toast = Toast.makeText(activity, getString(R.string.invalid_punkte_number), Toast.LENGTH_LONG)
                editText.setText("")
                toast.show()
            }

        }

        // stupid but works, keeping this as backup
        /*editText.setOnEditorActionListener { v, actionId, event ->
            if ((actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_DONE)
                && (editText.text.toString() != "")
            ) {
                controller.addPunkteToPlayer(player.getPlayerNR(), editText.text.toString().toInt())
                editText.setText("")

                Thread {
                    try {
                        val inst = Instrumentation()
                        inst.sendKeyDownUpSync(KeyEvent.KEYCODE_ENTER)
                    } catch (e: InterruptedException) {
                    }
                }.start()

                true
            } else {
                false
            }
        }*/
    }

    fun updateViews(playerName: String, playerPunkte: Int, playerPhasenAsString: String) {
        requireView().findViewById<TextView>(R.id.tvName).text = playerName
        requireView().findViewById<TextView>(R.id.tvPunkte).text = playerPunkte.toString()
        requireView().findViewById<TextView>(R.id.tvPhasen).text = playerPhasenAsString
    }

    fun setController(con: Controller, play: Player) {
        this.controller = con
        this.player = play
    }

    companion object {
        @JvmStatic
        fun newInstance(
        ): PlayerFragment =
            PlayerFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}