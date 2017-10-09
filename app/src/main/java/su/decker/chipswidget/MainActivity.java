package su.decker.chipswidget;

import android.app.ActionBar;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.StyleSpan;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ActionBar mActionBar;
    private TextView mainContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "0.01Alpha (c) Decker, 2017\n", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        /*
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);

        this.mActionBar = getActionBar();

        if (this.mActionBar != null) {
            this.mActionBar.setDisplayHomeAsUpEnabled(true);
            this.mActionBar.setTitle(R.string.main_title);
        }
        */

        this.mainContent = (TextView) findViewById(R.id.main_content);
        Resources res = getResources();
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        SpannableString title = new SpannableString(res.getString(R.string.main_title));
        title.setSpan(new StyleSpan(1), 0, title.length(), 33);
        spannableStringBuilder.append("\n\n\n");

        spannableStringBuilder.append(title);
        spannableStringBuilder.append("\n\n");
        spannableStringBuilder.append(res.getText(R.string.main_content_one));
        spannableStringBuilder.append("\n\n");
        // https://stackoverflow.com/questions/9969789/clickable-word-inside-textview-in-android
        spannableStringBuilder.append(res.getText(R.string.wallet));
        spannableStringBuilder.append("\n\n");
        spannableStringBuilder.append(res.getText(R.string.main_content_two));
        this.mainContent.setText(spannableStringBuilder);
        this.mainContent.setMovementMethod(LinkMovementMethod.getInstance());

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
        if (id == R.id.action_settings) {
             return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
