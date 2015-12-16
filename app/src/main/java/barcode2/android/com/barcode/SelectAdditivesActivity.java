package barcode2.android.com.barcode;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class SelectAdditivesActivity extends ListActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String[] options = new String[]{"Pavojingas", "Būtina vengti", "Nepavojingas"};

        listView = getListView();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, options);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String additiveCategory = ((TextView) view.findViewById(android.R.id.text1)).getText().toString();

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("categoryToDetect", additiveCategory);
                startActivity(intent);
            }
        });
        listView.setAdapter(adapter);

    }
}
