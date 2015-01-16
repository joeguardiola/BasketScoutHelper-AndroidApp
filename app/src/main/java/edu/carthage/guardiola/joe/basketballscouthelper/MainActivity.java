package edu.carthage.guardiola.joe.basketballscouthelper;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;


public class MainActivity extends ActionBarActivity {
    long selectedPlayerId = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get the player spinner
        Spinner spinner = (Spinner)findViewById(R.id.player_spinner);

        //query the db and get all the playerNames
        Cursor allPlayersResults = DBHelper.getInstance(this).getAllPlayerNames();

        //create an adapter to turn the cursor into something that can fill an adapter
        SimpleCursorAdapter allPlayersAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, allPlayersResults, new String[]{"playerName"}, new int[]{android.R.id.text1}, 0);

        //set the adapter
        spinner.setAdapter(allPlayersAdapter);

        //code to store the current player id. Where should it be set? Is this okay?
        selectedPlayerId = spinner.getSelectedItemId();
    }

    @Override
    public void onResume() {
        super.onResume();

        //refresh the players spinner
        Spinner playerSpinner = (Spinner)findViewById(R.id.player_spinner);
        CursorAdapter playerSpinnerAdapter = (CursorAdapter)playerSpinner.getAdapter();
        playerSpinnerAdapter.swapCursor(DBHelper.getInstance(this).getAllPlayerNames());
        playerSpinnerAdapter.notifyDataSetChanged();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.AddANewPlayer) {
            Log.d("MainActivity", "Adding a store");
            //go to the new store activity
            Intent intent = new Intent(this, AddAPlayer.class);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    
}
