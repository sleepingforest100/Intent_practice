package kz.just_code.intent_kotlin

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import kz.just_code.intent_kotlin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpHelloButton()
        setUpTakePhotoButton()
        setUpOpenBrowserButton()
        setUpSendTextButton()
    }



    private fun setUpHelloButton() {
       binding.sayHelloBtn.setOnClickListener {
           if (isValid()){
               val intent = Intent(this, HelloActivity::class.java)
               intent.putExtra(ArgumentKey.NAME.name, binding.nameInputView.text.toString())
               startActivity(intent)

           } else {Toast.makeText(this, "You need to input your name", Toast.LENGTH_SHORT).show()
       }
       }
    }

    private fun setUpTakePhotoButton() {
        binding.takePhotoBtn.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            cameraResult.launch(intent)
        }
    }

    private val cameraResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){

        if(it.resultCode == Activity.RESULT_OK){
            val photo = it.data?.extras?.get("data") as Bitmap
            photo?.let {
                binding.newImageView.setImageBitmap(it)
            }
        }

    }

    private fun setUpOpenBrowserButton() {
        binding.openBrowserBtn.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://github.com/"))
            startActivity(intent)
        }
    }

    private fun setUpSendTextButton() {
        binding.sendBtn.setOnClickListener {
            if (isValid()){
                val intent = Intent (Intent.ACTION_SEND)
                intent.putExtra(Intent.EXTRA_TEXT, binding.nameInputView.text.toString())
                intent.type = "text/plain"
                val chooseIntent = Intent.createChooser(intent, "Title")
                startActivity(chooseIntent)

            } else {
                Toast.makeText(this, "You need to input your name", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun isValid() = !binding.nameInputView.text.isNullOrBlank()
}


enum class ArgumentKey{
    NAME
}