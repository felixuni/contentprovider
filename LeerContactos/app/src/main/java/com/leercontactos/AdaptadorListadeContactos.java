package com.leercontactos;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by Javier on 16/09/2015.
 */
public class AdaptadorListadeContactos extends ResourceCursorAdapter {

    private final ContentResolver resolver;
    private Bitmap foto;

    public AdaptadorListadeContactos(Context principalContactos, int latout, Cursor c, int bandera) {
        super(principalContactos,latout,c,bandera);



    resolver=principalContactos.getContentResolver();

        //colocamos la foto por defecto si el contacto no la tiene

        foto= BitmapFactory.decodeResource(principalContactos.getResources(),R.drawable.ic_contact_picture);
    }

    //utilizar una nueva vista si es necesario

    public View nuevaVista(Context contexto,Cursor cursor, ViewGroup vista){

        LayoutInflater inflater=(LayoutInflater)contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return  inflater.inflate(R.layout.activity_principal_contactos,vista,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView nombres=(TextView)view.findViewById(R.id.name);

        nombres.setText(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));

        //set de la imagen del contacto

        ImageView fotico=(ImageView)view.findViewById(R.id.photo);
        Bitmap otrafoto=foto;

        String fotoUri=cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_URI));

        if(fotoUri!=null){
            InputStream entrada=null;
            //leemos la data de la memoria
            try {
                entrada=resolver.openInputStream(Uri.parse(fotoUri));

                if(entrada!=null){
                    otrafoto=BitmapFactory.decodeStream(entrada);

                }


            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Log.i("infromacion","FileNotFoundException");

            }


        }

        fotico.setImageBitmap(otrafoto);

    }
}
