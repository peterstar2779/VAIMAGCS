package com.vaima.vaimagcs

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {

    // on below line we are creating variables
    // for text view and image view
    lateinit var outputTV: TextView
    lateinit var micIV: ImageView
    lateinit var dialogFlowBtn: Button

    // on below line we are creating a constant value
    private val REQUEST_CODE_SPEECH_INPUT = 1

    //Dialog ui
    lateinit var account: EditText
    lateinit var password: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // initializing variables of list view with their ids.
        outputTV = findViewById(R.id.idTVOutput)
        micIV = findViewById(R.id.idIVMic)
        dialogFlowBtn = findViewById(R.id.dialogflow)

        // on below line we are adding on click
        // listener for mic image view.
        micIV.setOnClickListener {
            // on below line we are calling speech recognizer intent.
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)

            // on below line we are passing language model
            // and model free form in our intent
            intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )

            // on below line we are passing our
            // language as a default language.
            intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE,
                Locale.getDefault()
            )

            // on below line we are specifying a prompt
            // message as speak to text on below line.
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to text")

            // on below line we are specifying a try catch block.
            // in this block we are calling a start activity
            // for result method and passing our result code.
            try {
                startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT)
            } catch (e: Exception) {
                // on below line we are displaying error message in toast
                Toast
                    .makeText(
                        this@MainActivity, " " + e.message,
                        Toast.LENGTH_SHORT
                    )
                    .show()
            }
        }//micIv

        dialogFlowBtn.setOnClickListener{
            showNLPdialog()
        }//dialogFlowBtn

    }//onCreate

    // on below line we are calling on activity result method.
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // in this method we are checking request
        // code with our result code.
        if (requestCode == REQUEST_CODE_SPEECH_INPUT) {
            // on below line we are checking if result code is ok
            if (resultCode == RESULT_OK && data != null) {

                // in that case we are extracting the
                // data from our array list
                val res: ArrayList<String> =
                    data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS) as ArrayList<String>

                // on below line we are setting data
                // to our output text view.
                outputTV.setText(
                    Objects.requireNonNull(res)[0]
                )
            }//if
        }//if
    }//onActivityResult

    fun showNLPdialog(){
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
//        builder.setTitle("Please enter account and password!")
        // Set up the input
//        var account = EditText(this)
//        var password = EditText(this)
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
//        account.setHint("Enter account")
//        account.inputType = InputType.TYPE_CLASS_TEXT
//        password.setHint("Enter password")
//        password.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
//        builder.setView(account)
//        builder.setView(password)
//        val inflater = MainActivity().layoutInflater
        val inflater: View = layoutInflater.inflate(R.layout.dialog_signin, null)
        account = inflater.findViewById(R.id.username)
        password = inflater.findViewById(R.id.password)
        builder.setView(inflater)
        // Set up the buttons
        builder.setPositiveButton("Sign in", DialogInterface.OnClickListener { dialog, which ->
            // Here you get get input text from the Edittext
            var account_Text = account.text.toString()
            var password_Text = password.text.toString()
            if(account_Text=="Peter" && password_Text=="muxdisp1"){
                Toast.makeText(this, "Login success!", Toast.LENGTH_LONG).show()
                val intent = Intent(this, DialogFlowActivity::class.java)
                // To pass any data to next activity
                intent.putExtra("keyIdentifier", "value1")
                // start your next activity
                startActivity(intent)
            }else Toast.makeText(this, "You input account or password isn't correct! Please try again!", Toast.LENGTH_LONG).show()
        })
        builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })

        builder.show()
    }

}