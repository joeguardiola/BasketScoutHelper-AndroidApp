package edu.carthage.guardiola.joe.basketballscouthelper;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class AddAPlayer extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_aplayer);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_aplayer, menu);
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

    public void addPlayer(View button) {
        Log.d("AddAPlayer", "adding a player");

        //get the text from the text boxes
        EditText firstNameTB = (EditText)findViewById(R.id.first);
        String firstName = firstNameTB.getText().toString();

        EditText lastNameTB = (EditText)findViewById(R.id.last);
        String lastName = lastNameTB.getText().toString();

        EditText numberTB = (EditText)findViewById(R.id.number);
        int playerNumber = Integer.parseInt(numberTB.getText().toString());

        EditText teamTB = (EditText)findViewById(R.id.teamName);
        String teamName = teamTB.getText().toString();

        EditText heightTB = (EditText)findViewById(R.id.height);
        String playerHeight = heightTB.getText().toString();

        EditText playerExperienceTB = (EditText)findViewById(R.id.yearsExp);
        int playerExperience = Integer.parseInt(playerExperienceTB.getText().toString());

        EditText positionTB = (EditText)findViewById(R.id.position);
        String playerPosition = positionTB.getText().toString();

        //insert a new item in the db
        DBHelper.getInstance(this).insertAPlayer(firstName, lastName, playerNumber, teamName, playerHeight, playerExperience, playerPosition);

        //we are done with this activity, kill it
        finish();
    }
}
