/*
 * This file is part of Prepay Credit for Android
 *
 * Copyright © 2013  Damien O'Reilly
 *
 * Prepay Credit for Android is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Prepay Credit for Android is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Prepay Credit for Android.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Report bugs or new features at: https://github.com/DamienOReilly/PrepayCredit
 * Contact the author at:          damienreilly@gmail.com
 */

package damo.three.ie.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import damo.three.ie.R;
import damo.three.ie.prepay.Constants;
import damo.three.ie.util.DateUtils;

public class InternetExpirationActivity extends Activity {

    private TextView textViewSummary = null;

    //TODO: add some Activity for when the user clicks on the notification

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_LEFT_ICON);
        setContentView(R.layout.internet_expired);
        getWindow().setFeatureDrawableResource(Window.FEATURE_LEFT_ICON, R.drawable.ic_launcher);

        textViewSummary = (TextView) findViewById(R.id.textViewSummary);
        Button buttonThree = (Button) findViewById(R.id.buttonThree);
        Button buttonOk = (Button) findViewById(R.id.buttonOk);

        /*
         * Action for buttonThree
         * Opens my3account.three.ie and closes this Activity
         */
        buttonThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(Constants.MY3_MAIN_PAGE));
                startActivity(i);
                finish();
            }

        });

        /**
         * Action for ok button.
         * Closes the activity.
         */
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        updateTextViewSummary();
    }

    /**
     * Update the text view to warn the user their internet add-on expires tonight.
     */
    private void updateTextViewSummary() {
        //TODO; we need a different message if the addon has already expired. (can happen if add-on expires when phone is off)
        StringBuilder sb = new StringBuilder();
        sb.append("According to your last refresh on: ");
        sb.append(getLastRefreshTime());
        sb.append(", your latest internet add-on is due to expire at midnight tonight.");
        textViewSummary.setText(sb.toString());
    }

    /**
     * Get last refreshed time
     *
     * @return Last refreshed time as {@link String}
     */
    private String getLastRefreshTime() {
        SharedPreferences sharedPref = getSharedPreferences("damo.three.ie.previous_usage",
                Context.MODE_PRIVATE);

        long refreshDate = sharedPref.getLong("last_refreshed_milliseconds", 0L);
        return DateUtils.formatDateTime(refreshDate);
    }
}