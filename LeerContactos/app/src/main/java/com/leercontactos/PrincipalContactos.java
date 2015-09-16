package com.leercontactos;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

public class PrincipalContactos extends ListActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private AdaptadorListadeContactos Adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal);

        Adaptador= new AdaptadorListadeContactos(this,R.layout.activity_principal_contactos,null,0);
        setListAdapter(Adaptador);



        //Iniciamos la carga

        getLoaderManager().initLoader(0,null,this);



    }

    //creamos el filtro de consulta al contenedor paralos contactos

    static final String[] CONTACTOS_FILTRO=new String[]{

            ContactsContract.CommonDataKinds.Phone._ID,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.PHOTO_URI

    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principal_contactos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        Uri base;
        base=ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

        CursorLoader loader=new CursorLoader(this,
                base,
                CONTACTOS_FILTRO,
                null,
                null,
                null + " ASC");

        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        Adaptador.swapCursor(data);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Adaptador.swapCursor(null);
    }

    @SuppressLint("ResourceAsColor")
    public void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);
        Log.i("LA SELECCION FUE:",""+position);


    }
}
