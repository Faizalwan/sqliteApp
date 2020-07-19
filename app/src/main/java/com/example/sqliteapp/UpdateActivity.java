package com.example.sqliteapp;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class UpdateActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    DataHelper dbHelper;
    // Define TAG for logging.
    private static final String TAG = MainActivity.class.getSimpleName();

    // Define mSpinnerLabel for the label (the spinner item that the user chooses).
    private String mSpinnerLabel = "";

    protected Cursor cursor;
    Button tonSave;
    EditText textName, textPhone;
    CheckBox cbFamily, cbFriends;
    RadioGroup rgSim;

    private int idContact;
    private String groups, sim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

// CreateActivity the spinner.
        final Spinner spinner = (Spinner) findViewById(R.id.label_spinner);
        if (spinner != null) {
            spinner.setOnItemSelectedListener(this);
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

        dbHelper = new DataHelper(this);
        textName = (EditText) findViewById(R.id.editText_name);
        textPhone = (EditText) findViewById(R.id.editText_phone);
        cbFamily = (CheckBox) findViewById(R.id.cb_family);
        cbFriends = (CheckBox) findViewById(R.id.cb_friends);
        rgSim = (RadioGroup) findViewById(R.id.rgSim);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM contact WHERE id_contact = '" + getIntent().getStringExtra("id_contact") + "'",null);
        cursor.moveToFirst();
        if (cursor.getCount()>0)
        {
            cursor.moveToPosition(0);
            idContact = Integer.parseInt(cursor.getString(0).toString());
            textName.setText(cursor.getString(1).toString());
            textPhone.setText(cursor.getString(2).toString());
            int selectionPosition= adapter.getPosition(cursor.getString(3).toString());
            spinner.setSelection(selectionPosition);
            groups = cursor.getString(4).toString().replaceAll("\\s+","");
            switch (groups) {
                case "Family": cbFamily.setChecked(true); break;
                case "Friends": cbFriends.setChecked(true); break;
                case "FamilyFriends": cbFamily.setChecked(true); cbFriends.setChecked(true); break;
        }
            sim = cursor.getString(5).toString().replaceAll("\\s+","");
            switch (sim) {
                case "SIM1":
                    rgSim.check(R.id.rg1); break;
                case "SIM2":
                    rgSim.check(R.id.rg2); break;
        }
        }
        tonSave = (Button) findViewById(R.id.button_main);
// daftarkan even onClick pada btnSimpan
        tonSave.setOnClickListener(new View.OnClickListener() {@Override
        public void onClick(View arg0) {
// TODO Auto-generated method stub

            if ((textName != null) && (textPhone != null)) {
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
                db.execSQL("update contact set name='" +
                        textName.getText().toString() +"', phone='" + textPhone.getText().toString()+"', type='"+ mSpinnerLabel +"', groups='" + group.toString() +"', sim='" +
                        sim + "' where id_contact='" +
                        idContact+"'"); Toast.makeText(getApplicationContext(), "Success",
                        Toast.LENGTH_LONG).show();
            }
            MainActivity.ma.RefreshList();
            finish();
        }
        });
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
            Intent iread = new Intent(this, MainActivity.class);
            startActivity(iread);
            return true;
        } else if (id == R.id.action_create) {
            Intent icreate = new Intent(this, CreateActivity.class);
            startActivity(icreate);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
