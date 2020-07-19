package com.example.sqliteapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class CreateActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    DataHelper dbHelper;
    // Define TAG for logging.
    private static final String TAG = MainActivity.class.getSimpleName();

    // Define mSpinnerLabel for the label (the spinner item that the user chooses).
    private String mSpinnerLabel = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

    // CreateActivity the spinner.
        Spinner spinner = (Spinner) findViewById(R.id.label_spinner);
        if (spinner != null) { spinner.setOnItemSelectedListener(this);
        }

    // CreateActivity ArrayAdapter using the string array and default spinner layout.

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.labels_array, android.R.layout.simple_spinner_item);

    // Specify the layout to use when the list of choices appears.
        adapter.setDropDownViewResource (android.R.layout.simple_spinner_dropdown_item);

    // Apply the adapter to the spinner.
        if (spinner != null) {
            spinner.setAdapter(adapter);
        }
    }

    public void add(View view) {
        dbHelper = new DataHelper(this);

        EditText etName = (EditText) findViewById(R.id.editText_name);
        EditText etPhone = (EditText) findViewById(R.id.editText_phone);
        CheckBox cbFamily = (CheckBox)findViewById(R.id.cb_family);
        CheckBox cbFriends = (CheckBox)findViewById(R.id.cb_friends);
        RadioGroup rgSim = (RadioGroup)findViewById(R.id.rgSim);

        if ((etName != null) && (etPhone != null)) {
            StringBuffer group = new StringBuffer();
            if (cbFamily.isChecked()) {
                group.append("Family ");
        }
            if (cbFriends.isChecked()) {
                group.append("Friends");
            }

            String sim = new String();
            switch (rgSim.getCheckedRadioButtonId()) {
                case R.id.rg1:
                    sim = "SIM 1";
                    break; case R.id.rg2:
                    sim = "SIM 2";
                    break;
            }

            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL("INSERT INTO contact(name, phone, type, groups, sim) VALUES('" + etName.getText().toString() +"','"+
                            etPhone.getText().toString() +"','"+ mSpinnerLabel +"','" + group.toString() +"','"+ sim + "')");
            Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
        }
        MainActivity.ma.RefreshList();
        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
        mSpinnerLabel = adapterView.getItemAtPosition(pos).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Log.i(TAG, getString(R.string.nothing_selected));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
// Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_read) {
            Intent iread = new Intent(this, MainActivity.class); startActivity(iread);

            return true;
        } else if (id == R.id.action_create) {
            Intent icreate = new Intent(this, CreateActivity.class); startActivity(icreate);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

