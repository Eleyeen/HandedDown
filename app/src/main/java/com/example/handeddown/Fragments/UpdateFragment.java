package com.example.handeddown.Fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.handeddown.Models.RegistrationResponseModel;
import com.example.handeddown.Network.ApiClienTh;
import com.example.handeddown.Network.ApiInterface;
import com.example.handeddown.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateFragment extends Fragment {
    private android.widget.SearchView searchView;
    ImageView imageView;
    Button uploadbutton;
    Switch buttonSwitch;
    File sourceFile;
    final int IMAJE_REQUEST = 1;
    final int CAMERA_CAPTURE = 100;
    final int GALLERY_CAPTURE = 40 ;
    final int RESULT_OK = 2 ;

    TextInputEditText titleUpload, prepaeationTime,difficultyLevel,describeHere;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_update, container, false);
        imageView =view.findViewById(R.id.plus_circle);
        uploadbutton = view.findViewById(R.id.upload_button);
        titleUpload =view.findViewById(R.id.titleUpload);
        prepaeationTime =view.findViewById(R.id.preparationTime);
        difficultyLevel =view.findViewById(R.id.difficulty_level23);
        describeHere =view.findViewById(R.id.desc);
        buttonSwitch = view.findViewById(R.id.button_switch);


        buttonSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){

                    uploadbutton.setOnClickListener(new View.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.N)
                        @Override
                        public void onClick(View v) {
                            apiCallPrivate();
                        }
                    });
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            cameraBuilder();
                        }
                    });

                    Toast.makeText(getContext(), "Me", Toast.LENGTH_SHORT).show();
                }else {

                    Toast.makeText(getContext(), "Public", Toast.LENGTH_SHORT).show();

                    uploadbutton.setOnClickListener(new View.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.N)
                        @Override
                        public void onClick(View v) {
                            apiCall();
                        }
                    });
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            cameraBuilder();
                        }
                    });

                }
            }
        });




        getImagePermision();
        customActionBar();
    return view;
    }
    //opening Camera View
    private void cameraBuilder() {

        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(getContext());
        pictureDialog.setTitle("Upload the picture.");
        String[] pictureDialogItem = {
               "Gallery" ,"Camera"

       };

        pictureDialog.setItems(pictureDialogItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch (which){
                    case 0:
                        galleryIntent();
                        break;
                    case 1:
                        cameraIntent();
                        break;
                }
            }
        });
        pictureDialog.show();
    }

    private void galleryIntent() {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, GALLERY_CAPTURE);
    }

    private void cameraIntent(){
        Intent captureIntent;
        captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(captureIntent, CAMERA_CAPTURE);
    }

    private void getImagePermision() {

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (shouldShowRequestPermissionRationale(
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
            }

            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    IMAJE_REQUEST);
        }
    }

    @SuppressLint("SetTextI18n")
    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(projection[0]);
        String filePath = cursor.getString(columnIndex);
        imageView.setImageBitmap(BitmapFactory.decodeFile(filePath));
        return cursor.getString(column_index);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void apiCall() {
        ApiInterface services = ApiClienTh.getApiClient().create(ApiInterface.class);
        // String title = ivRecipeTitle.getText().toString();
        // String description = ivDescription.getText().toString();
//        String author = ivAuthor.getText.toString();
        final RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), sourceFile);
        final MultipartBody.Part profileImage = MultipartBody.Part.createFormData("file", sourceFile.getName(), requestFile);
        RequestBody Bodyname = RequestBody.create(MediaType.parse("text/plain"), "upload_test");
        RequestBody titleBody = RequestBody.create(MediaType.parse("multipart/form-data"), "TitleDash");
        RequestBody desciptionBody = RequestBody.create(MediaType.parse("multipart/form-data"), "DESCRIPTION");
        RequestBody authorBody = RequestBody.create(MediaType.parse("multipart/form-data"), "its static");

        final Call<RegistrationResponseModel> registration = services.registration(titleBody , desciptionBody ,authorBody, profileImage, Bodyname );
        registration.enqueue(new Callback<RegistrationResponseModel>() {
            @Override
            public void onResponse(Call<RegistrationResponseModel> call, Response<RegistrationResponseModel> response) {
                Toast.makeText(getActivity(), ""+response, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<RegistrationResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(), ""+t, Toast.LENGTH_SHORT).show();

            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void apiCallPrivate() {
        ApiInterface services = ApiClienTh.getApiClient().create(ApiInterface.class);
        // String title = ivRecipeTitle.getText().toString();
        // String description = ivDescription.getText().toString();
//        String author = ivAuthor.getText.toString();
        final RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), sourceFile);
        final MultipartBody.Part profileImage = MultipartBody.Part.createFormData("file", sourceFile.getName(), requestFile);
        RequestBody Bodyname = RequestBody.create(MediaType.parse("text/plain"), "upload_test");
        RequestBody titleBody = RequestBody.create(MediaType.parse("multipart/form-data"), "TitleDash");
        RequestBody desciptionBody = RequestBody.create(MediaType.parse("multipart/form-data"), "DESCRIPTION");
        RequestBody authorBody = RequestBody.create(MediaType.parse("multipart/form-data"), "its static");

        final Call<RegistrationResponseModel> registration = services.registration(titleBody , desciptionBody ,authorBody, profileImage, Bodyname );
        registration.enqueue(new Callback<RegistrationResponseModel>() {
            @Override
            public void onResponse(Call<RegistrationResponseModel> call, Response<RegistrationResponseModel> response) {
                Toast.makeText(getActivity(), ""+response, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<RegistrationResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(), ""+t, Toast.LENGTH_SHORT).show();

            }
        });
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_CAPTURE && null != data) {

            Uri selectedImageUri = data.getData();
            String imagepath = getPath(selectedImageUri);
            sourceFile = new File(imagepath);

        }

        else if (requestCode == CAMERA_CAPTURE && data != null) {

            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            thumbnail.compress(Bitmap.CompressFormat.PNG, 90, bytes);
            sourceFile = new File(Environment.getExternalStorageDirectory(),
                    System.currentTimeMillis() + ".png");
            FileOutputStream fo;
            try {
                sourceFile.createNewFile();
                fo = new FileOutputStream(sourceFile);
                fo.write(bytes.toByteArray());
                fo.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            imageView.setImageBitmap(thumbnail);
        }
    }

    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        if (requestCode == IMAJE_REQUEST) {
            if (grantResults.length == 1 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
                // showRationale = false if user clicks Never Ask Again, otherwise true
                boolean showRationale = (boolean) shouldShowRequestPermissionRationale( Manifest.permission.READ_EXTERNAL_STORAGE);

                if (showRationale) {

                } else {
                }
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
    public void customActionBar() {

        ActionBar mActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        mActionBar.setDisplayShowHomeEnabled(true);
        mActionBar.setDisplayShowTitleEnabled(false);
        mActionBar.setDisplayHomeAsUpEnabled(true);

        mActionBar.hide();

    }
}



