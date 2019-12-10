package com.jangkriek.ridwanharts.movyou;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.SwitchPreference;
import android.provider.Settings;
import android.support.v7.app.ActionBar;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.RingtonePreference;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.Toast;

import com.jangkriek.ridwanharts.movyou.api.APICall;
import com.jangkriek.ridwanharts.movyou.api.APIClient;
import com.jangkriek.ridwanharts.movyou.favorit.ItemResult;
import com.jangkriek.ridwanharts.movyou.main.MenuFragment;
import com.jangkriek.ridwanharts.movyou.main.MovieItems2;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.jangkriek.ridwanharts.movyou.BuildConfig.API_KEY;

/**
 * A {@link PreferenceActivity} that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 * <p>
 * See <a href="http://developer.android.com/design/patterns/settings.html">
 * Android Design: Settings</a> for design guidelines and the <a
 * href="http://developer.android.com/guide/topics/ui/settings.html">Settings
 * API Guide</a> for more information on developing a Settings UI.
 */
public class SettingsActivity extends AppCompatPreferenceActivity {

    public final static int NOTIFICATION_ID = 101;
    public final static String NOTIFICATION_CHANNEL_ID = "112" ;
    public final static String MOVIE_DETAIL = "movie_detail";



    private static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {
            String stringValue = value.toString();



            if (preference instanceof ListPreference) {
                // For list preferences, look up the correct display value in
                // the preference's 'entries' list.
                ListPreference listPreference = (ListPreference) preference;
                int index = listPreference.findIndexOfValue(stringValue);

                // Set the summary to reflect the new value.
                preference.setSummary(
                        index >= 0
                                ? listPreference.getEntries()[index]
                                : null);

            } else if (preference instanceof RingtonePreference) {
                // For ringtone preferences, look up the correct display value
                // using RingtoneManager.
                if (TextUtils.isEmpty(stringValue)) {
                    // Empty values correspond to 'silent' (no ringtone).
                    preference.setSummary(R.string.pref_ringtone_silent);

                } else {
                    Ringtone ringtone = RingtoneManager.getRingtone(
                            preference.getContext(), Uri.parse(stringValue));

                    if (ringtone == null) {
                        // Clear the summary if there was a lookup error.
                        preference.setSummary(null);
                    } else {
                        // Set the summary to reflect the new ringtone display
                        // name.
                        String name = ringtone.getTitle(preference.getContext());
                        preference.setSummary(name);
                    }
                }

            } else {
                // For all other preferences, set the summary to the value's
                // simple string representation.
                preference.setSummary(stringValue);
            }

            return true;
        }
    };

    /**
     * Helper method to determine if the device has an extra-large screen. For
     * example, 10" tablets are extra-large.
     */
    private static boolean isXLargeTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_XLARGE;
    }

    /**
     * Binds a preference's summary to its value. More specifically, when the
     * preference's value is changed, its summary (line of text below the
     * preference title) is updated to reflect the value. The summary is also
     * immediately updated upon calling this method. The exact display format is
     * dependent on the type of preference.
     *
     * @see #sBindPreferenceSummaryToValueListener
     */
    private static void bindPreferenceSummaryToValue(Preference preference) {
        // Set the listener to watch for value changes.
        preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);

        // Trigger the listener immediately with the preference's
        // current value.
        sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
                PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(), ""));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new GeneralPreferenceFragment()).commit();
    }

    /**
     * Set up the {@link android.app.ActionBar}, if the API is available.
     */
    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onIsMultiPane() {
        return isXLargeTablet(this);
    }

    /**
     * {@inheritDoc}
     */

    /**
     * This method stops fragment injection in malicious applications.
     * Make sure to deny any unknown fragments here.
     */
    protected boolean isValidFragment(String fragmentName) {
        return PreferenceFragment.class.getName().equals(fragmentName)
                || GeneralPreferenceFragment.class.getName().equals(fragmentName);
    }

    /**
     * This fragment shows general preferences only. It is used when the
     * activity is showing a two-pane settings UI.
     */



    public static class GeneralPreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener, Preference.OnPreferenceClickListener {
        SwitchPreference sReminderDaily;
        SwitchPreference sTodayRelease;
        Preference lang;
        DailyReminder dailyReminder = new DailyReminder();
        ReleaseReminder releaseReminder = new ReleaseReminder();
        List<ItemResult>itemList = new ArrayList<>();
        List<ItemResult>itemList2 = new ArrayList<>();
        APICall apiService;
        Call<MovieItems2> apiCall;


        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_general);
            setHasOptionsMenu(true);
            Toast.makeText(getActivity(), "masuk onCreate", Toast.LENGTH_SHORT).show();


            sReminderDaily = (SwitchPreference)findPreference("key_daily");
            sReminderDaily.setOnPreferenceChangeListener(this);
            sTodayRelease = (SwitchPreference)findPreference("key_release");
            sTodayRelease.setOnPreferenceChangeListener(this);
            lang = (Preference)findPreference("key_language");
            lang.setOnPreferenceClickListener(this);
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();
            if (id == android.R.id.home) {

                startActivity(new Intent(getActivity(), MainActivity.class));
                return true;
            }
            return super.onOptionsItemSelected(item);
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            String key = preference.getKey();
            boolean value = (boolean)newValue;
            if (key.equals("key_daily")){
                if (value) dailyReminder.setAlarmDaily(getActivity());
                else dailyReminder.cancelAlarmNotification(getActivity());
            }else{
                if (value) setReleaseAlarm();
                else releaseReminder.cancelAlarmNotification(getActivity());
            }
            return true;
        }



        private void setReleaseAlarm(){
            apiService = APIClient.getClient().create(APICall.class);
            apiCall = apiService.getUpcoming(API_KEY, "en-us", 1, Locale.getDefault().getCountry());
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());
            Date date = new Date();
            final String tgl = dateFormat.format(date);
            apiCall.enqueue(new Callback<MovieItems2>() {
                @Override
                public void onResponse(Call<MovieItems2> call, Response<MovieItems2> response) {
                    itemList = response.body().getItemResults();
                    for(ItemResult itemResult : itemList){
                        if(itemResult.getmTanggal().equals(tgl)){
                            itemList2.add(itemResult);
                        }
                    }
                    releaseReminder.setAlarmRelease(getActivity(), itemList2);

                }

                @Override
                public void onFailure(Call<MovieItems2> call, Throwable t) {
                    Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();

                }
            });

        }


        @Override
        public boolean onPreferenceClick(Preference preference) {
            String key = preference.getKey();

            if(key.equals("key_language")){
                Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(intent);
            }

            return true;
        }
    }

}
