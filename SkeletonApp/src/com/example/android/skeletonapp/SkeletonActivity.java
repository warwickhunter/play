/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.skeletonapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * This class provides a basic demonstration of how to write an Android
 * activity. Inside of its window, it places a single view: an EditText that
 * displays and edits some internal text.
 * 
 * Modified by Warwick Hunter (w.hunter@computer.org) to show some extra
 * features for an Android talk at the Gold Coast Java Users' Group.
 */
public class SkeletonActivity extends Activity {
    
    // Menu items
    private static final int    CLEAR_ID  = Menu.FIRST;
    private static final int    SAVE_ID   = Menu.FIRST + 1;
    private static final int    DELETE_ID = Menu.FIRST + 2;
    private static final int    SHARE_ID  = Menu.FIRST + 3;

    // Activities that return results
    private static final int    PICK_CONTACT = 1; 

    // Logging tag
    private static final String TAG = SkeletonActivity.class.getSimpleName();
    
    private EditText mEditor;
    
    public SkeletonActivity() {
    }

    /** Called with the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate our UI from its XML layout description.
        setContentView(R.layout.skeleton_activity);

        // Find the text editor view inside the layout, because we
        // want to do various programmatic things with it.
        mEditor = (EditText) findViewById(R.id.editor);

        // Hook up button presses to the appropriate event handler.
        ((Button) findViewById(R.id.back)).setOnClickListener(mBackListener);
        ((Button) findViewById(R.id.clear)).setOnClickListener(mClearListener);
        
        mEditor.setText(getText(R.string.main_label));
    }

    /**
     * Called when the activity is about to start interacting with the user.
     */
    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * Called when your activity's options menu needs to be created.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        // We are going to create menus. Note that we assign them
        // unique integer IDs, labels from our string resources, and
        // given them shortcuts.
        menu.add(0, CLEAR_ID,  0, R.string.clear).setShortcut('1', 'c').setIcon(android.R.drawable.ic_menu_close_clear_cancel);
        menu.add(0, SAVE_ID,   0, R.string.save).setShortcut('2', 's').setIcon(android.R.drawable.ic_menu_save);
        menu.add(0, DELETE_ID, 0, R.string.delete).setShortcut('3', 'd').setIcon(android.R.drawable.ic_menu_delete);
        menu.add(0, SHARE_ID,  0, R.string.share).setShortcut('4', 'x').setIcon(android.R.drawable.ic_menu_share);

        return true;
    }

    /**
     * Called right before your activity's option menu is displayed.
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        // Before showing the menu, we need to decide whether the clear
        // item is enabled depending on whether there is text to clear.
        menu.findItem(CLEAR_ID).setVisible(mEditor.getText().length() > 0);

        return true;
    }

    /**
     * Called when a menu item is selected.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case CLEAR_ID:
            mEditor.setText("");
            return true;
        case SAVE_ID:
            Toast.makeText(this, R.string.save_msg, Toast.LENGTH_SHORT).show();
            return true;
        case DELETE_ID:
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle(android.R.string.dialog_alert_title);
            alert.setMessage(R.string.delete_msg);
            alert.setPositiveButton(android.R.string.ok, null);
            alert.setNegativeButton(android.R.string.no, null);
            alert.create().show();
            return true;
        case SHARE_ID:
            // Use the Android contact picker to choose a contact  uri=content://contacts/people/
            Intent pickContact = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
            startActivityForResult(pickContact, PICK_CONTACT);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A call-back for when the user presses the back button.
     */
    OnClickListener mBackListener = new OnClickListener() {
        public void onClick(View v) {
            finish();
        }
    };

    /**
     * A call-back for when the user presses the clear button.
     */
    OnClickListener mClearListener = new OnClickListener() {
        public void onClick(View v) {
            mEditor.setText("");
        }
    };

    /**
     * A call-back for when an another activity invoked by this activity has finished it's work. 
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_CONTACT && resultCode == RESULT_OK && data != null) {
            // The Android contact picker has done its job
            Log.d(TAG, data.toString());
            String msg = getString(R.string.share_msg, getContactName(data.getData()));
            Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /** 
     * From a Contact URI get the contact's name 
     */
    private String getContactName(Uri contactUri) {
        Cursor cursor = null;
        try {
            cursor = getContentResolver().query(contactUri, new String[]{ContactsContract.Contacts.DISPLAY_NAME}, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                return cursor.getString(0);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }
}
