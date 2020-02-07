package com.example.thingymajig

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_recipe.*

class AddRecipe : AppCompatActivity() {

    private val PERMISSION_CODE = 1000;
    private val RESULT_LOAD_IMAGE = 1
    private val IMAGE_CAPTURE_CODE = 1001
    var image_uri: Uri? = null
    var editTextArray = arrayListOf<EditText>() //buat bisa akses list langkahnya
    var type = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_recipe)

        btnAddStepAddRecipe.setOnClickListener{
            val linearLayout = findViewById(R.id.linearLayout) as LinearLayout
            val editText = EditText(this)
            editText.isVerticalScrollBarEnabled()
            editText.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT)
            editText.hint = "Ingredients"
            editTextArray.add(editText)
            linearLayout.addView(editText)
        }

        btnGaleryAddRecipe.setOnClickListener {
            type = "gallery"
            choosePhotoFromGallary()
        }

        //button click
        btnCameraAddRecipe.setOnClickListener {
            type = "camera"
            //if system os is Marshmallow or Above, we need to request runtime permission
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (checkSelfPermission(Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_DENIED ||
                    checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED){
                    //permission was not enabled
                    val permission = arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    //show popup to request permission
                    requestPermissions(permission, PERMISSION_CODE)
                }
                else{
                    //permission already granted
                    openCamera()
                }
            }
            else{
                //system os is < marshmallow
                openCamera()
            }
        }

        btnSaveAddRecipe.setOnClickListener{
            val nameRecipe = txtNameAddRecipe.text
            val ingredientArray = editTextArray
            val desc = txtDescriptionAddRecipe.text
            val pictFood = imgPictureAddRecipe.drawable

            // Tembak ke saveImageRecipe.php
            // Tembak ke saveRecipe.php
        }
    }

    private fun choosePhotoFromGallary() {
        val i = Intent(
            Intent.ACTION_PICK,
            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

        startActivityForResult(i, RESULT_LOAD_IMAGE)
    }

    private fun openCamera() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "New Picture")
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera")
        image_uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        //camera intent
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri)
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        //called when user presses ALLOW or DENY from Permission Request Popup
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                    //permission from popup was granted
                    openCamera()
                }
                else{
                    //permission from popup was denied
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        //called when image was captured from camera intent
        if (type.equals("camera")) {
            if (resultCode == Activity.RESULT_OK) {
                //set image captured to image view
                imgPictureAddRecipe.setImageURI(image_uri)
            }
        }

        if (type.equals("gallery")) {
            if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
                val selectedImage = data.data
                val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)

                val cursor = contentResolver.query(selectedImage,
                    filePathColumn, null, null, null)
                cursor!!.moveToFirst()

                val columnIndex = cursor.getColumnIndex(filePathColumn[0])
                val picturePath = cursor.getString(columnIndex)
                cursor.close()

                imgPictureAddRecipe.setImageBitmap(BitmapFactory.decodeFile(picturePath))
            }
        }
    }
}
